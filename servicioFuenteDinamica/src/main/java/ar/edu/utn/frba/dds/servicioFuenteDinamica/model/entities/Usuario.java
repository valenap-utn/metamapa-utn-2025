package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities;


import java.time.LocalDate;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.roles.Rol;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaDeNacimiento;

    @Column(name = "rol_id")
    private Rol rol;

    public Usuario of(Rol rol){
        return Usuario
                .builder()
                .rol(rol)
                .nombre(null)
                .apellido(null)
                .fechaDeNacimiento(null)
                .build();
    }

    public Usuario of(Rol rol, String nombre){
        return Usuario
                .builder()
                .rol(rol)
                .nombre(nombre)
                .apellido(null)
                .fechaDeNacimiento(null)
                .build();
    }

    public static Usuario of(Rol rol, String nombre, String apellido){
        return Usuario
                .builder()
                .rol(rol)
                .nombre(nombre)
                .apellido(apellido)
                .fechaDeNacimiento(null)
                .build();
    }

    public Usuario of(Rol rol, String nombre, String apellido, LocalDate fechaDeNacimiento){
        return Usuario
                .builder()
                .rol(rol)
                .nombre(nombre)
                .apellido(apellido)
                .fechaDeNacimiento(fechaDeNacimiento)
                .build();
    }

    public Integer getEdad() {
        return LocalDate.now().getYear() - this.fechaDeNacimiento.getYear();
    }

    public Rol getRol() {
        return this.rol;
    }


}