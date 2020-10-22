package com.example.restaurantseminario.menuAdapterClient;

public class StructureListMenuRestaurant {

    private String idMenu;
    private String idRestaurant;
    private String name;
    private String urlPhotoProducto;
    private double precio;
    private  String description;



    public String getIdMenu(){
        return idMenu;
    }
    public  String getIdRestaurant(){
        return idRestaurant;
    }
    public String getName(){
        return name;
    }
    public String getUrlPhotoProducto(){
        return urlPhotoProducto;
    }
    public double getPrecio(){
        return precio;
    }
    public String getDescription(){
        return description;
    }


    public void setIdMenu(String idMenu){
        this.idMenu = idMenu;
    }
    public void setIdRestaurant(String idRestaurant){
        this.idRestaurant= idRestaurant;
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
