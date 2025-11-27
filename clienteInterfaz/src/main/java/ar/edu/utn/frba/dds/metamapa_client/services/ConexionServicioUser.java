package ar.edu.utn.frba.dds.metamapa_client.services;

import ar.edu.utn.frba.dds.metamapa_client.dtos.AuthResponseDTO;
import ar.edu.utn.frba.dds.metamapa_client.dtos.CredencialesUserDTO;
import ar.edu.utn.frba.dds.metamapa_client.dtos.LoginRequestDTO;
import ar.edu.utn.frba.dds.metamapa_client.dtos.LoginResponseDTO;
import ar.edu.utn.frba.dds.metamapa_client.dtos.RolesPermisosDTO;
import ar.edu.utn.frba.dds.metamapa_client.dtos.UsuarioCrearRequestDTO;
import ar.edu.utn.frba.dds.metamapa_client.dtos.UsuarioDTO;
import ar.edu.utn.frba.dds.metamapa_client.dtos.UsuarioNuevoDTO;
import ar.edu.utn.frba.dds.metamapa_client.exceptions.FalloEnLaAutenticacion;
import ar.edu.utn.frba.dds.metamapa_client.exceptions.ServicioDesconectado;
import ar.edu.utn.frba.dds.metamapa_client.exceptions.UsuarioNoEncontrado;
import ar.edu.utn.frba.dds.metamapa_client.services.internal.WebApiCallerService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;


@Component
@Profile("prod")
@Slf4j
public class ConexionServicioUser implements IConexionServicioUser {
  private final WebApiCallerService webApiCallerService;
  private final WebClient.Builder webClientBuilder;

  @Value("${api.servicioUsuarios.url}")
  private String baseUrl;
  private WebClient webClient;

  ConexionServicioUser(WebApiCallerService webApiCallerService, WebClient.Builder webClientBuilder) {
    this.webApiCallerService = webApiCallerService;
    this.webClientBuilder = webClientBuilder;
  }

  // El PostConstruct es para armar el baseUrl,
  // que para el momento del Constructor todavía no fue Inyectado
  @PostConstruct
  void init(){
    this.webClient = this.webClientBuilder.baseUrl(baseUrl).build();
    log.info("[ConexionServicioUser] WebClient inicializado con baseUrl={}", baseUrl);
  }

  @Override
  public AuthResponseDTO getTokens(String username, String password) {
    try {
      CredencialesUserDTO credenciales = new CredencialesUserDTO(username, password);
      return this.webClient.post()
          .uri(uriBuilder -> uriBuilder.path("/api/auth").build())
          .bodyValue(credenciales)
          .retrieve()
          .bodyToMono(AuthResponseDTO.class)
          .block();
    }catch (WebClientResponseException e) { //// errores HTTP
      //log.error(e.getMessage());
      if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
        throw new UsuarioNoEncontrado("Las credenciales de usuario no se encuentran en el sistema");
      } else if(e.getStatusCode() == HttpStatus.BAD_REQUEST) {
        throw new FalloEnLaAutenticacion("El email o la password no son válidos");
      }
      throw new RuntimeException("Error en el servicio de autenticación: " + e.getMessage(), e);
    } catch (Exception e) {
      throw new ServicioDesconectado("Error de conexión con el servicio de autenticación: " + e.getMessage());
    }
  }

  @Override
  public RolesPermisosDTO getRolesPermisos(String tokenAcceso) {
    try {
      return webApiCallerService.getWithAuth(
          baseUrl + "/api/auth/user/roles-permisos",
          tokenAcceso,
          RolesPermisosDTO.class
      );
    } catch (Exception e) {
      throw new RuntimeException("Error al obtener roles y permisos: " + e.getMessage(), e);
    }
  }

  // Para usuario actual (lee el accessToken de sesión y maneja el refresh)
  @Override
  public UsuarioDTO getMe() {
    return webApiCallerService.get(baseUrl + "/api/auth/me", UsuarioDTO.class);
  } // a chequear,,,

  @Override
  public UsuarioDTO findByEmail(String email) {
    String url = UriComponentsBuilder
        .fromHttpUrl(baseUrl + "/api/usuarios/search")
        .queryParam("email", email)
        .toUriString();

    log.info("[ConexionServicioUser] findByEmail email={} -> GET {}", email, url);

    return webApiCallerService.get(url, UsuarioDTO.class);
  }

  @Override
  public UsuarioDTO findById(Long id) {
    String url = baseUrl + "/api/usuarios/" + id;
    return webApiCallerService.get(url, UsuarioDTO.class);
  }

  @Override
  public LoginResponseDTO login(String email, String password) {
    String url = baseUrl + "/api/auth/login";
    log.info("[ConexionServicioUser] login email={} -> POST {}", email, url);

    var request = new LoginRequestDTO();
    request.setEmail(email);
    request.setPassword(password);

    return webApiCallerService.post(url, request, LoginResponseDTO.class);
  }

  // REGISTRO
  @Override
  public UsuarioDTO crearUsuario(UsuarioDTO dto, String providerOAuth) {

    UsuarioCrearRequestDTO request = new UsuarioCrearRequestDTO();
    request.setNombre(dto.getNombre());
    request.setApellido(dto.getApellido());
    request.setEmail(dto.getEmail());
    request.setPassword(
        dto.getPassword() != null ? dto.getPassword() : ""
    );
    request.setProviderOAuth(providerOAuth);
    request.setRolSolicitado(dto.getRol());

    String url = baseUrl + "/api/usuarios";
    log.info("[ConexionServicioUser] crearUsuario email={} -> POST {}", dto.getEmail(), url);

    try {
      return this.webClient
          .post()
          .uri(uriBuilder -> uriBuilder
              .path("/api/usuarios").build())
          .bodyValue(request)
          .retrieve()
          .bodyToMono(UsuarioDTO.class)
          .block();
    } catch (WebClientResponseException e) {

      if (e.getStatusCode() == HttpStatus.CONFLICT) {
        // usuario ya existía → lo buscamos
        log.warn("[ConexionServicioUser] crearUsuario: 409 CONFLICT, usuario ya existe; buscamos por email={}", dto.getEmail());
        return this.findByEmail(dto.getEmail());
      }
      throw e;
    }
  }

  public AuthResponseDTO autenticar(String email, String password) {
    return webApiCallerService.login(email, password);
  }

  @Override
  public UsuarioDTO crearUsuario(UsuarioNuevoDTO dto) {
    log.info("[ConexionServicioUser] crearUsuario (nuevo) POST {}", baseUrl + "/api/usuarios");
    return this.webClient
            .post()
            .uri(uriBuilder -> uriBuilder
                    .path("/api/usuarios").build())
            .bodyValue(dto)
            .retrieve()
            .bodyToMono(UsuarioDTO.class)
            .block();
  }

  @Override
  public UsuarioDTO buscarUsuarioPorEmail(String email, String accessToken) {
    String url = baseUrl + "/api/usuarios/search?email=" + email;
    return webApiCallerService.getWithAuth(url, accessToken, UsuarioDTO.class);
  }
}
