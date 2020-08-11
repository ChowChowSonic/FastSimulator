package com.mygdx.game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import Entities.Enemy;
import Entities.Player;

public class MyGdxGame extends ApplicationAdapter {
	/*
	 * framework class for the game. Does not do much aside from 
	 * supporting the other classes and allowing them to work
	 */
	SpriteBatch batch;
	TextureRegion region;
	OrthographicCamera camera;

	TiledGameMap gameMap;
	Player p;
	float deltaX, deltaY;
	float w;
	float h;
	public enum gamestate{menu, level, hub};
	gamestate currentstate = gamestate.menu;

	@Override
	public void create () {
		//menu state
		if(currentstate == gamestate.menu) {
			
		}

		//game state
		if(currentstate == gamestate.level) {
			batch = new SpriteBatch();
			region = new TextureRegion();
			Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
			Gdx.graphics.setWindowedMode(screensize.width, screensize.height);

			camera = new OrthographicCamera();
			w = Gdx.graphics.getWidth()/1.75f;
			h = Gdx.graphics.getHeight()/1.75f;
			camera.setToOrtho(false,w,h);
			camera.update();

			gameMap = new TiledGameMap("Practicemap.tmx", "aFinal Fantasy VII Remake - [ Battle Theme ] Let the Battles Begin (OST).mp3");// Map to be loaded
			//gameMap = new TiledGameMap(); //...Or use a default map
			p=(Player) gameMap.getEntitybyType(new Player());
			int[] spawnpoint = gameMap.getPlayerSpawnPoint();
			p.setX(spawnpoint[0]);
			p.setY(spawnpoint[1]);
		}
	}

	@Override
	public void render () {

		//game state
		if(currentstate == gamestate.level) {
			float deltatime = Gdx.graphics.getDeltaTime();
			Gdx.gl.glClearColor(49/255.0f, 162.0f/255, 242.0f/255, 100);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			if(p !=null) {
				camera.position.x = p.getX();
				camera.position.y = p.getY();

				//clamp the camera so it doesn't go out of bounds
				if(camera.position.x - w/2 < 0) {
					camera.position.x += Math.abs(camera.position.x - w/2);
				}else if(camera.position.x + w/2 > gameMap.getWidth()*TileType.TILE_SIZE) {
					camera.position.x -= Math.abs(camera.position.x + w/2 - gameMap.getWidth()*TileType.TILE_SIZE);
				}
				if(camera.position.y - h/2 < 0) {
					camera.position.y += Math.abs(camera.position.y - h/2);
				}else if(camera.position.y + h/2 > gameMap.getHeight()*TileType.TILE_SIZE) {
					camera.position.y -= Math.abs(camera.position.y + h/2 - gameMap.getHeight()*TileType.TILE_SIZE);
				}
			}
			gameMap.update(deltatime);
			camera.update();//Translate BEFORE Update. Always. 
			gameMap.render(camera, batch);
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
		gameMap.dispose();
	}
}
