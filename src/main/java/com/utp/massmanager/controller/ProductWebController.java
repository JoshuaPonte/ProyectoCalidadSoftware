package com.utp.massmanager.controller;

import com.utp.massmanager.model.Product;
import com.utp.massmanager.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductWebController {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<Product> listProducts = productService.getAllProducts();
        model.addAttribute("listProducts", listProducts);
        
        if (!model.containsAttribute("product")) {
            model.addAttribute("product", new Product());
        }
        return "index";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@Valid @ModelAttribute("product") Product product, 
                              BindingResult result, 
                              Model model) {
        if (result.hasErrors()) {
            List<Product> listProducts = productService.getAllProducts();
            model.addAttribute("listProducts", listProducts);
            model.addAttribute("showModal", true);
            return "index";
        }
        
        // createProduct maneja tanto inserciones como actualizaciones según si el ID ya existe
        productService.createProduct(product);
        return "redirect:/";
    }

    // Ruta para cargar los datos en el modal de edición
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {
        Product product = productService.getProductById(id);
        List<Product> listProducts = productService.getAllProducts();
        
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("product", product);
        model.addAttribute("showModal", true); // Abre el modal automáticamente con los datos cargados
        return "index";
    }

    // Ruta para eliminar un producto
    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable(value = "id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }
}
