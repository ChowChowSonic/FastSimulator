package com.mygdx.game;

import java.util.HashMap;

public enum TileType {
	
	/* When working with a TMX file, make sure the ID of each block used  
	 * corresponds to the ID of the block here PLUS ONE
	 */
	GRASS(1, 0, true, "Grass"),
	DIRT(2, 0, true, "Dirt"),
	SKY(3, 0, false, "Sky"),
	LAVA(4, 0, true, "Lava"),
	CLOUD(5, 0, true, "Cloud"),
	STONE(6, 0, true, "Stone");
	
	public static final int TILE_SIZE = 1;
	private int id;
	private int angle;
	private boolean collidable;
	private String name;
	private float damage = 0;
	
	private TileType(int id, int angle, boolean collidable, String name) {
	this.id = id;
	this.angle = angle;
	this.collidable = collidable;
	this.name = name;
	this.damage = 0;
	}
	
	private TileType(int id, boolean collidable, String name, float damage) {
		this.id = id;
		this.collidable = collidable;
		this.angle = 0;
		this.name = name;
		this.damage = damage;
	}

	public int getId() {
		return id;
	}
	public int getAngle() {
		return this.angle;
	}

	public boolean isCollidable() {
		return collidable;
	}

	public String getName() {
		return name;
	}

	public float getDamage() {
		return damage;
	}
	
	private static HashMap<Integer, TileType> TileMap;
	
	static {
		TileMap = new HashMap<Integer, TileType>();
		for (TileType type : TileType.values()) {
			TileMap.put(type.getId(), type);
		}
	}
	public static TileType getTileTypeByID(int id) {
		return TileMap.get(id);
	}
	
	
}
