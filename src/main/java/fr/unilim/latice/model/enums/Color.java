package fr.unilim.latice.model.enums;

public enum Color {
	YELLOW("\u001B[33m"),
	NAVY("\u001B[34m"),     
	MAGENTA("\u001B[35m"),
	RED("\u001B[31m"),
	GREEN("\u001B[32m"),
	TEAL("\u001B[36m");
	
	private final String code;

	Color(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}

