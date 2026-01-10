package com.praeses.blackjack.deck;

import com.praeses.blackjack.card.Card;
import com.praeses.blackjack.card.Rank;
import com.praeses.blackjack.card.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a standard deck of cards
 */
public class Deck {

    private final List<Card> cards = new ArrayList<>();

    /**
     * Creates a deck of cards
     */
    public Deck() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    /**
     * Shuffles the deck
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Draws the top card from the deck
     *
     * @return the drawn card
     */
    public Card draw() {
        return cards.remove(cards.size() - 1);
    }

}
