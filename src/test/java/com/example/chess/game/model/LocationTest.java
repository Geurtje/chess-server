package com.example.chess.game.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LocationTest {

    @ParameterizedTest
    @MethodSource("TestValidLocationPatternArguments")
    public void TestValidLocationPattern(String locationText, int expectedX, int expectedY) {
        Location l = Location.fromText(locationText);
        assertThat(l.x)
                .withFailMessage("For location text %s, expected X coord '%d', got '%d'", locationText, expectedX, l.x)
                .isEqualTo(expectedX);
        assertThat(l.y)
                .withFailMessage("For location text %s, expected Y coord '%d', got '%d'", locationText, expectedY, l.y)
                .isEqualTo(expectedY);
    }

    private static Stream<Arguments> TestValidLocationPatternArguments() {
        return Stream.of(
                Arguments.of("A1", 0, 0),
                Arguments.of("B1", 1, 0),
                Arguments.of("H8", 7, 7),
                Arguments.of("h8", 7, 7),
                Arguments.of("D6", 3, 5),
                Arguments.of("f4", 5, 3)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"X2", "T1", "J7"})
    public void TestLocationWithInvalidColumnLetterThrowsException(String locationText) {
        Exception e = assertThrows(IllegalArgumentException.class, () -> Location.fromText(locationText));
        assertThat(e.getMessage()).contains("Unknown row letter");
    }

    @ParameterizedTest
    @ValueSource(strings = {"A0", "A9"})
    public void TestLocationWithInvalidRowNumberThrowsException(String locationText) {
        Exception e = assertThrows(IllegalArgumentException.class, () -> Location.fromText(locationText));
        assertThat(e.getMessage()).contains("out of bounds");
    }
}
