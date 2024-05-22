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

/**
 * La classe {@link GameMap} représente une carte de jeu pour Flatcraft.
 * Elle est composée de cellules regroupées dans un tableau à deux dimensions.
 *
 * @author Daniel Le Berre
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public final class GameMap {

    /**
     * La hauteur de cette carte, en nombre de cellules.
     */
    private final int height;
    /**
     * La largeur de cette carte, en nombre de cellules.
     */
    private final int width;
    /**
     * La hauteur à laquelle se situe la surface du sol.
     */
    private final int soilHeight;
    /**
     * Les cellules composant cette carte.
     */
    private final Cell[][] cells;

    /**
     * Crée une nouvelle instance de fr.univartois.butinfo.ihm.flatcraft.model.GameMap.
     *
     * @param height La hauteur de la carte, en nombre de cellules.
     * @param width La largeur de la carte, en nombre de cellules.
     * @param soilHeight La hauteur à laquelle se situe la surface du sol.
     */
    public GameMap(int height, int width, int soilHeight) {
        if ((width <= 0) || (height <= 0)) {
            throw new IllegalArgumentException("Incorrect map dimension!");
        }

        this.cells = new Cell[height][width];
        this.height = height;
        this.width = width;
        this.soilHeight = soilHeight;
        init();
    }

    /**
     * Initialise cette carte en la remplissant de cellules initialement vides.
     */
    private void init() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    /**
     * Donne la hauteur de cette carte, mesurée en nombre de cellules.
     *
     * @return La hauteur de cette carte.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Donne la largeur de cette carte, mesurée en nombre de cellules.
     *
     * @return La largeur de cette carte.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Donne la hauteur à laquelle se situe la surface du sol dans cette map, mesurée en
     * nombre de cellules.
     *
     * @return La hauteur du sol.
     */
    public int getSoilHeight() {
        return soilHeight;
    }

    /**
     * Donne la cellule à la position donnée sur cette carte.
     *
     * @param row La ligne de la cellule à récupérer.
     * @param column La colonne de la cellule à récupérer.
     *
     * @return La cellule à la position donnée.
     *
     * @throws IllegalArgumentException Si la position donnée est en dehors de cette
     *         carte.
     */
    public Cell getAt(int row, int column) {
        if ((row < 0) || (height <= row) || (column < 0) || (width <= column)) {
            throw new IllegalArgumentException("Incorrect cell location!");
        }
        return cells[row][column];
    }

    /**
     * Modifie la cellule à la position donnée sur cette carte.
     *
     * @param row La ligne de la cellule à modifier.
     * @param column La colonne de la cellule à modifier.
     * @param cell La nouvelle cellule à placer.
     *
     * @throws IllegalArgumentException Si la position donnée est en dehors de cette
     *         carte.
     */
    public void setAt(int row, int column, Cell cell) {
        if ((row < 0) || (height <= row) || (column < 0) || (width <= column)) {
            throw new IllegalArgumentException("Incorrect cell location!");
        }
        cells[row][column].replaceBy(cell);
    }

}
