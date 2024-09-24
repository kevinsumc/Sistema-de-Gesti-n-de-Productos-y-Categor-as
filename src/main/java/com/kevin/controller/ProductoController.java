package com.kevin.controller;

import com.kevin.model.ProductoModel;
import com.kevin.model.CategoriaModel;
import com.kevin.repository.ProductoRepository;
import com.kevin.repository.CategoriaRepository;

import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.domain.JpaSort.Path;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.spring6.expression.Fields;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Mostrar todos los productos
    @GetMapping("/listar")
    public String listar(Model model) {
        List<ProductoModel> productos = productoRepository.findAll();
        model.addAttribute("productos", productos);
        return "producto/listar";
    }

    // Mostrar el formulario para agregar un nuevo producto
    @GetMapping("/inserir")
    public String crearForm(Model model) {
        model.addAttribute("producto", new ProductoModel());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "producto/inserir";
    }

    // Procesar el formulario para agregar un nuevo producto
@PostMapping("/inserir")
public String crear(@ModelAttribute ProductoModel producto,
@RequestParam("file") MultipartFile imagen,
 @RequestParam("id_categoria") Long idCategoria) {
    // Buscar la categoría por su id
    CategoriaModel categoria = categoriaRepository.findById(idCategoria)
        .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + idCategoria));
    // Asignar la categoría al producto
    producto.setCategoria(categoria);

    

    try {
        if (!imagen.isEmpty()) {
            byte[] bytes = imagen.getBytes();
            Path camino = Paths.get(
                "src/main/resources/static/img/"+imagen.getOriginalFilename());
            Files.write(camino, bytes);
            producto.setImagen(imagen.getOriginalFilename());
            
        }
    } catch (Exception e) {
        // TODO: handle exception
        System.out.println("Error Imagen");
    }
    // Guardar el producto en la base de datos
    productoRepository.save(producto);

    return "redirect:/producto/listar";
}
    // Procesar el formulario para agregar un nuevo producto
    //@PostMapping("/inserir")
    //public String crear(@ModelAttribute ProductoModel producto) {
      //  productoRepository.save(producto);
        //return "redirect:/producto/listar";
    //}

    // Mostrar el formulario para editar un producto existente
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable("id") Long id, Model model) {
        ProductoModel producto = productoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "producto/editar";
    }

    // Procesar el formulario para actualizar un producto existente
@PostMapping("/editar/{id}")
public String editar(@PathVariable("id") Long id, 
                     @ModelAttribute ProductoModel producto, 
                     @RequestParam("id_categoria") Long idCategoria) {
    // Buscar la categoría correspondiente por su id
    CategoriaModel categoria = categoriaRepository.findById(idCategoria)
            .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + idCategoria));

    // Asignar la categoría al producto
    producto.setCategoria(categoria);

    // Asegurarse de que el ID del producto es el correcto
    producto.setId(id);

    // Guardar el producto actualizado
    productoRepository.save(producto);

    return "redirect:/producto/listar";
}


    // Eliminar un producto
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id) {
        productoRepository.deleteById(id);
        return "redirect:/producto/listar";
    }
}
