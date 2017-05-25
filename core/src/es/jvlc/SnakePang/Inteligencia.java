package es.jvlc.SnakePang;

import java.util.Random;
/**
 * Created by Josev on 12/07/2015.
 */
public class Inteligencia {

    private Serpiente jugador;
    private int idPelota;
    private int idPremio;
    private float minimoX;
    private float maximoX;
    private float minimoY;
    private float maximoY;

    private int numeroDeCampo;
    //Si el campo es 1, la porteria esta en X = 0
    //Si el campo es 2, la porteria esta en X = Width

    private Nivel pantalla;

    private int estado;

        //1 deambulando
        //2 buscando la pelota
        //3 buscando objetos

    public Inteligencia(Serpiente j, float xmin, float xmax, float ymin, float ymax, Nivel nivel, int campo)
    {
        idPelota=-1;
        numeroDeCampo = campo;
        pantalla = nivel;
        jugador = j;
        estado = 1;
        minimoX = xmin;
        maximoX = xmax;
        minimoY = ymin;
        maximoY = ymax;
    }

    public void piensa()
    {
        //Estado inicial, deambulando

        estado = 1;

        /*
        *   Hay objetos cerca?
        *   SI
        *       Nuevo estado -> Buscar objetos
        * */

        //buscar objetos

        idPremio = -1;

        for (int i=0; i < pantalla.premios.size(); i++)
        {
            //si el objeto esta en mi campo
            if (pantalla.premios.elementAt(i).getX() > minimoX && pantalla.premios.elementAt(i).getX() < maximoX)
            {
                idPremio=i;
                estado = 3;
            }
        }

        /*
        * Viene la pelota hacia la porteria?
        */

        idPelota= -1; //Identificador de la pelota que se persigue

        if (numeroDeCampo==1)
        {
            //miro todas las pelotas a ver si alguna va hacia mi porteria
            for (int i = 0; i< pantalla.pelotas.size(); i++)
            {
               if (pantalla.pelotas.elementAt(i).getVelocidadX() < 0)
               {
                   idPelota=i;
                   estado = 2;
               }
            }
        }

        if (numeroDeCampo==2)
        {
            //miro todas las pelotas a ver si alguna va hacia mi porteria
            for (int i = 0; i< pantalla.pelotas.size(); i++)
            {
                if (pantalla.pelotas.elementAt(i).getVelocidadX() > 0)
                {
                    idPelota=i;
                    estado = 2;
                }
            }
        }


        //Deambulando
        if (estado == 1)
        {
            Random random = new Random();
            int movimiento = random.nextInt(100) + 1;

            if (movimiento == 1)
            {
                jugador.setDireccion("arriba");
            }
            else
                if (movimiento==2)
                {
                    jugador.setDireccion("abajo");
                }
                else
                    if (movimiento==3)
                    {
                        jugador.setDireccion("izquierda");
                    }
                else
                    if (movimiento==4)
                    {
                        jugador.setDireccion("derecha");
                    }
        }
        else
        {
            if (estado==2)
            {
                //Me muevo arriba o abajo para colocarme delante de la pelota
                if (pantalla.pelotas.elementAt(idPelota).getY() < jugador.getY())
                {
                    jugador.setDireccion("abajo");
                }
                else
                    jugador.setDireccion("arriba");

                //Pero si la pelota esta mas cerca de mi porteria que yo, entonces voy hacia mi porteria

                //Si soy el jugador1, voy hacia la porteria 1
                if (numeroDeCampo==1 && (pantalla.pelotas.elementAt(idPelota)).getX() < jugador.getX())
                {
                    jugador.setDireccion("izquierda");
                }
                else
                {   //Pero si soy el jugador2, defendere la porteria derecha
                    if (numeroDeCampo==2 && (pantalla.pelotas.elementAt(idPelota)).getX() > jugador.getX())
                    {
                        jugador.setDireccion("derecha");
                    }
                }
            }
            else
            {
                if (estado == 3) //esta en estado de buscar el objeto idPremio
                {
                    //primero busca el objeto en la coordenada x
                    if (Math.abs(pantalla.premios.elementAt(idPremio).getX() - jugador.getX()) > jugador.getWidth()/2 )
                    {
                        //La diferencia en X entre jugador y premio es mayor que medio jugador, persigo en x
                        if (pantalla.premios.elementAt(idPremio).getX() < jugador.getX())
                        {
                            jugador.setDireccion("izquierda");
                        }
                        else
                        {
                            jugador.setDireccion("derecha");
                        }
                    }
                    else
                    {
                        //como el jugador y el premio coinciden en X, persigo el premio en Y
                        if (pantalla.premios.elementAt(idPremio).getY() < jugador.getY())
                        {
                            jugador.setDireccion("abajo");
                        }
                        else
                        {
                            jugador.setDireccion("arriba");
                        }
                    }
                }
            }
        }

        //En cualquier caso, si se rebasan los limites del campo, tiene que dar la vuelta

        if (jugador.getX() <= minimoX)
            jugador.setDireccion("derecha");

        if (jugador.getX()>=maximoX)
            jugador.setDireccion("izquierda");

        if (jugador.getY()<=minimoY)
            jugador.setDireccion("arriba");

        if (jugador.getY()>=maximoY)
            jugador.setDireccion("abajo");

    }


}
