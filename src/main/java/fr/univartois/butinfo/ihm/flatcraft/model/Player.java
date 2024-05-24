package fr.univartois.butinfo.ihm.flatcraft.model; /**
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
 * (c) 2023-2024 Romain Wallon - Université d'Artois.
 * Tous droits réservés.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.util.NoSuchElementException;

/**
 * La classe fr.univartois.butinfo.ihm.flatcraft.model.Player représente le personnage du joueur qui se déplace sur la carte du jeu.
 *
 * @author Romain Wallon
 * @version 0.1.0
 */
public final class Player extends AbstractMovable {

    /**
     * Attribut correspondant à l'inventaire du joueur.
     */
    private final ObservableList<Resource> inventaire;

    /**
     * Crée une nouvelle instance de fr.univartois.butinfo.ihm.flatcraft.model.Player.
     *
     * @param game   Le jeu dans lequel le joueur évolue.
     * @param sprite Le sprite représentant le joueur.
     */
    public Player(FlatcraftGame game, Image sprite) {
        super(game, sprite, 3);
        this.inventaire = FXCollections.observableArrayList();
    }

    /**
     * Ajoute un objet à l'inventaire de ce joueur.
     *
     * @param resource L'objet à ajouter.
     */
    public void addToInventory(Resource resource) {
        inventaire.add(resource);
    }

    /**
     * Retire un objet de l'inventaire de ce joueur.
     *
     * @param resource L'objet à retirer.
     * @throws NoSuchElementException Si l'objet n'est pas présent dans l'inventaire.
     */
    public void removeFromInventory(Resource resource) {
        inventaire.remove(resource);
    }

    public ObservableList<Resource> getInventaire() {
        return inventaire;
    }
}
