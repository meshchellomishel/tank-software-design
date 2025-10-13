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
import com.badlogic.gdx.math.Interpolation;

import ru.mipt.bit.platformer.controller.PlayerController;
import ru.mipt.bit.platformer.controller.ViewController;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class GameDesktopLauncher implements ApplicationListener {
    private Batch batch;
    private TiledMap level;
    private MapRenderer levelRenderer;

    private ViewController viewController;
    private PlayerController playerController;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        viewController = new ViewController();
        playerController = new PlayerController(groundLayer, Interpolation.smooth);

        playerController.initScene();
    }

    @Override
    public void render() {
        viewController.clearScreen();
        float deltaTime = Gdx.graphics.getDeltaTime();
        
        playerController.processAction(deltaTime);

        // render each tile of the level
        levelRenderer.render();
        // start recording all drawing commands
        batch.begin();
        // render player
        playerController.drawObjects(batch);
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
        playerController.dispose();
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
