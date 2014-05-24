package com.example.socketclient;

import android.app.Activity;
import android.graphics.Color;
import android.os.PowerManager;

/**
 * Created by franqy on 08.03.14.
 */
public class MenuOptions {

    Activity activity;


    public MenuOptions(Activity activity) {
        this.activity = activity;
    }

    private void motyw(String motywValue) {
        if (motywValue.contentEquals("jasny")) {
            activity.findViewById(R.id.mainLay).setBackgroundColor(Color.parseColor("#e3dd21"));
            activity.findViewById(R.id.przyciskiColorTheme).setBackgroundColor(Color.parseColor("#e3dd21"));
        } else {
            activity.findViewById(R.id.mainLay).setBackgroundColor(Color.parseColor("#27201b"));
            activity.findViewById(R.id.przyciskiColorTheme).setBackgroundColor(Color.parseColor("#27201b"));
        }
    }

    public void motywInitiate() {
        motyw(new fileOperation().read("motyw"));
    }

    public void motywSet(String motywValue) {
        motyw(motywValue);
        new fileOperation().write("motyw", motywValue);
    }


    private void wygaszacz(int wartosc, boolean save) {

        switch (wartosc) {
            case 0: {
                MainActivity.screenManager = false;

                if (save)
                    new fileOperation().write("wygaszacz", "wylaczEkran");
                MainActivity.wl.release();

                break;
            }

            case 1: {
                MainActivity.screenManager = true;
                MainActivity.wl = MainActivity.pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "wygas ekran");
                MainActivity.wl.acquire();
                if (save)
                    new fileOperation().write("wygaszacz", "wygasEkran");

                break;
            }

            case 2: {
                MainActivity.screenManager = true;
                MainActivity.wl = MainActivity.pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "full wake lock");
                MainActivity.wl.acquire(MainActivity.wlTimeout);
                if (save)
                    new fileOperation().write("wygaszacz", "zostawWlaczony");

                break;
            }
        }
    }

    public void wygaszaczInitiate() {
        String wartosc = new fileOperation().read("wygaszacz");

        if (wartosc.contentEquals("wylaczEkran")) {
            wygaszacz(0, false);
        } else if (wartosc.contentEquals("wygasEkran")) {
            wygaszacz(1, false);
        } else if (wartosc.contentEquals("zostawWlaczony")) {
            wygaszacz(2, false);
        }
    }


    public void wygaszaczSet(int wygaszaczValue) {

        wygaszacz(wygaszaczValue, true);
    }
}
