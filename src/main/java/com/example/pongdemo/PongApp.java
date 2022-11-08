package com.example.pongdemo;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.addUINode;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;
import static com.almasb.fxgl.dsl.FXGL.getUIFactoryService;
import static com.almasb.fxgl.dsl.FXGL.getip;
import static com.almasb.fxgl.dsl.FXGL.onKey;
import static com.example.pongdemo.Util.bottomBounceCheck;
import static com.example.pongdemo.Util.getBallSize;
import static com.example.pongdemo.Util.getPaddle1;
import static com.example.pongdemo.Util.getPaddle2;
import static com.example.pongdemo.Util.getPaddleHeight;
import static com.example.pongdemo.Util.getPaddleSpeed;
import static com.example.pongdemo.Util.getPaddleWidth;
import static com.example.pongdemo.Util.player1BounceCheck;
import static com.example.pongdemo.Util.player1ScoreCheck;
import static com.example.pongdemo.Util.player2BounceCheck;
import static com.example.pongdemo.Util.player2ScoreCheck;
import static com.example.pongdemo.Util.setBall;
import static com.example.pongdemo.Util.setPaddle1;
import static com.example.pongdemo.Util.setPaddle2;
import static com.example.pongdemo.Util.spawnBall;
import static com.example.pongdemo.Util.spawnBat;
import static com.example.pongdemo.Util.topBounceCheck;
import static com.example.pongdemo.Util.updateBall;

public class PongApp extends GameApplication
{
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("Pong");
    }

    @Override
    protected void initInput() {
        onKey(KeyCode.Z, () -> getPaddle1().translateY(-getPaddleSpeed()));
        onKey(KeyCode.S, () -> getPaddle1().translateY(getPaddleSpeed()));
        onKey(KeyCode.UP, () -> getPaddle2().translateY(-getPaddleSpeed()));
        onKey(KeyCode.DOWN, () -> getPaddle2().translateY(getPaddleSpeed()));
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score1", 0);
        vars.put("score2", 0);
    }

    @Override
    protected void initGame() {
        setPaddle1(spawnBat(0, getAppHeight()/2.0 - getPaddleHeight()/2.0));
        setPaddle2(spawnBat(getAppWidth() - getPaddleWidth(), getAppHeight()/2.0 - getPaddleHeight()/2.0));
        setBall(spawnBall(getAppWidth()/2.0 - getBallSize()/2.0, getAppHeight()/2.0 - getBallSize()/2.0));
    }

    @Override
    protected void initUI() {
        Text textScore1 = getUIFactoryService().newText("", Color.BLACK, 22);
        Text textScore2 = getUIFactoryService().newText("", Color.BLACK, 22);

        textScore1.textProperty().bind(getip("score1").asString());
        textScore2.textProperty().bind(getip("score2").asString());

        addUINode(textScore1, 10, 50);
        addUINode(textScore2, getAppWidth() - 30, 50);
    }

    @Override
    protected void onUpdate(double tpf) {
        updateBall();

        player1BounceCheck();
        player2BounceCheck();

        player1ScoreCheck();
        player2ScoreCheck();

        topBounceCheck();
        bottomBounceCheck();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
