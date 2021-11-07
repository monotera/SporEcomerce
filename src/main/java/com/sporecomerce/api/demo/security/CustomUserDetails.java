package com.sporecomerce.api.demo.security;

import java.util.Arrays;
import java.util.Collection;

import com.sporecomerce.api.demo.player.Player;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private Player player;

    public CustomUserDetails(Player player) {
        this.player = player;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Arrays.asList(new SimpleGrantedAuthority(player.getPlayer_role().toString()));
    }

    @Override
    public String getPassword() {
        return player.getPassword();
    }

    @Override
    public String getUsername() {
        return player.getPlayer_name();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}
