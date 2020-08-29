package Entities;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.mygdx.game.GameMap;

public enum EntityType {
	PLAYER("player", Player.class, 14, 26, 40),
	ENEMY("enemy", Enemy.class, 20, 20, 100),
	POSTERBOARD("posterboard", PosterBoard.class, 48, 36, 0),
	SPRING("spring", Spring.class, 15, 20, 100);
	private String id;
	private Class LoaderClass;
	private int width, height;
	private float weight;
	
	private EntityType(String id, Class LoaderClass, int width, int height, float weight) {
		this.id = id;
		this.LoaderClass = LoaderClass;
		this.width = width;
		this.height = height;
		this.weight = weight;
	}

	public String getId() {
		return id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public float getWeight() {
		return weight;
	}
	
	public static Entity CreateEntityusingSnapshot(EntitySnapshot e, GameMap world) {
		EntityType type = EntityTypes.get(e.getType());
		try {
			@SuppressWarnings("unchecked")
			Entity entity = ClassReflection.newInstance(type.LoaderClass);
			entity.create(e, type, world);
			return entity;
		} catch (ReflectionException en) {
			Gdx.app.error("Entity Loader", "Could not load entity of type " + type.id);
			return null;
		}
	}
	
	private static HashMap<String, EntityType> EntityTypes;
	
	static {
		EntityTypes = new HashMap<String, EntityType>();
		for(EntityType type : EntityType.values()) {
			EntityTypes.put(type.id, type);
		}
	}
}
