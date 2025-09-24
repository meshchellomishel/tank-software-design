package ru.mipt.bit.platformer.model;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static com.badlogic.gdx.math.MathUtils.isEqual;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.GameActions;

public class TankModel extends GameObjectModel {
    public TankModel(GridPoint2 initCoordinates, String spritePath) {
        super(initCoordinates, spritePath);
    }

    public void continueProgress(float deltaTime, float speed) {
        this.movingProgress = clamp(this.movingProgress + deltaTime / speed, 0f, 1f);
    }

    public void calculateDestination(GameActions action) {
        GridPoint2 moveGap;
        
        if (!isEqual(this.movingProgress, 1f)) {
            return;
        }
        switch (action) {
            case MOVE_UP:
                moveGap = new GridPoint2(0, 1);
                this.rotation = 90f;
                break;
            case MOVE_DOWN:
                moveGap = new GridPoint2(0, -1);
                this.rotation = -90f;
                break;
            case MOVE_LEFT:
                moveGap = new GridPoint2(-1, 0);
                this.rotation = 180f;
                break;
            case MOVE_RIGHT:
                moveGap = new GridPoint2(1, 0);
                this.rotation = 0f;
                break;
            default:
                return;
        }
        this.destinationCoordinates = moveGap.add(this.coordinates);
        this.movingProgress = 0f;
    }

    public void resetDestination() {
        this.destinationCoordinates = this.coordinates.cpy();
        this.movingProgress = 1f;
    }

    public void processAction() {
        this.movingProgress = 1f;
        this.coordinates = destinationCoordinates.cpy();
    }
}
