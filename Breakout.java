/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		createBricks();
		createPaddle();
		startGame();
	}
	private void createBricks() {
		for ( int x = 0; x < NBRICKS_PER_ROW; x++ ) {
			for (int y = 0; y < NBRICK_ROWS; y++ ) {
				GRect brick = new GRect( getWidth() / 2 - ( NBRICKS_PER_ROW / 2 *BRICK_WIDTH ) + BRICK_WIDTH * x, BRICK_Y_OFFSET + ( y * BRICK_HEIGHT ), BRICK_WIDTH, BRICK_HEIGHT );
				brick.setFilled(true);
				brick.setFillColor(Color.red);
				brick.setColor(Color.white);
				switch(y) { 
				case 0: brick.setFillColor(Color.red);
					break;
				case 1: brick.setFillColor(Color.red);
					break;
				case 2: brick.setFillColor(Color.orange);
					break;
				case 3: brick.setFillColor(Color.orange);
					break;
				case 4: brick.setFillColor(Color.yellow);
					break;
				case 5: brick.setFillColor(Color.yellow);
					break;
				case 6: brick.setFillColor(Color.green);
					break;
				case 7: brick.setFillColor(Color.green);
					break;
				case 8: brick.setFillColor(Color.cyan);
					break;
				case 9: brick.setFillColor(Color.cyan);
					break;
				default: brick.setFillColor(Color.red);
					break;
				}
				
				add(brick);
			}
		}
	}
	private void createPaddle() {
		GRect paddle = new GRect( getWidth() / 2 - PADDLE_WIDTH / 2, getHeight() - PADDLE_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT );
		paddle.setColor(Color.black);
		add(paddle);
	}
	
	private void startGame() {
		add(ball);
		startMovement();
	}
	
	private void startMovement() {
		vy = 3.0;
		vx = rgen.nextDouble(1.0,3.0);
		if (rgen.nextBoolean(0.5)) vx = -vx;
		while(true) {
			ball.move(vx, vy);
			pause(500);
		}
	}

	private double vx, vy;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private GOval ball = new GOval( getWidth() / 2 - BALL_RADIUS / 2, getHeight() / 2 - BALL_RADIUS / 2, BALL_RADIUS, BALL_RADIUS );
}
