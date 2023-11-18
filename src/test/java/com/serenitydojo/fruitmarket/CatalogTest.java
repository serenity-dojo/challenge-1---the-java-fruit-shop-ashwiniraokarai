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
    public void setUp(){
        catalog = CatalogTestDataHelper.createNewCatalogWithItems();
    }

    @Test
    public void shouldBeAbleToUpdateTheCurrentPriceOfAFruit() {
// TODO: Uncomment this code and make it work - Done
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

    @Test//(expected = FruitUnavailableException.class )
    public void shouldReportFruitPrice(){
        Assertions.assertThat(catalog.getUnitPriceOf(Apple)).isEqualTo(4.00);
        Assertions.assertThat(catalog.getUnitPriceOf(Orange)).isEqualTo(5.50);
        Assertions.assertThat(catalog.getUnitPriceOf(Banana)).isEqualTo(6.00);
        Assertions.assertThat(catalog.getUnitPriceOf(Pear)).isEqualTo(4.50);
//        Assertions.assertThat(catalog.getUnitPriceOf(Grapefruit)).isEqualTo(4.50);
    }

    @Test(expected = FruitUnavailableException.class)
    public void throwsExceptionIfFruitIsUnavailable() throws FruitUnavailableException {
            catalog.checkFruitAvailability(Grapefruit);
    }

    @Test
    public void noExceptionIfFruitIsAvailable() throws FruitUnavailableException {
        catalog.checkFruitAvailability(Apple);
    }

    @Test
    //Ash: test that pricing calculation is correct when quantity > 1 kilo
    public void shouldCalculatePerFruitPriceForMultipleUnits()  {
        Double fruitPriceForQuantity = catalog.calculateFruitPriceForQuantity(Apple, 2);
        Assertions.assertThat(fruitPriceForQuantity).isEqualTo(8);
    }
}
