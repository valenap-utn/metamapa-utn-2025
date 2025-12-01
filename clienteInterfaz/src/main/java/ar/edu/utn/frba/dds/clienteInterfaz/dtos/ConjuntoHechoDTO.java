package ar.edu.utn.frba.dds.clienteInterfaz.dtos;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConjuntoHechoDTO {
  List<HechoDTOOutput> hechos = new ArrayList<>();
  List<String> categorias = new ArrayList<>();
}
