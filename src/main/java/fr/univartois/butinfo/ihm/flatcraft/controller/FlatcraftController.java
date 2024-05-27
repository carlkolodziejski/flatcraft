package fr.univartois.butinfo.ihm.flatcraft.controller;

import fr.univartois.butinfo.ihm.flatcraft.model.AbstractMovable;
import fr.univartois.butinfo.ihm.flatcraft.model.FlatcraftGame;
import fr.univartois.butinfo.ihm.flatcraft.model.GameMap;
import fr.univartois.butinfo.ihm.flatcraft.model.IFlatcraftController;
import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class FlatcraftController implements IFlatcraftController {

    private static final int LARGEUR = 1280;
    private static final int HAUTEUR = 720;
    private static final int TAILLE_CELLULE = 32;

    private ImageView[][] cellulesBackground;
    private ImageView[][] cellulesMainPane;

    private Stage stage;

    private FlatcraftGame jeu;

    @FXML
    private GridPane background;

    @FXML
    private ProgressBar barreDeVie;

    @FXML
    private GridPane mainPane;

    @FXML
    void initialize() {
        int nombreCellulesHauteur = HAUTEUR / TAILLE_CELLULE;
        int nombreCellulesLargeur = LARGEUR / TAILLE_CELLULE;
        cellulesBackground = new ImageView[nombreCellulesHauteur][nombreCellulesLargeur];
        cellulesMainPane = new ImageView[nombreCellulesHauteur][nombreCellulesLargeur];

        for (int i = 0; i < nombreCellulesHauteur; i++) {
            for (int j = 0; j < nombreCellulesLargeur; j++) {
                cellulesBackground[i][j] = new ImageView();
                cellulesBackground[i][j].setFitHeight(TAILLE_CELLULE);
                cellulesBackground[i][j].setFitWidth(TAILLE_CELLULE);
                background.add(cellulesBackground[i][j], j, i);

                cellulesMainPane[i][j] = new ImageView();
                cellulesMainPane[i][j].setFitHeight(TAILLE_CELLULE);
                cellulesMainPane[i][j].setFitWidth(TAILLE_CELLULE);
                mainPane.add(cellulesMainPane[i][j], j, i);
            }
        }

        barreDeVie.setProgress(1);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void onEtabliButtonClick(ActionEvent event) {
        // TODO afficher l'établi
    }

    @FXML
    void onFourneauButtonClick(ActionEvent event) {
        // TODO afficher le fourneau
    }

    @FXML
    void onInventaireButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/inventaire-view.fxml"));
        Parent viewContent = fxmlLoader.load();

        InventaireControleur controleurInventaire = fxmlLoader.getController();
        controleurInventaire.setStage(stage);
        controleurInventaire.setGameScene(this.stage.getScene());
        controleurInventaire.getInventaire(jeu.getJoueur().getInventaire());


        Scene scene = new Scene(viewContent);
        stage.setScene(scene);
    }

    @Override
    public void setJeu(FlatcraftGame jeu) {
        this.jeu = jeu;
    }

    @Override
    public void initGame(GameMap map) {
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                cellulesBackground[i][j].imageProperty().bind(map.getAt(i, j).getSpriteProperty());
            }
        }
        stage.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            KeyCode code = e.getCode();
            if (code == KeyCode.LEFT) {
                if (e.isAltDown()) jeu.digLeft();
                else jeu.moveLeft();
            } else if (code == KeyCode.RIGHT) {
                if (e.isAltDown()) jeu.digRight();
                else jeu.moveRight();
            } else if (code == KeyCode.DOWN) {
                jeu.digDown();
            }
        });
    }

    @Override
    public void afficherMovable(AbstractMovable movable) {
        cellulesMainPane[movable.getRow()][movable.getColumn()].setImage(movable.getSprite());
    }

    @Override
    public void masquerMovable(AbstractMovable movable) {
        cellulesMainPane[movable.getRow()][movable.getColumn()].setImage(null);
    }

    @Override
    public void setHealthProperty(IntegerProperty healthProperty) {
        barreDeVie.progressProperty().bind(healthProperty);
    }
}
