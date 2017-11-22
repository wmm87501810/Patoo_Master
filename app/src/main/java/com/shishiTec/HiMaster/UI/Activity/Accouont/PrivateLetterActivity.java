package com.shishiTec.HiMaster.UI.Activity.Accouont;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import com.shishiTec.HiMaster.Model.bean.EmojiBean;
import com.shishiTec.HiMaster.Model.bean.PrivateNewsListDetailBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.face.FaceAdapter;
import com.shishiTec.HiMaster.UI.Adapter.face.FacePageAdapter;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.PrivateLetterAdapter;
import com.shishiTec.HiMaster.Utils.DimenUtils;
import com.shishiTec.HiMaster.Utils.DividerItemDecoration;
import com.shishiTec.HiMaster.Utils.FileUtil;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PrivateLetterActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView rv_private_letter;
    ViewPager vpFaceContains;
    EditText inputEdit;
    private InputMethodManager imm;
    LinearLayout llFaceLayout;
    RadioGroup rgFacePagePoint;
    RadioButton rbFacePagePoint;
    TextView reselete;
    LinearLayout inputLayout;
    ImageButton ibFaceDelete;
    ImageButton ivPostFace;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        rv_private_letter = (RecyclerView) findViewById(R.id.rv_private_letter);
        reselete = (TextView) findViewById(R.id.reselete);
        inputLayout = (LinearLayout) findViewById(R.id.input_layout);
        LinearLayoutManager privateLetterManager = new LinearLayoutManager(this);
        privateLetterManager.setOrientation(LinearLayoutManager.VERTICAL);
        //添加分割线
        rv_private_letter.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.HORIZONTAL_LIST));
        rv_private_letter.setLayoutManager(privateLetterManager);
        rv_private_letter.setItemAnimator(new DefaultItemAnimator());
        vpFaceContains = (ViewPager) findViewById(R.id.vp_face_contains);
        llFaceLayout = (LinearLayout) findViewById(R.id.ll_face_layout);
        inputEdit = (EditText) findViewById(R.id.input_edit);
        rbFacePagePoint = (RadioButton) findViewById(R.id.rb_face_page_point);
        rgFacePagePoint = (RadioGroup) findViewById(R.id.rg_face_page_point);
        ivPostFace = (ImageButton) findViewById(R.id.iv_post_face);
        ibFaceDelete = (ImageButton) findViewById(R.id.ib_face_delete);
        inputEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                llFaceLayout.setVisibility(View.GONE);
                return false;
            }
        });
        reselete.setOnClickListener(this);
        ivPostFace.setOnClickListener(this);
        ibFaceDelete.setOnClickListener(this);
        initData();
    }

    //初始化数据
    public void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("page_size", "100");
        map.put("tag", "1");
        map.put("other_uid", getIntent().getStringExtra("other_uid"));
        params.setData(map);
        Subscription subscription = RetrofitManager.getmInstance().createService()
                .getPrivateNewsListDetail(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<List<PrivateNewsListDetailBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel<List<PrivateNewsListDetailBean>> listBaseModel) {
                        PrivateLetterAdapter privateLetterAdapter = new PrivateLetterAdapter(PrivateLetterActivity.this, listBaseModel.getData());
                        rv_private_letter.setAdapter(privateLetterAdapter);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_private_letter;
    }

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reselete:
                inputEdit.setText("");
                reselete.setVisibility(View.GONE);
                inputLayout.setVisibility(View.VISIBLE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
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
                break;
        }
    }
}
