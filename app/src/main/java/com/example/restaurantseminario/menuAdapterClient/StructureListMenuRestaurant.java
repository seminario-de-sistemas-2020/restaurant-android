package com.example.restaurantseminario.menuAdapterClient;

public class StructureListMenuRestaurant {

    private String name;
    private String urlPhotoProducto;
    private double precio;
    private  String description;


    public String getName(){
        return name;
    }
    public String getUrlPhotoProducto(){
        return urlPhotoProducto;
    }
    public Double getPrecio(){
        return precio;
    }
    public String getDescription(){
        return description;
    }


    public void setName(String name){
        this.name = name;
    }
    public void setUrlPhotoProducto(String url){
        this.urlPhotoProducto = url;
    }
    public  void setPrecio(double precio){
        this.precio = precio;
    }
    public void setDescription(String description){
        this.description = description;
    }
}
