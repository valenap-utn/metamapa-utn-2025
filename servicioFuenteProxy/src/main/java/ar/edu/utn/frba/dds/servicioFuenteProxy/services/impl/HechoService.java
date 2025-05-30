package ar.edu.utn.frba.dds.servicioFuenteProxy.services.impl;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.HechoInputDTO;
import ar.edu.utn.frba.dds.servicioFuenteProxy.services.IAPIService;
import ar.edu.utn.frba.dds.servicioFuenteProxy.services.IHechoService;
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
    public List<HechoInputDTO> obtenerHechosExternos(){
        return apiService.getAllHechosExternos();
    };
}

