package com.example.restaurantseminario.ordenesAdapter;

public class StructureDataOrdenes {

    private String results;
    private String idOrden;
    private String idMenu;
    private String nameMenu;
    private double precioUnitario=0.0;
    private String photoProducto;
    private double cantidad=0.0;
    private double precioCantidadTotal=0.0;


    public String getResults(){
        return results;
    }
    public String getIdOrden(){
        return idOrden;
    }
    public String getIdMenu(){
        return idMenu;
    }
    public String getNameMenu(){
        return nameMenu;
    }
    public double getPrecioUnitario(){
        return precioUnitario;
    }
    public String getPhotoProducto(){
        return photoProducto;
    }
    public double getCantidad(){
        return cantidad;
    }
    public double getPrecioCantidadTotal(){
        return precioCantidadTotal;
    }


    public void setResults(String results){
        this.results = results;
    }
    public void setIdOrden(String orden){
        this.idOrden = orden;
    }
    public void setNameMenu(String nameMenu){
        this.nameMenu = nameMenu;
    }
    public void setPrecioUnitario(double precioUnitario){
        this.precioUnitario = precioUnitario;
    }
    public void setPhotoProducto(String photoProducto){
        this.photoProducto = photoProducto;
    }
    public void setCantidad(double cantidad){
        this.cantidad = cantidad;
    }
    public void setPrecioCantidadTotal(double precioCantidadTotal){
        this.precioCantidadTotal = precioCantidadTotal;
    }


}
