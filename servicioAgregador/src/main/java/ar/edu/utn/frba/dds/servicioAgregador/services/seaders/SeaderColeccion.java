package ar.edu.utn.frba.dds.servicioAgregador.services.seaders;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Coleccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.ContenidoMultimedia;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso.MayoriaSimple;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso.TodosConsensuados;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros.FiltroPorFechaCarga;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros.FiltroPorTitulo;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.TipoOrigen;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles.Contribuyente;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IColeccionRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IUserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SeaderColeccion {
  private final IColeccionRepository coleccionRepository;
  private final IHechoRepository hechoRepository;
  @Value("${api.url.fuenteProxy}")
  private String urlFuenteProxy;
  @Value("${api.url.fuenteDinamica}")
  private String urlFuenteDinamica;
  @Value("${api.url.fuenteEstatica}")
  private String urlFuenteEstatica;
  private final IUserRepository userRepository;

  public SeaderColeccion(IColeccionRepository coleccionRepository, IHechoRepository hechoRepository, IUserRepository userRepository) {
    this.coleccionRepository = coleccionRepository;
    this.hechoRepository = hechoRepository;
    this.userRepository = userRepository;
  }

  public void init() {
    Origen origenDinamica = Origen.builder().url(this.urlFuenteDinamica).tipo(TipoOrigen.PORCONTRIBUYENTE).build();
    Origen origenEstatica = Origen.builder().url(this.urlFuenteEstatica).tipo(TipoOrigen.DATASET).build();
    Origen origenProxy = Origen.builder().url(this.urlFuenteProxy).tipo(TipoOrigen.PROXY).build();
    Coleccion coleccion = new Coleccion();
    Coleccion coleccion2 = new Coleccion();
    Fuente fuenteDinamica = new Fuente(origenDinamica);
    Fuente fuenteProxy = new Fuente(origenProxy);
    Fuente fuenteEstatica = new Fuente(origenEstatica);


    coleccion.setTitulo("Desastres Naturales");
    coleccion.setDescripcion("Se muestran los desastres Naturales ocurridos en Buenos Aires, Argentina");
    coleccion.setAlgoritmoConsenso(new TodosConsensuados());
    coleccion.agregarCriterios(new FiltroPorFechaCarga(LocalDate.of(2020, 1, 12), LocalDate.now()));
    coleccion.agregarFuentes(List.of(fuenteDinamica, fuenteEstatica));


    coleccion2.setTitulo("Terremotos");
    coleccion2.setDescripcion("Tiene los terremotos que ocurren cerca de la cordillera de los andes");
    coleccion2.setAlgoritmoConsenso(new MayoriaSimple());
    coleccion2.agregarFuentes(List.of(fuenteDinamica));
    //coleccion2.agregarFuentes(List.of(fuenteProxy));

    this.coleccionRepository.save(coleccion);
    this.coleccionRepository.save(coleccion2);
    ContenidoMultimedia contenidoMultimedia = new ContenidoMultimedia();
    Usuario contribuyente = Usuario.of(new Contribuyente(), "Carlos", "Romualdo");
    Usuario contribuyente2 = Usuario.of(new Contribuyente(), "Josefina", "Mariel");
    this.userRepository.save(contribuyente);
    this.userRepository.save(contribuyente2);
    // Creación de hechos
    Hecho hecho = Hecho.builder()
            .titulo("Inundacion Bahia Blanca")
            .categoria(new Categoria("Inundacion"))
            .fechaCarga(LocalDate.now())
            .fechaAcontecimiento(LocalDate.of(2025, 3, 7))
            .contenidoMultimedia(contenidoMultimedia)
            .origen(origenDinamica)
            .usuario(contribuyente)
            .build();

    Hecho hecho2 = Hecho.builder().origen(origenDinamica)
            .contenidoMultimedia(contenidoMultimedia)
            .fechaCarga(LocalDate.now())
            .titulo("Bahia Blanca se inunda")
            .categoria(new Categoria("Inundacion"))
            .fechaAcontecimiento(LocalDate.of(2025, 3, 7))
            .usuario(contribuyente2)
            .origen(origenDinamica)
            .build();

    this.hechoRepository.saveHecho(hecho);
    this.hechoRepository.saveHecho(hecho2);
  }
}
