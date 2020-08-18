package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledGameMap extends GameMap{

	TiledMap world;
	CustomOrthRenderer worldRenderer;
	Music m;
	Music mloop;

	/**
	 * Creates a new TiledGameMap object from a file specified by filename.
	 * The location of the file being refrenced should be within the assets
	 * folder, found within the game's directory. 
	 * @param filename - Name of the file being used. INCLUDE THE EXTENSION.
	 */
	public TiledGameMap(String mapFileName, String musicFileName) {
		world = new TmxMapLoader().load(mapFileName);
		worldRenderer = new CustomOrthRenderer(world);

		//music stuff
		try {
		m = Gdx.audio.newMusic(Gdx.files.internal(musicFileName));
		m.setLooping(false);
		m.play();
		m.setOnCompletionListener(new Music.OnCompletionListener() {
			@Override
			public void onCompletion(Music aMusic) {  
				aMusic.setPosition(200);
				aMusic.play();
			}
		});
		}catch(Exception e) {
			System.out.println("Error loading music");
		}
	}

	/**
	 * Loads a default map from the assets directory. Will always load
	 * a file named "map.tmx"
	 */
	public TiledGameMap() {
		world = new TmxMapLoader().load("map.tmx");
		worldRenderer = new CustomOrthRenderer(world);
	}

	@Override
	public void render (OrthographicCamera camera, SpriteBatch batch) {
		worldRenderer.setView(camera);
		worldRenderer.render();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		super.render(camera, batch);
		batch.end();
	}

	@Override
	public void update (float delta) {
		super.update(delta);
	}

	@Override
	public void dispose () {
		world.dispose();
	}

	public TileType getTileTypeByLocation(int layer, int col, int row) {
		Cell cell = ((TiledMapTileLayer) world.getLayers().get(layer)).getCell(col, row);

		if (cell != null) {
			TiledMapTile tile = cell.getTile();

			if (tile != null) {
				int id = tile.getId();
				return TileType.getTileTypeByID(id);
			}
		}
		return null;
	}
	/**
	 * Returns a set of ints representing an (x,y) coordinate point that will be suitable for spawning a player
	 * the first int in the list will be the X position (that is, int[0]). While the second (int[1]) will be the Y
	 */
	public int[] getPlayerSpawnPoint() {
		int[] position = {0,0};
		for(int x = 0; x < this.getWidth(); x++) {
			for(int y = 0; y < this.getHeight(); y++) {
				TileType toptile = this.getTileTypeByLocation(1, x, y+1);
				TileType bottile = this.getTileTypeByLocation(1, x, y);
				if((toptile != null && bottile != null)) {
					if((!toptile.isCollidable() && !bottile.isCollidable())) {
						position[0]=x*TileType.TILE_SIZE;
						position[1]=y*TileType.TILE_SIZE;
						return position;
					}
				}
			}
		}

		return position;
	}
	
	/**
	 * Returns a set of ints representing an (x,y) coordinate point that will be suitable for spawning a player.
	 * The first int in the list will be the X position (that is, int[0]). While the second (int[1]) will be the Y.
	 * 
	 * The spawnpoint will always be within a box with its corners at x1, y1, x2 and y2 OR at (0,0) if no spawnpoint is found suitable
	 */
	public int[] getPlayerSpawnPoint(int x1, int y1, int x2, int y2) {
		int[] position = {0,0};
		for(int x = x1; x < x2; x++) {
			for(int y = y1; y < y2; y++) {
				TileType toptile = this.getTileTypeByLocation(1, x, y+1);
				TileType bottile = this.getTileTypeByLocation(1, x, y);
				if((toptile != null && bottile != null)) {
					if((!toptile.isCollidable() && !bottile.isCollidable())) {
						position[0]=x*TileType.TILE_SIZE;
						position[1]=y*TileType.TILE_SIZE;
						return position;
					}
				}
			}
		}

		return position;
	}
	@Override
	public int getWidth () {
		return ((TiledMapTileLayer) world.getLayers().get(0)).getWidth();
	}

	@Override
	public int getHeight () {
		return ((TiledMapTileLayer) world.getLayers().get(0)).getHeight();
	}

	@Override
	public int getLayers() {
		return world.getLayers().getCount();
	}

}
