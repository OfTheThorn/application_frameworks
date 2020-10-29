package com.matthiasvdd.opdracht.models;

public enum CategoryEnum {
    GIN("G"), RUM("R"), WHISKEY("W");

    private String code;

    CategoryEnum(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
