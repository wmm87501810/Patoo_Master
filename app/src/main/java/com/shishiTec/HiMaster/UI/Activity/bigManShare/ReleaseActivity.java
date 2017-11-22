package com.shishiTec.HiMaster.UI.Activity.bigManShare;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;

import com.shishiTec.HiMaster.Model.bean.Item;
import com.shishiTec.HiMaster.Model.bean.UpLoadPictureBean;
import com.shishiTec.HiMaster.Model.bean.UserAddBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Views.SelectPicPopupWindow;
import com.shishiTec.HiMaster.Utils.LoginUtil;
import com.shishiTec.HiMaster.Utils.PictureManageUtil;
import com.shishiTec.HiMaster.Utils.SharedPreferencesUtil;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;

import java.io.File;
import java.util.*;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ReleaseActivity extends BaseActivity {
    private GridView release_gv;
    private ImageView release_add_images, left_back_release;
    private LinearLayout release_address;
    private EditText release_edit;
    private TextView release_tv, address_release_tv;
    private List<Bitmap> list = new ArrayList<Bitmap>();
    private List<String> stringList = new ArrayList<String>();
    private ImageAdapter adapter;
    private String name = "iv";
    private Bitmap i;
    private String imgUrl = "data:image/jpeg;base64,";
    private List<String> returnImgUrl = new ArrayList<String>();
    File fileone = null;
    File filetwo = null;
    private String c = "", p = "", lo = "", la = "", a = "";
    private SelectPicPopupWindow menuWindow;//选择图片的pop
    //保存图片本地路径
    public static final String ACCOUNT_DIR = Environment.getExternalStorageDirectory().getPath()
            + "/release/";
    public static final String ACCOUNT_MAINTRANCE_ICON_CACHE = "icon_cache/";
    private static final String IMGPATH = ACCOUNT_DIR + ACCOUNT_MAINTRANCE_ICON_CACHE;
    private static final String IMAGE_FILE_NAME = "faceImage.jpeg";
    private static final String TMP_IMAGE_FILE_NAME = "tmp_faceImage.jpeg";
    /* 用来标识请求照相功能的activity */
    private final int CAMERA_WITH_DATA = 3023;
    private File mCurrentPhotoFile;// 照相机拍照得到的图片
    /* 拍照的照片存储位置 */
    private final File PHOTO_DIR = new File(
            Environment.getExternalStorageDirectory()
                    + "/Android/data/com.photo.choosephotos");
    //常量定义
    private final int PHOTO_PICKED_WITH_DATA = 3021;
    private final int ADDRESS = 100;
    public static final int TAKE_A_PICTURE = 10;
    public static final int SELECT_A_PICTURE = 20;
    public static final int SET_PICTURE = 30;
    public static final int SET_ALBUM_PICTURE_KITKAT = 40;
    public static final int SELECET_A_PICTURE_AFTER_KIKAT = 50;
    //版本比较：是否是4.4及以上版本
    final boolean mIsKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

    @Override
    public int getLayoutId() {
        return R.layout.activity_release;
    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        release_edit = (EditText) findViewById(R.id.release_edit);
        release_address = (LinearLayout) findViewById(R.id.release_address);
        release_tv = (TextView) findViewById(R.id.release_tv);
        address_release_tv = (TextView) findViewById(R.id.address_release_tv);
        Resources res = getResources();
        i = BitmapFactory.decodeResource(res, R.mipmap.jia);
        list.add(i);
        release_gv = (GridView) findViewById(R.id.release_gv);
//        release_add_images = (ImageView) findViewById(R.id.release_add_images);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapter = new ImageAdapter(this, list);
        release_gv.setAdapter(adapter);
        initData();
    }

    private class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private List<Bitmap> list;

        public ImageAdapter(Context context, List<Bitmap> list) {
            this.mContext = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //定义一个ImageView,显示在GridView里
            ImageView imageView;

            if (i == list.get(position)) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(158, 158));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
                imageView.setImageBitmap(i);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (list.size() > 10) {
                            Toast.makeText(ReleaseActivity.this, "最多选取九张图片", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (null == menuWindow) {
                            menuWindow = new SelectPicPopupWindow(ReleaseActivity.this, itemsOnClick);
                        }
                        menuWindow.showAtLocation(ReleaseActivity.this.findViewById(R.id.release_layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                    }
                });
            } else {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(158, 158));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
                imageView.setImageBitmap(list.get(position));
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        list.remove(position);
                        notifyDataSetChanged();
                    }
                });
            }
            return imageView;
        }

        public void addMoreList(List<Bitmap> newDatas) {
            Toast.makeText(ReleaseActivity.this, "list===" + list.size(), Toast.LENGTH_SHORT).show();
            list = newDatas;
            notifyDataSetChanged();
        }

    }

    private void initData() {
        File directory = new File(ACCOUNT_DIR);
        File imagepath = new File(IMGPATH);
        if (!directory.exists()) {
            Log.i("zou", "directory.mkdir()");
            directory.mkdir();
        }
        if (!imagepath.exists()) {
            Log.i("zou", "imagepath.mkdir()");
            imagepath.mkdir();
        }
        fileone = new File(IMGPATH, IMAGE_FILE_NAME);
        filetwo = new File(IMGPATH, TMP_IMAGE_FILE_NAME);

        try {
            if (!fileone.exists() && !filetwo.exists()) {
                fileone.createNewFile();
                filetwo.createNewFile();
            }
        } catch (Exception ignored) {
        }
    }

    private Bitmap user_ico;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_PICKED_WITH_DATA: {
                // 调用Gallery返回的
                ArrayList<Item> tempFiles = new ArrayList<Item>();
                if (data == null)
                    return;
                tempFiles = data.getParcelableArrayListExtra("fileNames");
                Log.e("test", "被选中的照片" + tempFiles.toString());

                if (tempFiles == null) {
                    return;
                }
                Bitmap bitmap = null;
                for (int i = 0; i < tempFiles.size(); i++) {
                    bitmap = MediaStore.Images.Thumbnails.getThumbnail(this.getContentResolver(), tempFiles.get(i).getPhotoID(), MediaStore.Images.Thumbnails.MICRO_KIND, null);
                    int rotate = PictureManageUtil.getCameraPhotoOrientation(tempFiles.get(i).getPhotoPath());
                    bitmap = PictureManageUtil.rotateBitmap(bitmap, rotate);
                    String imgToBase64 = PictureManageUtil.imgToBase64(null, bitmap);
//                    String imgToBase64 = PictureUtil.imgToBase64(null, bitmap);
                    if (list.size() > 10) {
                        Toast.makeText(ReleaseActivity.this, "最多选取九张图片", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        list.add(0, bitmap);
                        adapter.addMoreList(list);
                    }
                    stringList.add(imgUrl + imgToBase64);
                }

                break;
            }
            case CAMERA_WITH_DATA: {
                if (mCurrentPhotoFile == null) {
                    return;
                }
                //根据路径，得到一个压缩过的Bitmap（宽高较大的变成500，按比例压缩）
                Bitmap bitmap = PictureManageUtil.getCompressBm(mCurrentPhotoFile.getAbsolutePath());
                //获取旋转参数
                int rotate = PictureManageUtil.getCameraPhotoOrientation(mCurrentPhotoFile.getAbsolutePath());
                //把压缩的图片进行旋转
                bitmap = PictureManageUtil.rotateBitmap(bitmap, rotate);
                String imgToBase64 = PictureManageUtil.imgToBase64(null, bitmap);
//                String imgToBase64 = PictureUtil.imgToBase64(null, bitmap);
                if (list.size() > 10) {
                    Toast.makeText(ReleaseActivity.this, "最多选取九张图片", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    list.add(0, bitmap);
                    adapter.addMoreList(list);
                }
                stringList.add(imgUrl + imgToBase64);
                break;
            }
            case ADDRESS: {
                if (data != null) {
                    c = data.getStringExtra("city");
                    p = data.getStringExtra("province");
                    lo = data.getStringExtra("longitude");
                    la = data.getStringExtra("latitude");
                    a = data.getStringExtra("address");
                    address_release_tv.setText(a);
                } else {
                    return;
                }
            }
        }
    }


    private void notAdapter(Bitmap icon) {
        if (list.size() > 10) {
            Toast.makeText(ReleaseActivity.this, "最多选取九张图片", Toast.LENGTH_SHORT).show();
            return;
        } else {
            list.add(0, icon);
            adapter.addMoreList(list);
        }
    }

    @Override
    public void initListener() {
//        adapter.setOnItemClickListener(new ReleaseItemAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//
//                }
//        });

//        release_add_images.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(null == menuWindow){
//                    menuWindow = new SelectPicPopupWindow(ReleaseActivity.this, itemsOnClick);
//                }
//                menuWindow.showAtLocation(ReleaseActivity.this.findViewById(R.id.release_layout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
//
//                adapter.addMoreItem(list);
//            }
//        });
        release_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < stringList.size(); i++) {
                    if (SharedPreferencesUtil.getInstance().isLogin()) {
                        uploadImage(stringList.get(i), i);
                    } else {
                        LoginUtil.getInstance().login(ReleaseActivity.this);
                    }
                }

            }
        });
        release_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReleaseActivity.this, PeripheryActivity.class);
                startActivityForResult(intent, ADDRESS);
            }
        });
    }

    private void releaseShare() {
        if (returnImgUrl == null) {
            return;
        }
        String imgUrl = "";
        String content = release_edit.getText().toString().trim();
        for (String url : returnImgUrl) {
            if (!imgUrl.equals("")) {
                imgUrl += "," + url;
            } else {
                imgUrl = url;
            }
        }
        Map<String, String> BigManMap = new HashMap<>();
        BigManMap.put("media_type", "1");
        BigManMap.put("content", content);
        BigManMap.put("url", imgUrl);
        BigManMap.put("course_id", "");
        BigManMap.put("province", p);
        BigManMap.put("city", c);
        BigManMap.put("address", a);
        BigManMap.put("longitude", lo);
        BigManMap.put("latitude", la);
        BigManMap.put("is_show_position", "1");
        params.setData(BigManMap);
        RetrofitManager.getmInstance().createService()
                .userAddUpload(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<UserAddBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showGravity(ReleaseActivity.this, "数据获取失败", Gravity.CENTER, 0, 0);
                    }

                    @Override
                    public void onNext(BaseModel<UserAddBean> BigManBaseModel) {
                        if (BigManBaseModel.getCode() == 200) {
                            ToastUtils.showGravity(ReleaseActivity.this, "成功", Gravity.CENTER, 20, 200);
                            ReleaseActivity.this.finish();
                        } else {
                            ToastUtils.showGravity(ReleaseActivity.this, BigManBaseModel.getMsg(), Gravity.CENTER, 20, 200);
                        }
                    }
                });
    }

    private void uploadImage(String s, final int i) {
        Map<String, String> BigManMap = new HashMap<>();
        BigManMap.put("upload_data", s);
        params.setData(BigManMap);
        RetrofitManager.getmInstance().createService()
                .upLoadPic(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<UpLoadPictureBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showGravity(ReleaseActivity.this, "数据获取失败", Gravity.CENTER, 0, 0);
                    }

                    @Override
                    public void onNext(BaseModel<UpLoadPictureBean> BigManBaseModel) {
                        if (BigManBaseModel.getCode() == 200) {
                            returnImgUrl.add(BigManBaseModel.getData().getUrl());
                            if (i == stringList.size() - 1) {
                                releaseShare();
                            }
                        } else {
                            ToastUtils.showGravity(ReleaseActivity.this, BigManBaseModel.getMsg(), Gravity.CENTER, 20, 200);
                        }
                    }
                });
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            if (list.size() > 10) {
                Toast.makeText(ReleaseActivity.this, "最多选取九张图片", Toast.LENGTH_SHORT).show();
                return;
            }
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_take_photo: {
                    String status = Environment.getExternalStorageState();
                    if (status.equals(Environment.MEDIA_MOUNTED)) {
                        //判断是否有SD卡
                        doTakePhoto();// 用户点击了从照相机获取
                    } else {
                        Toast.makeText(ReleaseActivity.this, getString(R.string.not_sdcard), Toast.LENGTH_LONG).show();
                    }
                    break;
                }
                case R.id.btn_pick_photo: {
                    //打开选择图片界面
                    doPickPhotoFromGallery();
                    break;
                }
                default:
                    break;
            }
        }
    };

    // 请求Gallery程序
    protected void doPickPhotoFromGallery() {
        try {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //设置为圆形
            dialog.setMessage("数据加载中...");
            dialog.setIndeterminate(false);//
            //dialog.setCancelable(true);	//按回退键取消
            dialog.show();
            Window window = dialog.getWindow();
            View view = window.getDecorView();
//			Tools.setViewFontSize(view,21);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    //初始化提示框
                    dialog.dismiss();
                }

            }, 1000);
//			final Intent intent = new Intent(MainActivity.this,GetAllImgFolderActivity.class);
            final Intent intent = new Intent(ReleaseActivity.this, CommentShowPhotoListActivity.class);
            intent.putExtra("number", 9);
            startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, getString(R.string.gallery_not_found_image), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 拍照获取图片
     */
    protected void doTakePhoto() {
        try {
            // 创建照片的存储目录
            mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
            final Intent intent = getTakePickIntent(mCurrentPhotoFile);
//			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, getString(R.string.not_find_camera), Toast.LENGTH_LONG).show();
        }
    }

    public String getPhotoFileName() {
        UUID uuid = UUID.randomUUID();
        return uuid + ".jpg";
    }

    public static Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return intent;
    }
}
