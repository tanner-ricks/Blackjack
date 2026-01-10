package com.praeses.blackjack.player;

import com.praeses.blackjack.card.Card;
import com.praeses.blackjack.card.Rank;
import com.praeses.blackjack.deck.Deck;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single hand of blackjack
 */
public class Hand {

    private final List<Card> cards = new ArrayList<>();
    private double bet;

    /**
     * Gets the list of cards in this hand
     * 
     * @return The list of cards in this hand
     */
    public List<Card> cards() {
        return cards;
    }

    /**
     * Draws a card from the deck and places it in this hand
     *
     * @param deck The deck to be drawn from
     */
    public void draw(Deck deck) {
        cards.add(deck.draw());
    }

    /**
     * Adds a card to this hand
     *
     * @param card The card to be added to the hand
     * @return Whether the card was successfully added
     */
    public boolean add(Card card) {
        return cards.add(card);
    }

    /**
     * Gets the bet for this hand
     *
     * @return The bet for this hand
     */
    public double getBet() {
        return bet;
    }

    /**
     * Sets the bet for this hand if it is a valid bet
     *
     * @param bet The bet to be set
     * @return Whether the bet was valid
     */
    public boolean setBet(double bet) {
        if (bet % 5.0 == 0.0) {
            this.bet = bet;
            return true;
        }
        return false;
    }

    /**
     * Gets the value for this hand using blackjack logic
     *
     * @return The value for this hand using blackjack logic
     */
    public int getValue() {
        int total = 0;

        for (Card card : cards) {
            if (card.getRank() == Rank.ACE && total + card.getValue() > 21) {
                total += card.getValueAlt();
            } else {
                total += card.getValue();
            }
        }

        return total;
    }

    /**
     * Gets whether the hand is bust
     *
     * @return Whether the hand is bust
     */
    public boolean isBust() {
        return getValue() > 21;
    }

    /**
     * Gets whether the hand is blackjack
     *
     * @return Whether the hand is blackjack
     */
    public boolean isBlackjack() {
        return cards.size() == 2 && getValue() == 21;
    }

    /**
     * Gets whether the hand can be split
     *
     * @return Whether the hand can be split
     */
    public boolean canSplit() {
        return cards.size() == 2 && cards.get(0).getRank() == cards.get(1).getRank();
    }

    /**
     * Gets whether the hand can be doubled down
     *
     * @return Whether the hand can be doubled down
     */
    public boolean canDoubleDown() {
        return cards.size() == 2;
    }

    /**
     * Splits the hand and returns the new hand
     *
     * @return The new hand that was split off
     */
    public Hand split() {
        Card cardToMove = cards.remove(1);
        Hand newHand = new Hand();
        newHand.add(cardToMove);
        newHand.setBet(bet); // split inherits original bet
        return newHand;
    }

    /**
     * Doubles down the bet
     */
    public void doubleDown() {
        bet *= 2;
    }

}
