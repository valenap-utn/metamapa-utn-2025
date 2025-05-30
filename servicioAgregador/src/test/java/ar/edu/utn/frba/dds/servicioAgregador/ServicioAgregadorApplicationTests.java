package ar.edu.utn.frba.dds.servicioAgregador;

import static org.mockito.Mockito.*;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Coleccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.ColeccionRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.HechoRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.UserRepository;
import ar.edu.utn.frba.dds.servicioAgregador.services.ColeccionService;
import ar.edu.utn.frba.dds.servicioAgregador.services.ConexionEstaticaDinamicaService;
import ar.edu.utn.frba.dds.servicioAgregador.services.ConexionProxyService;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

//@SpringBootTest
class ServicioAgregadorApplicationTests {
	Coleccion coleccionPrueba;
	Fuente fuentePrueba;
	ConexionEstaticaDinamicaService conexionDinamicaService;
	ConexionEstaticaDinamicaService conexionEstaticaService;
	ConexionProxyService conexionProxyService;
	ColeccionService coleccionService;
	ColeccionRepository coleccionRepository;
	UserRepository userRepository;
	HechoRepository hechoRepository;

	@BeforeEach
	public void init()  {
		//this.lectorConfigs = new ConfigReader("ArchivosCSV.properties");
		this.conexionDinamicaService = mock(ConexionEstaticaDinamicaService.class);
		this.conexionEstaticaService = mock(ConexionEstaticaDinamicaService.class);
		this.conexionProxyService = mock(ConexionProxyService.class);
		this.coleccionRepository = new ColeccionRepository();
		this.userRepository = new UserRepository();
		this.hechoRepository = new HechoRepository();
		this.coleccionService = new ColeccionService(this.coleccionRepository, this.userRepository, this.hechoRepository);
		this.coleccionPrueba = new Coleccion("Colección prueba", "Esto es una prueba");
		this.fuentePrueba = new Fuente(2L, "Estatica");
		this.coleccionPrueba.agregarFuentes(List.of(this.fuentePrueba));
		this.coleccionService.agregarConexionAFuente(1L, this.conexionDinamicaService);
		this.coleccionService.agregarConexionAFuente(2L, this.conexionEstaticaService);
		this.coleccionService.agregarConexionAFuente(3L, this.conexionProxyService);
		this.coleccionRepository.save(this.coleccionPrueba);
	}

	Mono<Void> llenarFuente(Fuente fuente) {
		fuente.agregarHechos(List.of(new Hecho(), new Hecho()));
		return Mono.empty();
	}

	@Test
	@DisplayName("Verificar si se da bien la acción de actualizar de un scheduler")
	void pruebaConUnaSolaFuente() {
		when(this.conexionEstaticaService.actualizarHechosFuente(this.fuentePrueba, this.hechoRepository)).thenReturn(this.llenarFuente(this.fuentePrueba));
		this.coleccionService.actualizarHechosColecciones().subscribe();

		Assertions.assertThat(this.fuentePrueba.getHechos().size()).isEqualTo(2);
	}


	@Test
	@DisplayName("Verificar si se da bien la acción de actualizar de un scheduler con multiples fuentes")
	void pruebaConVariasFuentes() {
		Fuente fuente2 = new Fuente(1L, "Dinamica");
		Fuente fuente3 = new Fuente(1L, "Dinamica");
		Fuente fuente4 = new Fuente(3L, "Proxy");
		this.coleccionPrueba.agregarFuentes(List.of(fuente2, fuente3, fuente4));
		when(this.conexionEstaticaService.actualizarHechosFuente(this.fuentePrueba, this.hechoRepository)).thenReturn(this.llenarFuente(this.fuentePrueba));
		when(this.conexionDinamicaService.actualizarHechosFuente(fuente2, this.hechoRepository)).thenReturn(this.llenarFuente(fuente2));
		when(this.conexionDinamicaService.actualizarHechosFuente(fuente3, this.hechoRepository)).thenReturn(this.llenarFuente(fuente3));
		when(this.conexionProxyService.actualizarHechosFuente(fuente4, this.hechoRepository)).thenReturn(this.llenarFuente(fuente4));
		this.coleccionService.actualizarHechosColecciones().subscribe();

		Assertions.assertThat(this.fuentePrueba.getHechos().size()).isEqualTo(2);
		Assertions.assertThat(fuente2.getHechos().size()).isEqualTo(2);
		Assertions.assertThat(fuente3.getHechos().size()).isEqualTo(2);
		Assertions.assertThat(fuente4.getHechos().size()).isEqualTo(2);
	}

	Mono<Void> llenarFuenteConNHechos(Fuente fuente, Integer cantidad) {
		for (int i = 0; i < cantidad; i++) {
			fuente.agregarHechos(List.of(new Hecho()));
		}
		return Mono.empty();
	}
	@Test
	@DisplayName("Verificar si se da bien la acción de actualizar de un scheduler con multiples fuentes, que tienen distintos hechos")
	void pruebaConDistintosHechosFuente() {
		Fuente fuente2 = new Fuente(1L, "Dinamica");
		Fuente fuente3 = new Fuente(1L, "Dinamica");
		Fuente fuente4 = new Fuente(3L, "Proxy");
		this.coleccionPrueba.agregarFuentes(List.of(fuente2, fuente3, fuente4));
		when(this.conexionEstaticaService.actualizarHechosFuente(this.fuentePrueba, this.hechoRepository)).thenReturn(this.llenarFuenteConNHechos(this.fuentePrueba, 2));
		when(this.conexionDinamicaService.actualizarHechosFuente(fuente2, this.hechoRepository)).thenReturn(this.llenarFuenteConNHechos(fuente2,4));
		when(this.conexionDinamicaService.actualizarHechosFuente(fuente3, this.hechoRepository)).thenReturn(this.llenarFuenteConNHechos(fuente3, 5));
		when(this.conexionProxyService.actualizarHechosFuente(fuente4, this.hechoRepository)).thenReturn(this.llenarFuenteConNHechos(fuente4, 6));
		this.coleccionService.actualizarHechosColecciones().subscribe();

		Assertions.assertThat(this.fuentePrueba.getHechos().size()).isEqualTo(2);
		Assertions.assertThat(fuente2.getHechos().size()).isEqualTo(4);
		Assertions.assertThat(fuente3.getHechos().size()).isEqualTo(5);
		Assertions.assertThat(fuente4.getHechos().size()).isEqualTo(6);
	}
}
