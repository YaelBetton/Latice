package fr.unilim.latice.view;

import fr.unilim.latice.controller.Referee;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ShopView {
    private VBox shopZone;

    public ShopView() {
        this.shopZone = new VBox();
        this.shopZone.setAlignment(Pos.CENTER);
    }

    public void initialiseShopView(Referee referee) {
        HBox shopHBox = new HBox();
        HBox turnCounterHBox = new HBox();

        shopHBox.setAlignment(Pos.CENTER);
        shopHBox.setPrefWidth(200);
        shopHBox.setPrefHeight(300);

        turnCounterHBox.setAlignment(Pos.CENTER);
        turnCounterHBox.setPrefWidth(300);
        turnCounterHBox.setPrefHeight(225);
        turnCounterHBox.setMaxWidth(300);
        turnCounterHBox.setMaxHeight(225);

        String joueur1Name = referee.getSpecificPlayer(0).getName();
        String joueur2Name = referee.getSpecificPlayer(1).getName();

        String playerNameStyle = "player-name";
        Label joueur1Label = new Label(joueur1Name);
        joueur1Label.getStyleClass().add(playerNameStyle);

        Label joueur2Label = new Label(joueur2Name);
        joueur2Label.getStyleClass().add(playerNameStyle);

        Label scorePlayer1Label = new Label();
        scorePlayer1Label.textProperty().bind(referee.getSpecificPlayer(0).getPointsProperty().asString());
        
        String scoreLabelStyle = "score-label";
        scorePlayer1Label.getStyleClass().add(scoreLabelStyle);

        Label scorePlayer2Label = new Label();
        scorePlayer2Label.textProperty().bind(referee.getSpecificPlayer(1).getPointsProperty().asString());
        scorePlayer2Label.getStyleClass().add(scoreLabelStyle);

        Label scoreTiretLabel = new Label(" - ");
        scoreTiretLabel.getStyleClass().add(scoreLabelStyle);

        Label pointsLabel = new Label("Points");
        pointsLabel.getStyleClass().add("titre-section");

        HBox pointTitreHBox = new HBox(pointsLabel);
        pointTitreHBox.prefHeight(40);
        pointTitreHBox.setAlignment(Pos.CENTER);

        HBox playerNamesHBox = new HBox(joueur1Label, joueur2Label);
        playerNamesHBox.setAlignment(Pos.CENTER);
        playerNamesHBox.setSpacing(180);

        HBox scoresHBox = new HBox(scorePlayer1Label, scoreTiretLabel, scorePlayer2Label);
        scoresHBox.setAlignment(Pos.CENTER);
        scoresHBox.setSpacing(60);

        Label extraMoveLabel = new Label("Coup Supplémentaire");
        extraMoveLabel.getStyleClass().add("option-label");
        extraMoveLabel.setAlignment(Pos.CENTER_LEFT);

        Button buyButton = new Button("2 Points");
        buyButton.setPrefWidth(90);
        buyButton.setPrefHeight(30);
        buyButton.setAlignment(Pos.CENTER);
        buyButton.getStyleClass().addAll("button", "shop-button");

        buyButton.setOnMouseClicked(event -> {
             if (referee.getCurrentPlayer().hasPerformedAction().get()) {
                 if (referee.getCurrentPlayer().getPoints() >= 2) {
                     referee.spendPointtoBuyExtraMove();
                 } else {
                     RackView.showError("Vous n'avez pas assez de points pour acheter un coup supplémentaire.");
                 }
            } else {
                RackView.showError("Vous ne pouvez pas rejouer : vous n'avez pas encore joué ce tour.");
            }
        });

        HBox extraMoveLine = new HBox(extraMoveLabel, buyButton);
        extraMoveLine.setAlignment(Pos.CENTER);
        extraMoveLine.setSpacing(20);

        VBox counterVBox = new VBox();
        counterVBox.setAlignment(Pos.TOP_CENTER);

        Label turnCounterTitleLabel = new Label("Nombre de Tour(s)");
        turnCounterTitleLabel.getStyleClass().add("turn-title");
        turnCounterTitleLabel.setAlignment(Pos.CENTER);
        turnCounterTitleLabel.setPadding(new Insets(15, 0, 0, 0));

        Label turnCounterLabel = new Label();
        turnCounterLabel.textProperty().bind(referee.getNumberTurns().asString());
        turnCounterLabel.getStyleClass().add("turn-counter");
        turnCounterLabel.setAlignment(Pos.CENTER);
        turnCounterLabel.setMaxWidth(Double.MAX_VALUE);
        turnCounterLabel.setPadding(new Insets(40, 0, 30, 0));

        counterVBox.getChildren().addAll(turnCounterTitleLabel, turnCounterLabel);

        VBox shopOptionsBox = new VBox(pointTitreHBox, playerNamesHBox, scoresHBox, extraMoveLine);
        pointTitreHBox.setAlignment(Pos.TOP_CENTER);
        extraMoveLine.setAlignment(Pos.BOTTOM_CENTER);
        shopOptionsBox.setSpacing(40);
        shopOptionsBox.setPadding(new Insets(0, 0, 10, 0));

        shopHBox.getChildren().add(shopOptionsBox);
        shopHBox.setSpacing(40);
        shopHBox.getStyleClass().add("shophbx");

        turnCounterHBox.getChildren().add(counterVBox);
        turnCounterHBox.setAlignment(Pos.CENTER);
        turnCounterHBox.getStyleClass().add("shophbx");

        this.shopZone.getChildren().addAll(shopHBox, turnCounterHBox);
        this.shopZone.setSpacing(50);
    }

    public VBox getShopZone() {
        return this.shopZone;
    }
}
