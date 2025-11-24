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
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

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

    Array<Sprite> enemies;

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
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            //TODO: spawn beam and make it shoot upwards.
        }
    }

    private void logic() {
        float gameWidth = viewport.getWorldWidth();
        playerSprite.setX(MathUtils.clamp(playerSprite.getX(), 0, gameWidth - playerSprite.getWidth()));

        float delta = Gdx.graphics.getDeltaTime();
        for (Sprite enemy: enemies){
            enemy.translateX(2f * delta);
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
        playerSprite.draw(spriteBatch);

        for (Sprite enemy : enemies){
            enemy.draw(spriteBatch);
        }

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

    private void createBeam(){ // TODO: konczenie funkcji
        float playerWidth = playerSprite.getWidth();
        float playerHeight = playerSprite.getHeight();
    }
}
