package io.robusta.hand.solution;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeSet;

import io.robusta.hand.Card;
import io.robusta.hand.CardColor;
import io.robusta.hand.interfaces.IDeck;

public class Deck extends LinkedList<Card> implements IDeck{

	
	private static final long serialVersionUID = -4686285366508321800L;
	
	public Deck() {

	}
	
	@Override
	public Card pick() {
		// shuffle;
		Collections.shuffle(this);
		
		// remove card from deck and returns it
		Card card = this.get(0);
		this.remove(0);
		return card;
	}


	

	@Override
	public TreeSet<Card> pick(int number) {
		// reuse pick()
		TreeSet<Card> set = new TreeSet<>();
		for (int i=1;i<=number;i++){
			set.add(pick());
		}
		
		return set;
	}

	@Override
	public Hand giveHand() {
		// A hand is a **5** card TreeSet
		Hand hand = new Hand();
		hand.addAll(pick(5));	
		return hand;
	}
	
	
	
}
