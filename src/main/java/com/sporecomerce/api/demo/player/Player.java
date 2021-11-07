package com.sporecomerce.api.demo.player;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.sporecomerce.api.demo.crewmembers.Crewmembers;

@Entity
public class Player {

    @Id
    @GeneratedValue
    private Long id;
    private String player_name;
    @Enumerated(EnumType.ORDINAL)
    private Role player_role;
    private String password;

    @ManyToOne
    private Crewmembers crewmembers;

    public Player() {
    }

    public Player(String player_name, Role player_role) {
        this.player_name = player_name;
        this.player_role = player_role;
    }

    public Player(String player_name, Role player_role, String password, Crewmembers crewmembers) {
        this.player_name = player_name;
        this.player_role = player_role;
        this.password = password;
        this.crewmembers = crewmembers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Crewmembers getCrewmembers() {
        return crewmembers;
    }

    public void setCrewmembers(Crewmembers crewmembers) {
        this.crewmembers = crewmembers;
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

}
