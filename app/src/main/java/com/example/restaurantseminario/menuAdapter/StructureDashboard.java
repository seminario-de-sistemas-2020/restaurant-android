package com.example.restaurantseminario.menuAdapter;

    public class StructureDashboard {


        private String name;
        private String idMenu;
        private String idRestaurant;
        private String urlPhotoProducto;
        private double precio;
        private  String description;


        public String getName(){
            return name;
        }
        public String getIdMenu(){
            return idMenu;
        }
        public String getIidRestaurant(){
            return idRestaurant;
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
        public void setIdMenu(String idMenu){
            this.idMenu = idMenu;
        }
        public void setIdRestaurant(String idRestaurant){
            this.idRestaurant = idRestaurant;
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
