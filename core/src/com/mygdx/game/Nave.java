package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class Nave {
    private static final TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("blueship.atlas"));
    private Rectangle naveArea;
    private Animation<Sprite> naveUP;
    private Animation<Sprite> naveDOWN;
    private Sprite naveIDLE;
    private Direction direction = Direction.idle;
    float speedY = 5;
    private float stateTime = 0;

    public Nave(float x, float y) {
        naveArea = new Rectangle(x, y, 100, 100);
        naveUP = new Animation<Sprite>(0.08f,
                atlas.createSprite("blueships1")

        );
        naveDOWN = new Animation<Sprite>(0.08f,
                atlas.createSprite("blueships1")
        );
        naveUP.setPlayMode(Animation.PlayMode.LOOP);
        naveDOWN.setPlayMode(Animation.PlayMode.LOOP);
        naveIDLE = new Sprite(atlas.createSprite("blueships1"));
    }
    public void setDirection(Direction direction){
        this.direction = direction;
    }
    public void draw(SpriteBatch batch) {
        Sprite s = naveIDLE;
        switch (direction) {
            case up:
                s = naveUP.getKeyFrame(stateTime, true);
                break;
            case down:
                s = naveDOWN.getKeyFrame(stateTime, true);
                break;
        }
        s.setSize(naveArea.width, naveArea.height);
        s.setPosition(naveArea.x, naveArea.y);
        s.draw(batch);
        stateTime += Gdx.graphics.getDeltaTime();
    }

    public void actualizarNave(float viewPortHeight) {
        switch (direction) {
            case up:
                if (naveArea.y + naveArea.height < viewPortHeight)
                naveArea.y += speedY;
                break;
            case down:
                if (naveArea.y > 0)
                naveArea.y -= speedY;
                break;

        }
    }
    enum Direction{
        up, down, idle;
    }

    public Rectangle getNaveArea() {
        return naveArea;
    }

}