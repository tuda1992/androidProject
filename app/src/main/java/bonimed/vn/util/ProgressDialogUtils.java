package bonimed.vn.util;

import android.app.ProgressDialog;
import android.content.Context;

import bonimed.vn.R;

/**
 * Created by mac on 11/4/17.
 */

public class ProgressDialogUtils {

    private ProgressDialog mProgressDialog;
    private Context mContext;

    public ProgressDialogUtils(Context context){
        this.mContext = context;
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setCancelable(false);
    }

    public void showDialog() {
        mProgressDialog.show();
        mProgressDialog.setContentView(R.layout.layout_my_progress);
    }

    public void hideDialog(){
        mProgressDialog.dismiss();
    }

}
