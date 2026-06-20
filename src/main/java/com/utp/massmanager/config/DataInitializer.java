package com.utp.massmanager.config;

import com.utp.massmanager.model.*;
import com.utp.massmanager.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private RolRepository rolRepository;
    @Autowired private EmpleadoRepository empleadoRepository;
    @Autowired private CategoriaRepository categoriaRepository;
    @Autowired private ProveedorRepository proveedorRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private InventarioRepository inventarioRepository;
    @Autowired private MovimientoInventarioRepository movimientoRepository;

    @Override
    public void run(String... args) {

        // ── ROLES ──────────────────────────────────────────
        Rol admin   = getOrCreateRol("Administrador");
        Rol cajero  = getOrCreateRol("Cajero");
        Rol almacen = getOrCreateRol("Almacén");

        // ── EMPLEADOS ──────────────────────────────────────
        createEmpleado("Carlos", "Ramírez", "admin@mass.pe",   "admin123",   admin);
        createEmpleado("Lucía",  "Torres",  "cajero@mass.pe",  "cajero123",  cajero);
        createEmpleado("Miguel", "Flores",  "almacen@mass.pe", "almacen123", almacen);

        // ── CATEGORÍAS ─────────────────────────────────────
        createCategoria("Abarrotes",  "Productos de primera necesidad");
        createCategoria("Bebidas",    "Bebidas y refrescos");
        createCategoria("Lácteos",    "Leche, queso, yogurt y derivados");
        createCategoria("Panadería",  "Pan, galletas y productos horneados");
        createCategoria("Limpieza",   "Productos de limpieza del hogar");
        createCategoria("Conservas",  "Latas y conservas alimenticias");

        // ── PROVEEDORES ────────────────────────────────────
        createProveedor("Gloria S.A.",       "01-2001000", "Av. República de Panamá 2461, Lima");
        createProveedor("Alicorp S.A.A.",    "01-3150800", "Av. Argentina 4793, Lima");
        createProveedor("Arca Continental",  "01-6189000", "Av. Naciones Unidas 1084, Lima");
        createProveedor("Distribuidora Lima","01-5551234", "Jr. Huallaga 348, Lima");
        createProveedor("Procter & Gamble",  "01-6110000", "Av. El Derby 254, Lima");

        // ── PRODUCTOS ──────────────────────────────────────
        Categoria abarrotes = categoriaRepository.findByNombre("Abarrotes").orElseThrow();
        Categoria lacteos   = categoriaRepository.findByNombre("Lácteos").orElseThrow();
        Categoria bebidas   = categoriaRepository.findByNombre("Bebidas").orElseThrow();
        Categoria limpieza  = categoriaRepository.findByNombre("Limpieza").orElseThrow();
        Categoria conservas = categoriaRepository.findByNombre("Conservas").orElseThrow();

        Proveedor gloria    = proveedorRepository.findByNombre("Gloria S.A.").orElseThrow();
        Proveedor alicorp   = proveedorRepository.findByNombre("Alicorp S.A.A.").orElseThrow();
        Proveedor arca      = proveedorRepository.findByNombre("Arca Continental").orElseThrow();
        Proveedor distLima  = proveedorRepository.findByNombre("Distribuidora Lima").orElseThrow();

        createProducto("Aceite Vegetal Mass 1L",        22.50, 200, 50, abarrotes, alicorp,  "/uploads/productos/PROY08-01.webp");
        createProducto("Leche Evaporada Gloria 400g",    3.80,  45, 10, lacteos,   gloria,   "/uploads/productos/PROY08-02.webp");
        createProducto("Coca Cola 500ml",                2.50, 150, 30, bebidas,   arca,     "/uploads/productos/PROY08-03.webp");
        createProducto("Detergente Líquido Limón 3L",   12.50,   2, 20, limpieza,  distLima, "/uploads/productos/PROY08-04.webp");
        createProducto("Atún en Trozos 170g",            4.20,  68, 30, conservas, null,     "/uploads/productos/PROY08-05.webp");

        // ── INVENTARIO ──────────────────────────────────────
        if (inventarioRepository.count() == 0) {
            for (Product p : productRepository.findAll()) {
                Inventario inv = new Inventario();
                inv.setProducto(p);
                inv.setCantidad(p.getStock());
                inventarioRepository.save(inv);
            }
        }

        // ── MOVIMIENTOS DE EJEMPLO ──────────────────────────
        // Solo creamos movimientos consistentes con el stock actual
        if (movimientoRepository.count() == 0) {
            Empleado empAlmacen = empleadoRepository.findByCorreo("almacen@mass.pe").orElse(null);
            Empleado empAdmin   = empleadoRepository.findByCorreo("admin@mass.pe").orElse(null);
            Product pAceite     = productRepository.findByNombre("Aceite Vegetal Mass 1L").orElse(null);
            Product pDetergente = productRepository.findByNombre("Detergente Líquido Limón 3L").orElse(null);

            // Historia: Aceite → entrada de 200 unidades (stock actual = 200)
            if (empAlmacen != null && pAceite != null) {
                MovimientoInventario m = new MovimientoInventario();
                m.setProducto(pAceite);
                m.setEmpleado(empAlmacen);
                m.setTipoMovimiento(MovimientoInventario.TipoMovimiento.ENTRADA);
                m.setCantidad(200);
                m.setJustificacion("Stock inicial - Compra proveedor");
                movimientoRepository.save(m);
            }
            // Historia: Detergente → entrada 30, venta 28, quedan 2 (stock actual = 2)
            if (empAdmin != null && pDetergente != null) {
                MovimientoInventario m1 = new MovimientoInventario();
                m1.setProducto(pDetergente);
                m1.setEmpleado(empAdmin);
                m1.setTipoMovimiento(MovimientoInventario.TipoMovimiento.ENTRADA);
                m1.setCantidad(30);
                m1.setJustificacion("Compra inicial");
                movimientoRepository.save(m1);

                MovimientoInventario m2 = new MovimientoInventario();
                m2.setProducto(pDetergente);
                m2.setEmpleado(empAdmin);
                m2.setTipoMovimiento(MovimientoInventario.TipoMovimiento.VENTA);
                m2.setCantidad(28);
                m2.setJustificacion(null);
                movimientoRepository.save(m2);
            }
        }
    }

    // ── HELPERS ────────────────────────────────────────────

    private Rol getOrCreateRol(String descripcion) {
        return rolRepository.findByDescripcion(descripcion)
                .orElseGet(() -> {
                    Rol rol = new Rol();
                    rol.setDescripcion(descripcion);
                    return rolRepository.save(rol);
                });
    }

    private void createEmpleado(String nombre, String apellido, String correo, String password, Rol rol) {
        if (empleadoRepository.findByCorreo(correo).isEmpty()) {
            Empleado e = new Empleado();
            e.setNombre(nombre);
            e.setApellido(apellido);
            e.setCorreo(correo);
            e.setPassword(passwordEncoder.encode(password));
            e.setRol(rol);
            empleadoRepository.save(e);
        }
    }

    private void createCategoria(String nombre, String descripcion) {
        if (categoriaRepository.findByNombre(nombre).isEmpty()) {
            Categoria c = new Categoria();
            c.setNombre(nombre);
            c.setDescripcion(descripcion);
            categoriaRepository.save(c);
        }
    }

    private void createProveedor(String nombre, String telefono, String direccion) {
        if (proveedorRepository.findByNombre(nombre).isEmpty()) {
            Proveedor p = new Proveedor();
            p.setNombre(nombre);
            p.setTelefono(telefono);
            p.setDireccion(direccion);
            proveedorRepository.save(p);
        }
    }

    private void createProducto(String nombre, Double precio, Integer stock, Integer stockMinimo,
                                 Categoria categoria, Proveedor proveedor, String imagenUrl) {
        if (productRepository.findAll().stream().noneMatch(p -> p.getNombre().equals(nombre))) {
            Product p = new Product();
            p.setNombre(nombre);
            p.setPrecio(precio);
            p.setStock(stock);
            p.setStockMinimo(stockMinimo);
            p.setCategoria(categoria);
            p.setProveedor(proveedor);
            p.setImagenUrl(imagenUrl);
            productRepository.save(p);
        }
    }
}