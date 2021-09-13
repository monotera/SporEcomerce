package com.sporecomerce.api.demo.player;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sporecomerce.api.demo.crewmembers.Crewmembers;

@Entity
public class Player {

    @Id
    @GeneratedValue
    private Long id;
    private String player_name;
    @Enumerated(EnumType.ORDINAL)
    private Role player_role;

    @ManyToOne
    @JsonManagedReference
    private Crewmembers crewmembers;

    public Player() {
    }

    public Player(String player_name, Role player_role) {
        this.player_name = player_name;
        this.player_role = player_role;
    }

    public Crewmembers getCrew_players() {
        return crewmembers;
    }

    public void setCrew_players(Crewmembers crew_players) {
        this.crewmembers = crew_players;
    }

    public Long getid() {
        return this.id;
    }

    public void setid(Long id) {
        this.id = id;
    }

    public String getPlayer_name() {
        return this.player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public Role getPlayer_role() {
        return this.player_role;
    }

    public void setPlayer_role(Role player_role) {
        this.player_role = player_role;
    }

    @Override
    public String toString() {
        return "{" + " id='" + getid() + "'" + ", player_name='" + getPlayer_name() + "'" + ", player_role='"
                + getPlayer_role() + "'" + "}";
    }

}
