package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class Asteroide {

    private static final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("asteroides.atlas"));
    private static final TextureAtlas atlasColisio = new TextureAtlas(Gdx.files.internal("explosion.atlas"));
    private Rectangle asteroid;
    private float speedX;
    private Animation<Sprite> animAsteroide;
    private Animation<Sprite> animColisio;
    private float stateTime = 0;
    private Sprite s;
    private boolean colisionado = false;

    public Asteroide(float x, float y, float width, float height, float speedX) {
        asteroid = new Rectangle(x,y,width, height);

        this.speedX = speedX;
        animAsteroide = new Animation<Sprite>(0.08f,
                atlas.createSprite("Asteroid-A-10-00"),
                atlas.createSprite("Asteroid-A-10-01"),
                atlas.createSprite("Asteroid-A-10-02"),
                atlas.createSprite("Asteroid-A-10-03"),
                atlas.createSprite("Asteroid-A-10-04"),
                atlas.createSprite("Asteroid-A-10-05"),
                atlas.createSprite("Asteroid-A-10-06"),
                atlas.createSprite("Asteroid-A-10-07"),
                atlas.createSprite("Asteroid-A-10-08"),
                atlas.createSprite("Asteroid-A-10-09"),
                atlas.createSprite("Asteroid-A-10-10"),
                atlas.createSprite("Asteroid-A-10-11"),
                atlas.createSprite("Asteroid-A-10-12"),
                atlas.createSprite("Asteroid-A-10-13"),
                atlas.createSprite("Asteroid-A-10-14"),
                atlas.createSprite("Asteroid-A-10-15"),
                atlas.createSprite("Asteroid-A-10-16"),
                atlas.createSprite("Asteroid-A-10-17"),
                atlas.createSprite("Asteroid-A-10-18"),
                atlas.createSprite("Asteroid-A-10-19"),
                atlas.createSprite("Asteroid-A-10-20"),
                atlas.createSprite("Asteroid-A-10-21"),
                atlas.createSprite("Asteroid-A-10-22"),
                atlas.createSprite("Asteroid-A-10-23"),
                atlas.createSprite("Asteroid-A-10-24"),
                atlas.createSprite("Asteroid-A-10-25"),
                atlas.createSprite("Asteroid-A-10-26"),
                atlas.createSprite("Asteroid-A-10-27"),
                atlas.createSprite("Asteroid-A-10-28"),
                atlas.createSprite("Asteroid-A-10-29"),
                atlas.createSprite("Asteroid-A-10-30"),
                atlas.createSprite("Asteroid-A-10-31"),
                atlas.createSprite("Asteroid-A-10-32"),
                atlas.createSprite("Asteroid-A-10-33"),
                atlas.createSprite("Asteroid-A-10-34"),
                atlas.createSprite("Asteroid-A-10-35"),
                atlas.createSprite("Asteroid-A-10-36"),
                atlas.createSprite("Asteroid-A-10-37"),
                atlas.createSprite("Asteroid-A-10-38"),
                atlas.createSprite("Asteroid-A-10-39"),
                atlas.createSprite("Asteroid-A-10-40"),
                atlas.createSprite("Asteroid-A-10-41"),
                atlas.createSprite("Asteroid-A-10-42"),
                atlas.createSprite("Asteroid-A-10-43"),
                atlas.createSprite("Asteroid-A-10-44"),
                atlas.createSprite("Asteroid-A-10-45"),
                atlas.createSprite("Asteroid-A-10-46"),
                atlas.createSprite("Asteroid-A-10-47"),
                atlas.createSprite("Asteroid-A-10-48"),
                atlas.createSprite("Asteroid-A-10-49"),
                atlas.createSprite("Asteroid-A-10-50"),
                atlas.createSprite("Asteroid-A-10-51"),
                atlas.createSprite("Asteroid-A-10-52"),
                atlas.createSprite("Asteroid-A-10-53"),
                atlas.createSprite("Asteroid-A-10-54"),
                atlas.createSprite("Asteroid-A-10-55"),
                atlas.createSprite("Asteroid-A-10-56"),
                atlas.createSprite("Asteroid-A-10-57"),
                atlas.createSprite("Asteroid-A-10-58"),
                atlas.createSprite("Asteroid-A-10-59")

        );
        animAsteroide.setPlayMode(Animation.PlayMode.LOOP);
        animColisio = new Animation<Sprite>(0.08f,
                atlasColisio.createSprite("bubble_explo2")

        );
        s = animAsteroide.getKeyFrame(stateTime,true);
    }

    public void update() {

        asteroid.x -= speedX;

    }
    public void draw(SpriteBatch batch) {
        Sprite s;
        if (!colisionado){
             s = animAsteroide.getKeyFrame(stateTime,true);
        }else{
            s = animColisio.getKeyFrame(stateTime,false);
        }

        s.setPosition(asteroid.x, asteroid.y);
        s.setSize(asteroid.width, asteroid.height);
        s.draw(batch);
        stateTime += Gdx.graphics.getDeltaTime();
    }

    public Rectangle getAsteroid() {
        return asteroid;
    }

    public boolean isColisionado() {
        return colisionado;
    }

    public void setColisionado(boolean colisionado) {
        this.colisionado = colisionado;
    }

}
