package ru.mipt.bit.platformer.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import static com.badlogic.gdx.Input.Keys.*;

public class TankProcessor {
    public static final GamePosition MOVE_UNSPEC = null;
    public static final GamePosition MOVE_UP = new GamePosition(new GridPoint2(0, 1), 90f);
    public static final GamePosition MOVE_DOWN = new GamePosition(new GridPoint2(0, -1), -90f);
    public static final GamePosition MOVE_LEFT = new GamePosition(new GridPoint2(-1, 0), 180f);
    public static final GamePosition MOVE_RIGHT = new GamePosition(new GridPoint2(1, 0), 0f);

    public static GamePosition getAction() {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            return MOVE_UP;
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            return MOVE_LEFT;
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            return MOVE_DOWN;
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            return MOVE_RIGHT;
        }
        return MOVE_UNSPEC;
    }
}
