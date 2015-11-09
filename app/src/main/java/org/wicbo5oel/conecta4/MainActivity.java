package org.wicbo5oel.conecta4;

import android.app.Activity;
//import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;
import android.util.Log;

/**
 * Clase principal
 */
public class MainActivity extends Activity implements OnClickListener {
    private Game game;
    static final int SIZE_Y = 6;
    static final int SIZE_X = 7;

    static int STATUS = 0;

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

        setContentView(R.layout.activity_main);

        game = new Game();

        ImageButton button;
        for (int y=0; y<SIZE_Y; y++)
            for (int x=0; x<SIZE_X; x++)
                if (ids[y][x]!=0){
                    button = (ImageButton) findViewById(ids[y][x]);
                    button.setOnClickListener(this);
                }

    }

    // Nuevo Juego
    public void newGame(){
        STATUS = 0;

        game.restart();
        dibujarTablero();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuPreferences:
                Toast.makeText(this, "Test_Preferences", Toast.LENGTH_LONG).show();
                return true;

            case R.id.menuAbout:
                startActivity(new Intent(this, About.class));
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }



    public void onClick(View v) {
        if (STATUS != 0) {
            Toast.makeText(this, getResources().getString(R.string.gameEnd), Toast.LENGTH_LONG).show();
            return;
        }

        int id = v.getId();

        int col = deIdentificadorAColumna(id);
        int row = deIdentificadorAFila(id);

        // HUMAN
        if ( game.sePuedeColocarFicha( col, row ) ) {
            game.putTablero(col, row, 1);
            dibujarTablero();

            if (game.comprobarCuatro(1)) {
                STATUS = 1;

                String title = getResources().getString(R.string.youWin);
                DialogPlayAgain wd = new DialogPlayAgain( title );
                wd.show(getFragmentManager(), "PLAY_AGAIN");

                return;
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.invalidPos), Toast.LENGTH_LONG).show();
            return;
        }


        // COMPUTER
        game.juegaMaquina();
        dibujarTablero();

        if (game.comprobarCuatro(2)) {
            STATUS = 2;

            String title = getResources().getString(R.string.youLose);
//            String title = "Gana la maquina.";
            DialogPlayAgain wd = new DialogPlayAgain( title );
            wd.show(getFragmentManager(), "PLAY_AGAIN");
        }
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

                button = (ImageButton) findViewById(ids[y][x]);
                if ( value != 0 ) {
                    if ( value == 1 )
                        button.setImageResource( R.drawable.c4_ficha_player1 );
                    else
                        button.setImageResource( R.drawable.c4_ficha_player2 );
                }
                else
                    button.setImageResource( R.drawable.c4_sin_ficha);
            }
    }


    private void log(String text) {
        Log.d("LifeCycleTest", text);
    }
}