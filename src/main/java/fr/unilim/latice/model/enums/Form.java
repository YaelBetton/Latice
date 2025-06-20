package fr.unilim.latice.model.enums;

public enum Form {

	FEATHER("🪶"),
	BIRD("🐦"),
	TURTLE("🐢"),
	FLOWER("🌸"),
	GECKO("🦎"),
	DOLPHIN("🐬");

	private final String emoji;

	Form(String emoji) {
		this.emoji = emoji;
	}

	public String getEmoji() {
		return emoji;
	}
	
}
