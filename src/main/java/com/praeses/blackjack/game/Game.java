package com.praeses.blackjack.game;

import com.praeses.blackjack.card.Card;
import com.praeses.blackjack.deck.Deck;
import com.praeses.blackjack.player.Hand;
import com.praeses.blackjack.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a game of blackjack
 */
public class Game {

    private final Scanner scanner = new Scanner(System.in);
    private final Deck deck = new Deck();
    private final List<Player> players = new ArrayList<>();
    private final Player dealer = new Player("Dealer", 0);

    /**
     * Main play loop for the game
     */
    public void play() {
        setupPlayers();

        boolean keepPlaying = true;
        while (keepPlaying) {
            // Single round
            collectBets();
            initialDeal();
            playerTurns();
            dealerTurn();
            settleBets();

            String choice = null;
            boolean valid = false;
            while (!valid) {
                System.out.print("Do you wish to continue playing? (y/n): ");
                choice = scanner.nextLine().trim().toLowerCase();
                if (!Arrays.asList("y", "n").contains(choice)) {
                    System.out.println("Invalid selection...");
                } else {
                    valid = true;
                }
            }
            if ("n".equals(choice)) {
                keepPlaying = false;
            }

        }
    }

    /**
     * Sets up the players for the game by asking for the
     * number of players and their names
     */
    private void setupPlayers() {
        String choice = null;
        boolean valid = false;
        while(!valid) {
            System.out.print("Number of players (1-4): ");
            choice = scanner.nextLine().trim();
            if (!Arrays.asList("1", "2", "3","4").contains(choice)) {
                System.out.println("Invalid selection...");
            } else {
                valid = true;
            }
        }

        int playerCount = Integer.parseInt(choice);
        for (int i = 1; i <= playerCount; i++) {
            System.out.print("Enter name for player " + i + ": ");
            String name = scanner.nextLine();
            players.add(new Player(name, 100));
        }
    }

    /**
     * Collects the bets for the starting hands
     */
    private void collectBets() {
        for (Player player : players) {
            boolean success = false;
            int bet = 0;
            while (!success) {
                try {
                    System.out.print(player.getName() + " (" + player.getChips() + " chips), place bet: ");
                    String choice = scanner.nextLine().trim();
                    bet = Integer.parseInt(choice);
                    if (player.placeBet(player.getHands().get(0), bet)) {
                        success = true;
                    } else {
                        System.out.println("Bets must be in multiples of 5.0");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Bets must be in multiples of 5.0");
                }
            }
        }
    }

    /**
     * Deals the initial cards to each player and the dealer
     */
    private void initialDeal() {
        deck.shuffle();
        deck.shuffle();
        for (Player player : players) {
            Hand hand = player.getHands().get(0);
            hand.draw(deck);
            hand.draw(deck);
        }
        Hand dealerHand = dealer.getHands().get(0);
        dealerHand.draw(deck);
        dealerHand.draw(deck);
    }

    /**
     * Player turn loop
     */
    private void playerTurns() {
        for (Player player : players) {
            System.out.println("------------------------------------------------------------------------------------");
            int handIndex = 0;
            List<Hand> playerHands = player.getHands();
            while(handIndex < playerHands.size()) {
                Hand hand = playerHands.get(handIndex);
                playHand(player, hand, playerHands);
                handIndex++;
            }
        }
    }

    /**
     * Plays a hand of blackjack allowing for splitting,
     * doubling down, hitting, and standing
     *
     * @param player The player playing the hand
     * @param hand The hand being played
     * @param allHands All of the player's hands
     */
    private void playHand(Player player, Hand hand, List<Hand> allHands) {
        printHand(dealer, dealer.getHands().get(0), true);
        while (true) {
            printHand(player, hand, false);

            if (hand.canSplit() && player.getChips() >= hand.getBet()) {
                String choice = null;
                boolean valid = false;
                while (!valid) {
                    System.out.print(player.getName() + ", do you want to split? (y/n): ");
                    choice = scanner.nextLine().trim().toLowerCase();
                    if (!Arrays.asList("y", "n").contains(choice)) {
                        System.out.println("Invalid selection...");
                    } else {
                        valid = true;
                    }
                }

                if ("y".equals(choice)) {
                    Hand newHand = hand.split();
                    hand.add(deck.draw());
                    newHand.add(deck.draw());
                    allHands.add(newHand);
                    player.placeBet(newHand, hand.getBet());
                    continue;
                }
            }

            if (hand.canDoubleDown() && player.getChips() >= hand.getBet()) {
                String choice = null;
                boolean valid = false;
                while (!valid) {
                    System.out.print(player.getName() + ", do you want to double down? (y/n): ");
                    choice = scanner.nextLine().trim().toLowerCase();
                    if (!Arrays.asList("y", "n").contains(choice)) {
                        System.out.println("Invalid selection...");
                    } else {
                        valid = true;
                    }
                }

                if ("y".equals(choice)) {
                    player.payChips(-hand.getBet()); // deduct bet for doubling
                    hand.doubleDown();
                    hand.add(deck.draw());
                    System.out.println(player.getName() + " doubled down.");
                    break;
                }
            }

            String choice = null;
            boolean valid = false;
            while (!valid) {
                System.out.print(player.getName() + ", hit or stand? (h/s): ");
                choice = scanner.nextLine().trim().toLowerCase();
                if (!Arrays.asList("h", "s").contains(choice)) {
                    System.out.println("Invalid selection...");
                } else {
                    valid = true;
                }
            }

            if ("h".equals(choice)) {
                hand.add(deck.draw());
                if (hand.isBust()) {
                    System.out.println(player.getName() + " busts!");
                    break;
                }
            } else if ("s".equals(choice)) {
                break;
            }
        }
    }

    /**
     * Performs the dealers turn
     */
    private void dealerTurn() {
        Hand dealerHand = dealer.getHands().get(0);
        while (dealerHand.getValue() < 17) {
            dealerHand.draw(deck);
        }
        System.out.println("------------------------------------------------------------------------------------");
        printHand(dealer, dealerHand, false);
    }

    /**
     * Handles all of the win/lose conditions and modifies
     * the players ships accordingly
     */
    private void settleBets() {
        Hand dealerHand = dealer.getHands().get(0);
        boolean dealerBlackjack = dealerHand.isBlackjack();
        for (Player player : players) {
            for (Hand hand : player.getHands()) {
                printHand(player, hand, false);
                boolean playerBlackjack = hand.isBlackjack();
                int playerScore = hand.getValue();

                if (playerBlackjack) {
                    if (!dealerBlackjack) {
                        player.winBet(hand);
                        System.out.println(player.getName() + " has Blackjack! Wins " + hand.getBet() * 1.5 + " chips!");
                    } else {
                        player.pushBet(hand);
                        System.out.println(player.getName() + " and Dealer both have Blackjack. Push.");
                    }
                } else if (hand.isBust()) {
                    System.out.println(player.getName() + " busts and loses " + hand.getBet() + " chips.");
                } else if (dealerHand.isBust() || playerScore > dealerHand.getValue()) {
                    player.winBet(hand);
                    System.out.println(player.getName() + " wins " + hand.getBet() + " chips.");
                } else if (playerScore == dealerHand.getValue()) {
                    player.pushBet(hand);
                    System.out.println(player.getName() + " pushes. Bet returned.");
                } else {
                    System.out.println(player.getName() + " loses " + hand.getBet() + " chips.");
                }
            }
            System.out.println(player.getName() + " now has " + player.getChips() + " chips.");
            player.clearHands();
        }
        dealer.clearHands();
    }

    /**
     * Prints out the player and cards in a hand
     * @param player The player having their hand printed
     * @param hand The hand being printed
     * @param hideFirst Whether the first card should be hidden
     */
    private void printHand(Player player, Hand hand, boolean hideFirst) {
        System.out.print(player.getName() + ": ");
        for (int i = 0; i < hand.cards().size(); i++) {
            Card card = hand.cards().get(i);
            if (hideFirst && i == 0) {
                    System.out.print("[hidden] ");
            } else {
                System.out.print("[" + card + "] ");
            }
        }
        if (!hideFirst) {
            System.out.print("=> " + hand.getValue());
        }
        System.out.println();
    }

}
