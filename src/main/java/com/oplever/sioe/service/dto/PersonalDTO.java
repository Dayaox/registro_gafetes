package com.oplever.sioe.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Personal entity.
 */
public class PersonalDTO implements Serializable {

    private Long id;

    @NotNull
    private String distrito;

    @NotNull
    private String municipio;

    @NotNull
    private String cargo;

    @NotNull
    private String nombre;

    private Integer tipo;

    @Lob
    private byte[] foto;
    private String fotoContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }
    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }
    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonalDTO personalDTO = (PersonalDTO) o;

        if ( ! Objects.equals(id, personalDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonalDTO{" +
            "id=" + id +
            ", distrito='" + distrito + "'" +
            ", municipio='" + municipio + "'" +
            ", cargo='" + cargo + "'" +
            ", nombre='" + nombre + "'" +
            ", tipo='" + tipo + "'" +
            ", foto='" + foto + "'" +
            '}';
    }
}
