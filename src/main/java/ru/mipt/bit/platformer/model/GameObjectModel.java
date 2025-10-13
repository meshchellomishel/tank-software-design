package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public abstract class GameObjectModel {
    protected GridPoint2 coordinates;
    protected float rotation;
    protected float movingProgress;

    public GameObjectModel(GridPoint2 initCoordinates) {
        coordinates = initCoordinates.cpy();
        rotation = 0f;
        movingProgress = 1f;
    }

    public void setCoordinates(GridPoint2 input) {
        coordinates = input.cpy();
    }

    public GridPoint2 getCoordinates() {
        return coordinates.cpy();
    }

    public float getRotation() {
        return rotation;
    }

    public float getProgress() {
        return movingProgress;
    }

    public void resetProgress() {
        movingProgress = 1f;
    }
}
