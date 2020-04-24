package com.mygdx.game;

import Entities.Entity;

public class CCDLine {//Constant Collision Detection Line

	public float startx, endx, starty, endy, slope;

	public CCDLine(float startx,float endx,float starty,float endy) {
		this.startx = startx;
		this.endx = endx;
		this.starty = starty;
		this.endy = endy;
		if(startx-endx !=0) {
			this.slope = (starty-endy)/(startx-endx);
		}else if(starty-endy > 0) {
			slope = (float) Math.pow(2, 32);//Because apparently "ComPUteRS caNt UndERStAnD THe CONcePt oF InfIniTy"
		}else {
			slope = (float) -Math.pow(2, 32);
		}
	}

	public boolean isonline(Entity e) {
		float x = e.getX();
		float y = e.getY();
		int height = e.getHeight();
		int width = e.getWidth();
		if((this.startx<= x+width && x <=this.endx) && (this.starty<= y+height && y <=this.endy)) {
			float yofline = this.slope*(x-this.startx) + this.starty;//(y-y1)=m(x-x1)
			if(yofline <= (y+height) && (y) <= yofline) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		return "Start X:"+startx+", End X:"+endx+", Start Y"+starty+", End Y:"+endy;
	}

}
