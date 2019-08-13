
public class Pair extends Hand {
	
	public Pair(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	public boolean isValid () {
		if (this.size()==2) {
			if (this.getCard(0).getRank()==this.getCard(1).getRank())
				return true;
		}		
		return false;
	}
	

	public String getType() {
		return "Pair";
	}
}
