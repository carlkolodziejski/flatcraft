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

import javafx.scene.image.Image;

import java.util.Objects;

/**
 * Une ressource est un élément de la carte avec lequel le joueur peut interagir.
 * Il peut soit l'extraire, soit la laisser sur place.
 *
 * @author Daniel Le Berre
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public final class Resource {

    /**
     * Le nom unique identifiant le type de cette ressource.
     */
    private final String name;

    /**
     * L'image représentant cette ressource.
     */
    private final Image sprite;

    /**
     * Crée une nouvelle instance de fr.univartois.butinfo.ihm.flatcraft.model.Resource.
     *
     * @param name Le nom unique identifiant le type de cette ressource.
     * @param sprite L'image représentant cette ressource.
     */
    public Resource(String name, Image sprite) {
        this.name = name;
        this.sprite = sprite;
    }

    /**
     * Donne le nom unique identifiant le type de cette ressource.
     *
     * @return Le nom unique identifiant le type de cette ressource.
     */
    public String getName() {
        return name;
    }

    /**
     * Donne l'image représentant cette ressource.
     *
     * @return L'image représentant cette ressource.
     */
    public Image getSprite() {
        return sprite;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Resource resource) {
            return name.equals(resource.name);
        }
        return false;
    }

}
