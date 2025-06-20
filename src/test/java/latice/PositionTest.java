package latice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fr.unilim.latice.model.Position;
import fr.unilim.latice.model.enums.Direction;

class PositionTest {
	@Test
	void testPositionInit() {
		Position position = new Position(0, 0);
		assertEquals(0, position.row());
		assertEquals(0, position.column());
	}
	
	@Test
	void testPositionToString() {
		Position position = new Position(1, 2);
		assertEquals("2,3", position.toString());
	}
	
	@Test
	void testPositionEquals() {
		Position position1 = new Position(1, 2);
		Position position2 = new Position(1, 2);
		assertEquals(position1, position2);
	}
	
	@Test
	void testPositionHashCode() {
		Position position1 = new Position(1, 2);
		Position position2 = new Position(1, 2);
		assertEquals(position1.hashCode(), position2.hashCode());
	}
	
	@Test
	void testPositionIsValid() {
		Position position = new Position(0, 0);
		assertTrue(position.isValid(position));
		
		Position invalidPosition = new Position(-1, 0);
		assertFalse(position.isValid(invalidPosition));
		
		invalidPosition = new Position(9, 0);
		assertFalse(position.isValid(invalidPosition));
		
		invalidPosition = new Position(0, -1);
		assertFalse(position.isValid(invalidPosition));
		
		invalidPosition = new Position(0, 9);
		assertFalse(position.isValid(invalidPosition));
	}
	
	@Test
	void testPositionAdjacent() {
		Position position = new Position(1, 1);
		Position top = Position.getAdjacentPosition(position, Direction.TOP);
		assertTrue(top.row() == 0 && top.column() == 1);
		
		Position bottom = Position.getAdjacentPosition(position, Direction.BOTTOM);
		assertTrue(bottom.row() == 2 && bottom.column() == 1);
		
		Position left = Position.getAdjacentPosition(position, Direction.LEFT);
		assertTrue(left.row() == 1 && left.column() == 0);
		
		Position right = Position.getAdjacentPosition(position, Direction.RIGHT);
		assertTrue(right.row() == 1 && right.column() == 2);
	}
}
