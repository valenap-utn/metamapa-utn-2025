package ar.edu.utn.frba.dds.clienteInterfaz.services;

import ar.edu.utn.frba.dds.clienteInterfaz.dtos.AuthResponseDTO;
import ar.edu.utn.frba.dds.clienteInterfaz.dtos.UsuarioDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;

public interface IUsuarioCuentaService {
  AuthResponseDTO ensureFromOAuth(String email, String nombre, String provider, String roleHint);

  UsuarioDTO obtenerUsuarioActual(HttpSession session, Authentication authentication);

  UsuarioDTO findById(Long id);

  UsuarioDTO findByEmail(String email);

}
