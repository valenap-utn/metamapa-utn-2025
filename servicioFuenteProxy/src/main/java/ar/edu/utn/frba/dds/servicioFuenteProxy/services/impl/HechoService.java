package ar.edu.utn.frba.dds.servicioFuenteProxy.services.impl;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.IAPIClient;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.input.HechoInputDTO;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.input.MetaMapaResponse;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.output.HechoMapper;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.output.HechoOutputDTO;
import ar.edu.utn.frba.dds.servicioFuenteProxy.services.IHechoService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// para ser usado localmente y entregados hacia el Controller, que luego los entregara al Servicio Agregador


@Service
public class HechoService implements IHechoService {

    private final List<IAPIClient> apiClients;
    private final HechoMapper hechoMapper;

    private final List<Long> hechosEliminados = new ArrayList<>();

    public void marcarComoEliminado(Long id) {
        if(!hechosEliminados.contains(id)) {
            hechosEliminados.add(id);
        }
    }

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
                .filter( hecho ->!hechosEliminados.contains(hecho.getId()))
                .filter(hecho -> categoria == null || hecho.getCategoria().equalsIgnoreCase(categoria))
                .filter(hecho -> latitud == null || hecho.getLatitud().equals(latitud))
                .filter(hecho -> longitud == null || hecho.getLongitud().equals(longitud))
                .filter(hecho -> fechaReporteDesde == null || hecho.getFechaCarga().isAfter(fechaReporteDesde))
                .filter(hecho -> fechaReporteHasta == null || hecho.getFechaCarga().isBefore(fechaReporteHasta))
                .filter(hecho -> fechaAcontecimientoDesde == null || hecho.getFecha().isAfter(fechaAcontecimientoDesde))
                .filter(hecho -> fechaAcontecimientoHasta == null || hecho.getFecha().isBefore(fechaAcontecimientoHasta))
                .toList();
    }
}
//TODO: encontrar una forma mas generica de recorrer los hechos segun criterio. Por ejemplo, que los filtros se reciban como una lista, y luego abstraer la parte que coincide: .filter(hecho -> hecho.algo)


