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
        //throw new RuntimeException("TODO, create catalog and add items to the list of available fruits"); - Done
        Catalog catalog = new Catalog();
        for (CatalogItem catalogItem : catalogItems) {
            catalog.availableFruits.add(catalogItem);
        }
        return catalog;
    }

    public List<CatalogItem> getAvailableFruits(){
        //Ash to reviewer: Which one's preferred? Mutate the availableFruits List as I did here OR sort on streams instead and produce a new sorted List
        availableFruits.sort(Comparator.comparing(catalogItem -> catalogItem.getFruit().name()));
        return availableFruits;

        //        Alternate solution: sort on streams instead and produce a new sorted List
        //        List<CatalogItem> availableFruitsSorted =
//                availableFruits.stream()
//                .sorted(Comparator.comparing(catalogItem -> catalogItem.getFruit()))
//                .collect(Collectors.toList());
//
//       return availableFruitsSorted;
//        return availableFruits;
    }
    public void setUnitPriceOf(Fruit fruit, double unitPrice) {
        //Ash: Commenting the exception now that I've created the map
        //throw new RuntimeException("TODO, create a map to keep track of fruits and their prices");

        // Ash to reviewer: To get the following preexisting line of code to work, I'd have to change the data type of the Map's key from Fruit to String
        // fruitToPrice.put(fruit.name(), unitPrice);

        fruitToUnitPrice.put(fruit, unitPrice);
    }

    public Double getUnitPriceOf(Fruit fruit) throws FruitUnavailableException{
        Boolean isFruitAvailable = false;

        // Ash to reviewer: Not sure in response to what exactly we wanted the "FruitUnavailableException", so I included it here too
        checkFruitAvailability(fruit);
        return fruitToUnitPrice.get(fruit);
    }

    public Double calculateFruitPriceForQuantity(Fruit fruit, Integer quantityInKilos) throws FruitUnavailableException{
        Double price = getUnitPriceOf(fruit);
        return quantityInKilos * price;
    }

    public Boolean checkEligibilityForDiscount(Integer quantityInKilos){
        if (quantityInKilos >= 5){
            return true;
        }
        return false;
    }

    public void markFruitEligibleForDiscount(Fruit fruit){
        fruitsEligibleForDiscount.add(fruit);
    }

    public Boolean checkFruitAvailability(Fruit fruit) throws FruitUnavailableException {
       Boolean isFruitAvailable = false;

// Ash: Originally wrote code using for-each loop but refactored later below to use stream instead
//        for(CatalogItem catalogItem : availableFruits){
//            if(catalogItem.getFruit() == fruit){
//                isFruitAvailable = true;
//            }
//        }
//
       isFruitAvailable = availableFruits.stream()
                .anyMatch(catalogItem -> catalogItem.getFruit() == fruit);

       if(isFruitAvailable == false){
           throw new FruitUnavailableException("This fruit is not currently available: " + fruit);
       }
       return true;
    }

    public ShoppingCart grabShoppingCart(){
        ShoppingCart cart = new ShoppingCart(this);
        return cart;
    }

}
