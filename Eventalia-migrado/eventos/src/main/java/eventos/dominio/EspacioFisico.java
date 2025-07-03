package eventos.dominio;

import eventos.dominio.enumerados.EstadoEspacioFisico;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "espacio_fisico")
public class EspacioFisico {

    @Id
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "capacidad", nullable = false)
    private int capacidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoEspacioFisico estado;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    public EspacioFisico() {
    }

    public EspacioFisico(UUID id, String nombre, int capacidad, EstadoEspacioFisico estado, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.estado = estado;
        this.direccion = direccion;
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public EstadoEspacioFisico getEstado() {
        return estado;
    }

    public void setEstado(EstadoEspacioFisico estado) {
        this.estado = estado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // Equals y HashCode
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EspacioFisico other)) return false;
        return capacidad == other.capacidad && Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre) && estado == other.estado && Objects.equals(direccion, other.direccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, capacidad, estado, direccion);
    }
}
