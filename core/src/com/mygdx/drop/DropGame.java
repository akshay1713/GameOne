package com.mygdx.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;


public class DropGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture img;
    private Texture dropImage;
    private Texture bucketImage;
    private Music rainMusic;
    private Sound dropSound;
    private OrthographicCamera camera;
    private Rectangle bucket;
    private Vector3 touchPosition;
    private Array<Rectangle> rainDrops;
    private long lastDropTime;
    private State state;
    private BitmapFont scoreFont;
    private int score;
    private String scoreText;
    private BitmapFont gameOverFont;
    private static final String fontCharacters = "GAMEOVER";

    @Override
    public void create () {
        dropImage = new Texture(Gdx.files.internal("droplet.png"));
        bucketImage = new Texture(Gdx.files.internal("bucket.png"));

        dropSound = Gdx.audio.newSound(Gdx.files.internal("water_drop.wav"));

        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        rainMusic.setLooping(true);
        rainMusic.play();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        batch = new SpriteBatch();

        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;

        touchPosition = new Vector3();
        rainDrops = new Array<Rectangle>();
        spawnRaindrop();
        score = 0;
        scoreText = "SCORE: " + score;
        scoreFont = new BitmapFont();
        state = State.RUN;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("OpenSans-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        gameOverFont = generator.generateFont(parameter);
        gameOverFont.setUseIntegerPositions(false);
    }

    @Override
    public void render () {
        switch (state)
        {
            case RUN:
                update();
                break;
            case PAUSE:
                break;
            case RESUME:
                break;
            default:
                break;
        }
    }

    private void update () {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        drawBatch();

        updateBucket();

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
            spawnRaindrop();
        }

        updateRaindrops();
    }

    private void drawBatch() {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(bucketImage, bucket.x, bucket.y);
        for (Rectangle raindrop : rainDrops) {
            batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        scoreFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        scoreFont.draw(batch, scoreText, 25, 100);
        if (state == State.STOPPED) {
            gameOverFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            gameOverFont.draw(batch, "Game Over", 320, 240);
            rainMusic.stop();
        }
        batch.end();
    }

    private void updateBucket() {
        if (Gdx.input.isTouched()) {
            touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPosition);
            bucket.x = Math.round(touchPosition.x) - 64 / 2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            bucket.x -= 200 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            bucket.x += 200 * Gdx.graphics.getDeltaTime();
        }

        if (bucket.x < 0) {
            bucket.x = 800 - 64;
        } else if (bucket.x > 800 - 64) {
            bucket.x = 0;
        }
    }


    private void updateRaindrops(){
        Iterator<Rectangle> iter = rainDrops.iterator();
        while(iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if(raindrop.y + 64 < 0) {
                iter.remove();
                state = State.STOPPED;
                update();
            }
            if(raindrop.overlaps(bucket)) {
                dropSound.play();
                iter.remove();
                updateScore();
            }
        }
    }

    private void updateScore() {
        score++;
        scoreText = "SCORE: " + score;
    }

    private void pauseResumeToggle() {
        if(state == State.PAUSE) {
            state = State.RESUME;
            rainMusic.play();
            return;
        }
        state = State.PAUSE;
        rainMusic.pause();
    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800-64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        rainDrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void dispose () {
        batch.dispose();
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }
}
