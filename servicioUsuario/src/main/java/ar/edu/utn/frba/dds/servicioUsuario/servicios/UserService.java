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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final IUsuarioRepositoryJPA usuarioRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  public UserService(IUsuarioRepositoryJPA usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
    this.passwordEncoder = new BCryptPasswordEncoder();
  }


  public AuthResponseDTO autenticarUsuario(String username, String password) {
    Usuario usuario = this.usuarioRepository.findByEmail(username).orElse(null);

    if (usuario == null) {
      throw new UsuarioNoEncontrado("Esta incorrecto el username o la password");
    }
    if (!passwordEncoder.matches(password, usuario.getPassword())) {
      throw new UsuarioNoEncontrado("Esta incorrecto el username o la password");
    }

    return this.generarTokens(usuario);
  }

  private AuthResponseDTO generarTokens(Usuario usuario) {
    AuthResponseDTO authResponseDTO = new AuthResponseDTO();
    authResponseDTO.setAccessToken(JwtUtil.generarAccessToken(usuario));
    authResponseDTO.setRefreshToken(JwtUtil.generarRefreshToken(usuario));
    return authResponseDTO;
  }

  public RolesPermisosDTO getRolesYPermisos(String accessToken){
    String email = JwtUtil.validarToken(accessToken);
    Usuario usuarios = this.usuarioRepository.findByEmail(email).orElse(null);

    if (usuarios == null) {
      throw new UsuarioNoEncontrado("Esta incorrecto el username o la password");
    }

    RolesPermisosDTO rolesPermisosDTO = new RolesPermisosDTO();
    rolesPermisosDTO.setPermisos(usuarios.getPermisos().stream().map(Permiso::name).toList());
    rolesPermisosDTO.setRol(usuarios.getRol().name());
    return rolesPermisosDTO;
  }

  public UsuarioCreadoDTO crearUsuario(UsuarioNuevoDTO usuario) {
    if(usuario.getNombre() == null || usuario.getEmail() == null || usuario.getPassword() == null) {
      throw new UsuarioInvalido("El perfil creado no cumple con los requisitos mínimos!");
    }
    Usuario usuarioEncontrado= this.usuarioRepository.findByEmail(usuario.getEmail()).orElse(null);
    if(usuarioEncontrado != null) {
      throw new UsuarioConflicto("Ya existe un usuario con ese nombre");
    }
    Usuario usuarioNuevo = new Usuario();
    usuarioNuevo.setNombre(usuario.getNombre());
    if (this.quiereSerAdministradorLocal(usuario) && this.leFaltanDatosAdmin(usuario))
        throw new AdministradorInvalido("No se puede crear un administrador con datos faltantes y que no vaya por Auth0");
    usuarioNuevo.setApellido(usuario.getApellido());
    usuarioNuevo.setEmail(usuario.getEmail());
    usuarioNuevo.setFechaDeNacimiento(usuario.getFechaDeNacimiento());
    usuarioNuevo.setRol(Rol.valueOf(usuario.getRolSolicitado()));
    usuarioNuevo.setPassword(this.passwordEncoder.encode(usuario.getPassword()));
    usuarioNuevo.setFechaCreacion(LocalDate.now());
    this.usuarioRepository.save(usuarioNuevo);

    return this.formarUsuarioCreadoDTO(usuarioNuevo);
  }

  private UsuarioCreadoDTO formarUsuarioCreadoDTO(Usuario usuarioNuevo) {
    UsuarioCreadoDTO usuarioCreadoDTO = new UsuarioCreadoDTO();
    usuarioCreadoDTO.setFechaCreacion(usuarioNuevo.getFechaCreacion());
    usuarioCreadoDTO.setEmail(usuarioNuevo.getEmail());
    return usuarioCreadoDTO;
  }

  private boolean leFaltanDatosAdmin(UsuarioNuevoDTO usuario) {
    return usuario.getApellido() == null || usuario.getFechaDeNacimiento() == null;
  }

  private Boolean quiereSerAdministradorLocal(UsuarioNuevoDTO usuario) {
    return !usuario.getEsConAuth0() && usuario.getRolSolicitado().equals("ADMINISTRADOR");
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
}
