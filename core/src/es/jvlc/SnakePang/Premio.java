package es.jvlc.SnakePang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

/**
 * Created by Josev on 13/07/2015.
 */
public class Premio extends Sprite {

    //Valor entero que indica el tipo de objeto del que se trata
        //1
        //2
        //3
    private int tipo;

    public Premio(TextureRegion imagen, int tip)
    {
        super(imagen);
        tipo = tip;
        Random random = new Random();
        setX(random.nextInt((int) Gdx.graphics.getWidth()-100)+50);
        setY(random.nextInt((int) Gdx.graphics.getHeight()-100)+50);
    }

    public int getTipo()
    {
        return tipo;
    }

    @Override
    public void draw(Batch batch)
    {
        rotate(5);
        super.draw(batch);
    }


}
