package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;


import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.deteccionDeSpam.TFIDFCalculadoraPalabras;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.normalizacion.Documento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Transient;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Entity
@NoArgsConstructor
@Inheritance( strategy = InheritanceType.SINGLE_TABLE)
public abstract class FiltroUbicacion  extends Filtro{
  @Column(name = "valor-ubicacion")
  @Getter
  @Setter
  private String valorUbicacion;
  @Transient
  @Getter
  @Setter
  @Value("${api.filtroUbicacion.cantidadAceptable}")
  private Double cantidadAceptable;

  public FiltroUbicacion(String valorUbicacion) {
    this.valorUbicacion = valorUbicacion;
  }

  @Override
  public boolean hechoCumple(Hecho unHecho){
    if (valorUbicacion == null) {
      return true;
    }
    TFIDFCalculadoraPalabras calculadora = new TFIDFCalculadoraPalabras();
    Documento documentoValor = Documento.ofStringSinNormalizar(valorUbicacion);
    Documento documentoDelHecho = Documento.ofStringSinNormalizar(this.getValorHechoUbicacion(unHecho));
    calculadora.recargarDocumentos(List.of(documentoDelHecho.getString(), documentoValor.getString()));
    documentoValor.calcularTFIDF(calculadora);
    documentoDelHecho.calcularTFIDF(calculadora);
    return documentoValor.calcularCosenoConRespectoA(documentoDelHecho) > this.getCantidadAceptable();
  }

  protected abstract String getValorHechoUbicacion(Hecho unHecho);

}
