package ar.edu.utn.frba.dds.servicioFuenteEstatica.services;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.exceptions.ImportacionFallidaException;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.HechoValueObject;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.FuenteEstatica;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.mappers.HechoMapper;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories.ImportadorCSV;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HechoService {
  public Set<HechoValueObject> importarDesdeArchivo(MultipartFile archivo){
    try{
//      ImportadorCSV importador = new ImportadorCSV(archivo);
//      FuenteEstatica fuenteEstatica = new FuenteEstatica(importador);
//      return fuenteEstatica.obtenerHechos();
      Fuente fuente = new FuenteEstatica(new ImportadorCSV(archivo));
      Set<Hecho> hechos = fuente.obtenerHechos();

      return hechos.stream()
          .map(HechoMapper::toValueObject)
          .collect(Collectors.toSet());
    }catch(Exception e){
      throw new ImportacionFallidaException("Error al importar hechos desde el archivo",e);
    }
  }
}
