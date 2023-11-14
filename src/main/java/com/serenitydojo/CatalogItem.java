package com.serenitydojo;

public class CatalogItem {

    private final Fruit fruit;

    public CatalogItem(Fruit fruit) {
        this.fruit = fruit;
    }

    public Fruit getFruit(){
        return fruit;
    }

    @Override
    public String toString(){
        return getFruit().name();
    }
}
