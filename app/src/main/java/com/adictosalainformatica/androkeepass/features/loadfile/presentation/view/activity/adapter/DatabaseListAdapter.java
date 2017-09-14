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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adictosalainformatica.androkeepass.R;
import com.adictosalainformatica.androkeepass.features.loadfile.domain.model.Database;

import java.lang.ref.WeakReference;
import java.util.List;


public class DatabaseListAdapter  extends RecyclerView.Adapter<DatabaseListItemViewHolder> {

    private List<Database> databases;
    private WeakReference<OnDatabaseListItemClickedListener> clickedListenerRef;
    private WeakReference<OnDatabaseListItemLongClickedListener> longClickedListenerRef;

    public DatabaseListAdapter() {

    }

    public void setOnDatabaseListItemClickedListener(OnDatabaseListItemClickedListener listener) {
        clickedListenerRef = new WeakReference<>(listener);
    }

    public void setOnDatabaseListItemLongClickedListener(OnDatabaseListItemLongClickedListener listener) {
        longClickedListenerRef = new WeakReference<>(listener);
    }

    public void setDatabases(List<Database> databases) {
        this.databases = databases;
        notifyDataSetChanged();
    }

    @Override
    public DatabaseListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.loadfile_activity_databse_item, parent, false);

        final DatabaseListItemViewHolder holder = new DatabaseListItemViewHolder(itemView);


        itemView.setOnLongClickListener(view ->{
            if (longClickedListenerRef != null) {
                OnDatabaseListItemLongClickedListener listener = longClickedListenerRef.get();
                if (listener != null) {
                    listener.onDatabaseListItemLongClickedListener(databases.get(holder.getAdapterPosition()));
                }
            }
            return true;
        });

        itemView.setOnClickListener(view -> {
            if (clickedListenerRef != null) {
                OnDatabaseListItemClickedListener listener = clickedListenerRef.get();
                if (listener != null) {
                    listener.onDatabaseListItemClicked(databases.get(holder.getAdapterPosition()));
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(DatabaseListItemViewHolder holder, int position) {
        holder.decorate(databases.get(position).getDatabaseName());
    }

    @Override
    public int getItemCount() {
        return databases != null ? databases.size() : 0;
    }

    public interface OnDatabaseListItemClickedListener {
        void onDatabaseListItemClicked(Database database);
    }

    public interface OnDatabaseListItemLongClickedListener {
        void onDatabaseListItemLongClickedListener(Database database);
    }
}
