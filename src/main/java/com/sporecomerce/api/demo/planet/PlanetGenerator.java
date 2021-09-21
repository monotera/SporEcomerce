package com.sporecomerce.api.demo.planet;

import java.util.ArrayList;
import java.util.Random;

import com.sporecomerce.api.demo.product.Product;
import com.sporecomerce.api.demo.product.ProductGenerator;
import com.sporecomerce.api.demo.productxplanet.Productxplanet;

public class PlanetGenerator {
    public Boolean initial(Planet planet, ArrayList<String> listProducts){
        Random random = new Random();
        Integer numProducts = random.nextInt(6) + 5; //5 to 10 products per planet
        Integer size = listProducts.size();
        Integer ran = 0;
        Integer count = 0;
        ArrayList<Integer> num = new ArrayList<>(); //Products taken
        Product p;
        Boolean sentry = false;
        ProductGenerator gP = new ProductGenerator();

        while (count < numProducts) {
            ran = random.nextInt(size);
            //Verify that ther are not identical products
            if(!num.contains(ran)){
                p = new Product();
                p.setProduct_name(listProducts.get(ran));
                gP.initial(p);
                planet.addProduct(p);

                sentry = true;
                num.add(ran);
                count ++;
            }
        }

        return sentry;
    }
}
