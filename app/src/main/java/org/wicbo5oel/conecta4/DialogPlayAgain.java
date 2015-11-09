package org.wicbo5oel.conecta4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by jcc314 on 9/11/15.
 */
public class DialogPlayAgain extends DialogFragment {
    String title;

    public DialogPlayAgain( String title ){
        super();
        this.title = title;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final MainActivity caller = (MainActivity)getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(title)
                .setMessage(R.string.playAgain)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        caller.newGame();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        caller.finish();
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }
}
