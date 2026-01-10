package com.praeses.blackjack.player;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a blackjack player
 */
public class Player {

    private final String name;
    private final List<Hand> hands = new ArrayList<>();

    private double chips;

    /**
     * Constructs a new player instance
     *
     * @param name The name of the player
     * @param chips The number of chips the player starts with
     */
    public Player(String name, float chips) {
        this.name = name;
        this.chips = chips;
        this.hands.add(new Hand());
    }

    /**
     * Gets the name of the player
     *
     * @return The name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the player's hands
     * @return The player's hands
     */
    public List<Hand> getHands() {
        return hands;
    }

    /**
     * Clears the players hands and reinitializes to one hand
     */
    public void clearHands() {
        hands.clear();
        hands.add(new Hand());
    }

    /**
     * Gets the monetary value of the player's chips
     *
     * @return The monetary value of the player's chips
     */
    public double getChips() {
        return chips;
    }

    /**
     * Adds chips to the player's pool
     *
     * @param amount The amount to be added
     */
    public void payChips(double amount) {
        chips += amount;
    }

    /**
     * Places a bet on a hand
     *
     * @param hand The hand to have its bet set
     * @param bet The bet to be set
     * @return Whether the bet was successful and a valid amount
     */
    public boolean placeBet(Hand hand, double bet) {
        boolean result = hand.setBet(bet);
        if (bet > chips) {
            result = false;
        }
        if (result) {
            chips -= bet;
        }
        return result;
    }

    /**
     * Wins the bet and adds the appropriate amount of chips back
     *
     * @param hand The hand that was won
     */
    public void winBet(Hand hand) {
        if (hand.isBlackjack()) {
            chips += hand.getBet() * 2.5;
        } else {
            chips += hand.getBet() * 2;
        }
    }

    /**
     * Pushes the bet and adds the appropriate amount of chips back
     *
     * @param hand The hand that was pushed
     */
    public void pushBet(Hand hand) {
        chips += hand.getBet();
    }

}
