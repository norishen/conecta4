package org.wicbo5oel.conecta4;

import android.content.Context;
import android.media.MediaPlayer;

public class Music {
	private static MediaPlayer player;

	public static void play(Context context, int id, boolean looping) {
		player = MediaPlayer.create(context, id);
		player.setLooping(looping);
		player.start();
	}

	public static void stop(Context context) {
		if (player != null) {
			player.stop();
			player.release();
			player = null;
		}
	}
}