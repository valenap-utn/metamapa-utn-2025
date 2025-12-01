package ar.edu.utn.frba.dds.clienteInterfaz.clients;

import org.springframework.web.multipart.MultipartFile;

public interface IFuenteEstatica {
//    String subirHechosCSV(MultipartFile archivo, Long idUsuario);
    String subirHechosCSV(MultipartFile archivo);
}
