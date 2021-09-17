package com.sporecomerce.api.demo.crewmembers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.sporecomerce.api.demo.product.Product;
import com.sporecomerce.api.demo.productxcrew.Productxcrew;

public class CustomSerializer extends StdSerializer<Set<Productxcrew>> {

    public CustomSerializer() {
        this(null);
    }

    public CustomSerializer(Class<Set<Productxcrew>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<Productxcrew> value, JsonGenerator gen, SerializerProvider provider) throws IOException {

        Set<Product> products = new HashSet<>();
        for (Productxcrew p : value) {
            products.add(p.getProduct());
        }
        gen.writeObject(products);
    }

}
