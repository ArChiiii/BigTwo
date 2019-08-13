
public class StraightFlush extends Hand {
	
	public StraightFlush (CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	public boolean isValid() {
		CardList cards = new CardList();
		for (Card card: this.getCards()) {
			cards.addCard(card);	
		}
		CardList straight= new Straight(this.getPlayer(), cards);
		CardList flush= new Flush(this.getPlayer(), cards);
		if (((Straight) straight).isValid() && ((Flush)flush).isValid()) {
			return true;
		}
		return false;
	}
	
	public boolean beats (Hand hand) {
		int compare=-1;
		if (hand.size()==this.size()) {
			if (hand.getType()=="Straight" || hand.getType()=="Flush" ||hand.getType()=="FullHouse"||hand.getType()=="Quad") {
				compare =1 ;
			}else if (hand.getType()=="StraightFlush") {
				compare = getTopCard().compareTo(hand.getTopCard());
			}
			return compare==1?true:false;
		}
		return false;
	}
	
	public String getType() {
		return "StraightFlush";
	}

}