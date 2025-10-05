package ru.mipt.bit.platformer.controller;

import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class ViewController {
    public ViewController() {}

    public void clearScreen() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }
}
