package com.serenitydojo;

import java.util.*;

public class Catalog {
    Map<Fruit, Double> fruitToUnitPrice = new HashMap<Fruit, Double>();
    List<CatalogItem> availableFruits = new ArrayList<CatalogItem>();
    List<Fruit> fruitsEligibleForDiscount = new ArrayList<Fruit>();

    public Catalog(){
        fruitToUnitPrice.put(Fruit.Apple, 4.00);
        fruitToUnitPrice.put(Fruit.Orange, 5.50);
        fruitToUnitPrice.put(Fruit.Banana, 6.00);
        fruitToUnitPrice.put(Fruit.Pear, 4.50);
        fruitToUnitPrice.put(Fruit.Grapefruit, 7.00);
    }

    public static Catalog withItems(CatalogItem... catalogItems) {
        //TODO, create catalog and add items to the list of available fruits - Done
        Catalog catalog = new Catalog();
        for (CatalogItem catalogItem : catalogItems) {
            catalog.availableFruits.add(catalogItem);
        }
        return catalog;
    }

    public List<CatalogItem> getAvailableFruits(){
        //Caution: Mutates the availableFruits List
        availableFruits.sort(Comparator.comparing(catalogItem -> catalogItem.getFruit().name()));
        return availableFruits;

 /*    //    Alternate and preferred solution irl apps: sort on streams instead and produce a new sorted List instead (less dangerous vs mutating)
         //     Some other part of app could potentially want the list sorted in a different way
                List<CatalogItem> availableFruitsSorted =
                availableFruits.stream()
                .sorted(Comparator.comparing(catalogItem -> catalogItem.getFruit()))
                .collect(Collectors.toList());

       return availableFruitsSorted;
        return availableFruits;*/
    }
    public void setUnitPriceOf(Fruit fruit, double unitPrice) {
        fruitToUnitPrice.put(fruit, unitPrice);
    }

    public Double getUnitPriceOf(Fruit fruit) {
        return fruitToUnitPrice.get(fruit);
    }

    public Double calculateFruitPriceForQuantity(Fruit fruit, Integer quantityInKilos) {
        Double price = getUnitPriceOf(fruit);
        return quantityInKilos * price;
    }

    public Boolean checkFruitAvailability(Fruit fruit) throws FruitUnavailableException {
       Boolean isFruitAvailable = false;

       isFruitAvailable = availableFruits.stream()
                .anyMatch(catalogItem -> catalogItem.getFruit() == fruit);

       if(!isFruitAvailable){
           throw new FruitUnavailableException("This fruit is not currently available: " + fruit);
       }
       return true;
    }

    public ShoppingCart grabShoppingCart(){
        ShoppingCart cart = new ShoppingCart(this);
        return cart;
    }

}
