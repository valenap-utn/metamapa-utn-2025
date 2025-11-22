package ar.edu.utn.frba.dds.metamapa_client.web;

import ar.edu.utn.frba.dds.metamapa_client.clients.IServicioAgregador;
import ar.edu.utn.frba.dds.metamapa_client.dtos.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.metamapa_client.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.metamapa_client.dtos.HechoDTOOutput;
import ar.edu.utn.frba.dds.metamapa_client.dtos.UsuarioDTO;
import ar.edu.utn.frba.dds.metamapa_client.dtos.UsuarioNuevoDTO;
import ar.edu.utn.frba.dds.metamapa_client.services.IConexionServicioUser;
import ar.edu.utn.frba.dds.metamapa_client.services.IUsuarioCuentaService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
@Slf4j
public class PageController {
  //  private final ClientSeader cliente;
  private final IServicioAgregador cliente;
  private final IUsuarioCuentaService usuarioCuentaService;
  private final IConexionServicioUser conexionServicioUser;

  public PageController(/* ClientSeader cliente */ IServicioAgregador cliente, IUsuarioCuentaService usuarioCuentaService, IConexionServicioUser conexionServicioUser) {
    this.cliente = cliente;
    this.usuarioCuentaService = usuarioCuentaService;
    this.conexionServicioUser = conexionServicioUser;
  }

  @GetMapping({"/","/index"})
  public String home() {
    return "index";
  }

  @GetMapping("/iniciar-sesion")
  public String iniciarSesion() {
    return "iniciar-sesion";
  }


  @GetMapping("/main-gral")
  public String mainGral(HttpSession session, Model model) {
    model.addAttribute("titulo", "MetaMapa");
    model.addAttribute("isContribuyente", Boolean.TRUE.equals(session.getAttribute("isContribuyente")));
    model.addAttribute("isAdmin",         Boolean.TRUE.equals(session.getAttribute("isAdmin")));
    return "main-gral";
  }

  @GetMapping("/colecciones")
  public String colecciones(Model model) {
    List<ColeccionDTOOutput> colecciones = this.cliente.findColecciones();
    model.addAttribute("colecciones", colecciones);
    return "colecciones";
  }

  @GetMapping("/colecciones/{id}/nav-hechos")
  public String coleccionesNavHechos(Model model, @PathVariable UUID id) {
    FiltroDTO filtroDTO = new FiltroDTO();
    List<HechoDTOOutput> hechos =  this.cliente.findHechosByColeccionId(id, filtroDTO);
    model.addAttribute("hechos", hechos);
    model.addAttribute("filtros", filtroDTO);
    model.addAttribute("urlColeccion", "/colecciones/" + id + "/nav-hechos");
    return "hechos/nav-hechos";
  }

  @PostMapping("/colecciones/{id}/nav-hechos")
  public String coleccionesNavHechos(Model model, @PathVariable UUID id, @ModelAttribute("filtroDTO") FiltroDTO filtroDTO) {
    List<HechoDTOOutput> hechos =  this.cliente.findHechosByColeccionId(id, filtroDTO);
    model.addAttribute("hechos", hechos);
    model.addAttribute("filtros", filtroDTO);
    model.addAttribute("urlColeccion", "/colecciones/" + id + "/nav-hechos");
    return "hechos/nav-hechos";
  }

  @GetMapping("/crear-cuenta")
  public String crearCuenta(Model model) {
    model.addAttribute("cuenta", new UsuarioDTO());
    return "crear-cuenta";
  }

  @PostMapping("/auth/register")
  public String register(@ModelAttribute("cuenta") UsuarioDTO form) {
    UsuarioNuevoDTO nuevo = new UsuarioNuevoDTO();
    nuevo.setNombre(form.getNombre());
    nuevo.setApellido(form.getApellido());
    nuevo.setEmail(form.getEmail());
    nuevo.setFechaDeNacimiento(form.getFechaDeNacimiento());
    nuevo.setPassword(form.getPassword());
    nuevo.setRolSolicitado(form.getRol().toUpperCase());
    nuevo.setProviderOAuth(null); // No viene de OAuth => null

//    conexionServicioUser.crearUsuario(nuevo);

    try {
      conexionServicioUser.crearUsuario(nuevo);
      return "redirect:/iniciar-sesion";
    } catch (Exception e) {
      log.error("[PageController] Error creando usuario", e);

      // O redirigir a página de error,,,
      return "redirect:/crear-cuenta?error";
    }

  }


  @GetMapping("/privacidad")
  public String privacidad() {
    return "privacidad";
  }

  @GetMapping("/terminos")
  public String terminos() {
    return "terminos";
  }

  @GetMapping("/main")
  public String mainDeUsuario() {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    String rol = (String) request.getSession().getAttribute("rol");
    if(rol == null) return "redirect:/iniciar-sesion";
    return rol.startsWith("ADMIN") ? "redirect:/admin" : "redirect:/main-gral";
  }

  @GetMapping("/mi-perfil")
  public String miPerfil(HttpSession session, Model model, Authentication authentication) {

    UsuarioDTO usuario = usuarioCuentaService.obtenerUsuarioActual(session, authentication);

    if (usuario == null) {
      return "redirect:/iniciar-sesion";
    }

    // Cargamos más info del usuario si está en nuestro "cliente" local
    if (usuario.getId() != null) {
      try {
        UsuarioDTO completo = conexionServicioUser.findById(usuario.getId());
        if (completo != null) {
          usuario = completo;
        }
      } catch (Exception e) {
        // si no se encuentra, usamos el básico
      }
    }

    model.addAttribute("titulo", "Mi perfil");
    model.addAttribute("usuario", usuario);
    return "mi-perfil";
  }

}
