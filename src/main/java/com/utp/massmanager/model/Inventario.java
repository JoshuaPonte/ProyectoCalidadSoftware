package com.utp.massmanager.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_producto", nullable = false, unique = true)
    private Product producto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    public Inventario() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Product getProducto() { return producto; }
    public void setProducto(Product producto) { this.producto = producto; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}