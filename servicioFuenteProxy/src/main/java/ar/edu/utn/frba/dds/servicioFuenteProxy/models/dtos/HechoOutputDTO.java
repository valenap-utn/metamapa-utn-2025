package ar.edu.utn.frba.dds.servicioFuenteProxy.models.dtos;

import lombok.Data;

@Data
// Data incluye automaticamente:
//      - @Getter
//      - @Setter
//      - @ToString
//      - @EqualsHashCode
//      - @RequiredArgsConstructor

public class HechoOutputDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String categoria;
    private String fechaDeReporte;
    private String fechaAcontecimiento;
    private String ubicacion;
}

// Nota: Este DTO no es una entidad, simplemente es un objeto para transportar datos desde el cliente externo (MetaMapa) y devolverlos al agregador.

