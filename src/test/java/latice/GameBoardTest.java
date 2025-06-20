package latice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unilim.latice.model.GameBoard;
import fr.unilim.latice.model.Player;
import fr.unilim.latice.model.Position;
import fr.unilim.latice.model.Rack;
import fr.unilim.latice.model.Tile;
import fr.unilim.latice.model.cell.SunCell;
import fr.unilim.latice.model.enums.Color;
import fr.unilim.latice.model.enums.Form;

class GameBoardTest {
	private static GameBoard gameBoard;
	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	@BeforeEach
	void setUp() {
		gameBoard = new GameBoard();
		gameBoard.init();
	}
	
	@Test
	void testGameBoardInitialization() {
		assertNotNull(gameBoard);
	}
	
	@Test
	void testGameBoardSunCell() {
		int[][] sunPositions = {
		        {0, 0}, {4, 0}, {8, 0},
		        {1, 1}, {2, 2}, {0, 4}, {0, 8},
		        {4, 8}, {8, 8}, {7, 7}, {6, 6},
		        {7, 1}, {6, 2}, {1, 7}, {2, 6},
		        {8, 4}
		    };
		
		for (int[] pos : sunPositions) {
			int x = pos[0];
			int y = pos[1];
			Position position = new Position(x, y);
			assertNotNull(gameBoard.getCell(position));
			assertTrue(gameBoard.getCell(position) instanceof SunCell);
		}
	}
	
	@Test
	void testGameBoardMoonCell() {
		Position moonPosition = new Position(4, 4);
		assertNotNull(gameBoard.getCell(moonPosition));
		assertTrue(gameBoard.getCell(moonPosition) instanceof fr.unilim.latice.model.cell.MoonCell);
	}
	
	@Test
	void testGameBoardOrdinaryCell() {
		for (int x = 0; x < gameBoard.getRows(); x++) {
			for (int y = 0; y < gameBoard.getColumns(); y++) {
				Position position = new Position(x, y);
				if (gameBoard.getCell(position) instanceof fr.unilim.latice.model.cell.OrdinaryCell) {
					assertNotNull(gameBoard.getCell(position));
				}
			}
		}
	}
	
	@Test
	void testputTileOnBoardTrue() throws ParseException {
		Player player = new Player("Player1", format.parse("01/01/2000"));
		player.setRack(new Rack(player));
		Boolean result = gameBoard.putTileOnBoardInAllDirections(new Tile(Form.BIRD, Color.GREEN), new Position(4, 4), player);
		assertTrue(result);
		assertTrue(gameBoard.getCell(new Position(4, 4)).isFilled());
		assertNotNull(gameBoard.getCell(new Position(4, 4)).getTile());
		assertEquals(Color.GREEN, gameBoard.getCell(new Position(4, 4)).getTile().getColor());
		assertEquals(Form.BIRD, gameBoard.getCell(new Position(4, 4	)).getTile().getForm());
	}

	@Test
	void testputTileOnBoardFalse() throws ParseException {
		Player player = new Player("Player1", format.parse("01/01/2000"));
		player.setRack(new Rack(player));
		Boolean isValid = gameBoard.putTileOnBoardInAllDirections(new Tile(Form.BIRD, Color.GREEN), new Position(19, 1), player);
		assertFalse(isValid);
	}
}
