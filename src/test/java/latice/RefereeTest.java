package latice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unilim.latice.controller.Referee;
import fr.unilim.latice.model.GameBoard;
import fr.unilim.latice.model.Player;
import fr.unilim.latice.model.Position;
import fr.unilim.latice.model.Rack;
import fr.unilim.latice.model.Tile;
import fr.unilim.latice.model.enums.Color;
import fr.unilim.latice.model.enums.Form;

class RefereeTest {
	private static Referee referee;
	private Player player1;
	private Player player2;
	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	@BeforeEach
	void setUp() throws Exception {
		player1 = new Player("Player 1", format.parse("01/01/2000"));
		player2 = new Player("Player 2", format.parse("02/02/2000"));
		
		referee = new Referee();
		referee.initGame(new Player[] { player1, player2 });
		referee.shuffleAndDistributeDeck();
	}
	
	@Test
	void testCurrentPlayerChange() {
		String currentPlayerName = referee.getCurrentPlayer().getName();
		assertTrue(currentPlayerName.equals("Player 1") || currentPlayerName.equals("Player 2"));

		referee.changeCurrentPlayer();
		
		String newCurrentPlayerName = referee.getCurrentPlayer().getName();
		assertNotEquals(currentPlayerName, newCurrentPlayerName);
		
		referee.changeCurrentPlayer();
		
		String revertedPlayerName = referee.getCurrentPlayer().getName();
		assertEquals(currentPlayerName, revertedPlayerName);
	}
	
	@Test
	void testGetSpecificPlayer() {
		Player specificPlayer = referee.getSpecificPlayer(0);
		assertNotNull(specificPlayer);
		assertEquals("Player 1", specificPlayer.getName());
		
		specificPlayer = referee.getSpecificPlayer(1);
		assertNotNull(specificPlayer);
		assertEquals("Player 2", specificPlayer.getName());
	}
	
	@Test 
	void testWinningCombination() {
		Tile tile1 = new Tile(Form.BIRD, Color.MAGENTA);
		Tile tile2 = new Tile(Form.FLOWER, Color.MAGENTA);
		
		assertTrue(Referee.isWinningCombination(tile1, tile2));
		
		Tile tile3 = new Tile(Form.DOLPHIN, Color.NAVY);
		Tile tile4 = new Tile(Form.DOLPHIN, Color.YELLOW);
		
		assertTrue(Referee.isWinningCombination(tile3, tile4));
	}
	
	@Test
	void testGetSpecificPlayerWithInvalidIndex() {
		assertThrows(IllegalArgumentException.class, () -> {
			referee.getSpecificPlayer(986);
		});
	}
	
	@Test
	void testNextPlayer() {
		Player currentPlayer = referee.getCurrentPlayer();
		referee.nextPlayer();
		Player nextPlayer = referee.getCurrentPlayer();
		
		assertNotEquals(currentPlayer, nextPlayer);
		assertTrue(nextPlayer.equals(referee.getSpecificPlayer(0)) || nextPlayer.equals(referee.getSpecificPlayer(1)));
	}
	
	@Test
	void testfirstMoveFalse() {
		GameBoard gameBoard = new GameBoard();
		gameBoard.init();
		Tile tile1 = new Tile(Form.BIRD, Color.MAGENTA);
		Position position = new Position(0, 0);
		boolean isFirstMove = Referee.valideMove(tile1, position, gameBoard, referee.getCurrentPlayer());
		
		assertFalse(isFirstMove);		
	}

	@Test
	void testFirstMoveTrue() {
		GameBoard gameBoard = new GameBoard();
		gameBoard.init();
		Tile tile1 = new Tile(Form.BIRD, Color.MAGENTA);
		Position position = new Position(4, 4);
		
		assertTrue(Referee.valideMove(tile1, position, gameBoard, referee.getCurrentPlayer()));
	}

	@Test
	void testValideMoveTrueWithForm() {
		Player currentPlayer = referee.getCurrentPlayer();
		currentPlayer.setRack(new Rack(currentPlayer));
		GameBoard gameBoard = new GameBoard();
		
		gameBoard.init();
		Tile tile1 = new Tile(Form.BIRD, Color.MAGENTA);
		Position position1 = new Position(4, 4);
		Position position2 = new Position(3, 4);
		Tile tile = new Tile(Form.BIRD, Color.NAVY);
		gameBoard.putTileOnBoardInAllDirections(tile1, position1, currentPlayer);
		
		
		assertTrue(Referee.valideMove(tile, position2, gameBoard, referee.getCurrentPlayer()));
	}
	
	@Test
	void testValideMoveTrueWithColor() {
		Player currentPlayer = referee.getCurrentPlayer();
		currentPlayer.setRack(new Rack(currentPlayer));
		GameBoard gameBoard = new GameBoard();
		
		gameBoard.init();
		Tile tile1 = new Tile(Form.BIRD, Color.MAGENTA);
		Position position1 = new Position(4, 4);
		Position position2 = new Position(3, 4);
		Tile tile = new Tile(Form.GECKO, Color.MAGENTA);
		gameBoard.putTileOnBoardInAllDirections(tile1, position1, currentPlayer);
		
		
		assertTrue(Referee.valideMove(tile, position2, gameBoard, referee.getCurrentPlayer()));	
	}
	
	@Test
	void testValideMoveFalse() {
		Player currentPlayer = referee.getCurrentPlayer();
		currentPlayer.setRack(new Rack(currentPlayer));
		GameBoard gameBoard = new GameBoard();
		
		gameBoard.init();
		Tile tile1 = new Tile(Form.BIRD, Color.MAGENTA);
		Position position1 = new Position(4, 4);
		Position position2 = new Position(3, 4);
		Tile tile = new Tile(Form.GECKO, Color.NAVY);
		gameBoard.putTileOnBoardInAllDirections(tile1, position1, currentPlayer);
		
		
		assertFalse(Referee.valideMove(tile, position2, gameBoard, referee.getCurrentPlayer()));
	}
	
	
	@Test
	void testGetWinner() {
		player1.setRack(new Rack(player1));
		player1.getRack().fillRack();
		player2.setRack(new Rack(player2));
		player2.getRack().fillRack();
		
		assertEquals(referee.getWinner().getName(), player1.getName());
	}
	
	@Test
	void testIsEndGame() {
		player1.setRack(new Rack(player1));
		player1.getRack().fillRack();
		player2.setRack(new Rack(player2));
		player2.getRack().fillRack();
		
		assertFalse(referee.isEndGame());
		
		player1.getDeck().getDeck().clear();
		player1.getRack().getTiles().clear();
		player2.getDeck().getDeck().clear();
		player2.getRack().getTiles().clear();
		
		assertTrue(referee.isEndGame());
	}
	
	@Test
	void testSpendPointToExtraMove() {
		referee.spendPointtoBuyExtraMove();
		referee.getCurrentPlayer().addPoints(3,0);
		referee.spendPointtoBuyExtraMove();
		
		assertEquals(0, referee.getCurrentPlayer().getPoints());
	}
	
	@Test
	void testExchangeRack() {
		player1.setRack(new Rack(player1));
		player1.getRack().fillRack();
		player2.setRack(new Rack(player2));
		player2.getRack().fillRack();
		
		Rack rack = referee.getCurrentPlayer().getRack();
		int initialSize = rack.getTiles().size();
		
		referee.exchangeRack();
		
		assertEquals(initialSize, rack.getTiles().size());
		assertTrue(rack.getTiles().size() > 0);
	}
	
	@Test
	void testCanExtraMove() {
		assertFalse(referee.canExtraMove());
		referee.getCurrentPlayer().addPoints(4, 0);
		assertTrue(referee.canExtraMove());
	}
	
	@Test
	void testNumberTurns() {
		assertEquals(0, referee.getNumberTurns().get());
		referee.nextPlayer();
		assertEquals(0, referee.getNumberTurns().get());
		referee.nextPlayer();
		assertEquals(1, referee.getNumberTurns().get());
	}
	
	@Test
	void testIncrementNumberTurns() {
		int initialTurns = referee.getNumberTurns().get();
		referee.incrementNumberTurns(1);
		assertEquals(initialTurns + 1, referee.getNumberTurns().get());
		
		referee.incrementNumberTurns(2);
		assertEquals(initialTurns + 3, referee.getNumberTurns().get());
	}
	
	@Test
	void testNumberActions() {
		assertNotNull(referee.numberActions());
	}
}
