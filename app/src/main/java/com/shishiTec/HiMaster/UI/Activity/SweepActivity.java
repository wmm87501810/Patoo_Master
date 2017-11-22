package com.shishiTec.HiMaster.UI.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.shishiTec.HiMaster.Model.bean.SweepResult;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.Utils.AppUtils;
import com.shishiTec.HiMaster.Utils.scan.camera.CameraManager;
import com.shishiTec.HiMaster.Utils.scan.decoding.CaptureActivityHandler;
import com.shishiTec.HiMaster.Utils.scan.decoding.InactivityTimer;
import com.shishiTec.HiMaster.Utils.scan.view.ViewfinderView;
import com.shishiTec.HiMaster.base.BaseActivity;


import org.xutils.HttpManager;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Pursue on 2015/6/27.
 */
public class SweepActivity extends Activity implements View.OnClickListener,SurfaceHolder.Callback{
    private ImageButton back;
    private ViewfinderView sweepView;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private SurfaceView preview;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private boolean playBeep;
    private boolean vibrate;
    private CaptureActivityHandler handler;
    private MediaPlayer mediaPlayer;
    private static final float BEEP_VOLUME = 0.10f;
    private final static String TAG = "SweepActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sweep);
        AppUtils.addActivity(this);
        CameraManager.init(getApplication());
        iniView();
    }

    private void iniView() {
        back = (ImageButton)findViewById(R.id.back_btn);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("扫一扫");
        back.setOnClickListener(this);
        preview = (SurfaceView)findViewById(R.id.preview_view);
        sweepView = (ViewfinderView)findViewById(R.id.viewfinder_view);
        back.setOnClickListener(this);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
        }
    }
    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();
        SurfaceHolder surfaceHolder = preview.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }


    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }

    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(SweepActivity.this, decodeFormats,
                    characterSet);
        }
    }

    /**
     * 处理扫描结果
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        if (!resultString.equals("")&&resultString!=null) {
            String decryptString = AppUtils.getDecryptString(resultString);
            Gson gson = new Gson();
            SweepResult sweepResult = gson.fromJson(decryptString, SweepResult.class);
            getSweepData(sweepResult.getOid(), sweepResult.getId(), sweepResult.getCode());
        }else {
            SweepActivity.this.finish();
        }
    }
    //获得扫描后的数据
    private void getSweepData(String oid,String userId,String code){
//        HttpManager.getInstance().orderVerification(new MasterHttpListener<BaseModel<SweepBean>>() {
//            @Override
//            public void success(BaseModel<SweepBean> listBaseModel) {
//                if (listBaseModel.getCode() == 200) {
//                    if (listBaseModel.getData() != null) {
//                        Intent intent = new Intent(SweepActivity.this, StudentApplyActivity.class);
//                        intent.putExtra("sweepBean", listBaseModel.getData());
//                        startActivity(intent);
//                    }
//                } else{
//                    Toast.makeText(SweepActivity.this,listBaseModel.getMsg(),Toast.LENGTH_SHORT).show();
//                    SweepActivity.this.finish();
//                }
//            }
//
//            @Override
//            public void error(Exception e) {
//                LogUtil.e(TAG, e.getMessage());
//            }
//
//            @Override
//            public void finish() {
//
//            }
//        }, oid, userId, code);
    }
    private static final long VIBRATE_DURATION = 200L;
    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    public ViewfinderView getViewfinderView() {
        return sweepView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        sweepView.drawViewfinder();

    }
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

}
