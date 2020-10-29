package com.example.restaurantseminario.utils;

public class DataServer {
                                //http://192.168.100.97:9000/api/v0.1/
    public static String HOST ="http://192.168.100.97:9000/api/v0.1/";

    public  static String HOST_LOGIN = HOST + "user/login/";
    public static  String HOST_NEW_USER = HOST + "user/new";
    public static String HOST_IMAGE_AVATAR = HOST + "user/upload/avatar/file=avatar/iduser=";
    public static String HOST_LIST_ALL_RESTAURANTS = HOST + "restaurant/list/all";
    public static String HOST_SHOW_IDRESTAURANT_OWNER = HOST +  "restaurant/list/for/idowner=";
    public static String HOST_NEW_MENU_IDRESTAURANTE = HOST + "menu/create/idrestaurant=";
    public static String HOST_LIST_MENU_IDRESTAURANTE = HOST + "menu/show/list/all/idrestaurant=";

    // restaurante
    public static String HOST_CREATE_REGISTRAR_RESTAURANT = HOST + "restaurant";
    public static String HOST_UPLOAD_LOGO = HOST+"restaurant/upload/file=logo/idrestaurant=";
    public static String HOST_UPLOAD_FOTO_LUGAR = HOST+"restaurant/upload/file=fotolugar/idrestaurant=";

    // realizar ordenes
    public static String HOST_ADD_MENU_TEMPORAL = HOST + "order/add/temporal/idClient=";
    public static String HOST_LIST_ALL_ORDENES_USER = HOST + "order/show/all/list/idClient=";
    public static String HOST_CANCELAR_ORDEN = HOST + "order/cancelar/idOrden=";
    public static String HOST_UPDATE_CANTIDAD_PRODUCTO = HOST + "order/update/producto/cantidad/idOrdenTemporal=";
    public static String HOST_GET_TOTALPRECIOCANTIDAD = HOST + "order/calcular/preciototal/idclient=";

    // Menus
    public static String HOST_CREAR_NUEVO_MENU = HOST + "menu/create/idrestaurant=";
    public static String HOST_UPLOAD_IMAGE_MENU = HOST + "menu/upload/fotoproduct/file=fotoproducto/idmenu=";
    public static String HOST_MENU_UPDATE_DATA = HOST + "menu/update/data/idmenu=";

}
