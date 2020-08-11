package com.example.createrzone;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class AlertDialogManager {
    private static AlertDialogManager instance;
    TextView col2, col3, col4;
    Dialog alert = null;

    private AlertDialogManager() {

    }

    public static AlertDialogManager getInstance(Context cont) {
        synchronized (AlertDialogManager.class) {
            if (instance == null) {
                instance = new AlertDialogManager();
            }
        }
        return instance;
    }

    public Dialog showDialog(final Context context, String title) {
        if (!((Activity) context).isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title);
            builder.setCancelable(false);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View _view = inflater.inflate(R.layout.dialog_layout, null, false);
            builder.setView(_view);
            col2 = (TextView) _view.findViewById(R.id.col2);
            col3 = (TextView) _view.findViewById(R.id.col3);
            col4 = (TextView) _view.findViewById(R.id.col4);
            col2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context instanceof MainActivity) {
                        MainActivity activity = (MainActivity) context;
                        activity.changeColumn(2);
                        if (alert != null && alert.isShowing()) {
                            alert.dismiss();
                        }
                    }
                }
            });
            col3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context instanceof MainActivity) {
                        MainActivity activity = (MainActivity) context;
                        activity.changeColumn(3);
                        if (alert != null && alert.isShowing()) {
                            alert.dismiss();
                        }
                    }
                }
            });
            col4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context instanceof MainActivity) {
                        MainActivity activity = (MainActivity) context;
                        activity.changeColumn(4);
                        if (alert != null && alert.isShowing()) {
                            alert.dismiss();
                        }
                    }
                }
            });
            alert = builder.create();
            alert.setCanceledOnTouchOutside(false);
            alert.show();
        }
        return alert;
    }


}
