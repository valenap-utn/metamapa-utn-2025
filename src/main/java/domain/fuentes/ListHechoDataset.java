package domain.fuentes;

import ar.edu.utn.frba.dds.Hecho;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class ListHechoDataset {
  List<HechoDataset> hechosDataset;

  public ListHechoDataset() {
    this.hechosDataset = new ArrayList<>();
  }

  public void agregarHechosDataset(HechoDataset ... hechosDataset) {
      this.hechosDataset.addAll(List.of(hechosDataset));
  }

  public void borrarRepetidos() {
    List<HechoDataset> listaAuxiliar = this.hechosDataset.stream().toList();
    listaAuxiliar.forEach( hechoDataset -> this.eliminarSiTieneRepetidos(hechoDataset));
  }

  private void eliminarSiTieneRepetidos(HechoDataset unHecho) {
    if(hayRepetidos(unHecho))
      this.hechosDataset.remove(unHecho);
  }

  private boolean hayRepetidos(HechoDataset unHecho) {
    return  this.obtenerRepetidosDe(unHecho)
            .size() > 1;
  }

  private List<HechoDataset> obtenerRepetidosDe(HechoDataset unHecho) {
    return this.hechosDataset.stream()
            .filter(hechoDataset -> hechoDataset.esIgualA(unHecho)).toList();
  }

  public Set<Hecho> getHechos() {
    return this.hechosDataset.stream().map( hechoDataset ->
            new Hecho(hechoDataset, new CargaManual())).collect(Collectors.toSet());
  }

}
