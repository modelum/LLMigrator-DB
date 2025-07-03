package eventos.dominio;

import eventos.dominio.enumerados.Categoria;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "UUID")
    private UUID id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Lob
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "organizador", nullable = false)
    private String organizador;

    @Column(name = "plazas", nullable = false)
    private int plazas;

    @Column(name = "cancelado", nullable = false)
    private boolean cancelado;

    @Embedded
    private Ocupacion ocupacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private Categoria categoria;

    public Evento() {
    }

    public Evento(String nombre, String descripcion, String organizador, int plazas, Ocupacion ocupacion, Categoria categoria) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.organizador = organizador;
        this.plazas = plazas;
        this.cancelado = false;
        this.ocupacion = ocupacion;
        this.categoria = categoria;
    }

    public Evento(String nombre, String descripcion, String organizador, int plazas, LocalDateTime fechaInicio, LocalDateTime fechaFin, EspacioFisico espacioFisico, Categoria categoria) {
        this(nombre, descripcion, organizador, plazas, new Ocupacion(fechaInicio, fechaFin, espacioFisico), categoria);
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOrganizador() {
        return organizador;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

    public int getPlazas() {
        return plazas;
    }

    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }

    public boolean isCancelado() {
        return cancelado;
    }

    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }

    public void cancelar() {
        this.cancelado = true;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Ocupacion getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(Ocupacion ocupacion) {
        this.ocupacion = ocupacion;
    }

    public LocalDateTime getFechaInicio() {
        return ocupacion.getFechaInicio();
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.ocupacion.setFechaInicio(fechaInicio);
    }

    public LocalDateTime getFechaFin() {
        return ocupacion.getFechaFin();
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.ocupacion.setFechaFin(fechaFin);
    }

    public String getNombreEspacioFisico() {
        return ocupacion.getNombreEspacioFisico();
    }

    public EspacioFisico getEspacioFisico() {
        return ocupacion.getEspacioFisico();
    }

    public void setEspacioFisico(EspacioFisico espacioFisico) {
        this.ocupacion.setEspacioFisico(espacioFisico);
    }

    // Equals y HashCode
    @Override
    public int hashCode() {
        return Objects.hash(cancelado, categoria, descripcion, id, nombre, ocupacion, organizador, plazas);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Evento other)) return false;
        return cancelado == other.cancelado && plazas == other.plazas && Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre) && Objects.equals(descripcion, other.descripcion) && Objects.equals(organizador, other.organizador) && Objects.equals(ocupacion, other.ocupacion) && categoria == other.categoria;
    }
}
