package Entities;

import java.util.HashMap;

public class EntitySnapshot {
	private String type;
	private float x, y;
	HashMap<String, String> data;

	public EntitySnapshot() {
	}

	public EntitySnapshot(String type, float x, float y) {
		super();
		this.type = type;
		this.x = x;
		this.y = y;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Stores the data type entered into <code>Value</code> under a HashMap 
	 * Data type will be stored has a string however. 
	 * This may cause problems when trying to retrieve it in the future, 
	 * depending on how it is changed back. 
	 * Behaves identical to HashMap.put(key, "" + value)
	 * @param key
	 * @param value
	 */
	public void putData(String key, float value) {
		data.put(key, ""+value);
	}

	/**
	 * Stores the data type entered into <code>Value</code> under a HashMap 
	 * Data type will be stored has a string however. 
	 * This may cause problems when trying to retrieve it in the future, 
	 * depending on how it is changed back. 
	 * Behaves identical to HashMap.put(key, "" + value)
	 * @param key
	 * @param value
	 */
	public void putData(String key, int value) {
		data.put(key, ""+value);
	}

	/**
	 * Stores the data type entered into <code>Value</code> under a HashMap 
	 * Data type will be stored has a string however. 
	 * This may cause problems when trying to retrieve it in the future, 
	 * depending on how it is changed back. 
	 * Behaves identical to HashMap.put(key, "" + value)
	 * @param key
	 * @param value
	 */
	public void putData(String key, Boolean value) {
		data.put(key, ""+value);
	}

	/**
	 * Stores the data type entered into <code>Value</code> under a HashMap 
	 * Data type will be stored has a string however. 
	 * This may cause problems when trying to retrieve it in the future, 
	 * depending on how it is changed back. 
	 * Behaves identical to HashMap.put(key, "" + value)
	 * @param key
	 * @param value
	 */
	public void putData(String key, String value) {
		data.put(key, ""+value);
	}
	/**
	 * Retrieves data from the HashMap refrenced in <code>putData</code>.
	 * Defaultvalue is returned if the key is not found or an incorrect
	 * primitive type is located at the specified key. 
	 * This is overloaded so that defaultvalue is always the same primitive type
	 * as whatever you're returning
	 * @param key
	 * @param defaultvalue
	 * @return
	 */
	public int getData(String key, int defaultvalue) {
		if(data.containsKey(key)) {
			try {
				return Integer.parseInt(data.get(key));
			} catch(Exception e) {
				return defaultvalue;
			}
		}else return defaultvalue;
	}
	
	/**
	 * Retrieves data from the HashMap refrenced in <code>putData</code>.
	 * Defaultvalue is returned if the key is not found or an incorrect
	 * primitive type is located at the specified key. 
	 * This is overloaded so that defaultvalue is always the same primitive type
	 * as whatever you're returning
	 * @param key
	 * @param defaultvalue
	 * @return
	 */
	public float getData(String key, float defaultvalue) {
		if(data.containsKey(key)) {
			try {
				return Float.parseFloat(data.get(key));
			} catch(Exception e) {
				return defaultvalue;
			}
		}else return defaultvalue;
	}
	/**
	 * Retrieves data from the HashMap refrenced in <code>putData</code>.
	 * Defaultvalue is returned if the key is not found or an incorrect
	 * primitive type is located at the specified key. 
	 * This is overloaded so that defaultvalue is always the same primitive type
	 * as whatever you're returning
	 * @param key
	 * @param defaultvalue
	 * @return
	 */
	public boolean getData(String key, Boolean defaultvalue) {
		if(data.containsKey(key)) {
			try {
				return Boolean.getBoolean(data.get(key));
			} catch(Exception e) {
				return defaultvalue;
			}
		}else return defaultvalue;
	}
	
	/**
	 * Retrieves data from the HashMap refrenced in <code>putData</code>.
	 * Defaultvalue is returned if the key is not found or an incorrect
	 * primitive type is located at the specified key. 
	 * This is overloaded so that defaultvalue is always the same primitive type
	 * as whatever you're returning
	 * @param key
	 * @param defaultvalue
	 * @return
	 */
	public String getData(String key, String defaultvalue) {
		if(data.containsKey(key)) {
			try {
				return data.get(key);
			} catch(Exception e) {
				return defaultvalue;
			}
		}else return defaultvalue;
	}
}//ends class
