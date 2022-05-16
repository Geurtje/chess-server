package com.example.chess.game;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class GameRepository {

    // Given the scope of the project we'll just store all games in memory
    private final Map<String, Game> gameMap = new ConcurrentHashMap<>();

    public Optional<Game> getGame(String gameId) {
        // todo: clone the game when retrieving it to avoid updating the "stored" object
        // wouldn't be a problem if some real persistence was used
        return Optional.ofNullable(gameMap.get(gameId));
    }

    public void saveGame(Game game) {
        gameMap.put(game.getId(), game);
    }

    public String generateGameId() {
        String randomID;
        int generationAttempts = 0;

        // Whilst it's extremely unlikely to have id collisions with this approach, we should ensure to not override
        // existing games
        do {
            generationAttempts++;
            if (generationAttempts >= 500) {
                throw new RuntimeException("failed to generate unique game id in 500 attempts, please try again");
            }
            randomID = UUID.randomUUID().toString().substring(0, 8);
        } while (gameMap.containsKey(randomID));

        return randomID;
    }

}
