package latice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unilim.latice.model.Deck;
import fr.unilim.latice.model.Player;
import fr.unilim.latice.model.Rack;
import fr.unilim.latice.model.Tile;
import fr.unilim.latice.model.enums.Color;
import fr.unilim.latice.model.enums.Form;

class RackTest {
    private static Rack rack;
    private static Player player;
    private static Tile[] tiles;
	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    @BeforeEach
    void setUp() throws Exception {
        player = new Player("TestPlayer", format.parse("01/01/2000"));
        tiles = new Tile[] {
            new Tile(Form.BIRD, Color.GREEN),
            new Tile(Form.BIRD, Color.RED),
            new Tile(Form.BIRD, Color.YELLOW),
            new Tile(Form.BIRD, Color.NAVY),
            new Tile(Form.DOLPHIN, Color.TEAL),
        };
        Deck deck = new Deck(tiles);
        rack = new Rack(player);
        player.setDeck(deck);
        player.setRack(rack);
        
        rack.fillRack();
    }

    @Test
    void testTakeTile() {
        rack.takeTile();
        assertEquals(5, rack.getTiles().size());
    }

    @Test
    void testGetTile() {
    	assertSame(Form.DOLPHIN, rack.getTile(0).getForm());
    	assertSame(Color.TEAL, rack.getTile(0).getColor());
    }

    @Test
    void testGetTileWithError() {
    	Tile tile = rack.getTile(999);
    	assertNull(tile);
    }
    
    @Test
    void testTakeTileWhenFull() {
		int initialSize = rack.getTiles().size();
		rack.takeTile();
		assertEquals(initialSize, rack.getTiles().size());
	}
    
    @Test
    void testGetTiles() {
        List<Tile> tilesInRack = rack.getTiles();
        assertEquals(5, tilesInRack.size());
        assertTrue(tilesInRack.containsAll(rack.getTiles()));
    }
    
    @Test
    void testRemoveTile() {
		Tile tileToRemove = rack.getTile(0);
		rack.removeTile(tileToRemove);
		assertTrue(!rack.getTiles().contains(tileToRemove));
		assertEquals(4, rack.getTiles().size());
	}
    
    @Test
    void testRackSize() {
		assertEquals(5, rack.getRackSize());
	}
}