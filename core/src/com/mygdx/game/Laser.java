package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class Laser {
    private static final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("laser.atlas"));
    private Animation<Sprite> animLaser;
    private Rectangle lsr;
    private float speedX = 4;
    private float stateTime = 0;

    public Laser(float x, float y) {
        lsr = new Rectangle(x, y, 150, 50);
        animLaser = new Animation<Sprite>(0.08f,
                atlas.createSprite("green_laser")

        );
    }
    public void update() {

        lsr.x += speedX;

    }

    public void draw(SpriteBatch batch){
        Sprite s = animLaser.getKeyFrame(stateTime,true);
        s.setPosition(lsr.x, lsr.y);
        s.setSize(lsr.width, lsr.height);
        s.draw(batch);

        stateTime += Gdx.graphics.getDeltaTime();
    }

    public Rectangle getLsr() {
        return lsr;
    }

}
