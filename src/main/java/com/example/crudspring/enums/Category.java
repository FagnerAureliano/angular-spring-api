package com.example.crudspring.enums;

public enum Category {

    WEB_DEVELOPMENT("Web Development"), MOBILE_DEVELOPMENT("Mobile Development");

    private String value;

    private Category(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

}
