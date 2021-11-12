package com.sporecomerce.api.demo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.validation.constraints.AssertTrue;

import com.sporecomerce.api.demo.crewmembers.Crewmembers;
import com.sporecomerce.api.demo.crewmembers.CrewmembersRepository;
import com.sporecomerce.api.demo.planet.Planet;
import com.sporecomerce.api.demo.planet.PlanetRepository;
import com.sporecomerce.api.demo.player.Player;
import com.sporecomerce.api.demo.player.PlayerRepository;
import com.sporecomerce.api.demo.player.Role;
import com.sporecomerce.api.demo.product.Product;
import com.sporecomerce.api.demo.product.ProductRepository;
import com.sporecomerce.api.demo.spaceship.Spaceship;
import com.sporecomerce.api.demo.spaceship.SpaceshipRepository;
import com.sporecomerce.api.demo.star.Star;
import com.sporecomerce.api.demo.star.StarRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integrationstest")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class CrewControllerIntegrationTest {

	@Autowired
	CrewmembersRepository crewmembersRepository;

	@Autowired
	PlayerRepository playerRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	SpaceshipRepository spaceshipRepository;

	@Autowired
	StarRepository starRepository;

	@Autowired
	PlanetRepository planetRepository;

	@LocalServerPort
	private int port;

	@Autowired
	TestRestTemplate rest;

	private Crewmembers crew;

	private Crewmembers crew2;

	private Player pl1, pl2;

	@BeforeEach
	void init() {

		Star star = new Star(0, 0, 0, "star", true);
		starRepository.save(star);

		Product p1 = new Product("p1", 10);
		productRepository.save(p1);

		Spaceship s1 = new Spaceship("s1", 1000, 100);
		s1.changeStar(star, null);
		spaceshipRepository.save(s1);

		Crewmembers c1 = new Crewmembers("crew1", 0, 1000);
		c1.setSpace_crew(s1);
		c1.addProduct(p1, 100, 0, false, 10, true);
		crewmembersRepository.save(c1);

		Player pl1 = new Player("pl1", Role.ROLE_CAPTAIN);
		Player pl2 = new Player("pl2", Role.ROLE_MERCHANT);
		Player pl3 = new Player("pl3", Role.ROLE_PILOT);
		pl1.setPassword("password");
		pl2.setPassword("password");
		pl3.setPassword("password");

		c1.addPlayer(pl1);
		c1.addPlayer(pl2);
		c1.addPlayer(pl3);
		playerRepository.save(pl1);
		playerRepository.save(pl2);
		playerRepository.save(pl3);
		this.crew = c1;
		this.pl1 = pl1;
		this.pl2 = pl2;

		Star star2 = new Star(1, 0, 0, "star2", false);
		starRepository.save(star2);

		Spaceship s2 = new Spaceship("s2", 1000, 100);
		s2.changeStar(star2, null);
		spaceshipRepository.save(s2);
		Crewmembers c2 = new Crewmembers("Crew2", 0, 2000);
		c2.setSpace_crew(s2);
		c2.addProduct(p1, 100, 0, false, 10, true);
		crewmembersRepository.save(c2);
		this.crew2 = c2;

	}

	@Test
	@DisplayName("Test the fetch of a single crew")
	void fetchSpecificCrew() {
		Crewmembers res = rest.getForObject("http://localhost:" + port + "/crew?crew_id=" + this.crew.getId(),
				Crewmembers.class);
		assertTrue(this.crew.getId() == res.getId());
	}

	@Test
	@DisplayName("Test the fetch all the crews")
	void fetchCrew() {
		Crewmembers[] res = rest.getForObject("http://localhost:" + port + "/crew/crews", Crewmembers[].class);
		assertTrue(res.length == 2);
	}

	@Test
	@DisplayName("Test petition that validates if there is a captain aboard")
	void fetchHasCaptain() {
		Boolean res = rest.getForObject("http://localhost:" + port + "/crew/captain?player_id=" + this.pl2.getId(),
				Boolean.class);
		assertEquals(true, res);
	}

	@Test
	@DisplayName("Test the calculation of the sum of all the weights of the Crew's products")
	void fetchLoadCapacity() {
		Double res = rest.getForObject("http://localhost:" + port + "/crew/load-capacity?crew_id=" + this.crew.getId(),
				Double.class);
		assertEquals(1000, res);
	}

	@Test
	@DisplayName("Test fetching of player with rol captain")
	void fetchCaptain() {
		Player res = rest.getForObject("http://localhost:" + port + "/crew/cap?crew_id=" + this.crew.getId(),
				Player.class);
		assertTrue(this.pl1.getId() == res.getId());
	}

}
