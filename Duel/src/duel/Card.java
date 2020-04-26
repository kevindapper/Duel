package duel;

/**
 * Provides type of card and the energy cost.
 * @author Kevin Dapper
 * @author Quentin Howa
 */
public class Card {

	private int energyCost;
	/**
	 * true = attack
	 * false = defend
	 */
	private boolean cardType;
	private boolean played;
	
	/**
	 * Assigns card type, energy cost, and if its been
	 * played when an instance of card has been created.
	 * @param energyCost
	 * @param cardType
	 * @param played
	 */
	public Card(int energyCost, boolean cardType, boolean played) {
		this.energyCost = energyCost;
		this.cardType = cardType;
		this.played = played;
	}
	
	/**
	 * If card type is true (attack) then returns
	 * an attack value.
	 * @param energyCost
	 * @return
	 */
	public int attack(int energyCost) {
		if(cardType) {
			return energyCost;
		} else {
			return 0;
		}
	}
	
	/**
	 * If card type is false (defend) then returns
	 * a defend value.
	 * @param energyCost
	 * @return
	 */
	public int defend(int energyCost) {
		if(!cardType) {
			return energyCost;
		} else {
			return 0;
		}
	}
	
	/**
	 * Provides access energyCost.
	 * @return
	 */
	public int getEnergyCost() {
		return energyCost;
	}
	
	/**
	 * Provides access to cardType.
	 * @return
	 */
	public boolean getCardType() {
		return cardType;
	}
	
	/**
	 * Provides access to getPlayed
	 * @return
	 */
	public boolean getPlayed() {
		return played;
	}
	
	/**
	 * Allows the ability to change on played status.
	 * @param status
	 */
	public void setPlayed(boolean status) {
		played = status;
	}

	@Override
	public String toString() {
		String cardDetails = "";
		if(getCardType()) {
			cardDetails += "Attack: ";
		}
		else {
			cardDetails += "Defend: ";
		}
		cardDetails += getEnergyCost();
		return cardDetails;
	}
	
}
