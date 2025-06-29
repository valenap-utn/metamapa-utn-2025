package ar.edu.utn.frba.dds.servicioFuenteProxy.services.impl;
import org.springframework.web.util.UriBuilder;

import java.time.LocalDate;
import java.util.function.Function;

public class UriHelper {

        public static Function<UriBuilder, UriBuilder> buildHechosUri(
                String categoria,
                LocalDate fechaReporteDesde,
                LocalDate fechaReporteHasta,
                LocalDate fechaAcontecimientoDesde,
                LocalDate fechaAcontecimientoHasta,
                String ubicacion
        ) {
            return uriBuilder -> {
                UriBuilder builder = uriBuilder.path("hechos");

                if (categoria != null) {
                    builder = builder.queryParam("categoria", categoria);
                }
                if (fechaReporteDesde != null) {
                    builder = builder.queryParam("fecha_reporte_desde", fechaReporteDesde);
                }
                if (fechaReporteHasta != null) {
                    builder = builder.queryParam("fecha_reporte_hasta", fechaReporteHasta);
                }
                if (fechaAcontecimientoDesde != null) {
                    builder = builder.queryParam("fecha_acontecimiento_desde", fechaAcontecimientoDesde);
                }
                if (fechaAcontecimientoHasta != null) {
                    builder = builder.queryParam("fecha_acontecimiento_hasta", fechaAcontecimientoHasta);
                }
                if (ubicacion != null) {
                    builder = builder.queryParam("ubicacion", ubicacion);
                }

                return builder;
            };
        }
    }

