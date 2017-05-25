package es.jvlc.SnakePang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

/**
 * Created by Josev on 13/07/2015.
 */
public class ControlMenu extends InputAdapter {

    private boolean boton1;
    private boolean boton2;
    private boolean boton3;

    public ControlMenu()
    {
        boton1 = false;
        boton2 = false;
        boton3 = false;
    }

    public int botonPulsado()
    {
        int res = -1;
        if (boton1)
            res = 1;
        else
            if (boton2)
                res = 2;
        else
                if (boton3)
                    res = 3;

        boton1=false;
        boton2=false;
        boton3=false;
        return res;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        boolean res = false;

        if (screenX > Gdx.graphics.getWidth()*0.6367 && screenX < Gdx.graphics.getWidth()*0.891)
        {
            if (screenY > Gdx.graphics.getHeight()*0.598 && screenY < Gdx.graphics.getHeight()*0.715)
            {
                boton1 = true;
                res = true;
            }
            if (screenY > Gdx.graphics.getHeight()*0.426 && screenY < Gdx.graphics.getHeight()*0.543)
            {
                boton2 = true;
                res = true;
            }
            if (screenY > Gdx.graphics.getHeight()*0.243 && screenY < Gdx.graphics.getHeight()*0.364)
            {
                boton3 = true;
                res = true;
            }
        }
        return res;
    }


}
