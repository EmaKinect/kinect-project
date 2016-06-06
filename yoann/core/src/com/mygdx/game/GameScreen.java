package com.mygdx.game;

import java.util.Iterator;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen {

	final MyGdxGame game;
	private OrthographicCamera camera;
	private Texture imgTacos, imgDeadpool;
	private Sound sound;
	private Music music;
	private Rectangle deadpool;
	private Array<Rectangle> tacos;
	private long lastTacosTime;
	private int tacosEaten, tacosLost;
	private Vector3 touchPos = new Vector3();
	private float width = Gdx.graphics.getWidth(), height = Gdx.graphics.getHeight();

	public GameScreen(MyGdxGame gam) {
		this.game = gam;

		imgTacos = new Texture(Gdx.files.internal("tacos.png"));
		imgDeadpool = new Texture(Gdx.files.internal("deadpool.jpg"));

		sound = Gdx.audio.newSound(Gdx.files.internal("eatTacos.wav"));
		music = Gdx.audio.newMusic(Gdx.files.getFileHandle("data/04.mp3", FileType.Internal));
		music.setVolume(0.2f);
		music.setLooping(true);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);

		deadpool = new Rectangle();
		deadpool.x = 20;
		deadpool.y = height / 2 - 64 / 2;
		deadpool.width = 64;
		deadpool.height = 64;

		tacos = new Array<Rectangle>();
		spawnTacos();

	}

	private void spawnTacos() {
		Rectangle taco = new Rectangle();
		taco.x = width;
		taco.y = MathUtils.random(0, height - 64);
		taco.width = 64;
		taco.height = 64;
		tacos.add(taco);
		lastTacosTime = TimeUtils.nanoTime();
	}

	@Override
	public void show() {
		music.play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.font.draw(game.batch, "Tacos Eaten: " + tacosEaten, width * 0.1f, height * 0.95f);
		game.font.draw(game.batch, "Tacos Lost: " + tacosLost, width * 0.1f, height * 0.93f);
		game.batch.draw(imgDeadpool, deadpool.x, deadpool.y);
		for (Rectangle taco : tacos) {
			game.batch.draw(imgTacos, taco.x, taco.y, 64, 64);
		}
		game.batch.end();

		if (Gdx.input.isTouched()) {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			deadpool.y = touchPos.y - 64 / 2;
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN))
			deadpool.y -= 800 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.UP))
			deadpool.y += 800 * Gdx.graphics.getDeltaTime();
		if (deadpool.y < 10)
			deadpool.y = 10;
		if (deadpool.y > height - (64 + 10))
			deadpool.y = height - (64 + 10);

		if (TimeUtils.nanoTime() - lastTacosTime > 1000000000)
			spawnTacos();

		Iterator<Rectangle> iter = tacos.iterator();
		while (iter.hasNext()) {
			Rectangle taco = iter.next();
			taco.x -= 500 * Gdx.graphics.getDeltaTime();
			if (taco.x + 64 < 0) {
				tacosLost++;
				iter.remove();
			}
			if (taco.overlaps(deadpool)) {
				tacosEaten++;
				sound.play();
				iter.remove();
			}
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
		imgDeadpool.dispose();
		imgTacos.dispose();
		sound.dispose();
		music.dispose();
	}

}
