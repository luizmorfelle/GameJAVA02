package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;
import com.gcstudios.world.World;

public class Player extends Entity {

	public boolean right, up, left, down;
	public int maxIndex = 5, index = 0, frames, maxFrames = 5;
	public static boolean moved = false;
	public int left_dir = 0, right_dir = 1, up_dir = 2, down_dir = 3, dir = right_dir;
	public BufferedImage[] sprites_left, sprites_right, sprites_up, sprites_down;

	public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);

		sprites_left = new BufferedImage[maxIndex + 1];
		sprites_right = new BufferedImage[maxIndex + 1];
		sprites_up = new BufferedImage[maxIndex + 1];
		sprites_down = new BufferedImage[maxIndex + 1];

		for (int i = 0; i <= maxIndex; i++) {
			sprites_left[i] = Game.spritesheet.getSprite((i + 2) * 16, 16, 16, 16);
			sprites_right[i] = Game.spritesheet.getSprite((i + 2) * 16, 0, 16, 16);
			sprites_up[i] = Game.spritesheet.getSprite((i + 2) * 16, 32, 16, 16);
			sprites_down[i] = Game.spritesheet.getSprite((i + 2) * 16, 48, 16, 16);

		}

	}

	public void tick() {
		depth = 1;
		if (right && World.isFree((int) (x + speed), this.getY())) {
			moved = true;
			x += speed;
			dir = right_dir;
		} else if (left && World.isFree((int) (x - speed), this.getY())) {
			moved = true;
			x -= speed;
			dir = left_dir;
		}
		if (up && World.isFree(this.getX(), (int) (y - speed))) {
			moved = true;
			y -= speed;
			dir = up_dir;

		} else if (down && World.isFree(this.getX(), (int) (y + speed))) {
			moved = true;
			y += speed;
			dir = down_dir;
		}

		if (moved) {
			moved = false;
			frames++;
			if (frames > maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex)
					index = 0;
			}
		}
		collidingBigCoin();
		collidingCoin();
		collidingEnemy();
	}

	public void collidingCoin() {
		for (int i = 0; i < Game.coins.size(); i++) {
			Entity atual = Game.coins.get(i);
			if (atual instanceof Coin) {
				if (Entity.isColidding(this, atual)) {
					Game.coins.remove(atual);
				}
			}
		}
	}

	public void collidingBigCoin() {
		for (int i = 0; i < Game.coins.size(); i++) {
			Entity atual = Game.coins.get(i);
			if (atual instanceof BigCoin) {
				if (Entity.isColidding(this, atual)) {
					Enemy.ghostMode = true;
					Game.coins.remove(atual);
				}
			}
		}
	}

	public void collidingEnemy() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if (atual instanceof Enemy) {
				if (Entity.isColidding(this, atual)) {
					if (Enemy.ghostMode == false) {
						System.exit(1);
						//Perde uma chance e animação de morte
					}else {
						Game.entities.remove(atual);
					}
				}
			}
		}
	}

	public void render(Graphics g) {

		switch (dir) {
		case 0:
			g.drawImage(sprites_left[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			break;
		case 1:
			g.drawImage(sprites_right[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			break;
		case 2:
			g.drawImage(sprites_up[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			break;
		case 3:
			g.drawImage(sprites_down[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			break;
		}

	}

}
