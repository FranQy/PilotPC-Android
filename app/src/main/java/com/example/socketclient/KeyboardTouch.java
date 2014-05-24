package com.example.socketclient;


import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.io.IOException;

/**
 * Created by franqy on 26.04.14.
 */
public class KeyboardTouch extends Keyboard implements View.OnClickListener {


    int i, a, key;
    TCP_Data data;

    int keyH;
    int keyW;
    int keyColor;

    public KeyboardTouch(int i, int a) {

        this.i = i;
        this.a = a;
        data = new TCP_Data();
        data.type = TCP_Data.typ.KEYBOARD;
    }


    @Override
    public void onClick(View viev) {

        key = keyboardButton[i][a].getText().hashCode();


        Animation upDown = AnimationUtils.loadAnimation(context, R.anim.updown);


        keyboardButton[i][a].startAnimation(upDown);


       /*keyColor = keyboardButton[i][a].getDrawingCacheBackgroundColor();

        keyboardButton[i][a].setBackgroundColor(Color.parseColor("#888888"));






        new Timer().schedule(new TimerTask(){
            @Override
            public void run() {
            changeSizeToOrginal();

            }
        }, 100);
*/


        MainActivity.blad.setText(String.valueOf(key));


        if (key == 64019) {
            if (typKlawiatury == typ.MALE) {
                typKlawiatury = typ.DUZE;
                shakeAnim();
            } else {
                typKlawiatury = typ.MALE;
                shakeAnim();
            }

            makeKeyboard(false);
        } else if (key == 46208) {
            if (typKlawiatury == typ.ZNAKI_SPECJALNE) {
                typKlawiatury = typ.MALE;
                shakeAnim();
            } else {
                typKlawiatury = typ.ZNAKI_SPECJALNE;
                shakeAnim();
            }
            makeKeyboard(false);

        } else {


            if (key == 1024)
                data.key = 32;
            else if (key == 8656)
                data.key = 8;
            else if (key == 8629)
                data.key = 10;
            else
                data.key = key;


            if (MainActivity.polaczony)
                send(data);
        }
        vibrate();


    }


    private void changeSizeToOrginal() {
        activity.runOnUiThread(new Runnable() {
            public void run() {

                keyboardButton[i][a].setBackgroundColor(keyColor);
            }
        });
    }


    private void send(TCP_Data data) {
        try {
            MainActivity.oos.writeObject(data);
            MainActivity.oos.reset();
            MainActivity.oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Vibrator v = (Vibrator) Pilot.activity.getSystemService(Context.VIBRATOR_SERVICE);
        int VIBRATION_TIME = 50;
        v.vibrate(VIBRATION_TIME);

    }


    private void vibrate() {
        if (key != 32) {
            Vibrator v = (Vibrator) Pilot.activity.getSystemService(Context.VIBRATOR_SERVICE);
            int VIBRATION_TIME = 30;
            v.vibrate(VIBRATION_TIME);
        }
    }


    private void shakeAnim() {
        for (int i = 1; i < 4; i++) {
            for (int a = 0; a < 10; a++) {
                if ((i == 2 && a == 9) || (i == 3 && a == 9) || (i == 4 && a == 5)) {
                    break;
                }

                if ((i + a) % 2 == 0) {
                    Animation shakeRL = AnimationUtils.loadAnimation(context, R.anim.shake_h_rl);
                    keyboardButton[i][a].startAnimation(shakeRL);
                } else {
                    Animation shakeLR = AnimationUtils.loadAnimation(context, R.anim.shake_h_lr);
                    keyboardButton[i][a].startAnimation(shakeLR);
                }


            }
        }
    }
}