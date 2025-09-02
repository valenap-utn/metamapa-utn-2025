package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Solicitud;
import ar.edu.utn.frba.dds.utils.Cache;
import ar.edu.utn.frba.dds.utils.CalculadorEstadisticas;
import ar.edu.utn.frba.dds.utils.ExportadorCSV;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class ServicioEstadisticas {

    private final ServicioAgregador agregador;
    private final CalculadorEstadisticas calculador;
    private final Cache cache;
    private final ExportadorCSV exportador;

    public ServicioEstadisticas(ServicioAgregador agregador) {
        this.agregador = agregador;
        this.calculador = new CalculadorEstadisticas();
        this.cache = new Cache();
        this.exportador = new ExportadorCSV();
    }

    public void recalcular() {
        List<Hecho> hechos = agregador.obtenerHechos();
        List<Solicitud> solicitudes = agregador.obtenerSolicitudes();

        Map<String, Object> resultados = new HashMap<>();
        resultados.put("ubicacionPorOrigen", calculador.ubicacionConMasHechosPorOrigen(hechos));
        resultados.put("categoriaTop", calculador.categoriaConMasHechos(hechos));
        resultados.put("ubicacionPorCategoria", calculador.ubicacionConMasHechosPorCategoria(hechos));
        resultados.put("mesPicoPorCategoria", calculador.mesPicoPorCategoria(hechos));
        resultados.put("solicitudesSpam", calculador.totalSolicitudesSpam(solicitudes));

        cache.actualizar(resultados);
    }

    public Object obtenerEstadistica(String clave) {
        return cache.obtener(clave);
    }

    public void exportarCSV(String clave, Path destino) throws IOException {
        Object data = cache.obtener(clave);
        String csv = exportador.generarCSV(data);
        Files.writeString(destino, csv);
    }
}
