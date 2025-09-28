package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;

import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.model.TreeModel;
import ru.mipt.bit.platformer.util.TileMovement;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;
import static com.badlogic.gdx.Input.Keys.*;

public class GameDesktopLauncher implements ApplicationListener {
    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private TankModel tank;
    private TreeModel tree;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        tree = new TreeModel(new GridPoint2(1, 1), "images/greenTree.png");
        tank = new TankModel(new GridPoint2(1, 3), "images/tank_blue.png");
        moveRectangleAtTileCenter(groundLayer, tree.getRectangle(), tree.getCoordinates());
    }

    private GameActions getAction() {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            return GameActions.MOVE_UP;
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            return GameActions.MOVE_LEFT;
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            return GameActions.MOVE_DOWN;
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            return GameActions.MOVE_RIGHT;
        }
        return GameActions.MOVE_UNSPEC;
    }

    private GridPoint2 processUnderScreenCoordinates(GridPoint2 coordinates) {
        GridPoint2 result = coordinates.cpy();
        TiledMapTileLayer groundLayer = getSingleLayer(level);

        if (result.x < 0) {
            result.add(groundLayer.getWidth(), 0);
        }
        if (result.y < 0) {
            result.add(0, groundLayer.getHeight());
        }

        result.set(
            result.x % groundLayer.getWidth(),
            result.y % groundLayer.getHeight()
        );

        return result;
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
        float deltaTime = Gdx.graphics.getDeltaTime();
        GameActions action = getAction();

        tank.calculateDestination(action);
        if (tree.getCoordinates().equals(tank.getDestinationCoordinates())) {
            tank.resetDestination();
        }
        tank.setDestinationCoordinates(
            processUnderScreenCoordinates(
                tank.getDestinationCoordinates()
            )
        );
        tileMovement.moveRectangleBetweenTileCenters(
            tank.getRectangle(),
            tank.getCoordinates(),
            tank.getDestinationCoordinates(),
            tank.getProgress()
        );

        tank.continueProgress(deltaTime, MOVEMENT_SPEED);
        if (isEqual(tank.getProgress(), 1f)) {
            tank.processAction();
        }

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        drawTextureRegionUnscaled(
            batch,
            tank.getGraphics(),
            tank.getRectangle(),
            tank.getRotation()
        );

        // render tree obstacle
        drawTextureRegionUnscaled(
            batch,
            tree.getGraphics(),
            tree.getRectangle(),
            0f
        );

        // submit all drawing requests
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void pause() {
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void resume() {
        // throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void dispose() {
        tank.dispose();
        tree.dispose();
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
