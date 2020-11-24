package com.gcstudios.entities;

import java.awt.image.BufferedImage;

public class BigCoin extends Coin{

	public BigCoin(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		depth = 1;
	}

}
