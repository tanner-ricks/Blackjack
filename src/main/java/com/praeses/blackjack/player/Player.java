package com.praeses.blackjack.player;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final String name;
    private final List<Hand> hands = new ArrayList<>();

    private double chips;

    public Player(String name, float chips) {
        this.name = name;
        this.chips = chips;
        this.hands.add(new Hand());
    }

    public String getName() {
        return name;
    }

    public List<Hand> getHands() {
        return hands;
    }

    public void clearHands() {
        hands.clear();
        hands.add(new Hand());
    }

    public double getChips() {
        return chips;
    }

    public void payChips(double amount) {
        chips += amount;
    }

    public boolean placeBet(double bet) {
        boolean result = hands.get(0).setBet(bet);
        if (result) {
            chips -= bet;
        }
        return result;
    }

    public void winBet(Hand hand) {
        if (hand.isBlackjack()) {
            chips += hand.getBet() * 2.5;
        } else {
            chips += hand.getBet() * 2;
        }
    }

    public void pushBet(Hand hand) {
        chips += hand.getBet();
    }

}
