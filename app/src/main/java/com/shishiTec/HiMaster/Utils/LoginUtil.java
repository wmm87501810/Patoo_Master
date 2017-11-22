package com.shishiTec.HiMaster.Utils;

import android.app.Activity;
import android.content.Intent;

import com.shishiTec.HiMaster.UI.Activity.Accouont.RegisterActivity;
import com.shishiTec.HiMaster.UI.Views.LoginDialogView;

/**
 * Created by hyu_anzhuo on 2016/6/6.
 */
public class LoginUtil {
    private LoginDialogView confirmDialog;
    private static LoginUtil loginutils = null;

    public static LoginUtil getInstance() {
        if (loginutils == null) {
            loginutils = new LoginUtil();
        }
        return loginutils;
    }

    public void login(final Activity activity) {
        if (confirmDialog == null) {
            confirmDialog = new LoginDialogView(activity);
        }
        confirmDialog.show();
        confirmDialog.setClicklistener(new LoginDialogView.ClickListenerInterface() {
            @Override
            public void doLogin() {
                activity.startActivity(new Intent(activity, RegisterActivity.class));
                confirmDialog.dismiss();
            }

            @Override
            public void doCancel() {
                confirmDialog.dismiss();
            }
        });
    }
}
