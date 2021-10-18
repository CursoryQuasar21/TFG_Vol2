package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Coche.
 */
@Entity
@Table(name = "coche")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Coche implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 15)
    @Column(name = "color", length = 15, nullable = false)
    private String color;

    @NotNull
    @Size(min = 3, max = 15)
    @Column(name = "modelo", length = 15, nullable = false)
    private String modelo;

    @NotNull
    @Size(min = 3, max = 15)
    @Column(name = "marca", length = 15, nullable = false)
    private String marca;

    @Column(name = "anio")
    private Instant anio;

    @NotNull
    @DecimalMin(value = "1")
    @Column(name = "precio", nullable = false)
    private Double precio;

    @ManyToOne
    @JsonIgnoreProperties(value = { "coches", "motos", "cliente", "empleado" }, allowSetters = true)
    private Venta venta;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Coche id(Long id) {
        this.id = id;
        return this;
    }

    public String getColor() {
        return this.color;
    }

    public Coche color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModelo() {
        return this.modelo;
    }

    public Coche modelo(String modelo) {
        this.modelo = modelo;
        return this;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return this.marca;
    }

    public Coche marca(String marca) {
        this.marca = marca;
        return this;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Instant getAnio() {
        return this.anio;
    }

    public Coche anio(Instant anio) {
        this.anio = anio;
        return this;
    }

    public void setAnio(Instant anio) {
        this.anio = anio;
    }

    public Double getPrecio() {
        return this.precio;
    }

    public Coche precio(Double precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Venta getVenta() {
        return this.venta;
    }

    public Coche venta(Venta venta) {
        this.setVenta(venta);
        return this;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Coche)) {
            return false;
        }
        return id != null && id.equals(((Coche) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Coche{" +
            "id=" + getId() +
            ", color='" + getColor() + "'" +
            ", modelo='" + getModelo() + "'" +
            ", marca='" + getMarca() + "'" +
            ", anio='" + getAnio() + "'" +
            ", precio=" + getPrecio() +
            "}";
    }
}
