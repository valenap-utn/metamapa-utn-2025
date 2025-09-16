package ar.edu.utn.frba.dds.utils;


import ar.edu.utn.frba.dds.model.entities.Hecho;
import ar.edu.utn.frba.dds.model.entities.Solicitud;
import java.util.*;
import java.util.stream.Collectors;

public class CalculadorEstadisticas {

    public Map<String, Long> ubicacionConMasHechosPorOrigen(List<Hecho> hechos) {
        return hechos.stream()
                .collect(Collectors.groupingBy(
                        h -> h.getOrigen() + "-" + h.getUbicacion(),
                        Collectors.counting()
                ));
    }

    public String categoriaConMasHechos(List<Hecho> hechos) {
        return hechos.stream()
                .collect(Collectors.groupingBy(Hecho::getCategoria, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> entry.getKey().getNombre())
                .orElse("N/A");
    }


    public Map<String, Long> ubicacionConMasHechosPorCategoria(List<Hecho> hechos) {
        return hechos.stream()
                .collect(Collectors.groupingBy(
                        h -> h.getCategoria() + "-" + h.getUbicacion(),
                        Collectors.counting()
                ));
    }

    public Map<Integer, Long> horaPicoPorCategoria(List<Hecho> hechos) {
        return hechos.stream()
                .collect(Collectors.groupingBy(h -> h.getFechaAcontecimiento().getHour(), Collectors.counting()));
    }

    public long totalSolicitudesSpam(List<Solicitud> solicitudes) {
        return solicitudes.stream()
                .filter(Solicitud::fueMarcadaComoSpam)
                .count();
    }
}
