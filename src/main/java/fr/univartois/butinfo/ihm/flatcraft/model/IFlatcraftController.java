package fr.univartois.butinfo.ihm.flatcraft.model;

import javafx.beans.property.IntegerProperty;

public interface IFlatcraftController {
    void setJeu(FlatcraftGame jeu);

    void initGame(GameMap map);

    void afficherMovable(AbstractMovable movable);

    void masquerMovable(AbstractMovable movable);

    void setHealthProperty(IntegerProperty healthProperty);
}
