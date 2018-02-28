package ap.ky.stopwatch;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Created by kylin25 on 2018/2/20.
 */

public class StopWatch extends RelativeLayout {
    Button btnStart;
    Button btnPause;
    TextView txtTime;
    boolean isStart = false;
    Long startTime,updateTime;

    Handler handler;
    Chronometer chronometer;
    String TAG = "Stopwatch";
    final int MSG = 123;
    public StopWatch(Context context) {
        super(context);
        Log.e(TAG,"StopWatch construtor");
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.stopwatch_layout,null);
        txtTime = (TextView)view.findViewById(R.id.textView);
        btnStart = (Button)view.findViewById(R.id.btnStart);
        btnPause = (Button)view.findViewById(R.id.btnPause);

        handler = new Handler();
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isStart){
                    updateTime = 0L;
                    startTime = System.currentTimeMillis();
                    //handler.postDelayed(runnable,0);
                    handlerMsg.sendEmptyMessage(MSG);
                    isStart =true;
                    btnStart.setText(R.string.stop);
                }else {
                    //handler.removeCallbacks(runnable);
                    handlerMsg.removeMessages(MSG);
                    startTime = 0L;
                    updateTime = 0L;
                    isStart =false;
                    btnStart.setText(R.string.start);
                }
            }
        });
        btnPause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isStart = !isStart;
                startTime = System.currentTimeMillis();
                handlerMsg.sendEmptyMessage(MSG);
            }
        });
        txtTime.setText("00:00:00.000");
        addView(view);

        //
        //startTime = System.currentTimeMillis();
        //updateTime = 0L;

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.e(TAG,"runnable ");
            Long endTime = System.currentTimeMillis() - startTime;

            updateTime +=endTime;
            long tmp =0;
            long ms = updateTime %1000;
            long sec = updateTime / 1000;
            long hour = sec/3600;
            tmp = sec % 3600;
            long minute = tmp /60;
            sec = tmp % 60;

            String time = String.format("%02d:%02d:%02d:%03d",hour,minute,sec,ms);
            txtTime.setText(time);

            startTime = System.currentTimeMillis();
            handler.postDelayed(this,0);
        }
    };

    Handler handlerMsg = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(isStart == false) return;

            Long endTime = System.currentTimeMillis() - startTime;
            updateTime +=endTime;
            long tmp =0;
            long ms = updateTime %1000;
            long sec = updateTime / 1000;
            long hour = sec/3600;
            tmp = sec % 3600;
            long minute = tmp /60;
            sec = tmp % 60;

            String time = String.format("%02d:%02d:%02d.%03d",hour,minute,sec,ms);
            txtTime.setText(time);
            Log.e(TAG,"handlerMsg " + updateTime);
            startTime = System.currentTimeMillis();
            handlerMsg.sendMessageDelayed(obtainMessage(MSG),1);
        }
    };

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Log.e(TAG,"StopWatch onSaveInstanceState");
        Bundle bundle = new Bundle();
        bundle.putParcelable("TIMER",super.onSaveInstanceState());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        Log.e(TAG,"StopWatch onRestoreInstanceState");
    }
}
