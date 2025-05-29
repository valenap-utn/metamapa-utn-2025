package ar.edu.utn.frba.dds.servicioAgregador;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.*;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.SolicitudesRepositorio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@EnableScheduling
public class ServicioAgregadorApplication {
	private List<Fuente> fuentes;
	private SolicitudesRepositorio solicitudes;
	private DetectorDeSpam detectorDeSpam;

	public static void main(String[] args) {
		SpringApplication.run(ServicioAgregadorApplication.class, args);
	}

	public ServicioAgregadorApplication(List<Fuente> fuentes, SolicitudesRepositorio solicitudes, DetectorDeSpam detectorDeSpam) {
		this.fuentes = fuentes;
		this.solicitudes = solicitudes;
		this.detectorDeSpam = detectorDeSpam;
	}

	//obtenerHechosDisponibles();

	public Solicitud crearSolicitud(Hecho hecho, Usuario user, String justificacion) {
		if (!hecho.getOrigen().permiteSolicitud()) {
			throw new IllegalArgumentException("No es posible solicitar eliminación de este hecho debido a su origen");
		}

		Solicitud solicitud = new Solicitud(hecho, user, justificacion);

		//se decide modelar la deteccion del spam que se realice antes de agregar la solicitud al repositorio

		if (detectorDeSpam.esSpam(justificacion)) {
			solicitud.marcarComoSpam();
		}

		solicitudes.agregar(solicitud);
		return solicitud;

	}

}
