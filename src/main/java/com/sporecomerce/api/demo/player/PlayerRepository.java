package com.sporecomerce.api.demo.player;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {

    @Query(value = "SELECT * FROM PLAYER WHERE PLAYER_NAME = ?1", nativeQuery = true)
    Optional<Player> findByPlayer_name(String player_name);

}
