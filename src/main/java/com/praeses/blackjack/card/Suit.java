package com.praeses.blackjack.card;

/**
 * Represents the suit of a playing card.
 */
public enum Suit {
    SPADES("♠"),
    HEARTS("♥"),
    CLUBS("♣"),
    DIAMONDS("♦");

    private final String suit;

    /**
     * Constructs a new instance of the suit enum
     *
     * @param suit THe symbol to represent the suit
     */
    Suit(String suit) {
        this.suit = suit;
    }

    /**
     * Gets the symbol that represents the suit
     *
     * @return The symbol that represents the suit like "♠", "♥", "♣", "♦"
     */
    public String getSuit() {
        return suit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return suit;
    }
}
