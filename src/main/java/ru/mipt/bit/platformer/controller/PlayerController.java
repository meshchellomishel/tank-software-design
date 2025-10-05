package ru.mipt.bit.platformer.controller;

import ru.mipt.bit.platformer.view.ObjectView;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;
import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;

import ru.mipt.bit.platformer.Input.GamePosition;
import ru.mipt.bit.platformer.Input.TankProcessor;
import ru.mipt.bit.platformer.model.GameObjectModel;
import ru.mipt.bit.platformer.model.TankModel;
import ru.mipt.bit.platformer.model.TreeModel;
import ru.mipt.bit.platformer.util.TileMovement;

public class PlayerController {
    private TankModel tankModel;
    private ObjectView tankView;
    private TreeModel treeModel;
    private ObjectView treeView;
    private TiledMapTileLayer layer;
    private TileMovement tileMovement;

    public void initScene() {
        moveRectangleAtTileCenter(layer, treeView.getRectangle(), treeModel.getCoordinates());
    }

    public void dispose() {
        tankView.dispose();
        treeView.dispose();
    }

    public PlayerController(TiledMapTileLayer layer, Interpolation interpolation) {
        tankView = new ObjectView("images/tank_blue.png");
        treeView = new ObjectView("images/greenTree.png");
        treeModel = new TreeModel(new GridPoint2(1, 1));
        tankModel = new TankModel(new GridPoint2(1, 3), 0.4f);
        this.layer = layer;
        this.tileMovement = new TileMovement(layer, interpolation);
    }

    private GridPoint2 processUnderScreenCoordinates(GridPoint2 coordinates) {
        GridPoint2 result = coordinates.cpy();

        if (result.x < 0) {
            result.add(layer.getWidth(), 0);
        }
        if (result.y < 0) {
            result.add(0, layer.getHeight());
        }

        result.set(
            result.x % layer.getWidth(),
            result.y % layer.getHeight()
        );

        return result;
    }

    private void moveObjects() {
        tileMovement.moveRectangleBetweenTileCenters(
            tankView.getRectangle(),
            tankModel.getCoordinates(),
            tankModel.getDestinationCoordinates(),
            tankModel.getProgress()
        );
    }

    private void drawTexture(Batch batch, ObjectView view, GameObjectModel model) {
        drawTextureRegionUnscaled(
            batch,
            view.getGraphics(),
            view.getRectangle(),
            model.getRotation()
        );
    }

    public void drawObjects(Batch batch) {
        drawTexture(batch, treeView, treeModel);
        drawTexture(batch, tankView, tankModel);
    }

    private void processNewPosition(GamePosition position) {
        tankModel.moveDestination(
            position.getCoordinates(),
            position.getRotation()
        );
        if (treeModel.getCoordinates().equals(tankModel.getDestinationCoordinates())) {
            tankModel.resetAction();
        }
    }

    private Boolean isUnderScreen() {
        GridPoint2 coordinates = tankModel.getDestinationCoordinates();

        if (coordinates.x < 0 || coordinates.x > layer.getWidth()) {
            return true;
        }
        if (coordinates.y < 0 || coordinates.y > layer.getHeight()) {
            return true;
        }
        return false;
    }

    public void processAction(float deltaTime) {
        GamePosition position = TankProcessor.getAction();
        if (position != null) {
            processNewPosition(position);
        }
        if (isUnderScreen()) {
            tankModel.setDestinationCoordinates(
                processUnderScreenCoordinates(
                    tankModel.getDestinationCoordinates()
                )
            );
            // tankModel.resetProgress();
        }

        moveObjects();
        tankModel.continueProgress(deltaTime);
        if (isEqual(tankModel.getProgress(), 1f)) {
            tankModel.commitAction();
        }
    }
}
