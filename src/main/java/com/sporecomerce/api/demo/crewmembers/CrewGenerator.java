package com.sporecomerce.api.demo.crewmembers;

import java.util.ArrayList;

import com.sporecomerce.api.demo.player.Player;
import com.sporecomerce.api.demo.spaceship.Spaceship;

public class CrewGenerator {
    //Initial values
    public Boolean initial(Crewmembers crew, Long crew_id, Spaceship ship) {
        Boolean sentry = false;
        crew.setAccTime(0);
        crew.setCredits(1000000);
        crew.setId(crew_id);
        crew.setSpace_crew(ship);
        sentry = true;
        return sentry;
    }

    //Adds a player to a crew, verifies that the crew does not exceed the set size
    public Boolean addPlayer(Crewmembers crew, Player P1){
        Boolean sentry = false;
        ArrayList<Player> cPlayer = new ArrayList<>(crew.getPlayer_list());
    
        if(cPlayer.size() < 11){
            cPlayer.add(P1);
            sentry = true;
        }
        return sentry;
    }

    //Remove a player from a crew
    public Boolean deletePlayer(Crewmembers crew, Long idP1){
        ArrayList<Player> cPlayer = new ArrayList<>(crew.getPlayer_list());
        Boolean sentry = false;

        for (int i = 0; i < cPlayer.size(); i++) {
            if(cPlayer.get(i).getId() == idP1){
                cPlayer.remove(i);
                sentry = true;
            }
        }
        return sentry;
    }
}
