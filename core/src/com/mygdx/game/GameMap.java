package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Entities.Enemy;
import Entities.Entity;
import Entities.EntitySnapshot;
import Entities.EntityType;
import Entities.Player;

public abstract class GameMap {

	protected ArrayList<Entity> entities; 
	final int SCREENPILLOWING_X = (int)Math.ceil(0.3*Gdx.graphics.getWidth());//prevents the player from seeing outside the map
	final int SCREENPILLOWING_Y = 250;//ditto above
	
	/**
	 * Creates a new GameMap class object, and loads in a new array of entities from a file.
	 * File directory is determined by EntityLoader.loadEntities();
	 */
	public GameMap() {
		entities = new ArrayList<Entity>();
		if(this.getEntitybyType(new Player()) == null) {
			EntitySnapshot e = new EntitySnapshot(EntityType.PLAYER.getId(), 1200.0f, 400.0f);
			entities.add(new Player(e, this));
			entities.add(new Enemy(e, this));
		}
	}
	public void render(OrthographicCamera camera, SpriteBatch batch) {
		for (Entity e : entities) {
			e.render(batch);
		}
	}
	
	/**
	 * The Update(float, float) methods in entities being updated MUST have the following:
	 * super.update(float, float) in there somewhere
	 * proper parameters
	 * ...otherwise they just wont work
	 * @param delta
	 */
	public void update(float delta) {
		for (Entity e : entities) {
			//this.playeronEnemycollision(delta);
			e.update(delta, -9.8f);
		}
		/*if(Gdx.input.isKeyJustPressed(Keys.S)) { //ununsed saving feature
			EntityLoader.saveEntities("entities", entities);
		}*/
	}
	public void dispose() {
	}

	/**
	 * This method gets a tile based on the pixel position in 
	 * the game world on a specified layer
	 * @param layer 
	 * @param x
	 * @param y
	 * @return
	 */
	public TileType getTileTypeByLocation(int layer, float x, float y) {
		return this.getTileTypeByLocation(layer,
				(int) (x/TileType.TILE_SIZE),(int) (y/TileType.TILE_SIZE));
	}

	public abstract TileType getTileTypeByLocation(int layer, int col, int row);

	public abstract int getHeight();
	public abstract int getWidth();
	public abstract int getLayers();

	public boolean RectCollidesWithMap(float x, float y, int width, int height) {
		boolean isoutofbounds = (x-SCREENPILLOWING_X) < 0 || (y - SCREENPILLOWING_Y) < 0 || 
				x + width + SCREENPILLOWING_X > getPixelWidth() || (y + height + SCREENPILLOWING_Y > getPixelHeight());
		if(isoutofbounds) return true;
		
		for (int row = (int) (y/TileType.TILE_SIZE); row < Math.ceil((y+height)/TileType.TILE_SIZE); row++) {
			for (int col = (int) (x/TileType.TILE_SIZE); col < Math.ceil((x+width)/TileType.TILE_SIZE); col++) {
				for (int layer = 0; layer < getLayers(); layer++) {
				TileType type = getTileTypeByLocation(layer, col, row);
				if(type != null && type.isCollidable()) {
					return true;
					}
				}
			}
		}
		return false;
		
	}//ends RectCollidesWithMap

	public int getPixelWidth() { 
		return(this.getWidth()*TileType.TILE_SIZE);
	}
	public int getPixelHeight() { 
		return(this.getHeight()*TileType.TILE_SIZE);
	}
	
	public ArrayList<Entity> getentitylist(){
		return entities;
	}
	public Player getEntitybyType(Player entity) {
		for(Entity ent : entities) {
			if (ent instanceof Player) {
				return (Player) ent;
			}
		}
		return null;
	}
	public ArrayList<Enemy> getEntitybyType(Enemy entity) {
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		for(Entity ent : entities) {
			if (ent instanceof Enemy) {
				enemies.add((Enemy) ent);
			}
		}
		return enemies;
	}

}//ends class