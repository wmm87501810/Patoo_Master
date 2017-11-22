package com.shishiTec.HiMaster.UI.Activity.mall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shishiTec.HiMaster.Model.bean.CardOrderBean;
import com.shishiTec.HiMaster.Model.bean.PaySureBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.mall.MallCouponAdapter;
import com.shishiTec.HiMaster.UI.Adapter.userCenter.CardCouponAdapter;
import com.shishiTec.HiMaster.base.BaseActivity;

import java.util.List;

public class MallCouponActivity extends BaseActivity {
    private RecyclerView mall_coupon_recycle;
    private LinearLayoutManager manager;
    private Gson gson;
    private List<PaySureBean.ConditionBean.CouponBean> couponListBean;
    private List<CardOrderBean.ConditionBean.CouponBean> couponBean;

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
        gson = new Gson();
        Intent i = getIntent();
        couponListBean = gson.fromJson(i.getStringExtra("couponList"), new TypeToken<List<PaySureBean.ConditionBean.CouponBean>>() {
        }.getType());
        couponBean = gson.fromJson(i.getStringExtra("cardCouponList"), new TypeToken<List<CardOrderBean.ConditionBean.CouponBean>>() {
        }.getType());
        mall_coupon_recycle = (RecyclerView) findViewById(R.id.mall_coupon_recycle);
        manager = new LinearLayoutManager(MallCouponActivity.this);
        mall_coupon_recycle.setLayoutManager(manager);
        if (couponListBean != null) {
            MallCouponAdapter mallCouponAdapter = new MallCouponAdapter(MallCouponActivity.this, couponListBean);
            mall_coupon_recycle.setAdapter(mallCouponAdapter);
            mallCouponAdapter.setOnItemClickListener(new MallCouponAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(int position, View view) {
                    Intent intent = new Intent();
                    intent.putExtra("couponPrice", couponListBean.get(position).getPrice());
                    intent.putExtra("condition_type", couponListBean.get(position).getCondition_type());
                    intent.putExtra("condition_id", couponListBean.get(position).getCondition_id());
                    intent.putExtra("couponName", couponListBean.get(position).getCoupon_name());
                    MallCouponActivity.this.setResult(RESULT_OK, intent);
                    MallCouponActivity.this.finish();
                }
            });
        } else if (couponBean != null) {
            CardCouponAdapter cardCouponAdapter = new CardCouponAdapter(MallCouponActivity.this, couponBean);
            mall_coupon_recycle.setAdapter(cardCouponAdapter);
            cardCouponAdapter.setOnItemClickListener(new CardCouponAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(int position, View view) {
                    Intent intent = new Intent();
                    intent.putExtra("couponPrice", couponBean.get(position).getPrice());
                    intent.putExtra("condition_type", couponBean.get(position).getCondition_type());
                    intent.putExtra("condition_id", couponBean.get(position).getCondition_id());
                    intent.putExtra("couponName", couponBean.get(position).getCoupon_name());
                    MallCouponActivity.this.setResult(RESULT_OK, intent);
                    MallCouponActivity.this.finish();
                }
            });
        }


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mall_coupon;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            Intent intent = new Intent();
            intent.putExtra("couponPrice", "");
            MallCouponActivity.this.setResult(RESULT_OK, intent);
            MallCouponActivity.this.finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
