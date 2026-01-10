package com.praeses.blackjack.card;

/**
 * Represents the rank of a playing card.
 */
public enum Rank {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(10),
    QUEEN(10),
    KING(10),
    ACE(11, 1);

    private final int rank;
    private final int rankAlt;

    /**
     * Constructs a new instance of the Rank enum
     *
     * @param rank The numerical value of the enum
     */
    Rank(int rank) {
        this(rank, -1);
    }

    /**
     * Constructs a new instance of the Rank enum
     *
     * @param rank The numerical value of the enum
     * @param rankAlt The alternative numerical value of the enum
     */
    Rank(int rank, int rankAlt) {
        this.rank = rank;
        this.rankAlt = rankAlt;
    }

    /**
     * Gets the numeric value for calculations
     *
     * @return The numeric value for calculations
     */
    public int getRank() {
        return rank;
    }

    /**
     * Gets the alternative numeric value for calculations
     *
     * @return The alternative numeric value for calculations
     */
    public int getRankAlt() {
        return rankAlt;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if (rankAlt == -1) {
            return String.format("%d", rank);
        }
        return String.format("%d or %d", rank, rankAlt);
    }

}
