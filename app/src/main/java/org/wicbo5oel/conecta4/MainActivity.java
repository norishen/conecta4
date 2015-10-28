package org.wicbo5oel.conecta4;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends Activity implements OnClickListener {
    private Game game;
    static final int SIZE_Y = 6;
    static final int SIZE_X = 7;

    private final int ids [][] = {
            {R.id.b11, R.id.b12, R.id.b13, R.id.b14, R.id.b15, R.id.b16, R.id.b17},
            {R.id.b21, R.id.b22, R.id.b23, R.id.b24, R.id.b25, R.id.b26, R.id.b27},
            {R.id.b31, R.id.b32, R.id.b33, R.id.b34, R.id.b35, R.id.b36, R.id.b37},
            {R.id.b41, R.id.b42, R.id.b43, R.id.b44, R.id.b45, R.id.b46, R.id.b47},
            {R.id.b51, R.id.b52, R.id.b53, R.id.b54, R.id.b55, R.id.b56, R.id.b57},
            {R.id.b61, R.id.b62, R.id.b63, R.id.b64, R.id.b65, R.id.b66, R.id.b67}};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new Game();

        setContentView(R.layout.activity_main);

        ImageButton button;
        for (int y=0; y<SIZE_Y; y++)
            for (int x=0; x<SIZE_X; x++)
                if (ids[y][x]!=0){
                    button = (ImageButton) findViewById(ids[y][x]);
                    button.setOnClickListener(this);
                }

    }


    public void onClick(View v) {
        int id = ((ImageButton) v).getId();

        int col = deIdentificadorAColumna(id);
        int row = deIdentificadorAFila(id);

        if ( game.sePuedeColocarFicha( col, row ) ) {
            log("Muevo yo");
            game.putTablero( col, row, 1);

            log("Ahora le toca a la maquina");
            game.juegaMaquina();

            log("Dibujo el tablero");
            dibujarTablero();
        }
        else
            Toast.makeText(this, "Posicion no valida", Toast.LENGTH_LONG).show();

    }


    public int deIdentificadorAFila(int id) {
        int out=0;

        for ( int y=0; y<SIZE_Y; y++)
            for ( int x=0; x<SIZE_X; x++)
                if ( findViewById(ids[y][x]).getId() == id ) {
                    out=x;
                    break;
                }

        return out;
     }


    public int deIdentificadorAColumna(int id) {
        int out=0;

        for ( int y=0; y<SIZE_Y; y++)
            for ( int x=0; x<SIZE_X; x++)
                if ( findViewById(ids[y][x]).getId() == id ) {
                    out=y;
                    break;
                }

        return out;

    }

    private void dibujarTablero(){
        ImageButton button;

        for (int y=0; y<SIZE_Y; y++)
            for (int x=0; x<SIZE_X; x++){
                int value = game.getTablero(y, x);

                if ( value != 0 ) {
                    button = (ImageButton) findViewById(ids[y][x]);
                    if ( value == 1 )
                        button.setImageResource( R.drawable.c4_ficha_player1 );
                    else
                        button.setImageResource( R.drawable.c4_ficha_player2 );
                }
            }
    }


    private void log(String text) {
        Log.d("LifeCycleTest", text);
    }
}