package org.wicbo5oel.conecta4;

import android.util.Log;

/**
 * Created by administrador on 27/10/15.
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

        log( "Entrada i=" + Integer.toString(i) + " j=" + Integer.toString(j));
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

        if ( outy != SIZE_Y )
            tablero[outy][outx] = 2;
    }


    private void log(String text) {
        Log.d("LifeCycleTest", text);
    }

}
