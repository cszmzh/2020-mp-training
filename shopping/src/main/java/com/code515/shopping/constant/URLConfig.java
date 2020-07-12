package com.code515.shopping.constant;

public class URLConfig {

    /*服务器或本机真实资源绝对路径*/
    private static String realURL = "/media/static/";
    //private static String realURL = "/Users/ming/Desktop/小程序开发/static/";

    public static String getRealURL() {
        return realURL;
    }

    public static void setRealURL(String realURL) {
        URLConfig.realURL = realURL;
    }
}
