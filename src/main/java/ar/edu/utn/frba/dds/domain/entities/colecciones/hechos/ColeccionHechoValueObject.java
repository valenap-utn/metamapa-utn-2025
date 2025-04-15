package ar.edu.utn.frba.dds.domain.entities.colecciones.hechos;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class ColeccionHechoValueObject {
  List<HechoValueObject> hechosDataset;

  public ColeccionHechoValueObject() {
    this.hechosDataset = new ArrayList<>();
  }

  public void agregarHechosDataset(HechoValueObject... hechosDataset) {
      this.hechosDataset.addAll(List.of(hechosDataset));
  }

  public void borrarRepetidos() {
    Map<String, HechoValueObject> hechosUnicos = new LinkedHashMap<>();

    for (HechoValueObject h : this.hechosDataset) {
      hechosUnicos.putIfAbsent(h.getTitulo(), h); // No pisa si ya estaba
    }

    this.hechosDataset = hechosUnicos.values().stream().toList();
  }

  public Set<Hecho> getHechos() {
    return this.hechosDataset.stream().map(hechoValueObject ->
            new Hecho(hechoValueObject, Origen.CARGAMANUAL)).collect(Collectors.toSet());
  }

}
