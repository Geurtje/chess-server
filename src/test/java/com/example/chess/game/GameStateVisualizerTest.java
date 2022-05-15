package com.example.chess.game;

import com.example.chess.game.model.Game;
import com.example.chess.game.model.Player;
import com.example.chess.game.util.BoardTemplate;
import com.example.chess.game.util.GameStateVisualizer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class GameStateVisualizerTest {

    @Disabled
    @Test
    public void printBoard() {
        Game g = new Game("test-game", Player.WHITE, BoardTemplate.createNewBoard());
        System.out.println(GameStateVisualizer.visualizeGameBoard(g));
    }

}
