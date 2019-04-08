package chinhsuaanh.donglv.com.freakingmath;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

public class FragmentPlay extends Fragment {

    private static final String[] s = {"Up","Down","Left","Right"};
    private static final int[] i ={R.drawable.up, R.drawable.down, R.drawable.left, R.drawable.right};
    private static final long time = 3000;
    private static TextView tv0 ;
    private static TextView tv1;
    private static TextView tv2;
    private static TextView tv3;
    private static ImageView image;
    private TextView tv_score;
    private TextView tv_hightScore;
    private SeekBar sb;
    private int score = 0;
    private int HightScore;
    private Boolean over = false;
    Boolean check(int i, TextView tv){
        return s[i].equals(tv.getText().toString());
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play, null);
        tv0 = (TextView) view.findViewById(R.id.textView);
        tv1 = (TextView) view.findViewById(R.id.textView1);
        tv2 = (TextView) view.findViewById(R.id.textView2);
        tv3 = (TextView) view.findViewById(R.id.textView3);
        tv_score = (TextView) view.findViewById(R.id.TextView_Score);
        tv_hightScore = (TextView) view.findViewById(R.id.TextView_HightScore);
        image = (ImageView) view.findViewById(R.id.imageView);
        sb = (SeekBar) view.findViewById(R.id.seekBar);
        sb.setEnabled(false);
        SharedPreferences pre = getActivity().getSharedPreferences("HightScore",Context.MODE_PRIVATE);
        HightScore = pre.getInt("HightScore",0);
        tv_hightScore.setText("Hight Score: "+HightScore);
        tv_hightScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                FragmentTopScore fragment = new FragmentTopScore();
                ft.replace(R.id.container,fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setEnabled(false);
                tv0.setEnabled(true);
                tv1.setEnabled(true);
                tv2.setEnabled(true);
                tv3.setEnabled(true);
                score = 0;
                over = false;
                CountDownTimer timer = new CountDownTimer(time, 1) {
                    int a =change();
                    Boolean ok = false;
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if(!ok){
                            sb.setProgress((int) ((time - millisUntilFinished) / (time/100)));
                        }
                        tv0.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (s[a].equals(tv0.getText().toString())) {
                                    score++;
                                    a = change();
                                    tv_score.setText("Score: " + String.valueOf(score));
                                    start();
                                } else {
                                    ok = true;
                                    onFinish();
                                }
                            }
                        });

                        tv1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (s[a].equals(tv1.getText().toString())) {
                                    score++;
                                    a = change();
                                    tv_score.setText("Score: " + String.valueOf(score));
                                    start();
                                } else {
                                    ok = true;
                                    onFinish();
                                }
                            }
                        });

                        tv2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (s[a].equals(tv2.getText().toString())) {
                                    score++;
                                    a = change();
                                    tv_score.setText("Score: " + String.valueOf(score));
                                    start();
                                } else {
                                    ok = true;
                                    onFinish();
                                }
                            }
                        });

                        tv3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (s[a].equals(tv3.getText().toString())) {
                                    score++;
                                    a = change();
                                    tv_score.setText("Score: " + String.valueOf(score));
                                    start();
                                } else {
                                    ok = true;
                                    onFinish();
                                }
                            }
                        });
                    }
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onFinish() {
                        if(sb.getProgress()==99) ok = true;
                        if(over) ok = false;
                        if(ok) {
                            over = true;
                            tv0.setEnabled(false);
                            tv1.setEnabled(false);
                            tv2.setEnabled(false);
                            tv3.setEnabled(false);
                            image.setEnabled(true);
                            image.setImageResource(R.drawable.refresh);
                            if(score > HightScore) {
                                tv_hightScore.setText("Hight Score: " + score);
                                SharedPreferences pre = getActivity().getSharedPreferences("HightScore", Context.MODE_PRIVATE);
                                SharedPreferences.Editor edit = pre.edit();
                                edit.putInt("HightScore", score);
                                edit.commit();
                                View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog,null);
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setView(view);
                                final Dialog dialog = builder.create();
                                dialog.setCancelable(false);
                                dialog.show();
                                Button bt = (Button) view.findViewById(R.id.button);
                                final EditText ed = (EditText) view.findViewById(R.id.editText);
                                bt.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (ed.getText().toString() != null) {
                                            Player player = new Player();
                                            player.setName(ed.getText().toString());
                                            player.setScore(score);
                                            HightScoreData db = new HightScoreData(getActivity());
                                            db.open();
                                            db.createData(player);
                                            db.close();
                                            dialog.dismiss();
                                        }
                                    }
                                });
                            }
                        }
                    }
                }.start();
            }
        });

        return view;
    }


    static int change(){
        Random r = new Random();
        int a = r.nextInt(4);
        int b, c;
        while((b = r.nextInt(4)) == a );
        while((c = r.nextInt(4)) == a||c == b);
        tv0.setText(s[a]);
        tv1.setText(s[b]);
        tv2.setText(s[c]);
        tv3.setText(s[6-a-b-c]);
        a = r.nextInt(4);
        Log.e("@@",""+a);
        image.setImageResource(i[a]);
        return a;
    }
}
