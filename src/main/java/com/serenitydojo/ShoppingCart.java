package com.serenitydojo;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    Catalog catalog;
    Map<Fruit, Integer> fruitToQuantityMap;
    Double discountPercentage = 0.10;
    //moving the discount logic to cart instead
    boolean discounted = false;

    public ShoppingCart(Catalog catalog) {
        this.catalog = catalog;
        fruitToQuantityMap = new HashMap<>();
    }

// This method adds item (Fruit) and flags the cart for Discount (if applicable) while at it
    public void addItem(Fruit fruit, Integer quantityInKilos) {
        fruitToQuantityMap.put(fruit, quantityInKilos);
        if(quantityInKilos >= 5){
            discounted = true;
            System.out.println("Discounted set to true because of: " + fruit.name() + " with quantity: " + quantityInKilos);
        }
    }

    public Map<Fruit, Integer> showItemsInCart() {
        System.out.println("Items in shopping cart: " + fruitToQuantityMap);
        return fruitToQuantityMap;
    }

    public Double getTotalPriceOfCartBeforeDiscounts()  {
        Double totalPrice = 0.0;

        for (Map.Entry<Fruit, Integer> entry : fruitToQuantityMap.entrySet()) {
            Fruit fruit = entry.getKey();
            Integer quantityInKilos = entry.getValue();
            totalPrice = totalPrice + catalog.calculateFruitPriceForQuantity(fruit, quantityInKilos);
        }

        System.out.println("Total price before discount from method getTotalPriceOfCartBeforeDiscounts : " + totalPrice);
        return totalPrice;
    }

    public Double getTotalPriceOfCartAfterDiscounts()  {
        Double totalPriceBeforeDiscount = getTotalPriceOfCartBeforeDiscounts();
        Double totalPriceAfterDiscount = totalPriceBeforeDiscount;

        if (discounted){
            //E.g: 18 = 20 - (20*0.1)
            totalPriceAfterDiscount =  totalPriceBeforeDiscount - getDiscountOffer(totalPriceBeforeDiscount);
            System.out.println("Total price after discount from method getTotalPriceOfCartAfterDiscounts : " + totalPriceAfterDiscount);
        } else {
            System.out.println("No discounts: " + totalPriceAfterDiscount);
        }
        return totalPriceAfterDiscount;
    }

    public Double getDiscountOffer(Double totalPriceBeforeDiscounts){
        Double discountAmount = 0.0;

        discountAmount = totalPriceBeforeDiscounts * discountPercentage;
        System.out.println("Discount on total price from method applyAnyDiscounts : " + discountAmount);
        return discountAmount;
    }
}
