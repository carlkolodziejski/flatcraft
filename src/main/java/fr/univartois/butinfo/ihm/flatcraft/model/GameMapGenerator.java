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

import java.util.Random;

/**
 * La classe {@link GameMapGenerator} permet de générer différentes cartes du jeu Flatcraft.
 * La carte de base comporte une plaine ne comportant que de l'herbe et des plans d'eau en surface.
 * Cette carte peut aussi être enrichie avec des arbres, des terrils, etc.
 *
 * @author Daniel Le Berre
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public final class GameMapGenerator {

    /**
     * Le générateur de nombres pseudo-aléatoires utilisé pour placer les arbres et les terrils.
     */
    private static final Random RANDOM = new Random();

    /**
     * La hauteur maximale des arbres.
     */
    private static final int MAX_TREE_HEIGHT = 5;

    /**
     * La hauteur maximale des terrils.
     */
    private static final int MAX_SLAG_HEAP_HEIGHT = 8;

    /**
     * Désactive l'instanciation en dehors de la classe.
     */
    private GameMapGenerator() {
        throw new AssertionError("No fr.univartois.butinfo.ihm.flatcraft.model.GameMapGenerator instances for you!");
    }

    /**
     * Génère une carte de base pour le jeu Flatcraft.
     * La carte est une plaine ne comportant que de l'herbe et des plans d'eau en surface.
     *
     * @param height La hauteur de la carte.
     * @param width La largeur de la carte.
     * @param factory La fabrique de cellules utilisée pour créer les cellules de la carte.
     *
     * @return La carte générée.
     */
    public static GameMap generatePlainMap(int height, int width, CellFactory factory) {
        GameMap map = new GameMap(height, width, 2 * height / 3);

        // La première partie de la carte représente le ciel.
        for (int i = 0; i < map.getSoilHeight(); i++) {
            for (int j = 0; j < width; j++) {
                map.setAt(i, j, factory.createSky());
            }
        }

        // Une ligne permet de représenter la surface du sol.
        for (int j = 0; j < width; j++) {
            map.setAt(map.getSoilHeight(), j, factory.createSoilSurface());
        }

        // La dernière partie de la carte représente le sous-sol.
        for (int i = map.getSoilHeight() + 1; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map.setAt(i, j, factory.createSubSoil());
            }
        }

        return map;
    }

    /**
     * Génère une carte pour le jeu Flatcraft.
     * La carte comporte une plaine avec en plus des arbres.
     *
     * @param height La hauteur de la carte.
     * @param width La largeur de la carte.
     * @param factory La fabrique de cellules utilisée pour créer les cellules de la carte.
     * @param nbTrees Le nombre d'arbres à ajouter à la carte.
     *
     * @return La carte générée.
     */
    public static GameMap generateMapWithTrees(int height, int width, CellFactory factory, int nbTrees) {
        return generateMapWithTreesAndSlagHeaps(height, width, factory, nbTrees, 0);
    }

    /**
     * Génère une carte pour le jeu Flatcraft.
     * La carte comporte une plaine avec en plus des terrils.
     *
     * @param height La hauteur de la carte.
     * @param width La largeur de la carte.
     * @param factory La fabrique de cellules utilisée pour créer les cellules de la carte.
     * @param nbSlagHeaps Le nombre de terrils à ajouter à la carte.
     *
     * @return La carte générée.
     */
    public static GameMap generateMapWithSlagHeaps(int height, int width, CellFactory factory, int nbSlagHeaps) {
        return generateMapWithTreesAndSlagHeaps(height, width, factory, 0, nbSlagHeaps);
    }

    /**
     * Génère une carte pour le jeu Flatcraft.
     * La carte comporte une plaine avec en plus des arbres et des terrils.
     *
     * @param height La hauteur de la carte.
     * @param width La largeur de la carte.
     * @param factory La fabrique de cellules utilisée pour créer les cellules de la carte.
     * @param nbTrees Le nombre d'arbres à ajouter à la carte.
     * @param nbSlagHeaps Le nombre de terrils à ajouter à la carte.
     *
     * @return La carte générée.
     */
    public static GameMap generateMapWithTreesAndSlagHeaps(int height, int width,
                                                           CellFactory factory, int nbTrees, int nbSlagHeaps) {
        GameMap map = generatePlainMap(height, width, factory);

        for (int i = 0; i < nbTrees; i++) {
            addTree(map, factory);
        }

        for (int i = 0; i < nbSlagHeaps; i++) {
            addSlagHeap(map, factory);
        }

        return map;
    }


    /**
     * Ajoute un arbre à une position aléatoire sur la carte.
     *
     * @param map La carte à laquelle ajouter l'arbre.
     * @param factory La fabrique de cellules utilisée pour créer les cellules d'arbre.
     */
    private static void addTree(GameMap map, CellFactory factory) {
        // On choisit l'endroit où placer l'arbre.
        int treeHeight = RANDOM.nextInt(MAX_TREE_HEIGHT) + 1;
        int col = RANDOM.nextInt(map.getWidth() - 2) + 1;
        int row = map.getSoilHeight();

        // On commence par placer le tronc.
        for (int i = 0; i < treeHeight; i++) {
            map.setAt(row, col, factory.createTrunk());
            row--;
        }

        // On ajoute ensuite les feuilles.
        map.setAt(row, col + 1, factory.createLeaves());
        map.setAt(row, col, factory.createLeaves());
        map.setAt(row, col - 1, factory.createLeaves());
        map.setAt(row + 1, col + 1, factory.createLeaves());
        map.setAt(row + 1, col - 1, factory.createLeaves());
    }

    /**
     * Ajoute un terril à une position aléatoire sur la carte.
     *
     * @param map La carte à laquelle ajouter le terril.
     * @param factory La fabrique de cellules utilisée pour créer les cellules de terril.
     */
    private static void addSlagHeap(GameMap map, CellFactory factory) {
        // On choisit l'endroit où placer le terril.
        int heapHeight = RANDOM.nextInt(MAX_SLAG_HEAP_HEIGHT) + 1;
        int x = RANDOM.nextInt(map.getWidth() - heapHeight) + heapHeight;
        int y = map.getSoilHeight();

        // On place les blocs constituant le terril, en partant de son sommet.
        for (int h = 0; h < heapHeight; h++) {
            for (int w = 0; w < (2 * h + 1); w++) {
                int row = y - heapHeight + h;
                int column = x + w;
                if ((column < map.getWidth()) && (0 <= row) && (row < map.getHeight())) {
                    map.setAt(row, column, factory.createSubSoil());
                }
            }
            x--;
        }
    }

}
