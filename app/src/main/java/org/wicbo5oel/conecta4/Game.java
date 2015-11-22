package org.wicbo5oel.conecta4;

import android.util.Log;

/**
 * Creado por administrador on 27/10/15.
 */
public class Game {
    static final int SIZE_Y = 6;
    static final int SIZE_X = 7;

    private int tablero[][];
    private int ganador[][];


    private int iaMaxProfundidad = 5;

    //-------------------------------------------
    // CONSTRUCTOR
    //
    public Game(){
        this.restart();
    }
    //-------------------------------------------


    //---------------------------------------------------------------------------------
    // Funciones principales de la logica
    //---------------------------------------------------------------------------------

    //-------------------------------------------
    // Inicializo la matriz de juego
    //
    public void restart(){
        tablero = new int[SIZE_Y][SIZE_X];
        ganador = new int[SIZE_Y][SIZE_X];

        for ( int y=0; y <SIZE_Y; y++ )
            for ( int x=0; x< SIZE_X; x++ ) {
                tablero[y][x] = 0;
                ganador[y][x] = 0;
            }
    }

    //-------------------------------------------
    // Coloca una ficha en el, tablero
    //
    public void putTablero(int i, int j, int player) {
        tablero[i][j] = player;
    }


    //-------------------------------------------
    // Debuelve true si tenemos sitio para poner ficha
    //
    public boolean sePuedeColocarFicha(int i, int j){
        boolean out=false;

        if ( tablero[i][j] == 0 )
            if (i == (SIZE_Y - 1))
                out = true;
            else
            if ( tablero[(i+1)][j] != 0 )
                out = true;

        return out;
    }

    //-------------------------------------------
    // Debuelve true si tenemos casillas vacias
    //
    public boolean casillasVacias(){

        for ( int y=0; y <SIZE_Y; y++ )
            for ( int x=0; x< SIZE_X; x++ )
                if ( tablero[y][x] == 0 )
                    return true;

        return false;
    }


    //-------------------------------------------
    // Comprobar Ganador del juego
    //
    boolean comprobarCuatro(int player) {
        return (comprobarFilas(player) || comprobarColumnas(player) || comprobarDiagonales(player));
    }


    //-------------------------------------------
    // Comprobar ganador en filas
    //
    boolean comprobarFilas(int player) {
        int fichas = 0;

        for (int y = 0; y < SIZE_Y; y++) {
            for (int x = 0; x < SIZE_X; x++) {
                if (tablero[y][x] == player) {
                    fichas++;
                    ganador[y][x] = player;
                }
                else
                    fichas = 0;

                if (fichas >= 4) {
                    return true;
                }
            }
            // Borro contadores de estado
            for (int x=0; x < SIZE_X; x++)
                ganador[y][x] = 0;

            fichas = 0;
        }

        return false;
    }


    //-------------------------------------------
    // Conprobar ganador en columnas
    //
    boolean comprobarColumnas(int player) {
        int fichas = 0;

        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                if (tablero[y][x] == player) {
                    fichas++;
                    ganador[y][x] = player;
                }
                else
                    fichas = 0;

                if (fichas >= 4) {
                    return true;
                }
            }
            // Borro contadores de estado
            for (int y=0; y < SIZE_Y; y++)
                ganador[y][x] = 0;

            fichas = 0;
        }

        return false;
    }


    //-------------------------------------------
    // Comprobar ganador en dialgonales
    //
    boolean comprobarDiagonales(int player) {
        int fichas = 0;

        //-----------------------------------------------------------------
        // Primero prueba los diagonales inferiores por posibilidades

        // MITAD DERECHA: izquierda a derecha (3,0 4,0 5,0)
        for (int y = 3; y < SIZE_Y; y++) {
            for (int x = 0; x <= y; x++) {
                if (tablero[y-x][x] == player) {
                    fichas++;
                    ganador[y-x][x] = player;
                }
                else
                    fichas = 0;

                if (fichas >= 4) {
                    return true;
                }
            }
            // Borro contadores de estado
            for (int x = 0; x <= y; x++)
                ganador[y-x][x] = 0;

            fichas = 0;
        }


        // MITAD IZQUIERDA: izquierda a derecha (3,6 4,6 5,6)
        for (int y = 3; y < SIZE_Y; y++) {
            for (int x = 0; x <= y; x++) {
                if (tablero[y-x][(SIZE_X-1) - x] == player) {
                    fichas++;
                    ganador[y-x][(SIZE_X-1) - x] = player;
                } else
                    fichas = 0;

                if (fichas >= 4) {
                    return true;
                }
            }
            // Borro contadores de estado
            for (int x = 0; x <= y; x++)
                ganador[y-x][(SIZE_X-1) - x] = 0;

            fichas = 0;
        }

        //-----------------------------------------------------------------
        // Despues las diagonales superiores

        // MITAD DERECHA: derecha a izquierda (0,0 1,0 2,0)
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < (SIZE_X - 1) - y; x++) {
                if (tablero[y+x][x] == player) {
                    fichas++;
                    ganador[y+x][x] = player;
                }
                else
                    fichas = 0;

                if (fichas >= 4) {
                    return true;
                }
            }
            // Borro contadores de estado
            for (int x = 0; x < (SIZE_X - 1) - y; x++)
                ganador[y+x][x] = 0;

            fichas = 0;

        }

        // MITAD IZQUIERDA: derecha a izquierda (0,6 1,6 2,6)
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < (SIZE_X - 1) - y; x++) {
                if (tablero[y+x][(SIZE_X-1) - x] == player) {
                    fichas++;
                    ganador[y+x][(SIZE_X-1) - x] = player;
                }
                else
                    fichas = 0;

                if (fichas >= 4) {
                    return true;
                }
            }
            // Borro contadores de estado
            for (int x = 0; x < (SIZE_X - 1) - y; x++)
                ganador[y+x][(SIZE_X-1) - x] = 0;

            fichas = 0;

        }


        return false;
    }
    //----------------------------------------------------------------------------



    //----------------------------------------------------------------------------
    // Funciones auxiliales
    //----------------------------------------------------------------------------

    //-------------------------------------------
    // Retorna en contenido de la posicion pedida
    //
    public int getTablero(int i, int j) {
        return tablero[i][j];
    }

    //-------------------------------------------
    // Retorna en contenido de la posicion pedida
    //
    public int getGanador(int i, int j) {
        return ganador[i][j];
    }

    //-------------------------------------------
    // Serializa/Deserializa Tablero
    //
    public String tableroToString() {
        String str = "";
        for (int y=0; y < SIZE_Y; y++)
            for (int x = 0; x < SIZE_X; x++)
                str += tablero[y][x];
        return str;
    }

    public void stringToTablero(String str) {
        for (int y = 0, cont = 0; y < SIZE_Y; y++)
            for (int x = 0; x < SIZE_X; x++)
                tablero[y][x] = str.charAt(cont++) - '0';
    }
    //-------------------------------------------

    //-------------------------------------------
    // Es solo para sacar el log
    //
    private void log(String text) {
        Log.d("LifeCycleTest", text);
    }

    //----------------------------------------------------------------------------




    //----------------------------------------------------------------------------
    // Le toca a la maquina Jugar
    //
    // Implementacion de la IA del juego
    //------------------------------------------------------------

    public void juegaMaquina(){
        int outy=SIZE_Y, outx=0;

        for ( int y=(SIZE_Y-1); y>=0; y-- )
            for ( int x=(SIZE_X-1); x>=0; x-- )
                if (tablero[y][x] == 0) {
                    outy = y;
                    outx = x;
                    y=0;
                    break;
                }

        if (outy != SIZE_Y) {
            tablero[outy][outx] = 2;
        }
    }

    //-------------------------------------------
    // Buscamos el mejor movimiento de la CPU
    //
    public void mueveIA(){
/*
        int aux, mejor=-9999;

        int alpha=-9999;
        int beta=9999;
*/
        int mejorY=0;
        int mejorX=0;
        int max, max_actual;

        log("Entro IA");
        iaMaxProfundidad = 5;

        max = Integer.MIN_VALUE;

        for ( int y=0; y<SIZE_Y; y++ )
            for ( int x=0; x<SIZE_X; x++ )
                if ( sePuedeColocarFicha( y, x) ) {
                    // probamos jugada CPU
                    tablero[y][x] = 2;
                    log("IA Pos["+Integer.toString(y)+","+Integer.toString(x)+"]");

                    // Simulamos el movimiento del HUMANO
                    max_actual = min(0, Integer.MIN_VALUE , Integer.MAX_VALUE);

                    log("max_actual="+Integer.toString(max_actual)+" - max="+Integer.toString(max));

                    // borra la jugada probada
                    tablero[y][x] = 0;
                    if ( max_actual > max ) {
                        max = max_actual;

                        mejorY = y;
                        mejorX = x;
                        log( "IA pos["+Integer.toString(y)+","+Integer.toString(x)+"]");
                    }
                }

        // Pone la mejor juegada encontrada
        tablero[mejorY][mejorX] = 2;
    }


    //-------------------------------------------
    // Simulamos el movimiento del HUMANO
    //
    public int min( int prof, int alpha, int beta){
        // Ha ganado CPU
        if ( comprobarCuatro( 2 ) ){
            return 10000;
        }

        // No se puede mover mas
        if ( !casillasVacias() ) return 0;

        // Compruebo profundidad
        if ( prof > iaMaxProfundidad ) return 0;

        for ( int y=0; y<SIZE_Y; y++ )
            for ( int x=0; x<SIZE_X; x++ )
                if ( sePuedeColocarFicha( y, x) ) {
                    // probamos jugada Humano
                    tablero[y][x] = 1;

                    // Simulamos el movimiento de la CPU
                    beta = Math.min(beta, max((prof + 1), alpha, beta));

                    // borra la jugada probada
                    tablero[y][x] = 0;

                    if ( alpha >= beta)
                        return alpha;

                }

        return beta;
    }


    //-------------------------------------------
    // Simulamos el movimiento de la CPU
    //
    public int max( int prof, int alpha, int beta ){
        // Ha ganado el HUMANO
        if ( comprobarCuatro( 1 ) )
            return -10000;

        // No se puede mover mas
        if ( !casillasVacias() ) return 0;

        // Compruebo profundidad
        if ( prof > iaMaxProfundidad ) return 0;

        for ( int y=0; y<SIZE_Y; y++ )
            for ( int x=0; x<SIZE_X; x++ )
                if ( sePuedeColocarFicha( y, x) ) {
                    // probamos jugada CPU
                    tablero[y][x] = 2;

                    // Simulamos el movimiento del HUMANO
                    alpha = Math.max(alpha, min( (prof+1), alpha, beta ) );

                    // borra la jugada probada
                    tablero[y][x] = 0;

                    if ( alpha >= beta )
                        return beta;

                }

        return alpha;
    }

    private int heuristica(){
        return 1;
    }
}
