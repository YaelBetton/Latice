package fr.unilim.latice.model.cell;

import fr.unilim.latice.model.Position;
import fr.unilim.latice.model.Tile;
import fr.unilim.latice.model.interfaces.CellInterface;

public abstract class Cell implements CellInterface {
	private Boolean isFilled = false;
	private String design;
	private Tile tile = null;
	private Position position = new Position(null, null);

	Cell(String design) {
		this.design = design;
	}
	
	public Boolean isFilled() {
		return this.isFilled;
	}
	
	public String emoji() {
		return design;
	}
	
	public Tile getTile() {
		return this.tile;
	}
	
	public void setTile(Tile tile) {
		this.tile = tile;
		this.isFilled = true;
		this.design = tile.tileImage();
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
}
