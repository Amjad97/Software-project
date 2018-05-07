package com.softwareproject.focus.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.softwareproject.focus.Activities.Profile_attributes;
import com.softwareproject.focus.Common.profile_apps;
import com.softwareproject.focus.Database.Database;
import com.softwareproject.focus.Models.app;
import com.softwareproject.focus.Notification.Utils;
import com.softwareproject.focus.R;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Amjad on 07/04/18.
 */

public class ListAppAdapter_select extends RecyclerView.Adapter<ListAppAdapter_select.ViewHolder_select>{

    public static class ViewHolder_select extends RecyclerView.ViewHolder{
        ImageView app_image;
        TextView app_name;
        Button app_delete;


       public ViewHolder_select(View itemView) {
           super(itemView);
           app_name = (TextView)itemView.findViewById(R.id.app_name_select);
           app_image = (ImageView)itemView.findViewById(R.id.app_image_select);
           app_delete = (Button) itemView.findViewById(R.id.app_delete);
       }
   }

    public Context context;
    private List<app> apps;
    private PackageManager pm;
    private SharedPreferences preferences;
    Database db;

    public ListAppAdapter_select(Context context, List<app> apps) {
        this.context = context;
        this.apps = apps;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        pm = context.getPackageManager();
        setHasStableIds(true);
    }

    @Override
    public ViewHolder_select onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.app_layout_select,parent,false);
        return new ViewHolder_select(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder_select holder, int position) {
        holder.app_name.setText(apps.get(position).getName());

        File internalStorage = context.getDir("app_icons", Context.MODE_PRIVATE);
        File reportFilePath = new File(internalStorage, holder.app_name.getText().toString() + ".png");
        String picturePath = reportFilePath.toString();
        Bitmap reportPicture = BitmapFactory.decodeFile(picturePath);

        holder.app_image.setImageBitmap(reportPicture);

        holder.app_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String pkg = ListAppAdapter_profile.pkg;
                HashSet<String> pkgs = new HashSet<>(Arrays.asList(preferences.getString(Utils.PREF_PACKAGES_BLOCKED, "").split(";")));
                pkgs.remove(pkg);
                preferences.edit().putString(Utils.PREF_PACKAGES_BLOCKED, TextUtils.join(";", pkgs)).apply();
                db = new Database(v.getContext());
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(v.getContext())
                        .setTitle("Delete")
                        .setMessage("It will be deleted ? ")
                        .setIcon(R.drawable.delete)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                db.del_app(holder.app_name.getText().toString(),Profile_attributes.id_);
                                profile_apps.profile_apps.remove(holder.app_name.getText().toString()+" "+
                                        Profile_attributes.id_);
                                Intent intent = new Intent(context,Profile_attributes.class);
                                intent.putExtra("position",Profile_attributes.position);
                                intent.putExtra("days",Profile_attributes.d);
                                intent.putExtra("name",Profile_attributes.profile_name);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                                Activity activity = (Activity) holder.itemView.getContext();
                                activity.finish();
                                Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create();
                myQuittingDialogBox.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public app getIem(int position) {
        return apps.get(position);
    }

}
