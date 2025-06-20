package fr.unilim.latice.view;

import fr.unilim.latice.controller.DragDropController;
import fr.unilim.latice.model.Console;
import fr.unilim.latice.model.Player;
import fr.unilim.latice.model.Tile;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;


public class RackView {
	private BorderPane playerZone;
	private Player player;
	private HBox rackCards;
	private static Label errorLabel;
	private int tileAnimationCounter = 0;



	
	
	public RackView(Player player) {
		this.player = player;
		this.playerZone = new BorderPane();
		this.playerZone.setPadding(new Insets(0, 15, 0, 15));
		this.playerZone.setMinWidth(650);
		this.playerZone.setMaxHeight(400);
		this.rackCards = new HBox();
		rackCards.setAlignment(Pos.CENTER);
		rackCards.setPrefWidth(300);
		rackCards.setPrefHeight(70);
		rackCards.setSpacing(40);
	}
	
	
	public void initialiseRackView(boolean gameStarted) {
	    StackPane rackStand = new StackPane();
	    rackStand.setPrefWidth(650);
	    rackStand.setPrefHeight(100);

	    Image image = new Image(getClass().getResource("/assets/rack.png").toExternalForm());
	    ImageView bgImageView = new ImageView(image);
	    bgImageView.setFitWidth(650);
	    bgImageView.setFitHeight(120);
	    bgImageView.setPreserveRatio(false);
	    StackPane.setAlignment(bgImageView, Pos.CENTER);
	    StackPane.setMargin(bgImageView, new Insets(7, 0, 0, 0));
	    rackStand.getChildren().add(bgImageView);

	    for (Tile tile : this.player.getRack().getTiles()) {
	        StackPane card = initialiseTileView(tile, gameStarted);
	        this.rackCards.getChildren().add(card);
	    }

	    rackStand.getChildren().add(rackCards);

	    VBox errorAndName = setErrorLabel();

	    VBox rackWithExtras = new VBox();
	    rackWithExtras.setAlignment(Pos.CENTER);
	    rackWithExtras.getChildren().addAll(rackStand, errorAndName);

	    this.playerZone.setCenter(rackWithExtras);
	}


	
	public static void showError(String message) {
	    errorLabel.setText(message);
	    errorLabel.setVisible(true);
	    PauseTransition pause = new PauseTransition(Duration.seconds(5));
	    pause.setOnFinished(event -> errorLabel.setVisible(false));
	    pause.play();
	}

	public static void hideError() {
	    errorLabel.setVisible(false);
	}
	
	public static void initializeErrorLabel() {
		errorLabel = new Label();
	    errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 20px;");
	    errorLabel.setVisible(false);
	    errorLabel.setWrapText(true);
	    errorLabel.setMaxWidth(600);
	    errorLabel.setAlignment(Pos.CENTER);
	}

	public VBox setErrorLabel() {
	    initializeErrorLabel();

	    Label playerNameLabel = new Label(this.player.getName());
	    playerNameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 32px;");
	    playerNameLabel.setAlignment(Pos.CENTER);

	    VBox bottomContent = new VBox();
	    bottomContent.setAlignment(Pos.CENTER);
	    bottomContent.getChildren().addAll(errorLabel, playerNameLabel);

	    return bottomContent;
	}

	
	public void fillRackView() {
		this.tileAnimationCounter = 0;

		Integer numberTilesNeeded = 5 - this.player.getRack().getTiles().size();
		while (numberTilesNeeded > 0) {
			this.player.getRack().takeTile();
			StackPane card = initialiseTileView(this.player.getRack().getTile(5 - numberTilesNeeded),false);
			this.rackCards.getChildren().add(card);
			numberTilesNeeded--;

		}
	}
	
	public StackPane initialiseTileView(Tile tile, boolean animateEntry) {
		StackPane card = new StackPane();
		Label cardValue = new Label(tile.description());
		String color = tile.getColor().name();
		String form = tile.getForm().name();
		card.setMaxWidth(70);
		card.setMaxHeight(70);
		card.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2;");
		card.getChildren().add(cardValue);
		card.setOnDragDetected(event -> {
			if (!player.hasPerformedAction().get()) {
				DragDropController.getDragDetectedController(card, tile, player).handle(event);
				DragDropController.getDragDoneController(card);
			}
			
			else {
				event.consume();
			}
		});
		
		try {
			Image image1 = new Image(getClass().getResource("/assets/" + color + "_" + form + ".png").toExternalForm());
			ImageView imageView = new ImageView(image1);
			imageView.setFitWidth(70);
			imageView.setFitHeight(70);
			card.getChildren().add(imageView);
			
		}
		catch (Exception e) {
			String imageName = color + "_" + form + ".png";
			Console.message("Error loading image for tile: " + imageName);
		}
		
		if (animateEntry) {
		    card.setTranslateY(-100);
		    card.setOpacity(0);

		    TranslateTransition slideDown = new TranslateTransition(Duration.millis(500), card);
		    slideDown.setToY(0);

		    FadeTransition fadeIn = new FadeTransition(Duration.millis(500), card);
		    fadeIn.setFromValue(0);
		    fadeIn.setToValue(1);

		    ParallelTransition anim = new ParallelTransition(slideDown, fadeIn);
		    anim.play();
		}
		else {
			card.setOpacity(0);

			Path path = new Path();
			path.getElements().add(new MoveTo(-700, -100));
			path.getElements().add(new QuadCurveTo(-350, -300, 37, 37));

			PathTransition pathTransition = new PathTransition(Duration.seconds(1), path, card);
			pathTransition.setInterpolator(javafx.animation.Interpolator.EASE_BOTH);

			FadeTransition fade = new FadeTransition(Duration.seconds(1), card);
			fade.setFromValue(0);
			fade.setToValue(1);

			ParallelTransition animation = new ParallelTransition(pathTransition, fade);

			PauseTransition delay = new PauseTransition(Duration.millis(300.0 * tileAnimationCounter++));
			delay.setOnFinished(e -> animation.play());

			delay.play();
		}

		
		return card;
		
	}
	
	public void initialisePlayerControlsView(Button validateButton, Button passButton) {
		// Setting control buttons 
		
		StackPane buttonBox = new StackPane();
		buttonBox.setMinHeight(50);
		buttonBox.setMinWidth(80);
		buttonBox.setAlignment(Pos.CENTER);
		
		buttonBox.getChildren().add(passButton);
		StackPane.setMargin(passButton, new Insets(0, 0, 0, 40));
		
		this.player.hasPerformedAction().addListener((observable, oldValue, newValue) -> {
			if (!oldValue) {
				if (!buttonBox.getChildren().isEmpty()) {
					buttonBox.getChildren().remove(passButton);
		        }
				
				buttonBox.getChildren().add(validateButton);
				StackPane.setMargin(validateButton, new Insets(0, 0, 0, 40));
			}
		});
		this.playerZone.setRight(buttonBox);
	}
	
	public void initialisePlayerDeckView(Button deckButton) {
	    StackPane deckContainer = new StackPane();
	    deckContainer.setPrefWidth(200);
	    deckContainer.setPrefHeight(200);

	    deckContainer.getChildren().add(deckButton);
	    this.playerZone.setLeft(deckContainer);
	}

	
	public BorderPane getPlayerZone() {
		return this.playerZone;
	}
}