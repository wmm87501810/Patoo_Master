package com.shishiTec.HiMaster.UI.Activity.bigManShare;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import com.shishiTec.HiMaster.Adapter.bigManCommentAdapter;
import com.shishiTec.HiMaster.Model.bean.EmojiBean;
import com.shishiTec.HiMaster.Model.bean.bigManCommentBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.face.FaceAdapter;
import com.shishiTec.HiMaster.UI.Adapter.face.FacePageAdapter;
import com.shishiTec.HiMaster.UI.Views.SuperSwipeRefreshLayout;
import com.shishiTec.HiMaster.Utils.*;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class bigManCommentActivity extends BaseActivity implements View.OnClickListener {

    //    @Bind(R.id.top_title)
    TextView topTitle;
    //    @Bind(R.id.right_title)
    TextView rightTitle;
    //    @Bind(R.id.toolbar)
    Toolbar toolbar;
    //    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    //    @Bind(R.id.bigman_comment_swiperefreshlayout)
    SuperSwipeRefreshLayout bigmanCommentSwiperefreshlayout;
    //    @Bind(R.id.reselete)
    TextView reselete;
    //    @Bind(R.id.input_edit)
    EditText inputEdit;
    //    @Bind(R.id.iv_post_face)
    ImageButton ivPostFace;
    //    @Bind(R.id.vp_face_contains)
    ViewPager vpFaceContains;
    //    @Bind(R.id.rb_face_page_point)
    RadioButton rbFacePagePoint;
    //    @Bind(R.id.rg_face_page_point)
    RadioGroup rgFacePagePoint;
    //    @Bind(R.id.ib_face_delete)
    ImageButton ibFaceDelete;
    //    @Bind(R.id.btn_send)
    Button btnSend;
    LinearLayout llFaceLayout;
    //    @Bind(R.id.input_layout)
    LinearLayout inputLayout;
    ImageView left_back;
    private String share_id;
    private bigManCommentAdapter bigManCommentAdapter;
    private int lastVisibleItem;
    private int w = 2;
    private int page = 1;
    private int page_size = 10;
    List<bigManCommentBean> commentData = new ArrayList<>();
    public int p;
    private InputMethodManager imm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        initRefreshAndLoad();
        queryBigManContent(page, page_size, true);
    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        share_id = intent.getStringExtra("share_id");
        topTitle = (TextView) findViewById(R.id.top_title);
        reselete = (TextView) findViewById(R.id.reselete);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        inputEdit = (EditText) findViewById(R.id.input_edit);
        left_back = (ImageView) findViewById(R.id.left_back);
        llFaceLayout = (LinearLayout) findViewById(R.id.ll_face_layout);
        inputLayout = (LinearLayout) findViewById(R.id.input_layout);
        bigmanCommentSwiperefreshlayout = (SuperSwipeRefreshLayout) findViewById(R.id.bigman_comment_swiperefreshlayout);
        vpFaceContains = (ViewPager) findViewById(R.id.vp_face_contains);
        rbFacePagePoint = (RadioButton) findViewById(R.id.rb_face_page_point);
        rgFacePagePoint = (RadioGroup) findViewById(R.id.rg_face_page_point);
        ibFaceDelete = (ImageButton) findViewById(R.id.ib_face_delete);
        btnSend = (Button) findViewById(R.id.btn_send);
        ivPostFace = (ImageButton) findViewById(R.id.iv_post_face);
//        findViewById(R.id.input_edit).setOnClickListener(this);
        onClick(ivPostFace);
        onClick(ibFaceDelete);
        onClick(btnSend);
        onClick(left_back);
//        topTitle.setText("评论列表");
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置toolbar有返回按钮
//        getSupportActionBar().setDisplayShowTitleEnabled(false);//是否有标题
//        toolbar.setNavigationIcon(R.mipmap.finish);

        //创建默认的线性LayoutManager
        recyclerView.setLayoutManager(linearLayoutManager);
        bigManCommentAdapter = new bigManCommentAdapter(bigManCommentActivity.this, commentData);
        recyclerView.setAdapter(bigManCommentAdapter);
        inputEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                llFaceLayout.setVisibility(View.GONE);
                return false;
            }
        });
        onClick(reselete);
        bigManCommentAdapter.setOnItemClickListener(new bigManCommentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position, Object object) {
                inputEdit.setText("");
                p = position;
                int r = reselete.getVisibility();
                int i = inputLayout.getVisibility();
                //得到InputMethodManager的实例
                if (r == 0 && i == 8) {
                    //如果开启
                    reselete.setVisibility(View.GONE);
                    inputLayout.setVisibility(View.VISIBLE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    imm.hideSoftInputFromWindow(inputEdit.getWindowToken(), 0); //强制隐藏键盘
                    reselete.setVisibility(View.VISIBLE);
                    inputLayout.setVisibility(View.GONE);
                    llFaceLayout.setVisibility(View.GONE);
                }

                inputEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        EditText edit = (EditText) v;
                        String key = edit.getText().toString().trim();
                        if (event != null
                                && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            if (SharedPreferencesUtil.getInstance().isLogin()) {
                                if (!TextUtils.isEmpty(inputEdit.getText())) {
                                    Map<String, String> BigManMap = new HashMap<>();
                                    BigManMap.put("comment_id", commentData.get(position).getComment_id());
                                    BigManMap.put("other_uid", commentData.get(position).getUid());
                                    BigManMap.put("content", key);
                                    params.setData(BigManMap);
                                    RetrofitManager.getmInstance().createService()
                                            .commentReply(params)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Subscriber<BaseModel>() {
                                                @Override
                                                public void onCompleted() {
                                                }

                                                @Override
                                                public void onError(Throwable e) {
                                                    ToastUtils.showGravity(bigManCommentActivity.this, "数据获取失败", Gravity.CENTER, 0, 0);
                                                }

                                                @Override
                                                public void onNext(BaseModel BigManBaseModel) {
                                                    if (BigManBaseModel.getCode() == 200) {
                                                        ToastUtils.showGravity(bigManCommentActivity.this, BigManBaseModel.getMsg(), Gravity.CENTER, 20, 200);
                                                        queryBigManContent(1, page_size, true);
                                                    } else {
                                                        ToastUtils.showGravity(bigManCommentActivity.this, BigManBaseModel.getMsg(), Gravity.CENTER, 20, 200);
                                                    }
                                                }
                                            });
                                } else {
                                    ToastUtils.showCenter(bigManCommentActivity.this, getString(R.string.input_content_not_null));
                                }
                            } else {
                                LoginUtil.getInstance().login(bigManCommentActivity.this);
                            }
                        }
                        return false;
                    }
                });
            }
        });
    }

    public void onClick(final View v) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vEvent(v.getId());
            }
        });
    }

    private void vEvent(int a) {
        switch (a) {
            case R.id.left_back:
                this.finish();
                break;
            case R.id.reselete:
                inputEdit.setText("");
                reselete.setVisibility(View.GONE);
                inputLayout.setVisibility(View.VISIBLE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                getEdit();
                break;
            case R.id.iv_post_face:
                inputEdit.requestFocus();
                showFaceList();
                break;
            case R.id.ib_face_delete:
                int selection = inputEdit.getSelectionStart();
                String text = inputEdit.getText().toString();
                if (selection > 0) {
                    String text2 = text.substring(selection - 1);
                    if ("]".equals(text2)) {
                        int start = text.lastIndexOf("[");
                        int end = selection;
                        inputEdit.getText().delete(start, end);
                        return;
                    }
                    inputEdit.getText().delete(selection - 1, selection);
                }
                getEdit();
                break;
            case R.id.btn_send:
                if (SharedPreferencesUtil.getInstance().isLogin()) {
                    String key = inputEdit.getText().toString().trim();
                    if (!TextUtils.isEmpty(key) && p != -1) {
                        commetnReply(commentData.get(p).getComment_id(), commentData.get(p).getUid(), key);
                    } else {
                        Map<String, String> BigManMap = new HashMap<>();
                        BigManMap.put("share_id", share_id);
                        BigManMap.put("content", key);
                        params.setData(BigManMap);
                        RetrofitManager.getmInstance().createService()
                                .bigManCommitComment(params)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<BaseModel>() {
                                    @Override
                                    public void onCompleted() {
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        ToastUtils.showGravity(bigManCommentActivity.this, "数据获取失败", Gravity.CENTER, 0, 0);
                                        recyclerView.setAdapter(null);
                                    }

                                    @Override
                                    public void onNext(BaseModel BigManBaseModel) {
                                        if (BigManBaseModel.getCode() == 200) {
                                            ToastUtils.showGravity(bigManCommentActivity.this, BigManBaseModel.getMsg() + "进来", Gravity.CENTER, 20, 200);
                                            queryBigManContent(1, page_size, true);
                                        } else {
                                            ToastUtils.showGravity(bigManCommentActivity.this, BigManBaseModel.getMsg(), Gravity.CENTER, 20, 200);
                                            recyclerView.setAdapter(null);
                                        }
                                    }
                                });
                    }
                } else {
                    LoginUtil.getInstance().login(bigManCommentActivity.this);
                }
                break;
        }
    }

    private void getEdit() {
        p = -1;
        inputEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                EditText edit = (EditText) v;
                String key = edit.getText().toString().trim();
                if (event != null
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (SharedPreferencesUtil.getInstance().isLogin()) {
                        if (!TextUtils.isEmpty(inputEdit.getText())) {
                            Map<String, String> BigManMap = new HashMap<>();
                            BigManMap.put("share_id", share_id);
                            BigManMap.put("content", key);
                            params.setData(BigManMap);
                            RetrofitManager.getmInstance().createService()
                                    .bigManCommitComment(params)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<BaseModel>() {
                                        @Override
                                        public void onCompleted() {
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            ToastUtils.showGravity(bigManCommentActivity.this, "数据获取失败", Gravity.CENTER, 0, 0);
                                            recyclerView.setAdapter(null);
                                        }

                                        @Override
                                        public void onNext(BaseModel BigManBaseModel) {
                                            if (BigManBaseModel.getCode() == 200) {
                                                ToastUtils.showGravity(bigManCommentActivity.this, BigManBaseModel.getMsg(), Gravity.CENTER, 20, 200);
                                                queryBigManContent(1, page_size, true);
                                            } else {
                                                ToastUtils.showGravity(bigManCommentActivity.this, BigManBaseModel.getMsg(), Gravity.CENTER, 20, 200);
                                                recyclerView.setAdapter(null);
                                            }
                                        }
                                    });
                        } else {
                            ToastUtils.showCenter(bigManCommentActivity.this, getString(R.string.input_content_not_null));
                        }
                    } else {
                        LoginUtil.getInstance().login(bigManCommentActivity.this);
                    }
                }
                return false;
            }
        });
    }

    //下拉刷新和上拉加载
    private void initRefreshAndLoad() {
        bigmanCommentSwiperefreshlayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                queryBigManContent(1, page_size, true);
                bigmanCommentSwiperefreshlayout.setRefreshing(false);
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });
        bigmanCommentSwiperefreshlayout.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                queryBigManContent(++page, page_size, false);
                bigmanCommentSwiperefreshlayout.setLoadMore(false);
            }

            @Override
            public void onPushDistance(int distance) {

            }

            @Override
            public void onPushEnable(boolean enable) {

            }
        });
    }


    //查询评论列表数据
    private void queryBigManContent(int page, int page_size, final boolean isRefresh) {
        Map<String, String> BigManMap = new HashMap<>();
        BigManMap.put("share_id", share_id);
        BigManMap.put("page", page + "");
        BigManMap.put("page_size", page_size + "");
        params.setData(BigManMap);
        RetrofitManager.getmInstance().createService()
                .bigManComment(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<bigManCommentBean>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        bigManCommentAdapter.notifyDataSetChanged();
                        ToastUtils.showGravity(bigManCommentActivity.this, "数据获取失败", Gravity.CENTER, 0, 0);
                    }

                    @Override
                    public void onNext(BaseModel<List<bigManCommentBean>> BigManBaseModel) {
                        if (BigManBaseModel.getCode() == 200) {
                            inputLayout.setVisibility(View.GONE);
                            reselete.setVisibility(View.VISIBLE);
                            imm.hideSoftInputFromWindow(inputEdit.getWindowToken(), 0); //强制隐藏键盘
                            List<bigManCommentBean> commentBeanList = BigManBaseModel.getData();
                            if (commentBeanList != null && commentBeanList.size() > 0) {
                                if (isRefresh) {
                                    commentData.clear();
                                    commentData.addAll(commentBeanList);
                                    bigManCommentAdapter.notifyDataSetChanged();
                                } else {
                                    commentData.addAll(commentBeanList);
                                    bigManCommentAdapter.addMoreItem(commentData);
                                }
                            }
                        } else {
                            ToastUtils.showGravity(bigManCommentActivity.this, BigManBaseModel.getMsg(), Gravity.CENTER, 20, 200);
                        }
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_big_man_comment;
    }


    /**
     * 评论回复
     *
     * @param comment_id
     * @param other_uid
     * @param content
     */
    private void commetnReply(String comment_id, String other_uid, String content) {
        Map<String, String> replyMap = new HashMap<>();
        replyMap.put("comment_id", comment_id);
        replyMap.put("other_uid", other_uid);
        replyMap.put("content", content);
        params.setData(replyMap);
        RetrofitManager.getmInstance().createService()
                .commentReply(params)
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
                            queryBigManContent(1, page_size, true);
                        }
                    }
                });

    }
//    @OnClick(R.id.input_edit) void setInputEdit(){
//        if (llFaceLayout!=null){
//            llFaceLayout.setVisibility(View.GONE);
//        }
//    }
//
//    @OnClick(R.id.iv_post_face) void setIvPostFace(){
//        inputEdit.requestFocus();
//        showFaceList();
//    }

    //删除表情
//    @OnClick(R.id.ib_face_delete) void deleteFace(){
//        int selection = inputEdit.getSelectionStart();
//        String text = inputEdit.getText().toString();
//        if (selection > 0) {
//            String text2 = text.substring(selection - 1);
//            if ("]".equals(text2)) {
//                int start = text.lastIndexOf("[");
//                int end = selection;
//                inputEdit.getText().delete(start, end);
//                return;
//            }
//            inputEdit.getText().delete(selection - 1, selection);
//        }
//    }

//    @OnClick(R.id.btn_send) void sendFace(){
//        if (!TextUtils.isEmpty(inputEdit.getText())) {
//
//        } else {
//            Toast.makeText(bigManCommentActivity.this, getString(R.string.input_content_not_null), Toast.LENGTH_SHORT).show();
//        }
//    }

    /**
     * 显示表情
     */
    public void showFaceList() {
        imm.hideSoftInputFromWindow(inputEdit.getWindowToken(), 0); //强制隐藏键盘
        vpFaceContains.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                for (int i = 0; i < rgFacePagePoint.getChildCount(); i++) {
                    RadioButton childAt = (RadioButton) rgFacePagePoint.getChildAt(i);
                    if (i == position + 1) {
                        childAt.setChecked(true);
                    } else {
                        childAt.setChecked(false);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        RadioButton rb_face_page_point = (RadioButton) findViewById(R.id.rb_face_page_point);
        ViewGroup.LayoutParams layoutParams = rb_face_page_point.getLayoutParams();
        List<View> pageViews = new ArrayList<View>();
        List<FaceAdapter> faceAdapters = new ArrayList<>();

        List<List<EmojiBean>> emojis = FileUtil.getAssetsFaceList();
        for (int i = 0; i < emojis.size(); i++) {
            RadioButton rb_face_point = new RadioButton(this);
            rb_face_point.setButtonDrawable(R.drawable.selector_face_point);
            rb_face_point.setLayoutParams(layoutParams);
            rb_face_point.setEnabled(false);
            if (i == 0) {
                rb_face_point.setChecked(true);
            }
            rgFacePagePoint.addView(rb_face_point);
            GridView view = new GridView(this);
            final FaceAdapter adapter = new FaceAdapter(this, emojis.get(i));
            view.setAdapter(adapter);
            faceAdapters.add(adapter);
            view.setNumColumns(7);
            view.setBackgroundColor(Color.TRANSPARENT);
            view.setHorizontalSpacing(1);
            view.setVerticalSpacing(1);
            view.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            view.setCacheColorHint(0);
            view.setPadding(5, 0, 5, 0);
            view.setSelector(new ColorDrawable(Color.TRANSPARENT));
            view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            view.setGravity(Gravity.CENTER);
            pageViews.add(view);
            view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    List<EmojiBean> emojiBeans = adapter.getData();
                    EmojiBean emojiBean = emojiBeans.get(position);
                    int currentPosition = inputEdit.getSelectionStart();//得到当前光标位置
                    inputEdit.getText().insert(currentPosition, addFace(emojiBean.getFileName(), emojiBean.getText()));//插入表情
                }
            });
        }
        PagerAdapter faceAdapter = new FacePageAdapter(pageViews);
        vpFaceContains.setAdapter(faceAdapter);
        if (llFaceLayout.getVisibility() == View.VISIBLE) {
            llFaceLayout.setVisibility(View.GONE);
        } else {
            llFaceLayout.setVisibility(View.VISIBLE);
            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(vpFaceContains.getApplicationWindowToken(), 0);
        }
    }

    //添加表情
    private SpannableString addFace(String fileName, String spannableString) {
        Bitmap bitmap = FileUtil.getImageFromAssetsFile(fileName, true);
        int sp2px = DimenUtils.sp2px(this, 20);
        bitmap = Bitmap.createScaledBitmap(bitmap, sp2px, sp2px, true);
        ImageSpan imageSpan = new ImageSpan(this, bitmap);
        SpannableString spannable = new SpannableString(spannableString);
        spannable.setSpan(imageSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

}
