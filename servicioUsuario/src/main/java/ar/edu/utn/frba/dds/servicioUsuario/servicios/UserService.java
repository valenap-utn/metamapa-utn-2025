package ar.edu.utn.frba.dds.servicioUsuario.servicios;

import ar.edu.utn.frba.dds.servicioUsuario.models.repositories.IUsuarioRepositoryJPA;
import ar.edu.utn.frba.dds.servicioUsuario.utils.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserService {
  private final IUsuarioRepositoryJPA usuarioRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  public UserService(IUsuarioRepositoryJPA usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
    this.usuarioRepository = usuarioRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public String generarAccessToken(String username) {
    return JwtUtil.generarAccessToken(username);
  }

  public String generarRefreshToken(String username) {
    return JwtUtil.generarRefreshToken(username);
  }

  public void autenticarUsuario(String username, String password) {

  }

  public void getRolesYPermisos(String accessToken){

  }

}
