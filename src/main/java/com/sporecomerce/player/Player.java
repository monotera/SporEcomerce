package com.sporecomerce.player;

public class Player {

    private Long player_id;
    private String player_name;
    private Role player_role;


    public Player() {
    }

    public Player(Long player_id, String player_name, Role player_role) {
        this.player_id = player_id;
        this.player_name = player_name;
        this.player_role = player_role;
    }

    public Long getPlayer_id() {
        return this.player_id;
    }

    public void setPlayer_id(Long player_id) {
        this.player_id = player_id;
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
        return "{" +
            " player_id='" + getPlayer_id() + "'" +
            ", player_name='" + getPlayer_name() + "'" +
            ", player_role='" + getPlayer_role() + "'" +
            "}";
    }

}
