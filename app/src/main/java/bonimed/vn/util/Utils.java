package bonimed.vn.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by acv on 10/26/17.
 */

public class Utils {

    public static void hideKeyboard(Context context,View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String convertToCurrencyStr(int number) {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US);
        formatter.setMaximumFractionDigits(0);
        return formatter.format(number) + " đ";
    }

}
