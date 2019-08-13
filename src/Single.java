public class Single extends Hand {
	public Single(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	public boolean isValid () {
		if (this.size()==1) 
			return true;
		return false;
	}
	
	public String getType() {
		return "Single";
	}

}