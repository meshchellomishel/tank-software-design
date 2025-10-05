package ru.mipt.bit.platformer.Input;

import com.badlogic.gdx.math.GridPoint2;

public class GamePosition {
    private GridPoint2 coordinates;
    private float rotation;

    public GamePosition(GridPoint2 coordinates, float rotation) {
        this.coordinates = coordinates.cpy();
        this.rotation = rotation;
    }

    public GridPoint2 getCoordinates() {
        return coordinates.cpy();
    }

    public float getRotation() {
        return rotation;
    }
}
