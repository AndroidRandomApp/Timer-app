package com.aakash.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String ResetMinute = "00";
    public static final String ResetSecond = "00";

    EditText etMinute, etSecond;
    Button btnStart, btnReset;
    TextView tvMinute, tvSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMinute = findViewById(R.id.etMinute);
        etSecond = findViewById(R.id.etSecond);
        tvMinute = findViewById(R.id.tvMinute);
        tvSecond = findViewById(R.id.tvSeconds);
        btnReset = findViewById(R.id.btnReset);
        btnStart = findViewById(R.id.btnStart);
        long additionalTime;

        long pauseStart;


        // int MinuteInSeconds = minute * 60;
        // 1 second=1000ms

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //converting edit texts to integers minutes and seconds
                //NumberFormatException
                //minutes text time(in seconds)/60
                //seconds text time(in seconds)%60
                int minute;
                int Second;
                boolean Error = false;
                Timer tA = new Timer();
                // this part of the code is to avoid the app crashing that happens when the edit text values are kept empty
                try {
                    Integer.parseInt(etMinute.getText().toString());
                } catch (Exception e) {
                    Error = true;
                    Second = Integer.parseInt(etSecond.getText().toString());
                    tA.execute(0, Second);
                }
                try {
                    Integer.parseInt(etSecond.getText().toString());
                } catch (Exception e) {
                    Error = true;
                    minute = Integer.parseInt(etMinute.getText().toString());
                    tA.execute(minute, 0);
                }
                // this part dont work
                //the app crashes when both the edit texts are empty can't find a logic for that
                if (etMinute.getText().toString().equals("")) {
                    if (etSecond.getText().toString().equals("")) {
                        try {
                            Integer.parseInt(etMinute.getText().toString());
                            Integer.parseInt(etSecond.getText().toString());
                        } catch (Exception e) {
                            Error = true;
                            Toast.makeText(MainActivity.this, "Try Entering  both the inputs", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                if (!Error) {
                    minute = Integer.parseInt(etMinute.getText().toString());
                    Second = Integer.parseInt(etSecond.getText().toString());

                    tA.execute(minute, Second); //always remember the order in which you send the parameters inside the execute function
                }
            }
        });
        //reset button dosen't works no loggic added here
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvMinute.setText(ResetMinute);
                tvSecond.setText(ResetSecond);
            }
        });
    }

    class Timer extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... integers) {
            int m = integers[0];
            int s = integers[1];

            if (s == 0) {
                // this is the case when there is 0 sec inside the timer  and only has the minutes
                for (int i = m; i > 0; i--) {
                    for (int j = 60; j >= 0; j--) {
                        publishProgress(i - 1, j);
                        wait1Sec();
                    }
                }
            }
            else {
                for (int i = s; i >= 0; i--) {
                    publishProgress(m, i);
                    wait1Sec();
                }

                for (int i = m; i > 0; i--) {
                    for (int j = 59; j >= 0; j--) {
                        publishProgress(i - 1, j);
                        wait1Sec();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tvMinute.setText(String.valueOf(values[0]));
            tvSecond.setText(String.valueOf(values[1]));

        }


    }

    void wait1Sec() {
        //Storing the start time
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() < startTime + 1000) ;
    }

}
