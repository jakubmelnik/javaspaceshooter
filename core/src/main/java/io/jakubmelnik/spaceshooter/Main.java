package io.jakubmelnik.spaceshooter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Os;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    Texture backgroundTexture;
    Texture playerTexture;
    Texture enemyTexture;
    Texture beamTexture;
    Texture enemyBeamTexture;

    SpriteBatch spriteBatch;
    FitViewport viewport;

    Sprite playerSprite;

    Array<Sprite> beams;
    Array<Sprite> enemies;
    long cooldownLeft=0;

    @Override
    public void create() {
        backgroundTexture = new Texture("background.png");
        playerTexture = new Texture("playership.png");
        enemyTexture = new Texture("enemy.png");
        enemyBeamTexture = new Texture("enemybeam.png");
        beamTexture = new Texture("beam.png");

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(16, 10);

        playerSprite = new Sprite(playerTexture);
        playerSprite.setSize(1, 1);
        playerSprite.setY(1);
        playerSprite.setX(7);

        enemies = new Array<>();
        beams = new Array<>();
        createEnemies();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    private void input() {

        float speed = 9;
        float delta = Gdx.graphics.getDeltaTime();


        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            playerSprite.translateX(speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            playerSprite.translateX(-speed * delta);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            long timeNow = System.currentTimeMillis();
            if (timeNow - cooldownLeft >= 1000){
                cooldownLeft = timeNow;
                createBeam();
            } else {
                System.out.println("cooldown");
            }
        }
    }

    private void logic() {
        float gameWidth = viewport.getWorldWidth();
        playerSprite.setX(MathUtils.clamp(playerSprite.getX(), 0, gameWidth - playerSprite.getWidth()));

        float delta = Gdx.graphics.getDeltaTime();
        for (Sprite enemy: enemies){
            enemy.translateX(2f * delta);
        }
        for (Sprite beam: beams){
            beam.translateY(8f * delta);
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float gameWidth = viewport.getWorldWidth();
        float gameHeight = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, gameWidth, gameHeight);

        for (Sprite enemy : enemies){
            enemy.draw(spriteBatch);
        }
        for (Sprite beam : beams){
            beam.draw(spriteBatch);
        }
        playerSprite.draw(spriteBatch);
        spriteBatch.end();
    }

    private void createEnemies(){
        float enemyHeight = 1, gameHeight = viewport.getWorldHeight();

        Sprite enemy = new Sprite(enemyTexture);
        enemy.setSize(1,1);
        enemy.setX(0);
        enemy.setY(gameHeight-enemyHeight);
        enemies.add(enemy);
    }

    private void createBeam(){
        Sprite beam = new Sprite(beamTexture);
        beam.setSize(0.2f,0.6f);
        beam.setX(playerSprite.getX()+(playerSprite.getWidth()/2)-(beam.getWidth()/2));
        beam.setY(playerSprite.getY());
        beams.add(beam);
    }
}
