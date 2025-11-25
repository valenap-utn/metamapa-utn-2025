package ar.edu.utn.frba.dds.servicioFuenteDinamica.services;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.HechoDTODinamica;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.RevisionDTO;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Hecho;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface IHechoServicio {
  Hecho crearHecho(HechoDTODinamica input, MultipartFile contenidoMultimedia);

  List<Hecho> obtenerHechosPublicos(Boolean pendientes, Long idUsuario);


  Hecho revisarHecho(Long id, RevisionDTO revisionDTO);

  Hecho marcarComoEliminado(Long id);

  Hecho findHechoById(Long idHecho);

  List<Categoria> findAllCategorias();
}
