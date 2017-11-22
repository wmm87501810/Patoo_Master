package com.shishiTec.HiMaster.UI.Views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.shishiTec.HiMaster.R;

/**
 * Created by hyu_anzhuo on 2016/6/6.
 */
public class LoginDialogView extends Dialog {
    private Context context;
    private ClickListenerInterface clickListenerInterface;
    private TextView not_login, login;

    public LoginDialogView(Context context) {
        super(context, R.style.LoginDialog);
        this.context = context;
    }

    public interface ClickListenerInterface {
        void doLogin();

        void doCancel();
    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            int id = v.getId();
            switch (id) {
                case R.id.not_login:
                    clickListenerInterface.doCancel();
                    break;
                case R.id.login:
                    clickListenerInterface.doLogin();
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_login_view, null);
        setContentView(view);
        not_login = (TextView) findViewById(R.id.not_login);
        login = (TextView) findViewById(R.id.login);
        not_login.setOnClickListener(new clickListener());
        login.setOnClickListener(new clickListener());
        init();
    }

    private void init() {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); //高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }
}
