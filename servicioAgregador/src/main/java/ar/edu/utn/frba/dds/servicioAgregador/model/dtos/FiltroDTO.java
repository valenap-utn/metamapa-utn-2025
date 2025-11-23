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

@Builder
@Getter
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

  public boolean tieneFiltroUbicacion() {
    return this.getProvincia() != null || this.getDepartamento() != null || this.getMunicipio() != null;
  }

  public List<Hecho> filtrarPorUbicacion(List<Hecho> hechos) {
    FiltroProvincia filtroProvincia = new FiltroProvincia(provincia);
    FiltroDepartamento filtroDepartamento = new FiltroDepartamento(departamento);
    FiltroMunicipio filtroMunicipio = new FiltroMunicipio(municipio);
    List<FiltroUbicacion> listaCriterios = List.of(filtroProvincia, filtroDepartamento, filtroMunicipio);
    return hechos.stream().filter(hecho -> listaCriterios.stream().allMatch(filtro -> filtro.hechoCumple(hecho))).toList();
  }
}
