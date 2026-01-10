package com.praeses.blackjack.game;

import com.praeses.blackjack.deck.Deck;
import com.praeses.blackjack.player.Hand;
import com.praeses.blackjack.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {

    private final Scanner scanner = new Scanner(System.in);
    private final Deck deck = new Deck();
    private final List<Player> players = new ArrayList<>();
    private final Player dealer = new Player("Dealer", 0);

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

    private void collectBets() {
        for (Player player : players) {
            boolean success = false;
            while (!success) {
                try {
                    System.out.print(player.getName() + " (" + player.getChips() + " chips), place bet: ");
                    String choice = scanner.nextLine().trim();
                    int bet = Integer.parseInt(choice);
                    if (player.placeBet(bet)) {
                        success = true;
                    } else {
                        System.out.println("Bets must be in multiples of 0.5");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Bets must be in multiples of 0.5");
                }
            }
        }
    }

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

    private void playerTurns() {
        for (Player player : players) {
            List<Hand> playerHands = new ArrayList<>(player.getHands());
            for (int h = 0; h < playerHands.size(); h++) {
                Hand hand = playerHands.get(h);
                playHand(player, hand, playerHands);
            }
        }
    }

    private void playHand(Player player, Hand hand, List<Hand> allHands) {
        while (true) {
            printHand(dealer, dealer.getHands().get(0), true);
            printHand(player, hand, false);

            // Check split
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
                    player.getHands().add(newHand);
                    continue;
                }
            }

            // Check double down
            if (hand.cards().size() == 2 && player.getChips() >= hand.getBet()) {
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

            // Regular hit/stand
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

    private void dealerTurn() {
        Hand dealerHand = dealer.getHands().get(0);
        while (dealerHand.value() < 17) {
            dealerHand.draw(deck);
        }
        System.out.println();
        printHand(dealer, dealerHand, false);
    }

    private void settleBets() {
        Hand dealerHand = dealer.getHands().get(0);
        boolean dealerBlackjack = dealerHand.isBlackjack();
        for (Player player : players) {
            for (Hand hand : player.getHands()) {
                printHand(dealer, dealer.getHands().get(0), true);
                printHand(player, hand, false);
                boolean playerBlackjack = hand.isBlackjack();
                int playerScore = hand.value();

                if (playerBlackjack) {
                    if (!dealerBlackjack) {
                        // Blackjack payout 3:2
                        player.payChips(hand.getBet() * 2.5);
                        System.out.println(player.getName() + " has Blackjack! Wins " + hand.getBet() * 2.5 + " chips!");
                    } else {
                        player.pushBet(hand);
                        System.out.println(player.getName() + " and Dealer both have Blackjack. Push.");
                    }
                } else if (hand.isBust()) {
                    System.out.println(player.getName() + " busts and loses " + hand.getBet() + " chips.");
                } else if (dealerHand.isBust() || playerScore > dealerHand.value()) {
                    player.winBet(hand);
                    System.out.println(player.getName() + " wins " + hand.getBet() + " chips.");
                } else if (playerScore == dealerHand.value()) {
                    player.pushBet(hand);
                    System.out.println(player.getName() + " pushes. Bet returned.");
                } else {
                    System.out.println(player.getName() + " loses " + hand.getBet() + " chips.");
                }

                System.out.println(player.getName() + " now has " + player.getChips() + " chips.");
            }
            player.clearHands();
        }
        dealer.clearHands();
    }

    private void printHand(Player player, Hand hand, boolean hideFirst) {
        System.out.print(player.getName() + ": ");
        for (int i = 0; i < hand.cards().size(); i++) {
            if (hideFirst && i == 0) {
                System.out.print("[hidden] ");
            } else {
                System.out.print("[" + hand.cards().get(i) + "] ");
            }
        }
        if (!hideFirst) {
            System.out.print("=> " + hand.value());
        }
        System.out.println();
    }

}
