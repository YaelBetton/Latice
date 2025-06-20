package latice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.unilim.latice.controller.Referee;
import fr.unilim.latice.model.Player;
import fr.unilim.latice.model.Rack;

class PlayerTest {


	private static Referee referee;
	private static Player player1;
	private static Player player2;	
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
	void testPlayerInitialization() {
		assertNotNull(player1);
		assertNotNull(player2);
		
		assertEquals("Player 1", player1.getName());
		assertEquals("Player 2", player2.getName());
	}
	
	@Test
	void testPlayerHaveCardsInDack() {
		assertTrue(player1.getDeck().getDeck().size() > 0);
		assertTrue(player2.getDeck().getDeck().size() > 0);
	}
	
	@Test 
	void testPlayerHaveCardsInRack() {
		player1.setRack(new Rack(player1));
		player1.getRack().fillRack();
		
		player2.setRack(new Rack(player2));
		player2.getRack().fillRack();
		
		assertNotEquals(0, player1.getRack().getTiles().size());
		assertNotEquals(0, player1.getRack().getTiles().size());
	}
	
	@Test
	void testPlayerPointsInitialization() {
		assertEquals(0, player1.getPoints());
		assertEquals(0, player2.getPoints());
	}

	@Test
	void testPlayerAddPoints() {
		player1.addPoints(0, 1);
		player1.addPoints(1, 1);
		player1.addPoints(2, 1);
		player1.addPoints(3, 1);
		player1.addPoints(4, 1);
		
		assertEquals(12, player1.getPoints());
	}
	
	@Test
	void testPlayerBirthdateInitialization() {
		assertNotNull(player1.getBirthdate());
		assertNotNull(player2.getBirthdate());
	}
	
	@Test
	void testHasPerformedAction() {
		assertFalse(player1.hasPerformedAction().get());
		assertFalse(player2.hasPerformedAction().get());
		
		player1.hasPerformedAction().set(true);
		player2.hasPerformedAction().set(true);
		assertTrue(player1.hasPerformedAction().get());
		assertTrue(player2.hasPerformedAction().get());
	}
	
	@Test
	void testGetPointsProperty() {
		assertNotNull(player1.getPointsProperty());
		assertNotNull(player2.getPointsProperty());
		
		player1.getPointsProperty().set(5);
		player2.getPointsProperty().set(10);
		
		assertEquals(5, player1.getPoints());
		assertEquals(10, player2.getPoints());
	}
}
