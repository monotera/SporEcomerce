package com.sporecomerce.api.demo.model;

import javax.transaction.Transactional;

import com.sporecomerce.api.demo.crewmembers.Crewmembers;
import com.sporecomerce.api.demo.crewmembers.CrewmembersRepository;
import com.sporecomerce.api.demo.planet.Planet;
import com.sporecomerce.api.demo.planet.PlanetRepository;
import com.sporecomerce.api.demo.player.Player;
import com.sporecomerce.api.demo.player.PlayerRepository;
import com.sporecomerce.api.demo.player.Role;
import com.sporecomerce.api.demo.product.Product;
import com.sporecomerce.api.demo.product.ProductRepository;
import com.sporecomerce.api.demo.productxcrew.ProductxcrewRepository;
import com.sporecomerce.api.demo.productxplanet.ProductxplanetRepository;
import com.sporecomerce.api.demo.spaceship.Spaceship;
import com.sporecomerce.api.demo.spaceship.SpaceshipRepository;
import com.sporecomerce.api.demo.star.Star;
import com.sporecomerce.api.demo.star.StarRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit implements ApplicationRunner {

    @Autowired
    StarRepository starRepository;

    @Autowired
    PlanetRepository planetRepository;

    @Autowired
    SpaceshipRepository spaceshipRepository;

    @Autowired
    CrewmembersRepository crewmembersRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    ProductxcrewRepository productxcrewRepository;

    @Autowired
    ProductxplanetRepository productxplanetRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        Star star1 = new Star(10, 5, 10, "Main Star", true);
        Star star2 = new Star(11, 4, 10, "Sta2r", true);

        Planet p1 = new Planet("Planet1");
        Planet p2 = new Planet("Planet2");
        Planet p3 = new Planet("Planet3");
        Planet p4 = new Planet("Planet4");

        Spaceship sp1 = new Spaceship("Ship1", 15.2, 10);
        Spaceship sp2 = new Spaceship("Ship2", 15.2, 10);
        Spaceship sp3 = new Spaceship("Ship3", 15.2, 10);
        Spaceship sp4 = new Spaceship("Ship4", 15.2, 10);
        Spaceship sp5 = new Spaceship("Ship5", 15.2, 10);
        Spaceship sp6 = new Spaceship("Ship6", 15.2, 10);

        Crewmembers c1 = new Crewmembers("Crew1", 20, 20);
        Crewmembers c2 = new Crewmembers("Crew2", 10, 20);
        Crewmembers c3 = new Crewmembers("Crew3", 20, 20);

        Player pl1 = new Player("player1", Role.CAPTAIN);
        Player pl2 = new Player("player2", Role.MERCHANT);
        Player pl3 = new Player("player3", Role.PILOT);

        Product pr1 = new Product("pr1", 10, 10, 10, 10, true, 10, 10, false);
        Product pr2 = new Product("pr2", 10, 10, 10, 10, true, 10, 10, false);
        Product pr3 = new Product("pr3", 10, 10, 10, 10, true, 10, 10, false);
        Product pr4 = new Product("pr4", 10, 10, 10, 10, true, 10, 10, false);
        Product pr5 = new Product("pr5", 10, 10, 10, 10, true, 10, 10, false);

        c1.setSpace_crew(sp2);
        c3.setSpace_crew(sp4);
        c2.setSpace_crew(sp3);
        c1.addPlayer(pl1);
        c1.addPlayer(pl2);
        c2.addPlayer(pl3);

        star1.addPlanet(p1);
        star1.addPlanet(p2);
        star2.addPlanet(p3);
        star2.addPlanet(p4);

        star1.addSpaceShip(sp1);
        star1.addSpaceShip(sp2);
        star1.addSpaceShip(sp3);
        star2.addSpaceShip(sp4);
        star2.addSpaceShip(sp5);
        star2.addSpaceShip(sp6);

        starRepository.save(star1);
        starRepository.save(star2);

        crewmembersRepository.save(c1);
        crewmembersRepository.save(c2);
        crewmembersRepository.save(c3);

        planetRepository.save(p1);
        planetRepository.save(p2);
        planetRepository.save(p3);
        planetRepository.save(p4);

        p1.addProduct(pr1);
        p1.addProduct(pr2);
        p1.addProduct(pr3);
        p2.addProduct(pr4);
        p3.addProduct(pr1);
        p2.addProduct(pr5);

        c1.addProduct(pr1);
        c2.addProduct(pr1);
        c3.addProduct(pr1);
        c1.addProduct(pr2);
        c1.addProduct(pr3);
        c3.addProduct(pr4);
        c2.addProduct(pr4);
    }

}
