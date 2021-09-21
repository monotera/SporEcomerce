package com.sporecomerce.api.demo.productxplanet;
import com.sporecomerce.api.demo.planet.Planet;
import com.sporecomerce.api.demo.product.Product;
import java.util.Random;

public class ProductxplanetGenerator {
    //Initial values
    public Boolean initial(Product product, Planet planet) {
        Random random = new Random();
        Integer bound = 1000000;
        Integer productStock = random.nextInt(bound);
        Integer offer = random.nextInt(bound);
        Integer demand = random.nextInt(bound);
        Boolean sentry = false;

        Integer productOffer = 0;
        Integer productDemand = 0;

        Boolean productPP = false;
        Boolean productSP = false;

        //Warrants that the product will be purchased or sold
        do{
            productPP = random.nextBoolean();
            productSP = random.nextBoolean();
        }while((productPP != true) && (productSP != true));


        //If the product is to be sold
        if(productPP){
            productOffer= offer;
        }

        //If the product is to be purchased
        if(productSP){
            productDemand = demand;
        }

        planet.addProduct(product, productStock, productDemand, productSP, productOffer, productPP);

        return sentry;
    }
}
