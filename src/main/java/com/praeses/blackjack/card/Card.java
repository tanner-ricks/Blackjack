package com.praeses.blackjack.card;

public class Card {

    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public int getValue() {
        return rank.getRank();
    }

    public int getValueAlt() {
        return rank.getRankAlt();
    }

    @Override
    public String toString() {
        if (rank.getRankAlt() == -1) {
            return String.format("%s%d", suit.getSuit(), rank.getRank());
        }
        return String.format("%s%d or %s%d", suit.getSuit(), rank.getRank(), suit.getSuit(), rank.getRankAlt());
    }

}
