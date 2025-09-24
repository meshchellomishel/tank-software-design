package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import static com.badlogic.gdx.math.MathUtils.clamp;

public abstract class GameObjectModel {
    protected GridPoint2 coordinates;
    protected GridPoint2 destinationCoordinates;
    protected float rotation;
    protected float movingProgress;
    private Texture texture;
    private TextureRegion graphics;
    private Rectangle rectangle;

    private Rectangle createBoundingRectangle(TextureRegion region) {
        return new Rectangle()
                .setWidth(region.getRegionWidth())
                .setHeight(region.getRegionHeight());
    }

    public void dispose() {
        texture.dispose();
    }

    public GameObjectModel(GridPoint2 initCoordinates, String spritePath) {
        coordinates = initCoordinates.cpy();
        destinationCoordinates = coordinates.cpy();
        rotation = 0f;
        texture = new Texture(spritePath);
        graphics = new TextureRegion(texture);
        rectangle = createBoundingRectangle(graphics);
        movingProgress = 1f;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    public void setDestinationCoordinates(GridPoint2 input) {
        destinationCoordinates = input.cpy();
    }

    public void setCoordinates(GridPoint2 input) {
        coordinates = input.cpy();
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public float getRotation() {
        return rotation;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public TextureRegion getGraphics() {
        return graphics;
    }

    public float getProgress() {
        return movingProgress;
    }
}
