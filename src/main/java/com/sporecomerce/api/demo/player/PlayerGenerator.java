package com.sporecomerce.api.demo.player;


public class PlayerGenerator {
    public Boolean initial(Player p1, Long pla_id) {
        Boolean sentry = false;

        p1.setId(pla_id);
        p1.setPlayer_role(Role.CAPTAIN);

        sentry = true;
        return sentry;
    }
}
