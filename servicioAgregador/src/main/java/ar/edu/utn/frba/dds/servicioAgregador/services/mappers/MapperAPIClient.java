package ar.edu.utn.frba.dds.servicioAgregador.services.mappers;

import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.HechoDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.HechoDTODinamica;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.HechoDTOEstatica;
import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.HechoDTOProxy;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho.HechoBuilder;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Ubicacion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;

public class MapperAPIClient {
  public Hecho toHechoFrom(HechoDTOEstatica hechoDTO) {
    return crearHechoBasico(hechoDTO)
            .origen(Origen.CARGAMANUAL)
            .build();
  }

  public Hecho toHechoFrom(HechoDTODinamica hechoDTODinamica) {
    return crearHechoConCategoria(hechoDTODinamica)
            .contenidoMultimedia(hechoDTODinamica.getContenidoMultimedia())
            .origen(Origen.PORCONTRIBUYENTE)
            .build();
  }

  public Hecho toHechoFrom(HechoDTOProxy hechoDTOProxy) {
    return crearHechoBasico(hechoDTOProxy)
            .categoria(hechoDTOProxy.getCategoria())
            .ubicacion(new Ubicacion(hechoDTOProxy.getLongitud().floatValue(), hechoDTOProxy.getLatitud().floatValue()))
            .origen(Origen.PROXY)
            .build();
  }

  private HechoBuilder crearHechoConCategoria(HechoDTO hechoDTO) {
    return this.crearHechoBasico(hechoDTO)
            .categoria(hechoDTO.getCategoria())
            .ubicacion(hechoDTO.getUbicacion());
  }


  private HechoBuilder crearHechoBasico(HechoDTO hechoDTO) {
    return Hecho.builder()
            .id(hechoDTO.getId())
            .titulo(hechoDTO.getTitulo())
            .descripcion(hechoDTO.getDescripcion())
            .fechaCarga(hechoDTO.getFechaCarga())
            .fechaAcontecimiento(hechoDTO.getFechaAcontecimiento());

  }
}
