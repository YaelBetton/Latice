package latice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import fr.unilim.latice.model.Console;
import fr.unilim.latice.model.Tile;
import fr.unilim.latice.model.enums.Color;
import fr.unilim.latice.model.enums.Form;

class TileTest {
	
	@Test
	void testTileImage() {
		Tile tile = new Tile(Form.BIRD, Color.RED);
		String expectedImage = Color.RED.getCode() + Form.BIRD.getEmoji() + Console.NEUTRAL_COLOR;
		assertEquals(tile.tileImage(), expectedImage);
	}
	
	@Test
	void testTileDescription() {
		Tile tile = new Tile(Form.FEATHER, Color.GREEN);
		String expectedDescription = "GREEN - FEATHER";
		assertEquals(tile.description(), expectedDescription);
	}
	
	@Test
	void testTileGetFormAndPosition() {
		Tile tile = new Tile(Form.FLOWER, Color.TEAL);
		assertEquals(Form.FLOWER, tile.getForm());
		assertEquals(Color.TEAL, tile.getColor());
	}
}
