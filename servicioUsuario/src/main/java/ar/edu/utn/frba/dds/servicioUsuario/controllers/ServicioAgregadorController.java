package ar.edu.utn.frba.dds.servicioUsuario.controllers;

import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ColeccionDTOInput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.ColeccionDTOOutput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.HechoDTOOutput;
import ar.edu.utn.frba.dds.servicioUsuario.models.dtos.SolicitudEliminacionDTO;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agregador")
public class ServicioAgregadorController {
  @GetMapping("/hechos")
  public List<HechoDTOOutput> findAllHechos(FiltroDTO filtro) {

  }

  @GetMapping("/colecciones/{coleccionId}/hechos")
  public List<HechoDTOOutput> findHechosByColeccionId(@PathVariable UUID coleccionId, FiltroDTO filtro) {}

  @GetMapping("/solicitudes")
  public List<SolicitudEliminacionDTO> findAllSolicitudes() {

  }

  @PostMapping("/solicitudes")
  public SolicitudEliminacionDTO crearSolicitud(SolicitudEliminacionDTO solicitudEliminacionDTO) {

  }

  @DeleteMapping("/solicitudes/{idSolicitud}")
  public SolicitudEliminacionDTO cancelarSolicitud(@PathVariable Long idSolicitud) {

  }

  @PutMapping("/solicitudes/{idSolicitud}")
  public SolicitudEliminacionDTO aceptarSolicitud(@PathVariable Long idSolicitud) {

  }

  @PutMapping("/colecciones/{idColeccion}")
  public ColeccionDTOOutput modificarColeccion(ColeccionDTOInput coleccionDTOInput, @PathVariable UUID coleccionId) {

  }

  @DeleteMapping("/colecciones/{idColeccion}")
  public ColeccionDTOOutput eliminarColeccion(@PathVariable UUID idColeccion){

  }

  @PostMapping("/colecciones")
  public ColeccionDTOOutput crearColeccion(ColeccionDTOInput coleccion){

  }

  @GetMapping("/hechos/{idHecho}")
  public HechoDTOOutput getHecho(@PathVariable  Long idHecho) {

  }

  @GetMapping("/colecciones")
  public List<ColeccionDTOOutput> findColecciones() {

  }

}
