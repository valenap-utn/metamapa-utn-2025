package ar.edu.utn.frba.dds.servicioFuenteProxy.services.impl;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.IAPIClient;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.output.HechoMapper;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.output.HechoOutputDTO;
import ar.edu.utn.frba.dds.servicioFuenteProxy.services.IHechoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// para ser usado localmente y entregados hacia el Controller, que luego los entregara al Servicio Agregador


@Service
public class HechoService implements IHechoService {

    private final List<IAPIClient> apiClients;
    private final HechoMapper hechoMapper;


    public HechoService(List<IAPIClient> apiClients, HechoMapper hechoMapper) {
        this.apiClients = apiClients;
        this.hechoMapper = hechoMapper;
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
                .filter( hecho -> hecho.getCategoria().equalsIgnoreCase(categoria))
                .filter(hecho -> hecho.getLatitud().equals(latitud))
                .filter(hecho -> hecho.getLongitud().equals(longitud))
                .filter(hecho -> hecho.getFechaCarga().isAfter(fechaReporteDesde))
                .filter(hecho -> hecho.getFechaCarga().isBefore(fechaReporteHasta))
                .filter(hecho -> hecho.getFecha().isAfter(fechaAcontecimientoDesde))
                .filter(hecho -> hecho.getFecha().isBefore(fechaAcontecimientoHasta))
                .toList();
    }
}


