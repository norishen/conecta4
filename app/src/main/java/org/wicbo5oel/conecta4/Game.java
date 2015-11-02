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

        // ------------ PRIMERA MITAD -------------
        // de derecha a izquierda
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 6 - i; k++) {
                log("Player" + Integer.toString(player) + " - pruebo0 [" + Integer.toString(i + k) + "," + Integer.toString(k) + "]");
                if (tablero[i + k][k] == player)
                    fichas++;
                else
                    fichas = 0;

                if (fichas >= 4) {
                    log("Cumplo comprobarDiagonales_0");
                    return true;
                }
            }
            fichas = 0;

        }

        // de izquierda a derecha
        fichas = 0;
        for (int y = 3; y < SIZE_Y; y++) {
            for (int x = 0; x <= y; x++) {
                log("Player" + Integer.toString(player) + " - pruebo1 [" + Integer.toString(y - x) + "," + Integer.toString(x) + "]");
                if (tablero[y - x][x] == player) {
                    log("Player" + Integer.toString(player) + " - Existe");
                    fichas++;
                }
                else
                    fichas = 0;

                if (fichas >= 4) {
                    log("Cumplo comprobarDiagonales_1");
                    return true;
                }
            }
            fichas = 0;
        }


        // ------------ SEGUNDA MITAD -------------
        // Este apunte es solo para estar seguros de la sincronizacion desde otros fuentes
        // Borrar en cuanto se comprueve que es correcta.



        return false;
    }


/*
    boolean comprobarDiagonales(int turno) {
        int MAQUINA = 2;
        String MAQUINASTR = "1111";
        String JUGADORSTR = "2222";
        String cadena = turno == MAQUINA ? MAQUINASTR : JUGADORSTR;

        // derecha a izquierda
        for (int i = 0; i < 3; i++) {
            String str = "";
            for (int k = 0; k < 6 - i; k++)
                str += Integer.toString(tablero[i + k][k]);
            if (str.contains(cadena)) {
                log("Cumplo comprobarDiagonales_1");
                return true;
            }

        }

        // Teoricamente el resto
        for (int j = 1; j < 4; j++) {
            String str = "";
            for (int k = 0; k < 7 - j; k++)
                str += Integer.toString(tablero[k][j + k]);
            if (str.contains(cadena)) {
                log("Cumplo comprobarDiagonales_2");
                return true;
            }
        }

        return false;
    }
*/

    // Es solo para sacar el log
    private void log(String text) {
        Log.d("LifeCycleTest", text);
    }

}
