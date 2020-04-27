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
		m = Gdx.audio.newMusic(Gdx.files.internal(musicFileName));
		m.play();
		m.setOnCompletionListener(new Music.OnCompletionListener() {
		    @Override
		    public void onCompletion(Music aMusic) {  
		       aMusic.setPosition(100);
		       aMusic.play();
		    }
		});
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
