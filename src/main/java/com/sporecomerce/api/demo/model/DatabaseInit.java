package com.sporecomerce.api.demo.model;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Random;

import com.sporecomerce.api.demo.crewmembers.CrewGenerator;
import com.sporecomerce.api.demo.crewmembers.Crewmembers;
import com.sporecomerce.api.demo.crewmembers.CrewmembersRepository;
import com.sporecomerce.api.demo.galaxy.GalaxyGraph;
import com.sporecomerce.api.demo.galaxy.GalaxyGraphService;
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
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("devel")
public class DatabaseInit implements ApplicationRunner {

    // mvn clean install spring-boot:run -Dspring-boot.run.profiles=devel
    private int nProducts;
    private int nStars;
    private int prob;
    private int nCrewmembers;
    private int nSpaceships;
    private int nPlayers;
    private GalaxyGraphService galaxyGraphService;

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

        galaxyGraphService = new GalaxyGraphService();
        galaxyGraphService.generateGalaxy();
        galaxyGraphService.printGraph();

        prob = 99;
        GalaxyGraph spore = new GalaxyGraph();

        nProducts = 500;
        ArrayList<Product> pr = generate_products_names(nProducts);

        for (Product s : pr) {
            save_o(s);
        }

        nStars = 40000;
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

    }

    private ArrayList<Product> generate_products_names(int n) {
        ArrayList<Product> test = new ArrayList<>();
        Random random = new Random();
        Integer capacity = 30;
        int cont = 0;
        int pro = 0;

        for (int i = 0; i < n; i++) {
            Product p = new Product();
            String name;

            if (pro == 0)
                name = ProductRole.values()[cont].toString();
            else
                name = ProductRole.values()[cont].toString() + "-0" + String.valueOf(pro);

            if (cont == 249) {
                cont = 0;
                pro++;
            }
            p.setProduct_name(name);
            p.setLoad_capacity(random.nextInt(capacity) / .8);

            cont++;
            test.add(p);
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
            i++;
        }
        return test;
    }

    private ArrayList<Star> generate_stars(ArrayList<Product> productNames, GalaxyGraph spore) {
        ArrayList<Star> test = new ArrayList<>();
        Random random = new Random();
        StarGenerator gS = new StarGenerator();

        for (int i = 0; i < nStars; i++) {
            String id = "XR-192" + String.valueOf(i);
            boolean habited = (random.nextInt(prob) == 0) ? true : false;

            Star e = new Star();
            gS.initial(e, productNames, id, habited, test);

            // spore.getGalaxyContent().add(e);
            test.add(e);
        }

        return test;
    }

    private ArrayList<Crewmembers> generate_crewmembers(ArrayList<Spaceship> sp) {
        ArrayList<Crewmembers> test = new ArrayList<>();
        CrewGenerator gC = new CrewGenerator();

        for (int i = 0; i < nCrewmembers; i++) {
            String id = "031805" + String.valueOf(i);
            Spaceship n = sp.get(i);
            Crewmembers t = new Crewmembers();

            gC.initial(t, id, n);
            test.add(t);
        }

        return test;
    }

    @Autowired
    private PasswordEncoder encoder;

    private ArrayList<Player> generate_players(ArrayList<Crewmembers> sc) {
        ArrayList<Player> test = new ArrayList<>();
        PlayerGenerator gP = new PlayerGenerator();
        Random random = new Random();
        boolean testUser = false;

        for (Crewmembers c : sc) {
            for (int i = 0; i < nPlayers; i++) {
                String id = "161201" + String.valueOf(i) + String.valueOf(c.getId());
                Player p = new Player();
                Role r = Role.values()[random.nextInt(2) + 1];

                if (i == 0) {
                    r = Role.values()[0];
                }
                gP.initial(p, id, r);
                if (!testUser) {
                    p.setPlayer_name("TestUser");
                    testUser = true;
                } else {
                    p.setPlayer_name("User" + p.getPlayer_name() + random.nextInt(500));
                }

                p.setPassword(encoder.encode("pass123"));
                c.addPlayer(p);

                test.add(p);
            }
        }

        return test;
    }

    private Boolean save_o(Object obj) {
        Boolean sentry = false;
        if (obj.getClass().equals(Crewmembers.class)) {
            crewmembersRepository.save((Crewmembers) obj);
            sentry = true;
        }
        if (obj.getClass().equals(Planet.class)) {
            planetRepository.save((Planet) obj);
            sentry = true;
        }
        if (obj.getClass().equals(Player.class)) {
            playerRepository.save((Player) obj);
            sentry = true;
        }
        if (obj.getClass().equals(Product.class)) {
            productRepository.save((Product) obj);
            sentry = true;
        }
        if (obj.getClass().equals(Spaceship.class)) {
            spaceshipRepository.save((Spaceship) obj);
            sentry = true;
        }
        if (obj.getClass().equals(Star.class)) {
            starRepository.save((Star) obj);
            sentry = true;
        }

        return sentry;
    }

}
