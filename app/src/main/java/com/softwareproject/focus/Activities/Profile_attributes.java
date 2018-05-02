package com.softwareproject.focus.Activities;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.softwareproject.focus.Adapter.ListAppAdapter_profile;
import com.softwareproject.focus.Adapter.ListAppAdapter_select;
import com.softwareproject.focus.Common.Get_apps;
import com.softwareproject.focus.Database.database;
import com.softwareproject.focus.Models.app;
import com.softwareproject.focus.Models.profile;
import com.softwareproject.focus.R;

public class Profile_attributes extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static String profile_name;
    public static String d;
    String FromTime,ToTime;
    TextView sun,mon,tue,wed,thu,fri,sat,Times;
    CheckBox repeat;
    RecyclerView list_select_app;
    public static int position;
    public static int id_ ;
    database db;
    String repeat_value;
    StringBuilder days;
    ArrayList<profile> list_profiles;
    ListAppAdapter_select adapter_;
    ListAppAdapter_profile adapter;
    SharedPreferences mPreferences;
    List<ApplicationInfo> applications;
    PackageManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_attributes);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        db = new database(this);

        Get_apps getApps = new Get_apps(this);
        applications = getApps.get_apps();
        manager = getPackageManager();

        sun = (TextView)findViewById(R.id.Sun);
        mon = (TextView)findViewById(R.id.Mon);
        tue = (TextView)findViewById(R.id.Tue);
        wed = (TextView)findViewById(R.id.Wed);
        thu = (TextView)findViewById(R.id.The);
        fri = (TextView)findViewById(R.id.Fri);
        sat = (TextView)findViewById(R.id.Sat);
        Times = (TextView)findViewById(R.id.profile_time);
        repeat = (CheckBox)findViewById(R.id.repeat);
        list_select_app = (RecyclerView) findViewById(R.id.selected_list);

        list_profiles = db.getAllProfile();

        Intent data = getIntent();
        profile_name = data.getExtras().getString("name");
        position = data.getIntExtra("position",100);
        d = data.getExtras().getString("days");
        if (d.contains("Sun")){
            sun.setBackgroundResource(R.drawable.day_layout_select);
            sun.setTextColor(getResources().getColor(R.color.cardview_light_background));
        }else {
            sun.setBackgroundResource(R.drawable.app_layout_us_select);
            sun.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        if (d.contains("Sun")){
            sun.setBackgroundResource(R.drawable.day_layout_select);
            sun.setTextColor(getResources().getColor(R.color.cardview_light_background));
        }else {
            sun.setBackgroundResource(R.drawable.app_layout_us_select);
            sun.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        if (d.contains("Mon")){
            mon.setBackgroundResource(R.drawable.day_layout_select);
            mon.setTextColor(getResources().getColor(R.color.cardview_light_background));
        }else {
            mon.setBackgroundResource(R.drawable.app_layout_us_select);
            mon.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        if (d.contains("Tue")){
            tue.setBackgroundResource(R.drawable.day_layout_select);
            tue.setTextColor(getResources().getColor(R.color.cardview_light_background));
        }else {
            tue.setBackgroundResource(R.drawable.app_layout_us_select);
            tue.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        if (d.contains("Wed")){
            wed.setBackgroundResource(R.drawable.day_layout_select);
            wed.setTextColor(getResources().getColor(R.color.cardview_light_background));
        }else {
            wed.setBackgroundResource(R.drawable.app_layout_us_select);
            wed.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        if (d.contains("Thu")){
            thu.setBackgroundResource(R.drawable.day_layout_select);
            thu.setTextColor(getResources().getColor(R.color.cardview_light_background));
        }else {
            thu.setBackgroundResource(R.drawable.app_layout_us_select);
            thu.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        if (d.contains("Fri")){
            fri.setBackgroundResource(R.drawable.day_layout_select);
            fri.setTextColor(getResources().getColor(R.color.cardview_light_background));
        }else {
            fri.setBackgroundResource(R.drawable.app_layout_us_select);
            fri.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        if (d.contains("Sat")){
            sat.setBackgroundResource(R.drawable.day_layout_select);
            sat.setTextColor(getResources().getColor(R.color.cardview_light_background));
        }else {
            sat.setBackgroundResource(R.drawable.app_layout_us_select);
            sat.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        List<profile> profiles = db.getAllProfile();
        for (profile p :profiles) {
            if (p.getName().equals(profile_name)) {
                id_ = p.getId();
                repeat_value = p.getRepeat();
                Times.setText(p.getTimes());
            }
        }

        if (repeat_value.equals("ON"))
            repeat.setChecked(true);
        else
            repeat.setChecked(false);

        repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (repeat.isChecked())
                    repeat_value="ON";
                else
                    repeat_value = "OFF";
            }
        });


        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sun.getTextColors().getDefaultColor() == Color.WHITE){
                    sun.setBackgroundResource(R.drawable.app_layout_us_select);
                    sun.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }else {
                    sun.setBackgroundResource(R.drawable.day_layout_select);
                    sun.setTextColor(getResources().getColor(R.color.cardview_light_background));
                }
            }
        });

        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mon.getTextColors().getDefaultColor() == Color.WHITE){
                    mon.setBackgroundResource(R.drawable.app_layout_us_select);
                    mon.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }else {
                    mon.setBackgroundResource(R.drawable.day_layout_select);
                    mon.setTextColor(getResources().getColor(R.color.cardview_light_background));
                }
            }
        });

        tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tue.getTextColors().getDefaultColor() == Color.WHITE){
                    tue.setBackgroundResource(R.drawable.app_layout_us_select);
                    tue.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }else {
                    tue.setBackgroundResource(R.drawable.day_layout_select);
                    tue.setTextColor(getResources().getColor(R.color.cardview_light_background));
                }
            }
        });

        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wed.getTextColors().getDefaultColor() == Color.WHITE){
                    wed.setBackgroundResource(R.drawable.app_layout_us_select);
                    wed.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }else {
                    wed.setBackgroundResource(R.drawable.day_layout_select);
                    wed.setTextColor(getResources().getColor(R.color.cardview_light_background));
                }
            }
        });

        thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thu.getTextColors().getDefaultColor() == Color.WHITE){
                    thu.setBackgroundResource(R.drawable.app_layout_us_select);
                    thu.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }else {
                    thu.setBackgroundResource(R.drawable.day_layout_select);
                    thu.setTextColor(getResources().getColor(R.color.cardview_light_background));
                }
            }
        });

        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fri.getTextColors().getDefaultColor() == Color.WHITE){
                    fri.setBackgroundResource(R.drawable.app_layout_us_select);
                    fri.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }else {
                    fri.setBackgroundResource(R.drawable.day_layout_select);
                    fri.setTextColor(getResources().getColor(R.color.cardview_light_background));
                }
            }
        });

        sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sat.getTextColors().getDefaultColor() == Color.WHITE){
                    sat.setBackgroundResource(R.drawable.app_layout_us_select);
                    sat.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }else {
                    sat.setBackgroundResource(R.drawable.day_layout_select);
                    sat.setTextColor(getResources().getColor(R.color.cardview_light_background));
                }
            }
        });

        ArrayList<app> select_app = new ArrayList<>();

        for (int i =0;i<db.getAllApp().size();i++){
            if (db.getAllApp().get(i).getProfile_id() == id_)
                select_app.add(db.getAllApp().get(i));
        }

        adapter_ = new ListAppAdapter_select(getApplicationContext(),select_app);
        list_select_app.setLayoutManager(new LinearLayoutManager(this));
        list_select_app.setHasFixedSize(true);
        adapter_.notifyDataSetChanged();
        list_select_app.setAdapter(adapter_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        setTitle(profile_name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.done_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.done) {
            days = new StringBuilder();
            if (sun.getTextColors().getDefaultColor() == Color.WHITE){
                days.append("Sun,");
            }
            if (mon.getTextColors().getDefaultColor() == Color.WHITE){
                days.append("Mon,");
            }
            if (tue.getTextColors().getDefaultColor() == Color.WHITE){
                days.append("Tue,");
            }
            if (wed.getTextColors().getDefaultColor() == Color.WHITE){
                days.append("Wed,");
            }
            if (thu.getTextColors().getDefaultColor() == Color.WHITE){
                days.append("Thu,");
            }
            if (fri.getTextColors().getDefaultColor() == Color.WHITE){
                days.append("Fri,");
            }
            if (sat.getTextColors().getDefaultColor() == Color.WHITE){
                days.append("Sat");
            }

            if (days.subSequence(days.length()-1,days.length()).equals(",")){
                days.replace(days.length()-1,days.length(),"");
            }

            db.update_profile(id_,profile_name,days.toString(),Times.getText().toString(),repeat_value);
            Toast.makeText(getApplicationContext(),"Edited",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Profile_attributes.this,MainActivity.class);
            startActivity(intent);
            finish();
        }else if (id == R.id.rename){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            final View view1 = inflater.inflate(R.layout.create_profile,null);
            final EditText profile_name_ = (EditText) view1.findViewById(R.id.profile_dialog_name);
            profile_name_.setText(profile_name);
            alert.setTitle("Create a new profile");

            alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db.update_name(profile_name_.getText().toString(),id_);
                    profile_name=profile_name_.getText().toString();
                    setTitle(profile_name_.getText().toString());
                }
            });
            alert.setView(view1);
            AlertDialog alertDialog=alert.create();
            alertDialog.show();
        }else if (id == R.id.delete){
            AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                    .setTitle("Delete")
                    .setMessage("Do you` want to Delete profile ? ")
                    .setIcon(R.drawable.delete)
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            db.del_profile(id_);
                            Intent intent = new Intent(Profile_attributes.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .create();
            myQuittingDialogBox.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void select(View view) {

        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.activity_block_one_app,null);
        RecyclerView list = (RecyclerView) view1.findViewById(R.id.block_one_app_list);
        SearchView searchView = (SearchView) view1.findViewById(R.id.search_);

        alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Profile_attributes.this,Profile_attributes.class);
                intent.putExtra("position",position);
                intent.putExtra("days",d);
                intent.putExtra("name",profile_name);
                startActivity(intent);
                finish();
            }
        });

        if (searchView != null) {
            searchView.setOnQueryTextListener(Profile_attributes.this);
        }

        mPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());


        if (list != null) {
            list.setHasFixedSize(true);
            list.setLayoutManager(new LinearLayoutManager(view.getContext()));
        }

        adapter = new ListAppAdapter_profile(view.getContext(),applications);
        list.setAdapter(adapter);
        alert.setView(view1);
        AlertDialog alertDialog=alert.create();
        alertDialog.show();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (TextUtils.isEmpty(s)) {
            adapter.setApps(applications);

        } else {
            String query = s.toLowerCase();
            ArrayList<ApplicationInfo> filtered = new ArrayList<>();
            for (ApplicationInfo app : applications) {
                String label = app.loadLabel(manager).toString().toLowerCase();
                if (label.contains(query)) {
                    filtered.add(app);
                }
            }
            adapter.setApps(filtered);
        }
        return true;
    }

    public void add_time(View view) {

        AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.time_picker_dialog,null);
        final Button from = (Button)view1.findViewById(R.id.From);
        final Button to = (Button)view1.findViewById(R.id.To);
        final TimePicker from_time = (TimePicker)view1.findViewById(R.id.from_time);
        final TimePicker to_time = (TimePicker)view1.findViewById(R.id.to_time);
        Button ok = (Button)view1.findViewById(R.id.ok);
        Button cancel = (Button)view1.findViewById(R.id.cancel);
        alert.setView(view1);
        final AlertDialog alertDialog=alert.create();
        alertDialog.show();

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                from.setTextColor(getResources().getColor(R.color.cardview_light_background));
                from.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                to.setTextColor(getResources().getColor(R.color.colorPrimary));
                to.setBackgroundColor(getResources().getColor(R.color.cardview_light_background));
                from_time.setVisibility(View.VISIBLE);
                to_time.setVisibility(View.INVISIBLE);
            }
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to.setTextColor(getResources().getColor(R.color.cardview_light_background));
                to.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                from.setTextColor(getResources().getColor(R.color.colorPrimary));
                from.setBackgroundColor(getResources().getColor(R.color.cardview_light_background));
                from_time.setVisibility(View.INVISIBLE);
                to_time.setVisibility(View.VISIBLE);
            }
        });

        from_time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                FromTime = hourOfDay+":"+minute;
            }
        });

        to_time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                ToTime = hourOfDay+":"+minute;
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat df = new SimpleDateFormat("HH:mm");
                String times = df.format(Calendar.getInstance().getTime());

                if (FromTime != null && ToTime != null)
                    Times.setText(FromTime+" - "+ToTime);
                else if (FromTime == null && ToTime != null) {
                    int from_time_ = Times.getText().toString().indexOf(" ");
                    Times.setText(Times.getText().subSequence(0,from_time_) + " - " + ToTime);
                }
                else if (FromTime != null && ToTime == null) {
                    int to_time_ = Times.getText().toString().lastIndexOf(" ");
                    Times.setText(FromTime + " - " + Times.getText().subSequence(to_time_,Times.getText().length()));
                }
                else if (FromTime == null && ToTime == null)
                    Times.setText(times+" - "+times);
                alertDialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                .setTitle("Back")
                .setMessage("Are you sure you want to ignore your editing ? ")
                .setIcon(R.drawable.back)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = new Intent(Profile_attributes.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .create();
        myQuittingDialogBox.show();
    }
}