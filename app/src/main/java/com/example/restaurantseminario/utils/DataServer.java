package com.example.restaurantseminario.utils;

public class DataServer {
                                //http://192.168.100.97:9000/api/v0.1/
    public static String HOST ="http://192.168.100.97:9000/api/v0.1/";
    public  static String HOST_LOGIN = HOST + "user/login/";
    public static  String HOST_NEW_USER = HOST + "user/new";
    public static String HOST_IMAGE_AVATAR = HOST + "user/upload/avatar/file=avatar/iduser=";
    public static String HOST_LIST_ALL_RESTAURANTS = HOST + "restaurant/list/all";
    public static String HOST_NEW_MENU_IDRESTAURANTE = HOST + "menu/create/idrestaurant=";
    public static String HOST_LIST_MENU_IDRESTAURANTE = HOST + "menu/show/list/all/idrestaurant=";


}
