package com.example.pongdemo;

import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.inc;

/**
 * Utility class containing all the data and entities for the game.
 */
public final class Util
{
    private static final int PADDLE_WIDTH = 30;
    private static final int PADDLE_HEIGHT = 100;
    private static final int BALL_SIZE = 20;
    private static final int PADDLE_SPEED = 5;
    private static final int BALL_SPEED = 2;

    private static Entity paddle1;
    private static Entity paddle2;
    private static Entity ball;

    private static Point2D velocity;

    //region getters/setters
    public static int getPaddleWidth() {
        return PADDLE_WIDTH;
    }

    public static int getPaddleHeight() {
        return PADDLE_HEIGHT;
    }

    public static int getBallSize() {
        return BALL_SIZE;
    }

    public static int getPaddleSpeed() {
        return PADDLE_SPEED;
    }

    public static int getBallSpeed() {
        return BALL_SPEED;
    }

    public static Entity getPaddle1() {
        return paddle1;
    }

    public static Entity getPaddle2() {
        return paddle2;
    }

    public static void setPaddle1(Entity paddle1) {
        Util.paddle1 = paddle1;
    }

    public static void setPaddle2(Entity paddle2) {
        Util.paddle2 = paddle2;
    }

    public static void setBall(Entity ball) {
        Util.ball = ball;
    }
    //endregion

    //region spawning methods
    /**
     * Spawns player 1 at x and y coordinates
     * @param x - coordinate
     * @param y - coordinate
     * @return - FXGL entity to build
     */
    public static Entity spawnBat(double x, double y) {
        return entityBuilder()
                .at(x, y)
                .viewWithBBox(new Rectangle(getPaddleWidth(), getPaddleHeight()))
                .buildAndAttach();
    }

    /**
     * Spawns player 2 at x and y coordinates
     * @param x - coordinate
     * @param y - coordinate
     * @return - FXGL entity to build
     */
    public static Entity spawnBall(double x, double y) {
        return entityBuilder()
                .at(x, y)
                .viewWithBBox(new Rectangle(getBallSize(), getBallSize()))
                .with("velocity", new Point2D(getBallSpeed(), getBallSpeed()))
                .buildAndAttach();
    }

    /**
     * Returns the ball to the screen center
     */
    public static void resetBall() {
        ball.setPosition(getAppWidth()/2.0 - getBallSize()/2.0, getAppHeight()/2.0 - getBallSize()/2.0);
        ball.setProperty("velocity", new Point2D(getBallSpeed(), getBallSpeed()));
    }
    //endregion

    /**
     * Moves ball to the next position on frame update.
     */
    public static void updateBall() {
        velocity = ball.getObject("velocity");
        ball.translate(velocity);
    }

    /**
     * Bounces the ball of player 1 if it hits
     */
    public static void player1BounceCheck() {
        if(ball.getX() == paddle1.getRightX()
                && ball.getY() < paddle1.getBottomY()
                && ball.getBottomY() > paddle1.getY())
            ball.setProperty("velocity", new Point2D(-velocity.getX(), velocity.getY()));
    }

    /**
     * Bounces the ball of player 2 if it hits
     */
    public static void player2BounceCheck() {
        if (ball.getRightX() == paddle2.getX()
                && ball.getY() < paddle2.getBottomY()
                && ball.getBottomY() > paddle2.getY()) {
            ball.setProperty("velocity", new Point2D(-velocity.getX(), velocity.getY()));
        }
    }

    /**
     * If ball hits player 2 side
     */
    public static void player1ScoreCheck() {
        if(ball.getRightX() >= getAppWidth())
        {
            inc("score1", +1);
            resetBall();
        }
    }

    /**
     * If ball hits player 1 side
     */
    public static void player2ScoreCheck() {
        if(ball.getX() <= 0) {
            inc("score2", +1);
            resetBall();
        }
    }

    /**
     * If ball hits the top of the screen
     */
    public static void topBounceCheck() {
        if (ball.getY() <= 0) {
            ball.setY(0);
            ball.setProperty("velocity", new Point2D(velocity.getX(), -velocity.getY()));
        }
    }

    /**
     * If ball hits the bottom of the screen
     */
    public static void bottomBounceCheck() {
        if (ball.getBottomY() >= getAppHeight()) {
            ball.setY(getAppHeight() - getBallSize());
            ball.setProperty("velocity", new Point2D(velocity.getX(), -velocity.getY()));
        }
    }
}
