package com.example.chess.piece;

import com.example.chess.game.model.Player;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Piece {
    public final PieceType pieceType;
    public final Player owner;

    @Override
    public String toString() {
        return owner + " " + pieceType;
    }

    public String getSymbol() {
        return pieceType.getSymbolForPlayer(owner);
    }
}
