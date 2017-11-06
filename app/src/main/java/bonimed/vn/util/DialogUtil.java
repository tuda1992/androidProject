package bonimed.vn.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

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


}
