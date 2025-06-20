package fr.unilim.latice.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import fr.unilim.latice.model.Console;
import fr.unilim.latice.model.Deck;
import fr.unilim.latice.model.GameBoard;
import fr.unilim.latice.model.Player;
import fr.unilim.latice.model.Position;
import fr.unilim.latice.model.Tile;
import fr.unilim.latice.model.cell.Cell;
import fr.unilim.latice.model.enums.Color;
import fr.unilim.latice.model.enums.Direction;
import fr.unilim.latice.model.enums.Form;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Referee {
	private Player[] players;
	private Player currentPlayer;
	private Integer playerNumber = 2;
	private Integer numberActions = 0;
	private IntegerProperty numberTurns = new SimpleIntegerProperty(0);
    Random random = new Random();
	
	private static final Position[] SUN_POSITIONS = {
	        new Position(0, 0), new Position(4, 0), new Position(8, 0),
	        new Position(1, 1), new Position(2, 2), new Position(0, 4), new Position(0, 8),
	        new Position(4, 8), new Position(8, 8), new Position(7, 7), new Position(6, 6),
	        new Position(7, 1), new Position(6, 2), new Position(1, 7), new Position(2, 6),
	        new Position(8, 4)
	};
	
	public void initGame(Player[] players) {
	    this.players = new Player[playerNumber];

	    if (players == null) {
	        for (int i = 0; i < playerNumber; i++) {
	            Optional<String> playerName = promptUniquePlayerName(i);
	            if (playerName.isEmpty()) {
	                Console.message("Annulation de l'initialisation du joueur.");
	                return;
	            }

	            Optional<Date> birthdate = promptValidBirthdate();
	            if (birthdate.isEmpty()) {
	                Console.message("Annulation de l'initialisation du joueur.");
	                return;
	            }

	            this.players[i] = new Player(playerName.get(), birthdate.get());
	        }
	    } else {
	        System.arraycopy(players, 0, this.players, 0, Math.min(players.length, playerNumber));
	    }

	    this.currentPlayer = whoStarts();
	}

	private Optional<String> promptUniquePlayerName(int playerIndex) {
	    for (int attempt = 0; attempt < 5; attempt++) {
	        String name = Console.inputString("Entrez le nom du joueur " + (playerIndex + 1)).trim();

	        if (name.isEmpty()) {
	            Console.message("Le nom du joueur ne peut pas être vide. Veuillez réessayer.");
	            continue;
	        }

	        boolean isDuplicate = Arrays.stream(players, 0, playerIndex)
	            .filter(Objects::nonNull)
	            .anyMatch(p -> p.getName().equalsIgnoreCase(name));

	        if (isDuplicate) {
	            Console.message("Le nom du joueur doit être unique. Veuillez réessayer.");
	        } else {
	            return Optional.of(name);
	        }
	    }
	    return Optional.empty();
	}

	private Optional<Date> promptValidBirthdate() {
	    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	    format.setLenient(false);

	    for (int attempt = 0; attempt < 5; attempt++) {
	        try {
	            String input = Console.inputString("Entrez votre date de naissance (jj/mm/aaaa)");
	            Date birthdate = format.parse(input);
	            if (!birthdate.after(new Date())) {
	                return Optional.of(birthdate);
	            }
	            Console.message("La date de naissance ne peut pas être dans le futur. Veuillez réessayer.");
	        } catch (ParseException e) {
	            Console.message("Format invalide. Veuillez respecter le format jj/mm/aaaa.");
	        }
	    }
	    return Optional.empty();
	}
	
	
	
	
	
	
	
	public Player whoStarts() {
	    List<Player> youngestPlayers = new ArrayList<>();
	    Date latestBirthdate = players[0].getBirthdate();

	    for (Player player : players) {
	        Date birthdate = player.getBirthdate();
	        if (birthdate.after(latestBirthdate)) {
	            latestBirthdate = birthdate;
	            youngestPlayers.clear();
	            youngestPlayers.add(player);
	        } else if (birthdate.equals(latestBirthdate)) {
	            youngestPlayers.add(player);
	        }
	    }

	    return youngestPlayers.get(random.nextInt(youngestPlayers.size()));
	}
	
	
	
	public Player getCurrentPlayer(){
		return currentPlayer;
	}
	
	public void changeCurrentPlayer(){
		if (currentPlayer == players[0]) {
			currentPlayer = players[1];
		} else {
			currentPlayer = players[0];
		}
	}
	
	public void nextPlayer() {
	    int index = Arrays.asList(players).indexOf(currentPlayer);
	    if (index == -1) {
	        throw new IllegalStateException("Le joueur actuel n'est pas trouvé dans la liste des joueurs.");
	    }
	    currentPlayer = players[(index + 1) % players.length];
	    numberActions++;
	    if (numberActions % 2 == 0) {
	    	incrementNumberTurns(1);
	    }
	}

	public void shuffleAndDistributeDeck() {
	    List<Tile> allTilesList = new ArrayList<>();
	    for (Form form : Form.values()) {
	        for (Color color : Color.values()) {
	            allTilesList.add(new Tile(form, color));
	            allTilesList.add(new Tile(form, color));
	        }
	    }

	    Collections.shuffle(allTilesList, new Random());

	    int totalTiles = allTilesList.size();
	    int tilesPerPlayer = totalTiles / playerNumber;

	    if (totalTiles % playerNumber != 0) {
	        Console.message("Attention : les tuiles ne sont pas divisibles également entre tous les joueurs !");
	        return;
	    }

	    for (int i = 0; i < playerNumber; i++) {
	        Tile[] playerTiles = new Tile[tilesPerPlayer];
	        for (int j = 0; j < tilesPerPlayer; j++) {
	            playerTiles[j] = allTilesList.get(i * tilesPerPlayer + j);
	        }

	        players[i].setDeck(new Deck(playerTiles));
	    }

	}
	
	public Tile askTile() {
	    try {
	    	Console.separator();
	    	Console.message("C'est le tour de " + currentPlayer.getName());
	    	currentPlayer.getRack().printRack();
	    	
	        String resultNum = Console.inputString("Quelle est le numéro de la tuile dans votre jeu ? (entre 1 et 5) (\"retour\" pour annuler)");
	
	        if (resultNum == null || resultNum.trim().isEmpty()) {
	            Console.message("La valeur saisie est vide ou nulle.");
	            return null;
	        }
	        
	        if (resultNum.equalsIgnoreCase("retour")) {
	        	return null;
	        }
	
	        int index = Integer.parseInt(resultNum.trim());
	
	        if (index < 1 || index > 5) {
	            Console.message("La valeur saisie doit être comprise entre 1 et 5 : " + index);
	            return null;
	        }
	
	        Tile tile = currentPlayer.getRack().getTile(index - 1);
	        if (tile == null) {
	            Console.message("La tuile demandée n'existe pas dans le jeu.");
	            return null;
	        }
	        
	        return tile;
	    } catch (NumberFormatException e) {
	        Console.message("Veuillez entrer un nombre valide : " + e.getMessage());
	        return null;
	    } catch (Exception e) {
	        Console.message("Une erreur s'est produite : " + e.getMessage());
	        return null;
	    }
	}
	
	
	public static boolean firstMove(Position position) {
        return position.row() == 4 && position.column() == 4;
    }
	
	public static boolean valideMove(Tile tile, Position position, GameBoard gameBoard, Player currentPlayer) {
	    if (gameBoard.allSellIsFilled()) {
	        return firstMove(position);
	    }

	    int pointsFromSun = isSunPosition(position) ? 2 : 0;
	    Color tileColor = tile.getColor();
	    Form tileShape = tile.getForm();

	    boolean hasAtLeastOneValidNeighbor = false;
	    int similarityCount = 0;

	    for (Direction direction : Direction.values()) {
	        Position adjacentPos = Position.getAdjacentPosition(position, direction);

	        boolean isValid = adjacentPos.isValid(adjacentPos);
	        Cell cell = isValid ? gameBoard.getCell(adjacentPos) : null;
	        boolean isFilled = cell != null && cell.isFilled();

	        if (isValid && isFilled) {
	            Tile adjacentTile = cell.getTile();
	            boolean colorMatch = adjacentTile.getColor() == tileColor;
	            boolean shapeMatch = adjacentTile.getForm() == tileShape;

	            if (colorMatch || shapeMatch) {
	                hasAtLeastOneValidNeighbor = true;
	                similarityCount++;
	            } else {
	                hasAtLeastOneValidNeighbor = false;
	                break;
	            }
	        }
	    }

	    if (hasAtLeastOneValidNeighbor) {
	        currentPlayer.addPoints(similarityCount, pointsFromSun);
	        return true;
	    }

	    return false;
	}
	
	private static boolean isSunPosition(Position position) {
        for (Position sun : SUN_POSITIONS) {
            if (Objects.equals(sun.row(), position.row()) && Objects.equals(sun.column(), position.column())) {
                return true;
            }
        }
        return false;
    }
	
	public static boolean isWinningCombination(Tile tile1, Tile tile2) {
        return tile1.getForm() == tile2.getForm() || tile1.getColor() == tile2.getColor();
    }

	
	public Boolean isEndGame() {
		for (Player player : players) {
			if (player.getDeck().getDeck().isEmpty() && player.getRack().getTiles().isEmpty()) {
				return true;
			}
		}
		
		return this.numberActions() == (this.players.length * 10) + 1;
	}
	
	public void showResult(GameBoard gameBoard) {
	    Console.clear();
	    gameBoard.display();
	    Console.separator();
	    Console.message("La partie est terminée !");
	    
	    Player winner = getWinner();
	    
	    if (winner == null) {
	        Console.message("Il n'y a pas de gagnant, égalité entre les joueurs !");
	    } else {
	        int winnerTilesCount = winner.getDeck().getDeck().size() + winner.getRack().getTiles().size();
	        Console.message("Le gagnant est : " + winner.getName() + " avec " + winnerTilesCount + " tuiles restantes.");
	    }
	    
	    Console.message("Merci d'avoir joué !");
	    Console.message("Au revoir !");
	}

	
	public Boolean spendPointtoBuyExtraMove() {
		Player curentPlayer = getCurrentPlayer();
		if (curentPlayer.getPoints() >= 2) {
			curentPlayer.setPoints(curentPlayer.getPoints() - 2);
			Console.message(curentPlayer.getName() + " a dépensé 2 points pour un mouvement supplémentaire.");
			curentPlayer.hasPerformedAction().set(false);
			return true;
		} else {
			Console.message(curentPlayer.getName() + " n'a pas assez de points pour acheter un mouvement supplémentaire.");
			return false;
		}
	}
	
	public void exchangeRack() {
		Player player = getCurrentPlayer();
		player.getRack().putTilesInDeck();
		player.getDeck().shuffle();
		player.getRack().fillRack();
	}
	
	
	public Boolean canExtraMove() {
        Player player = getCurrentPlayer();
        return player.getPoints() >= 2;
    }
	
	
	public Player getWinner() {
	    Player winner = null;
	    int minTiles = 36;
	    boolean egality = false;

	    for (Player player : players) {
	        int playerTiles = player.getDeck().getDeck().size() + player.getRack().getTiles().size();
	        if (playerTiles < minTiles) {
	            minTiles = playerTiles;
	            winner = player;
	            egality = false;
	        } else if (playerTiles == minTiles) {
	            egality = true;
	        }
	    }

	    if (egality) {
	        return null;
	    } else {
	        return winner;
	    }
	}

	
	public Player getSpecificPlayer(int i) {
		if (i < 0 || i >= players.length) {
			throw new IllegalArgumentException("Invalid player index: " + i);
		}
		return players[i];
	}
	
	public IntegerProperty getNumberTurns() {
		return this.numberTurns;
	}
	
	public void incrementNumberTurns(Integer value) {
		this.numberTurns.setValue(this.numberTurns.get() + value);
	}
	
	public Integer numberActions() {
		return numberActions;
	}
}