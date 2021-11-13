package com.sporecomerce.api.demo.player;

public class PlayerGenerator {

    public Boolean initial(Player p1, String pla_id, Role role) {

        Boolean sentry = false;

        p1.setPlayer_name(pla_id);
        p1.setPlayer_role(role);
        sentry = true;
        return sentry;

    }
}
