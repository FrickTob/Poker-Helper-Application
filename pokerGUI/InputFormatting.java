package pokerGUI;



public class InputFormatting {
	
	public static boolean isProperlyFormattedHand(String hand) {
		if(hand.length() != 4) return false;
		
		String card1 = hand.substring(0, 2);
		String card2 = hand.substring(2, 4);
		if(!isPossibleCard(card1) || !isPossibleCard(card2))
			return false;
		if(card1.equals(card2))
			return false;
		return true;
	}
	
	public static boolean isProperlyFormattedTable(String table) {
		if(table.length() == 6) { // flop
			String flop1 = table.substring(0, 2);
			String flop2 = table.substring(2, 4);
			String flop3 = table.substring(4, 6);
			if(!isPossibleCard(flop1) || !isPossibleCard(flop2) || !isPossibleCard(flop3)) 
				return false; // incorrect card exists
			
			String[] cards = {flop1, flop2, flop3};
			for (int card1Index = 0; card1Index < 2; card1Index++) 
				for (int card2Index = card1Index + 1; card2Index < 3; card2Index++)
					if(cards[card1Index].equals(cards[card2Index])) 
						return false; // repeated card
			return true;	
			}
		
		if(table.length() == 8) { // turn
			String flop1 = table.substring(0, 2);
			String flop2 = table.substring(2, 4);
			String flop3 = table.substring(4, 6);
			String turn = table.substring(6, 8);
			if(!isPossibleCard(flop1) || !isPossibleCard(flop2) || !isPossibleCard(flop3) || !isPossibleCard(turn))
				return false; // incorrect card exists
			
			String[] cards = {flop1, flop2, flop3, turn};
			for(int card1Index = 0; card1Index < 3; card1Index++)
				for(int card2Index = card1Index + 1; card2Index < 4; card2Index++)
					if(cards[card1Index].equals(cards[card2Index]))
						return false; // repeated card
			return true;
			
		}
		if(table.length() == 10) { // river
			String flop1 = table.substring(0, 2);
			String flop2 = table.substring(2, 4);
			String flop3 = table.substring(4, 6);
			String turn = table.substring(6, 8);
			String river = table.substring(8, 10);
			if(!isPossibleCard(flop1) || !isPossibleCard(flop2) || !isPossibleCard(flop3) || !isPossibleCard(turn) || !isPossibleCard(river)) 
				return false; // incorrect card exists
			
			String[] cards = {flop1, flop2, flop3, turn, river};
			for(int card1Index = 0; card1Index < 4; card1Index++) 
				for(int card2Index = card1Index + 1; card2Index < 5; card2Index++)
					if(cards[card1Index].equals(cards[card2Index])) 
						return false; // repeated card
			return true;
			
		}
		return false; // incorrect length
	}
	
	public static boolean isPossibleCard(String card) {
		return isPossibleValue(card.charAt(0)) && isPossibleSuit(card.charAt(1));
	}
	
	public static boolean isPossibleSuit(char suit) {
		switch (suit) {
		case 'C' : return true;
		case 'D' : return true;
		case 'H' : return true;
		case 'S' : return true;
		default : return false;
		}
	}
	
	public static boolean isPossibleValue(char value) {
		if(value >= (int) '2' && value <= (int)'9') // 2-9 
			return true;
		if(value == '0' || value == 'J' || value == 'Q' || value == 'K' || value == 'A') // 10 and face cards
			return true;
		return false;
	}

}
