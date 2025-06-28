package ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.output;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.input.HechoInputDTO;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.Fuente;
import org.springframework.stereotype.Component;

@Component
public class HechoMapper {
    public HechoOutputDTO toOutputDTO(HechoInputDTO input, Fuente fuente) {
        HechoOutputDTO output = new HechoOutputDTO();
        output.setId(input.getId());
        output.setTitulo(input.getTitulo());
        output.setDescripcion(input.getDescripcion());
        output.setCategoria(input.getCategoria());
        output.setLatitud(input.getLatitud());
        output.setLongitud(input.getLongitud());
        output.setFecha(input.getFecha_hecho());
        output.setFechaCarga(input.getCreated_at());
        output.setFuente(fuente.name()); // .name() lo devuelve tal cual el enum pero en formato string "DESASTRES_NATURALES" o "META_MAPA"
        return output;
    }
}
