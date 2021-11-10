package com.sporecomerce.api.demo.security;

import com.sporecomerce.api.demo.player.Player;
import com.sporecomerce.api.demo.player.PlayerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomPlayerDetailService implements UserDetailsService {

    @Autowired
    private PlayerRepository playerRepository;

    // TODO:cambiar

    @Override
    public UserDetails loadUserByUsername(String player_name) throws UsernameNotFoundException {
        Player player = playerRepository.findByPlayer_name(player_name).orElseThrow();
        return new CustomUserDetails(player);
    }

}
