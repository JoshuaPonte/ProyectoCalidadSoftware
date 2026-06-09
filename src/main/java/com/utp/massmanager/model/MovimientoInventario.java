package com.utp.massmanager.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos_inventario")
public class MovimientoInventario {

    public enum TipoMovimiento {
        ENTRADA, SALIDA, AJUSTE, VENTA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Product producto;

    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado empleado;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento", nullable = false, length = 20)
    private TipoMovimiento tipoMovimiento;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(length = 200)
    private String justificacion;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public MovimientoInventario() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Product getProducto() { return producto; }
    public void setProducto(Product producto) { this.producto = producto; }
    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }
    public TipoMovimiento getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(TipoMovimiento tipo) { this.tipoMovimiento = tipo; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public String getJustificacion() { return justificacion; }
    public void setJustificacion(String justificacion) { this.justificacion = justificacion; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}