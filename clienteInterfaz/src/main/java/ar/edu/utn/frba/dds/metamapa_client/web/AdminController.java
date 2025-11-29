package ar.edu.utn.frba.dds.metamapa_client.web;

import ar.edu.utn.frba.dds.metamapa_client.clients.IFuenteDinamica;
import ar.edu.utn.frba.dds.metamapa_client.clients.IFuenteEstatica;
import ar.edu.utn.frba.dds.metamapa_client.clients.IServicioAgregador;
import ar.edu.utn.frba.dds.metamapa_client.clients.ServicioDeEstadistica;
import ar.edu.utn.frba.dds.metamapa_client.dtos.*;
import ar.edu.utn.frba.dds.metamapa_client.services.ConexionServicioUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {
  //  private final ClientSeader agregador;
  private final IServicioAgregador agregador;
  private final DefaultErrorAttributes defaultErrorAttributes;
  private final IFuenteEstatica fuenteEstatica;
  private final HttpSession session;
  private final ConexionServicioUser conexionServicioUser;
  private final IFuenteDinamica fuenteDinamica;
  private final ServicioDeEstadistica servicioDeEstadistica;
  @Value("${api.servicioFuenteDinamica.url}")
  private String urlFuenteDinamica;

  public AdminController(IServicioAgregador agregador, DefaultErrorAttributes defaultErrorAttributes, IFuenteEstatica fuenteEstatica, HttpSession session, ConexionServicioUser conexionServicioUser, IFuenteDinamica fuenteDinamica, ServicioDeEstadistica servicioDeEstadistica) {
    this.agregador = agregador;
    this.defaultErrorAttributes = defaultErrorAttributes;
    this.fuenteEstatica = fuenteEstatica;
    this.session = session;
    this.conexionServicioUser = conexionServicioUser;
    this.fuenteDinamica = fuenteDinamica;
    this.servicioDeEstadistica = servicioDeEstadistica;
  }


  @GetMapping
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public String dashboard(Model model) {
    long totalHechos = agregador.getCantidadHechos();

    //Por el momento al total de fuentes lo hacemos así (después vemos si lo terminamos poniendo o no)
    long totalFuentes = this.agregador.getCantidadFuentes();

    long totalSolEliminacion = agregador.findAllSolicitudes().size();

    Map<String, Long> metrics = new HashMap<>();
    metrics.put("totalHechos", totalHechos);
    metrics.put("totalFuentes", totalFuentes);
    metrics.put("solicitudes", totalSolEliminacion);

    model.addAttribute("metrics", metrics);
    model.addAttribute("titulo", "Panel de Administrador | MetaMapa");

    return "admin";
  }

  @GetMapping("/crear-coleccion")
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public String crearColeccion(Model model) {
    List<String> categorias = this.agregador.findAllCategorias();
    model.addAttribute("categorias", categorias);
    model.addAttribute("coleccion", new ColeccionDTOInput());
    model.addAttribute("titulo", "Crear Coleccion");

    return "admins/crear-coleccion";
  }

  @PostMapping("/crear-coleccion")
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public String crearColeccionPost(@ModelAttribute("coleccion") ColeccionDTOInput coleccion, Model model, RedirectAttributes ra) {
    ColeccionDTOOutput coleccionDTOOutput = this.agregador.crearColeccion(coleccion);
    ra.addFlashAttribute("success", "Colección creada correctamente");

    return "redirect:/admin";
  }

  // ---------- COLECCIONES ----------

  @GetMapping("/modificar-coleccion/{id}/editar")
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public String modificarColeccion(@PathVariable UUID id, Model model, RedirectAttributes ra) {
    //Traemos la Coleccion
    ColeccionDTOOutput out = this.agregador.revisarColeccion(id);
    if (out == null) {
      ra.addFlashAttribute("error", "La colección no existe.");
      return "redirect:/colecciones";
    }

    //Mapeamos el Output
    ColeccionDTOInput in = new ColeccionDTOInput();
    in.setTitulo(out.getTitulo());
    in.setDescripcion(out.getDescripcion());
    in.setFuentes(out.getFuentes() != null ? out.getFuentes() : List.of());
    in.setAlgoritmo(out.getAlgoritmoDeConsenso());
    in.setCriterios(out.getCriterios() != null ? out.getCriterios() : List.of());

    List<String> categorias = this.agregador.findAllCategorias();
    model.addAttribute("categorias", categorias);
    model.addAttribute("coleccionId", out.getId());
    model.addAttribute("coleccion", in);
    model.addAttribute("titulo", "Modificar Coleccion");

    return "admins/modificar-coleccion";
  }

  @PostMapping("/modificar-coleccion/{id}/editar")
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public String modificarColeccionPost(@PathVariable UUID id, @ModelAttribute("coleccion") ColeccionDTOInput in, Model model, RedirectAttributes ra) {

    try {
      ColeccionDTOOutput actualizado = this.agregador.modificarColeccion(in, id);
      if (actualizado == null) {
        model.addAttribute("error", "No se pudo actulizar la colección.");
        model.addAttribute("coleccionId", id);
        model.addAttribute("titulo", "Modificar Coleccion");
        return "redirect:/colecciones";
      }
      ra.addFlashAttribute("success", "Colección actulizada correctamente.");
      return "redirect:/colecciones";
    } catch (WebClientResponseException e) {
      // Muestra el cuerpo de error del backend
      String body = e.getResponseBodyAsString();
      model.addAttribute("error", "Error del backend: " + e.getRawStatusCode() + " " + body);
      model.addAttribute("coleccionId", id);
      model.addAttribute("titulo", "Modificar Colección");
      return "redirect:/colecciones";
    } catch (Exception e) {
      model.addAttribute("error", "Error inesperado: " + e.getMessage());
      model.addAttribute("coleccionId", id);
      model.addAttribute("titulo", "Editar Colección");
      return "redirect:/colecciones";
    }
  }

  @GetMapping("/colecciones")
  public String listarColecciones(Model model) {
    var items = agregador.findColecciones();        // <- trae las colecciones del ClientSeader
    model.addAttribute("colecciones", items);       // <- nombre que usa tu template
    model.addAttribute("titulo", "Colecciones");
    return "colecciones";                           // <- si tu archivo es templates/colecciones.html
  }

  @PostMapping("/eliminar-coleccion/{id}/eliminar")
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public String eliminarColeccion(@PathVariable UUID id, RedirectAttributes ra) {
    var eliminada = this.agregador.eliminarColeccion(id);
    if (eliminada == null) {
      ra.addFlashAttribute("error", "La colección no existe o ya fue eliminada.");
    } else {
      ra.addFlashAttribute("success", "Colección eliminada correctamente.");
    }
    return "redirect:/colecciones";
  }

  // ---------- CSV ----------

  @GetMapping("/importar-csv")
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public String importarCsv() {
    return "admins/importar-csv";
  }

  @PostMapping("/importar-csv")
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public String importarCsvPost(@RequestParam("file") MultipartFile file, RedirectAttributes ra) {
    log.info("POST /admin/importar-csv recibido");
    try {
      String mensaje = fuenteEstatica.subirHechosCSV(file);
      ra.addFlashAttribute("success", mensaje);
    } catch (Exception e) {
      log.error("Error importando CSV", e);
      ra.addFlashAttribute("error", "Error al importar CSV: " + e.getMessage());
    }
    return "redirect:/admin";
  }

  // ---------- HECHOS ----------

  //Para gestionar Nuevos Hechos (Aprobar o Rechazar)
  @GetMapping("/gest-nuevosHechos")
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public String gestNuevosHechos(Model model, @RequestParam(value = "estado", required = false, defaultValue = "TODAS") String estado, @RequestParam(value = "q", required = false, defaultValue = "") String q, @RequestParam(value = "nroPagina", required = false, defaultValue = "0") Integer nroPagina) {
    List<HechoDTOOutput> hechos = this.fuenteDinamica.findHechosNuevos(urlFuenteDinamica,estado, nroPagina);

    hechos.forEach(h -> log.info("Hecho {} estado='{}'", h.getId(), h.getEstado()));

    long total = hechos.size();
    long pendientes = hechos.stream().filter(h -> "EN_REVISION".equals(h.getEstado())).count();
    long aprobados = hechos.stream().filter(h -> "ACEPTADA".equals(h.getEstado())).count();
    long rechazados = hechos.stream().filter(h -> "RECHAZADA".equals(h.getEstado())).count();

    //Filtro por Estado
    Stream<HechoDTOOutput> filtrados = hechos.stream();
    if (!"TODAS".equalsIgnoreCase(estado)) {
      filtrados = filtrados.filter(h -> estado.equalsIgnoreCase(String.valueOf(h.getEstado())));
    }

    //Filtro por búsqueda
    String qNorm = q.trim().toLowerCase();
    if (!qNorm.isEmpty()) {
      filtrados = filtrados.filter(h ->
          (h.getTitulo() != null && h.getTitulo().toLowerCase().contains(qNorm)) ||
              (h.getDescripcion() != null && h.getDescripcion().toLowerCase().contains(qNorm))
      );
    }

    List<HechoDTOOutput> filtradosList = filtrados.toList();

    model.addAttribute("hechos", filtradosList);
    model.addAttribute("estado", estado);
    model.addAttribute("q", q);
    model.addAttribute("nroPagina", nroPagina);
    model.addAttribute("countTodas", total);
    model.addAttribute("countPend", pendientes);
    model.addAttribute("countApro", aprobados);
    model.addAttribute("countRech", rechazados);

    model.addAttribute("titulo", "Gestionar Nuevos Hechos");
    return "admins/gest-nuevosHechos";
  }

  @PostMapping("/gest-nuevosHechos/{id}/aprobar")
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public String aprobarHecho(@PathVariable("id") Long id, RedirectAttributes ra) {
    this.revisarHecho(id, "ACEPTADA", "El hecho ha sido aceptado por el administrador.");
    ra.addFlashAttribute("success", "Hecho aprobado correctamente.");
    return "redirect:/admin/gest-nuevosHechos";
  }

  private void revisarHecho(Long id, String estado, String comentario) {
    RevisionDTO revisionDTO = new RevisionDTO();
    revisionDTO.setEstado(estado);
    revisionDTO.setComentario(comentario);
    this.fuenteDinamica.revisarHecho(id, revisionDTO, this.urlFuenteDinamica);
  }

  @PostMapping("/gest-nuevosHechos/{id}/rechazar")
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public String rechazarHecho(@PathVariable Long id, RedirectAttributes ra) {
    this.revisarHecho(id, "RECHAZADA", "El hecho ha sido rechazado por el administrador.");
    ra.addFlashAttribute("success", "Hecho rechazado correctamente.");
    return "redirect:/admin/gest-nuevosHechos";
  }

  //Para ver detalle del hecho (desde fuente dinámica)
  @GetMapping("/gest-nuevosHechos/{id}")
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public String verHechoEnModeracion(@PathVariable Long id, Model model) {
    HechoDTOOutput hecho = this.fuenteDinamica.getHecho(id, urlFuenteDinamica);
    model.addAttribute("hecho", hecho);
    model.addAttribute("titulo", "Detalle del hecho en moderación");
    return "hechos/hecho-completo";
  }

  //Para solicitudes de Edición
  @GetMapping("/gest-solEdicion")
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public String solicitudesEdicion(Model model, @RequestParam(value = "estado", required = false, defaultValue = "EN_REVISION") String estado, @RequestParam(value = "q", required = false, defaultValue = "") String q) {
    List<SolicitudEdicionDTO> solicitudes = this.fuenteDinamica.findAllSolicitudesEdicion(this.urlFuenteDinamica);

    List<Map<String, Object>> vms = solicitudes.stream()
        .filter(h -> "TODAS".equalsIgnoreCase(estado) || estado.equalsIgnoreCase(h.getEstado()))
        .map(h -> {
          HechoDTOOutput original = this.fuenteDinamica.getHecho(h.getIdHecho(), this.urlFuenteDinamica);
          String nombreUsuario = original.getUsuario() != null ? original.getUsuario().getEmail() : "Anónimo";
          return Map.of("sol", h, "orig", original, "nombreUsuario", nombreUsuario);
        })
        .filter(vm -> {
          String qNorm = q.trim().toLowerCase();
          if (qNorm.isEmpty()) {
            return true;
          }
          HechoDTOOutput otpDTO = (HechoDTOOutput) vm.get("orig");
          SolicitudEdicionDTO solEdDTO = (SolicitudEdicionDTO) vm.get("sol");
          return (otpDTO != null && otpDTO.getTitulo() != null && otpDTO.getTitulo().toLowerCase().contains(qNorm))
              || (solEdDTO.getPropuesta() != null && solEdDTO.getPropuesta().getTitulo() != null && solEdDTO.getPropuesta().getTitulo().toLowerCase().contains(qNorm));
        }).toList();

    long total = solicitudes.size();
    long pendientes = solicitudes.stream().filter(h -> "EN_REVISION".equals(h.getEstado())).count();
    long aceptadas = solicitudes.stream().filter(h -> "ACEPTADA".equals(h.getEstado())).count();
    long canceladas = solicitudes.stream().filter(h -> "RECHAZADA".equals(h.getEstado())).count();

    model.addAttribute("items", vms);
    model.addAttribute("estado", estado);
    model.addAttribute("q", q);

    model.addAttribute("countTodas", total);
    model.addAttribute("countPend", pendientes);
    model.addAttribute("countAcep", aceptadas);
    model.addAttribute("countCancel", canceladas);

    model.addAttribute("titulo", "Solicitudes de Edición");
    return "admins/gest-solEdicion";
  }

  @PostMapping("/gest-solEdicion/{idSolicitud}/aprobar")
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public String aprobarSolicitudEdicion(@PathVariable("idSolicitud") Long idSolicitud) {
    RevisionDTO revision = new RevisionDTO();
    revision.setEstado("ACEPTADA");
    revision.setComentario("Solicitud de edición aprobada por el administrador");

    this.fuenteDinamica.procesarSolicitudEdicion(idSolicitud, this.urlFuenteDinamica, revision);
    return "redirect:/admin/gest-solEdicion"; //204 en caso de exito !
  }

  @PostMapping("/gest-solEdicion/{idSolicitud}/rechazar")
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public String rechazarSolicitudEdicion(@PathVariable("idSolicitud") Long idSolicitud) {
    RevisionDTO revision = new RevisionDTO();
    revision.setEstado("RECHAZADA");
    revision.setComentario("Edición rechazada por el administrador");

    this.fuenteDinamica.procesarSolicitudEdicion(idSolicitud, this.urlFuenteDinamica, revision);
    return "redirect:/admin/gest-solEdicion"; //204 en caso de exito !
  }


  //Para solicitudes de eliminación
  //View-Model simple para la vista
  public static class SolicitudVM {
    public final SolicitudEliminacionDTO solicitud;
    public final String tituloHecho;
    public final String urlHecho;

    public SolicitudVM(SolicitudEliminacionDTO solicitud, String tituloHecho, String urlHecho) {
      this.solicitud = solicitud;
      this.tituloHecho = tituloHecho;
      this.urlHecho = urlHecho;
    }
  }

  //Listar con filtros y  contadores
  @GetMapping("/gest-solEliminacion")
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public String solicitudes(Model model, @RequestParam(value = "estado", required = false, defaultValue = "TODAS") String estado,
                            @RequestParam(value = "q", required = false, defaultValue = "") String q) {
    List<SolicitudEliminacionDTO> solicitudes = this.agregador.findAllSolicitudes();

    //Enriquecemos con titulo y url del Hecho
    List<SolicitudVM> solicitudesVM = solicitudes.stream().map(s -> {
      var h = this.agregador.getHecho(s.getIdHecho());
      String titulo = (h != null && h.getTitulo() != null) ? h.getTitulo() : "-";
      String url = (h != null && h.getId() != null) ? "/hechos/" + h.getId() : "#";
      return new SolicitudVM(s, titulo, url);
    }).toList();

    //Contadores
    long total = solicitudesVM.size();
    long pendientes = solicitudesVM.stream().filter(h -> "PENDIENTE".equals(h.solicitud.getEstado())).count();
    long aceptadas = solicitudesVM.stream().filter(h -> "ACEPTADA".equals(h.solicitud.getEstado())).count();
    long canceladas = solicitudesVM.stream().filter(h -> "RECHAZADA".equals(h.solicitud.getEstado())).count();

    //Filtros (por estado y/o búsqueda)
    String qNorm = q.trim().toLowerCase();
    List<SolicitudVM> filtradas = solicitudesVM.stream()
        .filter(h -> "TODAS".equalsIgnoreCase(estado) || estado.equalsIgnoreCase(h.solicitud.getEstado()))
        .filter(h -> qNorm.isEmpty()
            || (h.solicitud.getJustificacion() != null && h.solicitud.getJustificacion().toLowerCase().contains(qNorm))
            || (h.tituloHecho != null && h.tituloHecho.toLowerCase().contains(qNorm)))
        .toList();

    model.addAttribute("solicitudes", filtradas);
    model.addAttribute("estado", estado);
    model.addAttribute("q", q);

    model.addAttribute("countTodas", total);
    model.addAttribute("countPend", pendientes);
    model.addAttribute("countAcep", aceptadas);
    model.addAttribute("countCancel", canceladas);

    model.addAttribute("titulo", "Solicitudes de Eliminacion");
    return "admins/gest-solEliminacion";
  }

  //Aceptar solicitud
  @PostMapping("/gest-solEliminacion/{id}/aceptar")
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public String aceptarSolicitud(@PathVariable("id") Long id) {
    RevisionDTO revision = new RevisionDTO();
    revision.setComentario("El administrador ha aceptado la solicitud");
    revision.setEstado("ACEPTADA");
    this.agregador.aceptarSolicitud(id, revision);
    return "redirect:/admin/gest-solEliminacion"; //204 en caso de exito !
  }



  //Rechazar solicitud
  @PostMapping("/gest-solEliminacion/{id}/cancelar")
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public String cancelarSolicitud(@PathVariable("id") Long id) {
    RevisionDTO revision = new RevisionDTO();
    revision.setComentario("El administrador ha rechazado la solicitud");
    revision.setEstado("RECHAZADA");
    this.agregador.cancelarSolicitud(id, revision);
    return "redirect:/admin/gest-solEliminacion";
  }

  @GetMapping("/dashboard-estadisticas")
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public String estadisticas() {
    return "admins/dashboard-estadisticas";
  }

  @GetMapping("/dashboard-estadisticas/data")
  @ResponseBody
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public ConjuntoEstadisticasDTO estadisticasData() {
    return servicioDeEstadistica.obtenerEstadisticas();
  }

  @GetMapping("/dashboard-estadisticas/exportar")
  @PreAuthorize("hasRole('ADMINISTRADOR')")
  public ResponseEntity<byte[]> exportarCsv() {
    byte[] csv = servicioDeEstadistica.exportarCSV();
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=estadisticas.csv")
        .contentType(MediaType.parseMediaType("text/csv"))
        .body(csv);
  }
}
