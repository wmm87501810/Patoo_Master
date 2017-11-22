package com.shishiTec.HiMaster.UI.Activity.master;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.shishiTec.HiMaster.Model.bean.MasterAddBean;
import com.shishiTec.HiMaster.Model.bean.MediaListBean;
import com.shishiTec.HiMaster.Model.bean.UpLoadPictureBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.master.UploadAdapter;
import com.shishiTec.HiMaster.UI.Views.CoursePicPopupWindow;
import com.shishiTec.HiMaster.UI.Views.SelectPicPopupWindow;
import com.shishiTec.HiMaster.Utils.ImageUtils;
import com.shishiTec.HiMaster.Utils.PictureUtil;
import com.shishiTec.HiMaster.Utils.SharedPreferencesUtil;
import com.shishiTec.HiMaster.Utils.ToastUtils;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.shishiTec.HiMaster.base.BaseModel;
import com.zhy.autolayout.AutoRelativeLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MasterUploadActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_add_content;
    private TextView topTitle;
    private TextView rightTitle;
    private Toolbar toolbar;
    private List<MediaListBean> listBean = new ArrayList<>();

    //版本比较：是否是4.4及以上版本
    //常量定义
    final static int ADD_TAG = 111;
    final static int COVER_OPEN_PHOTO = 10;
    final static int COVER_OPEN_CROP = 11;
    final static int COVER_OPEN_CAMERA = 12;
    final static int OPEN_PHOTO = 13;
    final static int OPEN_CROP = 14;
    final static int OPEN_CAMERA = 15;

    private EditText et_title;
    private TextView tv_link_course;        //链接课程
    private TextView tv_store_name;         //课程名称
    private TextView tv_course_price;       //课程价格
    private TextView tv_km;                 //课程距离
//    private TextView et_master_add_tag;     //标签
//    private TextView tv_master_upload_name; //用户名
    private RecyclerView rl_master_upload;
    private SelectPicPopupWindow menuWindow;    //选择图片的pop
    private SelectPicPopupWindow adapterWindow; //adapter pop
    private CoursePicPopupWindow coursePicPopupWindow; //课程 pop
    private ImageView iv_master_upload_cover;
    private ImageView iv_course_cover;
//    private ImageView iv_master_upload;
    private UploadAdapter masterUploadAdapter;
    private List<MediaListBean> list =  new ArrayList<MediaListBean>();
    private AutoRelativeLayout ll_set_course;
    private Map<String,String> hashMap = new HashMap<>();
    private String cover; //封面图片
    private String p;
    private View view;

    @Override
    public int getLayoutId() {
        return R.layout.activity_master_upload;
    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        topTitle = (TextView) findViewById(R.id.top_title);
        rightTitle = (TextView) findViewById(R.id.right_title);
        iv_course_cover = (ImageView) findViewById(R.id.iv_course_cover);

        topTitle.setText("");
        rightTitle.setText("保存");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置toolbar有返回按钮
        getSupportActionBar().setDisplayShowTitleEnabled(false);//是否有标题
        toolbar.setNavigationIcon(R.mipmap.finish);
        toolbar.setBackgroundColor(Color.rgb(255,216,1));

        et_title = (EditText)findViewById(R.id.et_title);
//        iv_master_upload = (ImageView) findViewById(R.id.iv_master_upload);

        tv_km = (TextView) findViewById(R.id.tv_km);
        tv_store_name = (TextView) findViewById(R.id.tv_store_name);
        tv_link_course = (TextView) findViewById(R.id.tv_link_course);
        tv_add_content = (TextView) findViewById(R.id.tv_add_content);
        tv_course_price = (TextView) findViewById(R.id.tv_course_price);
//        et_master_add_tag = (TextView)findViewById(R.id.et_master_add_tag);
//        tv_master_upload_name = (TextView) findViewById(R.id.tv_master_upload_name);
//        tv_master_upload_name.setText(SharedPreferencesUtil.getInstance().getNikeName());
//        BaseApplication.getInstance().loadCircleImageView(this,iv_master_upload,SharedPreferencesUtil.getInstance().getImgTop());

        ll_set_course = (AutoRelativeLayout) findViewById(R.id.ll_set_course);
        rl_master_upload = (RecyclerView) findViewById(R.id.rl_master_upload);
        iv_master_upload_cover = (ImageView) findViewById(R.id.iv_master_upload_cover);
        list.add(new MediaListBean("",""));
        masterUploadAdapter = new UploadAdapter(MasterUploadActivity.this,list);
        masterUploadAdapter.setOnItemClickListener(new UploadAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object object,View v) {
                if(adapterWindow==null){
                    adapterWindow = new SelectPicPopupWindow(MasterUploadActivity.this, adapterItemsOnClick);
                }
                view = v;
                p = position+"";
                adapterWindow.showAtLocation(findViewById(R.id.upload), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置

            }
        });
        LinearLayoutManager contentManager = new LinearLayoutManager(this);
        contentManager.setOrientation(LinearLayoutManager.VERTICAL);
        //添加分割线
        rl_master_upload.setLayoutManager(contentManager);
        rl_master_upload.setItemAnimator(new DefaultItemAnimator());
        rl_master_upload.setAdapter(masterUploadAdapter);
    }

    @Override
    protected void initListener() {
        rightTitle.setOnClickListener(this);
        ll_set_course.setOnClickListener(this);
        tv_add_content.setOnClickListener(this);
//        et_master_add_tag.setOnClickListener(this);
        iv_master_upload_cover.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.iv_master_upload_cover:
                if (null == menuWindow) {
                    menuWindow = new SelectPicPopupWindow(this, itemsOnClick);
                }
                menuWindow.showAtLocation(this.findViewById(R.id.upload), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                break;
            case R.id.tv_add_content:
                list.add(new MediaListBean("",""));
                masterUploadAdapter.onChageDataList(list);
                masterUploadAdapter.notifyDataSetChanged();
                break;
//            case R.id.et_master_add_tag:
//                //添加标签
//                intent = new Intent(MasterUploadActivity.this,AddTagActivity.class);
//                startActivityForResult(intent,ADD_TAG);
//                break;
            case R.id.ll_set_course:
                if(null == coursePicPopupWindow){
                    coursePicPopupWindow = new CoursePicPopupWindow(this,null);
                }
                coursePicPopupWindow.showAtLocation(this.findViewById(R.id.upload), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                break;
            case R.id.right_title:
                uploadData();
                break;
        }
    }

    private void uploadData(){
        Gson gson = new Gson();
        for(int i=0;i<masterUploadAdapter.getItemCount();i++){
            View view =  rl_master_upload.getChildAt(i);
            EditText editText = (EditText) view.findViewById(R.id.et_master_upload_content);
            listBean.add(new MediaListBean(editText.getText().toString(),hashMap.get(i+"")));
        }
        String str = gson.toJson(listBean);
        Map<String,String> map = new HashMap<>();
//        map.put("tag_name",et_master_add_tag.getText().toString());
        map.put("course_id",coursePicPopupWindow.getCourseId());
        map.put("title",et_title.getText().toString());
        map.put("content","最牛的达人，最好玩的同学。");   //分享内容
        map.put("cover",cover);     //分享封面图
        map.put("media_type","1");  //媒体类型：1、图片，2、视频
        map.put("media_list",str);  //资源列表
        params.setData(map);
        Subscription subscribe = RetrofitManager.getmInstance().createService()
                .masterAdd(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<MasterAddBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<MasterAddBean> masterAddBeanBaseModel) {
                        ToastUtils.show(MasterUploadActivity.this,masterAddBeanBaseModel.getCode()+"分享ID"+masterAddBeanBaseModel.getData().getShare_id(),ToastUtils.LENGTH_LONG);
                    }
                });
    }



    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_take_photo: {
                    String status = Environment.getExternalStorageState();
                    if (status.equals(Environment.MEDIA_MOUNTED)) {
                        //判断是否有SD卡
//                        doTakePhoto();// 用户点击了从照相机获取
                        ImageUtils.camera(MasterUploadActivity.this,COVER_OPEN_CAMERA);
                    } else {
                        Toast.makeText(MasterUploadActivity.this, getString(R.string.not_sdcard), Toast.LENGTH_LONG).show();
                    }
                    break;
                }
                case R.id.btn_pick_photo: {
                    //打开选择图片界面
//                    doPickPhotoFromGallery();
                    selectImageUriAfterKikat();
                    break;
                }
                default:
                    break;
            }
        }
    };

    //为弹出窗口实现监听类
    private View.OnClickListener adapterItemsOnClick = new View.OnClickListener() {
        public void onClick(View v) {
            adapterWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_take_photo: {
                    String status = Environment.getExternalStorageState();
                    if (status.equals(Environment.MEDIA_MOUNTED)) {
                        //判断是否有SD卡
//                        doTakePhoto();// 用户点击了从照相机获取
                        ImageUtils.camera(MasterUploadActivity.this,OPEN_CAMERA);
                    } else {
                        Toast.makeText(MasterUploadActivity.this, getString(R.string.not_sdcard), Toast.LENGTH_LONG).show();
                    }
                    break;
                }
                case R.id.btn_pick_photo: {
                    //打开选择图片界面
//                    doPickPhotoFromGallery();
                    selectImageUriAfterKikat2();
                    break;
                }
                default:
                    break;
            }
        }
    };


    /**
     * 打开系统相册
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void selectImageUriAfterKikat() {
        try {
            ImageUtils.gallery(MasterUploadActivity.this,COVER_OPEN_PHOTO);
        } catch (Exception e) {
            Toast.makeText(MasterUploadActivity.this, "相机打开错误", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * 打开系统相册
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void selectImageUriAfterKikat2() {
        try {
            ImageUtils.gallery(MasterUploadActivity.this,OPEN_PHOTO);
        } catch (Exception e) {
            Toast.makeText(MasterUploadActivity.this, "相机打开错误", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
//            case ADD_TAG:
//                et_master_add_tag.setText(data.getStringExtra("tagName") );
//                et_master_add_tag.setGravity(Gravity.CENTER);
//                break;
            case COVER_OPEN_PHOTO:
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    ImageUtils.crop(this,uri,400,400,1,1,COVER_OPEN_CROP);
                }
                break;
            case COVER_OPEN_CROP:
                // 从剪切图片返回的数据
                if (data != null) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    iv_master_upload_cover.setImageBitmap(bitmap);
                    upLoadUserImage(bitmap,false);
                }
                try {
                    // 将临时文件删除
                    File tempFile = new File(Environment.getExternalStorageDirectory().getPath(),
                            "images/");
                    tempFile.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case COVER_OPEN_CAMERA:
                // 从相册返回的数据
                // 得到图片的全路径
                File tempFile = new File(Environment.getExternalStorageDirectory().getPath(),
                        "images/");
                Uri uri = Uri.fromFile(tempFile);
                    ImageUtils.crop(this,uri,400,400,1,1,COVER_OPEN_CROP);
                break;
            case OPEN_PHOTO:
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri2 = data.getData();
                    ImageUtils.crop(this,uri2,400,400,1,1,OPEN_CROP);
                }
                break;
            case OPEN_CROP:
                // 从剪切图片返回的数据
                if (data != null) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    ImageView imageView = (ImageView) view;
                    imageView.setImageBitmap(bitmap);
                    upLoadUserImage(bitmap,true);
                }
                try {
                    // 将临时文件删除
                    File tempFile2 = new File(Environment.getExternalStorageDirectory().getPath(),
                            "images/");
                    tempFile2.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case OPEN_CAMERA:
                // 从相册返回的数据
                // 得到图片的全路径
                File tempFile2 = new File(Environment.getExternalStorageDirectory().getPath(),
                        "images/");
                Uri uri2 = Uri.fromFile(tempFile2);
                ImageUtils.crop(this,uri2,400,400,1,1,OPEN_CROP);
                break;
        }
    }

    /**
     * 单图上传
     * @param bitmap
     */
    public void upLoadUserImage(Bitmap bitmap, final boolean isList) {
        Map<String, String> upLoadImageMap = new HashMap<>();
        upLoadImageMap.put("upload_data", PictureUtil.imgToBase64(null, bitmap));
        params.setData(upLoadImageMap);
        Subscription subscribe = RetrofitManager.getmInstance().createService()
                .upLoadPic(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<UpLoadPictureBean>>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<UpLoadPictureBean> upLoadPictureBeanBaseModel) {
                        if (upLoadPictureBeanBaseModel.getCode() == 200) {
                            UpLoadPictureBean data = upLoadPictureBeanBaseModel.getData();
                            ToastUtils.show(MasterUploadActivity.this,data.getUrl(),ToastUtils.LENGTH_LONG);
                            if(isList){
                                hashMap.put(p,data.getUrl());
                            }else{
                                cover = data.getUrl();
                            }
                            //获得总项数
                            //存取每一个子项的内容 用俩个list<String>分别存放对应的内容和图片路径
                            //将俩个list<String> 数据取出拼成集合
                        }

                    }
                });
        addSubscription(subscribe);
    }

}
