package com.shishiTec.HiMaster.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
    public static int tag = 0;//0：课程1：兴趣通卡
    public static View view;
    public static String cid;
	private static final String TAG = "WXPayEntryActivity";
	private IWXAPI api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		api = WXAPIFactory.createWXAPI(this, WXPayUtil.APP_ID);

		api.handleIntent(getIntent(), this);
//
//    	api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
//
//        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(final BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
//		Log.d(TAG,"onPayFinish, errCode = ");
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            String message = "";
            switch (resp.errCode){
                case 0:
                    message = "支付成功";
//                    gotoCourseDetail();
//                    view.setEnabled(true);
                    break;
                case -1:
                    message = "支付失败";
//                    view.setEnabled(true);
                    break;
                case -2:
                    message = "取消支付";
//                    view.setEnabled(true);
                    break;
            }

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage(message);
            builder.setCancelable(false);
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (resp.errCode == 0) {
                        if (tag == 0) {
//                            gotoCourseDetail();
                        } else {
                            dialog.dismiss();
                            finish();
//                            Intent cardIntent = new Intent(WXPayEntryActivity.this, CardBuyCourseActivity.class);
//                            cardIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(cardIntent);
                        }
                    } else {
                        finish();
                    }
                }
            });
			builder.show();
		}
	}
//    private void gotoCourseDetail(){
//        Intent it1 = new Intent(this, CoursesDetailActivity.class);
//        AppUtil.cardName=null;
//        it1.putExtra("cid", cid);
//        it1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(it1);
//        cid = null;
//    }
}
