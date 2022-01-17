package 
pokerhelp;

import java.util.ArrayList;

import pokerParts.AllCards;
import pokerParts.PokerCard;
import pokerParts.PokerHand;
import pokerParts.PokerTable;

public class PokerWinningOddsTesting {
	
	public static void main(String[] args) {
		PokerHand hand1 = new PokerHand(AllCards.getCard('A', 'C'), AllCards.getCard('A', 'S'));
		PokerTable table1 = new PokerTable(hand1);
		PokerHand hand2 = new PokerHand();
//		System.out.println(PokerStats2Players.winningOddsPreFlop(hand1));
		PokerCard flop1 = AllCards.getCard('A', 'C');
		PokerCard flop2 = AllCards.getCard('K', 'C');
		PokerCard flop3 = AllCards.getCard('4', 'S');
		System.out.println(PokerStats2Players.winningOddsGivenFlop(hand1, flop1, flop2, flop3));
		
	}
}