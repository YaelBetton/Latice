package fr.unilim.latice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	
	private ArrayList<Tile> deck;
	
	private Integer deckSize = 36;
	
	public Deck(Tile[] deck) {
		this.deck = new ArrayList<>(deckSize);
		for (Tile tile : deck) {
			this.deck.add(tile);
		}
	}
	
	public void addTile(Tile tile) {
		deck.add(tile);
	}
	
	public Tile removeTile() {
		if (!deck.isEmpty()) {
			return deck.remove(deck.size() - 1);
		} else {
			return null;
		}
	}
	
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	public List<Tile> getDeck() {
		return deck;
	}
	
	public void enumDeck() {
		for (Tile tile : deck) {
			Console.message(tile.tileImage() + ":" + tile.description());
		}
	}
	
}