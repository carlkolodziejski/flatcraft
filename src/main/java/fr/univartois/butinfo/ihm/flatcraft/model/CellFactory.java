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

import java.util.Random;

/**
 * La classe {@link CellFactory} permet de créer les cellules formant la carte du jeu Flatcraft.
 *
 * @author Daniel Le Berre
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public final class CellFactory {

    /**
     * Le générateur de nombres pseudo-aléatoires à utiliser pour créer des cellules aléatoires.
     */
    private static final Random RANDOM = new Random();

    /**
     * Le {@link SpriteStore} à utiliser pour récupérer les sprites des cellules.
     */
    private final SpriteStore spriteStore;

    /**
     * Crée une nouvelle instance de fr.univartois.butinfo.ihm.flatcraft.model.CellFactory.
     *
     * @param spriteStore Le {@link SpriteStore} à utiliser pour récupérer les sprites des cellules.
     */
    public CellFactory(SpriteStore spriteStore) {
        this.spriteStore = spriteStore;
    }

    /**
     * Crée une cellule de ciel.
     * Il peut s'agir de ciel bleu ou d'un nuage, par exemple.
     *
     * @return La cellule créée.
     */
    public Cell createSky() {
        if (RANDOM.nextInt(10) < 1) {
            return createCell("cloud");
        }
        return createCell("ice");
    }

    /**
     * Crée une cellule représentant la surface du sol.
     * Il peut s'agir de pelouse ou d'eau, par exemple.
     *
     * @return La cellule créée.
     */
    public Cell createSoilSurface() {
        if (RANDOM.nextInt(10) < 1) {
            return createResourceCell("junglegrass");
        }

        if (RANDOM.nextInt(10) < 2) {
            return createResourceCell("water");
        }

        return createResourceCell("grass");
    }

    /**
     * Crée une cellule représentant le sous-sol.
     * Il peut s'agir de terre, ou de minerai à aller chercher en profondeur, par exemple.
     *
     * @return La cellule créée.
     */
    public Cell createSubSoil() {
        return switch (RANDOM.nextInt(20)) {
            case 0 -> createResourceCell("gold_block");
            case 1 -> createResourceCell("diamond_block");
            case 2 -> createResourceCell("bronze_block");
            case 3 -> createResourceCell("coal_block");
            case 4 -> createResourceCell("copper_block");
            default -> createResourceCell("dirt");
        };
    }

    /**
     * Crée une cellule représentant le tronc d'un arbre.
     *
     * @return La cellule créée.
     */
    public Cell createTrunk() {
        return createCell("tree");
    }

    /**
     * Crée une cellule représentant les feuilles d'un arbre.
     *
     * @return La cellule créée.
     */
    public Cell createLeaves() {
        return createCell("leaves");
    }

    /**
     * Crée une cellule affichant le sprite ayant le nom donné.
     * La cellule créée est vide (elle ne contient pas de ressource).
     *
     * @param name Le nom du sprite affiché par la cellule à créer.
     *
     * @return La cellule créée.
     */
    private Cell createCell(String name) {
        Image sprite = spriteStore.createSprite(name);
        return new Cell(sprite);
    }

    /**
     * Crée une cellule contenant la ressource ayant le nom donné.
     *
     * @param name Le nom de la ressource contenue dans la cellule à créer.
     *
     * @return La cellule créée.
     */
    private Cell createResourceCell(String name) {
        Image sprite = spriteStore.createSprite(name);
        Resource resource = new Resource(name, sprite);
        return new Cell(resource);
    }

}
