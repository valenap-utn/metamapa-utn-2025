package ar.edu.utn.frba.dds.servicioAgregador.controllers;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoCategorias;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.CategoriaRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implReal.ICategoriaRepositoryJPA;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/categorias")
public class CategoriaController {
  private final ICategoriaRepositoryJPA categoriaRepository;
  @Value("${api.value.tamanio.pagina}")
  private Integer tamanioPagina;

  public CategoriaController(ICategoriaRepositoryJPA categoriaRepository) {
    this.categoriaRepository = categoriaRepository;
  }

  @GetMapping
  public ResponseEntity<ConjuntoCategorias> listaCategorias(){
    List<Categoria> categorias = this.categoriaRepository.findAll(PageRequest.of(0, this.tamanioPagina)).getContent();
    ConjuntoCategorias conjunto = new ConjuntoCategorias();
    conjunto.setCategorias(categorias.stream().map(Categoria::getNombre).toList());
    return ResponseEntity.ok(conjunto);
  }
}
