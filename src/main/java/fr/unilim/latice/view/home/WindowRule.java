package fr.unilim.latice.view.home;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WindowRule {

    public void show() {
        Stage ruleStage = new Stage();

        VBox contentBox = new VBox(20);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setMaxWidth(800);
        contentBox.setMinWidth(800);
        contentBox.setPrefWidth(800);
        contentBox.setPadding(new Insets(20, 0, 20, 250));
        contentBox.setSpacing(50);

        Button closeButton = new Button("Fermer");
        closeButton.setOnAction(e -> ruleStage.close());
        closeButton.setPadding(new Insets(20, 0, 0, 0));

        Label titleLabel = new Label("Règles du jeu");
        titleLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: white; -fx-font-family: 'Cinzel';");
        titleLabel.setPadding(new Insets(0));
        
        Image boardImage = new Image(getClass().getResourceAsStream("/assets/imagePlateauLatice.png"));
        ImageView boardImageView = new ImageView(boardImage);

        boardImageView.setFitWidth(400);  
        boardImageView.setPreserveRatio(true);

        // Ajout règle en créant les paragraphes avec CreationParagraphe.jave
        contentBox.getChildren().addAll(
            titleLabel,
            
            boardImageView,
            
            ParagraphCreator.createParagraph("🎯 But du jeu",
                "Chaque joueur doit poser ses tuiles sur le plateau en respectant certaines règles de placement. "
                + "Le but est de marquer des points en plaçant ses tuiles de façon stratégique. "
                + "À la fin de la partie, le joueur ayant posé le plus de tuiles sera déclaré vainqueur.", 
                26, 16),

            ParagraphCreator.createParagraph("🧩 Fonctionnement d'un tour",
                "À chaque tour, un joueur peut réaliser plusieurs actions : il peut poser une tuile de son rack sur le plateau, "
                + "à condition qu’elle corresponde soit par la couleur soit par le symbole à une tuile adjacente. "
                + "Si la tuile est posée à côté d’un Soleil, cela rapporte deux points ; sinon, cela rapporte un point. "
                + "Un joueur peut également choisir d'acheter une action supplémentaire pour deux points. "
                + "S'il le souhaite, il peut échanger l'intégralité de son rack pour de nouvelles tuiles, ou passer son tour sans action.", 
                24, 16),

            ParagraphCreator.createParagraph("✨ Particularités",
                "Dans cette version simplifiée du jeu, il n'existe plus de pierres spéciales : elles sont remplacées par un système de points. "
                + "Une demi-pierre équivaut à un point, et une pierre de Soleil vaut deux points. "
                + "Le jeu ne comporte pas de tuile Vent. "
                + "Lorsqu'un joueur souhaite échanger son rack, il doit obligatoirement échanger toutes ses tuiles sans exception. "
                + "Il n'y a pas de limite de points à atteindre au cours de la partie.", 
                24, 16),

            ParagraphCreator.createParagraph("🏆 Fin de la partie",
                "La partie peut se terminer de deux manières : soit lorsqu'un joueur n'a plus de tuiles et que la pioche est vide, "
                + "soit après dix cycles de jeu (un cycle correspondant à deux tours de jeu, soit un tour par joueur). "
                + "À l'issue de la partie, le joueur ayant posé le plus grand nombre de tuiles est déclaré vainqueur.", 
                24, 16),

            ParagraphCreator.createParagraph("⚡ Actions disponibles",
                "Pendant son tour, un joueur peut choisir de poser une tuile sur le plateau, d'acheter une action supplémentaire en dépensant deux points, "
                + "d'échanger l'intégralité de son rack, ou simplement de passer son tour.", 
                24, 16),

            closeButton
        );

        // Création ScrollPane pour défiler les règles
        ScrollPane scrollPane = new ScrollPane(contentBox);
        scrollPane.getStyleClass().add("scroll-pane");

        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        StackPane layout = new StackPane(scrollPane);
        Scene ruleScene = new Scene(layout, 1000, 600);

        ruleStage.setTitle("Règles du jeu");
        ruleScene.getStylesheets().add(getClass().getResource("/deco.css").toExternalForm());
        ruleStage.initStyle(StageStyle.UNDECORATED);
        ruleStage.setScene(ruleScene);

        ruleStage.show();

        // Charge le code d'abord et à la fin remonte en haut de la page
        javafx.application.Platform.runLater(() -> {
            scrollPane.setVvalue(0.0);
        });
    }
}
