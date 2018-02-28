package ap.ky.stopwatch;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by kylin25 on 2018/2/20.
 */

public class CntDownTimer extends RelativeLayout {
    TextView txtTime;
    Button [] buttons;
    int[] btnResids = {R.id.btn0,
            R.id.btn1,R.id.btn2,R.id.btn3,
            R.id.btn4,R.id.btn5,R.id.btn6,
            R.id.btn7,R.id.btn8,R.id.btn9};
    String TAG = "CountDownTimer";
    int countTime = 0;
    Button btnStart;
    Button btnDel;
    String strTime = "00:00:00";
    public CntDownTimer(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.countdown_timer,null);

        txtTime = (TextView) view.findViewById(R.id.txtCountDown);
        txtTime.setText(strTime);

        buttons = new Button[10];

        for(int i = 0;i<10;i++) {
            buttons[i] = view.findViewById(btnResids[i]);
            buttons[i].setId(i);
            buttons[i].setOnClickListener(onClickListener);
        }
        btnStart = view.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(onStartClickListener);

        btnDel = view.findViewById(R.id.btnDel);
        btnDel.setOnClickListener(onDelClickListener);
        addView(view);
    }
    void showTime(long countTime){
        String tmp = String.format("%06d",countTime);
        strTime = tmp.substring(0,2) + ":" + tmp.substring(2,4) + ":" + tmp.substring(4,6);

        txtTime.setText(strTime);
    }
    long toCountDown(long countTime){
        long hour = countTime/3600;
        countTime = countTime % 3600;
        long min = countTime/60;
        long sec = countTime%60;

        String strTime = String.format("%02d:%02d:%02d",hour,min,sec);

        txtTime.setText(strTime);
        return 0L;
    }

    private boolean isStart=false;
    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isStart == false) {
                Log.e(TAG, "view id " + v.getId());
                if (String.valueOf(countTime).length() <= 5) {
                    countTime = countTime * 10 + v.getId();
                }
                showTime(countTime);
            }
        }
    };

    OnClickListener onDelClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isStart == false) {
                countTime /= 10;
                showTime(countTime);
            }
        }
    };

    OnClickListener onStartClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            final String[] times = strTime.split(":");
            int hour = Integer.parseInt(times[0]);
            int min = Integer.parseInt(times[1]);
            int sec = Integer.parseInt(times[2]);
            long totalsec = hour * 3600 + min * 60 + sec;
            new CountDownTimer(totalsec*1000 + 499 , 1000) {

                public void onTick(long millisUntilFinished) {
                    //txtTime.setText("" + millisUntilFinished / 1000);
                    toCountDown(millisUntilFinished/1000);
                    Log.e(TAG,"" + millisUntilFinished);
                }

                public void onFinish() {
                    //txtTime.setText("done!");
                    countTime = 0;
                    btnStart.setEnabled(true);
                    btnDel.setEnabled(true);
                    isStart = false;
                }
            }.start();
            btnStart.setEnabled(false);
            btnDel.setEnabled(false);
            isStart = true;

        }
    };


}
