package com.sporecomerce.api.demo.planet;

import java.util.ArrayList;
import java.util.Random;

import com.sporecomerce.api.demo.product.Product;
import com.sporecomerce.api.demo.productxplanet.ProductxplanetGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlanetGenerator {
    public Boolean initial(Planet planet, ArrayList<Product> listProducts) {
        Random random = new Random();
        Integer numProducts = random.nextInt(6) + 5; //5 to 10 products per planet
        Integer size = 50;
        Integer count = 0;
        ArrayList<Product> num = new ArrayList<>(); // Products taken

        Boolean sentry = false;
        ProductxplanetGenerator ppG = new ProductxplanetGenerator();
        Logger logger = LoggerFactory.getLogger(PlanetGenerator.class);

        while (count < numProducts) {
            try {
                Product pro = listProducts.get(random.nextInt(size));
                // Verify that ther are not identical products
                if (!num.contains(pro)) {
                    
                    ppG.initial(pro, planet);
                    sentry = true;
                    num.add(pro);
                    count++;
                }
            } catch (Exception e) {
                logger.info("AAAAAAAAAAAAAAA"+e.toString());
            }

        }

        return sentry;
    }

}
