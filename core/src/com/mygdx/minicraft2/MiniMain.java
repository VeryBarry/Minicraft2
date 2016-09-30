package com.mygdx.minicraft2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MiniMain extends ApplicationAdapter {
	SpriteBatch batch;
	Texture tiles;
	TextureRegion up, down, right, left;
	Animation walk;
	float x, y, xv, yv, totalTime;
	boolean faceRight = true;
	boolean faceUp = true;

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
		down = grid[6][0];
		up = grid[6][1];
		right = grid[6][3];
		left = new TextureRegion(right);
		left.flip(true, false);
		//walk = new Animation(0.2f, );

	}

	@Override
	public void render () {
		totalTime += Gdx.graphics.getDeltaTime();
		move();

		TextureRegion guyDude;
		if (y > 0) {
			guyDude = up;
		}
		else if (xv != 0) {
			guyDude = right;
		}
		else {
			guyDude = down;
		}
		Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		if (faceRight) {
			batch.draw(guyDude, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}
		else {
			batch.draw(guyDude, x + DRAW_WIDTH, y, DRAW_WIDTH * -1, DRAW_HEIGHT);
		}
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		tiles.dispose();
	}
	public void move() {
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			yv = MAX_VELOCITY;
			faceUp = true;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			yv = MAX_VELOCITY * -1;
			faceUp = false;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			xv = MAX_VELOCITY;
			faceRight = true;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			xv = MAX_VELOCITY * -1;
			faceRight = false;
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
