package ar.edu.utn.frba.dds.servicioFuenteEstatica.services;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.exceptions.ArchivoVacioException;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.exceptions.ExcepcionIO;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.exceptions.HechoNoEncontrado;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.exceptions.HechoYaEstaEliminado;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.exceptions.UsuarioNoEncontrado;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.exceptions.UsuarioSinPermiso;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.daos.IHechosDAO;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.HechoDTOEstatica;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.HechoValueObject;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.roles.PermisoSubidaArchivo;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories.implReal.IHechoRepositoryJPA;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories.implReal.IUsuarioRepositoryJPA;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HechoService implements IHechoService {
  private final IHechosDAO hechosDAO;
  private final IHechoRepositoryJPA hechoRepository;
  private final IUsuarioRepositoryJPA usuarioRepository;

  public HechoService(IHechosDAO hechosDAO, IHechoRepositoryJPA hechoRepository, IUsuarioRepositoryJPA usuarioRepository) {
    this.hechosDAO = hechosDAO;
    this.hechoRepository = hechoRepository;
    this.usuarioRepository = usuarioRepository;
  }

  //Importar desde CSV a Set<Hecho>
  public Set<HechoDTOEstatica> importarDesdeCSV(MultipartFile archivo, Long idUsuario) {
    if(archivo.isEmpty()){
      throw new ArchivoVacioException("El archivo subido está vacío. Esto puede ser porque se ha subido incorrectamente o el archivo está corrupto");
    }

    Usuario usuario = this.usuarioRepository.findById(idUsuario).orElse(null);
    if(usuario == null){
      throw new UsuarioNoEncontrado("El usuario no existe");
    }
    if(!usuario.tienePermisoDe(new PermisoSubidaArchivo(""))){
      throw new UsuarioSinPermiso("El usuario especificado no tiene permisos para subir archivos con hechos");
    }
    Set<HechoValueObject> hechosVO;
    try {
       hechosVO = hechosDAO.getAll(archivo.getInputStream());
    } catch (IOException e) {
      throw new ExcepcionIO(e.getMessage());
    }


    Set<Hecho> hechos = hechosVO.stream()
        .map(Hecho::new)
        .collect(Collectors.toSet());

    hechos.forEach(hecho -> hecho.setUsuario(usuario));

    hechoRepository.saveAll(hechos);
    return hechos.stream().map(this::toHechoDTOEstatica).collect(Collectors.toSet());
  }

  public Set<HechoDTOEstatica> obtenerTodos() {
    return hechoRepository.findAll().stream()
        .map(this::toHechoDTOEstatica)
        .collect(Collectors.toSet());
  }

  public HechoDTOEstatica marcarEliminadoHecho(Long idHecho) {
    Hecho hecho = this.hechoRepository.findHechoById(idHecho);
    if(hecho == null){
      throw new HechoNoEncontrado("El hecho no existe");
    }
    if(hecho.getEliminado()) {
      throw new HechoYaEstaEliminado("El hecho de id: " + idHecho + " ya está eliminado!");
    }
    hecho.setEliminado(true);
    this.hechoRepository.save(hecho);
    return this.toHechoDTOEstatica(hecho);
  }

  public void eliminarTodo() {
    hechoRepository.deleteAll();
  }

  public HechoDTOEstatica toHechoDTOEstatica(Hecho hecho) {
    HechoDTOEstatica dto = new HechoDTOEstatica();
    dto.setId(hecho.getId());
    dto.setTitulo(hecho.getTitulo());
    dto.setUbicacion(hecho.getUbicacion());
    dto.setDescripcion(hecho.getDescripcion());
    dto.setFechaAcontecimiento(hecho.getFechaAcontecimiento());
    dto.setFechaCarga(hecho.getFechaCarga());
    dto.setCategoria(hecho.getCategoria());
    dto.setIdUsuario(hecho.getIdUsuario());
    return dto;
  }
}
