package es.jvlc.SnakePang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Josev on 13/07/2015.
 */
public class Contador {

    private int jugador1;

    private int jugador2;

    private BitmapFont fuente;

    private TextureRegion[] numeros;

    public Contador(Texture texturas)
    {
        fuente = new BitmapFont();

        numeros = new TextureRegion[11];

        numeros[0]  = new TextureRegion(texturas, 1341, 75, 33, 48);
        numeros[1]  = new TextureRegion(texturas, 1016, 75, 28, 48);
        numeros[2]  = new TextureRegion(texturas, 1050, 75, 33, 48);
        numeros[3]  = new TextureRegion(texturas, 1087, 75, 28, 48);
        numeros[4]  = new TextureRegion(texturas, 1121, 75, 28, 48);
        numeros[5]  = new TextureRegion(texturas, 1160, 75, 28, 48);
        numeros[6]  = new TextureRegion(texturas, 1196, 75, 28, 48);
        numeros[7]  = new TextureRegion(texturas, 1233, 75, 28, 48);
        numeros[8]  = new TextureRegion(texturas, 1268, 75, 28, 48);
        numeros[9]  = new TextureRegion(texturas, 1305, 75, 28, 48);
        numeros[10] = new TextureRegion(texturas, 1381, 75, 11, 48);

        fuente.setColor(1f, 1f, 1f, 1f);
        jugador1 = 0;
        jugador2 = 0;
    }

    public int getPuntuacion1()
    {
        return jugador1;
    }

    public int getPuntuacion2()
    {
        return jugador2;
    }

    public void gol(int numeroDeJugador)
    {
        if (numeroDeJugador==1)
            jugador1++;
        else if (numeroDeJugador==2)
            jugador2++;
    }

    public void draw(Batch batch)
    {
        fuente.draw(batch, String.valueOf(jugador1), Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()-10);
        fuente.draw(batch, String.valueOf(jugador2), Gdx.graphics.getWidth()/4*3, Gdx.graphics.getHeight()-10);
    }

}
