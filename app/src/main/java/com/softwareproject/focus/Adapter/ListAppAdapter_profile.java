package com.softwareproject.focus.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.softwareproject.focus.Activities.Profile_attributes;
import com.softwareproject.focus.Database.database;
import com.softwareproject.focus.Notification.Utils;
import com.softwareproject.focus.R;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Amjad on 07/04/18.
 */

public class ListAppAdapter_profile extends RecyclerView.Adapter<ListAppAdapter_profile.ViewHolder_profile>{


    public static class ViewHolder_profile extends RecyclerView.ViewHolder{
        TextView app_name;
        ImageView app_image;
        CheckBox app_check;
        database db;

        public ViewHolder_profile(final View itemView) {
            super(itemView);
            app_name = (TextView)itemView.findViewById(R.id.app_name_profile);
            app_image = (ImageView)itemView.findViewById(R.id.app_image_profile);
            app_check = (CheckBox) itemView.findViewById(R.id.app_check);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    app_check.performClick();
                }
            });

            app_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    db = new database(itemView.getContext());
                    if (app_check.isChecked()){
                        db.Insert_app(app_name.getText().toString(),"Active",Profile_attributes.id_);
                    }
                }
            });

    }
}

    private List<ApplicationInfo> apps;
    private PackageManager pm;
    private SharedPreferences preferences;
    private LayoutInflater layoutInflater;

    public ListAppAdapter_profile(Context context,List<ApplicationInfo> apps) {
        this.apps = apps;
        pm = context.getPackageManager();
        layoutInflater = LayoutInflater.from(context);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        setHasStableIds(true);
    }

    @Override
    public ViewHolder_profile onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.app_layout_profile,parent,false);
        return new ViewHolder_profile(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder_profile holder, int position) {
        ApplicationInfo app = apps.get(position);
        HashSet<String> blocked = new HashSet<>(Arrays.asList(preferences.getString(Utils.PREF_PACKAGES_BLOCKED,
                "").split(";")));
        holder.app_image.setImageDrawable(app.loadIcon(pm));
        holder.app_name.setText(app.loadLabel(pm));
        holder.app_check.setChecked(blocked.contains(app.packageName));
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    @Override
    public long getItemId(int position) {
        return (long) apps.get(position).packageName.hashCode();
    }

    public ApplicationInfo getIem(int position) {
        return apps.get(position);
    }

    public void setApps(List<ApplicationInfo> apps) {
        this.apps = apps;
        notifyDataSetChanged();
    }
}
