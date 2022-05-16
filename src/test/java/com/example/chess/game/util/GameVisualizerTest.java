package com.example.chess.game.util;

import com.example.chess.game.Game;
import com.example.chess.game.model.Player;
import com.example.chess.game.util.BoardTemplate;
import com.example.chess.game.util.GameVisualizer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class GameVisualizerTest {

    @Disabled
    @Test
    public void printBoard() {
        Game g = new Game("test-game", Player.WHITE, BoardTemplate.createNewBoard());
        System.out.println(GameVisualizer.visualizeGame(g));
    }

}
