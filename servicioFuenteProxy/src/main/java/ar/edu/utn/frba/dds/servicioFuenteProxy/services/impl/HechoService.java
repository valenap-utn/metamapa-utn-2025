package ar.edu.utn.frba.dds.servicioFuenteProxy.services.impl;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.HechoInputDTO;
import ar.edu.utn.frba.dds.servicioFuenteProxy.models.dtos.HechoOutputDTO;
import ar.edu.utn.frba.dds.servicioFuenteProxy.services.IAPIService;
import ar.edu.utn.frba.dds.servicioFuenteProxy.services.IHechoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// HechoService consume a ClientService, recibe esos HechoInputDTO, los transforma directamente a HechoOutputDTO porque no persiste
// para ser usado localmente y entregados hacia el Controller, que luego los entregara al Servicio Agregador


@Service
public class HechoService implements IHechoService {

    private final IAPIService apiService;

    public HechoService(IAPIService apiService){
        this.apiService = apiService;
    }

    @Override
    public List<HechoOutputDTO> transformarHechos(){
        //deberia asegurarme que el usuario que esta intentando recuperar todos los hechos tiene permisos para hacerlo
        List<HechoInputDTO> hechos = this.apiService.getAllHechosExternos();
        return hechos
                .stream()
                .map(this::transformarAOutputDTO)
                .toList();
    }

    //Metodo MAPPER
    private HechoOutputDTO transformarAOutputDTO(HechoInputDTO hecho){
        return new HechoOutputDTO(
            hecho.getId()
            //campos que correspondan
        );
    };
}

