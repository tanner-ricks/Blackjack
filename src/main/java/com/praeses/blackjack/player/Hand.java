package com.praeses.blackjack.player;

import com.praeses.blackjack.card.Card;
import com.praeses.blackjack.card.Rank;
import com.praeses.blackjack.deck.Deck;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private final List<Card> cards = new ArrayList<>();
    private double bet;
    private boolean doubledDown = false;

    public List<Card> cards() {
        return cards;
    }

    public void draw(Deck deck) {
        cards.add(deck.draw());
    }

    public boolean add(Card card) {
        return cards.add(card);
    }

    public void clear() {
        cards.clear();
    }

    public double getBet() {
        return bet;
    }

    public boolean setBet(double bet) {
        if (bet % 0.50 == 0.0) {
            this.bet = bet;
            return true;
        }
        return false;
    }

    public int value() {
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

    public boolean isBust() {
        return value() > 21;
    }

    public boolean isBlackjack() {
        return cards.size() == 2 && value() == 21;
    }

    public boolean canSplit() {
        return cards.size() == 2 && cards.get(0).getRank() == cards.get(1).getRank();
    }

    public boolean canDoubleDown() {
        return cards.size() == 2;
    }

    public boolean isDoubledDown() {
        return doubledDown;
    }

    public Hand split() {
        Card cardToMove = cards.remove(1);
        Hand newHand = new Hand();
        newHand.add(cardToMove);
        newHand.setBet(bet); // split inherits original bet
        return newHand;
    }

    public void doubleDown() {
        bet *= 2;
    }

}
