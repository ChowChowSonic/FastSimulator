package com.mygdx.game;

import java.awt.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;

public class CustomButton {

	int width, height;
	int x,y;
	String text;
	static ShapeRenderer g;
	BitmapFont font;
	SpriteBatch batch;

	public CustomButton(int x, int y, int width, int height, String text) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		if(g == null) {
			g = new ShapeRenderer();
			g.setAutoShapeType(true);
		}
		if(font == null) {
			font = new BitmapFont();
		}
		if(batch == null) {
			batch = new SpriteBatch();
		}
	}

	/**
	 * This literally does nothing. Override it somewhere idk. 
	 */
	public void onclick() {

	}

	public void draw() {
		g.begin();
		g.rect(x, y, x, y, width, height, 1, 1, 0, Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE);
		g.end();
		batch.begin();
		BitmapFontCache renderedtext = new BitmapFontCache(font);

		{//copied from somewhere...
			// Set the text normally. The position must be 0, otherwise the scaling won't work.
			// If you don't want perspective scaling, you can set x,y to textPosX,textPosY directly and skip the next part.
			renderedtext.setText(text, 0, 0, 0, text.length(), width, height, false, text);

			// Scaling factor
			final float fontScale = (float) (text.length());
			// Go through prepared vertices of the font cache and do necessary transformation
			final int regionCount = font.getRegions().size;
			for (int page = 0; page < regionCount; page++) {
				final int vertexCount = renderedtext.getVertexCount(page);
				final float[] vertices = renderedtext.getVertices(page);
				for (int v = 0; v < vertexCount; v += 5) {
					// This is why the text position must be 0 - otherwise the scaling would move the text
					vertices[v] = vertices[v] * fontScale + x;
					vertices[v + 1] = vertices[v + 1] * fontScale + y+height;
				}
				renderedtext.draw(batch);
				//font.draw(batch, text, x, y+height, 0, text.length(), 0, height, true, text);
				batch.end();
			}
		}//End of copied code
	}

	public void remove() {
		g.flush();
		g.dispose();
	}

}
