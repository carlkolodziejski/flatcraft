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

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.Random;

/**
 * La classe fr.univartois.butinfo.ihm.flatcraft.model.Mob représente une créature vivante du jeu Flatcraft.
 * Celle-ci peut se déplacer seule, sans avoir besoin d'être contrôlée par le joueur.
 *
 * @author Romain Wallon
 * @version 0.1.0
 */
public final class Mob extends AbstractMovable {

    /**
     * Le générateur de nombres pseudo-aléatoires utilisé pour les déplacements aléatoires
     * des mobs.
     */
    private static final Random RANDOM = new Random();

    /**
     * La timeline permettant d'animer le mob.
     */
    private Timeline timeline;

    /**
     * Crée une nouvelle instance de fr.univartois.butinfo.ihm.flatcraft.model.Mob.
     *
     * @param game          Le jeu dans lequel le mob évolue.
     * @param sprite        Le sprite représentant le mob.
     * @param initialHealth Les points de vie initiaux du mob.
     */
    public Mob(FlatcraftGame game, Image sprite, int initialHealth) {
        super(game, sprite, initialHealth);
    }

    /**
     * Anime ce mob afin qu'il se déplace seul.
     */
    public void animate() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> moveRandomly()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Réalise un déplacement aléatoire de ce mob.
     */
    private void moveRandomly() {
        if (RANDOM.nextBoolean()) {
            game.moveLeft(this);

        } else {
            game.moveRight(this);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.ihm.flatcraft.model.fr.univartois.butinfo.ihm.flatcraft.model.AbstractMovable#decrementHealth()
     */
    @Override
    public void decrementHealth() {
        super.decrementHealth();
        if (getHealth() == 0) {
            timeline.stop();
        }
    }

}
