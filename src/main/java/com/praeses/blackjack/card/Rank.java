package com.praeses.blackjack.card;

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
    ACE(11);

    private final int rank;
    private final int rankAlt;

    Rank(int rank) {
        this.rank = rank;
        this.rankAlt = -1;
    }

    public int getRank() {
        return rank;
    }

    public int getRankAlt() {
        return rankAlt;
    }

    @Override
    public String toString() {
        if (rankAlt == -1) {
            return String.format("%d", rank);
        }
        return String.format("%d or %d", rank, rankAlt);
    }

}
