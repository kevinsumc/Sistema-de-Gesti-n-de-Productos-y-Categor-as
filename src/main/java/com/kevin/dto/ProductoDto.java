package com.kevin.dto;

public class ProductoDto {

    private Long id;
    private String nombre;
    private String precio;
    private int id_categoria; // Puedes usar Long o String dependiendo de cómo manejes las IDs en tu aplicación

    // Constructor vacío
    public ProductoDto() {
    }

    // Constructor con parámetros
    public ProductoDto(Long id, String nombre, String precio, int id_categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.id_categoria = id_categoria;
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

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

   
}

