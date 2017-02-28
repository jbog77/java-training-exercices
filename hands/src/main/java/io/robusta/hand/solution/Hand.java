package io.robusta.hand.solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import io.robusta.hand.Card;
import io.robusta.hand.CardColor;
import io.robusta.hand.HandClassifier;
import io.robusta.hand.HandValue;
import io.robusta.hand.interfaces.IDeck;
import io.robusta.hand.interfaces.IHand;
import io.robusta.hand.interfaces.IHandResolver;

public class Hand extends TreeSet<Card> implements IHand {

	private static final long serialVersionUID = 7824823998655881611L;

	@Override
	public Set<Card> changeCards(IDeck deck, Set<Card> cards) {
		// For exemple remove three cards from this hand
		// , and get 3 new ones from the Deck
		// returns the new given cards
		TreeSet<Card> newCards = new TreeSet<>();
		newCards.addAll(deck.pick(cards.size()));
		this.remove(cards);
		this.addAll(newCards);
		return newCards;
	}

	@Override
	public boolean beats(IHand villain) {
		HandValue player = this.getValue();
		HandValue com = villain.getValue();
		System.out.println("Player: "+player.getClassifier());
		System.out.println("Villain: "+com.getClassifier());

		int score = player.compareTo(com);
		if (score > 0) {
			System.out.println("Player wins");
			return true;
		} else {
			System.out.println("Player looses");
			return false;
		}
	}

	@Override
	public IHand getHand() {
		return this;
	}

	@Override
	public HandClassifier getClassifier() {

		return this.getValue().getClassifier();
	}

	@Override
	public boolean isStraight() {
		int a = 0;
		int n = -1;
		HashMap<Integer, List<Card>> map = new HashMap<>();
		TreeSet<Card> inv = new TreeSet<>();
		map = this.group();
		
		if (map.containsKey(2) && map.containsKey(3) && map.containsKey(4) && map.containsKey(5) && map.containsKey(14)){
			return true;
		}
		
		if (map.size() == 5) {
			for (HashMap.Entry<Integer, List<Card>> entry : map.entrySet()) {
				if (entry.getKey() == (n + 1)) {
					a++;
				}
				n = entry.getKey();
			}
		}
		if (a == 4) {
			return true;
		}
		

		return false;
	}

	@Override
	public boolean isFlush() {
		int i = 0;
		Card temp = new Card(1, CardColor.SPADE);
		int a = 0;
		for (Card current : this) {
			if (i == 0) {
				i++;
			} else {

				if (current.getColor() == temp.getColor()) {
					a++;
				}
			}
			temp = current;
		}
		if (a == 4) {
			return true;
		}

			return false;
	}

	/**
	 * Returns number of identical cards 5s5cAd2s3s has two cardValue of 5
	 */
	@Override
	public int number(int cardValue) {
		int result = 0;
		for (Card current : this) {
			if (current.getValue() == cardValue) {
				result++;
			}
		}
		return result;
	}

	/**
	 * The fundamental map Check the tests and README to understand
	 */
	@Override
	public HashMap<Integer, List<Card>> group() {
		HashMap<Integer, List<Card>> map = new HashMap<>();

		// fill the map
		for (Card current : this) {
			if (map.get(current.getValue()) == null) {
				// Key no exist
				List<Card> list = new ArrayList<>();
				list.add(current);
				map.put(current.getValue(), list);
			} else {
				// Key Exist, add card to the list
				List<Card> temp = new ArrayList<>();
				temp = map.get(current.getValue());
				temp.add(current);
				map.put(current.getValue(), temp);
			}
		}
		return map;
	}

	// different states of the hand
	int mainValue;
	int tripsValue;
	int pairValue;
	int secondValue;
	TreeSet<Card> remainings;

	/**
	 * return all single cards not used to build the classifier
	 * 
	 * @param map
	 * @return
	 */
	TreeSet<Card> getGroupRemainingsCard(Map<Integer, List<Card>> map) {
		TreeSet<Card> groupRemaining = new TreeSet<>();
		// May be adapted at the end of project:
		// if straight or flush : return empty
		// If High card, return 4 cards

		for (List<Card> group : map.values()) {
			if (group.size() == 1) {
				groupRemaining.add(group.get(0));
			}
		}
		return groupRemaining;
	}

	@Override
	public boolean isPair() {
		if (this.group().size() == 4) {
			return true;
		}
		if (this.group().size() == 2 && !this.isFourOfAKind()) {
			return true;
		}
		return false;

	}

	@Override
	public boolean isDoublePair() {
		HashMap<Integer, List<Card>> map = new HashMap<>();
		map = this.group();
		if (map.size() == 3) {
			for (HashMap.Entry<Integer, List<Card>> entry : map.entrySet()) {
				if (entry.getValue().size() == 2) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isHighCard() {
		if (this.group().size() == 5 && !this.isStraight() && !this.isFlush()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isTrips() {
		HashMap<Integer, List<Card>> map = new HashMap<>();
		map = this.group();
		if (map.size() == 3) {
			for (HashMap.Entry<Integer, List<Card>> entry : map.entrySet()) {
				if (entry.getValue().size() == 3) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isFourOfAKind() {
		HashMap<Integer, List<Card>> map = new HashMap<>();
		map = this.group();
		if (map.size() == 2) {
			for (HashMap.Entry<Integer, List<Card>> entry : map.entrySet()) {
				if (entry.getValue().size() == 4) {
					return true;
				}
			}
		}
		return false;

	}

	@Override
	public boolean isFull() {
		HashMap<Integer, List<Card>> map = new HashMap<>();
		map = this.group();
		if (map.size() == 2) {
			for (HashMap.Entry<Integer, List<Card>> entry : map.entrySet()) {
				if (entry.getValue().size() == 3) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isStraightFlush() {
		if (this.isStraight() && this.isFlush()) {
			return true;
		}
		return false;
	}

	@Override
	public HandValue getValue() {
		HandValue handValue = new HandValue();

		if (this.isStraightFlush()) {
			handValue.setClassifier(HandClassifier.STRAIGHT_FLUSH);
			HashMap<Integer, List<Card>> map = new HashMap<>();
			map = this.group();
			for (HashMap.Entry<Integer, List<Card>> entry : map.entrySet()) {
				if (this.mainValue < entry.getKey()) {
					this.mainValue = entry.getKey();
				}
			}
			this.remainings = null;
			handValue.setLevelValue(this.mainValue);
			handValue.setOtherCards(this.remainings); // or this.getRemainings()
			return handValue;
		}

		if (this.isFourOfAKind()) {
			handValue.setClassifier(HandClassifier.FOUR_OF_A_KIND);
			HashMap<Integer, List<Card>> map = new HashMap<>();
			TreeSet<Card> left = new TreeSet<>();
			map = this.group();
			for (HashMap.Entry<Integer, List<Card>> entry : map.entrySet()) {
				if (entry.getValue().size() == 4) {
					this.mainValue = entry.getKey();
				} else {
					left.add(entry.getValue().get(0));
				}
			}
			this.remainings = left;
			handValue.setLevelValue(this.mainValue);
			handValue.setOtherCards(this.remainings); // or this.getRemainings()
			return handValue;
		}

		if (this.isFull()) {
			handValue.setClassifier(HandClassifier.FULL);
			HashMap<Integer, List<Card>> map = new HashMap<>();
			TreeSet<Card> left = new TreeSet<>();
			map = this.group();
			for (HashMap.Entry<Integer, List<Card>> entry : map.entrySet()) {
				if (entry.getValue().size() == 3) {
					this.mainValue = entry.getKey();
				} else {
					this.secondValue = entry.getKey();
					left.add(entry.getValue().get(0));
				}
			}
			this.remainings = left;
			handValue.setLevelValue(this.mainValue);
			handValue.setSecondLevel(this.secondValue);
			handValue.setOtherCards(this.remainings); // or this.getRemainings()
			return handValue;
		}

		if (this.isFlush()) {
			handValue.setClassifier(HandClassifier.FLUSH);
			HashMap<Integer, List<Card>> map = new HashMap<>();
			map = this.group();
			for (HashMap.Entry<Integer, List<Card>> entry : map.entrySet()) {
				if (this.mainValue < entry.getKey()) {
					this.mainValue = entry.getKey();
				}
			}
			this.remainings = null;
			handValue.setLevelValue(this.mainValue);
			handValue.setOtherCards(this.remainings); // or this.getRemainings()
			return handValue;
		}

		if (this.isStraight()) {
			handValue.setClassifier(HandClassifier.STRAIGHT);
			HashMap<Integer, List<Card>> map = new HashMap<>();
			map = this.group();
			for (HashMap.Entry<Integer, List<Card>> entry : map.entrySet()) {
				if (this.mainValue < entry.getKey()) {
					this.mainValue = entry.getKey();
				}
			}
			this.remainings = null;
			handValue.setLevelValue(this.mainValue);
			handValue.setOtherCards(this.remainings); // or this.getRemainings()
			return handValue;
		}

		if (this.isTrips()) {
			handValue.setClassifier(HandClassifier.TRIPS);
			HashMap<Integer, List<Card>> map = new HashMap<>();
			TreeSet<Card> left = new TreeSet<>();
			map = this.group();
			for (HashMap.Entry<Integer, List<Card>> entry : map.entrySet()) {
				if (entry.getValue().size() == 3) {
					this.mainValue = entry.getKey();
				} else if (entry.getValue().size() == 2) {
					left.add(entry.getValue().get(0));
					left.add(entry.getValue().get(1));
				} else {
					left.add(entry.getValue().get(0));
				}
			}
			this.remainings = left;
			handValue.setLevelValue(this.mainValue);
			handValue.setOtherCards(this.remainings); // or this.getRemainings()
			return handValue;
		}

		if (this.isDoublePair()) {
			handValue.setClassifier(HandClassifier.TWO_PAIR);
			HashMap<Integer, List<Card>> map = new HashMap<>();
			TreeSet<Card> left = new TreeSet<>();
			map = this.group();
			for (HashMap.Entry<Integer, List<Card>> entry : map.entrySet()) {
				if (entry.getValue().size() == 2) {
					if (this.mainValue < entry.getKey()) {
						this.mainValue = entry.getKey();
					} else {
						this.secondValue = entry.getKey();
					}
				} else {
					left.add(entry.getValue().get(0));
				}
			}

			this.remainings = left;
			handValue.setLevelValue(this.mainValue);
			handValue.setSecondLevel(this.secondValue);
			handValue.setOtherCards(this.remainings); // or this.getRemainings()
			return handValue;
		}

		if (this.isPair()) {
			handValue.setClassifier(HandClassifier.PAIR);
			HashMap<Integer, List<Card>> map = new HashMap<>();
			TreeSet<Card> left = new TreeSet<>();
			map = this.group();
			for (HashMap.Entry<Integer, List<Card>> entry : map.entrySet()) {
				if (entry.getValue().size() == 2) {
					this.mainValue = entry.getKey();
				} else {
					left.add(entry.getValue().get(0));
				}
			}

			this.remainings = left;
			handValue.setLevelValue(this.mainValue);
			handValue.setOtherCards(this.remainings); // or this.getRemainings()
			return handValue;
		}

		if (this.isHighCard()) {
			handValue.setClassifier(HandClassifier.HIGH_CARD);
			HashMap<Integer, List<Card>> map = new HashMap<>();
			TreeSet<Card> left = new TreeSet<>();
			map = this.group();
			for (HashMap.Entry<Integer, List<Card>> entry : map.entrySet()) {
				if (this.mainValue < entry.getKey()) {
					left.add(entry.getValue().get(0));
					this.mainValue = entry.getKey();
				}
			}
			this.remainings = left;
			handValue.setLevelValue(this.mainValue);
			handValue.setOtherCards(this.remainings); // or this.getRemainings()
			return handValue;
		}

		return handValue;
	}

	@Override
	public boolean hasCardValue(int level) {
		for (Card current : this) {
			if (current.getValue() == level){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasAce() {
		for (Card current : this) {
			if (current.getValue() == Card.AS_VALUE){
				return true;
			}
		}
		return false;
	}

	@Override
	public int highestValue() {
		// ace might be the highest value
		int max = 0;
		Card temp = new Card(2,CardColor.CLUB);
		for (Card current : this) {
			if (current.getValue() > temp.getValue() ){
				max = current.getValue();
			}
			temp = current;
		}
		return max;
	}

	@Override
	public int compareTo(IHandResolver o) {
		return 0;
	}

}
