package es.jvlc.SnakePang;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.*;

import java.util.Random;
import java.util.Vector;



/**
 * Created by Josev on 11/07/2015.
 */
public class Nivel {

    private static int DuracionPartido = 240;

    private boolean finDePartido;
    private TextureRegion fondo;
    private Serpiente jugador1;
    private Serpiente jugador2;
    private boolean jugando;
    private Contador marcador;
    private ContadorTiempo contador;
    public Vector<Pelota> pelotas;
    public Vector<Premio> premios;
    private boolean mostrarGanador=false;

    private TextureRegion pantallaGanador;
    private TextureRegion textoDeGanador;

    private Texture texturas;

    //**********************************************************************************************
    //**********************************************************************************************

    public Nivel(Texture imagen)
    {
        jugando = false;
        finDePartido = true;

        //Me guardo el mapa de texturas para posibles usos futuros
        texturas = imagen;

        //Imagen de fin del partido donde se muestra el ganador
        pantallaGanador = new TextureRegion(texturas, 143, 1120, 844, 547);

        //Nuevo marcador de goles a cero
        marcador = new Contador(texturas);

        //Creo los vectores de pelotas y premios
        pelotas = new Vector<Pelota>();
        premios = new Vector<Premio>();

        //Creo el jugador 1, tiene un true porque es un player real
        jugador1 = new Serpiente(imagen, true, 1, this);
        jugador1.setDireccion("derecha");

        //Creo el jugador 2, tiene un false porque sera manejado por una IA
        jugador2 = new Serpiente(imagen,  false, 2, this);
        jugador2.setDireccion("izquierda");

        //cargo la textura del fondo del campo
        fondo = new TextureRegion(imagen, 140, 560, 843, 548);

        //Inicio la musica de Benny hill
        CajaDeMusica.loadMusica("data/Benny.ogg");
        CajaDeMusica.load("data/rebote_jugador.ogg", "jugador");
        CajaDeMusica.load("data/rebote_barrera.ogg", "barrera");
        CajaDeMusica.load("data/gol.ogg", "gol");
        CajaDeMusica.load("data/ovacion.ogg", "ovacion");
    }

    //**********************************************************************************************
    //**********************************************************************************************
    public boolean isFinDePartido()
    {
        return finDePartido;
    }
    //**********************************************************************************************
    //**********************************************************************************************

    public void jugar(int numjugadores)
    {

        if (finDePartido && numjugadores != -1)
        {
            if (numjugadores==0)
            {
                jugador1 = new Serpiente(texturas, false, 1, this);
                jugador2 = new Serpiente(texturas,  false, 2, this);
            }
            if (numjugadores==1)
            {
                jugador1 = new Serpiente(texturas, true, 1, this);
                jugador2 = new Serpiente(texturas,  false, 2, this);
            }
            if (numjugadores==2)
            {
                jugador1 = new Serpiente(texturas, true, 1, this);
                jugador2 = new Serpiente(texturas,  true, 2, this);
            }

            marcador = new Contador(texturas);

            contador = new ContadorTiempo(  DuracionPartido,
                                            Gdx.graphics.getWidth()/2,
                                            Gdx.graphics.getHeight() - 60,
                                            texturas);
            mostrarGanador = false;
            finDePartido=false;
            jugador1.setDireccion("derecha");
            jugador2.setDireccion("izquierda");
        }


        /**Arranca el partido
            Jugadores a la posicion de partida
            nueva pelota en lugar inicial
            pitido del arbitro
            Suena la musica
         */
        if (!jugando)
        {
            //mostrarGanador = true;  //Con este valor en true, draw dibujara la pantalla del vencedor
            pelotas.clear();
            pelotas.addElement(new Pelota(texturas, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2));
            CajaDeMusica.play("gol");
            pausa(2);
            premios.clear();
            jugador1.initialPos();
            jugador2.initialPos();
            jugando = true;
            CajaDeMusica.playMusica();
        }
    }

    //**********************************************************************************************
    //**********************************************************************************************
    /**
    * Finaliza el partido
    *   Detener la muesica
    *   eliminar pelotas y premios
    *   Se muestra el mensaje con el ganador*/
    private void finDelPartido()
    {
        jugando = false;            //Valor a false, los jugadores no juegan
        mostrarGanador = true;      //a True, se mostrara la pantalla con el ganador del partido
        CajaDeMusica.stopMusica();  //Se para la musica
        pelotas.clear();            //Fuera pelotas
        premios.clear();            //fuera premios
        seAcabo();                  //contador de 8 segundos y se da el partido por finalizado
    }

    //**********************************************************************************************
    //**********************************************************************************************
    /**
     * Metodo que par el partido*/
    public void stop()
    {
        if (jugando)
        {
            jugando = false;
            CajaDeMusica.stopMusica();
            CajaDeMusica.play("gol");
        }
    }

    //**********************************************************************************************
    //**********************************************************************************************

    public void draw(Batch batch)
    {
       if (jugando)
       {
            //con una probabilidad de 1 a 1000, pongo un premio
           Random random = new Random();
           if (random.nextInt(1000) == 1)
           {
               addPremio(random.nextInt(4)+1);
           }

           //Verifica si alguna pelota ha entrado en la porteria
           verificarPelotas();

           //Pinto el campo de futbol
           batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

           //Mando dibujar en el batch todas las pelotas
           for (int i=0; i<pelotas.size();i++)
               pelotas.elementAt(i).draw(batch);

           //Mando dibujar todos los premios
           for (int i=0; i<premios.size();i++)
                premios.elementAt(i).draw(batch);

           //Dibujo los jugadores
           jugador1.draw(batch);
           jugador2.draw(batch);

           //Dibujo el marcador
           marcador.draw(batch);
           contador.draw(batch);

           if (jugando && contador.isFinalizado())
           {
               finDelPartido();
           }

       }
        else
       {
           if (mostrarGanador)
           {
               if (marcador.getPuntuacion1() > marcador.getPuntuacion2())
               {
                    if (jugador1.isEsPlayer())
                    {
                        textoDeGanador = new TextureRegion(texturas, 1003, 1132, 453, 69);
                    }
                   else
                    {
                        textoDeGanador = new TextureRegion(texturas, 1012, 1262, 280, 68);
                    }
               }
               else
               if (marcador.getPuntuacion1()< marcador.getPuntuacion2())
               {
                   if (jugador2.isEsPlayer())
                   {
                       textoDeGanador = new TextureRegion(texturas, 1003, 1198, 453, 69);
                   }
                   else
                   {
                       textoDeGanador = new TextureRegion(texturas, 1012, 1325, 280, 68);
                   }
               }
               else
               {
                   //empate
                   textoDeGanador = new TextureRegion(texturas, 1014, 1392, 322, 64);
               }
               batch.draw(pantallaGanador, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
               batch.draw(  textoDeGanador,
                            Gdx.graphics.getWidth()/2 - textoDeGanador.getRegionWidth()/2,
                            Gdx.graphics.getHeight()/2);
           }
       }
    }

    //**********************************************************************************************
    //**********************************************************************************************

    private void verificarPelotas()
    {
        boolean parar = false;

        for (int i=0; i<pelotas.size() && !parar; i++)
        {
            //La pelota esta en el borde izquierdo
            if (pelotas.elementAt(i).getX() <=5)
            {
                //La pelota esta en la porteria
                if (pelotas.elementAt(i).getY()>= Gdx.graphics.getHeight()*0.1419 &&
                        pelotas.elementAt(i).getY()<= Gdx.graphics.getHeight()-Gdx.graphics.getHeight()*0.1419)
                {
                    stop();
                    parar = true;
                    CajaDeMusica.play("ovacion");
                    pausa(5);
                    marcador.gol(2);
                }
            }

            //La pelota esta en el borde derecho
            if (pelotas.elementAt(i).getX() + pelotas.elementAt(i).getWidth() >= Gdx.graphics.getWidth()-5)
            {
                //La pelota esta en la porteria
                if (pelotas.elementAt(i).getY()>= Gdx.graphics.getHeight()*0.1419 &&
                        pelotas.elementAt(i).getY()<= Gdx.graphics.getHeight()-Gdx.graphics.getHeight()*0.1419)
                {
                    stop();
                    parar = true;
                    CajaDeMusica.play("ovacion");
                    pausa(5);
                    marcador.gol(1);
                }
            }
        }
    }

    //**********************************************************************************************
    //**********************************************************************************************
    private void seAcabo() {
        Timer.schedule(new Task() {
            @Override
            public void run() {
                finDePartido = true;
            }
        }, 8);
    }
    //**********************************************************************************************
    //**********************************************************************************************

    public void pausa(float segundos) {
        Timer.schedule(new Task() {
            @Override
            public void run() {
                if (!finDePartido) jugar(-1);
            }
        }, segundos);
    }


    public Serpiente getJugador1(){return jugador1;}

    public Serpiente getJugador2(){return jugador2;}

    //**********************************************************************************************
    //**********************************************************************************************
    private void addPremio(int tipo)
    {
        if (tipo == 1)
        {
            premios.addElement(new Premio( new TextureRegion(texturas, 1011, 7, 23, 50),1));
        }
        if (tipo == 2)
        {
            premios.addElement(new Premio( new TextureRegion(texturas, 1043, 9, 29, 47),2));
        }
        if (tipo == 3)
        {
            premios.addElement(new Premio( new TextureRegion(texturas, 1082, 11, 42, 47),3));
        }
        if (tipo == 4)
        {
            premios.addElement(new Premio( new TextureRegion(texturas, 1134, 9, 46, 47),4));
        }
    }

}
