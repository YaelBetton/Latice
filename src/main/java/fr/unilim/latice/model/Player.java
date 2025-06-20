package fr.unilim.latice.model;

import java.util.Date;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Player {
	private final String name;
	private Date birthdate;
	private Deck deck;
	private Rack rack;
	private IntegerProperty points = new SimpleIntegerProperty(0);
	private BooleanProperty hasPerformedAction = new SimpleBooleanProperty(false);
	
	public Player(String name, Date birthdate) {
		this.name = name;
		this.birthdate = birthdate;
	}

	public String getName() {
		return name;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public Rack getRack() {
		return rack;
	}

	public void setRack(Rack rack) {
		this.rack = rack;
	}

	public Integer getPoints() {
		return points.get();
	}
	
	public IntegerProperty getPointsProperty() {
		return points;
	}
	
	public void setPoints(Integer points) {
		this.points.set(points);
	}
	
	public void enumDeck() {
		this.deck.getDeck().forEach(tile -> Console.message(tile.tileImage() + ":" + tile.description()));
	}
	
	public void addPoints(Integer points, Integer pointsGameboard) {
		switch (points) {
			case 0 -> this.points.set(this.points.get() + pointsGameboard);
			case 1 -> this.points.set(this.points.get() + pointsGameboard);
			case 2 -> this.points.set(1 + this.points.get() + pointsGameboard);
			case 3 -> this.points.set(2 + this.points.get() + pointsGameboard);
			case 4 -> this.points.set(4 + this.points.get() + pointsGameboard);
			default -> this.points.set(0 + this.points.get() + pointsGameboard);
		}
	}
	
	public BooleanProperty hasPerformedAction() {
		return hasPerformedAction;
	}
	
}