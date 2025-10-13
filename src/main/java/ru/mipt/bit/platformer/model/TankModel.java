package ru.mipt.bit.platformer.model;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static com.badlogic.gdx.math.MathUtils.isEqual;

import com.badlogic.gdx.math.GridPoint2;

public class TankModel extends GameObjectModel {
    private float speed;
    private GridPoint2 destinationCoordinates;

    public TankModel(GridPoint2 initCoordinates, float speed) {
        super(initCoordinates);
        this.speed = speed;
        this.destinationCoordinates = coordinates.cpy();
    }

    public void continueProgress(float deltaTime) {
        this.movingProgress = clamp(this.movingProgress + deltaTime / speed, 0f, 1f);
    }

    public void setDestinationCoordinates(GridPoint2 coordinates) {       
        if (!isEqual(this.movingProgress, 1f) || this.coordinates.equals(coordinates)) {
            return;
        }

        this.destinationCoordinates = coordinates.cpy();
        this.movingProgress = 0f;
    }

    public void moveDestination(GridPoint2 coordinates, float rotation) {       
        if (!isEqual(this.movingProgress, 1f)) {
            return;
        }

        this.destinationCoordinates = coordinates.cpy();
        this.destinationCoordinates.add(this.coordinates);
        this.rotation = rotation;
        this.movingProgress = 0f;
    }

    public void resetAction() {
        this.destinationCoordinates = this.coordinates.cpy();
        this.movingProgress = 1f;
    }

    public GridPoint2 getDestinationCoordinates() {
        return this.destinationCoordinates.cpy();
    }

    public void commitAction() {
        this.coordinates = destinationCoordinates.cpy();
        this.movingProgress = 1f;
    }
}
