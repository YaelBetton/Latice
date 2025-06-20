package fr.unilim.latice.model.cell;

import fr.unilim.latice.model.Console;
import fr.unilim.latice.model.enums.Color;

public class SunCell extends Cell{
	private static String emoji = "☀️";

	public SunCell() {
		super(Color.YELLOW.getCode() + emoji + Console.NEUTRAL_COLOR);
	}

}
