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

import java.util.Objects;

/**
 * La classe {@link FlatcraftGame} permet de gérer une partie du jeu Flatcraft.
 *
 * @author Romain Wallon
 * @version 0.1.0
 */
public final class FlatcraftGame {

    public static final String LADDER = "ladder";
    /**
     * La largeur de la carte du jeu affichée (en pixels).
     */
    private final int width;

    /**
     * La hauteur de la carte du jeu affichée (en pixels).
     */
    private final int height;

    /**
     * L'instance e {@link SpriteStore} utilisée pour créer les sprites du jeu.
     */
    private final SpriteStore spriteStore;

    /**
     * L'instance de {@link CellFactory} utilisée pour créer les cellules du jeu.
     */
    private final CellFactory cellFactory;

    /**
     * La carte du jeu, sur laquelle le joueur évolue.
     */
    private GameMap map;
    /**
     * La représentation du joueur.
     */
    private Player joueur;
    private IFlatcraftController controleur;

    /**
     * Crée une nouvelle instance de fr.univartois.butinfo.ihm.flatcraft.model.FlatcraftGame.
     *
     * @param width  La largeur de la carte du jeu (en pixels).
     * @param height La hauteur de la carte du jeu (en pixels).
     */
    public FlatcraftGame(int width, int height) {
        this.width = width;
        this.height = height;
        this.spriteStore = new SpriteStore();
        this.cellFactory = new CellFactory(spriteStore);
    }

    public Player getJoueur() {
        return joueur;
    }

    /**
     * Donne la largeur de la carte du jeu affichée (en pixels).
     *
     * @return La largeur de la carte du jeu affichée (en pixels).
     */
    public int getWidth() {
        return width;
    }

    /**
     * Donne la hauteur de la carte du jeu affichée (en pixels).
     *
     * @return La hauteur de la carte du jeu affichée (en pixels).
     */
    public int getHeight() {
        return height;
    }

    /**
     * Prépare la partie de Flatcraft avant qu'elle ne démarre.
     */
    public void prepare() {
        map = GameMapGenerator.generateMapWithTreesAndSlagHeaps(getHeight(), getWidth(), cellFactory, 5, 2);
        controleur.initGame(map);
        joueur = new Player(this, spriteStore.createSprite("player"));
        joueur.setRow(map.getSoilHeight() - 1);
        joueur.setColumn(0);
        controleur.afficherMovable(joueur);
        controleur.setHealthProperty(joueur.getHealthProperty());
    }

    /**
     * Fait se déplacer le joueur vers la gauche.
     */
    public void moveLeft() {
        moveLeft(joueur);
    }

    /**
     * Fait se déplacer un objet mobile vers la gauche.
     *
     * @param movable L'objet mobile à déplacer.
     */
    public void moveLeft(AbstractMovable movable) {
        int column = movable.getColumn();
        int row = movable.getRow();
        int previousColumn = column - 1;

        controleur.masquerMovable(movable);
        if ((previousColumn >= 0)) {
            avancer(movable, row, previousColumn);
        }
        controleur.afficherMovable(movable);
    }

    /**
     * Fait se déplacer le joueur vers la droite.
     */
    public void moveRight() {
        moveRight(joueur);
    }


    /**
     * Fait se déplacer un objet mobile vers la droite.
     *
     * @param movable L'objet mobile à déplacer.
     */
    public void moveRight(AbstractMovable movable) {
        int column = movable.getColumn();
        int row = movable.getRow();
        int nextColumn = column + 1;

        controleur.masquerMovable(movable);
        if (nextColumn < map.getWidth()) {
            avancer(movable, row, nextColumn);
        }
        controleur.afficherMovable(movable);
    }

    /**
     * Fait avancer l'objet mobile vers la droite ou vers la gauche.
     *
     * @param movable      Le movable à déplacer.
     * @param rangee       La rangée actuelle du movable.
     * @param colonneCible La colonne sur laquelle le movable doit être déplacé.
     */
    private void avancer(AbstractMovable movable, int rangee, int colonneCible) {
        // Si c'est vide à droite ou si c'est une échelle, le movable avance à droite.
        if (map.getAt(rangee, colonneCible).getResource() == null || map.getAt(rangee, colonneCible).getResource().getName().equals(LADDER)) {
            movable.setColumn(colonneCible);
            move(joueur);
        }
        // S'il y a un bloc à droite, mais pas en haut à droite, le movable grimpe sur le bloc.
        else {
            grimper(movable, rangee, colonneCible);
        }
    }

    /**
     * Fait grimper le movable sur un bloc.
     *
     * @param movable      Le movable à déplacer.
     * @param row          La rangée actuelle du movable.
     * @param colonneCible La colonne sur laquelle le movable doit être déplacé.
     */
    private void grimper(AbstractMovable movable, int row, int colonneCible) {
        int rowAbove = row - 1;
        boolean blocGrimpable = map.getAt(row, colonneCible).getResource() != null && (map.getAt(rowAbove, colonneCible).getResource() == null || map.getAt(rowAbove, colonneCible).getResource().getName().equals("ladder"));
        if (blocGrimpable) {
            movable.setColumn(colonneCible);
            movable.setRow(rowAbove);
        }
    }

    /**
     * Fait se déplacer le joueur vers le haut.
     */
    public void moveUp() {
        moveUp(joueur);
    }

    /**
     * Fait se déplacer un movable vers le haut s'il est situé sur une échelle.
     *
     * @param movable Le movable à déplacer.
     */
    public void moveUp(AbstractMovable movable) {
        int row = movable.getRow();
        int rowAbove = row - 1;

        if (rowAbove >= 0) {
            Resource celluleActuelle = map.getAt(row, movable.getColumn()).getResource();
            boolean deplacementPossible = isMoveUpPossible(movable, rowAbove, celluleActuelle);

            if (deplacementPossible) {
                controleur.masquerMovable(movable);
                monterOuDescendre(movable, rowAbove);
                controleur.afficherMovable(movable);
            }
        }
    }

    /**
     * Vérifie que le déplacement vers le haut est possible.
     *
     * @param movable         Le movable qui veut aller vers le haut.
     * @param rowAbove        La rangée actuelle.
     * @param celluleActuelle La cellule sur laquelle se trouve le movable.
     * @return Si le movable peut se déplacer vers le haut.
     */
    private boolean isMoveUpPossible(AbstractMovable movable, int rowAbove, Resource celluleActuelle) {
        Resource celluleAuDessus = map.getAt(rowAbove, movable.getColumn()).getResource();

        // Vérifie si la cellule au-dessus est null, ou que c'est une échelle.
        boolean celluleAuDessusDisponible = celluleAuDessus == null || Objects.equals(celluleAuDessus.getName(), LADDER);

        // Vérifie si la cellule actuelle est une échelle.
        boolean celluleActuelleValide = celluleActuelle != null && Objects.equals(celluleActuelle.getName(), LADDER);

        return celluleActuelleValide && celluleAuDessusDisponible;
    }

    public void moveDown() {
        moveDown(joueur);
    }

    /**
     * Fait se déplacer un movable vers le bas si la ressource en-dessous de lui est une échelle.
     *
     * @param movable Le movable à déplacer.
     */
    public void moveDown(AbstractMovable movable) {
        int row = movable.getRow();
        int rowBelow = row + 1;
        if (rowBelow < map.getHeight()) {
            Resource celluleEnDessous = map.getAt(rowBelow, movable.getColumn()).getResource();

            // Vérifie si la cellule est une échelle.
            boolean celluleEnDessousValide = Objects.equals(celluleEnDessous.getName(), LADDER);

            if (celluleEnDessousValide) {
                controleur.masquerMovable(movable);
                monterOuDescendre(movable, rowBelow);
                controleur.afficherMovable(movable);
            }
        }

    }

    /**
     * Fait avancer le movable vers le haut ou le bas.
     *
     * @param movable     Le movable à déplacer
     * @param rangeeCible La rangée au-dessus du movable.
     */
    private void monterOuDescendre(AbstractMovable movable, int rangeeCible) {
        movable.setRow(rangeeCible);
    }

    /**
     * Déplace un objet mobile en tenant compte de la gravité.
     *
     * @param movable L'objet à déplacer.
     */
    private void move(AbstractMovable movable) {
        // On applique la gravité.
        Cell currentCell = getCellOf(movable);

        controleur.masquerMovable(movable);

        // Boucle qui déplace le personnage vers le bas jusqu'à ce qu'il soit positionné sur une ressource non nulle.
        for (int row = currentCell.getRow() + 1; row < map.getHeight(); row++) {
            Cell below = map.getAt(row, currentCell.getColumn());
            if (!below.move(movable)) {
                break;
            }
        }

        controleur.afficherMovable(movable);
    }

    /**
     * Fait creuser le joueur vers le bas.
     */
    public void digDown() {
        Cell currentCell = getCellOf(joueur);
        if ((currentCell.getRow() + 1) < map.getHeight()) {
            map.getAt(currentCell.getRow() + 1, currentCell.getColumn()).dig(joueur);
            move(joueur);
        }
    }

    /**
     * Fait creuser le joueur vers la gauche.
     */
    public void digLeft() {
        Cell currentCell = getCellOf(joueur);
        if ((currentCell.getColumn() - 1) >= 0) {
            map.getAt(currentCell.getRow(), currentCell.getColumn() - 1).dig(joueur);
            move(joueur);
        }
    }

    /**
     * Fait creuser le joueur vers la droite.
     */
    public void digRight() {
        Cell currentCell = getCellOf(joueur);
        if ((currentCell.getColumn() + 1) < map.getWidth()) {
            map.getAt(currentCell.getRow(), currentCell.getColumn() + 1).dig(joueur);
            move(joueur);
        }
    }

    public void digUp() {
        Cell currentCell = getCellOf(joueur);
        if ((currentCell.getRow() - 1) >= 0) {
            map.getAt(currentCell.getRow() - 1, currentCell.getColumn()).dig(joueur);
        }
    }

    /**
     * Retire un objet mobile du jeu.
     *
     * @param movable L'objet mobile à retirer.
     */
    public void removeMovable(AbstractMovable movable) {
        controleur.masquerMovable(movable);
    }

    public void placerEchelle() {
        Cell currentCell = getCellOf(joueur);
        currentCell.setResource(new Resource("ladder", spriteStore.createSprite("ladder")));
    }

    /**
     * Récupére la cellule correspondant à la position d'un objet mobile.
     *
     * @param movable L'objet mobile dont la cellule doit être récupérée.
     * @return La cellule occupée par l'objet mobile.
     */
    private Cell getCellOf(AbstractMovable movable) {
        int row = movable.getRow();
        int column = movable.getColumn();
        return map.getAt(row, column);
    }

    public void setControleur(IFlatcraftController controleur) {
        this.controleur = controleur;
    }
}
