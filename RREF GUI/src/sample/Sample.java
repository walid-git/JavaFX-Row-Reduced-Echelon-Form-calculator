package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class Sample {

    private int numCols=2;
    private int numRows=2;

    @FXML
    private VBox vBox;

    @FXML
    private AnchorPane anchor;

    @FXML
    private Button rrefButton;

    @FXML
    private TextField dimension_n;

    @FXML
    private TextField dimension_m;

    @FXML
    private GridPane matriceGridPane;

    @FXML
    private CheckMenuItem showSteps;

    @FXML
    private void initialize() {
        System.out.println("Initializing...");
        showSteps.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                RREF.showSteps = newValue;
            }
        });
        matriceGridPane.getChildren().clear();
        matriceGridPane.getColumnConstraints().clear();
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints(80,80,80);
            matriceGridPane.getColumnConstraints().add(colConst);
        }
        matriceGridPane.getRowConstraints().clear();
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints(60,60,60);
            matriceGridPane.getRowConstraints().add(rowConst);
        }
        for(int i=0; i<numRows ; i++) {
            for (int j = 0; j < numCols; j++) {
                TextField textField = new TextField();
                textField.alignmentProperty().setValue(Pos.CENTER);
                matriceGridPane.add(textField, j, i);
                matriceGridPane.setMargin(textField, new Insets(1, 10, 1, 10));
            }
        }
    }

    @FXML
    void onDimensionChanged(ActionEvent event) {
        try {
            numCols = Integer.parseInt(dimension_m.getText());
            numRows = Integer.parseInt(dimension_n.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a correct value.", ButtonType.OK);
            alert.show();
            return;
        }
        matriceGridPane.getChildren().clear();
        matriceGridPane.getColumnConstraints().clear();
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints(80,80,80);
            matriceGridPane.getColumnConstraints().add(colConst);
        }
        matriceGridPane.getRowConstraints().clear();
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints(60,60,60);
            matriceGridPane.getRowConstraints().add(rowConst);
        }
        for(int i=0; i<numRows ; i++) {
            for (int j = 0; j < numCols; j++) {
                TextField textField = new TextField();
                textField.alignmentProperty().setValue(Pos.CENTER);
                matriceGridPane.add(textField, j, i);
                matriceGridPane.setMargin(textField, new Insets(1, 10, 1, 10));
            }
        }
    }

    @FXML
    void onCalculate(ActionEvent event) {
        vBox.getChildren().remove(2,vBox.getChildren().size());

        double[][] matrice = new double[numRows][numCols];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                try {
                    matrice[i][j] = Double.parseDouble(((TextField) matriceGridPane.getChildren().get(numCols * i + j)).getText());
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a correct value.", ButtonType.OK);
                    alert.show();
                    return;
                }

            }
        }
        RREF.pane = vBox;
        RREF.rref(matrice);
        if(!RREF.showSteps)
            RREF.dispMat(matrice,"RREF :");
        int rank = RREF.getRank(matrice);
        Label rankNullityLabel = new Label("Rank = "+rank+" Nullity = "+(matrice[0].length-rank));
        rankNullityLabel.setAlignment(Pos.CENTER);
        rankNullityLabel.setFont(Font.font(null, FontWeight.BOLD,18));
        rankNullityLabel.setPadding(new Insets(0,20,0,20));
        rankNullityLabel.setPrefWidth(vBox.getWidth());
        rankNullityLabel.setPrefHeight(200);
        rankNullityLabel.setWrapText(true);
        vBox.getChildren().add(rankNullityLabel);

    }

    @FXML
    private void exit() {
        Main.exit();
    }

    @FXML
    private void clearMatrix() {
        for (int i = 0; i < matriceGridPane.getChildren().size(); i++) {
            ((TextField)matriceGridPane.getChildren().get(i)).setText("");
        }
    }

    @FXML
    private void aboutDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Developed by Walid BOUDEBOUDA\nEmail: walid.boudebouda@gmail.com\nGithub : ",ButtonType.OK);
        alert.setTitle("about");
        alert.setHeaderText("");
        alert.show();
    }

}
