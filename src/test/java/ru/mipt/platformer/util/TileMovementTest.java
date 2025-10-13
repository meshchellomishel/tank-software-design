package ru.mipt.platformer.util;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ru.mipt.bit.platformer.util.TileMovement;

public class TileMovementTest {
    private TiledMapTileLayer tileLayer;
    private Interpolation interpolation;
    private TileMovement tileMovement;

    @BeforeEach
    public void setUp() {
        this.interpolation = Interpolation.smooth;
        this.tileLayer = new TiledMapTileLayer(100, 100, 10, 10);
        this.tileMovement = new TileMovement(tileLayer, interpolation);
    }

    private void moveRectangleBetweenTileCentersForProgress(Rectangle rect, GridPoint2 fromTile, GridPoint2 toTile) {
        for (float progress = 0f; progress < 1f; progress += 0.1f) {
            Rectangle result = this.tileMovement.moveRectangleBetweenTileCenters(rect, fromTile, toTile, progress);

            float expectedX = this.interpolation.apply(fromTile.x * 10, toTile.x * 10, progress);
            float expectedY = this.interpolation.apply(fromTile.y * 10, toTile.y * 10, progress);

            assertEquals(expectedX, result.x, 0.001);
            assertEquals(expectedY, result.y, 0.001);
        }
    }

    @Test
    public void testMoveRectangleBetweenTileCentersZero() {
        Rectangle rect = new Rectangle(0, 0, 10, 10);
        GridPoint2 fromTile = new GridPoint2(1, 2);
        GridPoint2 toTile = new GridPoint2(1, 2);

        moveRectangleBetweenTileCentersForProgress(rect, fromTile, toTile);
    }

    @Test
    public void testMoveRectangleBetweenTileCentersSimple() {
        Rectangle rect = new Rectangle(0, 0, 10, 10);
        GridPoint2 fromTile = new GridPoint2(1, 2);
        GridPoint2 toTile = new GridPoint2(3, 4);

        moveRectangleBetweenTileCentersForProgress(rect, fromTile, toTile);
    }
}