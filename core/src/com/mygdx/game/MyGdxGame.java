package com.mygdx.game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
    
    GameMap gameMap;
    Player p;
    float deltaX, deltaY;
    
	@Override
	public void create () {
		batch = new SpriteBatch();
		region = new TextureRegion();
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Gdx.graphics.setWindowedMode(screensize.width, screensize.height);
        float w = Gdx.graphics.getWidth()/1.75f;
        float h = Gdx.graphics.getHeight()/1.75f;
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        
        gameMap = new TiledGameMap("DemoMap.tmx", "Discord Sounds.mp3");// Map to be loaded
        //gameMap = new TiledGameMap(); //...Or use a default map
        p=(Player) gameMap.getEntitybyType(new Player());
	}

	@Override
	public void render () {
		float deltatime = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(49/255.0f, 162.0f/255, 242.0f/255, 100);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if(p !=null) {
        camera.position.x = p.getX()-p.getXvel();
        camera.position.y = p.getY()-p.getYvel();
        }
        camera.update();//Translate BEFORE Update. Always. 
        gameMap.update(deltatime);
        gameMap.render(camera, batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		gameMap.dispose();
	}
}
