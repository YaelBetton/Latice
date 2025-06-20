package fr.unilim.latice.view;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import fr.unilim.latice.view.home.WindowRule;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;

public class HomeView {
	private Pane currentRoot;
	private StackPane mainMenuRoot;

	private TextField textFieldPlayer1;
	private TextField textFieldPlayer2;

	private DatePicker datePickerLeft;
	private DatePicker datePickerRight;

    private Label errorPseudoLeft;
    private Label errorPseudoRight;
    
    private Label errorDateLeft;
    private Label errorDateRight;
    
    private Label errorImageConfirmLeft;
    private Label errorImageConfirmRight;
    
    
    	
	private Button playButton = new Button("Valider");

	public void initializeHomeView() {

		Font cinzelFont = Font.loadFont(getClass().getResourceAsStream("/assets/Cinzel-Bold.ttf"), 120);

		Media media = new Media(getClass().getResource("/assets/videoFond.mp4").toExternalForm());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		MediaView mediaView = new MediaView(mediaPlayer);

		mediaView.setFitWidth(1920);
		mediaView.setFitHeight(1080);
		mediaView.setPreserveRatio(false);

		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();
		mediaPlayer.setVolume(0);

		VBox menuBox = new VBox(20);
		menuBox.setAlignment(Pos.CENTER);

		Button validateButton = new Button("Jouer");
		Button rulesButton = new Button("Règle");
		Button quitButton = new Button("Quitter");

		String menuButtonStyle = "bouton-menu";
		validateButton.getStyleClass().add(menuButtonStyle);
		rulesButton.getStyleClass().add(menuButtonStyle);
		quitButton.getStyleClass().add(menuButtonStyle);

		menuBox.getChildren().addAll(validateButton, rulesButton, quitButton);
		menuBox.setPadding(new Insets(300, 0, 0, 0));

		Label titleLabel = new Label("Latice");
		titleLabel.setId("lblTitreJeu");
		titleLabel.setFont(cinzelFont);
		titleLabel.setTextFill(javafx.scene.paint.Color.WHITE);

		VBox titleBox = new VBox(titleLabel);
		titleBox.setAlignment(Pos.TOP_CENTER);
		titleBox.setPadding(new Insets(200, 0, 0, 0));

		quitButton.setOnAction(e -> {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.initStyle(StageStyle.UNDECORATED);
			alert.getDialogPane().getStylesheets().add(getClass().getResource("/deco.css").toExternalForm());
			alert.setTitle("Fermer le jeu");
			alert.setHeaderText(null);
			alert.setContentText("Voulez-vous vraiment quitter le jeu ?");

			ButtonType quit = new ButtonType("Quitter");
			ButtonType stay = new ButtonType("Revenir au jeu");

			alert.getButtonTypes().setAll(stay, quit);

			alert.showAndWait().ifPresent(response -> {
				if (response == quit) {
					Platform.exit();
				} else {
					e.consume();
				}
			});
		});

		rulesButton.setOnAction(e -> openRules());

		validateButton.setOnAction(e -> {
			openForm();
			Scene scene = validateButton.getScene();
			if (scene != null) {
				scene.setRoot(this.currentRoot);
			}
		});

		this.mainMenuRoot = new StackPane(mediaView, titleBox, menuBox);
		this.currentRoot = this.mainMenuRoot;
	}

	public boolean validateForm() {
	    boolean isValid = true;

	    errorPseudoLeft.setText("");
	    errorDateLeft.setText("");
	    errorImageConfirmLeft.setText("");

	    errorPseudoRight.setText("");
	    errorDateRight.setText("");
	    errorImageConfirmRight.setText("");

	    if (textFieldPlayer1.getText().trim().isEmpty()) {
	        errorPseudoLeft.setText("Veuillez entrer un pseudo");
	        isValid = false;
	    }

	    if (datePickerLeft.getValue() == null) {
	        errorDateLeft.setText("Veuillez choisir une date");
	        isValid = false;
	    } else if (!datePickerLeft.getValue().isBefore(LocalDate.now())) {
	        errorDateLeft.setText("La date doit être antérieure à aujourd'hui");
	        isValid = false;
	    }

	    if (textFieldPlayer2.getText().trim().isEmpty()) {
	        errorPseudoRight.setText("Veuillez entrer un pseudo");
	        isValid = false;
	    }

	    if (datePickerRight.getValue() == null) {
	        errorDateRight.setText("Veuillez choisir une date");
	        isValid = false;
	    } else if (!datePickerRight.getValue().isBefore(LocalDate.now())) {
	        errorDateRight.setText("La date doit être antérieure à aujourd'hui");
	        isValid = false;
	    }

		if (textFieldPlayer1.getText().trim().equals(textFieldPlayer2.getText().trim())) {
			errorPseudoLeft.setText("Les pseudos ne peuvent pas être identiques");
			errorPseudoRight.setText("Les pseudos ne peuvent pas être identiques");
			isValid = false;
		}
	    
	    return isValid;
	}


	
	
	private void openForm() {

		StackPane left = new StackPane();
		StackPane right = new StackPane();

		left.setPrefWidth(600);
		right.setPrefWidth(600);

		VBox leftForm = createForm("Joueur 1");
		VBox rightForm = createForm("Joueur 2");

		left.getChildren().add(leftForm);
		right.getChildren().add(rightForm);
		leftForm.setAlignment(Pos.CENTER);
		leftForm.setPadding(new Insets(0, 0, 0, 200));
		rightForm.setAlignment(Pos.CENTER);
		rightForm.setPadding(new Insets(0, 0, 0, 100));

		Button backButton = new Button("Retour au menu");
		backButton.setOnAction(e -> {
			this.currentRoot = this.mainMenuRoot;
			Scene scene = backButton.getScene();
			if (scene != null) {
				scene.setRoot(this.currentRoot);
			}
		});

		HBox buttonsBox = new HBox(20, this.playButton, backButton);
		buttonsBox.setAlignment(Pos.CENTER);

		HBox formBox = new HBox(left, right);
		formBox.setAlignment(Pos.CENTER);

		Label titleLabel = new Label("Inscription");
		titleLabel.setId("lblTitrePage");

		HBox titleBox = new HBox(titleLabel);
		titleBox.setAlignment(Pos.CENTER);
		titleBox.setPadding(new Insets(0, 0, 50, 0));

		VBox root = new VBox(titleBox, formBox, buttonsBox);
		root.setPadding(new Insets(20, 0, 0, 0));
		root.setAlignment(Pos.TOP_CENTER);

		this.currentRoot = root;
	}

	private VBox createForm(String name) {
		VBox form = new VBox();
		form.setPadding(new Insets(10));

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(5));

		Label playerLabel = new Label(name);
		playerLabel.setPadding(new Insets(0, 150, 80, 0));
		playerLabel.setAlignment(Pos.CENTER);
		playerLabel.setId("lblJoueur");

		Label pseudoLabel = new Label("Pseudo :");
		TextField pseudoTextField = new TextField();
		pseudoTextField.setPromptText("Entrez votre pseudo ici");

		Label pseudoError = new Label();
		pseudoTextField.setPrefWidth(300);

		Label birthDateLabel = new Label("Date de naissance :");
		DatePicker birthDatePicker = new DatePicker();
		Label birthDateError = new Label();
		birthDatePicker.setPrefWidth(300);

		Label imageLabel = new Label("Image (Optionnel) :");
		Button photoButton = new Button("Photo");
		photoButton.setPrefWidth(300);
		Label imageConfirmError = new Label();

		ImageView imageView = new ImageView();
		imageView.setFitWidth(150);
		imageView.setFitHeight(150);
		imageView.setPreserveRatio(true);

		if (name.equals("Joueur 1")) {
		    textFieldPlayer1 = pseudoTextField;
		    datePickerLeft = birthDatePicker;
		    errorPseudoLeft = pseudoError;
		    errorDateLeft = birthDateError;
		    errorImageConfirmLeft = imageConfirmError;
		} else {
		    textFieldPlayer2 = pseudoTextField;
		    datePickerRight = birthDatePicker;
		    errorPseudoRight = pseudoError;
		    errorDateRight = birthDateError;
		    errorImageConfirmRight = imageConfirmError;
		}


		String errorTextStyle = "erreurText";
		imageConfirmError.getStyleClass().add(errorTextStyle);
		pseudoError.getStyleClass().add(errorTextStyle);
		birthDateError.getStyleClass().add(errorTextStyle);

		grid.add(pseudoLabel, 0, 0);
		grid.add(pseudoTextField, 0, 1);
		grid.add(pseudoError, 0, 2);

		grid.add(birthDateLabel, 0, 10);
		grid.add(birthDatePicker, 0, 11);
		grid.add(birthDateError, 0, 12);

		grid.add(imageLabel, 0, 13);
		grid.add(photoButton, 0, 14);
		grid.add(imageConfirmError, 0, 15);

		grid.add(imageView, 0, 16);
		GridPane.setHalignment(imageView, HPos.CENTER);

		form.getChildren().addAll(playerLabel, grid);
		
		return form;
	}

	private void openRules() {
		WindowRule secondWindow = new WindowRule();
		secondWindow.show();
	}

	public String getNamePlayer1() {
		return textFieldPlayer1.getText();
	}

	public String getNamePlayer2() {
		return textFieldPlayer2.getText();
	}

	public Pane currentRoot() {
		return this.currentRoot;
	}

	public Button playButton() {
		return this.playButton;
	}

	public Date getBirthDatePlayer1() {
		LocalDate localDate = datePickerLeft.getValue();
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public Date getBirthDatePlayer2() {
		LocalDate localDate = datePickerRight.getValue();
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}
