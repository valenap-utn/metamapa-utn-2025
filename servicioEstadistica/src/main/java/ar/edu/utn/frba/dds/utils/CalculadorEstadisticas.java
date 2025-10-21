package ar.edu.utn.frba.dds.utils;


import ar.edu.utn.frba.dds.model.dtos.DatoEstadisticoDTO;
import ar.edu.utn.frba.dds.model.dtos.EstadisticaDTO;
import ar.edu.utn.frba.dds.model.entities.Hecho;
import ar.edu.utn.frba.dds.model.entities.Solicitud;
import java.util.*;
import java.util.stream.Collectors;

public interface CalculadorEstadisticas {
    EstadisticaDTO calcular(List<Hecho> hechos);
}
