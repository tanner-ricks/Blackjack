package com.praeses.blackjack.card;

/**
 * Represents a standard playing card
 */
public class Card {

    private final Rank rank;
    private final Suit suit;

    /**
     * Constructor
     *
     * @param rank The rank of the card, i.e. the numerical value
     * @param suit The suit of the card, one of "♠", "♥", "♣", "♦"
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Gets the rank of the card
     *
     * @return The Rank of the card
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Gets the numeric value of the card
     *
     * @return The numeric value of the card
     */
    public int getValue() {
        return rank.getRank();
    }

    /**
     * Gets the alternative numeric value of the card
     *
     * @return The alternative numeric value of the card
     */
    public int getValueAlt() {
        return rank.getRankAlt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if (rank.getRankAlt() == -1) {
            return String.format("%s%d", suit.getSuit(), rank.getRank());
        }
        return String.format("%s%d or %s%d", suit.getSuit(), rank.getRank(), suit.getSuit(), rank.getRankAlt());
    }

}
