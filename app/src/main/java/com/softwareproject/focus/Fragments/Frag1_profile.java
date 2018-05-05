package com.softwareproject.focus.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.softwareproject.focus.Adapter.ListProfileAdapter;
import com.softwareproject.focus.Database.Database;
import com.softwareproject.focus.Models.profile;
import com.softwareproject.focus.R;

import java.util.List;

/**
 * Created by Amjad on 31/03/18.
 */

public class Frag1_profile extends Fragment {

    RecyclerView profile_list;
    RecyclerView.LayoutManager layoutManager;
    Database db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag1_layout,container,false);

        db = new Database(getContext());
        profile_list = (RecyclerView) view.findViewById(R.id.list_profile);
        ImageView no_profile = (ImageView) view.findViewById(R.id.no_profile);
        List<profile> profiles = db.getAllProfile();

        if (!profiles.isEmpty()){
            profile_list.setVisibility(view.VISIBLE);
            no_profile.setVisibility(view.INVISIBLE);
        }else {
            profile_list.setVisibility(view.INVISIBLE);
            no_profile.setVisibility(view.VISIBLE);
        }

        ListProfileAdapter adapter = new ListProfileAdapter(getContext(),profiles);
        profile_list.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        profile_list.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
        profile_list.setAdapter(adapter);

        return view;
    }
}
