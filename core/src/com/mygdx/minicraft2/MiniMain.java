package com.mygdx.minicraft2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;

public class MiniMain extends ApplicationAdapter {
	SpriteBatch batch;
	Texture tiles;
	TextureRegion up, up2, down, down2, right, right2, left, left2;
	Animation moveUp, moveDown, moveRight, moveLeft;
	float x, y, xv, yv, totalTime;
	String facing = "down";

	static final int WIDTH = 18;
	static final int HEIGHT = 26;
	static final int DRAW_WIDTH = WIDTH * 3;
	static final int DRAW_HEIGHT = HEIGHT *3;
	static final float MAX_VELOCITY = 100;
	static final float FRICTION = 0.94f;

	@Override
	public void create () {
		batch = new SpriteBatch();
		tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
		up = grid[6][1];
		up2 = new TextureRegion(grid[6][1]);
		up2.flip(true, false);
		down = grid[6][0];
		down2 = new TextureRegion(grid[6][0]);
		down2.flip(true, false);
		right = grid[6][3];
		right2 = grid[6][2];
		right.flip(true, false);
		right2.flip(true, false);
		left = new TextureRegion(right);
		left2 = new TextureRegion(right2);
		left.flip(true, false);
		left2.flip(true, false);
		moveRight = new Animation(.2f, left, left2);
		moveLeft = new Animation(.2f, right, right2);
		moveUp = new Animation(.2f, up, up2);
		moveDown = new Animation(.2f, down, down2);
	}
	@Override
	public void render () {
		totalTime += Gdx.graphics.getDeltaTime();
		move();

		TextureRegion guyDude = new TextureRegion();
		if(facing.equals("right")){
			if(xv != 0){
				guyDude = moveRight.getKeyFrame(totalTime,true);
			}
			else {
				guyDude = right;
			}
		} if(facing.equals("left")){
			if(xv != 0){
				guyDude = moveLeft.getKeyFrame(totalTime,true);
			}
			else {
				guyDude = left;
			}
		} if(facing.equals("up")){
			if(yv != 0){
				guyDude = moveUp.getKeyFrame(totalTime,true);
			}
			else {
				guyDude = up;
			}
		} if(facing.equals("down")){
			if(yv != 0){
				guyDude = moveDown.getKeyFrame(totalTime,true);
			}
			else {
				guyDude = down;
			}
		}
		Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(guyDude, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		batch.end();

		if (y < 0){
			y = Gdx.graphics.getHeight();
		}
		if (y > Gdx.graphics.getHeight()){
			y = 0;
		}
		if (x < 0){
			x = Gdx.graphics.getWidth();
		}
		if (x > Gdx.graphics.getWidth()){
			x = 0;
		}
	}
	@Override
	public void dispose () {
		batch.dispose();
		tiles.dispose();
	}
	public void move() {
		if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			yv = MAX_VELOCITY * 5;
			facing = "up";
		} else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			yv = MAX_VELOCITY * 5 * -1;
			facing = "down";
		} else if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			xv = MAX_VELOCITY * 5;
			facing = "right";
		} else if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			xv = MAX_VELOCITY * 5 * -1;
			facing = "left";
		} else if(Gdx.input.isKeyPressed(Input.Keys.W)){
			yv = MAX_VELOCITY;
			facing = "up";
		} else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			yv = MAX_VELOCITY * -1;
			facing = "down";
		} else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			xv = MAX_VELOCITY;
			facing = "right";
		} else if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			xv = MAX_VELOCITY * -1;
			facing = "left";
		}

		x += xv * Gdx.graphics.getDeltaTime();
		y += yv * Gdx.graphics.getDeltaTime();

		xv = decelerate(xv);
		yv = decelerate(yv);
	}
	public float decelerate(float velocity) {
		velocity *= FRICTION;
		if(Math.abs(velocity) < 80){
			velocity = 0;
		}
		return velocity;
	}
}