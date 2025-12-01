package ar.edu.utn.frba.dds.servicioFuenteProxy.services.impl;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.IAPIClient;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.output.HechoMapper;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.output.HechoOutputDTO;
import ar.edu.utn.frba.dds.servicioFuenteProxy.exceptions.HechoYaEliminado;
import ar.edu.utn.frba.dds.servicioFuenteProxy.model.entities.IDAPI;
import ar.edu.utn.frba.dds.servicioFuenteProxy.model.repositories.IIDSEliminadosRepositoryJPA;
import ar.edu.utn.frba.dds.servicioFuenteProxy.services.IHechoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// para ser usado localmente y entregados hacia el Controller, que luego los entregara al Servicio Agregador


@Service
public class HechoService implements IHechoService {

    private final List<IAPIClient> apiClients;
    private final HechoMapper hechoMapper;
    private final IIDSEliminadosRepositoryJPA iidsEliminadosRepositoryJPA;

    public void marcarComoEliminado(Long id, String clientNombre) {
        IDAPI idapi = iidsEliminadosRepositoryJPA.findById(id).orElse(null);
        if(idapi != null) {
            throw new HechoYaEliminado("El Hecho ya estaba eliminado");

        }

        IDAPI hechoAEliminar= new IDAPI();
        hechoAEliminar.setIdEliminado(id);
        hechoAEliminar.setNombreAPI(clientNombre);
        this.iidsEliminadosRepositoryJPA.save(hechoAEliminar);
    }

    public HechoService(List<IAPIClient> apiClients, HechoMapper hechoMapper, IIDSEliminadosRepositoryJPA iidsEliminadosRepositoryJPA) {
        this.apiClients = apiClients;
        this.hechoMapper = hechoMapper;
        this.iidsEliminadosRepositoryJPA = iidsEliminadosRepositoryJPA;
    }

    @Override
    public List<HechoOutputDTO> getHechosExternos(
            String categoria,
            Double latitud,
            Double longitud,
            LocalDateTime fechaReporteDesde,
            LocalDateTime fechaReporteHasta,
            LocalDateTime fechaAcontecimientoDesde,
            LocalDateTime fechaAcontecimientoHasta

    ) {
        return apiClients
                .stream()
                .flatMap(client -> client.getAllHechosExternos().stream()
                        .map(dto -> hechoMapper.toOutputDTO(dto, client.nombre())))
                .filter(this::noEstaEliminado)
                .filter(hecho -> categoria == null || categoria.isEmpty() || hecho.getCategoria().equalsIgnoreCase(categoria))
                .filter(hecho -> latitud == null || hecho.getLatitud().equals(latitud))
                .filter(hecho -> longitud == null || hecho.getLongitud().equals(longitud))
                .filter(hecho -> fechaReporteDesde == null || hecho.getFechaCarga().isAfter(fechaReporteDesde))
                .filter(hecho -> fechaReporteHasta == null || hecho.getFechaCarga().isBefore(fechaReporteHasta))
                .filter(hecho -> fechaAcontecimientoDesde == null || hecho.getFecha().isAfter(fechaAcontecimientoDesde))
                .filter(hecho -> fechaAcontecimientoHasta == null || hecho.getFecha().isBefore(fechaAcontecimientoHasta))
                .toList();
    }

    private boolean noEstaEliminado(HechoOutputDTO hecho) {
        return this.iidsEliminadosRepositoryJPA.findByIdEliminadoAndNombreAPI(hecho.getId(), hecho.getFuente()).isEmpty();
    }
}


