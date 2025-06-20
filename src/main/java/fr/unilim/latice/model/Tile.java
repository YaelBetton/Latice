package fr.unilim.latice.model;

import fr.unilim.latice.model.enums.Color;
import fr.unilim.latice.model.enums.Form;

public class Tile {
	private Form form;
	private Color color;
	
	public Tile(Form form, Color color) {
		this.form = form;
		this.color = color;
	}
	
	public String tileImage() {
		return color.getCode() + form.getEmoji() + Console.NEUTRAL_COLOR;
	}
	
	public String description() {
		return color.name() + " - " + form.name();
	}
	
	public Form getForm() {
		return this.form;
	}
	
	public Color getColor() {
		return this.color;
	}
}
