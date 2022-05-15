package com.example.chess.piece;

import com.example.chess.game.model.Player;

public enum PieceType {
    PAWN("♙", "♟"),
    ROOK("♖", "♜"),
    KNIGHT("♘", "♞"),
    BISHOP("♗", "♝"),
    QUEEN("♕", "♛"),
    KING("♔", "♚");

    private String symbolWhite;
    private String symbolBlack;

    PieceType(String symbolWhite, String symbolBlack) {
        this.symbolWhite = symbolWhite;
        this.symbolBlack = symbolBlack;
    }

    public String getSymbolForPlayer(Player player) {
        if (player == Player.WHITE) {
            return symbolWhite;
        }
        return symbolBlack;
    }

}
