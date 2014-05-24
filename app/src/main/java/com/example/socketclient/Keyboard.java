package com.example.socketclient;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.ObjectOutputStream;

/**
 * Created by franqy on 22.04.14.
 */
public class Keyboard {
    public static Activity activity;
    public static Context context;
    public boolean active = false;

    float scale;
    int btnTxtSize;


    enum typ {DUZE, MALE, ZNAKI_SPECJALNE}

    ;

    typ typKlawiatury = typ.MALE;

    static Button[][] keyboardButton;

    ObjectOutputStream oos;


    String keybMale[] = {"qwertyuiop", "asdfghjkl", " zxcvbnm "};


    String keybDuze[] = {"QWERTYUIOP", "ASDFGHJKL", " ZXCVBNM "};

    String keybDol[] = {"A/a", ".?1", "  ", "⇐", "↵"};

    String keybSpecial[] = {"&/:;()-+$\\", "%=<>[]_^~", ".,?!\'\"*#@"};


    LinearLayout[] keyboardLay;


    public Keyboard(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;

        oos = null;

        keyboardButton = new Button[5][10];
        keyboardLay = new LinearLayout[5];


       // scale = context.getResources().getDisplayMetrics().density;
       // btnTxtSize = (int) (15 * scale + 0.5f);
        btnTxtSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, context.getResources().getDisplayMetrics());


        keyboardLay[0] = (LinearLayout) this.activity.findViewById(R.id.Keyboard0lay);
        keyboardLay[1] = (LinearLayout) this.activity.findViewById(R.id.Keyboard1lay);
        keyboardLay[2] = (LinearLayout) this.activity.findViewById(R.id.Keyboard2lay);
        keyboardLay[3] = (LinearLayout) this.activity.findViewById(R.id.Keyboard3lay);
        keyboardLay[4] = (LinearLayout) this.activity.findViewById(R.id.Keyboard4lay);


        preMakeKeyboard();








        /*for(int i=0; i < 10; i++) // where x is the size of the list containing your alphabet.
        {
            Button button = new Button(this.activity);
            String keyb2male = "qwertyuiop";

                button.setId(i+10);
                button.setText(String.valueOf(keyb2male.charAt(i)));




            button.setTextSize(btnTxtSize);
            button.setBackgroundColor(Color.parseColor("#2e2e2e"));
            button.setTextColor(Color.parseColor("#D1D3C6"));

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.FILL_PARENT);

            lp.weight = 1;
            keyboard1lay.addView(button, lp);


        }

    }
*/
    }

    private void preMakeKeyboard() {
        for (int i = 0; i < 5; i++) {
            for (int a = 0; a < 10; a++) {

                if ((i == 2 && a == 9) || (i == 3 && i == 7) || (i == 4 && a == 5)) {
                    break;
                }

                keyboardButton[i][a] = new Button(this.activity);
            }
        }
        makeKeyboard(true);
    }


    protected void makeKeyboard(boolean pre) {


        for (int i = 0; i < 5; i++) {
            for (int a = 0; a < 10; a++) {
                if ((i == 2 && a == 9) || (i == 3 && a == 9) || (i == 4 && a == 5)) {
                    break;
                }


                if (i == 0) {
                    if (a == 9) {
                        keyboardButton[i][a].setText("0");
                    } else {
                        keyboardButton[i][a].setText(String.valueOf(a + 1));
                    }
                } else if (i == 4) {
                    keyboardButton[i][a].setText(keybDol[a]);
                } else {


                    if (typKlawiatury == typ.MALE) {

                        keyboardButton[i][a].setText(String.valueOf(keybMale[i - 1].charAt(a)));
                    } else if (typKlawiatury == typ.DUZE) {
                        keyboardButton[i][a].setText(String.valueOf(keybDuze[i - 1].charAt(a)));
                    } else {
                        keyboardButton[i][a].setText(String.valueOf(keybSpecial[i - 1].charAt(a)));
                    }


                }


                if (pre) {


                    if (i == 0 || i == 2) {
                        keyboardButton[i][a].setBackgroundColor(Color.parseColor("#555555"));
                    } else if (i == 4 && (a == 1 || a == 3)) {
                        keyboardButton[i][a].setBackgroundColor(Color.parseColor("#555555"));
                    } else if (i == 4 && a == 2) {
                        keyboardButton[i][a].setBackgroundColor(Color.parseColor("#888888"));

                    } else {
                        keyboardButton[i][a].setBackgroundColor(Color.parseColor("#2e2e2e"));
                    }


                    keyboardButton[i][a].setTextSize(btnTxtSize);
                    keyboardButton[i][a].setTextColor(Color.parseColor("#D1D3C6"));


                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.FILL_PARENT);
                    lp.weight = 1;
                    if ((i == 0 && a != 10) || (i == 1 && a != 10) || (i == 2 && a != 9) || (i == 3 && a != 9) || (i == 4 && a != 5)) {
                        lp.rightMargin = -(int) (9 * scale + 0.5f);
                    }

                    if (i == 4) {
                        keyboardButton[i][a].setTextSize((int) (10 * scale + 0.5f));
                        if (a == 2) {
                            lp = new LinearLayout.LayoutParams((int) (150 * scale + 0.5f), LinearLayout.LayoutParams.FILL_PARENT);
                        }
                    }
                    keyboardLay[i].addView(keyboardButton[i][a], lp);

                    if (i == 4) {
                        LinearLayout przerwa = new LinearLayout(this.activity);
                        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams((int) (1.5 * scale + 0.5f), LinearLayout.LayoutParams.FILL_PARENT);

                        if (a == 0 || a == 3) {
                            przerwa.setBackgroundColor(Color.parseColor("#222222"));

                        } else if (a == 1 || a == 2) {
                            przerwa.setBackgroundColor(Color.parseColor("#333333"));

                        }
                        keyboardLay[i].addView(przerwa, lp1);
                    }

                    keyboardButton[i][a].setOnClickListener(new KeyboardTouch(i, a));
                }
            }

        }

    }


    public void giveOutputStream(ObjectOutputStream oos) {
        this.oos = oos;

    }


    public Keyboard() {
    }

}
