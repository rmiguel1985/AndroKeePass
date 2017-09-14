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

package com.adictosalainformatica.androkeepass.features.loadfile.presentation.view.activity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.adictosalainformatica.androkeepass.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DatabaseListItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.loadfile_textview_database_list_item)
    TextView databaseName;

    public DatabaseListItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void decorate(String database) {
        databaseName.setText(database);
    }
}
