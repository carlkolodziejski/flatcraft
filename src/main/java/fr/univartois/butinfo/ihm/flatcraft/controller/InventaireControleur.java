package fr.univartois.butinfo.ihm.flatcraft.controller;

import fr.univartois.butinfo.ihm.flatcraft.model.Resource;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class InventaireControleur {

    private Stage stage;

    /**
     * La scène de la map.
     */
    private Scene gameScene;

    @FXML
    private ImageView imageItem;

    @FXML
    private Label descriptionItem;

    @FXML
    private ListView<Resource> listItems;

    @FXML
    private Label nomItem;

    public void setGameScene(Scene scene) {
        this.gameScene = scene;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void onRetourButtonClick(ActionEvent event) {
        stage.setScene(gameScene);
    }

    /**
     * Fait le lien entre l'inventaire du joueur ainsi que la liste présente dans la vue.
     *
     * @param inventaire l'inventaire du joueur concerné.
     */
    public void getInventaire(ObservableList<Resource> inventaire) {
        listItems.setItems(inventaire);
    }

    @FXML
    void initialize() {
        listItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, itemChoisi) -> {
            if (itemChoisi != null) {
                nomItem.setText(itemChoisi.getName());
                descriptionItem.setText(itemChoisi.getName());
                imageItem.setImage(itemChoisi.getSprite());
            }
        });
    }


}
