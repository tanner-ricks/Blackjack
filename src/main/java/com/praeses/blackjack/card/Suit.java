package com.praeses.blackjack.card;

public enum Suit {
    SPADES("♠"),
    HEARTS("♥"),
    CLUBS("♣"),
    DIAMONDS("♦");

    private final String suit;

    Suit(String suit) {
        this.suit = suit;
    }

    public String getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return suit;
    }
}
