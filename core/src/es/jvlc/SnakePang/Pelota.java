package es.jvlc.SnakePang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Josev on 12/07/2015.
 */
public class Pelota extends Sprite {

    private float velx;
    private float vely;
    private float vDefecto;

    public Pelota(Texture imagen, float x, float y)
    {
        super(new TextureRegion(imagen, 72, 2, 33, 33));
        vDefecto = Gdx.graphics.getHeight()* 0.006f;
        setX(x);
        setY(y);
        velx = vDefecto;
        vely = vDefecto;
    }

    @Override
    public void draw(Batch batch)
    {
        mover();
        super.draw(batch);
    }


    private void mover()
    {
        if (getX()<=0 || getX()+getWidth()>= Gdx.graphics.getWidth()) {
            velx = velx * -1;
            CajaDeMusica.play("barrera");
        }

        if (getY()<=0 || getY()+getHeight()>= Gdx.graphics.getHeight()) {
            vely = vely * -1;
            CajaDeMusica.play("barrera");
        }

        setX(getX() + velx);
        setY(getY() + vely);
    }


    public float getVelocidadX()
    {
        return velx;
    }

    public float getVelocidadY()
    {
        return vely;
    }

    /*
    La pelota recibe por parametro las coordenadas x,y centrales del objeto con el que rebota y cambia
    su direccion en consecuencia
     */
    public void rebotar(float xrebote, float yrebote) {
        //Calculo mi propio centro
        float x = getX() + getWidth() / 2;
        float y = getY() + getHeight() / 2;

        //obtengo el modulo de mi velocidad
        float velocidad = (float) Math.sqrt(velx * velx + vely * vely);


        float difx = x - xrebote;
        float dify = y - yrebote;

        float relacion = Math.abs(difx) / Math.abs(dify);

        if (relacion >= 1) {
            //Rebote en la x
            if (difx > 0)
                if (velx < 0)
                    velx = velx * -1;
                else{}
            else
                if (velx > 0)
                    velx = velx * -1;
        } else {
            //Rebote en la y
            if (dify > 0)
                if (vely < 0)
                    vely = vely * -1;
                else{}

                else if (vely > 0)
                    vely = vely * -1;
        }

        CajaDeMusica.play("jugador");
    }
}
