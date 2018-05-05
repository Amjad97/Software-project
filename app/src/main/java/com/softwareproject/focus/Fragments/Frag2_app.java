package com.softwareproject.focus.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.softwareproject.focus.Adapter.ListAppAdapter_app;
import com.softwareproject.focus.Database.Database;
import com.softwareproject.focus.Models.app;
import com.softwareproject.focus.R;

import java.util.List;

/**
 * Created by Amjad on 31/03/18.
 */

public class Frag2_app extends Fragment {

    public static ListAppAdapter_app adapter;
    Database db;
    RecyclerView list_app;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag2_layout,container,false);
        list_app = (RecyclerView) view.findViewById(R.id.list_app);
        ImageView no_app_sel = (ImageView) view.findViewById(R.id.no_app_sel);

        db = new Database(getContext());
        List<app> app = db.get_app();

        if (app.isEmpty()){
            list_app.setVisibility(view.INVISIBLE);
            no_app_sel.setVisibility(view.VISIBLE);
        }else {
            list_app.setVisibility(view.VISIBLE);
            no_app_sel.setVisibility(view.INVISIBLE);
        }

        list_app.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(getContext());
        list_app.setLayoutManager(layoutManager);
        adapter = new ListAppAdapter_app(getContext(), db.get_app());
        adapter.notifyDataSetChanged();
        list_app.setAdapter(adapter);
        return view;
    }
}