package com.sporecomerce.api.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.AdditionalMatchers.geq;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sporecomerce.api.demo.crewmembers.Crewmembers;
import com.sporecomerce.api.demo.crewmembers.CrewmembersRepository;
import com.sporecomerce.api.demo.player.Player;
import com.sporecomerce.api.demo.player.PlayerRepository;
import com.sporecomerce.api.demo.player.Role;
import com.sporecomerce.api.demo.product.Product;
import com.sporecomerce.api.demo.spaceship.Spaceship;
import com.sporecomerce.api.demo.spaceship.SpaceshipRepository;
import com.sporecomerce.api.demo.star.Star;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integrationstest")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class CrewControllerUnitTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CrewmembersRepository crewmembersRepository;

    @MockBean
    SpaceshipRepository spaceshipRepository;

    @MockBean
    PlayerRepository playerRepository;

    private Crewmembers globalCrew;

    @BeforeEach
    void init() {
        Player pl1 = new Player("pl1", Role.ROLE_CAPTAIN);
        Player pl2 = new Player("pl2", Role.ROLE_MERCHANT);
        Player pl3 = new Player("pl3", Role.ROLE_PILOT);
        Star star = new Star(0, 0, 0, "star", true);
        Star star2 = new Star(1, 0, 0, "star2", false);
        Product p1 = new Product("p1", 10);
        Spaceship s1 = new Spaceship("s1", 1000, 100);
        Spaceship s2 = new Spaceship("s2", 1000, 100);
        Spaceship s3 = new Spaceship("s3", 1000, 100);
        Crewmembers c1 = new Crewmembers("crew1", 0, 1000);
        Crewmembers c2 = new Crewmembers("Crew2", 0, 2000);

        s1.changeStar(star, null);
        c1.setSpace_crew(s1);
        c1.addProduct(p1, 100, 0, false, 10, true);
        pl1.setPassword("password");
        pl2.setPassword("password");
        pl3.setPassword("password");
        c1.addPlayer(pl1);
        c1.addPlayer(pl2);
        c1.addPlayer(pl3);

        s2.changeStar(star2, null);
        c2.setSpace_crew(s2);
        c2.addProduct(p1, 100, 0, false, 10, true);

        s3.changeStar(star, null);

        c1.setId(1l);
        c2.setId(2l);
        s1.setId(3l);
        s2.setId(4l);
        s3.setId(5l);
        pl1.setId(6l);

        globalCrew = c1;

        Crewmembers auxCrews[] = { c1, c2 };
        Iterable<Crewmembers> crews = Arrays.asList(auxCrews);

        when(crewmembersRepository.findById(1l)).thenReturn((Optional.of(c1)));
        when(crewmembersRepository.findById(2l)).thenReturn((Optional.of(c2)));
        when(crewmembersRepository.findAll()).thenReturn(crews);
        when(crewmembersRepository.findById(geq(3l))).thenThrow(new NoSuchElementException());
        when(crewmembersRepository.save(c1)).thenReturn(c1);
        when(crewmembersRepository.save(c2)).thenReturn(c2);

        when(spaceshipRepository.findById(3l)).thenReturn((Optional.of(s1)));
        when(spaceshipRepository.findById(4l)).thenReturn((Optional.of(s2)));
        when(spaceshipRepository.findById(5l)).thenReturn((Optional.of(s3)));
        when(spaceshipRepository.save(s1)).thenReturn(s1);
        when(spaceshipRepository.save(s2)).thenReturn(s2);
        when(spaceshipRepository.save(s2)).thenReturn(s3);

        when(playerRepository.findById(6l)).thenReturn((Optional.of(pl1)));
        when(playerRepository.save(pl1)).thenReturn(pl1);

    }

    @Test
    @DisplayName("Test method for changin the crew ship")
    void testChangeShip() throws Exception {
        MockHttpServletRequestBuilder request = put("/crew/change-spaceship?crew_id=" + "1" + "&spaceship_id=" + "5");
        String response_string = mvc.perform(request).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
        Crewmembers crew = new ObjectMapper().readValue(response_string, Crewmembers.class);
        assertTrue(crew.getSpace_crew().getId() == 5l);
    }

    @Test
    @DisplayName("Test method for modifying varios attributes of a crew")
    void testModCrew() throws Exception {
        Gson gson = new Gson();

        Crewmembers modCrew = new Crewmembers();
        modCrew.setCredits(555);
        modCrew.setAccTime(10);

        String modCrewJson = gson.toJson(modCrew);
        MockHttpServletRequestBuilder request = put("/crew?crew_id=" + "2").contentType(MediaType.APPLICATION_JSON)
                .content(modCrewJson);
        String response_string = mvc.perform(request).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
        Crewmembers crew = new ObjectMapper().readValue(response_string, Crewmembers.class);
        assertTrue(crew.getId() == 2l && crew.getAccTime() == 10 && crew.getCredits() == 555);

    }

    @Test
    @DisplayName("Test method for changing crew name")
    void testModCrewName() throws Exception {
        MockHttpServletRequestBuilder request = put("/crew/change_crewName?crew_id=" + "2" + "&newName=monotera");
        String response_string = mvc.perform(request).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
        assertEquals("true", response_string);

    }

    @Test
    @DisplayName("Test method for ending and restarting the game")
    void testGameOver() throws Exception {
        MockHttpServletRequestBuilder request = put("/crew/game-over?player_id=6");
        String response_string = mvc.perform(request).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
        assertEquals("true", response_string);

    }

    @Test
    @DisplayName("Test method for creating a new crew")
    void testCreateCrew() throws Exception {
        Gson gson = new Gson();

        Crewmembers modCrew = new Crewmembers();
        modCrew.setId(88l);
        modCrew.setCrew_name("monotera");
        modCrew.setCredits(555);
        modCrew.setAccTime(10);
        modCrew.setSpace_crew(null);

        String modCrewJson = gson.toJson(modCrew);

        MockHttpServletRequestBuilder request = post("/crew").contentType(MediaType.APPLICATION_JSON)
                .content(modCrewJson);
        String response_string = mvc.perform(request).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
        Crewmembers crew = new ObjectMapper().readValue(response_string, Crewmembers.class);
        assertEquals(modCrew.toString(), crew.toString());

    }

    @Test
    @DisplayName("Test method for deleting a crew")
    void testDeleteCrew() throws Exception {

        MockHttpServletRequestBuilder request = delete("/crew?crew_id=1");
        String response_string = mvc.perform(request).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
        Crewmembers crew = new ObjectMapper().readValue(response_string, Crewmembers.class);
        assertEquals(globalCrew.toString(), crew.toString());

    }
}
