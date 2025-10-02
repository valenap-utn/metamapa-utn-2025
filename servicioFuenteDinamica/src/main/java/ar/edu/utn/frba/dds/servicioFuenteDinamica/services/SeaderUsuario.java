package ar.edu.utn.frba.dds.servicioFuenteDinamica.services;



import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.roles.Administrador;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.roles.Contribuyente;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.roles.PermisoModificarHecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.implReal.IUsuarioRepositoryJPA;
import org.springframework.stereotype.Service;

@Service
public class SeaderUsuario {
  private final IUsuarioRepositoryJPA userRepository;

  public SeaderUsuario(IUsuarioRepositoryJPA userRepository) {

    this.userRepository = userRepository;
  }

  public void init() {
    Contribuyente contribuyenteRol = new Contribuyente();
    PermisoModificarHecho permisoRol = new PermisoModificarHecho();
    permisoRol.setDescripcion("Modificar Hecho");
    contribuyenteRol.agregarPermisos(permisoRol);

    Usuario contribuyente = Usuario.of(contribuyenteRol, "Carlos", "Romualdo");
    Usuario administrador = Usuario.of(new Administrador(), "Josefina", "Mariel");
    this.userRepository.save(contribuyente);
    this.userRepository.save(administrador);
  }
}
