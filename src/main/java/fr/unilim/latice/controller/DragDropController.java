package fr.unilim.latice.controller;

import fr.unilim.latice.model.GameBoard;
import fr.unilim.latice.model.Player;
import fr.unilim.latice.model.Position;
import fr.unilim.latice.model.Tile;
import fr.unilim.latice.model.cell.Cell;
import fr.unilim.latice.view.RackView;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class DragDropController {
	private static GameBoard gameBoard = null;
	
	public static EventHandler<MouseEvent> getDragDetectedController(StackPane tile, Tile tileID, Player owner) {
		return event -> {
			Dragboard db = tile.startDragAndDrop(TransferMode.MOVE);
			Integer imageIndexInTileObject = 1;
			ImageView snapshot = (ImageView) tile.getChildren().get(imageIndexInTileObject);
			db.setDragView(snapshot.getImage());
			
			ClipboardContent content = new ClipboardContent();
			content.putString(tileID.description());
			db.setContent(content);
			
			tile.setVisible(false);
			DragContent.tile = tileID;
			DragContent.owner = owner;
			System.out.println(tileID);
			
			
			event.consume();
		};
	}
	
	public static EventHandler<DragEvent> getDragDoneController(StackPane tile) {
		return event -> {
			if (!event.isDropCompleted()) {
				tile.setVisible(true);
			}
		};
	}
	
	public static EventHandler<DragEvent> getDragEnteredController(StackPane cell) {
		return event -> {
			if (event.getGestureSource() != event.getSource() && event.getDragboard().hasString()) {
				cell.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2); -fx-border-color: black; -fx-border-width: 2;");
			}
			
			event.consume();
		};
	}
	
	public static EventHandler<DragEvent> getDragExitedController(StackPane cell) {
		return event -> {
			cell.setStyle("-fx-background-color: #007BFF; -fx-border-color: black; -fx-border-width: 2;");
		};
	}
	
	// this method prevents us to drop an object in itself and verifies if there is a content in the dragged object
	public static EventHandler<DragEvent> getDragOverController() {
		return event -> {
			if (event.getGestureSource() != event.getSource() && event.getDragboard().hasString()) {
				event.acceptTransferModes(TransferMode.MOVE);
			}
			
			event.consume();
		};
	}
	
	public static EventHandler<DragEvent> getDragDroppedController(StackPane cell, Cell cellObject) {
		return event -> {
			Dragboard db = event.getDragboard();
			Tile draggedTileObject = DragContent.tile;
			Position positionOfCellToFill = cellObject.getPosition();
			Boolean isValideMove = Referee.valideMove(draggedTileObject, positionOfCellToFill, gameBoard, DragContent.owner);
			StackPane draggedTile = (StackPane) event.getGestureSource();
			
			if (cellObject.isFilled()) {
				draggedTile.setVisible(true);
				event.setDropCompleted(false);
			}
			
			else {
				if (db.hasString() && isValideMove) {
					((Pane) draggedTile.getParent()).getChildren().remove(draggedTile);
					cell.getChildren().add(draggedTile);
					draggedTile.setVisible(true);
					cell.setStyle("-fx-background-color: #007BFF;");
					draggedTile.setOnDragDetected(null);
					draggedTile.setOnDragDone(null);
					cellObject.setTile(DragContent.tile);
					DragContent.owner.getRack().removeTile(DragContent.tile);
					DragContent.owner.hasPerformedAction().set(true);
					DragContent.owner.getRack().printRack();  //to delete
					DragContent.tile = null;
					DragContent.owner = null;
					gameBoard.display(); //to delete 
					
					RackView.hideError();
					
					event.setDropCompleted(true);
				}
			
				else {
					draggedTile.setVisible(true);
					event.setDropCompleted(false);
					RackView.showError("Vous ne pouvez pas poser cette tuile ici !");
				}
			}
			
			
			
			event.consume();
		};
	}

	public static GameBoard getGameBoard() {
		return gameBoard;
	}

	public static void setGameBoard(GameBoard gameBoard) {
		DragDropController.gameBoard = gameBoard;
	}
	

}
