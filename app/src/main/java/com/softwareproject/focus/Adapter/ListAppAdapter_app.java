package com.softwareproject.focus.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.softwareproject.focus.Activities.Profile_attributes;
import com.softwareproject.focus.Common.Get_apps;
import com.softwareproject.focus.Database.database;
import com.softwareproject.focus.Fragments.Frag2_app;
import com.softwareproject.focus.Models.app;
import com.softwareproject.focus.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Amjad on 08/04/18.
 */


public class ListAppAdapter_app extends RecyclerView.Adapter<ListAppAdapter_app.ViewHolder_app> {

    public static class ViewHolder_app extends RecyclerView.ViewHolder {
        ImageView app_image;
        TextView app_name;
        Switch app_switch;
        Button delete;

    public ViewHolder_app(View itemView) {
        super(itemView);
        app_name = (TextView)itemView.findViewById(R.id.app_name);
        app_image = (ImageView)itemView.findViewById(R.id.app_image);
        app_switch = (Switch) itemView.findViewById(R.id.app_switch);
        delete = (Button)itemView.findViewById(R.id.delete_app);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app_switch.performClick();
            }
        });
    }
}

    public Context context;
    private List<app> apps;
    private PackageManager pm;
    private database db;


    public ListAppAdapter_app(Context context, List<app> apps) {
        this.context = context;
        this.apps = apps;
        this.pm = context.getPackageManager();
        setHasStableIds(true);
    }

    @Override
    public ViewHolder_app onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.app_layout,parent,false);
        return new ViewHolder_app(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder_app holder, int position) {
        holder.app_name.setText(apps.get(position).getName());
        /*
        Get_apps app = new Get_apps(context);
        for (int i = 0;i<app.get_apps().size();i++){
            if (app.get_apps().get(i).loadLabel(pm).equals(holder.app_name.getText().toString())){
                holder.app_image.setImageDrawable(app.get_apps().get(i).loadIcon(pm));
            }
        }
*/
        db=new database(context);

        List<app> apps = db.get_app();
        for (int i=0;i<apps.size();i++){
            if (apps.get(i).getaSwitch().equals("Activate")){
                holder.app_switch.setChecked(true);
            }else
                holder.app_switch.setChecked(false);
        }

        holder.app_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (holder.app_switch.isChecked()){
                    db.update_app_status(holder.app_name.getText().toString(),"Activate",0);
                    Toast.makeText(context,"Activate",Toast.LENGTH_SHORT).show();
                }else {
                    db.update_app_status(holder.app_name.getText().toString(),"Deactivate",0);
                    Toast.makeText(context,"Deactivate",Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                db = new database(v.getContext());
                AlertDialog myQuittingDialogBox = new AlertDialog.Builder(v.getContext())
                        .setTitle("Delete")
                        .setMessage("Do you want to delete app ? ")
                        .setIcon(R.drawable.delete)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                db.del_app(holder.app_name.getText().toString(),0);
                                Toast.makeText(v.getContext(), "deleted", Toast.LENGTH_SHORT).show();
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
    public long getItemId(int position)
    {
        return  position;
    }

    public app getIem(int position) {
        return apps.get(position);
    }
}