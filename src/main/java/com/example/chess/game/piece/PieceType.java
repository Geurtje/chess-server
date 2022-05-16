package com.example.chess.game.piece;

import java.util.Collections;
import java.util.List;

public enum PieceType {
    PAWN(Collections.emptyList()),
    ROOK(MovementOffsets.ROOK),
    KNIGHT(MovementOffsets.KNIGHT),
    BISHOP(MovementOffsets.BISHOP),
    QUEEN(MovementOffsets.QUEEN),
    KING(MovementOffsets.KING);

    final List<MovementOffset> movementOffsets;

    PieceType(List<MovementOffset> movementOffsets) {
        this.movementOffsets = movementOffsets;
    }
}
