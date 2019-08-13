
public class BigTwoCard extends Card {
	
	public BigTwoCard(int suit, int rank) {
		super(suit, rank);
	}
	
	public int compareTo (Card card) {
		int rank = this.getRank()<2? this.getRank()+13:this.getRank();
		int cardrank = card.getRank()<2? card.getRank()+13:card.getRank();
		if (rank > cardrank) {
			return 1;
		} else if (rank < cardrank) {
			return -1;
		} else if (this.getSuit() > card.getSuit()) {
			return 1;
		} else if (this.getSuit() < card.getSuit()) {
			return -1;
		} else {
			return 0;
		}
		
	}
}
