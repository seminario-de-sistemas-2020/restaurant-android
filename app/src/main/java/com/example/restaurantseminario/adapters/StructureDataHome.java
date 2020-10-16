package com.example.restaurantseminario.adapters;

public class StructureDataHome {

    private String nombre;
    private String id;
    private String idClient;
    private String nit;
    private String propietario;
    private String calle;
    private Number telefono;
    private double log;
    private double lat;
    private String logo;
    private String fotoLugar;

    public String getNombre(){
        return nombre;
    }
    public String getId(){
        return id;
    }
    public String getIdClient(){
        return idClient;
    }
    public String getNit(){
        return nit;
    }
    public String getPropietario(){
        return propietario;
    }
    public String getCalle(){
        return calle;
    }
    public Number getTelefono(){
        return telefono;
    }
    public double getLog(){
        return log;
    }
    public double getLat(){
        return lat;
    }
    public String getLogo(){
        return logo;
    }
    public String getFotoLugar(){
        return fotoLugar;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setId(String id){
        this.id = id;
    }
    public  void setIdClient(String idClient){
        this.idClient = idClient;
    }
    public void setNit(String nit){
        this.nit = nit;
    }
    public void setPropietario(String propietario){
        this.propietario = propietario;
    }
    public void setCalle(String calle){
        this.calle = calle;
    }
    public void setTelefono(Number telefono){
        this.telefono = telefono;
    }
    public void setLog(double log){
        this.log = log;
    }
    public void setLat(Double lat){
        this.lat = lat;
    }
    public void setLogo(String logo){
        this.logo = logo;
    }
    public void setFotoLugar(String fotoLugar){
        this.fotoLugar = fotoLugar;
    }
}
