package fr.unilim.latice.view.home;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ParagraphCreator {

    public static VBox createParagraph(String title, String content, int titleSize, int contentSize) {
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Cinzel", FontWeight.BOLD, titleSize));
        titleLabel.setStyle("-fx-text-fill: #e0e0ff;");

        Label contentLabel = new Label(content);
        contentLabel.setWrapText(true);
        contentLabel.setFont(Font.font("Cinzel", contentSize));
        contentLabel.setStyle("-fx-text-fill: white;");

        VBox paragraphBox = new VBox();
        paragraphBox.getChildren().addAll(titleLabel, contentLabel);
        
        return paragraphBox;
    }
}
