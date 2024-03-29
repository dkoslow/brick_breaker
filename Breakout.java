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

/** Speeds up movement with lower numbers */
	private static final int PAUSE_LENGTH = 10;

	public void run() {
		createBricks();
		createPaddle();
		lifeCount = new GLabel("Lives: " + lives + " left",10 ,10 );
		lifeCount.setFont("Times-12");
		add(lifeCount);
		addMouseListeners();
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
		paddle = new GRect( getWidth() / 2 - PADDLE_WIDTH / 2, getHeight() - PADDLE_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT );
		paddle.setColor(Color.black);
		add(paddle);
	}
	public void mouseMoved(MouseEvent e) {
		int x;
		x = 0;
		if(paddle.getX() <= 0) {
			if ((e.getX() - lastX) > 0) {
				paddleMovement = (e.getX() - lastX);
			} else {
				return;
			}
		} else if((PADDLE_WIDTH + paddle.getX()) >= getWidth()) {
			if ((e.getX() - lastX) < 0) {
				paddleMovement = (e.getX() - lastX);
			} else {
				return;
			}
		} else {
			paddleMovement = (e.getX() - lastX);
		}
		paddle.move(paddleMovement, 0);
		lastX = paddle.getX();
		x++;
	}
	private void startGame() {
		lifeCount.setLabel( "Lives: " + lives + " left" );
		ball = new GOval( (getWidth() - BALL_RADIUS) / 2, (getHeight() - BALL_RADIUS) / 2, BALL_RADIUS, BALL_RADIUS );
		add(ball);
		if(brickCounter <= 0) {
			winGame();
		} else if (lives <= 0) {
			loseGame();
		} else if(lives > 0) {
			if(brickCounter > 0) {
				startMovement();
			}
		} else {
			return;
		}
	}
	private void winGame() {
		remove(ball);
		vx = 0;
		vy = 0;
		GLabel endGame = new GLabel("You Win!");
		endGame.setFont("Times-42");
		add(endGame, (getWidth() - endGame.getWidth()) / 2, (getHeight() + endGame.getAscent()) / 2);
		pause(10000);
	}
	private void loseGame() {
		remove(ball);
		vx = 0;
		vy = 0;
		GLabel gameOver = new GLabel("You Lose.");
		gameOver.setFont("Times-42");
		add(gameOver, (getWidth() - gameOver.getWidth()) / 2, (getHeight() + gameOver.getAscent()) / 2);
		pause(10000);
	}
	private void startMovement() {
		vy = 3.0;
		vx = rgen.nextDouble(1.0,3.0);
		if (rgen.nextBoolean(0.5)) vx = -vx;
		while(true) {
			ball.move(vx, vy);
			pause(PAUSE_LENGTH * brickCounter / 100 );
			ballCollisionCheck();
			ballWallCheck();
		}
	}
	private void ballCollisionCheck() {
		ballLeftX = ball.getX();
		ballRightX = ball.getX()+ 2 * BALL_RADIUS;
		ballTopY = ball.getY();
		ballBottomY = ball.getY() + 2 * BALL_RADIUS;
		GObject collider = getCollidingObject();
		if(collider != null) {
			if(collider == paddle) {
				vy= -vy;
			} else {
				vy= -vy;
				remove(collider);
				brickCounter = brickCounter - 1;
			}
		}
		
	}
	private GObject getCollidingObject() {
		if(getElementAt(ballLeftX, ballTopY) != null) {
			return getElementAt(ballLeftX, ballTopY);
		} else if(getElementAt(ballRightX, ballTopY) != null) {
			return getElementAt(ballRightX, ballTopY);
		} else if(getElementAt(ballLeftX, ballBottomY) != null) {
			return getElementAt(ballLeftX, ballBottomY);
		} else if(getElementAt(ballRightX, ballBottomY) != null) {
			return getElementAt(ballRightX, ballBottomY);
		}else {
			return null;
		}
	}
	private void ballWallCheck() {
		if(ballLeftX <= 0) {
			vx = -vx;
		} else if(ballRightX >= getWidth()) {
			vx = -vx;
		} else if(ballTopY <= 0) {
			vy = -vy;
		} else if (ballBottomY >= getHeight()) {
			remove(ball);
			lives--;
			startGame();
		}
	}

	private double vx, vy;
	private double lastX;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private GOval ball;
	private GRect paddle;
	private double paddleMovement;
	private double ballLeftX, ballRightX, ballTopY, ballBottomY;
	private int brickCounter = 100;
	private int lives = 3;
	private GLabel lifeCount;
}
