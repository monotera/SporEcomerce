package com.sporecomerce.api.demo.product;

import java.util.Random;

public class ProductGenerator {

    //Initial values
    public Boolean initial(Product product) {
        Random random = new Random();
        Integer bound = 1000000;
        Integer capacity = 30;
        Integer stock = random.nextInt(bound);
        Integer offer = random.nextInt(bound);
        Integer demand = random.nextInt(bound);
        Boolean sentry = false;
        product.setOffer(0);
        product.setDemand(0);

        //Sets the load capacity
        product.setLoad_capacity(random.nextInt(capacity)/.8);

        //Sets the stock
        product.setStock(stock);

        //Warrants that the product will be purchased or sold
        do{
            product.setPP_(random.nextBoolean());
            product.setSP_(random.nextBoolean());
        }while((product.isPP_() != true) && (product.isSP_() != true));


        //If the product is to be sold
        if(product.isPP_()){
            product.setOffer(offer);
        }

        //If the product is to be purchased
        if(product.isSP_()){
            product.setDemand(demand);
        }

        sentry = updatePrices(product);
        return sentry;
    }

    public Boolean updatePrices(Product product){
        Integer offer = product.getOffer();
        Integer demand = product.getDemand();
        Integer stock = product.getStock();
        Boolean sentry = false;

        //If the product is to be sold
        if(product.isPP_()){
            product.setPurchase_price(offer/(1.0+stock));
            sentry = true;
        }

        //If the product is to be purchased
        if(product.isSP_()){
            product.setSales_price(demand/(1.0+stock));
            sentry = true;
        }
        return sentry;
    }

    public boolean checkStock(Product product){
        Boolean sentry = false;

        if(product.getStock() > 0){
            sentry = true;
        }
        return sentry;
    }

}
