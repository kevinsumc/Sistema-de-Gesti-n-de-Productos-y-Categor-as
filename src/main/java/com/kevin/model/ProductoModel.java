package com.kevin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "producto")
public class ProductoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  // Mapea la columna "id" como clave primaria
    private Long id;

    @Column(name = "nombre")  // Añadir nombre a la columna si es necesario
    private String nombre;

    @Column(name = "precio")  // Añadir nombre a la columna si es necesario
    private String precio;

    @ManyToOne
    @JoinColumn(name = "id_categoria")  // Relación con la tabla de categorías
    private CategoriaModel categoria;

    // Constructor vacío
    public ProductoModel() {
    }

    // Constructor con parámetros
    public ProductoModel(String nombre, String precio, CategoriaModel categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public CategoriaModel getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaModel categoria) {
        this.categoria = categoria;
    }
}
