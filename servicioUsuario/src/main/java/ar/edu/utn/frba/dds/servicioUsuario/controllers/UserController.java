package ar.edu.utn.frba.dds.servicioUsuario.controllers;

import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.AuthResponseDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.CredencialesUserDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.RefreshTokenDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.RolesPermisosDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.UsuarioCreadoDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.UsuarioNuevoDTO;
import ar.edu.utn.frba.dds.servicioUsuario.servicios.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/usuarios")
  public ResponseEntity<UsuarioCreadoDTO> crearUsuario(@RequestBody UsuarioNuevoDTO usuario) {
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.crearUsuario(usuario));
  }

  @PostMapping("/auth")
  public ResponseEntity<AuthResponseDTO> autenticarUsuario(@RequestBody CredencialesUserDTO credenciales) {
    log.info("[UserController] POST /api/auth email={}", credenciales.getEmail());
    return ResponseEntity.ok(this.userService.autenticarUsuario(credenciales.getEmail(), credenciales.getPassword()));
  }

  @GetMapping("/auth/user/roles-permisos")
  public ResponseEntity<RolesPermisosDTO> findRolesPermiso(Authentication authentication) {
    String username = authentication.getName();
    return ResponseEntity.ok(this.userService.getRolesYPermisos(username));
  }

  @PostMapping("/auth/refresh")
  public ResponseEntity<AuthResponseDTO> refresh(@RequestBody RefreshTokenDTO request) {
    try {
      return ResponseEntity.ok(this.userService.hacerElRefrescoDeSesion(request));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/auth/me")
  public ResponseEntity<UsuarioCreadoDTO> me(Authentication authentication) {
    String email = authentication.getName();
    return ResponseEntity.ok(userService.getUsuarioPorEmail(email));
  }

  @GetMapping("/usuarios/search")
  public ResponseEntity<UsuarioCreadoDTO> buscarPorEmail(@RequestParam String email) {
    try {
      UsuarioCreadoDTO usuario = userService.getUsuarioPorEmail(email);
      return ResponseEntity.ok(usuario);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/usuarios/{id}")
  public ResponseEntity<UsuarioCreadoDTO> buscarPorId(@PathVariable Long id) {
    try {
      UsuarioCreadoDTO usuario = userService.getUsuarioPorId(id);
      return ResponseEntity.ok(usuario);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
