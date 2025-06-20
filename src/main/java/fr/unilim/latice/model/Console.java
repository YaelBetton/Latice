package fr.unilim.latice.model;

import java.util.Scanner;

public class Console {
	private static final String SEPARATOR_LINE = "-----------------------------------";
	public static final String NEUTRAL_COLOR = "\u001B[0m";
    public static final Scanner scanner = new Scanner(System.in);
    
    private Console() {
	}

    public static String inputString(String question) {
        while (true) {
            try {
            	Console.print(question + " : ");
                String result = scanner.nextLine();

                if (result == null || result.trim().isEmpty()) {
                    Console.message("L'entrée ne peut pas être vide.");
                    continue;
                }

                return result;
            } catch (Exception e) {
                Console.message("Une erreur s'est produite lors de la lecture de l'entrée : " + e.getMessage());
            }
        }
    }
    
    public static Integer inputInteger(String question, Integer min, Integer max) {
        while (true) {
            try {
                Console.print(question + " : ");
                String result = scanner.nextLine();

                // Check if the input is empty
                if (result == null || result.trim().isEmpty()) {
                    Console.message("L'entrée ne peut pas être vide.");
                } else {
                    int value = Integer.parseInt(result.trim());

                    // Check if the value is within the specified range
                    if (value < min || value > max) {
                        Console.message("La valeur doit être comprise entre " + min + " et " + max + ".");
                    } else {
                        return value;
                    }
                }
            } catch (NumberFormatException e) {
                Console.message("Veuillez entrer un nombre valide : " + e.getMessage());
            } catch (Exception e) {
                Console.message("Une erreur s'est produite : " + e.getMessage());
            }
        }
    }

    public static Position inputPosition() {
        while (true) {
            try {
                String resultX = inputString("Où voulez-vous jouer ? (coordonnée X)");
                String resultY = inputString("Où voulez-vous jouer ? (coordonnée Y)");

                Position position = new Position(Integer.parseInt(resultX) - 1, Integer.parseInt(resultY) - 1);
                if (!position.isValid(position)) {
                    Console.message("La position est invalide. Veuillez entrer des coordonnées valides.");
                    continue;
                }

                return position;
            } catch (NumberFormatException e) {
                Console.message("La valeur saisie est incorrecte : " + e.getMessage());
            } catch (Exception e) {
                Console.message("Une erreur s'est produite : " + e.getMessage());
            }
        }
    }
	
	public static void message(String message) {
		System.out.println(message);
	}
	
	public static void print(String message) {
		System.out.print(message);
	}
	
	public static void separator() {
		message(SEPARATOR_LINE);
	}
	
	public static void clear() {
		for (int i = 0; i < 50; i++) {
			message("");
		}
	}
	
}
