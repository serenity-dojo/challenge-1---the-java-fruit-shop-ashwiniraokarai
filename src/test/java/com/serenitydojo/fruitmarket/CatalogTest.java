package com.serenitydojo.fruitmarket;

import com.serenitydojo.*;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.serenitydojo.Fruit.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CatalogTest {
    Catalog catalog;
    @Before
    public void addItemsToCatalog(){
            catalog = Catalog.withItems(
            new CatalogItem(Pear),
            new CatalogItem(Apple),
            new CatalogItem(Banana),
            new CatalogItem(Orange)
        );
    }

    @Test
    public void shouldBeAbleToUpdateTheCurrentPriceOfAFruit() throws FruitUnavailableException {
// TODO: Uncomment this code and make it work - Done
  //       Catalog catalog = new Catalog();
         catalog.setUnitPriceOf(Apple, 4.00);
         assertThat(catalog.getUnitPriceOf(Apple)).isEqualTo(4.00);
    }

    @Test
    public void shouldListAvailableFruitsAlphabetically() {
// TODO: Uncomment this code and make it work - Done
         List<CatalogItem> availableFruits = catalog.getAvailableFruits();
         System.out.println("Available fruits in alphabetical order: " + availableFruits);
         assertThat(availableFruits.get(0).getFruit()).isEqualTo(Apple);
         assertThat(availableFruits.get(1).getFruit()).isEqualTo(Banana);
         assertThat(availableFruits.get(2).getFruit()).isEqualTo(Orange);
         assertThat(availableFruits.get(3).getFruit()).isEqualTo(Pear);
    }

    @Test(expected = FruitUnavailableException.class )
    public void shouldReportFruitPrice() throws FruitUnavailableException{
        Assertions.assertThat(catalog.getUnitPriceOf(Apple)).isEqualTo(4.00);
        Assertions.assertThat(catalog.getUnitPriceOf(Orange)).isEqualTo(5.50);
        Assertions.assertThat(catalog.getUnitPriceOf(Banana)).isEqualTo(6.00);
        Assertions.assertThat(catalog.getUnitPriceOf(Pear)).isEqualTo(4.50);
        Assertions.assertThat(catalog.getUnitPriceOf(Grapefruit)).isEqualTo(4.50);
    }

    @Test(expected = FruitUnavailableException.class)
    public void throwsExceptionIfFruitIsUnavailable() throws FruitUnavailableException {
        catalog.checkFruitAvailability(Orange);

        //Ash: throws the expected exception because Grapefruit is not in the Catalog
        //The exception is hidden away because it is marked as "expected" via the annotation above this Test method's signature
            catalog.checkFruitAvailability(Grapefruit);
    }

    @Test
    public void noExceptionIfFruitIsAvailable() throws FruitUnavailableException {
        catalog.checkFruitAvailability(Apple);
    }

    @Test
    //Ash: test that pricing calculation is correct when quantity > 1 kilo
    public void shouldCalculatePerFruitPriceForMultipleUnits() throws FruitUnavailableException {
        Double fruitPriceForQuantity = catalog.calculateFruitPriceForQuantity(Apple, 2);
        Assertions.assertThat(fruitPriceForQuantity).isEqualTo(8);
    }

    @Test
    public void canAddItemsToShoppingCart() throws FruitUnavailableException {
        ShoppingCart cart = catalog.grabShoppingCart();

        cart.addItem(Apple, 1);
        cart.addItem(Orange, 2);
        cart.addItem(Banana, 3);

        Map<Fruit, Integer> actualItemsInCart = cart.showItemsInCart();

        Map<Fruit, Integer> expectedItemsInCart = new HashMap<Fruit, Integer>();
        expectedItemsInCart.put(Apple, 1);
        expectedItemsInCart.put(Orange, 2);
        expectedItemsInCart.put(Banana, 3);

        Assertions.assertThat(actualItemsInCart).containsAllEntriesOf(expectedItemsInCart);
    }

    @Test
    //Ash: testing that total is calculated accurately before checking any applicable discounts
    public void shouldTrackRunningTotalOfShoppingCart() throws FruitUnavailableException{

        ShoppingCart cart = catalog.grabShoppingCart();
        cart.addItem(Apple, 5);
        cart.addItem(Banana, 2);
        cart.addItem(Pear, 5);
        cart.addItem(Orange, 6);

        Double expectedTotalPriceOfCart = catalog.getUnitPriceOf(Apple) * 5 +
                catalog.getUnitPriceOf(Banana) * 2 +
                catalog.getUnitPriceOf(Pear) * 5 +
                catalog.getUnitPriceOf(Orange) * 6;

        Assertions.assertThat(cart.getTotalPriceOfCartBeforeDiscounts()).isEqualTo(expectedTotalPriceOfCart);
    }

    @Test
    //Ash: testing discounted total on a single item in the cart that satisfies the discount criteria
    public void shouldApplyDiscountedPriceOnCartTotal() throws FruitUnavailableException {
        ShoppingCart cart = catalog.grabShoppingCart();
        cart.addItem(Apple, 5);

       Assertions.assertThat(cart.getTotalPriceOfCartAfterDiscounts()).isEqualTo(18);
    }

    @Test
    //Ash: testing discounted total on 2 items in the cart one of which satisfies the discount criteria
    public void shouldApplyDiscountedPriceOnCartTotalWithTwoItems() throws FruitUnavailableException {
        ShoppingCart cart = catalog.grabShoppingCart();
        cart.addItem(Apple, 5);
        cart.addItem(Orange, 2);

        Assertions.assertThat(cart.getTotalPriceOfCartAfterDiscounts()).isEqualTo(27.9);
    }

@Test
    //Ash: testing discounted total on multiple items in the cart some of which satisfy the discount criteria
    public void shouldApplyDiscountedPriceOnCartTotalWithMultipleItems() throws FruitUnavailableException {

        ShoppingCart cart = catalog.grabShoppingCart();
        cart.addItem(Apple, 5);
        cart.addItem(Orange, 2);
        cart.addItem(Banana, 5);
        cart.addItem(Pear, 6);

        Assertions.assertThat(cart.getTotalPriceOfCartAfterDiscounts()).isEqualTo(79.2);
    }

    @After
    public void clearCatalog(){
        //Ash to reviewer: It looks like the catalog object is getting reset between tests on its own.
        //Is it good practice to reset it explicitly? If yes, how exactly do you reset an object?
    }
}
