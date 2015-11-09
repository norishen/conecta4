package org.wicbo5oel.conecta4;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

//CAMBIO Crear dialogo con texto variable
public class WarnDialog extends DialogFragment {
	String title;
	String message;
	
	public WarnDialog(String title, String message) {
		super();
		this.title = title;		
		this.message = message;		
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Activity caller = getActivity();
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(title)
			   .setMessage(message)
			   .setPositiveButton(android.R.string.yes, new OnClickListener() {
				
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.setNegativeButton(android.R.string.no, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						caller.finish();
						dialog.dismiss();
					}
				});
				
		return builder.create();
	}

}
