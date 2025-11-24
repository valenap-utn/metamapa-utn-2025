package ar.edu.utn.frba.dds.metamapa_client.web;

import ar.edu.utn.frba.dds.metamapa_client.clients.IFuenteDinamica;
import ar.edu.utn.frba.dds.metamapa_client.clients.IServicioAgregador;
import ar.edu.utn.frba.dds.metamapa_client.clients.utils.JwtUtil;
import ar.edu.utn.frba.dds.metamapa_client.dtos.*;
import ar.edu.utn.frba.dds.metamapa_client.services.IUsuarioCuentaService;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/hechos")
public class HechosController {
  //  private final ClientSeader agregador;
  private final IServicioAgregador agregador;
  private final IUsuarioCuentaService usuarioCuentaService;
  private final IFuenteDinamica fuenteDinamica;
  private final JwtUtil jwtUtil;
  @Value("${api.servicioFuenteDinamica.url}")
  private String urlFuenteDinamica;
  public HechosController(IServicioAgregador agregador, IUsuarioCuentaService usuarioCuentaService, IFuenteDinamica fuenteDinamica, JwtUtil jwtUtil) {
    this.agregador = agregador;
    this.usuarioCuentaService = usuarioCuentaService;
    this.fuenteDinamica = fuenteDinamica;
    this.jwtUtil = jwtUtil;
  }

  @GetMapping("/{idHecho}")
  public String hechoCompleto(@PathVariable Long idHecho, Model model, @RequestParam(value = "urlColeccion", required = false) String urlColeccion) {
    HechoDTOOutput hecho = this.agregador.getHecho(idHecho);
    model.addAttribute("titulo", "Detalle de Hecho");
    model.addAttribute("hecho", hecho);
    model.addAttribute("urlColeccion", urlColeccion);
    return "hechos/hecho-completo";
  }

  @GetMapping("/nav-hechos")
  public String navHechos(Model model) {
    FiltroDTO filtroDTO = new FiltroDTO();
    List<HechoDTOOutput> hechos = this.agregador.findAllHechos(filtroDTO);

    model.addAttribute("hechos", hechos);
    model.addAttribute("filtros", filtroDTO);
    model.addAttribute("titulo", "Listado de todos los hechos");
    return "hechos/nav-hechos";
  }

  @PostMapping("/nav-hechos")
  public String navHechosPost(Model model, @ModelAttribute("filtros") FiltroDTO filtroDTO) {
    List<HechoDTOOutput> hechos = this.agregador.findAllHechos(filtroDTO);
    model.addAttribute("hechos", hechos);
    model.addAttribute("filtros", filtroDTO);
    model.addAttribute("titulo", "Listado de todos los hechos");
    return "hechos/nav-hechos";
  }

  @GetMapping("/subir-hecho")
  public String subirHecho(Model model) {
    model.addAttribute("hecho", new HechoDTOInput());
    return "hechos/subir-hecho";
  }

  @PostMapping("/subir-hecho")
  public String subirHechoPost(@Valid @ModelAttribute("hecho") HechoDTOInput hechoDtoInput, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession session,Authentication authentication) {
    log.info("[HechosController] Entró a POST /hechos/subir-hecho");

    if (bindingResult.hasErrors()) {
      log.warn("[HechosController] Errores de validación al subir hecho: {}", bindingResult.getAllErrors());

      redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.hecho", bindingResult);
      redirectAttributes.addFlashAttribute("hecho", hechoDtoInput);
      redirectAttributes.addFlashAttribute("titulo", "Revisá los campos marcados");
      return "redirect:/hechos/subir-hecho";
    }

    try {
      //var usuario = usuarioCuentaService.obtenerUsuarioActual(session, authentication);
      String accessToken = (String) session.getAttribute("accessToken");
      Long userId = null;
      if (accessToken != null) {
        userId = jwtUtil.getId(accessToken);
      }
      hechoDtoInput.setIdUsuario(userId);

      log.info("[HechosController] Llamando a agregador.crearHecho() usuarioId={}", userId);
      fuenteDinamica.crearHecho(hechoDtoInput, this.urlFuenteDinamica); // o la baseUrl que uses
      log.info("[HechosController] Hecho creado OK en agregador");

      if (userId != null) {
        redirectAttributes.addFlashAttribute("mensajeOk", "Hecho cargado exitosamente!");
        return "redirect:/hechos/mis-hechos";
      }

      return "redirect:/hechos/subir-hecho";
    } catch (Exception e) {
      log.error("[HechosController] Error al crear el hecho", e);
      redirectAttributes.addFlashAttribute("error", "Ha ocurrido un error al crear el hecho. Volvé a intentarlo");
      return "redirect:/hechos/subir-hecho";
    }
  }


  private static final Logger log = LoggerFactory.getLogger(HechosController.class);

  // Para ver Hechos subidos por uno mismo
  @GetMapping("/mis-hechos")
  @PreAuthorize("hasRole('CONTRIBUYENTE')")
  public String misHechos(@RequestParam(defaultValue = "12") int limit, @RequestParam(defaultValue = "12") int step, HttpSession session, Model model, Authentication authentication) {

    log.info("[HechosController] auth principal={} authorities={}", authentication != null ? authentication.getName() : null, authentication != null ? authentication.getAuthorities() : null);

    UsuarioDTO usuario = usuarioCuentaService.obtenerUsuarioActual(session, authentication);

    if (usuario == null || usuario.getId() == null) {
      log.warn("[HechosController] Usuario actual nulo o sin ID. usuario={}", usuario);
      model.addAttribute("error", "No se pudo identificar al usuario actual");
      return "redirect:/iniciar-sesion";
    }

    Long userId = usuario.getId();
    log.info("[HechosController] Cargando hechos del usuario id={}", userId);

    // Hechos en revisión
    Set<Long> enRevision = Collections.emptySet();
    try {
      enRevision = agregador.findAllSolicitudesEdicion().stream()
          .filter(h -> "PENDIENTE".equalsIgnoreCase(h.getEstado()))
          .map(SolicitudEdicionDTO::getIdHecho)
          .collect(Collectors.toSet());
    } catch (Exception e) {
      log.error("[HechosController] Error obteniendo solicitudes de edición", e);
      // si falla, seguimos con conjunto vacío: no filtramos nada por revisión
    }

    // Hechos del usuario
    List<HechoDTOOutput> all;
    try {
      Set<Long> finalEnRevision = enRevision;
      all = agregador.listHechosDelUsuario(userId).stream()
          .filter(h -> "APROBAR".equalsIgnoreCase(h.getEstado()))
          .filter(h -> !finalEnRevision.contains(h.getId()))
          .sorted((a, b) -> {
            var ka = (a.getFechaAprobacion() != null ? a.getFechaAprobacion() : a.getFechaCarga());
            var kb = (b.getFechaAprobacion() != null ? b.getFechaAprobacion() : b.getFechaCarga());
            if (ka == null && kb == null) return 0;
            if (ka == null) return 1;
            if (kb == null) return -1;
            return kb.compareTo(ka);
          })
          .toList();
    } catch (Exception e) {
      log.error("[HechosController] Error consultando hechos del usuario {}", userId, e);
      model.addAttribute("error", "No se pudieron cargar tus hechos en este momento.");
      model.addAttribute("items", List.of());
      model.addAttribute("shown", 0);
      model.addAttribute("total", 0);
      model.addAttribute("hasMore", false);
      model.addAttribute("nextLimit", 0);
      model.addAttribute("step", step);
      model.addAttribute("titulo", "Mis Hechos");
      return "hechos/mis-hechos";
    }

    int total = all.size();
    int shown = Math.min(Math.max(limit, 0), total);

    model.addAttribute("items", all.subList(0, shown));
    model.addAttribute("shown", shown);
    model.addAttribute("total", total);
    model.addAttribute("hasMore", shown < total);
    model.addAttribute("nextLimit", Math.min(shown + step, total));
    model.addAttribute("step", step);
    model.addAttribute("titulo", "Mis Hechos");

    return "hechos/mis-hechos";
  }

  // Para editar el Hecho

  /**
   * Validamos existencia del Usuario actual,
   *    y ventana de 7 días desde fecha de Carga del Hecho
   *    Si falla => redirige con flash error (ver si lo queremos cambiar a esto)
   *    Sino => renderiza editar.html (reutilizamos subir-hecho con click-to-edit)
   */
  @GetMapping("/{idHecho}/editar")
  @PreAuthorize("hasRole('CONTRIBUYENTE')")
  public String editar(@PathVariable Long idHecho, HttpSession session, RedirectAttributes ra, Model model, Authentication authentication) {
    // Usuario actual (por Jwt - OAuth2 - el que sea)
    UsuarioDTO usuario = usuarioCuentaService.obtenerUsuarioActual(session, authentication);
    if (usuario == null || usuario.getId() == null) {
      ra.addFlashAttribute("error", "No se pudo identificar al usuario actual");
      return "redirect:/main-gral";
    }

    HechoDTOOutput hecho = agregador.getHecho(idHecho);
    if (hecho == null) {
      ra.addFlashAttribute("error", "El hecho no existe.");
      return "redirect:/hechos/mis-hechos";
    }
    //Enviamos datos a la vista
    model.addAttribute("hecho", hecho);
    model.addAttribute("titulo", "Editar Hecho");
    return "hechos/editar";
  }

  @PostMapping("/{idHecho}/editar")
  @PreAuthorize("hasRole('CONTRIBUYENTE')")
  public String enviarEdicion(@PathVariable Long idHecho, @ModelAttribute("hecho") HechoDTOInput hechoDtoInput, HttpSession session, RedirectAttributes ra, Authentication authentication) {
    // Usuario actual
    UsuarioDTO usuario = usuarioCuentaService.obtenerUsuarioActual(session, authentication);
    if (usuario == null || usuario.getId() == null) {
      ra.addFlashAttribute("error", "No se pudo identificar al usuario actual.");
      return "redirect:/iniciar-sesion";
    }

    Long userId = usuario.getId();

    // Creamos la solicitud
    SolicitudEdicionDTO solicitud = new SolicitudEdicionDTO();
    solicitud.setIdHecho(idHecho);
    solicitud.setEstado("PENDIENTE");
    solicitud.setFechaSolicitud(LocalDateTime.now());
    solicitud.setPropuesta(hechoDtoInput);
    solicitud.setIdusuario(userId);
    this.fuenteDinamica.solicitarModificacion(solicitud, this.urlFuenteDinamica);

    ra.addFlashAttribute("success", "Tu edición fue enviada a revisión. Aparecerá nuevamente cuando sea aprobada.");

    return "redirect:/hechos/mis-hechos";
  }

  //Solicitudes de Eliminación
  @PostMapping("/{idHecho}/solicitud-eliminacion")
  @PreAuthorize("hasRole('CONTRIBUYENTE')")
  public ResponseEntity<?> crearSolicitudEliminacion(@PathVariable Long idHecho, @RequestParam String justificacion, HttpSession session) {
    String accessToken = (String) session.getAttribute("accessToken");
    Long userId = null;
    if (accessToken != null) {
      userId = jwtUtil.getId(accessToken);
    }

    if (justificacion == null || justificacion.trim().length() < 500) {
      return ResponseEntity.badRequest().body("La justificación debe tener al menos 500 caracteres");
    }

    SolicitudEliminacionDTO solicitud = new SolicitudEliminacionDTO();
    solicitud.setIdHecho(idHecho);
    solicitud.setIdusuario(userId);
    solicitud.setJustificacion(justificacion);
    solicitud.setEstado("PENDIENTE");
    solicitud.setFechaSolicitud(LocalDateTime.now());
    this.agregador.crearSolicitud(solicitud);
    return ResponseEntity.ok().build(); //200 en caso de éxito!
  }


  //-------------------------------------

  record HechoDto(Long id, String titulo, String fecha, String categoria, String ubicacion) {
  }

  @GetMapping("/hechos/nav-hechos")
  public String navHechos(@RequestParam(defaultValue = "0") int page, Model model, WebClient backend, HttpSession session) {
    var spec = backend.get().uri(uri -> uri.path("/api/hechos")
        .queryParam("page", page)
        .queryParam("size", 20)
        .build());

    var token = (String) session.getAttribute("AUTH_TOKEN");
    if (token != null) spec = spec.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);

    var hechos = spec.retrieve().bodyToFlux(HechoDto.class).collectList().block();
    model.addAttribute("hechos", hechos);
    model.addAttribute("page", page);
    return "hechos/nav-hechos";
  }

}
