
public class Flush extends Hand {
	public Flush(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	public boolean isValid() {
		if (size() ==5) {
			int suit = this.getTopCard().getSuit();
			for (int i =1; i<5; i++) {
				if (this.getCard(i).getSuit()==suit) {
					continue;
				}else
					return false;
			}
		}
		return true;
	}
	
	public boolean beats (Hand hand) {
		int compare=-1;
		if (hand.size()==this.size()) {
			if (hand.getType()=="Straight") {
				compare =1 ;
			}else if (hand.getType()=="Flush") {
				compare = getTopCard().compareTo(hand.getTopCard());
			}
			return compare==1?true:false;
		}
		return false;
	}
	
	public String getType() {
		return "Flush";
	}

}
