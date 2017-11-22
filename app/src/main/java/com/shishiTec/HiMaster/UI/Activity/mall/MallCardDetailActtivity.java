package com.shishiTec.HiMaster.UI.Activity.mall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.shishiTec.HiMaster.Model.bean.BuySureBean;
import com.shishiTec.HiMaster.Model.bean.CardDetailBean;
import com.shishiTec.HiMaster.Model.bean.CardOrderBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
import com.shishiTec.HiMaster.Utils.DateUtil;
import com.shishiTec.HiMaster.Utils.PayResult;
import com.shishiTec.HiMaster.base.BaseActivity;
import com.shishiTec.HiMaster.base.BaseApplication;
import com.shishiTec.HiMaster.base.BaseModel;
import com.shishiTec.HiMaster.wxapi.WXPayEntryActivity;
import com.shishiTec.HiMaster.wxapi.WXPayUtil;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MallCardDetailActtivity extends BaseActivity implements View.OnClickListener {
    private String card_id;
    @Bind(R.id.flip_intro)
    ImageButton flipIntro;
    @Bind(R.id.use_intro)
    TextView useIntro;
    @Bind(R.id.show_negative)
    RelativeLayout showNegative;
    @Bind(R.id.card_img)
    ImageView cardImg;
    @Bind(R.id.flip)
    TextView flip;
    @Bind(R.id.date_limit)
    TextView dateLimit;
    @Bind(R.id.show_front)
    RelativeLayout showFront;
    @Bind(R.id.tv_intro)
    TextView tv_intro;
    private Button buy_card;
    private RelativeLayout AliPay, WeChat, UnionPay;//支付宝，银联和微信支付父布局
    private ImageView zhifubao_cir_img, yinlian_cir_img, weixin_cir_img;
    private RelativeLayout coupon_rl;
    private int payType = 1;//支付选择
    private static final int CARDDETAIL = 3600;
    private CardOrderBean cardOrderBean;
    private String condition_type = "";
    private String condition_id = "";
    private TextView coupon_name;
    private TextView tv_price;
    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;

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
        Intent intent = getIntent();
        card_id = intent.getStringExtra("card_id");
        AliPay = (RelativeLayout) findViewById(R.id.AliPay);
        WeChat = (RelativeLayout) findViewById(R.id.WeChat);
        UnionPay = (RelativeLayout) findViewById(R.id.UnionPay);
        tv_price = (TextView) findViewById(R.id.tv_price);
        AliPay.setOnClickListener(this);
        WeChat.setOnClickListener(this);
        UnionPay.setOnClickListener(this);
        zhifubao_cir_img = (ImageView) findViewById(R.id.zhifubao_cir_img);
        yinlian_cir_img = (ImageView) findViewById(R.id.yinlian_cir_img);
        weixin_cir_img = (ImageView) findViewById(R.id.weixin_cir_img);
        buy_card = (Button) findViewById(R.id.buy_card);
        coupon_rl = (RelativeLayout) findViewById(R.id.coupon_rl);
        coupon_name = (TextView) findViewById(R.id.coupon_name);
        coupon_rl.setOnClickListener(this);
        buy_card.setOnClickListener(this);
        Map<String, String> map = new HashMap<>();
        map.put("card_id", card_id);
        initData(map);
        cardOrderData(map);
    }

    private void cardOrderData(Map<String, String> map) {
        params.setData(map);
        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .cardOrder(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<CardOrderBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("courseDetail", e.getMessage() + "11");
                    }

                    @Override
                    public void onNext(BaseModel<CardOrderBean> mb) {
                        if (mb.getCode() == 200) {
                            cardOrderBean = mb.getData();
                            tv_price.setText(cardOrderBean.getPrice());
                            if(cardOrderBean.getCondition().getCoupon().size()>0){
                                coupon_rl.setVisibility(View.VISIBLE);
                            }else{
                                coupon_rl.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }

    private void initData(Map<String, String> map) {
        params.setData(map);
        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .cardDetail(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<CardDetailBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("courseDetail", e.getMessage() + "11");
                    }

                    @Override
                    public void onNext(BaseModel<CardDetailBean> mb) {
                        if (mb.getCode() == 200) {
                            BaseApplication.getInstance().loadImageALLView(MallCardDetailActtivity.this, cardImg, mb.getData().getPic_url());
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("使用期限:");
                            stringBuilder.append(DateUtil.getYearMonthDay(Long.parseLong(mb.getData().getUp_time())));
                            stringBuilder.append(DateUtil.getYearMonthDay(Long.parseLong(mb.getData().getDown_time())));
                            dateLimit.setText(stringBuilder.toString());
                            tv_intro.setText(mb.getData().getIntro());
                        }
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mall_card_detail_acttivity;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.AliPay:
                payType = 1;
                zhifubao_cir_img.setImageResource(R.mipmap.circular);
                weixin_cir_img.setImageResource(R.mipmap.circular_black);
                yinlian_cir_img.setImageResource(R.mipmap.circular_black);
                break;
            case R.id.WeChat:
                payType = 4;
                zhifubao_cir_img.setImageResource(R.mipmap.circular_black);
                weixin_cir_img.setImageResource(R.mipmap.circular);
                yinlian_cir_img.setImageResource(R.mipmap.circular_black);
                break;
            case R.id.UnionPay:
                payType = 2;
                zhifubao_cir_img.setImageResource(R.mipmap.circular_black);
                weixin_cir_img.setImageResource(R.mipmap.circular_black);
                yinlian_cir_img.setImageResource(R.mipmap.circular);
                break;
            case R.id.buy_card:
                pay(payType);
                break;
            case R.id.coupon_rl:
                Intent intent = new Intent(MallCardDetailActtivity.this, MallCouponActivity.class);
                Gson gson = new Gson();
                String coupon = gson.toJson(cardOrderBean.getCondition().getCoupon());
                intent.putExtra("cardCouponList", coupon);
                startActivityForResult(intent, CARDDETAIL);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CARDDETAIL:
                String couponPrice = data.getStringExtra("couponPrice");
                if (couponPrice.equals("") || couponPrice == null) {
                    return;
                }
                condition_id = data.getStringExtra("condition_id");
                condition_type = data.getStringExtra("condition_type");
                int cp = Integer.parseInt(couponPrice);
                int price = Integer.parseInt(cardOrderBean.getPrice());
                String couponName = data.getStringExtra("couponName");
                coupon_name.setText(couponName);
                tv_price.setText((cp - price) + "");
                break;
            case 10:
                Bundle b = data.getExtras();
                String str = b.getString("pay_result");
                if (str.equalsIgnoreCase("success")) {
                    Log.d("UPTest", "onActivityResult,银联支付成功");
                    Toast.makeText(MallCardDetailActtivity.this, "支付成功",
                            Toast.LENGTH_SHORT).show();
                } else if (str.equalsIgnoreCase("fail")) {
                    Log.d("UPTest", "onActivityResult,银联支付失败");
                    Toast.makeText(MallCardDetailActtivity.this, "支付失败",
                            Toast.LENGTH_SHORT).show();
                } else if (str.equalsIgnoreCase("cancel")) {
                    Log.d("UPTest", "onActivityResult,银联支付取消");
                    Toast.makeText(MallCardDetailActtivity.this, "取消支付",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
//                        pay_sure.setEnabled(true);
                        Toast.makeText(MallCardDetailActtivity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(MallCardDetailActtivity.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
//                            pay_sure.setEnabled(true);
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(MallCardDetailActtivity.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(MallCardDetailActtivity.this, "返回结果为" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                default:
                    break;
            }
        }
    };

    private void pay(final int type) {
        Map<String, String> map = new HashMap<>();
        map.put("card_id", card_id);
        map.put("zf_type", type + "");
        map.put("mobile", cardOrderBean.getMobile());
        map.put("condition_type", condition_type);
        map.put("condition_id", condition_id);
        params.setData(map);
        RetrofitManager.getmInstance()
                .createService()
                .payCard(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<BuySureBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("courseDetail", e.getMessage() + "11");
                    }

                    @Override
                    public void onNext(final BaseModel<BuySureBean> mb) {
                        if (mb.getCode() == 200) {
                            if (type == 4) {
                                //微信支付
                                WXPayEntryActivity.tag = 0;
                                IWXAPI msgApi = WXAPIFactory.createWXAPI(MallCardDetailActtivity.this, null);
                                msgApi.registerApp(WXPayUtil.APP_ID);
                                PayReq req = new PayReq();
                                req.appId = WXPayUtil.APP_ID;
                                req.partnerId = mb.getData().getPay_param().getPartnerid();
                                req.prepayId = mb.getData().getPay_param().getPrepayid();
                                req.packageValue = mb.getData().getPay_param().getPackageX();
                                req.nonceStr = mb.getData().getPay_param().getNoncestr();
                                req.timeStamp = mb.getData().getPay_param().getTimestamp() + "";
                                req.sign = mb.getData().getPay_param().getSign();
                                msgApi.sendReq(req);
                            } else if (type == 1) {
                                Runnable payRunnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        // 构造PayTask 对象
                                        PayTask alipay = new PayTask(MallCardDetailActtivity.this);
                                        // 调用支付接口，获取支付结果
                                        String result = alipay.pay(mb.getData().getPay_param().getSign());
                                        Message msg = new Message();
                                        msg.what = SDK_PAY_FLAG;
                                        msg.obj = result;
                                        mHandler.sendMessage(msg);
                                    }
                                };
                                // 必须异步调用
                                Thread payThread = new Thread(payRunnable);
                                payThread.start();
                            } else if (type == 2) {
                                // mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
                                final String mMode = "01";
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        UPPayAssistEx.startPayByJAR(MallCardDetailActtivity.this, PayActivity.class, null, null, mb.getData().getPay_param().getSign(), mMode);
                                    }
                                }).start();
                            }
                        }
                    }
                });
    }
}
