package fr.unilim.latice.application;

import java.util.Optional;

import fr.unilim.latice.controller.Referee;
import fr.unilim.latice.model.Console;
import fr.unilim.latice.model.GameBoard;
import fr.unilim.latice.model.Player;
import fr.unilim.latice.model.Position;
import fr.unilim.latice.model.Rack;
import fr.unilim.latice.model.Tile;
import fr.unilim.latice.model.enums.Color;
import fr.unilim.latice.view.HomeView;
import fr.unilim.latice.view.MainView;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class LaticeApplicationConsole extends Application {
	private static final String TXT_FINISHED = "La partie est terminée !";
	private static final String TXT_TOUR = "C'est le tour de ";
	private static GameBoard board = null;
	private static Referee referee = null;

	@Override
	public void start(Stage primaryStage) {
		HomeView homeView = new HomeView();
		homeView.initializeHomeView();
		MainView mainView = new MainView(board, referee);
		
		Pane root = homeView.currentRoot();
		Scene scene = new Scene(root, 1920, 1080);
		scene.getStylesheets().add(getClass().getResource("/deco.css").toExternalForm());
		
		homeView.playButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (!homeView.validateForm()) {
					return;
				}

				Player player1 = new Player(homeView.getNamePlayer1(), homeView.getBirthDatePlayer1());
				Player player2 = new Player(homeView.getNamePlayer2(), homeView.getBirthDatePlayer2());
				Player[] players = {player1, player2};

				referee.initGame(players);
				preparePlayers();
				mainView.initialiseTable();
				mainView.initialiseRackOfACurrentPlayer(true);
				mainView.initialiseShop();

				scene.setRoot(mainView.getWrapper());
			}
		});

		
		mainView.validateButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mainView.validateButton().setDisable(true);
				if (referee.isEndGame()) {
					Console.message(TXT_FINISHED);
					referee.showResult(board);
					showEndGameWindow(scene);
				} else {
					PauseTransition pause = new PauseTransition(Duration.seconds(2));
					mainView.rackView().fillRackView();
					
					pause.setOnFinished(e -> {
						referee.getCurrentPlayer().hasPerformedAction().set(false);
						referee.nextPlayer();
						referee.getCurrentPlayer().hasPerformedAction().set(false);
						mainView.initialiseRackOfACurrentPlayer(true);
						Console.message(TXT_TOUR + referee.getCurrentPlayer().getName());
						mainView.validateButton().setDisable(false);
					});
					
					pause.play();
				}
			}
		});

		
		mainView.passButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				
				if (referee.isEndGame()) {
					Console.message(TXT_FINISHED);
					referee.showResult(board);
					showEndGameWindow(scene);

					return;
				}
				
				referee.nextPlayer();
				mainView.initialiseRackOfACurrentPlayer(true);
				Console.message(TXT_TOUR + referee.getCurrentPlayer().getName());
			}
		});
		
		mainView.deckButton().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (!referee.getCurrentPlayer().hasPerformedAction().get()) {
					mainView.deckButton().setDisable(true);
					mainView.passButton().setDisable(true);
					referee.exchangeRack();
					mainView.initialiseRackOfACurrentPlayer(false);
				
					PauseTransition pause = new PauseTransition(Duration.seconds(3));
				
				
					pause.setOnFinished(e -> {
						if (referee.isEndGame()) {
							Console.message(TXT_FINISHED);
							referee.showResult(board);
							showEndGameWindow(scene);

							return;
						}
				
						else {
							referee.nextPlayer();
							mainView.initialiseRackOfACurrentPlayer(true);
							mainView.deckButton().setDisable(false);
							mainView.passButton().setDisable(false);
						}
					
					});
					pause.play();
				}
			}
		});

		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Main table view");
		primaryStage.setMaximized(true);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.show();
	}
	
	public static void preparePlayers() {
		Player player1 = referee.getSpecificPlayer(0);

		Player player2 = referee.getSpecificPlayer(1);

		referee.shuffleAndDistributeDeck();

		player1.setRack(new Rack(player1));
		player1.getRack().takeTile();

		player2.setRack(new Rack(player2));
		player2.getRack().takeTile();

		Console.clear();
	}

	public static void main(String[] args) {
		Console.message("Bienvenue dans le jeu Latice");
		Console.message("Choisissez votre type de jeu : ");
		Console.message("1. Jeu console");
		Console.message("2. Jeu graphique (JavaFX)");
		String choix = Console.inputInteger("Choix", 1, 2).toString();
		while (choix == null || (!choix.equals("1") && !choix.equals("2"))) {
			choix = Console.inputString("Choix invalide. Veuillez entrer 1 ou 2 : ");
		}

		referee = new Referee();

		// GAME BOARD
		GameBoard gameBoard = new GameBoard();
		gameBoard.init();

		if (choix.equals("1")) {
			referee.initGame(null);
			preparePlayers();
			while (!referee.isEndGame()) {
				mainGame(gameBoard);
			}
			
			referee.showResult(gameBoard);
		} else {
			board = gameBoard;
			launch(args);
		}
	}

	private static void mainGame(GameBoard gameBoard) {
	    boolean turnCompleted = false;
	    while (!turnCompleted) {
	        String resultat = determinateTurn(gameBoard);

	        if (resultat.equals("1")) {
	        	if (referee.getCurrentPlayer().hasPerformedAction().get()) {
	        		Console.message("dépense de l'argent");
	        	} else {
	        		playTurn(referee, gameBoard, null);
	        	}
	            turnCompleted = true;
	        } else if (resultat.equals("2")) {
	        	Boolean choix = referee.spendPointtoBuyExtraMove();
	        	if (choix) {
	        		playTurn(referee, gameBoard, null);
		            turnCompleted = true;
	        	}
	        } else if (resultat.equals("3")) {
	            referee.exchangeRack();
	            referee.nextPlayer();
	            turnCompleted = true;
	        } else if (resultat.equals("4")) {
	            referee.getCurrentPlayer().getRack().fillRack();
	            referee.nextPlayer();
	            turnCompleted = true;
	        } else {
	            Console.message("Choix invalide. Veuillez entrer 1, 2, 3 ou 4.");
	        }
	    }
	}

	private static String determinateTurn(GameBoard gameBoard) {
		Console.clear();
		gameBoard.display();
		Console.message(TXT_TOUR + referee.getCurrentPlayer().getName());
		Console.separator();
		Console.message("Vos points : " + referee.getCurrentPlayer().getPoints());
		Console.message("Nombre de tours" + ": " + referee.getNumberTurns().get());
		Console.message("Que voulez-vous faire ?");
		Console.message("1. Placer une tuile");
		Console.message("2. Acheter une action supplémentaire (2 points)");
		Console.message("3. Échanger tout le rack et passer son tour");
		Console.message("4. Passer votre tour");
		return Console.inputInteger("Choix", 1, 4).toString();
	}

	private static void playTurn(Referee referee, GameBoard gameBoard, String errorMessage) {
	    boolean tilePlaced = false;
	    while (!tilePlaced) {
	        Console.clear();
	        gameBoard.display();
	        if (errorMessage != null) {
	            Console.message(Color.RED.getCode() + errorMessage + Console.NEUTRAL_COLOR);
	        }

	        Player currentPlayer = referee.getCurrentPlayer();
	        Tile tile = referee.askTile();

	        if (tile == null) {
	            return;
	        }

	        Position position = Console.inputPosition();

	        if (!gameBoard.putTileOnBoardInAllDirections(tile, position, currentPlayer)) {
	            errorMessage = "La tuile ne peut pas être placée sur la case " + position.toString();
	        } else {
	            tilePlaced = true;
	        }
	    }
	}
	
	private void showEndGameWindow(Scene scene) {
		Player winner = referee.getWinner();
		String winnerName = "Aucun gagnant";
		if (winner != null) {
		    winnerName = winner.getName();
		}

		javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.NONE);
		alert.initStyle(StageStyle.UNDECORATED);
		alert.setTitle("Fin de la partie");
		alert.setHeaderText(null);

		if (winner != null) {
			alert.setContentText("La partie est terminée ! Le gagnant est : " + winnerName);
		} else {
			alert.setContentText("La partie est terminée ! Il n'y a pas de gagnant.");
		}

		alert.getDialogPane().getStylesheets().add(getClass().getResource("/deco.css").toExternalForm());

		ButtonType replayButton = new ButtonType("Rejouer");
		ButtonType quitButton = new ButtonType("Quitter");
		alert.getButtonTypes().setAll(replayButton, quitButton);

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == replayButton) {
			startNewGame(scene);
		} else {
			System.exit(0);
		}
	}

	private void startNewGame(Scene scene) {
	    board = new GameBoard();
	    board.init();

	    referee = new Referee();

	    Stage newStage = new Stage();
	    start(newStage);

	    Stage currentStage = (Stage) scene.getWindow();
	    currentStage.close();
	}



}