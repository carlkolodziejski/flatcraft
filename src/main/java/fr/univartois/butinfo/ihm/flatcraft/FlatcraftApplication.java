/**
 * Ce logiciel est distribué à des fins éducatives.
 * <p>
 * Il est fourni "tel quel", sans garantie d’aucune sorte, explicite
 * ou implicite, notamment sans garantie de qualité marchande, d’adéquation
 * à un usage particulier et d’absence de contrefaçon.
 * En aucun cas, les auteurs ou titulaires du droit d’auteur ne seront
 * responsables de tout dommage, réclamation ou autre responsabilité, que ce
 * soit dans le cadre d’un contrat, d’un délit ou autre, en provenance de,
 * consécutif à ou en relation avec le logiciel ou son utilisation, ou avec
 * d’autres éléments du logiciel.
 * <p>
 * (c) 2022-2023 Romain Wallon - Université d'Artois.
 * Tous droits réservés.
 */

package fr.univartois.butinfo.ihm.flatcraft;

import fr.univartois.butinfo.ihm.flatcraft.controller.FlatcraftController;
import fr.univartois.butinfo.ihm.flatcraft.model.FlatcraftGame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * La classe HelloApplication illustre le fonctionnement d'une {@link Application} JavaFX.
 *
 * @author Romain Wallon
 * @version 0.1.0
 */
public class FlatcraftApplication extends Application {

    /**
     * Cette méthode exécute l'application JavaFX.
     * Pour le cours d'IHM, la méthode {@code main} d'une application JavaFX sera
     * toujours la même : un simple appel à la méthode {@link #launch(String...)}
     * définie dans la classe {@link Application}.
     *
     * @param args Les arguments de la ligne de commande (dont on ne tient pas compte).
     * @see #launch(String...)
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Cette méthode permet d'initialiser l'affichage de la fenêtre de l'application.
     *
     * @param stage La fenêtre (initialement vide) de l'application.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Il faut d'abord récupérer la description de la vue (au format FXML).
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./view/flatcraft-view.fxml"));
        Parent viewContent = fxmlLoader.load();

        FlatcraftController controleur = fxmlLoader.getController();
        controleur.setStage(stage);

        // Ensuite, on la place dans une Scene...
        Scene scene = new Scene(viewContent, 1280, 720);
        // que l'on place elle-même dans la fenêtre.
        stage.setScene(scene);

        FlatcraftGame jeu = new FlatcraftGame(1280 / 32, 720 / 32);
        jeu.setControleur(controleur);
        controleur.setJeu(jeu);
        jeu.prepare();

        // On peut ensuite donner un titre à la fenêtre.
        stage.setTitle("Flatcraft");

        // Enfin, on affiche la fenêtre.
        stage.show();
    }

}
