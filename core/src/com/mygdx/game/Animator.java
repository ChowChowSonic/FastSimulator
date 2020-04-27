package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator {
	ArrayList<TextureRegion> frameregions;
	Texture solotexture;
	int frame = 0;
	float playspeed;

	public Animator(Texture r, int width, int height, float speed) {
		playspeed=speed;
		if(width > 1 && height > 1) {
		TextureRegion region = new TextureRegion(r);
		TextureRegion[][] result = region.split(width, height);
		for(int i =0; i< result.length; i++) {
			for(int i2 = 0; i2 < result[i].length; i2++) {
				frameregions.add(result[i][i2]);
			}
		}
		}else solotexture=r;
		frameregions.trimToSize();
	}
	public Texture getFrame() {
		if(solotexture !=null) {
		if(frame < frameregions.size()) {
		return frameregions.get(frame).getTexture();
		}else {
			frame=0;
			return frameregions.get(frame).getTexture();
		}
		}else return solotexture;
	}
}