package fr.unilim.latice.model;

import java.util.HashMap;

import fr.unilim.latice.controller.Referee;
import fr.unilim.latice.model.cell.Cell;
import fr.unilim.latice.model.cell.MoonCell;
import fr.unilim.latice.model.cell.OrdinaryCell;
import fr.unilim.latice.model.cell.SunCell;
import fr.unilim.latice.model.interfaces.DisplayGameboard;

public class GameBoard implements DisplayGameboard {
	private static final int COLUMN = 9;
	private static final int ROW = 9;

	private HashMap<Position, Cell> cells;

	public void init() {
		cells = new HashMap<>();

		int[][] sunPositions = {
				{ 0, 0 }, { 4, 0 }, { 8, 0 },
				{ 1, 1 }, { 2, 2 }, { 0, 4 }, { 0, 8 },
				{ 4, 8 }, { 8, 8 }, { 7, 7 }, { 6, 6 },
				{ 7, 1 }, { 6, 2 }, { 1, 7 }, { 2, 6 },
				{ 8, 4 }
		};

	    for (int[] pos : sunPositions) {
	        int x = pos[0];
	        int y = pos[1];
	        SunCell sunCell = new SunCell();
	        sunCell.setPosition(new Position(x, y));
	        cells.put(sunCell.getPosition(), sunCell);
	    }
	    
	    MoonCell moonCell = new MoonCell();
        moonCell.setPosition(new Position(4, 4));
	    cells.put(moonCell.getPosition(), moonCell);

	    for (int x = 0; x < COLUMN; x++) {
	        for (int y = 0; y < ROW; y++) {
	        	
	            Position position = new Position(x, y);
	            if (!cells.containsKey(position)) {
	            	OrdinaryCell ordinaryCell = new OrdinaryCell();
	            	ordinaryCell.setPosition(position);
	                cells.put(position, ordinaryCell);
	            }
	        }
	    }
	}

	@Override
	public void display() {
	    for (int y = 0; y < COLUMN; y++) {
	        Console.print(y + 1 + " ");
	        for (int x = 0; x < ROW; x++) {
	            Position position = new Position(x, y);
	            Cell cell = cells.get(position);
	            Console.print(cell.emoji() + " ");
	        }
	        Console.message("");
	    }
	}

	public Cell getCell(Position position) {
		return cells.get(position);
	}

	public Integer getColumns() {
		return COLUMN;
	}

	public Integer getRows() {
		return ROW;
	}

	public Boolean putTileOnBoardInAllDirections(Tile tile, Position position, Player player) {
          	if (position.isValid(position)
                && !this.getCell(position).isFilled() 
                && Referee.valideMove(tile, position, this, player)) {
                this.getCell(position).setTile(tile);
                player.getRack().removeTile(tile);
                return true;
            }
        
        return false;
    }
	
	public Boolean allSellIsFilled() {
		for (int x = 0; x < COLUMN; x++) {
			for (int y = 0; y < ROW; y++) {
				Position position = new Position(x, y);
				if (cells.get(position).isFilled()) {
					return false;
				}
			}
		}
		return true;
	}
}
