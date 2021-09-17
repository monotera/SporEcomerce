package com.sporecomerce.api.demo;

import com.sporecomerce.api.demo.crewmembers.Crewmembers;
import com.sporecomerce.api.demo.crewmembers.CrewmembersRepository;
import com.sporecomerce.api.demo.planet.Planet;
import com.sporecomerce.api.demo.planet.PlanetRepository;
import com.sporecomerce.api.demo.player.Player;
import com.sporecomerce.api.demo.player.PlayerRepository;
import com.sporecomerce.api.demo.product.Product;
import com.sporecomerce.api.demo.product.ProductRepository;
import com.sporecomerce.api.demo.spaceship.Spaceship;
import com.sporecomerce.api.demo.spaceship.SpaceshipRepository;
import com.sporecomerce.api.demo.star.Star;
import com.sporecomerce.api.demo.star.StarRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @Autowired
    CrewmembersRepository crewmembersRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    SpaceshipRepository spaceshipRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PlanetRepository planetRepository;

    @Autowired
    StarRepository starRepository;

    @GetMapping("")
    public String getMainPage(Model model) {
        return "home";
    }

    @GetMapping("/crew/view")
    public String getCrewPage(Model model) {
        Iterable<Crewmembers> crews = crewmembersRepository.findAll();
        Iterable<Product> products = productRepository.findAll();
        Iterable<Spaceship> spaceships = spaceshipRepository.findAll();
        model.addAttribute("crews", crews);
        model.addAttribute("products", products);
        model.addAttribute("spaceships", spaceships);
        return "crew";
    }

    @GetMapping("/planet/view")
    public String getPlanetPage(Model model) {
        Iterable<Planet> planets = planetRepository.findAll();
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("planets", planets);
        model.addAttribute("products", products);
        return "planet";
    }

    @GetMapping("/player/view")
    public String getPlayerPage(Model model) {
        Iterable<Player> players = playerRepository.findAll();
        Iterable<Crewmembers> crews = crewmembersRepository.findAll();
        model.addAttribute("players", players);
        model.addAttribute("crews", crews);
        return "player";
    }

    @GetMapping("/product/view")
    public String getProductPage(Model model) {
        Iterable<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "product";
    }

    @GetMapping("/spaceship/view")
    public String getSpaceshipPage(Model model) {
        Iterable<Star> stars = starRepository.findAll();
        Iterable<Spaceship> spaceships = spaceshipRepository.findAll();
        model.addAttribute("stars", stars);
        model.addAttribute("spaceships", spaceships);
        return "spaceship";
    }

    @GetMapping("/star/view")
    public String getStarPage(Model model) {
        Iterable<Star> stars = starRepository.findAll();
        Iterable<Planet> planets = planetRepository.findAll();
        Iterable<Spaceship> spaceships = spaceshipRepository.findAll();
        model.addAttribute("stars", stars);
        model.addAttribute("planets", planets);
        model.addAttribute("spaceships", spaceships);
        return "star";
    }
}
