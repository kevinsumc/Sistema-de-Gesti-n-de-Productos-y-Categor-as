package com.kevin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.kevin.dto.CategoriaDto;
import com.kevin.model.CategoriaModel;
import com.kevin.repository.CategoriaRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/index")
    public String index(Model model) {
        List<CategoriaModel> categorias = categoriaRepository.findAll();
        model.addAttribute("categorias", categorias);
        return "categoria/index";
    }

    @GetMapping("/nova")
public String novaCategoriaForm(Model model) {
    model.addAttribute("categoria", new CategoriaDto());  // Asegúrate de pasar un objeto CategoriaDto
    return "categoria/inserir"; // Cambia el nombre para que apunte a inserir.html
}


@PostMapping
public String inserir(@ModelAttribute CategoriaDto categoriaDto) {
    CategoriaModel categoria = new CategoriaModel(categoriaDto.getDescripcion());
    categoriaRepository.save(categoria);
    return "redirect:/categoria/index";
}


    @GetMapping("/editar/{id}")
    public String editarCategoriaForm(@PathVariable Long id, Model model) {
        Optional<CategoriaModel> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            model.addAttribute("categoria", categoria.get());
            return "categoria/index"; //reutilizamos el formulario para edicion
        } else {
            return "redirect:/categoria/index";
        }
    }

    /**
     * @param id
     * @param categoriaDto
     * @return
     */
    @PostMapping("/editar/{id}")
    public String editar(@PathVariable Long id, @ModelAttribute CategoriaDto categoriaDto) {
        Optional<CategoriaModel> optionalCategoria = categoriaRepository.findById(id);
        if (optionalCategoria.isPresent()) {
            CategoriaModel categoria = optionalCategoria.get();
            categoriaRepository.save(categoria);
        }
        return "redirect:/categoria";
    }

     // Mostrar el formulario para eliminar una categoría existente
     @GetMapping("/eliminar/{id}")
     public String eliminarForm(@PathVariable("id") Long id, Model model) {
         CategoriaModel categoria = categoriaRepository.findById(id)
             .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
         model.addAttribute("categoria", categoria);
         return "categoria/eliminar";
     }
 
     // Procesar el formulario para eliminar una categoría existente
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            // Primero, asegúrate de que los productos asociados sean manejados
            // productoRepository.deleteByCategoriaId(id);

            // Luego elimina la categoría
            categoriaRepository.deleteById(id);

            return "redirect:/categoria/index";
        } catch (DataIntegrityViolationException e) {
            // Manejar la excepción y redirigir con un mensaje de error
            redirectAttributes.addFlashAttribute("errorMessage", "No se puede eliminar la categoría porque está siendo utilizada.");
            return "redirect:/categoria/index";
        }
    }
    @GetMapping("/{id}")
    public String retornarUm(@PathVariable Long id, Model model) {
        Optional<CategoriaModel> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            model.addAttribute("categoria", categoria.get());
            return "categorias/detalhes";
        } else {
            return "redirect:/categorias";
        }
    }
}

