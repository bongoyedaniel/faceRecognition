package com.bongoye.faceRecognition.ui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.bongoye.faceRecognition.faceRecognizer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class mainController implements Initializable {
    @FXML
    private AnchorPane f;
    @FXML
    private VBox v;
    @FXML
    private ImageView recognizedImage, selectedImage;
    @FXML
    private MenuButton fOption;

    private String selectedPath = faceRecognizer.pathToProject + "/input.jpg";
    private String selectedCascade = "haarcascade_frontalface_alt.xml";

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        selectedImage.setImage(new Image(new File(selectedPath).toURI().toString()));
        updateImage();

        File haarcascades = new File(faceRecognizer.pathToProject + "/haarcascades");
        fOption.getItems().clear();

        for (File option : haarcascades.listFiles()) {
            MenuItem m = new MenuItem(option.getName().replace("_", " ").split(".xml")[0]);

            m.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    selectedCascade = option.getName();
                    fOption.setText(option.getName().replace("_", " ").split(".xml")[0]);
                    updateImage();
                }
            });

            fOption.getItems().add(m);
        }
    }

    void updateImage() {
        new faceRecognizer(selectedPath, selectedCascade);
        recognizedImage.setImage(new Image(new File("output.jpg").toURI().toString()));
    }

    public void openImage() {

        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter[] ef = {
                new FileChooser.ExtensionFilter("*", "*.jpg"),
                new FileChooser.ExtensionFilter("*", "*.png"),
                new FileChooser.ExtensionFilter("*", "*.jpeg")
        };
        fc.getExtensionFilters().addAll(ef);
        Stage c = new Stage();

        String s = fc.showOpenDialog(c).toString();
        if (s != null) {
            selectedPath = s;
            selectedImage.setImage(new Image(new File(selectedPath).toURI().toString()));
            updateImage();
        }
    }

    public void dragOver(DragEvent ev) {
        Dragboard b = ev.getDragboard();
        if (b.hasFiles())
            ev.acceptTransferModes(TransferMode.COPY);
        ev.consume();
    }

    public void drop(DragEvent ev) {
        Dragboard b = ev.getDragboard();
        if (b.hasFiles()) {
            for (File f : b.getFiles()) {
                selectedPath = f.getAbsolutePath();
                selectedImage.setImage(new Image(f.toURI().toString()));
            }
            updateImage();
            ev.setDropCompleted(true);
        } else
            ev.setDropCompleted(false);

        ev.consume();
    }
}
