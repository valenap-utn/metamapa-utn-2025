package ar.edu.utn.frba.dds.servicioUsuario.controllers;

import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.AuthResponseDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.CredencialesUserDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.RefreshTokenDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.RolesPermisosDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.UsuarioCreadoDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.UsuarioNuevoDTO;
import ar.edu.utn.frba.dds.servicioUsuario.servicios.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/usuarios")
  public ResponseEntity<UsuarioCreadoDTO> crearUsuario(UsuarioNuevoDTO usuario) {
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.crearUsuario(usuario));
  }

  @PostMapping("/auth")
  public ResponseEntity<AuthResponseDTO> autenticarUsuario(@RequestBody CredencialesUserDTO credenciales) {
    return ResponseEntity.ok(this.userService.autenticarUsuario(credenciales.getEmail(), credenciales.getPassword()));
  }

  @GetMapping("/auth/user/roles-permisos")
  public ResponseEntity<RolesPermisosDTO> findRolesPermiso(Authentication authentication) {
    String username = authentication.getName();
    return ResponseEntity.ok(this.userService.getRolesYPermisos(username));
  }

  @PostMapping("/refresh")
  public ResponseEntity<AuthResponseDTO> refresh(@RequestBody RefreshTokenDTO request) {
    try {
      return ResponseEntity.ok(this.userService.hacerElRefrescoDeSesion(request));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
