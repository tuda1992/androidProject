package bonimed.vn.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import bonimed.vn.R;
import bonimed.vn.listener.DialogOneButtonCallBackListener;
import bonimed.vn.listener.DialogTwoButtonCallBackListener;

/**
 * Created by acv on 11/1/17.
 */

public class DialogUtil {

    public static void showAlertDialogButtonClicked(Context context, String title, String message, String textPositive, String textNegative, final DialogTwoButtonCallBackListener listener) {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        // add the buttons
        builder.setPositiveButton(textPositive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (listener != null) {
                    listener.onPositiveButtonClick();
                }
            }
        });
        builder.setNegativeButton(textNegative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (listener != null) {
                    listener.onNegativeButtonClick();
                }
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showAlertDialogOneButtonClicked(Context context, String title, String message, String textPositive, final DialogOneButtonCallBackListener listener) {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);

        // add the buttons
        builder.setPositiveButton(textPositive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (listener != null) {
                    listener.onPositiveButtonClick();
                }
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showAlertDialogOrderSuccessOneButtonClicked(Activity activity, final DialogOneButtonCallBackListener listener) {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_alert_dialog_order_success, null);
        builder.setView(dialogView);

        Calendar rightNow = Calendar.getInstance();
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);

        Log.d("TUDA", "currentHour = " + currentHour);

        TextView tvOrderSuccess1 = (TextView) dialogView.findViewById(R.id.tv_order_success_1);
        Button btnOk = (Button) dialogView.findViewById(R.id.btn_ok);
        String strTime = "";
        if (currentHour >= 14 && currentHour < 18) {
            strTime = "lúc\n10h - 13h30 ngày mai";
        } else if (currentHour >= 18 && currentHour < 10) {
            strTime = "sau 14h";
        } else if (currentHour >= 10 && currentHour < 12) {
            strTime = "sau 15h";
        } else {
            strTime = "sau 18h30";
        }

        tvOrderSuccess1.setText(activity.getResources().getString(R.string.order_success_1) + " " + strTime);

        // create and show the alert dialog
        final AlertDialog dialog = builder.create();
        dialog.show();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onPositiveButtonClick();
                }
            }
        });
    }


}
