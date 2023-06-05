package com.example.objetivossaludables.manager.media;

import static android.os.VibrationEffect.DEFAULT_AMPLITUDE;
import static android.os.VibrationEffect.createOneShot;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Vibrator;

import com.example.objetivossaludables.R;
import com.example.objetivossaludables.manager.sharedpreferences.SettingPreferences;

public class MediaManager {

    private static MediaPlayer sonido;
    private static Vibrator vibrator;

    public static void exeMedia(Context context) {
        playSound(context);
        vibrate(context);
    }

    public static void playSound(Context c) {
        if (new SettingPreferences(c).getSoundState()) {
            sonido = MediaPlayer.create(c, R.raw.sonido1);
            sonido.start();
        }
    }

    public static void stopSound() {
        if (sonido != null) {
            sonido.stop();
            sonido.release();
            sonido = null;
        }
    }

    public static void vibrate(Context c) {
        if (new SettingPreferences(c).getVibrateState()) {
            vibrator = (Vibrator) c.getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                vibrator.vibrate(createOneShot(500, DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(500);
            }
        }
    }

    public static void playSoundWithoutVerification(Context c) {
        sonido = MediaPlayer.create(c, R.raw.sonido1);
        sonido.start();
    }

    public static void vibrateWithoutVerification(Context c) {
        vibrator = (Vibrator) c.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            vibrator.vibrate(createOneShot(500, DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(500);
        }
    }

    public static void stopVibrate() {
        if (vibrator != null) {
            vibrator.cancel();
        }
    }
}
