package fr.unilim.latice.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.control.Button;

import fr.unilim.latice.model.Console;
import fr.unilim.latice.controller.Referee;
import fr.unilim.latice.model.GameBoard;

public class MainView {
	private GameBoard gameBoard;
	private StackPane wrapper;
	private HBox gameTable;
	private VBox gameZone;
	private HBox rackZone;
	private VBox playerControlZone;
	private Referee referee;
	private Button validateButton;
	private Button passButton;
	private Button deckButton;
	private RackView rackView;
	
	public MainView(GameBoard board, Referee referee) {
		this.referee = referee;
		this.wrapper = new StackPane();
		this.wrapper.setPrefWidth(1920);
		this.wrapper.setPrefHeight(1080);
		
        Media media = new Media(getClass().getResource("/assets/videoFond.mp4").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(0.0);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(0.0);

        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setPreserveRatio(false);
        mediaView.setFitWidth(1920);
        mediaView.setFitHeight(1200);
        this.wrapper.getChildren().add(mediaView);
        
		this.gameBoard = board;
		this.gameTable = new HBox();
		
		this.gameZone = new VBox();
		this.gameZone.setPrefWidth(1320);
		//this.gameZone.setStyle("-fx-background-color: #007BFF; -fx-border-color: red; -fx-border-width: 2;");  //to find out the size
		this.gameZone.setAlignment(Pos.CENTER);
		
		this.playerControlZone = new VBox();
        this.playerControlZone.setPrefWidth(600);
        this.playerControlZone.setAlignment(Pos.TOP_CENTER);
        //this.playerControlZone.setStyle("-fx-background-color: #007BFF; -fx-border-color: red; -fx-border-width: 2;");  // to find out the size 
		
		this.rackZone = new HBox();
		this.rackZone.setPrefWidth(500);
		this.rackZone.setPrefHeight(80);
		this.rackZone.setAlignment(Pos.CENTER_LEFT);
		
		this.gameTable.getChildren().addAll(playerControlZone, gameZone);
		this.wrapper.getChildren().add(this.gameTable);

		this.validateButton = setValidateButton();
	    this.passButton = setPassButton();
	    this.deckButton = setDeckButton();
	}
	
	public void initialiseTable() {
		GameBoardView boardView = new GameBoardView(this.gameBoard);
		boardView.display();
		this.gameZone.getChildren().add(boardView.getGrid());
	}
	
	public void initialiseShop() {
        ShopView shopView = new ShopView();
        shopView.initialiseShopView(referee);

        Label lblLaticeTitre = new Label("LATICE");
        lblLaticeTitre.setId("lblTitreJeu");
        lblLaticeTitre.setAlignment(Pos.CENTER);
        lblLaticeTitre.setPadding(new Insets(50, 0, 0, 10));

        Label boutique = new Label("Boutique");
        boutique.getStyleClass().add("boutique");
        boutique.setAlignment(Pos.CENTER);
        boutique.setPadding(new Insets(60, 0, 60, 0));

        lblLaticeTitre.setAlignment(Pos.CENTER);
        boutique.setAlignment(Pos.CENTER);


        this.playerControlZone.getChildren().addAll(lblLaticeTitre,boutique,shopView.getShopZone());
        this.playerControlZone.setPadding(new Insets(0, 0, 0, 50));

    }
	
	public void initialiseRackOfACurrentPlayer(boolean animateEntry) {
		rackView = new RackView(referee.getCurrentPlayer());
		Console.message(referee.getCurrentPlayer().getName());
		rackView.initialiseRackView(animateEntry);
		rackView.initialisePlayerControlsView(this.validateButton, this.passButton);
		rackView.initialisePlayerDeckView(this.deckButton);
		
		this.rackZone.getChildren().clear();
		this.rackZone.getChildren().add(rackView.getPlayerZone());
		HBox.setMargin(rackView.getPlayerZone(), new Insets(20, 0, 0, 0));
		if (!this.gameZone.getChildren().contains(this.rackZone)) {
			this.gameZone.getChildren().add(this.rackZone);
		}
	}
	
	public Button setValidateButton() {
		Button btnValidate = new Button("Validate");
		btnValidate.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
		btnValidate.setPrefWidth(150);
		btnValidate.setMaxWidth(150);
		btnValidate.setMaxHeight(50);
		btnValidate.setPrefHeight(50);
	    return btnValidate;
	}
	
	public Button setPassButton() {
		Button btnPass = new Button("Pass");
		btnPass.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
		btnPass.setPrefWidth(150);
		btnPass.setMaxWidth(150);
		btnPass.setMaxHeight(50);
		btnPass.setPrefHeight(50);
		return btnPass;
	}
	
	public Button setDeckButton() {
		// Images pour normal et hover
	    Image normalDeck = new Image(getClass().getResource("/assets/Deck.png").toExternalForm());
	    Image hoverDeck = new Image(getClass().getResource("/assets/DeckShine.png").toExternalForm());

	    ImageView deckView = new ImageView(normalDeck);
	    deckView.setFitHeight(300);
	    deckView.setFitWidth(300);
	    deckView.setPreserveRatio(false);

	    Button btnDeck = new Button();
	    btnDeck.getStyleClass().add("deck-button");
	    btnDeck.setGraphic(deckView);
	    
	    btnDeck.setOnMouseEntered(e -> deckView.setImage(hoverDeck));
	    
	    btnDeck.setOnMouseExited(e -> deckView.setImage(normalDeck));
	    
	    return btnDeck;
	}
	
	public HBox getGameTable() {
		return this.gameTable;
	}
	
	public StackPane getWrapper() {
		return this.wrapper;
	}
	
	public Button validateButton() {
		return this.validateButton;
	}
	
	public Button passButton() {
		return this.passButton;
	}
	
	public Button deckButton() {
		return this.deckButton;
	}
	
	public RackView rackView() {
		return this.rackView;
	}
}