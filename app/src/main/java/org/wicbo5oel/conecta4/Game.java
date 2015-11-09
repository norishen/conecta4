package org.wicbo5oel.conecta4;

import android.util.Log;

/**
 * Creado por administrador on 27/10/15.
 */
public class Game {
    static final int SIZE_Y = 6;
    static final int SIZE_X = 7;

    private int tablero[][];


    public Game(){
        this.restart();
    }

    // Inicializo la matriz de juego
    public void restart(){
        tablero = new int[SIZE_Y][SIZE_X];

        for ( int y=0; y <SIZE_Y; y++ )
            for ( int x=0; x< SIZE_X; x++ )
                tablero[y][x] = 0;

    }

    // Retorna en contenido de la posicion pedida
    public int getTablero(int i, int j) {
        return tablero[i][j];
    }

    // Coloca una ficha en el, tablero
    public void putTablero(int i, int j, int player) {
        tablero[i][j] = player;
    }


    // Debuelve true si tenemos sitio para poner ficha
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

    // Le toca a la maquina Jugar
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
            log("Maquina - pos " + Integer.toString(outy) + "," + Integer.toString(outx));
            tablero[outy][outx] = 2;
        }
    }


    boolean comprobarCuatro(int player) {
        if (comprobarFilas(player) || comprobarColumnas(player) || comprobarDiagonales(player)) {
            log("Cumplo alguna condicion");
            return true;
        }
        else
            return false;

    }

    boolean comprobarFilas(int player) {
        int fichas = 0;

        for (int y = 0; y < SIZE_Y; y++) {
            for (int x = 0; x < SIZE_X; x++) {
                if (tablero[y][x] == player)
                    fichas++;
                else
                    fichas = 0;

                if (fichas >= 4) {
                    log("Cumplo comprobarFilas");
                    return true;
                }
            }
            fichas = 0;
        }

        return false;
    }


    boolean comprobarColumnas(int player) {
        int fichas = 0;

        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                if (tablero[y][x] == player)
                    fichas++;
                else
                    fichas = 0;

                if (fichas >= 4) {
                    log("Cumplo comprobarColumnas");
                    return true;
                }
            }

            fichas = 0;
        }

        return false;
    }


    boolean comprobarDiagonales(int player) {
        int fichas = 0;

        //-----------------------------------------------------------------
        // Primero prueba los diagonales inferiores por posibilidades

        // MITAD DERECHA: izquierda a derecha (3,0 4,0 5,0)
        for (int y = 3; y < SIZE_Y; y++) {
            for (int x = 0; x <= y; x++) {
                log("Player" + Integer.toString(player) + " - pruebo0 [" + Integer.toString(y - x) + "," + Integer.toString(x) + "]");
                if (tablero[y - x][x] == player) {
                    log("Player" + Integer.toString(player) + " - Existe");
                    fichas++;
                }
                else
                    fichas = 0;

                if (fichas >= 4) {
                    log("Cumplo comprobarDiagonales_0");
                    return true;
                }
            }
            fichas = 0;
        }


        // MITAD IZQUIERDA: izquierda a derecha (3,6 4,6 5,6)
        for (int y = 3; y < SIZE_Y; y++) {
            for (int x = 0; x <= y; x++) {
                log("Player" + Integer.toString(player) + " - pruebo1 [" + Integer.toString(y - x) + "," + Integer.toString((SIZE_X - 1) - x) + "]");
                if (tablero[y - x][(SIZE_X - 1) - x] == player) {
                    log("Player" + Integer.toString(player) + " - Existe");
                    fichas++;
                } else
                    fichas = 0;

                if (fichas >= 4) {
                    log("Cumplo comprobarDiagonales_1");
                    return true;
                }
            }
            fichas = 0;
        }

        //-----------------------------------------------------------------
        // Despues las diagonales superiores

        // MITAD DERECHA: derecha a izquierda (0,0 1,0 2,0)
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < (SIZE_X - 1) - y; x++) {
                log("Player" + Integer.toString(player) + " - pruebo2 [" + Integer.toString(y + x) + "," + Integer.toString(x) + "]");
                if (tablero[y + x][x] == player)
                    fichas++;
                else
                    fichas = 0;

                if (fichas >= 4) {
                    log("Cumplo comprobarDiagonales_2");
                    return true;
                }
            }
            fichas = 0;

        }

        // MITAD IZQUIERDA: derecha a izquierda (0,6 1,6 2,6)
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < (SIZE_X - 1) - y; x++) {
                log("Player" + Integer.toString(player) + " - pruebo3 [" + Integer.toString(y + x) + "," + Integer.toString((SIZE_X - 1) - x) + "]");
                if (tablero[y + x][(SIZE_X - 1) - x] == player)
                    fichas++;
                else
                    fichas = 0;

                if (fichas >= 4) {
                    log("Cumplo comprobarDiagonales_3");
                    return true;
                }
            }
            fichas = 0;

        }


        return false;
    }

    // Es solo para sacar el log
    private void log(String text) {
        Log.d("LifeCycleTest", text);
    }

}
