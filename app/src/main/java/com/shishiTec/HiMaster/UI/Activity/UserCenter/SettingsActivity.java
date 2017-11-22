package com.shishiTec.HiMaster.UI.Activity.UserCenter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.*;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.cache.DiskCache;
import com.shishiTec.HiMaster.Model.params.BaseParams;
import com.shishiTec.HiMaster.Model.params.DeviceParams;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.Utils.DataCleanManager;
import com.shishiTec.HiMaster.Utils.SharedPreferencesUtil;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SettingsActivity extends BaseActivity {

    @Bind(R.id.submit_suggest)
    RelativeLayout submitSuggest;
    @Bind(R.id.to_gradle)
    RelativeLayout toGradle;
    @Bind(R.id.abort_us)
    RelativeLayout abortUs;
    @Bind(R.id.clear_cache)
    RelativeLayout clearCache;
    @Bind(R.id.logout_accout)
    RelativeLayout logoutAccout;
    @Bind(R.id.top_title)
    TextView topTitle;
    @Bind(R.id.right_title)
    TextView rightTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    TextView tv_clear_cache;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        topTitle.setText(getString(R.string.setting));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置toolbar有返回按钮
        getSupportActionBar().setDisplayShowTitleEnabled(false);//是否有标题
        toolbar.setNavigationIcon(R.mipmap.finish);

        tv_clear_cache = (TextView) findViewById(R.id.tv_clear_cache);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && hasExternalStoragePermission(this)) {
            //读取SD卡缓存
            new GetDiskCacheSizeTask(tv_clear_cache).execute(new File(getExternalCacheDir(), Environment.getExternalStorageDirectory()
                    .getAbsolutePath()
                    + File.separator + "imageCache" + File.separator + "master"));
        } else {
            //读取磁盘缓存
            new GetDiskCacheSizeTask(tv_clear_cache).execute(new File(getCacheDir(), DiskCache.Factory.DEFAULT_DISK_CACHE_DIR));
        }

        //清除缓存
        clearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(SettingsActivity.this, "清除缓存点击", ToastUtils.LENGTH_LONG);
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && hasExternalStoragePermission(SettingsActivity.this)) {
                    //清除SD卡缓存
                    DataCleanManager.deleteFilesByDirectory(new File(getExternalCacheDir(), Environment.getExternalStorageDirectory()
                            .getAbsolutePath()
                            + File.separator + "imageCache" + File.separator + "master"));
                } else {
                    //清除磁盘缓存
                    DataCleanManager.deleteFilesByDirectory(new File(getCacheDir(), DiskCache.Factory.DEFAULT_DISK_CACHE_DIR));
                }
            }
        });
        //反馈意见
        submitSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, FeedbackActivity.class));
            }
        });
        //去评分
        toGradle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getApplication().getPackageName())));
                } catch (Exception e) {

                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_settings;
    }

    @OnClick(R.id.logout_accout)
    void exit() {
        DeviceParams deviceParams = new DeviceParams();
        Map<String, String> logoutMap = new HashMap<>();
        BaseParams params = new BaseParams(deviceParams);
        params.setDevice(deviceParams);
        params.setData(logoutMap);
        params.setSign(params.paramsSign());
        params.setRest_version("3.0");
        Subscription subscribe = RetrofitManager.getmInstance().createService()
                .exit(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if (baseModel.getCode() == 200) {
                            SharedPreferencesUtil.getInstance().clearUserInfo();
                            finish();
                            ToastUtils.showGravity(SettingsActivity.this, "成功退出", Gravity.CENTER, 0, 0);
                        }

                    }
                });
        addSubscription(subscribe);
    }

    static class GetDiskCacheSizeTask extends AsyncTask<File, Long, Long> {
        private final TextView resultView;

        public GetDiskCacheSizeTask(TextView resultView) {
            this.resultView = resultView;
        }

        @Override
        protected void onPreExecute() {
            resultView.setText("Calculating...");
        }

        @Override
        protected void onProgressUpdate(Long... values) { /* onPostExecute(values[values.length - 1]); */ }

        @Override
        protected Long doInBackground(File... dirs) {
            try {
                long totalSize = 0;
                for (File dir : dirs) {
                    publishProgress(totalSize);
                    totalSize += calculateSize(dir);
                }
                return totalSize;
            } catch (RuntimeException ex) {
                final String message = String.format("Cannot get size of %s: %s", Arrays.toString(dirs), ex);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        resultView.setText("error");
                        Toast.makeText(resultView.getContext(), message, Toast.LENGTH_LONG).show();
                    }
                });
            }
            return 0L;
        }

        @Override
        protected void onPostExecute(Long size) {
            String sizeText = android.text.format.Formatter.formatFileSize(resultView.getContext(), size);
            resultView.setText(sizeText);
        }

        private static long calculateSize(File dir) {
            if (dir == null) return 0;
            if (!dir.isDirectory()) return dir.length();
            long result = 0;
            File[] children = dir.listFiles();
            if (children != null)
                for (File child : children)
                    result += calculateSize(child);
            return result;
        }
    }

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        return perm == 0;
    }
}
