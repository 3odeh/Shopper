package com.example.shoper.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;

import com.example.shoper.R;

public class ProgressDialog {
    private Context context;
    private Dialog dialog;

    public ProgressDialog (Context context, String title){
        this.context = context;
        dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_progress);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tv = dialog.findViewById(R.id.dialog_progress_tv_title);
        tv.setText(title);
        dialog.create();
    }
    public void showProgress(){
        dialog.show();
    }
    public void hideProgress(){
        dialog.dismiss();
    }
}

