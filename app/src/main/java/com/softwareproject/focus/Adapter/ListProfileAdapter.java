package com.softwareproject.focus.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.softwareproject.focus.Activities.Profile_attributes;
import com.softwareproject.focus.Database.Database;
import com.softwareproject.focus.Interfaces.ItemClickListener;
import com.softwareproject.focus.Models.profile;
import com.softwareproject.focus.R;
import java.util.List;

/**
 * Created by Amjad on 04/04/18.
 */

class ListProfileViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView profile_name,days,times,profile_repeate;
    Switch profile_switch;
    ItemClickListener itemClickListener;

    public ListProfileViewHolder(View itemView) {
        super(itemView);
        profile_name = (TextView)itemView.findViewById(R.id.profile_name);
        profile_switch = (Switch)itemView.findViewById(R.id.profile_switch);
        days = (TextView)itemView.findViewById(R.id.days);
        times = (TextView)itemView.findViewById(R.id.times);
        profile_repeate = (TextView)itemView.findViewById(R.id.profile_repeat);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}

public class ListProfileAdapter extends RecyclerView.Adapter<ListProfileViewHolder>{

    private Context context;
    private List<profile> profiles;
    Database db;

    public ListProfileAdapter(Context context, List<profile> profiles) {
        this.context = context;
        this.profiles = profiles;
    }

    @Override
    public ListProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.profile_layout,parent,false);
        return new ListProfileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ListProfileViewHolder holder, final int position) {
        holder.profile_name.setText(profiles.get(position).getName());
        holder.days.setText(profiles.get(position).getDays());
        holder.times.setText(profiles.get(position).getTimes());
        holder.profile_repeate.setText(profiles.get(position).getRepeat());

        db = new Database(context);
        final List<profile> profiles = db.getAllProfile();
        for (int i=0;i<profiles.size();i++){
            if (profiles.get(i).getStatus().equals("Activate")){
                holder.profile_switch.setChecked(true);
            }else
                holder.profile_switch.setChecked(false);
        }

        holder.profile_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (holder.profile_switch.isChecked()){
                    db.update_status(holder.profile_name.getText().toString(),"Activate");
                    Toast.makeText(context,"Activate",Toast.LENGTH_LONG).show();
                }else {
                    db.update_status(holder.profile_name.getText().toString(),"Deactivate");
                    Toast.makeText(context,"Deactivate",Toast.LENGTH_LONG).show();
                }
            }
        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(context, Profile_attributes.class);
                intent.putExtra("position",position);
                intent.putExtra("days",profiles.get(position).getDays());
                intent.putExtra("name",holder.profile_name.getText().toString());
                context.startActivity(intent);
                Activity activity = (Activity)context;
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }
}