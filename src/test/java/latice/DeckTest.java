package latice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unilim.latice.model.Deck;
import fr.unilim.latice.model.Tile;
import fr.unilim.latice.model.enums.Color;
import fr.unilim.latice.model.enums.Form;

class DeckTest {
	private static Deck deck;

	@BeforeEach
	void setUp() {
		Tile[] tiles = new Tile[36];
 		deck = new Deck(tiles);
 	}
	
	@Test
	void testAddTile() {
		Tile newTile = new Tile(Form.BIRD, Color.GREEN);
		deck.addTile(newTile);
		assertTrue(deck.getDeck().contains(newTile));
	}
	
	@Test
	void testRemoveTile() {
		Tile newTile = new Tile(Form.BIRD, Color.GREEN);
		deck.addTile(newTile);
		Tile removedTile = deck.removeTile();
		assertEquals(removedTile, newTile);
		assertTrue(!deck.getDeck().contains(newTile));
	}
	
	@Test
	void testRemoveTileFromEmptyDeck() {
		for (int i = 0; i < 36; i++) {
			deck.removeTile();
		}
		assertNull(deck.removeTile());
	}
}
