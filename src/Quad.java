
public class Quad extends Hand {
	
	public Quad (CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	public boolean isValid () {
		if (this.size()==5) {
			int [] count = {0,0,0,0,0,0,0,0,0,0,0,0,0};
			for (int j=0 ; j<5;j++) {
				count[this.getCard(j).getRank()]++;	 
			}
			
			for (int i=0 ; i<13; i++) {
				if (count[i]==4) {
					return true;
				}
			}
						
		}		
			return false;
	}
	
	public boolean beats (Hand hand) {
		if (hand ==null || this.getPlayer()==hand.getPlayer()) {
			return true;
		}
		int compare=-1;
		if (hand.size()==this.size()) {
			if (hand.getType()=="Straight" || hand.getType()=="Flush" ||hand.getType()=="FullHouse") {
				compare =1 ;
			}else if (hand.getType()=="Quad") {
				compare = getTopCard().compareTo(hand.getTopCard());
			}
			return compare==1?true:false;
		}
		return false;
	}
	
	public Card getTopCard() {
		
		if (this.size()==5) {
			int [] count = {0,0,0,0,0,0,0,0,0,0,0,0,0};
			for (int j=0 ; j<5;j++) {
				count[this.getCard(j).getRank()]++;	 
			}
			for (int i=0 ; i<13; i++) {
				if (count[i]==4) {
					System.out.println(getCard(i).getRank());
					return this.getCard(i);
					
				}
			}
		}
		return null;
	}
	
	public String getType() {
		return "Quad";
	}
}

