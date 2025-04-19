package ar.edu.utn.frba.dds.domain.solicitud;

import ar.edu.utn.frba.dds.domain.entities.Usuario;
import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;
import ar.edu.utn.frba.dds.domain.entities.roles.Administrador;
import ar.edu.utn.frba.dds.domain.entities.roles.Contribuyente;

import java.util.ArrayList;
import java.util.List;

public class gestionarSolicitud {
    List<Solicitud> solicitudes = new ArrayList<>();

    public Solicitud crearSolicitud(Hecho hecho, Usuario contr, String justificacion) {
        verificarRol(contr);
        Solicitud solicitud = new Solicitud(hecho, contr, justificacion);
        solicitudes.add(solicitud);
        return solicitud;
    }

    public void aceptarSolicitud(Solicitud solicitud, Usuario admin) {
        verificarRol(admin);
        solicitud.aceptar();
    }

    public void rechazarSolicitud(Solicitud solicitud, Usuario admin) {
        verificarRol(admin);
        solicitud.rechazar();
    }

    private void verificarRol(Usuario usuario) {
        if (!(usuario.getRol() instanceof Contribuyente)) {
            throw new IllegalArgumentException("Solo si es contribuyente puede solicitar eliminaciones");
        }
        else if (!(usuario.getRol() instanceof Administrador)) {
            throw new IllegalArgumentException("Solo si es administrador puede aprobar/rechazar solicitudes");
        }
    }

}
