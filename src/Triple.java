
public class Triple extends Hand {
		
	public Triple(CardGamePlayer player, CardList cards) {
			super(player, cards);
		}
		
		public boolean isValid () {
			if (this.size()==3) {
				if (this.getCard(0).getRank()==this.getCard(1).getRank() && this.getCard(1).getRank()==this.getCard(2).getRank())
					return true;
			}		
			return false;
		
		}

		public String getType() {
			return "Triple";
		}
}
