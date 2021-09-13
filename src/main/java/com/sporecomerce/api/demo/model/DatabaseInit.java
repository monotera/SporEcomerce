package com.sporecomerce.api.demo.model;

import java.util.ArrayList;
import java.util.Random;

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
import com.sporecomerce.api.demo.productxcrew.Productxcrew;
import com.sporecomerce.api.demo.productxcrew.ProductxcrewRepository;
import com.sporecomerce.api.demo.productxplanet.Productxplanet;
import com.sporecomerce.api.demo.productxplanet.ProductxplanetRepository;
import com.sporecomerce.api.demo.spaceship.Spaceship;
import com.sporecomerce.api.demo.spaceship.SpaceshipRepository;
import com.sporecomerce.api.demo.star.Star;
import com.sporecomerce.api.demo.star.StarRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DatabaseInit implements ApplicationRunner {
    /*
     * private static final int NUM_PERSONS = 100; private static final int
     * NUM_COMPANIES = 100; private static final int ROLES_PER_COMPANY = 3; private
     * static final int EMPLOYEES_PER_ROLE_PER_COMPANY = 4;
     * 
     * /*
     * 
     * @Autowired CompanyRepository companyRepository;
     * 
     * @Autowired DivisionRepository divisionRepository;
     * 
     * @Autowired RoleRepository roleRepository;
     */

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

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseInit.class);

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
        star1.addPlanet(p3);
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

        productxcrewRepository.save(new Productxcrew(pr1, c1));
        productxcrewRepository.save(new Productxcrew(pr1, c2));
        productxcrewRepository.save(new Productxcrew(pr1, c3));
        productxcrewRepository.save(new Productxcrew(pr2, c1));
        productxcrewRepository.save(new Productxcrew(pr3, c1));
        productxcrewRepository.save(new Productxcrew(pr4, c3));
        productxcrewRepository.save(new Productxcrew(pr4, c2));

        productxplanetRepository.save(new Productxplanet(pr1, p1));
        productxplanetRepository.save(new Productxplanet(pr2, p1));
        productxplanetRepository.save(new Productxplanet(pr3, p1));
        productxplanetRepository.save(new Productxplanet(pr4, p2));
        productxplanetRepository.save(new Productxplanet(pr1, p3));
    }

}
