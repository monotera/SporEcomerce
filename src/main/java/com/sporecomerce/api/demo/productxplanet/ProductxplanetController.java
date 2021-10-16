package com.sporecomerce.api.demo.productxplanet;

import com.sporecomerce.api.demo.crewmembers.Crewmembers;
import com.sporecomerce.api.demo.crewmembers.CrewmembersRepository;
import com.sporecomerce.api.demo.productxcrew.Productxcrew;
import com.sporecomerce.api.demo.productxcrew.ProductxcrewRepository;

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

    @Autowired
    ProductxcrewRepository productxcrewRepository;

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
        crew.addProduct(pxp.getProduct(), amountProducts, pxp.getDemand(), true, pxp.getOffer(), true);
        crewmembersRepository.save(crew);
        productxplanetRepository.save(pxp);
        return new ResponseEntity<>(pxp, null, HttpStatus.OK);
    }

    @PutMapping("/sell")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Productxplanet> sellProduct(@RequestParam Integer amountProducts, @RequestParam Long pxp_id,
            @RequestParam Long pxc_id) {

        Productxplanet pxp = productxplanetRepository.findById(pxp_id).orElseThrow();
        Productxcrew pxc = productxcrewRepository.findById(pxc_id).orElseThrow();
        Crewmembers crew = pxc.getCrewmembers();

        if (pxc.getStock() < amountProducts) {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_MODIFIED);
        }

        Double priceToPay = amountProducts * pxc.getSales_price();
        pxp.setStock(pxp.getStock() + amountProducts);
        crew.removeProduct(pxc.getProduct(), amountProducts);
        crew.setCredits(crew.getCredits() + priceToPay);
        pxp.updatePrices();
        crewmembersRepository.save(crew);
        productxplanetRepository.save(pxp);
        return new ResponseEntity<>(pxp, null, HttpStatus.OK);
    }
}
