package es.jvlc.SnakePang;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by Josev on 15/07/2015.
 */
public class ContadorTiempo {

    private int tiempo;

    private TextureRegion[] numeros;
    private boolean fin;

    private float posX;
    private float posY;

    public ContadorTiempo(int segundos, float x, float y, Texture texturas)
    {
        tiempo = segundos;
        fin = false;
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
        posX = x - numeros[4].getRegionWidth() * 2 - numeros[10].getRegionWidth()/2;
        posY = y;
        contar();
    }


    public boolean isFinalizado()
    {
        return fin;
    }

    public int getMinutos()
    {
        return (int) tiempo / 60;
    }

    public int getSegundos()
    {
        return tiempo - (getMinutos()*60);
    }

    public void contar() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
              tiempo--;
                if (tiempo > 0)
                {
                    contar();
                }
                else
                {
                    fin = true;
                }
            }
        }, 1);
    }


    public void draw(Batch batch)
    {
        int M2 = getMinutos();
        int M1 = M2/10;
        M2 = M2 - M1*10;

        float x = posX;

        int S2 = getSegundos();
        int S1 = S2/10;
        S2 = S2 - S1*10;

        batch.draw(numeros[M1], x, posY);
        x = x + numeros[M1].getRegionWidth();
        batch.draw(numeros[M2], x, posY);
        x = x + numeros[M2].getRegionWidth();

        batch.draw(numeros[10], x, posY);
        x = x + numeros[10].getRegionWidth();


        batch.draw(numeros[S1], x, posY);
        x = x + numeros[S1].getRegionWidth();
        batch.draw(numeros[S2], x, posY);
    }

}
