package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen implements Screen {

	final MyGdxGame game;
	OrthographicCamera camera;
	private Texture wall;

	public MainMenuScreen(final MyGdxGame gam) {
		game = gam;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		wall = new Texture(Gdx.files.internal("wall.jpg"));
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.batch.draw(wall, 0, 0);
		game.font.draw(game.batch, "Welcome to \"Deadpool eat Tacos\" the game!!! ", (Gdx.graphics.getWidth() * 0.01f),
				(Gdx.graphics.getHeight() * 0.95f));
		game.font.draw(game.batch, "Tap anywhere to begin!", (Gdx.graphics.getWidth() * 0.01f),
				(Gdx.graphics.getHeight() * 0.9f));
		game.batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}
}
