/*
 * Yorick de Boer
 */

/*
 * Yorick de Boer
 */

package nl.yrck.comicsprout.dialogs;


import android.content.Context;
import android.support.v7.app.AlertDialog;

public class AboutDialog {
    public static void get(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("By Yorick de Boer")
                .setTitle("About")
                .setCancelable(true)
                .setNeutralButton("DISMISS", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
