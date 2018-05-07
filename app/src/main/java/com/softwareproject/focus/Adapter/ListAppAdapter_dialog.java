package com.softwareproject.focus.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.softwareproject.focus.Activities.MainActivity;
import com.softwareproject.focus.Database.Database;
import com.softwareproject.focus.Models.app;
import com.softwareproject.focus.Notification.Utils;
import com.softwareproject.focus.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Amjad on 02/04/18.
 */



public class ListAppAdapter_dialog extends RecyclerView.Adapter<ListAppAdapter_dialog.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder{
    TextView app_name;
    ImageView app_image;
    CheckBox app_check;
    Database db;

    public ViewHolder(final View itemView) {
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

    }
}
    private Context context;
    private LayoutInflater layoutInflater;
    private List<ApplicationInfo> apps;
    private PackageManager pm;
    private SharedPreferences preferences;
    public static String pkg;
    Database db;

    public ListAppAdapter_dialog( Context context,List<ApplicationInfo> apps) {
        this.apps = apps;
        this.context=context;
        pm = context.getPackageManager();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        layoutInflater = LayoutInflater.from(context);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.app_layout_profile,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        db = new Database(context);

        ApplicationInfo app = apps.get(position);
        holder.app_image.setImageDrawable(app.loadIcon(pm));
        holder.app_name.setText(app.loadLabel(pm));

        holder.app_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                pkg = getIem(position).packageName;
                HashSet<String> pkgs = new HashSet<>(Arrays.asList(preferences.getString(Utils.PREF_PACKAGES_BLOCKED, "").split(";")));

                    if (holder.app_check.isChecked()) {
                        pkgs.add(pkg);
                        Drawable drawable = holder.app_image.getDrawable();
                        BitmapDrawable B_D = (BitmapDrawable)drawable;
                        Bitmap icon = B_D.getBitmap();

                        File internalStorage = context.getDir("app_icons", Context.MODE_PRIVATE);
                        File reportFilePath = new File(internalStorage, holder.app_name.getText().toString() + ".png");
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(reportFilePath);
                            icon.compress(Bitmap.CompressFormat.PNG, 100 /*quality*/, fos);
                            fos.close();
                        }
                        catch (Exception ex) {
                            // Log.i("DATABASE", "Problem updating picture", ex);
                        }

                        List<app> apps = db.get_app();
                        List<String> apps_name = new ArrayList<>();
                        for (app p :apps){
                            apps_name.add(p.getName());
                        }
                        if (!apps_name.contains(holder.app_name.getText().toString())){
                            db.Insert_app(holder.app_name.getText().toString(),"Activate",0);
                            Intent intent = new Intent(context,MainActivity.class);
                            context.startActivity(intent);
                            Activity activity =(Activity)context;
                            activity.finish();
                        }else {
                            Toast.makeText(context,"It's already exist",Toast.LENGTH_SHORT).show();
                            MainActivity.alertDialog.dismiss();
                    }
                }
                    preferences.edit().putString(Utils.PREF_PACKAGES_BLOCKED, TextUtils.join(";", pkgs)).apply();
            }
        });
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