package org.wicbo5oel.conecta4;

import android.app.Activity;
//import android.content.Intent;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
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

    //---------------------------------------------------------
    // Candidatos a preferencias
    //---------------------------------------------------------
    // tipos: 1 humano/maquina, 2: humano/humano
    private int tipoJuego;
    private String namePlayer[];
    //---------------------------------------------------------

    private int turnoJuego;

    private final int statusHeader = R.id.statusHeader;

    private final int ids [][] = {
            {R.id.b11, R.id.b12, R.id.b13, R.id.b14, R.id.b15, R.id.b16, R.id.b17},
            {R.id.b21, R.id.b22, R.id.b23, R.id.b24, R.id.b25, R.id.b26, R.id.b27},
            {R.id.b31, R.id.b32, R.id.b33, R.id.b34, R.id.b35, R.id.b36, R.id.b37},
            {R.id.b41, R.id.b42, R.id.b43, R.id.b44, R.id.b45, R.id.b46, R.id.b47},
            {R.id.b51, R.id.b52, R.id.b53, R.id.b54, R.id.b55, R.id.b56, R.id.b57},
            {R.id.b61, R.id.b62, R.id.b63, R.id.b64, R.id.b65, R.id.b66, R.id.b67}};

    private final int statusFooter = R.id.statusFooter;

    private int score1;
    private int score2;

    private int colorPlayer1;
    private int colorPlayer2;

    //--------------------------------------------------------------
    // CICLO DE VIDA
    //--------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        game = new Game();
        namePlayer = new String[2];

        ImageButton button;
        for (int y=0; y<SIZE_Y; y++)
            for (int x=0; x<SIZE_X; x++)
                if (ids[y][x]!=0){
                    button = (ImageButton) findViewById(ids[y][x]);
                    button.setOnClickListener(this);
                }

        score1 = 0;
        score2 = 0;

        newGame();
    }

    // omResume

    @Override
    protected void onResume(){
        super.onResume();
        Boolean play = false;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if (sharedPreferences.contains(CCCPreference.PLAY_MUSIC_KEY))
            play = sharedPreferences.getBoolean(CCCPreference.PLAY_MUSIC_KEY,CCCPreference.PLAY_MUSIC_DEFAULT);

        if ( play )
            Music.play(this, R.raw.funkandblues);

        if (sharedPreferences.contains(CCCPreference.PLAYER_KEY))
            namePlayer[0] = sharedPreferences.getString(CCCPreference.PLAYER_KEY, CCCPreference.PLAYER_DEFAULT);

        dibujarTablero();
    }

    // onPause
    @Override
    protected void onPause(){
        super.onPause();
        Music.stop(this);
    }

    @Override
    public void onSaveInstanceState (Bundle outState) {
        outState.putString("TABLERO", game.tableroToString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState (Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        String grid = savedInstanceState.getString("TABLERO");
        game.stringToTablero(grid);

        dibujarTablero();
    }

    //-----------------------------------------------------------------------------------
    // Menus
    //-----------------------------------------------------------------------------------
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
            case R.id.menu1About:
                startActivity(new Intent(this, About.class));
                return true;

            case R.id.menu1Settings:
                startActivity(new Intent(this, CCCPreference.class));

        }

        return super.onMenuItemSelected(featureId, item);
    }
    //-----------------------------------------------------------------------------------



    // Option Button: Exit Game
    public void buttonGameBack(View v){
        exitGame();
    }

    // Option Button: New game
    public void buttonGameAgain(View v){
        newGame();
    }


    //-----------------------------------------------------------------------------------
    // Botones
    //-----------------------------------------------------------------------------------

    // Button: Android Back
    public void onBackPressed() {
        exitGame();
    }

    // Gestion de las pulsaciones
    public void onClick(View v) {
        if (STATUS != 0) {
            Toast.makeText(this, getResources().getString(R.string.gameEnd), Toast.LENGTH_LONG).show();
            return;
        }

        int id = v.getId();

        int col = deIdentificadorAColumna(id);
        int row = deIdentificadorAFila(id);


        // HUMAN
        if (game.sePuedeColocarFicha(col, row)) {
            game.putTablero(col, row, turnoJuego);

            dibujarTablero();
            comprobarJugada();
        } else {
            Toast.makeText(this, getResources().getString(R.string.invalidPos), Toast.LENGTH_LONG).show();
            return;
        }

        // COMPUTER
        if (tipoJuego == 1 && STATUS == 0) {
            game.juegaMaquina();
            dibujarTablero();

            comprobarJugada();
        }
    }
    //-----------------------------------------------------------------------------------


    //---------------------------------------------------
    // New Game
    public void newGame(){
        //--------------------------------------------
        // Properties
        //--------------------------------------------
        tipoJuego = 1;
        if ( tipoJuego == 1 ) {
            if ( getPlayerName().equals( "" ) )
                namePlayer[0] = getResources().getString(R.string.playerHUMAN);
            else
                namePlayer[0] = getPlayerName();

            namePlayer[1] = getResources().getString(R.string.playerCPU);
        } else {
            namePlayer[0] = getResources().getString(R.string.player1);
            namePlayer[1] = getResources().getString(R.string.player2);
        }

        // Colores por defecto
        colorPlayer1=1;
        colorPlayer2=4;
        //--------------------------------------------


        game.restart();
        dibujarTablero();

        ImageButton statusB = (ImageButton)findViewById(R.id.b1Foot);
        statusB.setVisibility(TableRow.INVISIBLE);
        statusB = (ImageButton)findViewById(R.id.b2Foot);
        statusB.setVisibility(TableRow.INVISIBLE);

        STATUS = 0;
        turnoJuego = 1;
        statusTurnoJuego();
    }

    // Exit Game
    public void exitGame(){
        String title;

        if ( STATUS == 0 )
            title = getResources().getString(R.string.exitGame);
        else
            title = getResources().getString(R.string.exitScore);


        DialogExit wd = new DialogExit( title );
        wd.show(getFragmentManager(), "EXIT");
    }
    //-----------------------------------------------------------------------------------


    //-----------------------------------------------------------------------------------
    // Recupero preferencias

    public String getPlayerName(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String name = sharedPreferences.getString(CCCPreference.PLAYER_KEY, CCCPreference.PLAYER_DEFAULT);
        return name;
    }
    //-----------------------------------------------------------------------------------


    //-----------------------------------------------------------------------------------
    // Logica del juego
    //-----------------------------------------------------------------------------------
    public void comprobarJugada(){
        TextView statusH = (TextView) findViewById(statusHeader);
        TextView statusF = (TextView) findViewById(statusFooter);

        if (game.comprobarCuatro(turnoJuego)) {
            STATUS = turnoJuego;
            dibujarGanador();

            //  GONE (8) - INVISIBLE (4) - VISIBLE (0)
            ImageButton statusB1 = (ImageButton)findViewById(R.id.b1Foot);
            ImageButton statusB2 = (ImageButton)findViewById(R.id.b2Foot);
            statusB1.setVisibility(TableRow.VISIBLE);
            statusB2.setVisibility(TableRow.VISIBLE);

            // Informacion de jugador ganador
            String title = getResources().getString(R.string.playerWin)+" "+namePlayer[turnoJuego-1];
            statusH.setText( title );
            statusF.setText( getResources().getString(R.string.playAgain) );

            if ( turnoJuego == 1 ) {
                TextView statusScore = (TextView)findViewById(R.id.score1);
                score1++;
                statusScore.setText( String.format("%02d", score1) );
            } else {
                TextView statusScore = (TextView)findViewById(R.id.score2);
                score2++;
                statusScore.setText( String.format("%02d", score2) );
            }
        } else {

            if (turnoJuego == 1)
                turnoJuego = 2;
            else
                turnoJuego = 1;

            statusTurnoJuego();
        }
    }


    private void statusTurnoJuego(){
        TextView statusH = (TextView) findViewById(statusHeader);
        TextView statusF = (TextView) findViewById(statusFooter);


        // Informacion de proximo jugador
        statusF.setText( getResources().getString(R.string.playerRun)+" "+namePlayer[turnoJuego-1] );
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
                switch ( value ) {
                    case 0:
                        button.setImageResource(R.drawable.c4_sin_ficha);
                        break;
                    case 1:
                        button.setImageResource(getResources().getIdentifier("c4_ficha_color" + Integer.toString(colorPlayer1), "drawable", getPackageName()));
                        break;
                    case 2:
                        button.setImageResource(getResources().getIdentifier("c4_ficha_color" + Integer.toString(colorPlayer2), "drawable", getPackageName()));
                        break;
                }
            }
    }

    private void dibujarGanador() {
        ImageButton button;

        log("Turno: " + Integer.toString(turnoJuego));

        for (int y = 0; y < SIZE_Y; y++)
            for (int x = 0; x < SIZE_X; x++)
                if ( game.getGanador(y, x) == turnoJuego ) {
                    button = (ImageButton) findViewById(ids[y][x]);

                    // NOTA
                    // La linea inferior equivale a poner el recurso directamente lo que
                    // permite cierto dinamismo, pues podemos convertir variables numericas
                    // y crear la cadena de Id que necesita set<uno>Resource( R.deawable.c4_ficha_player(1 o 2)_gana )
                    switch ( turnoJuego ) {
                        case 0:
                            button.setImageResource(R.drawable.c4_sin_ficha);
                            break;
                        case 1:
                            button.setImageResource(getResources().getIdentifier("c4_ficha_color" + Integer.toString(colorPlayer1) + "_gana", "drawable", getPackageName()));
                            break;
                        case 2:
                            button.setImageResource(getResources().getIdentifier("c4_ficha_color" + Integer.toString(colorPlayer2) + "_gana", "drawable", getPackageName()));
                            break;
                    }

                }




    }
    //-----------------------------------------------------------------------------------


    private void log(String text) {
        Log.d("LifeCycleTest", text);
    }
}