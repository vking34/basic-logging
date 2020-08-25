package com.example.logproducer.models;

public enum Platform {
    IOS,
    ANDROID,
    LINUX,
    WINDOWS,
    MAC,
    UNKNOWN;

    public static Platform getPlatform(String userAgent){
        if (userAgent == null || userAgent.isEmpty())
            return UNKNOWN;
        userAgent = userAgent.toLowerCase();
        if (userAgent.contains("windows"))
            return WINDOWS;
        else if (userAgent.contains("iphone"))
            return IOS;
        else if (userAgent.contains("mac"))
            return MAC;
        else if (userAgent.contains("android"))
            return ANDROID;
        else if (userAgent.contains("x11"))
            return LINUX;
        else
            return UNKNOWN;
    }
}
