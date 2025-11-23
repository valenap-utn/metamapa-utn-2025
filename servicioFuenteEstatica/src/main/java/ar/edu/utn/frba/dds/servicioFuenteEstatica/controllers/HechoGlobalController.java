package ar.edu.utn.frba.dds.servicioFuenteEstatica.controllers;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.exceptions.UsuarioNoEncontrado;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.ConjuntoHechoDTOEstatica;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.HechoDTOEstatica;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.UsuarioDTO;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.roles.Permiso;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.roles.Rol;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories.implReal.IUsuarioRepositoryJPA;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.services.IHechoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/hechos")
@Slf4j
public class HechoGlobalController {

  /** Manejo con HechoService, dado que el Controller no debe conocer directamente la persistencia,
    sino la lógica de negocio que usa.
    Si el Controller dependiera del Repository, tendría que meter lógica del Service acá y romperia
    con el principio de responsabilidad única.
    => Si mañana cambiara de un repo en memoria a una base de datos real
                                                                   => mi controller ni se entera */
  private final IHechoService hechoService;
  private final IUsuarioRepositoryJPA usuarioRepository;

  @Autowired
  public HechoGlobalController(IHechoService hechoService, IUsuarioRepositoryJPA usuarioRepository) {
    this.hechoService = hechoService;
    this.usuarioRepository = usuarioRepository;
  }

  /** Devuelven un ResponseEntity
  * Y qué es el ResponseEntity? Qué hace?
  *   Lo que hace ResponseEntity es en envolver la rta. y darle un HTTP status code claro
  * Y para qué queremos eso?
  *   Queremos eso dado que es mas RESTful y flexible
  * */

  @DeleteMapping("/{id}")
  public ResponseEntity<HechoDTOEstatica> eliminarHecho(@PathVariable Long id ) {
    HechoDTOEstatica hecho = this.hechoService.marcarEliminadoHecho(id);
    return ResponseEntity.ok(hecho);
  }

  /**
   * @@RequestPart está diseñado específicamente para gestionar
   * la carga de archivos (como MultipartFile) y otros datos en
   * solicitudes multipart.
   */
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> importar(@RequestPart("archivo") MultipartFile archivo, @RequestPart("usuario") String usuarioIdStr) {

    log.info("[HechoGlobalController] multipart recibido. usuario='{}' archivo='{}'", usuarioIdStr, archivo.getOriginalFilename());

    Long idUsuario = Long.parseLong(usuarioIdStr);

    Usuario usuario = usuarioRepository.findById(idUsuario)
        .orElseThrow(() -> new UsuarioNoEncontrado("Usuario no encontrado: " + idUsuario));

    //Mapeamos a DTO para el servicio
    UsuarioDTO usuarioDTO = new UsuarioDTO();
    usuarioDTO.setId(usuario.getId());
    usuarioDTO.setNombre(usuario.getNombre());
    usuarioDTO.setApellido(usuario.getApellido());
    usuarioDTO.setRol(Rol.ADMINISTRADOR);
    usuarioDTO.setPermisos(List.of(Permiso.SUBIDAARCHIVO));

    Set<HechoDTOEstatica> hechos = hechoService.importarDesdeCSV(archivo, usuarioDTO);

    return ResponseEntity.ok("Importados: " + hechos.size());
  }

  @GetMapping
  public ResponseEntity<ConjuntoHechoDTOEstatica> getAll() {
    Set<HechoDTOEstatica> todosLosHechos = hechoService.obtenerTodos();
    ConjuntoHechoDTOEstatica conjuntoHechos = new ConjuntoHechoDTOEstatica();
    conjuntoHechos.setHechos(todosLosHechos.stream().toList());
    return ResponseEntity.ok(conjuntoHechos);
  }


  @DeleteMapping
  public ResponseEntity<Void> eliminarTodo() {
    hechoService.eliminarTodo();
    return ResponseEntity.noContent().build();
  }

}

