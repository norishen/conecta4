package org.wicbo5oel.conecta4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


/**
 * Creado por administrador on 16/11/15.
 */

public class Initial extends Activity implements View.OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.initial);

        // Registro los botones
        Button button = (Button) findViewById(R.id.initialB1);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.initialB2);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.initialB3);
        button.setOnClickListener(this);
    }


    public void onClick(View v) {
        int id = v.getId();

        switch(id) {
            case R.id.initialB1:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.initialB2:
                Toast.makeText(this, "Si, si propiedades...", Toast.LENGTH_LONG).show();
                break;

            case R.id.initialB3:
                exitGame();
                break;
        }

    }

    // Button: Android Back
    public void onBackPressed() {
        exitGame();
    }

    // Salir del juego
    public void exitGame(){
        String title = getResources().getString(R.string.exitApp);

        DialogExit wd = new DialogExit( title );
        wd.show(getFragmentManager(), "EXIT");
    }
}
