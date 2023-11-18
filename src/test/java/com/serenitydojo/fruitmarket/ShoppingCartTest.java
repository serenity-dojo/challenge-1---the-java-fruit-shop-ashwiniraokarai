package com.serenitydojo.fruitmarket;

import com.serenitydojo.Catalog;
import com.serenitydojo.CatalogItem;
import com.serenitydojo.Fruit;
import com.serenitydojo.ShoppingCart;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.serenitydojo.Fruit.*;

public class ShoppingCartTest {
    Catalog catalog;
    @Before
    public void setUp(){
        catalog = CatalogTestDataHelper.createNewCatalogWithItems();
    }

    @Test
    public void canAddItemsToShoppingCart() {
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
    public void shouldTrackRunningTotalOfShoppingCart() {
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
    public void shouldApplyDiscountedPriceOnCartTotal()  {
        ShoppingCart cart = catalog.grabShoppingCart();
        cart.addItem(Apple, 5);

        Assertions.assertThat(cart.getTotalPriceOfCartAfterDiscounts()).isEqualTo(18);
    }

    @Test
    //Ash: testing discounted total on 2 items in the cart one of which satisfies the discount criteria
    public void shouldApplyDiscountedPriceOnCartTotalWithTwoItems()  {
        ShoppingCart cart = catalog.grabShoppingCart();
        cart.addItem(Apple, 5);
        cart.addItem(Orange, 2);

        Assertions.assertThat(cart.getTotalPriceOfCartAfterDiscounts()).isEqualTo(27.9);
    }

    @Test
    //Ash: testing discounted total on multiple items in the cart some of which satisfy the discount criteria
    public void shouldApplyDiscountedPriceOnCartTotalWithMultipleItems()  {

        ShoppingCart cart = catalog.grabShoppingCart();
        cart.addItem(Apple, 5);
        cart.addItem(Orange, 2);
        cart.addItem(Banana, 5);
        cart.addItem(Pear, 6);

        Assertions.assertThat(cart.getTotalPriceOfCartAfterDiscounts()).isEqualTo(79.2);
    }

    @Test
    //Ash: testing that discount is not applied to the cart with items that do not qualify for discount
    public void shouldNotApplyDiscountedPriceOnCartTotal()  {
        ShoppingCart cart = catalog.grabShoppingCart();
        cart.addItem(Apple, 4);
        cart.addItem(Banana, 3);

        Assertions.assertThat(cart.getTotalPriceOfCartAfterDiscounts()).isEqualTo(34);
    }
}
