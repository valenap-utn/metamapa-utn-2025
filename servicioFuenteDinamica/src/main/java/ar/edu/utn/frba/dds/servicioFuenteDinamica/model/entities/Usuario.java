package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities;


import java.time.LocalDate;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.roles.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
    private String nombre;
    private String apellido;
    private LocalDate fechaDeNacimiento;
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

    public Usuario of(Rol rol, String nombre, String apellido){
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