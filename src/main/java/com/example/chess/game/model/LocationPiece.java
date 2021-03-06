package com.example.chess.game.model;

import com.example.chess.game.piece.Piece;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LocationPiece {
    public final Location location;
    public final Piece piece;
}
