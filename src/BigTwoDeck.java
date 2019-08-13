
public class BigTwoDeck extends Deck {
	
	public BigTwoDeck() {
		initialize();
	}
	
	public void initialize() {
		removeAllCards();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				Card card = new BigTwoCard(i, j);
				addCard(card);
			}
		}
	}
}