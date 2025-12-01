package ar.edu.utn.frba.dds.clienteInterfaz.services;

import ar.edu.utn.frba.dds.clienteInterfaz.dtos.AuthResponseDTO;
import ar.edu.utn.frba.dds.clienteInterfaz.dtos.LoginResponseDTO;
import ar.edu.utn.frba.dds.clienteInterfaz.dtos.RolesPermisosDTO;
import ar.edu.utn.frba.dds.clienteInterfaz.dtos.UsuarioDTO;
import ar.edu.utn.frba.dds.clienteInterfaz.dtos.UsuarioNuevoDTO;

public interface IConexionServicioUser {
  AuthResponseDTO getTokens(String username, String password);
  RolesPermisosDTO getRolesPermisos(String tokenAcceso);
//  UsuarioDTO crearUsuario(UsuarioDTO dto, String providerOAuth);
  UsuarioDTO crearUsuario(UsuarioDTO dto, String providerOAuth);
  UsuarioDTO getMe();
  UsuarioDTO findByEmail(String email);
  UsuarioDTO findById(Long id);
  LoginResponseDTO login(String email, String password);
  AuthResponseDTO autenticar(String email, String password);
  UsuarioDTO crearUsuario(UsuarioNuevoDTO dto);
  UsuarioDTO buscarUsuarioPorEmail(String email, String accessToken);
}
