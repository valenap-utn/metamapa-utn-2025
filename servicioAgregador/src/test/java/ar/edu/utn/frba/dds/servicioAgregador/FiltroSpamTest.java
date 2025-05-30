package ar.edu.utn.frba.dds.servicioAgregador;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.TFIDFCalculadoraPalabras;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.DetectorDeSpamBasico;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FiltroSpamTest {
    DetectorDeSpamBasico implDetectorDeSpam;
    TFIDFCalculadoraPalabras itidfCalculadoraPalabras;

    @BeforeEach
    public void init()  {
      List<String> documentos = List.of("No es un crédito, es Libertad para tener todo incluido. Gana mucho dinero. Vuelos redondos: $7,500* Hotel todo incluido: $28,000* Outfit playero: $4,500* Crédito desde $40,000.00 pesos",
              "o esperes más para vivir al máximo. La Tarjeta te da la libertad de pagar a tu ritmo y disfrutar de experiencias que antes parecían imposibles. Consegui este viaje y Gana",
              "Liquida tus deudas con hasta 70% de descuento. Asesoría gratis.",
              "Descubre las ventajas exclusivas para ti Préstamo Personal hasta $50,000 pesos* y gana Plazos flexibles hasta 36 meses* Dinero Electrónico*",
              "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
              "Recibe hasta el 3% de cashback en tus compras con tu tarjeta de Debito. Recibe hasta 3% de cashback en giros participantes Abre tu cuenta en minutos desde cualquier dispositivo móvil. Solo necesitas ser mayor de edad, tener nacionalidad mexicana y tu INE. Sin comisión por apertura",
              "Aprovecha Ahora Tiempo limitado Solicitar información ¿Quieres recibir nuestras mejores ofertas?",
              "Soluciones financieras a tu alcance Respaldo económico cuando más lo necesitas Un préstamo confiable para ti Solicítalo en menos de 10 minutos Estimado(a) cliente: En entendemos que la vida puede presentar situaciones inesperadas que requieren apoyo financiero inmediato. Por eso, hemos desarrollado una solución de préstamos personales que combina rapidez, transparencia y condiciones adaptadas a tus necesidades. Nuestro proceso de solicitud 100% en línea te permite obtener los fondos que necesitas sin complicaciones y sin largas esperas.",
              "Gana por mes Gana efectivo Acceso gratuito",
              "Información gratuita Membresía gratuita Aumentar los ingresos Ganancias instantáneas Cien por ciento gratis");
      this.itidfCalculadoraPalabras = new TFIDFCalculadoraPalabras(documentos);
      this.implDetectorDeSpam = new DetectorDeSpamBasico(this.itidfCalculadoraPalabras, 0.5);
    }


    @Test
    @DisplayName("Verificar si con un documento con la palabra gana repetida muchas veces da que es spam")
    void pruebaConUnSoloDocumento() {
      Assertions.assertTrue(this.implDetectorDeSpam.esSpam("Gana Gana Gana Gana Gana Gana Gana Gana Gana Gana Gana Gana Gana"));
    }
}
