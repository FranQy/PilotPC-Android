package com.example.socketclient;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SlidingDrawer;
import android.widget.TabHost;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;


/**
 * Created by franqy on 12.10.13.
 * MENU
 */
public class menu {

    ViewGroup mContainerView, infoContainerView, wyswietlanieContainerView, motywContainerView;

    //  static View pclistView;


    static ViewGroup nameView, infoView, wyswietlanieView, motywView;

    //  Spinner wyswietlanieCzasSpinner, wyswietlanieTypSpinner;

    ClickableSlidingDrawer sliding;

    //ViewFlipper connectFlipper;

    //  fileOperation file;

    static MenuOptions menuOptions = null;


    static DatagramSocket broadcast = null;


    static QrReader Qr;

    boolean connectMenuIsOpened = false;

    //   static Thread PClistThread = null;

    public static Activity activity;
    public static Context kontext;

    TabHost tabHost;


    public menu(final Activity activity, Context kontext) {
        this.activity = activity;
        this.kontext = kontext;

        menuOptions = new MenuOptions(activity);


        try {
            broadcast = new DatagramSocket(8753);
            broadcast.setBroadcast(true);

        } catch (SocketException e) {
            e.printStackTrace();
        }


        Button nameButton = (Button) this.activity.findViewById(R.id.buttonNameChange);


        sliding = (ClickableSlidingDrawer) this.activity.findViewById(R.id.slidingDrawer);


        /**
         *
         * containers
         *
         */

        {
            mContainerView = (ViewGroup) this.activity.findViewById(R.id.container2);
            infoContainerView = (ViewGroup) this.activity.findViewById(R.id.infoContainer);
            wyswietlanieContainerView = (ViewGroup) this.activity.findViewById(R.id.wyswietlanieContainer);
            motywContainerView = (ViewGroup) this.activity.findViewById(R.id.motywContainer);


            nameView = (ViewGroup) LayoutInflater.from(this.activity).inflate(
                    R.layout.list_item_example2, mContainerView, false);

            infoView = (ViewGroup) LayoutInflater.from(this.activity).inflate(
                    R.layout.authors, infoContainerView, false);

            wyswietlanieView = (ViewGroup) LayoutInflater.from(this.activity).inflate(
                    R.layout.wyswietlanie, wyswietlanieContainerView, false);

            motywView = (ViewGroup) LayoutInflater.from(this.activity).inflate(
                    R.layout.motywmenu, motywContainerView, false);
        }


        Qr = new QrReader(kontext, activity); // inicjuje Qr readera


        /**
         *
         * Slidier close listener
         *
         * on close cancell all menus
         *
         */

        sliding.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                closeMENUall();

            }
        });


        /**
         *
         * TabHost do zmiany PC
         *
         */
        {


            tabHost = (TabHost) nameView.findViewById(R.id.tabHost);
            tabHost.setup();


            TabHost.TabSpec spec1 = tabHost.newTabSpec("TAB 1");

            spec1.setContent(R.id.tab2);
            spec1.setIndicator("write ");


            TabHost.TabSpec spec2 = tabHost.newTabSpec("TAB 2");
            spec2.setIndicator("serch");
            spec2.setContent(R.id.tab1);


            TabHost.TabSpec spec3 = tabHost.newTabSpec("TAB 3");
            spec3.setContent(R.id.tab3);
            spec3.setIndicator("Qr ");

            tabHost.addTab(spec1);
            tabHost.addTab(spec2);
            tabHost.addTab(spec3);


            tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
                @Override
                public void onTabChanged(String s) {
                    Log.d("TabHost", s);

                    if (s.contains("TAB 1") || s.contains("TAB 2")) {
                        Qr.releaseCamera();
                    } else {
                        Qr.getIn();

                    }
                }
            });

        }


        nameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mContainerView.getChildCount() == 0) {

                    addConnect();


                } else {
                    mContainerView.removeView(nameView);


                }
            }
        });


        Button wyswietlanieButton = (Button) this.activity.findViewById(R.id.buttonWyswietlanie);
        wyswietlanieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wyswietlanieContainerView.getChildCount() == 0) {
                    addWyswietlanie();
                } else {
                    wyswietlanieContainerView.removeView(wyswietlanieView);
                }
            }
        });

        Button motywButton = (Button) this.activity.findViewById(R.id.buttonMotyw);
        motywButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (motywContainerView.getChildCount() == 0) {
                    addMotyw();
                } else {
                    motywContainerView.removeView(motywView);
                }
            }
        });


        Button infoButton = (Button) this.activity.findViewById(R.id.buttonInfo);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (infoContainerView.getChildCount() == 0) {
                    addInfo();
                } else {
                    infoContainerView.removeView(infoView);
                }
            }
        });

    }


    private void addConnect() {

        connectMenuIsOpened = true;

        nameView.findViewById(R.id.buttondel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeConnect();

               /* if(Qr!=null)
                Qr.releaseCamera();
                Qr=null;*/
            }
        });


        final ListView PClist = (ListView) nameView.findViewById(R.id.PClist);


        PClist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String item = ((TextView) view).getText().toString();
                if (new fileOperation().read(item) != "brak") {
                    MainActivity.lacz(new fileOperation().read(item));
                    new fileOperation().write("server", new fileOperation().read(item));

                } else {
                    // Created a new Dialog
                    final Dialog dialog = new Dialog(activity);
                    dialog.setTitle("Wpisz kod");
                    dialog.setContentView(R.layout.zly_kod);

                    Button dialogAnuluj = (Button) dialog.findViewById(R.id.dialogKodAnuluj);
                    final Button dialogOk = (Button) dialog.findViewById(R.id.dialogKodOk);


                    dialogAnuluj.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });


                    dialogOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            new fileOperation().write("server", item + ":8753:" + ((EditText) dialog.findViewById(R.id.dialogKod)).getText().toString());
                            new fileOperation().write(item, item + ":8753:" + ((EditText) dialog.findViewById(R.id.dialogKod)).getText().toString());
                            MainActivity.lacz(item + ":8753:" + ((EditText) dialog.findViewById(R.id.dialogKod)).getText().toString());

                            dialog.cancel();
                        }
                    });


                    dialog.show();


                }

            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {


                Log.d("broadcast", "ok");


                String[] values = new String[5];
                values[0] = "";
                values[1] = "";
                values[2] = "";
                values[3] = "";
                values[4] = "";


                while (connectMenuIsOpened) {


                    try {
                        byte[] buf = new byte[256];

                        DatagramPacket packet = new DatagramPacket(buf, buf.length);

                        broadcast.receive(packet);


                        String rec = new String(packet.getData());


                        rec = rec.split("aaaa")[1];
                        rec = rec.trim();

                        if (!values[0].contains(rec)) {

                            if (values[0].isEmpty()) {
                                values[0] = rec;
                            } else if (values[1].isEmpty()) {
                                values[1] = rec;
                            } else if (values[2].isEmpty()) {
                                values[2] = rec;
                            }


                            final ArrayList<String> list = new ArrayList<String>();
                            for (int i = 0; i < values.length; ++i) {
                                list.add(values[i]);
                            }

                            final ArrayAdapter aa = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, list);

                            activity.runOnUiThread(new Runnable() {
                                public void run() {
                                    PClist.setAdapter(aa);


                                }
                            });

                            Log.d("broadcast", "ok");
                        }


                        Log.d("broadcast", rec);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }


            }
        }).start();


        final EditText servPass = (EditText) nameView.findViewById(R.id.servPass);

        servPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                // if (actionId == EditorInfo.IME_ACTION_DONE) {
                new fileOperation().write("server", ((EditText) nameView.findViewById(R.id.servName)).getText().toString() + ":8753:" + servPass.getText().toString());

                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

                closeMENUall();
                close();
                //  }
                return false;
            }
        });


        nameView.findViewById(R.id.saveName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  pilot Pilot = new pilot();

                new fileOperation().write("server", ((EditText) nameView.findViewById(R.id.servName)).getText().toString() + ":8753:" + servPass.getText().toString());
                MainActivity.lacz(((EditText) nameView.findViewById(R.id.servName)).getText().toString() + ":8753:" + servPass.getText().toString());


                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

                closeMENUall();
                close();
//TODO: check Null pointer...


            }
        });


        // Qr.ad();


        mContainerView.addView(nameView, 0);
        tabHost.setCurrentTab(1);


    }

    private void addWyswietlanie() {


        wyswietlanieView.findViewById(R.id.closeWyswietlanie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wyswietlanieContainerView.removeView(wyswietlanieView);
            }
        });

        wyswietlanieContainerView.addView(wyswietlanieView, 0);


        RadioButton wylaczEkran = (RadioButton) wyswietlanieView.findViewById(R.id.radioWylaczEkran);
        RadioButton wygaSEkran = (RadioButton) wyswietlanieView.findViewById(R.id.radioWygasPodswietlenie);
        RadioButton zostawWlaczony = (RadioButton) wyswietlanieView.findViewById(R.id.radioPozostawWlaczonyEkran);


        String wartosc = new fileOperation().read("wygaszacz");

        if (wartosc.contentEquals("wylaczEkran")) {
            wylaczEkran.setChecked(true);
        } else if (wartosc.contentEquals("wygasEkran")) {
            wygaSEkran.setChecked(true);
        } else if (wartosc.contentEquals("zostawWlaczony")) {
            zostawWlaczony.setChecked(true);
        }


        wylaczEkran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                menuOptions.wygaszaczSet(0);
            }
        });

        wygaSEkran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                menuOptions.wygaszaczSet(1);
            }
        });

        zostawWlaczony.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                menuOptions.wygaszaczSet(2);
            }
        });


    }

    private void addMotyw() {


        motywView.findViewById(R.id.closeMotywMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                motywContainerView.removeView(motywView);
            }
        });

        motywContainerView.addView(motywView, 0);

        RadioButton motywCiemny = (RadioButton) motywView.findViewById(R.id.motywCiemny);
        RadioButton motywJasny = (RadioButton) motywView.findViewById(R.id.motywJasny);


        if (new fileOperation().read("motyw").contentEquals("jasny")) {
            motywJasny.setChecked(true);
        } else {
            motywCiemny.setChecked(true);
        }


        motywCiemny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuOptions.motywSet("ciemny");
            }
        });

        motywJasny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuOptions.motywSet("jasny");
            }
        });

    }

    private void addInfo() {

        infoView.findViewById(R.id.closeInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoContainerView.removeView(infoView);
            }
        });
        infoContainerView.addView(infoView, 0);


    }

    private void closeConnect() {
        Qr.releaseCamera();
        mContainerView.removeView(nameView);

        connectMenuIsOpened = false;

    }

    public void closeMENUall() {

       /* if(Qr!=null)
        Qr.releaseCamera();
        Qr=null;*/

        if (mContainerView.getChildCount() == 1) {
            closeConnect();

        }
        if (wyswietlanieContainerView.getChildCount() == 1) {
            wyswietlanieContainerView.removeView(wyswietlanieView);
        }
        if (motywContainerView.getChildCount() == 1) {
            motywContainerView.removeView(motywView);
        }
        if (infoContainerView.getChildCount() == 1) {
            infoContainerView.removeView(infoView);

        }


    }

    public boolean closeMENU() {
        Qr.releaseCamera();
       /* if(Qr!=null)
        Qr.releaseCamera();
        Qr=null;*/
        if (infoContainerView.getChildCount() == 1) {

            infoContainerView.removeView(infoView);
            return true;
        } else if (motywContainerView.getChildCount() == 1) {
            motywContainerView.removeView(motywView);
            return true;
        } else if (wyswietlanieContainerView.getChildCount() == 1) {
            wyswietlanieContainerView.removeView(wyswietlanieView);
            return true;
        } else if (mContainerView.getChildCount() == 1) {
            closeConnect();
            return true;
        } else if (sliding.isOpened()) {
            sliding.animateClose();
            return true;
        }
        return false;
    }


    public void openclose() {
        sliding.animateToggle();
    }

    public void close() {
        if (sliding.isOpened())
            sliding.animateClose();
    }


    static public void polaczono(String Host) {
        ((TextView) infoView.findViewById(R.id.stanPolaczenia)).setText(activity.getString(R.string.menu_info_state_connected));
        ((TextView) infoView.findViewById(R.id.stanPolaczenia)).setTextColor(Color.GREEN);

        ((TextView) infoView.findViewById(R.id.hostTxt)).setText("-Host: " + Host);
    }

    static public void rozlaczono() {
        ((TextView) infoView.findViewById(R.id.stanPolaczenia)).setText(activity.getString(R.string.menu_info_state_disconnected));
        ((TextView) infoView.findViewById(R.id.stanPolaczenia)).setTextColor(Color.RED);

        ((TextView) infoView.findViewById(R.id.hostTxt)).setText("-Host: b/d");
    }


    public menu() {

    }
}
