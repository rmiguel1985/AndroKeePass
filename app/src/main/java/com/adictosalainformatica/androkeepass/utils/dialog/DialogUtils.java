/*
 *      DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *                  Version 2, December 2004
 *
 *      Copyright (C) 2004 Sam Hocevar <sam@hocevar.net>
 *
 *      Everyone is permitted to copy and distribute verbatim or modified
 *      copies of this license document, and changing it is allowed as long
 *      as the name is changed.
 *
 *      DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 *      TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 *      0. You just DO WHAT THE FUCK YOU WANT TO.
 */

package com.adictosalainformatica.androkeepass.utils.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.text.InputType;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.adictosalainformatica.androkeepass.R;
import com.adictosalainformatica.androkeepass.features.loadfile.domain.model.Database;
import com.adictosalainformatica.androkeepass.features.loadfile.presentation.view.activity.LoadFileActivity;

/**
 * DialogUtils Class
 *
 * <p>Helper class to generate Dialogs</p>
 */
public class DialogUtils {

    private DialogUtils() {
    }

    /**
     * Show info dialog
     *
     * @param context
     * @param message
     */
    public static void createInfoDialog(Context context, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setPositiveButton(context.getString(R.string.ok),
                (dialog, which) -> dialog.dismiss());
        builder.setTitle("Info");
        builder.setMessage(message);

        AlertDialog errorDialog = builder.create();
        errorDialog.show();
    }

    /**
     * Show error dialog
     *
     * @param context Activity context
     * @param message Message to show
     */
    public static void createErrorDialog(Context context, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setPositiveButton(context.getString(R.string.ok),
                (dialog, which) -> dialog.dismiss());
        builder.setTitle("Error");
        builder.setMessage(message);

        AlertDialog errorDialog = builder.create();
        errorDialog.show();
    }

    /**
     * Dialog to delete database
     *
     * @param databaseName
     * @param loadFileActivity
     * @param context
     */
    public static void deleteDatabaseDialog(String databaseName, LoadFileActivity loadFileActivity,
                                            Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.loadfile_delete_database_dialog_title));
        builder.setMessage(context.getString(R.string.loadfile_delete_database_dialog_database, databaseName));

        builder.setPositiveButton(context.getString(R.string.ok), (dialog, which) -> {
            loadFileActivity.onDeleteDatabaseDialogClicked(databaseName);
        });
        builder.setNegativeButton(context.getString(R.string.cancel), (dialog, which) -> dialog.cancel());

        builder.show();
    }

    /**
     * Dialog to open database
     *
     * @param database
     * @param context
     * @param loadFileActivity
     */
    public static void openDatabaseDialog(Database database, Context context, LoadFileActivity loadFileActivity){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.loadfile_open_database_dialog_title));
        builder.setMessage(context.getString(R.string.loadfile_open_database_dialog_database));

        final EditText passwordInput = new EditText(context);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(45, 0, 45, 0);

        passwordInput.setHint(context.getString(R.string.password));
        passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        layout.addView(passwordInput, params);


        builder.setView(layout);
        builder.setPositiveButton(context.getString(R.string.ok), (dialog, which) -> {
            database.setPassword(passwordInput.getText().toString());

            loadFileActivity.onOpenDatabaseDialogClicked(database);
        });

        builder.setNegativeButton(context.getString(R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();
    }
}
