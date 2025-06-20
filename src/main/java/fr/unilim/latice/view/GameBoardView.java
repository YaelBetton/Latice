package fr.unilim.latice.view;

import fr.unilim.latice.controller.DragDropController;
import fr.unilim.latice.model.GameBoard;
import fr.unilim.latice.model.Position;
import fr.unilim.latice.model.cell.Cell;
import fr.unilim.latice.model.cell.MoonCell;
import fr.unilim.latice.model.cell.SunCell;
import fr.unilim.latice.model.interfaces.DisplayGameboard;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class GameBoardView implements DisplayGameboard {
    private GridPane grid;
    private GameBoard board;

    public GameBoardView(GameBoard board) {
        this.grid = new GridPane();
        this.grid.setAlignment(Pos.CENTER);
        this.board = board;
    }

    @Override
    public void display() {
        DragDropController.setGameBoard(board);

        grid.setVgap(1);
        grid.setHgap(1);

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getColumns(); col++) {
                Position position = new Position(col, row);
                Cell cell = board.getCell(position);

                StackPane content = new StackPane();
                content.setStyle("-fx-background-color: #007BFF; -fx-border-color: black; -fx-border-width: 2;");
                content.setPrefWidth(80);
                content.setPrefHeight(80);

                ImageView imageView = getCellImageView(cell);
                content.getChildren().add(imageView);

                content.setOnDragOver(DragDropController.getDragOverController());
                content.setOnDragEntered(DragDropController.getDragEnteredController(content));
                content.setOnDragExited(DragDropController.getDragExitedController(content));
                content.setOnDragDropped(DragDropController.getDragDroppedController(content, cell));

                this.grid.add(content, col, row);
            }
        }
    }

    private ImageView getCellImageView(Cell cell) {
        String imagePath = "/assets/bg_sea.png";

        if (cell instanceof MoonCell) {
            imagePath = "/assets/bg_moon.png";
        } else if (cell instanceof SunCell) {
            imagePath = "/assets/bg_sun.png";
        }

        Image image = new Image(getClass().getResource(imagePath).toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);
        imageView.setPreserveRatio(true);

        return imageView;
    }

    public GridPane getGrid() {
        return grid;
    }
}
