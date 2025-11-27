package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros.FiltroDepartamento;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros.FiltroMunicipio;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros.FiltroProvincia;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros.FiltroUbicacion;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class FiltroDTO {
  String categoria;
  LocalDateTime fecha_reporte_desde;
  LocalDateTime fecha_reporte_hasta;
  LocalDateTime fecha_acontecimiento_desde;
  LocalDateTime fecha_acontecimiento_hasta;
  Float latitud;
  Float longitud;
  Boolean curada;
  Boolean entiemporeal;
  Long idUsuario;
  String provincia;
  String departamento;
  String municipio;
  Integer nroPagina;

  public boolean tieneFiltroUbicacion() {
    return (this.getProvincia() != null && !this.getProvincia().isEmpty())
            || (this.getDepartamento() != null && !this.getDepartamento().isEmpty())
            || (this.getMunicipio() != null && !this.getMunicipio().isEmpty());
  }

  public List<Hecho> filtrarPorUbicacion(List<Hecho> hechos, Double cantidadAceptable) {
    FiltroProvincia filtroProvincia = new FiltroProvincia(provincia, cantidadAceptable);
    FiltroDepartamento filtroDepartamento = new FiltroDepartamento(departamento, cantidadAceptable);
    FiltroMunicipio filtroMunicipio = new FiltroMunicipio(municipio, cantidadAceptable);
    List<FiltroUbicacion> listaCriterios = List.of(filtroProvincia, filtroDepartamento, filtroMunicipio);
    return hechos.stream().filter(hecho -> listaCriterios.stream().allMatch(filtro -> filtro.hechoCumple(hecho))).toList();
  }
}
