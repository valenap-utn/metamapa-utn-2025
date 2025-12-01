package ar.edu.utn.frba.dds.clienteInterfaz.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DepartamentoResp {
    private List<UbicacionDTO> departamentos;
    private int cantidad;
    private int total;
}