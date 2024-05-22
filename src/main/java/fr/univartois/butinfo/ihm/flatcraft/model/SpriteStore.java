package fr.univartois.butinfo.ihm.flatcraft.model;
/*
 * Ce logiciel est distribué à des fins éducatives.
 *
 * Il est fourni "tel quel", sans garantie d’aucune sorte, explicite
 * ou implicite, notamment sans garantie de qualité marchande, d’adéquation
 * à un usage particulier et d’absence de contrefaçon.
 * En aucun cas, les auteurs ou titulaires du droit d’auteur ne seront
 * responsables de tout dommage, réclamation ou autre responsabilité, que ce
 * soit dans le cadre d’un contrat, d’un délit ou autre, en provenance de,
 * consécutif à ou en relation avec le logiciel ou son utilisation, ou avec
 * d’autres éléments du logiciel.
 *
 * (c) 2023-2024 Romain Wallon - Université d'Artois.
 * Tous droits réservés.
 */

import javafx.scene.image.Image;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * La classe {@link SpriteStore} permet de charger les différentes images utilisées
 * pour représenter les éléments du jeu une et une seule fois pendant l'exécution du
 * programme.
 *
 * @author Romain Wallon
 * @version 0.1.0
 */
public final class SpriteStore {

    /**
     * La {@link Map} permettant de conserver en cache les différentes images déjà
     * chargées.
     */
    private final Map<String, Image> spriteCache = new HashMap<>();

    /**
     * Charge l'image d'un sprite donné par son identifiant.
     *
     * @param identifier L'identifiant du sprite à charger.
     * @return L'image chargée.
     * @throws NoSuchElementException Si aucun sprite ne correspond à l'identifiant donné.
     */
    public Image createSprite(String identifier) {
        // On commence par regarder si l'image a déjà été chargée.
        Image cached = spriteCache.get(identifier);
        if (cached != null) {
            return cached;
        }

        // On charge l'image, et on la met en cache.
        Image image = loadImage(identifier);
        spriteCache.put(identifier, image);
        return image;
    }

    /**
     * Donne la taille des images à charger.
     *
     * @return La taille des images (en pixels).
     */
    public int getSpriteSize() {
        return 32;
    }

    /**
     * Charge une image donnée par son nom.
     *
     * @param name Le nom de l'image à charger.
     * @return L'image ayant le nom donné.
     * @throws NoSuchElementException S'il n'existe pas d'image ayant le nom donné.
     */
    private Image loadImage(String name) {
        try {
            URL urlImage = getClass().getResource("../view/images/default_" + name + ".png");
            if (urlImage == null) urlImage = getClass().getResource("../view/images/gui_" + name + ".png");
            if (urlImage == null) urlImage = getClass().getResource("../view/images/" + name + ".png");
            assert urlImage != null;
            return new Image(urlImage.toExternalForm(), getSpriteSize(), getSpriteSize(), true, true);

        } catch (NullPointerException | IllegalArgumentException e) {
            throw new NoSuchElementException("Could not load image " + name, e);
        }
    }

}
