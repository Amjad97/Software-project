package com.softwareproject.focus.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.softwareproject.focus.Activities.MainActivity;
import com.softwareproject.focus.Activities.Profile_attributes;
import com.softwareproject.focus.Common.Get_apps;
import com.softwareproject.focus.Database.database;
import com.softwareproject.focus.Models.app;
import com.softwareproject.focus.R;
import java.util.List;

import static com.softwareproject.focus.Activities.Profile_attributes.id_;

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
    database db;

    public ListAppAdapter_select(Context context, List<app> apps) {
        this.context = context;
        this.apps = apps;
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
        /*
        Get_apps apps = new Get_apps(context);
        for (int i = 0;i<apps.get_apps().size();i++){
            if (apps.get_apps().get(i).loadLabel(pm).equals(holder.app_name.getText().toString())){
                holder.app_image.setImageDrawable(apps.get_apps().get(i).loadIcon(pm));
            }
        }
*/
        holder.app_delete.setOnClickListener(new View.OnClickListener() {
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
                                db.del_app(holder.app_name.getText().toString(),Profile_attributes.id_);
                                Toast.makeText(v.getContext(), "deleted", Toast.LENGTH_LONG).show();
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
