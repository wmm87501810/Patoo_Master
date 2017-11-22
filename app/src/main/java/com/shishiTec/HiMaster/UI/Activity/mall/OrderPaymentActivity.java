package com.shishiTec.HiMaster.UI.Activity.mall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.shishiTec.HiMaster.Model.bean.BuySureBean;
import com.shishiTec.HiMaster.Model.bean.PaySureBean;
import com.shishiTec.HiMaster.Net.RetrofitManager;
import com.shishiTec.HiMaster.R;
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

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OrderPaymentActivity extends BaseActivity implements View.OnClickListener {
    private static final int CARD = 3366;
    private static final int COUPON = 6633;
    private ImageView left_back;
    private String course_id = "", course_cfg_id = "";
    private ImageView iv_course_cover;//商品cover
    private TextView tv_course_title;//商品name
    private TextView tv_course_price;//商品price
    private TextView tv_price;//支付的价格
    private RelativeLayout rl_add_or_reduce_num;//商品加减布局
    private TextView tv_num;//商品num
    private TextView tv_add_or_reduce_num;//商品加减num
    private TextView pay_address;//商品地址
    private TextView tv_add_num, tv_reduce_num;
    private RelativeLayout class_time_rl;//上课时间父布局
    private RelativeLayout soy_sauce_card_rl;//酱油卡父布局
    private RelativeLayout coupon_rl;//优惠券父布局
    private TextView tv_package_title;//套餐name
    private EditText tv_user_phone;//用户电话
    private PaySureBean paySureBean;
    private TextView time_order;//选择时间
    private String dates, num, choose_time;
    private String time_id = "";
    private String condition_type = "";
    private String condition_id = "";
    private boolean coupon = false;
    private boolean card = false;
    private boolean isCard, isCoupon;
    private TextView new_user_tv;//新用户首单name
    private RelativeLayout rl_new_user;//新用户首单
    private TextView coupon_name;
    private TextView card_name;
    private ImageView new_user;
    private TextView user_pay;//支付按钮
    private TextView pay_type_tv;//支付方式标题
    private boolean img_new_user = true;
    private RelativeLayout AliPay, WeChat, UnionPay;//支付宝，银联和微信支付父布局
    private ImageView zhifubao_cir_img, yinlian_cir_img, weixin_cir_img;
    private int payType = 1;//支付选择
    private String is_mpay = "";
    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;
    @Override
    public int getLayoutId() {
        return R.layout.activity_order_payment;
    }

    @Override
    public String[] getPermissions() {
        return new String[0];
    }

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        course_id = intent.getStringExtra("course_id");
        course_cfg_id = intent.getStringExtra("course_cfg_id");
        dates = intent.getStringExtra("dates");
        num = intent.getStringExtra("num");
        choose_time = intent.getStringExtra("choose_time");
        time_id = intent.getStringExtra("time_id");
        is_mpay = intent.getStringExtra("is_mpay");
        initData();
        AliPay = (RelativeLayout) findViewById(R.id.AliPay);
        WeChat = (RelativeLayout) findViewById(R.id.WeChat);
        UnionPay = (RelativeLayout) findViewById(R.id.UnionPay);
        AliPay.setOnClickListener(this);
        WeChat.setOnClickListener(this);
        UnionPay.setOnClickListener(this);
        zhifubao_cir_img = (ImageView) findViewById(R.id.zhifubao_cir_img);
        yinlian_cir_img = (ImageView) findViewById(R.id.yinlian_cir_img);
        weixin_cir_img = (ImageView) findViewById(R.id.weixin_cir_img);
        left_back = (ImageView) findViewById(R.id.left_back);
        iv_course_cover = (ImageView) findViewById(R.id.iv_course_cover);
        time_order = (TextView) findViewById(R.id.time_order);
        new_user = (ImageView) findViewById(R.id.new_user);
        tv_price = (TextView) findViewById(R.id.tv_price);
        coupon_name = (TextView) findViewById(R.id.coupon_name);
        tv_course_title = (TextView) findViewById(R.id.tv_course_title);
        tv_course_price = (TextView) findViewById(R.id.tv_course_price);
        tv_user_phone = (EditText) findViewById(R.id.tv_user_phone);
        card_name = (TextView) findViewById(R.id.card_name);
        new_user_tv = (TextView) findViewById(R.id.new_user_tv);
        rl_add_or_reduce_num = (RelativeLayout) findViewById(R.id.rl_add_or_reduce_num);
        class_time_rl = (RelativeLayout) findViewById(R.id.class_time_rl);
        rl_new_user = (RelativeLayout) findViewById(R.id.rl_new_user);
        soy_sauce_card_rl = (RelativeLayout) findViewById(R.id.soy_sauce_card_rl);
        pay_type_tv = (TextView) findViewById(R.id.pay_type_tv);
        soy_sauce_card_rl.setOnClickListener(this);
        coupon_rl = (RelativeLayout) findViewById(R.id.coupon_rl);
        coupon_rl.setOnClickListener(this);
        tv_add_or_reduce_num = (TextView) findViewById(R.id.tv_add_or_reduce_num);
        tv_add_num = (TextView) findViewById(R.id.tv_add_num);
        tv_reduce_num = (TextView) findViewById(R.id.tv_reduce_num);
        user_pay = (TextView) findViewById(R.id.user_pay);
        user_pay.setOnClickListener(this);
        tv_add_num.setOnClickListener(this);
        tv_reduce_num.setOnClickListener(this);
        tv_package_title = (TextView) findViewById(R.id.tv_package_title);
        tv_num = (TextView) findViewById(R.id.tv_num);
        pay_address = (TextView) findViewById(R.id.pay_address);
        if (dates != null) {
            time_order.setText(dates + " " + choose_time);
            class_time_rl.setVisibility(View.VISIBLE);
        } else {
            class_time_rl.setVisibility(View.GONE);
        }
        if (num != null) {
            tv_num.setText("X  " + num);
            tv_num.setVisibility(View.VISIBLE);
            rl_add_or_reduce_num.setVisibility(View.GONE);
        } else {
            tv_num.setVisibility(View.GONE);
            rl_add_or_reduce_num.setVisibility(View.VISIBLE);
        }
        left_back.setOnClickListener(this);

    }

    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("course_cfg_id", course_cfg_id);
        map.put("course_id", course_id);
        params.setData(map);
        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .paySure(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel<PaySureBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("courseDetail", e.getMessage() + "11");
                    }

                    @Override
                    public void onNext(BaseModel<PaySureBean> mb) {
                        if (mb.getCode() == 200) {
                            paySureBean = mb.getData();
                            BaseApplication.getInstance().loadImageALLView(OrderPaymentActivity.this, iv_course_cover, paySureBean.getCover());
                            tv_course_title.setText(paySureBean.getTitle());
                            if (is_mpay.equals("0")) {
                                tv_course_price.setText(paySureBean.getPrice());
                                if (num != null) {
                                    double number = Double.parseDouble(num);
                                    double price = Double.parseDouble(paySureBean.getPrice());
                                    double allPrice = number * price;
                                    tv_price.setText(allPrice + "");
                                } else {
                                    tv_price.setText(paySureBean.getPrice());
                                }
                                if (paySureBean.getCustom_spec_name().equals("")) {
                                    tv_package_title.setText("购买数量");
                                } else {
                                    tv_package_title.setText(paySureBean.getCustom_spec_name());
                                }
                                pay_address.setText(paySureBean.getAddress());
                                tv_user_phone.setText(paySureBean.getMobile());
                                if (paySureBean.getCondition().getCard().size() == 0) {
                                    soy_sauce_card_rl.setVisibility(View.GONE);
                                } else {
                                    soy_sauce_card_rl.setVisibility(View.VISIBLE);
                                }
                                if (paySureBean.getCondition().getCoupon().size() == 0) {
                                    coupon_rl.setVisibility(View.GONE);
                                } else {
                                    coupon_rl.setVisibility(View.VISIBLE);
                                }
                                if (paySureBean.getCondition().getNew_user().size() > 0) {
                                    rl_new_user.setVisibility(View.VISIBLE);
                                    new_user.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            int new_user_num;
                                            double p1 = Double.parseDouble(tv_course_price.getText().toString());
                                            if (img_new_user) {
                                                img_new_user = false;
                                                condition_id = paySureBean.getCondition().getNew_user().get(0).getCondition_id();
                                                condition_type = paySureBean.getCondition().getNew_user().get(0).getCondition_type();
                                                double new_user_price = Double.parseDouble(paySureBean.getCondition().getNew_user().get(0).getPrice());
                                                if (num != null) {
                                                    new_user_num = Integer.parseInt(num);
                                                } else {
                                                    new_user_num = Integer.parseInt(tv_add_or_reduce_num.getText().toString());
                                                }
                                                double p = p1 * new_user_num - new_user_price;
                                                tv_price.setText(p + "");
                                                coupon_name.setText("");
                                                card_name.setText("");
                                                new_user_tv.setText(paySureBean.getCondition().getNew_user().get(0).getTitle());
                                                new_user.setImageResource(R.mipmap.open);
                                            } else {
                                                img_new_user = true;
                                                if (num != null) {
                                                    new_user_num = Integer.parseInt(num);
                                                } else {
                                                    new_user_num = Integer.parseInt(tv_add_or_reduce_num.getText().toString());
                                                }
                                                double p = p1 * new_user_num;
                                                tv_price.setText(p + "");
                                                coupon_name.setText("");
                                                card_name.setText("");
                                                new_user_tv.setText("新用户首单减免");
                                                new_user.setImageResource(R.mipmap.shut);
                                            }

                                        }
                                    });
                                } else {
                                    rl_new_user.setVisibility(View.GONE);
                                }
                            } else if (is_mpay.equals("1")) {
                                tv_course_price.setText(paySureBean.getM_price());
                                tv_package_title.setText("购买数量");
                                rl_add_or_reduce_num.setVisibility(View.GONE);
                                tv_num.setVisibility(View.VISIBLE);
                                tv_num.setText("1");
                                pay_address.setText(paySureBean.getAddress());
                                class_time_rl.setVisibility(View.GONE);
                                tv_user_phone.setText(paySureBean.getMobile());
                                soy_sauce_card_rl.setVisibility(View.GONE);
                                coupon_rl.setVisibility(View.GONE);
                                rl_new_user.setVisibility(View.GONE);
                                pay_type_tv.setVisibility(View.GONE);
                                AliPay.setVisibility(View.GONE);
                                WeChat.setVisibility(View.GONE);
                                UnionPay.setVisibility(View.GONE);
                                tv_price.setText(paySureBean.getM_price() + "  M点");
                            }
                        }
                    }
                });
    }


    @Override
    public void onClick(View view) {
        Gson gson = new Gson();
        switch (view.getId()) {
            case R.id.left_back:
                this.finish();
                break;
            case R.id.tv_reduce_num:
                if (!tv_add_or_reduce_num.getText().toString().equals("1")) {
                    tv_add_or_reduce_num.setText((Integer.parseInt(tv_add_or_reduce_num.getText().toString()) - 1) + "");
                    CountNumPrice(false);
                } else {
                    Toast.makeText(OrderPaymentActivity.this, "已经不能再少了", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_add_num:
                if (Integer.parseInt(tv_add_or_reduce_num.getText().toString()) < Integer.parseInt(paySureBean.getCan_buy_num())) {
                    tv_add_or_reduce_num.setText((Integer.parseInt(tv_add_or_reduce_num.getText().toString()) + 1) + "");
                    CountNumPrice(true);
                } else {
                    Toast.makeText(OrderPaymentActivity.this, "已达到最大购买数量", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.soy_sauce_card_rl:
                Intent in = new Intent(OrderPaymentActivity.this, MallCardActivity.class);
                String card = gson.toJson(paySureBean.getCondition().getCard());
                in.putExtra("cardList", card);
                startActivityForResult(in, CARD);
                break;
            case R.id.coupon_rl:
                Intent intent = new Intent(OrderPaymentActivity.this, MallCouponActivity.class);
                String coupon = gson.toJson(paySureBean.getCondition().getCoupon());
                intent.putExtra("couponList", coupon);
                startActivityForResult(intent, COUPON);
                break;
            case R.id.user_pay:
                if (is_mpay.equals("1")) {
                    double mp = Double.parseDouble(paySureBean.getM_price());
                    double mymp = Double.parseDouble(paySureBean.getMy_mpoints());
                    if (mp > mymp) {
                        Toast.makeText(OrderPaymentActivity.this, "您当前的M点不足", Toast.LENGTH_SHORT).show();
                    } else {
                        pay(1);
                    }
                } else {
                    String totalPrice = tv_price.getText().toString().trim();

                    if (Double.parseDouble(totalPrice) > 0) {
                        pay(payType);
                    } else {
                        pay(payType);
                    }
                }
                break;
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
        }
    }

    public void CountNumPrice(boolean b) {
        double price = Double.parseDouble(tv_price.getText().toString());
        double course_price = Double.parseDouble(tv_course_price.getText().toString());
        double allPrice;
        if (b) {
            allPrice = price + course_price;
        } else {
            allPrice = price - course_price;
        }
        tv_price.setText(allPrice + "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        double p1 = Double.parseDouble(tv_course_price.getText().toString());
        switch (requestCode) {
            case CARD:
                String cardNum = data.getStringExtra("cardNum");
                condition_id = data.getStringExtra("condition_id");
                condition_type = data.getStringExtra("condition_type");
                String cardName = data.getStringExtra("cardName");
                int cardNumber;
                if (cardNum != null && !cardNum.equals("") && !cardNum.equals("0")) {
                    if (num != null) {
                        cardNumber = Integer.parseInt(num);
                    } else {
                        cardNumber = Integer.parseInt(tv_add_or_reduce_num.getText().toString());
                    }
                    double p = p1 * cardNumber - p1;
                    tv_price.setText(p + "");
                    coupon_name.setText("");
                    new_user_tv.setText("新用户首单减免");
                    card_name.setText(cardName);
                    new_user.setImageResource(R.mipmap.shut);
                    img_new_user = true;
                }
                break;
            case COUPON:
                String couponPrice = data.getStringExtra("couponPrice");
                if (couponPrice.equals("") || couponPrice == null) {
                    return;
                }
                condition_id = data.getStringExtra("condition_id");
                condition_type = data.getStringExtra("condition_type");
                double cp = Double.parseDouble(couponPrice);
                String couponName = data.getStringExtra("couponName");
                int couponNumber;
                if (couponPrice != null && !couponPrice.equals("")) {
                    if (num != null) {
                        couponNumber = Integer.parseInt(num);
                    } else {
                        couponNumber = Integer.parseInt(tv_add_or_reduce_num.getText().toString());
                    }
                    double p = p1 * couponNumber - cp;
                    tv_price.setText(p + "");
                    coupon_name.setText(couponName);
                    card_name.setText("");
                    new_user_tv.setText("新用户首单减免");
                    new_user.setImageResource(R.mipmap.shut);
                    img_new_user = true;
                }
                break;
            case 10:
                Bundle b = data.getExtras();
                String str = b.getString("pay_result");
                if (str.equalsIgnoreCase("success")) {
                    Log.d("UPTest", "onActivityResult,银联支付成功");
                    Toast.makeText(OrderPaymentActivity.this, "支付成功",
                            Toast.LENGTH_SHORT).show();
                } else if (str.equalsIgnoreCase("fail")) {
                    Log.d("UPTest", "onActivityResult,银联支付失败");
                    Toast.makeText(OrderPaymentActivity.this, "支付失败",
                            Toast.LENGTH_SHORT).show();
                } else if (str.equalsIgnoreCase("cancel")) {
                    Log.d("UPTest", "onActivityResult,银联支付取消");
                    Toast.makeText(OrderPaymentActivity.this, "取消支付",
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
                        Toast.makeText(OrderPaymentActivity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(OrderPaymentActivity.this, "支付结果确认中",
                                    Toast.LENGTH_SHORT).show();

                        } else {
//                            pay_sure.setEnabled(true);
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(OrderPaymentActivity.this, "支付失败",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(OrderPaymentActivity.this, "返回结果为" + msg.obj,
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
        map.put("course_cfg_id", course_cfg_id);
        map.put("course_id", course_id);
        map.put("time_id", time_id);
        if (num != null) {
            map.put("num", num);
        } else {
            map.put("num", tv_add_or_reduce_num.getText().toString());
        }
        map.put("zf_type", type + "");
        map.put("mobile", tv_user_phone.getText().toString().trim());
        map.put("condition_type", condition_type);
        map.put("condition_id", condition_id);
        params.setData(map);
        Subscription subscribe = RetrofitManager.getmInstance()
                .createService()
                .buySure(params)
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
                                IWXAPI msgApi = WXAPIFactory.createWXAPI(OrderPaymentActivity.this, null);
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
                            }else if(type==1){
                                Runnable payRunnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        // 构造PayTask 对象
                                        PayTask alipay = new PayTask(OrderPaymentActivity.this);
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
                            }else if(type ==2){
                                // mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
                                final String mMode = "01";
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        UPPayAssistEx.startPayByJAR(OrderPaymentActivity.this, PayActivity.class, null, null,mb.getData().getPay_param().getSign(), mMode);
                                    }
                                }).start();
                            }
                        }
                    }
                });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            OrderPaymentActivity.this.finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
