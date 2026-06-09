package com.utp.massmanager.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "detalle_ventas")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_venta", nullable = false)
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Product producto;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Double subtotal;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public DetalleVenta() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Venta getVenta() { return venta; }
    public void setVenta(Venta venta) { this.venta = venta; }
    public Product getProducto() { return producto; }
    public void setProducto(Product producto) { this.producto = producto; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}