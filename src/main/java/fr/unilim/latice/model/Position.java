package fr.unilim.latice.model;

import java.util.Objects;

import fr.unilim.latice.model.enums.Direction;

public final class Position {

	private final Integer column;
	private final Integer row;

	public Position(Integer row, Integer column) {
		this.column = column;
		this.row = row;
	}

	public Integer row() {
		return row;
	}

	public Integer column() {
		return column;
	}

	@Override
	public String toString() {
		return (row + 1) + "," + (column + 1);
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(column, row);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		return Objects.equals(column, other.column) && Objects.equals(row, other.row);
	}
	
	public Boolean isValid(Position position) {
		return (position.row() >= 0 && position.row() <= 8 && position.column() >= 0 && position.column() <= 8);
	}

    public static Position getAdjacentPosition(Position position, Direction direction) {
        switch (direction) {
            case TOP:
                return new Position(position.row() - 1, position.column());
            case BOTTOM:
                return new Position(position.row() + 1, position.column());
            case LEFT:
                return new Position(position.row(), position.column() - 1);
            case RIGHT:
                return new Position(position.row(), position.column() + 1);
            default:
                throw new IllegalArgumentException("Invalid direction");
        }
    }
}