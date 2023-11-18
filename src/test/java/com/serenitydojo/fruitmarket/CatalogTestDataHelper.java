package com.serenitydojo.fruitmarket;

import com.serenitydojo.Catalog;
import com.serenitydojo.CatalogItem;

import static com.serenitydojo.Fruit.*;
import static com.serenitydojo.Fruit.Orange;

public class CatalogTestDataHelper {
    public static Catalog  createNewCatalogWithItems(){
        Catalog catalog = Catalog.withItems(
                new CatalogItem(Pear),
                new CatalogItem(Apple),
                new CatalogItem(Banana),
                new CatalogItem(Orange)
        );
        return catalog;
    }
}
