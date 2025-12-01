package ar.edu.utn.frba.dds.clienteInterfaz.services;

import ar.edu.utn.frba.dds.clienteInterfaz.clients.utils.JwtUtil;
import ar.edu.utn.frba.dds.clienteInterfaz.dtos.AuthResponseDTO;
import ar.edu.utn.frba.dds.clienteInterfaz.dtos.UsuarioDTO;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
@Profile("prod")
@Slf4j
public class UsuarioCuentaService implements IUsuarioCuentaService {
  private final JwtUtil jwtUtil;
  private final IConexionServicioUser conexionServicioUsuario;

  public UsuarioCuentaService(JwtUtil jwtUtil, IConexionServicioUser conexionServicioUsuario) {
    this.jwtUtil = jwtUtil;
    this.conexionServicioUsuario = conexionServicioUsuario;
  }

  @Override
  public AuthResponseDTO ensureFromOAuth(String email, String nombre, String provider, String roleHint) {
    log.info("[UsuarioCuentaService] ensureFromOAuth email={} provider={} role={}", email, provider, roleHint);

    if (email == null || email.isBlank()) {
      log.warn("[UsuarioCuentaService] Email OAuth2 vacío");
      throw new IllegalArgumentException("Email OAuth2 vacío");
    }

    //Intentamos obtener usuario con email desde servicioUsuario
    try{
      AuthResponseDTO existente = conexionServicioUsuario.autenticar(email, null);
      if(existente != null) {
        log.info("[UsuarioCuentaService] Usuario ya existente id={} email={}",
            jwtUtil.getId(existente.getAccessToken()), email);
        return existente;
      }
    }catch (Exception e){
      log.warn("[UsuarioCuentaService] Error buscando usuario por email={} (seguimos con alta nueva)",
          email, e);
    }


    //Si no existe => Creamos usuario nuevo en servicioUsuario
    UsuarioDTO nuevo = new UsuarioDTO();
    nuevo.setEmail(email);
    nuevo.setNombre(nombre);
    nuevo.setRol(roleHint);

    log.info("[UsuarioCuentaService] Creando usuario nuevo email={} provider={}", email, provider);

    String providerOAuth = (provider == null || provider.isBlank()) ? "OAUTH" : provider;

//    UsuarioDTO creado = conexionServicioUsuario.crearUsuario(nuevo, providerOAuth);
    UsuarioDTO creado = conexionServicioUsuario.crearUsuario(nuevo, providerOAuth);
    log.info("[UsuarioCuentaService] Usuario creado id={} email={}",
        creado != null ? creado.getId() : null,
        creado != null ? creado.getEmail() : null);

    return conexionServicioUsuario.autenticar(email, null);
  }

  @Override
  public UsuarioDTO obtenerUsuarioActual(HttpSession session, Authentication authentication) {
    log.info("[UsuarioCuentaService] obtenerUsuarioActual...");

    // Flujo OAuth2: primero
    if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
      OAuth2User oAuth2User = oauthToken.getPrincipal();
      String email = (String) oAuth2User.getAttributes().get("email");

      log.info("[UsuarioCuentaService] OAuth2AuthenticationToken detectado. email atr={}", email);

      if (email != null && !email.isBlank()) {
        try {
          UsuarioDTO u = conexionServicioUsuario.findByEmail(email);
          if (u != null && session != null && u.getId() != null) {
            // lo guardo para el agregador, pero NO lo uso para volver a pedir /usuarios/{id}
            session.setAttribute("USER_ID", u.getId());
          }
          return u;
        } catch (Exception e) {
          log.warn("[UsuarioCuentaService] Error buscando usuario por email={} (OAuth2)", email, e);
        }
      }
    }

    // Flujo login normal con JWT (/auth/me)
    try {
      UsuarioDTO me = conexionServicioUsuario.getMe();
      if (me != null) {
        log.info("[UsuarioCuentaService] getMe() devolvió id={} email={}", me.getId(), me.getEmail());
        if (me.getId() != null && session != null) {
          session.setAttribute("USER_ID", me.getId());
        }
        return me;
      }
    } catch (Exception e) {
      log.warn("[UsuarioCuentaService] getMe() falló, probamos con Authentication clásico", e);
    }

    // Fallback login normal sin JWT: usar getName() como email
    if (authentication != null) {
      String username = authentication.getName();
      log.info("[UsuarioCuentaService] Authentication clásico. username={}", username);

      if (username != null && !username.isBlank()) {
        try {
          UsuarioDTO u = conexionServicioUsuario.findByEmail(username);
          if (u != null && u.getId() != null && session != null) {
            session.setAttribute("USER_ID", u.getId());
          }
          return u;
        } catch (Exception e) {
          log.warn("[UsuarioCuentaService] Error buscando usuario por email={} (user/pass)", username, e);
        }
      }
    }

    log.warn("[UsuarioCuentaService] No se pudo determinar el usuario actual");
    return null;
  }


  @Override
  public UsuarioDTO findByEmail(String email) {
    return conexionServicioUsuario.findByEmail(email);
  }



  @Override
  public UsuarioDTO findById(Long id) {
    return conexionServicioUsuario.findById(id);
  }
}
