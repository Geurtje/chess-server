package com.example.chess.game;

import com.example.chess.game.model.Game;
import org.junit.jupiter.api.Test;

public class GameStateVisualizerTest {

    @Test
    public void printBoard() {
        Game g = new Game("asdsdfsd");
        System.out.println(GameStateVisualizer.visualizeGameBoard(g));
    }

}
