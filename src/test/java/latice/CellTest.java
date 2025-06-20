package latice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fr.unilim.latice.model.Tile;
import fr.unilim.latice.model.cell.Cell;
import fr.unilim.latice.model.cell.MoonCell;
import fr.unilim.latice.model.enums.Color;
import fr.unilim.latice.model.enums.Form;

class CellTest {
	@Test
	void testCellInitialization() {
		Cell cell = new MoonCell();
		assertTrue(cell instanceof Cell);
	}
	
	@Test
	void testCellIsNotFilledInitially() {
		Cell cell = new MoonCell();
		assertFalse(cell.isFilled());
	}
	
	@Test
	void testCellEmoji() {
		Cell cell = new MoonCell();
		String expectedEmoji = "🌔";
		assertEquals(expectedEmoji, cell.emoji());
	}
	
	@Test
	void testSetTile() {
		Cell cell = new MoonCell();
		// Assuming Tile is a valid class with a method tileImage()
		Tile tile = new Tile(Form.BIRD, Color.MAGENTA);
		cell.setTile(tile);
		
		assertTrue(cell.isFilled());
		assertEquals(tile.tileImage(), cell.emoji());
	}
	
	@Test
	void testGetTile() {
		Cell cell = new MoonCell();
		Tile tile = new Tile(Form.BIRD, Color.MAGENTA);
		cell.setTile(tile);
		
		assertNotNull(cell.getTile());
		assertSame(Form.BIRD, cell.getTile().getForm());
		assertSame(Color.MAGENTA, cell.getTile().getColor());
	}
}