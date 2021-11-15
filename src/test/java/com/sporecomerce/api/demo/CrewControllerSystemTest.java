package com.sporecomerce.api.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import io.github.bonigarcia.wdm.WebDriverManager;

@ActiveProfiles("systemtest")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class CrewControllerSystemTest {

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

	private Crewmembers crew;

	private Player pl1, pl2;

	private String baseUrl;

	private FirefoxDriver browser;

	private WebDriverWait wait;

	@Autowired
	private PasswordEncoder encoder;

	@BeforeEach
	void init() {

		Star star = new Star(0, 0, 0, "Test star", true);
		starRepository.save(star);

		Planet planet = new Planet("planet1");
		planetRepository.save(planet);
		Product p1 = new Product("p1", 10);
		productRepository.save(p1);

		planet.addProduct(p1, 100, 10000, true, 100000, true);
		star.addPlanet(planet);

		planetRepository.save(planet);
		Spaceship s1 = new Spaceship("s1", 1000, 100);
		s1.changeStar(star, null);
		spaceshipRepository.save(s1);

		Crewmembers c1 = new Crewmembers("crew1", 0, 1000);
		c1.setSpace_crew(s1);
		c1.addProduct(p1, 100, 0, false, 10, true);
		crewmembersRepository.save(c1);

		Player pl1 = new Player("TestUser", Role.ROLE_CAPTAIN);
		Player pl2 = new Player("pl2", Role.ROLE_MERCHANT);
		Player pl3 = new Player("pl3", Role.ROLE_PILOT);
		pl1.setPassword(encoder.encode("pass123"));
		pl2.setPassword(encoder.encode("password"));
		pl3.setPassword(encoder.encode("password"));

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

		this.baseUrl = "http://localhost:4200";
		WebDriverManager.firefoxdriver().setup();

		FirefoxOptions options = new FirefoxOptions();
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-gpu");
		options.addArguments("--disable-extensions");
		options.addArguments("--headless");
		options.merge(DesiredCapabilities.firefox());

		this.browser = new FirefoxDriver(options);
		this.wait = new WebDriverWait(browser, 15);

	}

	@AfterEach
	void end() {
		this.browser.quit();
	}

	@Test
	@DisplayName("Test user consulting his crew info")
	void testFetchSpecificCrew() {
		browser.get(baseUrl + "/login");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login")));
		WebElement loginBtn = browser.findElementById("login");
		loginBtn.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("hamburger")));
		WebElement hamburger = browser.findElementById("hamburger");
		hamburger.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("navliAccount")));
		WebElement accountLink = browser.findElementById("navliAccount");
		accountLink.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("accountbtnCrewInfo")));
		WebElement crewBtn = browser.findElementById("accountbtnCrewInfo");
		crewBtn.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("crewliCrewName")));
		WebElement crewName = browser.findElementById("crewliCrewName");
		String [] cleanName = crewName.getText().split("crew name:");
		assertEquals(cleanName[1], "crew1");
	}

	@Test
	@DisplayName("Test user buying a product")
	void testCommerce() {
		browser.get(baseUrl + "/login");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login")));
		WebElement loginBtn = browser.findElementById("login");
		loginBtn.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("navibtnCommerce")));
		WebElement commerceBtn = browser.findElementById("navibtnCommerce");
		commerceBtn.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("combtnBuy0")));
		WebElement buyBtn = browser.findElementById("combtnBuy0");
		buyBtn.click();
		String alertMsg = browser.switchTo().alert().getText();
		assertEquals("Your transaction was successful!", alertMsg);	
	}

	@Test
	@DisplayName("Test user not having right credentials to buy a product")
	void testCommerceError() {
		Player player_aux = playerRepository.findById(this.pl1.getId()).orElseThrow();
		player_aux.setPlayer_role(Role.ROLE_PILOT);
		playerRepository.save(player_aux);
		browser.get(baseUrl + "/login");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login")));
		WebElement loginBtn = browser.findElementById("login");
		loginBtn.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("navibtnCommerce")));
		WebElement commerceBtn = browser.findElementById("navibtnCommerce");
		commerceBtn.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("combtnBuy0")));
		WebElement buyBtn = browser.findElementById("combtnBuy0");
		buyBtn.click();
		String alertMsg = browser.switchTo().alert().getText();
		assertEquals("You dont have the credentials to do this action!", alertMsg);	
	}

	@Test
	@DisplayName("Test user not having money to buy a product")
	void testCommerceError2() {
		Player player_aux = playerRepository.findById(this.pl1.getId()).orElseThrow();
		player_aux.getCrewmembers().setCredits(0);
		playerRepository.save(player_aux);
		crewmembersRepository.save(player_aux.getCrewmembers());
		browser.get(baseUrl + "/login");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login")));
		WebElement loginBtn = browser.findElementById("login");
		loginBtn.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("navibtnCommerce")));
		WebElement commerceBtn = browser.findElementById("navibtnCommerce");
		commerceBtn.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("combtnBuy0")));
		WebElement buyBtn = browser.findElementById("combtnBuy0");
		buyBtn.click();
		String alertMsg = browser.switchTo().alert().getText();
		assertEquals("Error!!", alertMsg);	
	}

	@Test
	@DisplayName("Test user captain changing his crew info")
	void testChangeCrewName() {
		browser.get(baseUrl + "/login");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login")));
		WebElement loginBtn = browser.findElementById("login");
		loginBtn.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("hamburger")));
		WebElement hamburger = browser.findElementById("hamburger");
		hamburger.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("navliAccount")));
		WebElement accountLink = browser.findElementById("navliAccount");
		accountLink.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("accounttxtNewCrewName")));
		WebElement crewNameTxt = browser.findElementById("accounttxtNewCrewName");
		crewNameTxt.sendKeys("Millennium falcon");
		WebElement applyBtn = browser.findElementById("accountbtnNewCrewName");
		applyBtn.click();
		String alertMsg = browser.switchTo().alert().getText();
		assertEquals("¡Changes have been made! Millennium falcon", alertMsg);
	}
	@Test
	@DisplayName("Test user merchant changing his crew info")
	void testChangeCrewNameError() {
		Player player_aux = playerRepository.findById(this.pl1.getId()).orElseThrow();
		player_aux.setPlayer_role(Role.ROLE_MERCHANT);
		playerRepository.save(player_aux);
		browser.get(baseUrl + "/login");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login")));
		WebElement loginBtn = browser.findElementById("login");
		loginBtn.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("hamburger")));
		WebElement hamburger = browser.findElementById("hamburger");
		hamburger.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("navliAccount")));
		WebElement accountLink = browser.findElementById("navliAccount");
		accountLink.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("accounttxtNewCrewName")));
		WebElement crewNameTxt = browser.findElementById("accounttxtNewCrewName");
		crewNameTxt.sendKeys("Millennium falcon");
		WebElement applyBtn = browser.findElementById("accountbtnNewCrewName");
		applyBtn.click();
		String alertMsg = browser.switchTo().alert().getText();
		assertEquals("¡You are not the captain! ROLE_MERCHANT", alertMsg);
	}


	@Test
	@DisplayName("Test the fetching of the info of the star where the crew is")
	void testFetchStarInfo() {
		browser.get(baseUrl + "/login");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login")));
		WebElement loginBtn = browser.findElementById("login");
		loginBtn.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("navStarName")));
		WebElement starName = browser.findElementById("navStarName");
		assertEquals("Test star", starName.getText());
	}

	@Test
	@DisplayName("Test the game ender button when you dont have enough credentials")
	void testTerminateGameError() {
		Player player_aux = playerRepository.findById(this.pl1.getId()).orElseThrow();
		player_aux.setPlayer_role(Role.ROLE_MERCHANT);
		playerRepository.save(player_aux);
		browser.get(baseUrl + "/login");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login")));
		WebElement loginBtn = browser.findElementById("login");
		loginBtn.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("navibtnTerminate")));
		WebElement terminateBtn = browser.findElementById("navibtnTerminate");
		terminateBtn.click();
		String alertMsg = browser.switchTo().alert().getText();
		assertEquals("Only the captain can finish the game", alertMsg);
	}

	@Test
	@DisplayName("Test the game ender button")
	void testTerminateGame() {
		browser.get(baseUrl + "/login");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login")));
		WebElement loginBtn = browser.findElementById("login");
		loginBtn.click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("navibtnTerminate")));
		WebElement terminateBtn = browser.findElementById("navibtnTerminate");
		terminateBtn.click();
		String alertMsg = browser.switchTo().alert().getText();
		assertEquals("Total time: 0 Total Earned: 1000", alertMsg);
	}
	

	@Test
	@DisplayName("Test logout")
	void testLogout() {
		browser.get(baseUrl + "/login");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login")));
		WebElement loginBtn = browser.findElementById("login");
		loginBtn.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("hamburger")));
		WebElement hamburger = browser.findElementById("hamburger");
		hamburger.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("navliLogout")));
		WebElement accountLink = browser.findElementById("navliLogout");
		accountLink.click();
		assertEquals(baseUrl+"/", browser.getCurrentUrl());
	}
}
