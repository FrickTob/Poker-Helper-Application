package pokerhelp;

import java.util.ArrayList;

import pokerParts.AllCards;
import pokerParts.PokerCard;
import pokerParts.PokerHand;
import pokerParts.PokerTable;

public class PokerStats2Players {
	
	
	public static float winningOddsPreFlop(PokerHand hand) {
		ArrayList<PokerCard> remainingCards = new ArrayList<PokerCard>();
		for(int i = 0; i < AllCards.NUMCARDS; i++)
			remainingCards.add(AllCards.getCard(i));
		remainingCards.remove(hand.getCard1());
		remainingCards.remove(hand.getCard2());
		
		float winningOdds = 0;
		int numTables = 0;
		int numCards = remainingCards.size();
		for(int flop1Index = 0; flop1Index < numCards - 4; flop1Index++) {
			for(int flop2Index = flop1Index + 1; flop2Index < numCards - 3; flop2Index++) {
				for(int flop3Index = flop2Index + 1; flop3Index < numCards - 2; flop3Index++) {
					for(int turnIndex = flop3Index + 1; turnIndex < numCards - 1; turnIndex++) {
						for(int riverIndex = turnIndex + 1; riverIndex < numCards; riverIndex++) {
							winningOdds += winningOddsGivenTable(hand, new PokerTable(remainingCards.get(flop1Index), 
																					  remainingCards.get(flop2Index), 
																					  remainingCards.get(flop3Index), 
																					  remainingCards.get(turnIndex), 
																					  remainingCards.get(riverIndex)));
							System.out.println("1:" + flop1Index + " 2:" + flop2Index + " 3:" + flop3Index + " 4:" + turnIndex + " 5:" + riverIndex);
							numTables++;
						}
					}
				}
			}
		}
		System.out.println("Number of Tables: " + numTables);
		System.out.println("Total winning Odds: " + winningOdds);
		return winningOdds / numTables;
	}
	
	public static float winningOddsGivenTable(PokerHand hand, PokerTable table) {
		ArrayList<PokerCard> remainingCards = new ArrayList<PokerCard>();
		for(int i = 0; i < AllCards.NUMCARDS; i++)
			remainingCards.add(AllCards.getCard(i));
		remainingCards.remove(hand.getCard1());
		remainingCards.remove(hand.getCard2());
		remainingCards.remove(table.getFlop1());
		remainingCards.remove(table.getFlop2());
		remainingCards.remove(table.getFlop3());
		remainingCards.remove(table.getTurn());
		remainingCards.remove(table.getRiver());
		
		float numWins = 0;
		int numIterations = 0;
		int numCards = remainingCards.size();
		for(int card1Index = 0; card1Index < numCards; card1Index++) {
			for(int card2Index = card1Index + 1; card2Index < numCards; card2Index++) {
				PokerHand currHand = new PokerHand(remainingCards.get(card1Index), remainingCards.get(card2Index));
				if(CompareTwoHands.determineWinner(table, hand, currHand) == 1)// hand beat currHand
					numWins++;
				numIterations++;
			}
		}
		return numWins / numIterations;
	}
}
