package com.example.chess.game.util;

import com.example.chess.game.model.Game;
import com.example.chess.piece.Piece;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class GameStateVisualizer {

    public static String visualizeGameBoard(Game game) {
        StringBuilder strBuilder = new StringBuilder();

        strBuilder.append("#######################\n")
                .append("Game: ").append(game.getId()).append("\n")
                .append("Current player: ").append(game.getCurrentPlayer()).append("\n")
                .append("#######################\n\n");

        // columns
        strBuilder.append("  AㅤBㅤCㅤDㅤEㅤFㅤGㅤH\n");

        Piece[][] board = game.getBoard();
        for (int i = board.length-1; i >= 0; i--) {
            Piece[] row = board[i];
            strBuilder.append(i+1).append("|")
                    .append(String.join("|", convertPiecesForDisplay(row)))
                    .append("|").append(i+1).append("\n");
        }

        strBuilder.append("  AㅤBㅤCㅤDㅤEㅤFㅤGㅤH\n");
        return strBuilder.toString();
    }

    private static Collection<String> convertPiecesForDisplay(Piece[] row) {
        return Arrays.stream(row)
                .map(piece -> piece != null ? piece.getSymbol() : "ㅤ")
                .collect(Collectors.toList());
    }
}
