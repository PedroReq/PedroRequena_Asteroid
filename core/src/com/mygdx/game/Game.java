package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Game extends ApplicationAdapter {
    SpriteBatch batch;
    Nave nave;
    private OrthographicCamera camera;
    public static final float viewportHeight = 450;
    public static final float viewportWidth = 800;
    private Music gameMusic;
    private static Sound collisionSound;
    private static Sound laserSound;
    private Random random;
    private List<Asteroide> asteroides;
    private List<Laser> lasers;
    private int vidas = 3;
    private BitmapFont fontPuntuacio, fontGameOver, fontVides, fontTornarAJugar;
    private float puntuacio;
    private boolean gameOverCondition = false;
    private Texture fondo;
    private Texture nave1, nave2, nave3;
    private boolean pantallaPulsada = false;

    private List<Texture> naves;
    @Override
    public void create() {

        puntuacio = 0;
        fondo = new Texture(Gdx.files.internal("fondo3.png"));
        collisionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.wav"));
        laserSound = Gdx.audio.newSound(Gdx.files.internal("laser1.wav"));
        random = new Random();
        asteroides = new ArrayList<>();
        lasers = new ArrayList<>();
        fontPuntuacio = new BitmapFont();
        fontPuntuacio.getData().setScale(2);
        fontPuntuacio.setColor(Color.YELLOW);
        fontGameOver = new BitmapFont();
        fontGameOver.getData().setScale(2);
        fontGameOver.setColor(Color.RED);
        fontTornarAJugar = new BitmapFont();
        fontTornarAJugar.getData().setScale(2);
        fontTornarAJugar.setColor(Color.GREEN);

        fontVides = new BitmapFont();
        fontVides.getData().setScale(2);
        fontVides.setColor(Color.YELLOW);
        nave1 = new Texture(Gdx.files.internal("blueship.png"));
        nave2 = new Texture(Gdx.files.internal("blueship.png"));
        nave3 = new Texture(Gdx.files.internal("blueship.png"));

        naves = new ArrayList<>();
        naves.add(nave1);
        naves.add(nave2);
        naves.add(nave3);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("BattleTheme.ogg"));
        gameMusic.setLooping(true);
        gameMusic.play();
        batch = new SpriteBatch();
        nave = new Nave(viewportWidth/6, viewportHeight/3);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, viewportWidth, viewportHeight);
        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (gameOverCondition){
                    pantallaPulsada = true;
                }
                Vector3 pos = new Vector3();
                pos.set(screenX, screenY,0);
                camera.unproject(pos);
                if(pos.x<viewportWidth/2 && pos.y>viewportHeight/2) nave.setDirection(Nave.Direction.up);
                else if(pos.x<viewportWidth/2 && pos.y<viewportHeight/2) nave.setDirection(Nave.Direction.down);
                else{
                    if (lasers.size() < 3 && !gameOverCondition){
                        crearLaser();
                    }
                }
                return super.touchDown(screenX, screenY, pointer, button);
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                nave.setDirection(Nave.Direction.idle);
                return super.touchUp(screenX, screenY, pointer, button);
            }
        });
    }

    @Override
    public void render () {
        ScreenUtils.clear(0.8f, 0.8f, 1, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        if (!gameOverCondition) {
            puntuacio+= Gdx.graphics.getDeltaTime();
            Rectangle naveRect = nave.getNaveArea();
        for (Asteroide a : asteroides){
            for(Laser l : lasers){
                if (a.getAsteroid().overlaps(l.getLsr()) && !a.isColisionado()){
                    puntuacio+=5;
                    a.setColisionado(true);
                    collisionSound.play();
                    lasers.remove(l);
                    break;
                }
            }
            if(naveRect.overlaps(a.getAsteroid()) && !a.isColisionado()){
                a.setColisionado(true);
                collisionSound.play();
                nave.getNaveArea().x = viewportWidth/6;
                nave.getNaveArea().y = viewportHeight/3;
                vidas--;
                naves.remove(vidas);
                if (vidas == 0){
                    gameMusic.stop();
                    gameMusic = Gdx.audio.newMusic(Gdx.files.internal("GameOverSound.mp3"));
                    gameMusic.setLooping(true);
                    gameMusic.play();
                    gameOverCondition = true;
                }
            }
        }
        batch.begin();
        batch.draw(fondo,0,0, viewportWidth,viewportHeight);
        nave.draw(batch);
        for(Asteroide a : asteroides){
            a.draw(batch);
        }
        for(Laser l : lasers){
            l.draw(batch);
        }
            String puntuacioText = "Puntos: " + (int)puntuacio;
            GlyphLayout glyphLayout = new GlyphLayout();
            glyphLayout.setText(fontPuntuacio, puntuacioText);
            fontPuntuacio.draw(batch, puntuacioText, (camera.viewportWidth - glyphLayout.width) / 2, camera.viewportHeight);
            int ind = 50;
            for (int i = 0; i < naves.size(); i++) {
                batch.draw(naves.get(i), camera.viewportWidth - ind, camera.viewportHeight - 50, 50, 50);
                ind +=50;

            }
            fontVides.draw(batch, "Vidas", camera.viewportWidth - 250, camera.viewportHeight);

        batch.end();
        nave.actualizarNave(viewportHeight);

        Iterator<Asteroide> iterator = asteroides.iterator();
        while (iterator.hasNext()) {
            Asteroide a = iterator.next();
            a.update();

            if (a.getAsteroid().x+a.getAsteroid().width <= 0){
                iterator.remove();
            }
        }
        Iterator<Laser> iteratorLsr = lasers.iterator();
        while (iteratorLsr.hasNext()) {
            Laser l = iteratorLsr.next();
            l.update();

            if (l.getLsr().x >= viewportWidth){
                iteratorLsr.remove();
            }
        }
        if (random.nextFloat() < 0.01f) {
            crearAsteroide();
        }
        }else{
            camera.update();
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            batch.draw(fondo,0,0, viewportWidth,viewportHeight);
            String gameOverText = "Game Over." + "\n" + "Puntuación: " + (int) puntuacio;
            fontGameOver.draw(batch, gameOverText, 320, 250);
            fontTornarAJugar.draw(batch, "Pulsa la pantalla para volver a jugar.", 200, 150);
            batch.end();
            if (pantallaPulsada) {
                pantallaPulsada = false;
                reiniciarPartida();
            }
        }
    }
    public void crearAsteroide() {
        float width = random.nextFloat() * 50 + 150;
        float height = width;
        float x = camera.viewportWidth + width;
        float minY = 0;
        float maxY = camera.viewportHeight;
        float y = random.nextFloat() * (maxY - minY) + minY;
        float speed = random.nextFloat() * 1 + 5;
        Asteroide asteroide = new Asteroide(x, y, width, height, speed);
        asteroides.add(asteroide);
    }
    public void crearLaser() {
        float x = nave.getNaveArea().x + nave.getNaveArea().width;
        float y = nave.getNaveArea().y + nave.getNaveArea().height/3;
        Laser laser = new Laser(x, y);
        lasers.add(laser);
        laserSound.play();
    }
    private void reiniciarPartida() {
        gameMusic.stop();
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("BattleTheme.ogg"));
        gameMusic.setLooping(true);
        gameMusic.play();
        puntuacio = 0;
        vidas = 3;
        naves.clear();
        naves.add(nave1);
        naves.add(nave2);
        naves.add(nave3);
        gameOverCondition = false;
        asteroides.clear();
        lasers.clear();
        nave.getNaveArea().x = viewportWidth / 6;
        nave.getNaveArea().y = viewportHeight / 3;
    }

    @Override
    public void dispose () {
        batch.dispose();
        nave1.dispose();
        nave2.dispose();
        nave3.dispose();
    }

}
