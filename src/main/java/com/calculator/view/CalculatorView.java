package com.calculator.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class CalculatorView extends VBox {

    // Composants de l'interface
    private final TextField operationInput;
    private final GridPane buttonsGrid;
    private final ObservableList<HBox> historyList;

    // Configuration des boutons
    private static final String[] BUTTON_LABELS = {
            "mod", "(", ")", "DEL", "AC",
            "7", "8", "9", "÷", "√",
            "4", "5", "6", "×", "X²",
            "1", "2", "3", "-", "=",
            "0", ".", "%", "+"
    };

    private static final int BUTTON_COLUMNS = 5;
    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 50;

    public CalculatorView() {
        this.operationInput = new TextField();
        this.buttonsGrid = new GridPane();
        this.historyList = FXCollections.observableArrayList();

        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        setupOperationInput();
        setupButtonsGrid();
    }

    private void setupOperationInput() {
        operationInput.setEditable(true);
        operationInput.setFocusTraversable(true);

        // Focus automatique au démarrage
        Platform.runLater(() -> operationInput.requestFocus());

        // Formatage automatique de l'affichage
        operationInput.textProperty().addListener((obs, oldText, newText) -> {
            formatDisplayText(newText);
            // Placer le curseur à la fin
            Platform.runLater(() ->
                    operationInput.positionCaret(operationInput.getText().length())
            );
        });
    }

    private void formatDisplayText(String text) {
        if (text == null) return;

        String formatted = text
                .replace(" ", "")           // Enlever tous les espaces d'abord
                .replace("*", " × ")
                .replace("/", " ÷ ")
                .replace("mod", " mod ")
                .replace("+", " + ")
                .replace("-", " - ")
                .replace("%", " % ")
                .replace("×", " × ")
                .replace("÷", " ÷ ")
                .replace("=", " = ");

        // Éviter la boucle infinie en vérifiant si le texte a changé
        if (!formatted.equals(operationInput.getText())) {
            operationInput.setText(formatted);
        }
    }

    private void setupButtonsGrid() {
        buttonsGrid.setHgap(5);
        buttonsGrid.setVgap(5);

        for (int i = 0; i < BUTTON_LABELS.length; i++) {
            Button button = createButton(BUTTON_LABELS[i], i);
            addButtonToGrid(button, BUTTON_LABELS[i], i);
        }
    }

    private Button createButton(String label, int index) {
        Button button = new Button(label);
        button.setFocusTraversable(false);
        GridPane.setHgrow(button, Priority.ALWAYS);
        GridPane.setVgrow(button, Priority.ALWAYS);

        // Style spécial pour certains boutons
        if (isSpecialButton(label)) {
            button.getStyleClass().add("special-button");
        }

        return button;
    }

    private boolean isSpecialButton(String label) {
        return label.equals("=") || label.equals("DEL") || label.equals("AC");
    }

    private void addButtonToGrid(Button button, String label, int index) {
        int col = index % BUTTON_COLUMNS;
        int row = index / BUTTON_COLUMNS;

        if (label.equals("=")) {
            // Le bouton = prend 2 lignes
            buttonsGrid.add(button, col, row, 1, 2);
            button.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT * 2);
            button.setDefaultButton(true);  // Activé par la touche Entrée
        } else {
            buttonsGrid.add(button, col, row);
            button.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        }
    }

    private void setupLayout() {
        // Liste de l'historique
        ListView<HBox> historyListView = new ListView<>(historyList);
        historyListView.setFocusTraversable(false);

        // En-tête (historique + input)
        VBox header = new VBox(historyListView, operationInput);
        VBox.setVgrow(historyListView, Priority.ALWAYS);
        header.getStyleClass().add("header");

        // Conteneur principal
        VBox container = new VBox(15, header, buttonsGrid);
        container.setMaxSize(600, Double.MAX_VALUE);
        container.setPadding(new Insets(10, 0, 10, 0));
        VBox.setVgrow(header, Priority.ALWAYS);
        VBox.setVgrow(container, Priority.ALWAYS);

        this.getChildren().add(container);
        this.setAlignment(Pos.CENTER);
    }

    public void setOnButtonClick(EventHandler<ActionEvent> handler) {
        buttonsGrid.getChildren().stream()
                .filter(node -> node instanceof Button)
                .map(node -> (Button) node)
                .forEach(button -> button.setOnAction(handler));
    }

    public void setDisplayText(String text) {
        operationInput.setText(text);
    }

    public String getDisplayText() {
        return operationInput.getText();
    }

    public void addHistory(String expression, String result) {
        Label expressionLabel = new Label(expression);
        expressionLabel.setPrefWidth(300);
        expressionLabel.setTextFill(Color.WHITE);

        Label equalLabel = new Label(" = ");
        equalLabel.setTextFill(Color.WHITE);

        Label resultLabel = new Label(result);
        resultLabel.setPrefWidth(200);
        resultLabel.setTextFill(Color.WHITE);
        resultLabel.setAlignment(Pos.CENTER_RIGHT);

        HBox historyLine = new HBox(20, expressionLabel, equalLabel, resultLabel);
        historyList.add(0, historyLine);
    }
}
