package ar.edu.utn.frba.dds.utils;

import java.util.Map;
import java.util.stream.Collectors;

public class ExportadorCSV {

    public String generarCSV(Object data) {
        if (data instanceof Map<?, ?> mapa) {
            return mapa.entrySet().stream()
                    .map(e -> e.getKey() + "," + e.getValue())
                    .collect(Collectors.joining("\n", "clave,valor\n", ""));
        }
        return data.toString();
    }
}
