package com.sporecomerce.api.demo.model;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Random;

import com.sporecomerce.api.demo.crewmembers.CrewGenerator;
import com.sporecomerce.api.demo.crewmembers.Crewmembers;
import com.sporecomerce.api.demo.crewmembers.CrewmembersRepository;
import com.sporecomerce.api.demo.galaxy.GalaxyGraph;
import com.sporecomerce.api.demo.planet.Planet;
import com.sporecomerce.api.demo.planet.PlanetRepository;
import com.sporecomerce.api.demo.player.Player;
import com.sporecomerce.api.demo.player.PlayerGenerator;
import com.sporecomerce.api.demo.player.PlayerRepository;
import com.sporecomerce.api.demo.player.Role;
import com.sporecomerce.api.demo.product.Product;
import com.sporecomerce.api.demo.product.ProductRepository;
import com.sporecomerce.api.demo.product.ProductRole;
import com.sporecomerce.api.demo.productxcrew.ProductxcrewRepository;
import com.sporecomerce.api.demo.productxplanet.ProductxplanetRepository;
import com.sporecomerce.api.demo.spaceship.Spaceship;
import com.sporecomerce.api.demo.spaceship.SpaceshipGenerator;
import com.sporecomerce.api.demo.spaceship.SpaceshipRepository;
import com.sporecomerce.api.demo.spaceship.SpaceshipRole;
import com.sporecomerce.api.demo.star.Star;
import com.sporecomerce.api.demo.star.StarGenerator;
import com.sporecomerce.api.demo.star.StarRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit implements ApplicationRunner {

    private int nProducts;
    private int nStars;
    private int prob;
    private int nCrewmembers;
    private int nSpaceships;
    private int nPlayers;


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

        prob = 1;
        GalaxyGraph spore = new GalaxyGraph();

        nProducts = 500;
        ArrayList<String> pr = generate_products_names(nProducts);

        nStars = 400;
        ArrayList<Star> st = generate_stars(pr, spore);

        nSpaceships = 20;
        ArrayList<Spaceship> sp = generate_spaceships(st);

        nCrewmembers = 10;
        ArrayList<Crewmembers> sc = generate_crewmembers(sp);

        nPlayers = 10;
        ArrayList<Player> pl = generate_players(sc);


        for (Star s : st) {
            save_o(s);
            for (Planet s1 : s.getPlanetList()) {
                save_o(s1);
            }
        }

        for (Spaceship s : sp) {
            save_o(s);
        }

        for (Crewmembers s : sc) {
            save_o(s);
        }

        for (Player s : pl) {
            save_o(s);
        }

        /*
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
        c3.addProduct(pr4);
        c2.addProduct(pr4);
        */
    }
    
    //TODO: CAMBIAR A LSITA DE PRODUCTOS
    private ArrayList<String> generate_products_names(int n){
        ArrayList<String> test = new ArrayList<>();
        int cont = 0;
        int pro = 0;

        for (int i = 0; i < n; i++) {
            String name;
            if(pro == 0)
                name = ProductRole.values()[cont].toString();
            else 
                name = ProductRole.values()[cont].toString() + "-0" +String.valueOf(pro);
            
            if(cont == 249){
                cont = 0;
                pro ++;
            }
                
            cont ++;
            test.add(name);
        }
        return test;
    }

    private ArrayList<Spaceship> generate_spaceships(ArrayList<Star> st) {
        ArrayList<Spaceship> test = new ArrayList<>();
        SpaceshipGenerator c = new SpaceshipGenerator();
        int i = 1;

        for (SpaceshipRole dir : SpaceshipRole.values()) {
            Spaceship p = new Spaceship();
            Random random = new Random();

            p.changeStar(st.get(random.nextInt(st.size())), null);
            c.initial(p, dir.toString(), i);
            test.add(p);
            i ++;
        }
        return test;
    }

    private ArrayList<Star> generate_stars(ArrayList<String> productNames, GalaxyGraph spore){
        ArrayList<Star> test = new ArrayList<>();
        Random random = new Random();
        StarGenerator gS = new StarGenerator();

        for(int i=0; i<nStars; i++){
            String id = "XR-192" + String.valueOf(i);
            boolean habited = (random.nextInt(prob) == 0) ? true : false;

            Star e = new Star();
            gS.initial(e, productNames, id, habited, test);
            
            //spore.getGalaxyContent().add(e);
            test.add(e);
        }

        return test;
    }

    private ArrayList<Crewmembers> generate_crewmembers(ArrayList<Spaceship> sp) {
        ArrayList<Crewmembers> test = new ArrayList<>();
        CrewGenerator gC = new CrewGenerator();

        for(int i=0; i<nCrewmembers; i++){
            String id = "031805" + String.valueOf(i);
            Spaceship n = sp.get(i);
            Crewmembers t = new Crewmembers();

            gC.initial(t, id, n);
            test.add(t);
        }

        return test;
    }

    private ArrayList<Player> generate_players(ArrayList<Crewmembers> sc){
        ArrayList<Player> test = new ArrayList<>();
        PlayerGenerator gP = new PlayerGenerator();
        Random random = new Random();

        for (Crewmembers c : sc) {
            for (int i = 0; i < nPlayers; i++) {
                String id = "161201" + String.valueOf(i) + String.valueOf(c.getId());
                Player p = new Player();
                Role r = Role.values()[random.nextInt(2)+1];
                
                if(i == 0){
                    r = Role.values()[0];
                }
                
                gP.initial(p, id, r);
                c.addPlayer(p);

                test.add(p);
            }
        }

        return test;
    }

    private Boolean save_o(Object obj){
        Boolean sentry = false;
        if(obj.getClass().equals(Crewmembers.class)){
            crewmembersRepository.save((Crewmembers)obj);
            sentry = true;
        }
        if(obj.getClass().equals(Planet.class)){
            planetRepository.save((Planet)obj);
            sentry = true;
        }
        if(obj.getClass().equals(Player.class)){
            playerRepository.save((Player)obj);
            sentry = true;
        }
        if(obj.getClass().equals(Product.class)){
            productRepository.save((Product)obj);
            sentry = true;
        }
        if(obj.getClass().equals(Spaceship.class)){
            spaceshipRepository.save((Spaceship)obj);
            sentry = true;
        }
        if(obj.getClass().equals(Star.class)){
            starRepository.save((Star)obj);
            sentry = true;
        }
        
        return sentry;
    }

}
