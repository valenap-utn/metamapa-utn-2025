package ar.edu.utn.frba.dds.clienteInterfaz.clients;

import ar.edu.utn.frba.dds.clienteInterfaz.dtos.ConjuntoEstadisticasDTO;
import ar.edu.utn.frba.dds.clienteInterfaz.services.internal.WebApiCallerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServicioDeEstadistica {
    private final WebApiCallerService webApiCallerService;
    private final String baseUrl;

    public ServicioDeEstadistica(WebApiCallerService webApiCallerService, @Value("${api.servicioUsuarios.url}") String baseUrl) {
        this.webApiCallerService = webApiCallerService;
        this.baseUrl = baseUrl;
    }

    public ConjuntoEstadisticasDTO obtenerEstadisticas() {
        String url = baseUrl + "/api/estadisticas";
        return webApiCallerService.get(url, ConjuntoEstadisticasDTO.class);
    }

    public byte[] exportarCSV() {
        String url = baseUrl + "/api/estadisticas/csv";
        return webApiCallerService.get(url, byte[].class);
    }
}

