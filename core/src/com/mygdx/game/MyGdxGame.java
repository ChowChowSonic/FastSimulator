package com.mygdx.game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Entities.Enemy;
import Entities.Player;

public class MyGdxGame extends ApplicationAdapter {
	/*
	 * framework class for the game. Does not do much aside from 
	 * supporting the other classes and allowing them to work
	 */
	SpriteBatch batch;
    OrthographicCamera camera;
    
    GameMap gameMap;
    Player p;
    
    float deltaX, deltaY;
    
	@Override
	public void create () {
		batch = new SpriteBatch();
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Gdx.graphics.setWindowedMode(screensize.width, screensize.height);
        float w = Gdx.graphics.getWidth()/1.75f;
        float h = Gdx.graphics.getHeight()/1.75f;
        
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        
        gameMap = new TiledGameMap("Practicemap.tmx");// Map to be loaded
        //gameMap = new TiledGameMap(); //...Or use a default map
        p=(Player) gameMap.getEntitybyType(new Player());
	}

	@Override
	public void render () {
		float deltatime = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClearColor(49/255.0f, 162.0f/255, 242.0f/255, 100);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if(p !=null)camera.translate((int)Math.ceil((p.getX()-camera.position.x)*deltatime*Math.abs(p.getXvel()+1)), 
        		(int)Math.ceil((p.getY() - camera.position.y + p.getYvel())*deltatime*Math.abs(p.getYvel()+1)));
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
