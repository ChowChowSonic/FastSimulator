package com.mygdx.game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import Entities.Player;

public class MyGdxGame extends ApplicationAdapter {
	/*
	 * framework class for the game. Does not do much aside from 
	 * supporting the other classes and allowing them to work
	 */
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private TiledGameMap gameMap;
	private Player p;
	private Stage menu;
	private Skin defaultlook;

	private float w, h;
	private double loadtime;
	private boolean maploaded = false;
	public enum gamestate{menu, level, hub};
	public gamestate currentstate = gamestate.menu;

	@Override
	public void create () {
		//Load in all the required components  
		//loadtime = System.currentTimeMillis();
		defaultlook = new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
		batch = new SpriteBatch();
		new TextureRegion();
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Gdx.graphics.setWindowedMode(screensize.width, screensize.height);

		//load in the main menu
		menu = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(menu);
		Button QueuedButton = new TextButton("Play",defaultlook);
		QueuedButton.setSize(500, 200);
		QueuedButton.setPosition(menu.getWidth()/3, menu.getHeight()/1.4f);
		QueuedButton.addListener(new InputListener() {
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				currentstate = gamestate.hub;
				maploaded = false;
				return true;
			}
		});

		Button Options = new TextButton("Options",defaultlook);
		Options.setSize(500, 200);
		Options.setPosition(menu.getWidth()/3, menu.getHeight()/2.5f);
		Options.addListener(new InputListener() {
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});

		Button ExitButton = new TextButton("Exit",defaultlook);
		ExitButton.setSize(500, 200);
		ExitButton.setPosition( menu.getWidth()/3, menu.getHeight()/12);
		ExitButton.addListener(new InputListener() {
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
			}
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				dispose();
				System.exit(0);
				System.gc();
				return true;
			}
		});

		menu.addActor(QueuedButton);
		menu.addActor(Options);
		menu.addActor(ExitButton);

		System.out.println(loadtime - System.currentTimeMillis());
	}

	@Override
	public void render () {
		if(currentstate == gamestate.menu) {
			Gdx.gl.glClearColor( 0, 0, 0, 0 );//clears the background
			Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );//clears the depth buffer bit
			//...Dont ask me what that is all I know is that these two lines of code stop everything from flickering.
			menu.act();
			menu.draw();
		}
		
		else if(currentstate == gamestate.hub) {
			if(!maploaded)loadhub();
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
		
		else if(currentstate == gamestate.level) {
			if(!maploaded) loadmap("exterminationDemo.tmx");
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
	private void loadmap(String mapname) {
		try {
			maploaded = false;
			gameMap = new TiledGameMap(mapname, 1);// Map to be loaded
			p=(Player) gameMap.getEntitybyType(new Player());
			p.setLayer(1);
			//gameMap = new TiledGameMap(); //...Or use a default map
			int[] spawnpoint = gameMap.getPlayerSpawnPoint();
			p.setX(spawnpoint[0]);
			p.setY(spawnpoint[1]);

			//load the camera
			camera = new OrthographicCamera();
			w = Gdx.graphics.getWidth()/1.75f;
			h = Gdx.graphics.getHeight()/1.75f;
			camera.setToOrtho(false,w,h);
			camera.update();

			//confirm map was loaded
			maploaded = true;
			return;
		}catch(Exception e) {
			this.dispose();
			e.printStackTrace();
		}
		maploaded = false;
	}
	private void loadmap(String mapname, String musicname) {
		try {
			maploaded = false;
			gameMap = new TiledGameMap(mapname, musicname, 1);// Map to be loaded
			p=(Player) gameMap.getEntitybyType(new Player());
			p.setLayer(1);
			//gameMap = new TiledGameMap(); //...Or use a default map
			int[] spawnpoint = gameMap.getPlayerSpawnPoint();
			p.setX(spawnpoint[0]);
			p.setY(spawnpoint[1]);

			//load the camera
			camera = new OrthographicCamera();
			w = Gdx.graphics.getWidth()/1.75f;
			h = Gdx.graphics.getHeight()/1.75f;
			camera.setToOrtho(false,w,h);
			camera.update();

			//confirm map was loaded
			maploaded = true;
			return;
		}catch(Exception e) {
			this.dispose();
			e.printStackTrace();
		}
		maploaded = false;
	}
	
	private void loadmap(String mapname, String musicname, int standinglayer) {
		try {
			maploaded = false;
			gameMap = new TiledGameMap(mapname, musicname, standinglayer);// Map to be loaded
			p=(Player) gameMap.getEntitybyType(new Player());
			p.setLayer(standinglayer);
			//gameMap = new TiledGameMap(); //...Or use a default map
			int[] spawnpoint = gameMap.getPlayerSpawnPoint();
			p.setX(spawnpoint[0]);
			p.setY(spawnpoint[1]);

			//load the camera
			camera = new OrthographicCamera();
			w = Gdx.graphics.getWidth()/1.75f;
			h = Gdx.graphics.getHeight()/1.75f;
			camera.setToOrtho(false,w,h);
			camera.update();

			//confirm map was loaded
			maploaded = true;
			return;
		}catch(Exception e) {
			this.dispose();
			e.printStackTrace();
		}
		maploaded = false;
	}

	private void loadhub() {
		loadmap("hub.tmx", "Shop (Magolor the Wayfarer).mp3", 2);
		p.setLayer(2);
		p.setX(10);
		p.setY(500);
	}

	/**
	 * Extracts the player from the mission, cues the mission successful/fail theme, then displays the mission summary menu.
	 * 
	 * Then it unloads the map and returns the player to the hub
	 */
	private void extract() {
		//extract from the mission
		//gotta add this later...

		//unload the map
		gameMap = null; 
		maploaded = false;
		System.gc();

		//return to the hub
		currentstate = gamestate.hub;
	}

	@Override
	public void dispose () {
		if (batch!= null)
			batch.dispose();
		if(gameMap != null)
			gameMap.dispose();
	}
}
