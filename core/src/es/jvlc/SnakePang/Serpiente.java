package es.jvlc.SnakePang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.Vector;

/**
 * Created by Josev on 11/07/2015.
 */
public class Serpiente extends Sprite {


    private Vector<Sprite> partes;
    private TextureRegion cuerpo;
    private tControlador controlador;
    private Inteligencia IA;
    private String direccion;
    private float velocidad;
    private boolean esPlayer;
    private Nivel nivel;
    private int numCampo;
    private float vDefecto;
    private String estado;
    private int contadorEstado;

    private float R;
    private float G;
    private float B;

    public float getvDefecto(){return vDefecto;}

    public Serpiente(Texture img,  boolean player, int numerodeCampo, Nivel pantalla)
    {
        super(new TextureRegion(img, 11, 76, 35, 30));

        vDefecto = Gdx.graphics.getHeight()*0.0058f;
        nivel = pantalla;
        numCampo = numerodeCampo;
        esPlayer = player;

        if (player)
            controlador = new tControlador(pantalla);
        else{
            if (numerodeCampo == 1)
                IA = new Inteligencia(this, 0, Gdx.graphics.getWidth()/2, 0, Gdx.graphics.getHeight(), pantalla, numerodeCampo);
            else
                IA = new Inteligencia(this, Gdx.graphics.getWidth()/2, Gdx.graphics.getWidth(), 0, Gdx.graphics.getHeight(), pantalla, numerodeCampo);

        }

        cuerpo = new TextureRegion(img, 11, 42, 34, 30);

        partes = new Vector<Sprite>();
        anyadirParte(30);
        velocidad = vDefecto;

        initialPos();

    }


    public boolean isEsPlayer()
    {
        return esPlayer;
    }

    public void congelar()
    {
        estado = "congelado";
        contadorEstado = 500;

            float R = 0.5f;
            float G = 0.5f;
            float B = 1;
            setColor(R, G, B, 1);
            for (int i=0; i<partes.size();i++)
                partes.elementAt(i).setColor(R,G,B,1);
            velocidad = vDefecto * 0.8f;
    }

    public void estamina()
    {
        estado = "estamina";
        contadorEstado = 500;
        R = 1;
        G = 0.5f;
        B = 0.5f;
        setColor(R,G,B,1);
        for (int i=0; i<partes.size();i++)
            partes.elementAt(i).setColor(R,G,B,1);

        velocidad = vDefecto * 1.3f;
    }

    public void normalizar()
    {
        estado = "normal";
        contadorEstado = 0;
        setColor(1,1,1,1);
        for (int i=0; i<partes.size();i++)
            partes.elementAt(i).setColor(1,1,1,1);

        velocidad = vDefecto;
    }

    public void initialPos()
    {
        //estado por defecto
        normalizar();

        //30 partes por defecto
        if (partes != null)
        {
            partes.clear();
            anyadirParte(30);
        }

        //Segun el campo asignado, coloco los gusanos en el punto de partida
        if (numCampo==1)
        {
            setX(Gdx.graphics.getWidth()/8);
            setY(Gdx.graphics.getHeight()/2);
        }
        if (numCampo==2)
        {
            setX(Gdx.graphics.getWidth()/8*7);
            setY(Gdx.graphics.getHeight() / 2);
        }

        if (partes != null)
        for (int i = 0; i<partes.size();i++)
        {
            partes.elementAt(i).setX(getX());
            partes.elementAt(i).setY(getY());
        }
    }

    public void anyadirParte(int cantidad)
    {
        for (int i=0; i<cantidad; i++)
        {
            partes.addElement(new Sprite(cuerpo));
            partes.elementAt(partes.size() - 1).setX(getX());
            partes.elementAt(partes.size() - 1).setY(getY());
            partes.elementAt(partes.size()-1).setColor(getColor());
        }
    }

    public void setVelocidad(float nueva)
    {
        if (nueva >0)
        {
            velocidad = nueva;
        }
    }

    public float getVelocidad()
    {
        return velocidad;
    }

    public void quitarParte()
    {
        partes.clear();
        anyadirParte(30);
    }

    public String getDireccion()
    {
        return direccion;
    }

    public void setDireccion(String nueva)
    {
        if (nueva == "arriba" || nueva== "abajo" ||nueva == "izquierda" ||nueva == "derecha")
        {
            direccion = nueva;
        }
    }

    @Override
    public void draw(Batch batch)
    {
        //Si no es un jugador, ordeno a la IA que piensa lo que debe de hacer
        if (!esPlayer)  IA.piensa();

        //Aplico los movimientos a todos los sprites del gusano
        mover();

        //Verifico las colisiones con las pelotas
        colisionPelota();

        //Verifico los premios
        colisionPremio();

        //Dibujo todos los sprites
        for (int i=0; i<partes.size(); i++)
            partes.elementAt(i).draw(batch);

        //Y llamo al batch para pintar la pantalla
        super.draw(batch);
    }

    private void mover()
    {
        //Revision del contador de estado por si esta congelado o hipervitaminado
        //a los 1000 movimientos se le pasa
        if (estado!= "normal")
        {
            contadorEstado--;
            if (contadorEstado<=0)  normalizar();
        }


        for (int i = partes.size()-1; i >= 0; i--)
        {
            if (i>0)
            {
                partes.elementAt(i).setX(partes.elementAt(i-1).getX());
                partes.elementAt(i).setY(partes.elementAt(i - 1).getY());
            }
            else
            {
                partes.elementAt(i).setX(getX());
                partes.elementAt(i).setY(getY());
            }
        }


        //Arriba
        if (direccion == "arriba"
                &&
                //No supera los limites de la pantalla
                getY() + getHeight() < Gdx.graphics.getHeight()
                ) {
            setRotation(180);
            setY(getY()+velocidad);
        }

        //Abajo
        if (direccion == "abajo" && getY() > 0) {
            setRotation(0);
            setY(getY()-velocidad);
        }
        //Derecha
        if (direccion == "derecha"
                &&
                // No rebasa el limit de pantalla
                getX() + getWidth() < Gdx.graphics.getWidth()
                &&
                //O es el jugador 2 o es jugador1 pero supera el medio campo
                (numCampo==2 || (numCampo==1 && getX() + getWidth() < Gdx.graphics.getWidth()/2)))
            {
                setRotation(90);
                setX(getX()+velocidad);
            }

        //Izquierda
        if (direccion == "izquierda"
                &&
                getX()>0
                &&
                (numCampo==1 || (numCampo==2 && getX() + getWidth() > Gdx.graphics.getWidth()/2))) {
            setRotation(180);
            setX(getX()-velocidad);
        }
    }

    public int colisionPelota()
    {
        Vector<Pelota> pelotas;
        pelotas = nivel.pelotas;
        int resultado = -1;

        float radioPelota;
        float xpelota;
        float ypelota;

        float RadioParte;
        float xParte;
        float yParte;



        for (int i=0; i < pelotas.size(); i++)
        {
            //miro todas las pelotas, y verifico si alguna colision con alguna de las partes del cuerpo
            xpelota = pelotas.elementAt(i).getX() + pelotas.elementAt(i).getWidth()/2;
            ypelota = pelotas.elementAt(i).getY() + pelotas.elementAt(i).getHeight()/2;
            radioPelota = pelotas.elementAt(i).getWidth()/2;

            for (int j=0; j<partes.size(); j++)
            {
                RadioParte = partes.elementAt(j).getWidth()/2;
                xParte = partes.elementAt(j).getX() + RadioParte;
                yParte = partes.elementAt(j).getY() + RadioParte;

                float difx = xpelota - xParte;
                float dify = ypelota - yParte;

                difx = difx * difx;
                dify = dify * dify;

                float distancia = (float)Math.sqrt(difx + dify);

                if (distancia <= radioPelota + RadioParte)
                {
                    resultado = j;
                    //Hago rebotar a la pelota
                    pelotas.elementAt(i).rebotar(xParte, yParte);

                }
            }
        }
        return resultado;
    }

    public void colisionPremio()
    {
        Vector<Premio> premios;
        premios = nivel.premios;

        float radioPremio;
        float xpremio;
        float ypremio;

        float RadioParte;
        float xParte;
        float yParte;

        boolean salir = false;

        //Recorro todas las partes del gusano
        for (int i=0; i < premios.size() && !salir; i++)
        {
            //miro todos los premios, y verifico si colisiona con alguna de las partes

            xpremio = premios.elementAt(i).getX() + premios.elementAt(i).getWidth()/2;
            ypremio = premios.elementAt(i).getY() + premios.elementAt(i).getHeight()/2;
            radioPremio = premios.elementAt(i).getWidth()/2;

            for (int j=0; j<partes.size() && !salir; j++)
            {
                RadioParte = partes.elementAt(j).getWidth()/2;
                xParte = partes.elementAt(j).getX() + RadioParte;
                yParte = partes.elementAt(j).getY() + RadioParte;

                float difx = xpremio - xParte;
                float dify = ypremio - yParte;

                difx = difx * difx;
                dify = dify * dify;

                float distancia = (float)Math.sqrt(difx + dify);

                if (distancia <= radioPremio + RadioParte)
                {
                    //Obtengo el tipo de prmio
                    int tipoDePremio = premios.elementAt(i).getTipo();

                    //No se dan mas vueltas al bucle
                    salir = true;

                    //Elimino el premio de la pantalla
                    premios.removeElementAt(i);

                    if (tipoDePremio==1)    estamina();

                    //if (tipoDePremio==2)    //puntos

                    if (tipoDePremio==3)
                    {
                        //Relentiza al enemigo
                        if (numCampo==1)
                        {
                            //relentizo al jugador 2
                            nivel.getJugador2().congelar();
                        }
                        else
                        {
                            //relentizo al jugador 2
                            nivel.getJugador1().congelar();
                        }
                    }
                    if (tipoDePremio==4)    anyadirParte(30);


                }
            }
        }
    }


}
