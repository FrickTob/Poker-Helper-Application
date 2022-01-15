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
		System.out.println(PokerStats2Players.winningOddsPreFlop(hand1));
		
	}
}