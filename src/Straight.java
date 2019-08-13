
public class Straight extends Hand {

	public Straight(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	public boolean isValid() {
		if (size() ==5) {
			sort();
			if (this.getTopCard().rank<2 || this.getTopCard().rank>5) {
				for (int i=1; i<5;i++) {
					if (this.getCard(i).rank-this.getCard(i-1).rank==1 ||this.getCard(i).rank-this.getCard(i-1).rank==-1) {
						continue;
					}else {
						return false;
					}
				}
			}
			
		}
		return true;
	}
	
	public String getType() {
		return "Straight";
	}
}
