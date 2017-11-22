package com.shishiTec.HiMaster.UI.Activity.mall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shishiTec.HiMaster.Model.bean.PaySureBean;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.UI.Adapter.mall.MallCardAdapter;
import com.shishiTec.HiMaster.base.BaseActivity;

import java.util.List;

public class MallCardActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private int page = 1;
    private int page_size = 10;
    private Gson gson;
    private List<PaySureBean.ConditionBean.CardBean> cardBeen;

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
        cardBeen = gson.fromJson(i.getStringExtra("cardList"), new TypeToken<List<PaySureBean.ConditionBean.CardBean>>() {
        }.getType());
        recyclerView = (RecyclerView) findViewById(R.id.mall_card_recycle);
        manager = new LinearLayoutManager(MallCardActivity.this);
        recyclerView.setLayoutManager(manager);

        MallCardAdapter mallCardAdapter = new MallCardAdapter(MallCardActivity.this, cardBeen);
        recyclerView.setAdapter(mallCardAdapter);
        mallCardAdapter.setOnItemClickListener(new MallCardAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position, View view) {
                if (cardBeen.get(position).getValidNum().equals("0")) {
                    Toast.makeText(MallCardActivity.this, "该券次数已用完", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("cardNum", cardBeen.get(position).getValidNum());
                intent.putExtra("condition_type", cardBeen.get(position).getCondition_type());
                intent.putExtra("condition_id", cardBeen.get(position).getCondition_id());
                intent.putExtra("cardName", cardBeen.get(position).getCardTitle());
                MallCardActivity.this.setResult(RESULT_OK, intent);
                MallCardActivity.this.finish();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mall_card;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            Intent intent = new Intent();
            intent.putExtra("cardNum", "");
            MallCardActivity.this.setResult(RESULT_OK, intent);
            MallCardActivity.this.finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
