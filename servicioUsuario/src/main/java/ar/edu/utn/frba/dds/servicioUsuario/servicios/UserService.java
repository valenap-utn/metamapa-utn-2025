package ar.edu.utn.frba.dds.servicioUsuario.servicios;

import ar.edu.utn.frba.dds.servicioUsuario.exceptions.AdministradorInvalido;
import ar.edu.utn.frba.dds.servicioUsuario.exceptions.UsuarioConflicto;
import ar.edu.utn.frba.dds.servicioUsuario.exceptions.UsuarioInvalido;
import ar.edu.utn.frba.dds.servicioUsuario.exceptions.UsuarioNoEncontrado;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.AuthResponseDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.RefreshTokenDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.RolesPermisosDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.UsuarioCreadoDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.UsuarioNuevoDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.entities.Permiso;
import ar.edu.utn.frba.dds.servicioUsuario.models.entities.Rol;
import ar.edu.utn.frba.dds.servicioUsuario.models.entities.Usuario;
import ar.edu.utn.frba.dds.servicioUsuario.models.repositories.IUsuarioRepositoryJPA;
import ar.edu.utn.frba.dds.servicioUsuario.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.time.LocalDate;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
  private final IUsuarioRepositoryJPA usuarioRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  public UserService(IUsuarioRepositoryJPA usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
    this.passwordEncoder = new BCryptPasswordEncoder();
  }


  public AuthResponseDTO autenticarUsuario(String username, String password) {
    log.info("[UserService] login email={}", username);

    if (username == null) {
      throw new UsuarioInvalido("Email y password son obligatorios");
    }

    var optUsuario = usuarioRepository.findByEmail(username);
    if (optUsuario.isEmpty()) {
      log.warn("[UserService] login - usuario no encontrado username={}", username);
      throw new BadCredentialsException("Usuario o contraseña inválidos");
    }

    Usuario usuario = optUsuario.get();

    if (usuario.getProviderOAuth() == null && !passwordEncoder.matches(password, usuario.getPassword())) {
      log.warn("[UserService] login - password inválido para email={}", username);
      throw new BadCredentialsException("Usuario o contraseña inválidos");
    }
    return this.generarTokens(usuario);
  }

  private AuthResponseDTO generarTokens(Usuario usuario) {
    AuthResponseDTO authResponseDTO = new AuthResponseDTO();
    authResponseDTO.setAccessToken(JwtUtil.generarAccessToken(usuario));
    authResponseDTO.setRefreshToken(JwtUtil.generarRefreshToken(usuario));
    return authResponseDTO;
  }

  public RolesPermisosDTO getRolesYPermisos(String email){
    Usuario usuario = this.usuarioRepository.findByEmail(email).orElse(null);

    if (usuario == null) {
      throw new UsuarioNoEncontrado("El usuario no existe");
    }

    RolesPermisosDTO rolesPermisosDTO = new RolesPermisosDTO();
    rolesPermisosDTO.setPermisos(usuario.getPermisos().stream().map(Permiso::name).toList());
    rolesPermisosDTO.setRol(usuario.getRol().name());
    return rolesPermisosDTO;
  }


  public UsuarioCreadoDTO crearUsuario(UsuarioNuevoDTO usuario) {
    if (usuario.getNombre() == null || usuario.getEmail() == null
        || (usuario.getPassword() == null && usuario.getProviderOAuth() == null)) {
      throw new UsuarioInvalido("El perfil creado no cumple con los requisitos mínimos!");
    }

    Usuario usuarioEncontrado = this.usuarioRepository.findByEmail(usuario.getEmail()).orElse(null);
    if (usuarioEncontrado != null) {
      throw new UsuarioConflicto("Ya existe un usuario con ese nombre");
    }

    Usuario usuarioNuevo = new Usuario();
    usuarioNuevo.setNombre(usuario.getNombre());

    if (this.quiereSerAdministradorLocal(usuario) && this.leFaltanDatosAdmin(usuario)) {
      throw new AdministradorInvalido("No se puede crear un administrador con datos faltantes y que no vaya por Auth0");
    }

    usuarioNuevo.setApellido(usuario.getApellido());
    usuarioNuevo.setEmail(usuario.getEmail());
    usuarioNuevo.setFechaDeNacimiento(usuario.getFechaDeNacimiento());
    String rolStr = usuario.getRolSolicitado();
    if (rolStr == null || rolStr.isBlank()) {
      throw new UsuarioInvalido("Debe indicarse un rolSolicitado válido");
    }
    usuarioNuevo.setRol(Rol.valueOf(rolStr));

    // Manejo de password según el tipo de usuario
    if (usuario.getPassword() != null) {
      // Usuario local: password real
      usuarioNuevo.setPassword(this.passwordEncoder.encode(usuario.getPassword()));
    } else if (usuario.getProviderOAuth() == null) {
      throw new UsuarioInvalido("Al usuario le falta la contraseña");
    }

    usuarioNuevo.setProviderOAuth(usuario.getProviderOAuth());
    usuarioNuevo.setFechaCreacion(LocalDate.now());
    this.usuarioRepository.save(usuarioNuevo);

    return this.formarUsuarioCreadoDTO(usuarioNuevo);
  }

  private UsuarioCreadoDTO formarUsuarioCreadoDTO(Usuario usuarioNuevo) {
    UsuarioCreadoDTO usuarioCreadoDTO = new UsuarioCreadoDTO();
    usuarioCreadoDTO.setFechaCreacion(usuarioNuevo.getFechaCreacion());
    usuarioCreadoDTO.setEmail(usuarioNuevo.getEmail());

    // Los campos nuevos
    usuarioCreadoDTO.setId(usuarioNuevo.getId());
    usuarioCreadoDTO.setNombre(usuarioNuevo.getNombre());
    usuarioCreadoDTO.setApellido(usuarioNuevo.getApellido());
    usuarioCreadoDTO.setFechaDeNacimiento(usuarioNuevo.getFechaDeNacimiento());
    usuarioCreadoDTO.setRol(usuarioNuevo.getRol().name());

    return usuarioCreadoDTO;
  }

  private boolean leFaltanDatosAdmin(UsuarioNuevoDTO usuario) {
    return usuario.getApellido() == null || usuario.getFechaDeNacimiento() == null;
  }

  private Boolean quiereSerAdministradorLocal(UsuarioNuevoDTO usuario) {
    return usuario.getProviderOAuth() == null && usuario.getRolSolicitado().equals("ADMINISTRADOR");
  }

  public AuthResponseDTO hacerElRefrescoDeSesion(RefreshTokenDTO request) {
    String email = JwtUtil.validarToken(request.getRefreshToken());

    // Validar que el token sea de tipo refresh
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(JwtUtil.getKey())
        .build()
        .parseClaimsJws(request.getRefreshToken())
        .getBody();

    if (!"refresh".equals(claims.get("type"))) {
      throw new UsuarioInvalido("El refresh token no es valido");
    }
    Usuario usuario = this.usuarioRepository.findByEmail(email).orElse(null);
    if (usuario == null) {
      throw new UsuarioNoEncontrado("El usuario no existe");
    }
    String newAccessToken = JwtUtil.generarAccessToken(usuario);
    AuthResponseDTO authResponseDTO = new AuthResponseDTO();
    authResponseDTO.setAccessToken(newAccessToken);
    authResponseDTO.setRefreshToken(request.getRefreshToken());
    return authResponseDTO;

  }

  public UsuarioCreadoDTO getUsuarioPorEmail(String email) {
    Usuario usuario = this.usuarioRepository.findByEmail(email)
        .orElseThrow(() -> new UsuarioNoEncontrado("El usuario con el email " + email + " no existe"));
    return formarUsuarioCreadoDTO(usuario);
  }

  public UsuarioCreadoDTO getUsuarioPorId(Long id) {
    Usuario usuario = this.usuarioRepository.findById(id)
        .orElseThrow(() -> new UsuarioNoEncontrado("El usuario con el id " + id + " no existe"));
    return formarUsuarioCreadoDTO(usuario);
  }
}