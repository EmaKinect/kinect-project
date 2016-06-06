package com.mygdx.game.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Yoann Tacos Challenge";
		config.width = 1920;
		config.height = 1080;
		// config.fullscreen = true;
		config.addIcon("icon.png", FileType.Internal);
		new LwjglApplication(new MyGdxGame(), config);
	}
}
