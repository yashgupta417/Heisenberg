package com.heisenberg;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;

import pl.droidsonroids.gif.GifImageView;

public class LoadingDialog {
    Activity activity;
    Dialog dialog;
    //..we need the context else we can not create the dialog so get context in constructor
    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    public void showDialog() {

        dialog  = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //...set cancelable false so that it's never get hidden
        dialog.setCancelable(false);
        //...that's the layout i told you will inflate later
        dialog.setContentView(R.layout.loading_dialog_layout);
        GifImageView gifImageView = dialog.findViewById(R.id.loading);
        dialog.show();
    }

    public void hideDialog(){
        dialog.dismiss();
    }
}
