package com.sporecomerce.api.demo.productxplanet;

import com.sporecomerce.api.demo.crewmembers.Crewmembers;
import com.sporecomerce.api.demo.crewmembers.CrewmembersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pxp")
public class ProductxplanetController {
    @Autowired
    ProductxplanetRepository productxplanetRepository;

    @Autowired
    CrewmembersRepository crewmembersRepository;

    @PutMapping("/buy")
    @CrossOrigin(origins = "http://localhost:4200")

    public ResponseEntity<Productxplanet> buyProduct(@RequestParam Integer amountProducts, @RequestParam Long pxp_id,
            @RequestParam Long crew_id) {
        Productxplanet pxp = productxplanetRepository.findById(pxp_id).orElseThrow();
        Crewmembers crew = crewmembersRepository.findById(crew_id).orElseThrow();
        Double priceToPay = amountProducts * pxp.getSales_price();
        if (pxp.getStock() < amountProducts || crew.getCredits() < priceToPay) {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_MODIFIED);
        }
        pxp.setStock(pxp.getStock() - amountProducts);
        pxp.updatePrices();
        crew.setCredits(crew.getCredits() - priceToPay);
        crew.addProduct(pxp.getProduct(), amountProducts, pxp.getDemand(), pxp.isSP_(), pxp.getOffer(), pxp.isPP_());
        crewmembersRepository.save(crew);
        productxplanetRepository.save(pxp);
        return new ResponseEntity<>(pxp, null, HttpStatus.OK);
    }
}
