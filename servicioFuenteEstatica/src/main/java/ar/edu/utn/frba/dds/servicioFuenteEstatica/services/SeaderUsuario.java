package ar.edu.utn.frba.dds.servicioFuenteEstatica.services;


import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.roles.Administrador;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.roles.Contribuyente;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories.IUserRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SeaderUsuario {
  private final IUserRepository userRepository;

  public SeaderUsuario(IUserRepository userRepository) {

    this.userRepository = userRepository;
  }

  public void init() {
    Usuario contribuyente = Usuario.of(new Contribuyente(), "Carlos", "Romualdo");
    Usuario administrador = Usuario.of(new Administrador(), "Josefina", "Mariel");
    this.userRepository.save(contribuyente);
    this.userRepository.save(administrador);
  }
}
