
public abstract class Hand extends CardList {
	
	private CardGamePlayer player;
	
	public Hand(CardGamePlayer player, CardList cards) {
		setPlayer(player);
		for (Card card: cards.getCards()) {
			this.addCard(card);	
		}
	}

	public CardGamePlayer getPlayer() {
		return player;
	}

	public void setPlayer(CardGamePlayer player) {
		this.player = player;
	}

	public Card getTopCard() {
		this.sort();
		return this.getCard(this.size()-1);
	}
	
	public boolean beats (Hand hand) {
		int compare =-1 ;
		if (hand ==null || this.getPlayer()==hand.getPlayer()) {
			return true;
		}
		if (hand.size()==this.size()) {
			if (this.size()<4) {
				compare= getTopCard().compareTo(hand.getTopCard());
			}
		}
		
		if (compare <1)
			return false;
		else
			return true;
	}
	
	public abstract boolean isValid();
	
	public abstract String getType();
}
