package ar.edu.utn.frba.dds.utils;

import java.util.HashMap;
import java.util.Map;

public class Cache {
    private final Map<String, Object> cache = new HashMap<>();

    public void actualizar(Map<String, Object> resultados) {
        cache.clear();
        cache.putAll(resultados);
    }

    public Object obtener(String clave) {
        return cache.get(clave);
    }
}
