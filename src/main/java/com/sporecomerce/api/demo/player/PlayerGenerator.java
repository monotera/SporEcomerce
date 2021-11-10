package com.sporecomerce.api.demo.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PlayerGenerator {

    public Boolean initial(Player p1, String pla_id, Role role) {

        Boolean sentry = false;

        p1.setPlayer_name(pla_id);
        p1.setPlayer_role(role);
        sentry = true;
        return sentry;

    }
}
