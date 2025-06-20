package fr.unilim.latice.model;

import java.util.ArrayList;
import java.util.List;

public class Rack {
	private ArrayList<Tile> tiles;
	private Player player;
	
	private Integer rackSize = 5;

	public Rack(Player player) {
		this.tiles = new ArrayList<>(rackSize);
		this.player = player;
	}
	
	public void takeTile() {
		while (this.player.getRack().getTiles().size() < rackSize && !this.player.getDeck().getDeck().isEmpty()) {
	        int lastIndex = this.player.getDeck().getDeck().size() - 1;
	        Tile tile = this.player.getDeck().getDeck().get(lastIndex);
	        this.player.getDeck().removeTile();
	        this.player.getRack().getTiles().add(tile);
	    }
	}

	
	public void removeTile(Tile tile) {
		if (this.player.getRack().getTiles().contains(tile)) {
			this.player.getRack().getTiles().remove(tile);
		}
	}
	
	public void putTilesInDeck() {
		for (Tile tile : this.tiles) {
			this.player.getDeck().addTile(tile);
		}
		this.tiles.clear();
	}
	
	public void fillRack() {
		while (this.tiles.size() < rackSize) {
			this.takeTile();
		}
	}
	
	public void printRack() {
	    int index = 1;
	    for (Tile tile : this.player.getRack().getTiles()) {
	        Console.message(index + " : " + tile.tileImage());
	        index++;
	    }
	}
	
	public Tile getTile(Integer index) {
		if (index < this.tiles.size()) {
			return this.tiles.get(index);
		} else {
			return null;
		}
	}

	public List<Tile> getTiles() {
		return this.tiles;
	}
	
	public Integer getRackSize() {
		return this.tiles.size();
	}
}