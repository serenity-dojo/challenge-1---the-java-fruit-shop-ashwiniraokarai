package com.serenitydojo;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    /*Ash to reviewer: when adding items to shopping cart, should you add catalog item? or fruit?
    * */
    Catalog catalog;
    Map<Fruit, Integer> fruitToQuantityMap;

    Double discountPercentage = 0.10;


    public ShoppingCart(Catalog catalog) {
        this.catalog = catalog;
        fruitToQuantityMap = new HashMap<>();
    }

// This method adds item (Fruit) and marks it for Discount if applicable, to apply to total later
// Ash to reviewer: When adding items to cart, I thought it made sense to add the Fruit directly as opposed to adding a CatalogItem.
//What are your thoughts?
    public void addItem(Fruit fruit, Integer quantityInKilos) {
        fruitToQuantityMap.put(fruit, quantityInKilos);
        if(catalog.checkEligibilityForDiscount(quantityInKilos)) {
            catalog.markFruitEligibleForDiscount(fruit);
        }
    }

    public Map<Fruit, Integer> showItemsInCart() {
        System.out.println("Items in shopping cart: " + fruitToQuantityMap);
        return fruitToQuantityMap;
    }

    public Double getTotalPriceOfCartBeforeDiscounts() throws FruitUnavailableException {
        Double totalPrice = 0.0;
        Double totalPriceAfterDiscount = 0.0;
        for (Map.Entry<Fruit, Integer> entry : fruitToQuantityMap.entrySet()) {
            Fruit fruit = entry.getKey();
            Integer quantityInKilos = entry.getValue();
            totalPrice = totalPrice + catalog.calculateFruitPriceForQuantity(fruit, quantityInKilos);
        }

        System.out.println("Total price before discount from method getTotalPriceOfCartBeforeDiscounts : " + totalPrice);
        return totalPrice;
    }

    public Double getTotalPriceOfCartAfterDiscounts() throws FruitUnavailableException {
        Double totalPriceAfterDiscount = 0.0;
        Double totalPriceBeforeDiscount = getTotalPriceOfCartBeforeDiscounts();

        //E.g: 18 = 20 - (20*0.1)
        totalPriceAfterDiscount =  totalPriceBeforeDiscount - getDiscountOffer(totalPriceBeforeDiscount);
        System.out.println("Total price after discount from method getTotalPriceOfCartAfterDiscounts : " + totalPriceAfterDiscount);

        return totalPriceAfterDiscount;
    }

    public Double getDiscountOffer(Double totalPriceBeforeDiscounts){
        Double discountAmount = 0.0;
        //if there's one or more fruits in the eligible List for discount, then apply 10% discount on the total cart price
        if(catalog.fruitsEligibleForDiscount.size() >= 1) {
            discountAmount = totalPriceBeforeDiscounts * discountPercentage;
        }
        System.out.println("Discount on total price from method applyAnyDiscounts : " + discountAmount);
        return discountAmount;
    }

//Ash: Ended up moving this method to Catalog
//    public Double calculateFruitPriceForQuantity(Fruit fruit, Integer quantityInKilos) throws FruitUnavailableException{
//        Double price = catalog.getUnitPriceOf(fruit);
//        return quantityInKilos * price;
//    }
}
