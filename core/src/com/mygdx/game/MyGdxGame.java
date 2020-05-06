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
        float w = Gdx.graphics.getWidth()/2.2f;
        float h = Gdx.graphics.getHeight()/2.2f;
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false,320*1.3f,224*1.3f);
        camera.update();
        
        gameMap = new TiledGameMap("smallmap.tmx", "Mio Honda & Crush 40 - Step! x I Am... All Of Me (Mashup).mp3");// Map to be loaded
        //gameMap = new TiledGameMap(); //...Or use a default map
        p=(Player) gameMap.getEntitybyType(new Player());
	}

	@Override
	public void render () {
		float deltatime = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(49/255.0f, 162.0f/255, 242.0f/255, 100);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
camera.translate((p.getX()-camera.position.x)/2, (p.getY()-camera.position.y)/2);
        camera.update();//Translate BEFORE Update. Always. 
        gameMap.update(deltatime);
        gameMap.render(camera, batch);
        
        //Debug and preformance stuff
        int FPS = Gdx.graphics.getFramesPerSecond();
        if(FPS < 60) {
        System.out.println(FPS);
        }
        }
	
	@Override
	public void dispose () {
		batch.dispose();
		gameMap.dispose();
	}
}
