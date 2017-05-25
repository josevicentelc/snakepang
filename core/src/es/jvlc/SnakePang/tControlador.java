package es.jvlc.SnakePang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.Vector;

/**
 * Created by Josev on 02/07/2015.
 */


public class tControlador extends InputAdapter {

    private BitmapFont texto;
    private Nivel nivel;

    private float xp1;
    private float yp1;
    private float xp2;
    private float yp2;

    private Vector<Coordenada> punteros;

    public tControlador(Nivel n)
    {
        nivel = n;
        Gdx.input.setInputProcessor(this);
        punteros = new Vector<Coordenada>();
    }

    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button)
    {
        punteros.insertElementAt(new Coordenada(screenX, screenY), pointer);
        return true;
    }


    @Override
    public boolean touchDragged (int screenX, int screenY, int pointer)
    {

        float difx = screenX - punteros.elementAt(pointer).x;
        float dify = screenY - punteros.elementAt(pointer).y;
        String direccion = "";

        if (Math.abs(difx) > Math.abs(dify))
        {
            //en x
            if (difx < 0)   direccion = "izquierda";
            else            direccion = "derecha";
        }
        else
        {
            //en y
            if (dify < 0)   direccion = "arriba";
            else            direccion = "abajo";
        }



        if (punteros.elementAt(pointer).x>=0 && punteros.elementAt(pointer).x< Gdx.graphics.getWidth()/2)
        {
            nivel.getJugador1().setDireccion(direccion);
        }
        else
        {
            nivel.getJugador2().setDireccion(direccion);
        }
        return false;
    }



    @Override
    public boolean keyDown (int keycode)
    {
        boolean res = false;

        if (keycode == Input.Keys.D )
        {
            res = true;
            nivel.getJugador1().setDireccion("derecha");
        }
        else
        if (keycode == Input.Keys.A )
        {
            res = true;
            nivel.getJugador1().setDireccion("izquierda");
        }
        else
        if (keycode == Input.Keys.S )
        {
            res = true;
            nivel.getJugador1().setDireccion("abajo");
        }
        else
        if (keycode == Input.Keys.W )
        {
            res = true;
            nivel.getJugador1().setDireccion("arriba");
        }
        return res;
    }





}
