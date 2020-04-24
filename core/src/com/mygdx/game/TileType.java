package com.mygdx.game;

import java.util.HashMap;

public enum TileType {
	
	/* When working with a TMX file, make sure the ID of each block used  
	 * corresponds to the ID of the block here PLUS ONE
	 */
	GRASS(1, true, "Grass"),
	DIRT(2, true, "Dirt"),
	SKY(3, false, "Sky"),
	LAVA(4, true, "Lava"),
	CLOUD(5, true, "Cloud"),
	STONE(6, true, "Stone");
	
	public static final int TILE_SIZE = 16;
	private int id;
	private boolean collidable;
	private String name;
	private float damage = 0;
	
	private TileType(int id, boolean collidable, String name) {
	this.id = id;
	this.collidable = collidable;
	this.name = name;
	this.damage = 0;
	}
	
	private TileType(int id, boolean collidable, String name, float damage) {
		this.id = id;
		this.collidable = collidable;
		this.name = name;
		this.damage = damage;
	}

	public int getId() {
		return id;
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
