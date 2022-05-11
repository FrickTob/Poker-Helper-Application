package pokerhelp;

import java.util.ArrayList;

import pokerParts.AllCards;
import pokerParts.PokerCard;
import pokerParts.PokerHand;
import pokerParts.PokerTable;

public class Stats2Player {
	
	
	public float winningOddsPreFlop(PokerHand hand) {
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
	
	public float winningOddsGivenTable(PokerHand hand, PokerTable table) {
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
	
	public float winningOddsGivenFlop(PokerHand hand, PokerCard flop1, PokerCard flop2, PokerCard flop3) {
		ArrayList<PokerCard> remainingCards = new ArrayList<>();
		for(int i = 0; i < AllCards.NUMCARDS; i++)
			remainingCards.add(AllCards.getCard(i));
		remainingCards.remove(hand.getCard1());
		remainingCards.remove(hand.getCard2());
		remainingCards.remove(flop1);
		remainingCards.remove(flop2);
		remainingCards.remove(flop3);
		
		float sumOfAverages = 0;
		int numTables = 0;
		int numCards = remainingCards.size();
		
		for(int turnIndex = 0; turnIndex < numCards; turnIndex++) {
			sumOfAverages += winningOddsGivenTurn(hand, flop1, flop2, flop3, remainingCards.get(turnIndex));
			numTables++;
		}
		return sumOfAverages / numTables;
	}
	
	public float winningOddsGivenTurn(PokerHand hand, PokerCard flop1, PokerCard flop2, PokerCard flop3, PokerCard turn) {
		ArrayList<PokerCard> remainingCards = new ArrayList<>();
		for(int i = 0; i < AllCards.NUMCARDS; i++)
			remainingCards.add(AllCards.getCard(i));
		remainingCards.remove(hand.getCard1());
		remainingCards.remove(hand.getCard2());
		remainingCards.remove(flop1);
		remainingCards.remove(flop2);
		remainingCards.remove(flop3);
		remainingCards.remove(turn);
		
		
		float sumOfAverages = 0;
		int numTables = 0;
		int numCards = remainingCards.size();
		
		for(int riverIndex = 0; riverIndex < numCards; riverIndex++) {
			PokerTable currTable = new PokerTable(flop1, flop2, flop3, turn, remainingCards.get(riverIndex));
			sumOfAverages += winningOddsGivenTable(hand, currTable);
			numTables++;
		}
		return sumOfAverages / numTables;
	}
	
	public float winningOddsGivenHands(PokerHand yourHand, PokerHand hand2) {
		ArrayList<PokerCard> remainingCards = new ArrayList<>();
		for(int i = 0; i < AllCards.NUMCARDS; i++)
			remainingCards.add(AllCards.getCard(i));
		remainingCards.remove(yourHand.getCard1());
		remainingCards.remove(yourHand.getCard2());
		remainingCards.remove(hand2.getCard1());
		remainingCards.remove(hand2.getCard2());
		
		float sumOfAverages = 0;
		int numTables = 0;
		int numCards = remainingCards.size();
		
		for(int flop1Index = 0; flop1Index < numCards - 4; flop1Index++) {
			for(int flop2Index = flop1Index + 1; flop2Index < numCards - 3; flop2Index++) {
				for(int flop3Index = flop2Index + 1; flop3Index < numCards - 2; flop3Index++) {
					for(int turnIndex = flop3Index + 1; turnIndex < numCards - 1; turnIndex++) {
						for(int riverIndex = turnIndex + 1; riverIndex < numCards; riverIndex++) {
							PokerTable currTable = new PokerTable(remainingCards.get(flop1Index), 
																  remainingCards.get(flop2Index), 
																  remainingCards.get(flop3Index), 
																  remainingCards.get(turnIndex), 
																  remainingCards.get(riverIndex));
							 if(CompareTwoHands.determineWinner(currTable, yourHand, hand2) == 1)
								 sumOfAverages += 1;
							 numTables++;
						}
					}
				}
			}
		}
		return sumOfAverages / numTables;
							
	}
}
