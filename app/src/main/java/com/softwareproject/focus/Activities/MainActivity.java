package com.softwareproject.focus.Activities;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import com.softwareproject.focus.Adapter.ListAppAdapter_dialog;
import com.softwareproject.focus.Common.Get_apps;
import com.softwareproject.focus.Database.database;
import com.softwareproject.focus.Fragments.Frag1_profile;
import com.softwareproject.focus.Fragments.Frag2_app;
import com.softwareproject.focus.Notification.Utils;
import com.softwareproject.focus.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private SharedPreferences mPreferences;
    private ImageView empty;
    private FloatingActionButton fab;
    Fragment fragment;
    ListAppAdapter_dialog adapter_;
    public static AlertDialog alertDialog;
    private PackageManager manager;
    private List<ApplicationInfo> applications;
    public static AlertDialog.Builder alert;
    database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new database(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                int i = tabLayout.getSelectedTabPosition();
                if (i == 0){
                    AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                    LayoutInflater inflater = getLayoutInflater();
                    final View view1 = inflater.inflate(R.layout.create_profile,null);
                    final EditText profile_name_ = (EditText) view1.findViewById(R.id.profile_dialog_name);

                    alert.setTitle("Create a new profile");

                    alert.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alert.setPositiveButton("create", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if (!profile_name_.getText().toString().equals("")){

                                DateFormat df = new SimpleDateFormat("HH:mm");
                                Calendar from = Calendar.getInstance();
                                String from_time = df.format(from.getTime());
                                Calendar to = Calendar.getInstance();
                                to.add(Calendar.HOUR,1);
                                String to_time = df.format(to.getTime());

                                db.Insert_profile(profile_name_.getText().toString(),"Activate",
                                        "Sun,Mon,Tue,Wed,Thu,Fri,Sat",from_time+" - "+to_time,"OFF");
                                Intent intent = new Intent(view1.getContext(),Profile_attributes.class);
                                intent.putExtra("days","Sun,Mon,Tue,Wed,Thu,Fri,Sat");
                                intent.putExtra("name",profile_name_.getText().toString());
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                    alert.setView(view1);
                    AlertDialog alertDialog=alert.create();
                    alertDialog.show();

                }else if (i == 1){
                    alert = new AlertDialog.Builder(view.getContext());
                    LayoutInflater inflater = getLayoutInflater();
                    View view1 = inflater.inflate(R.layout.activity_block_one_app,null);
                    RecyclerView list = (RecyclerView) view1.findViewById(R.id.block_one_app_list);
                    SearchView searchView = (SearchView) view1.findViewById(R.id.search_);

                    if (searchView != null) {
                        searchView.setOnQueryTextListener(MainActivity.this);
                    }

                    manager = view.getContext().getPackageManager();
                    Get_apps getApps = new Get_apps(getApplicationContext());
                    applications = getApps.get_apps();

                    mPreferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());

                    if (list != null) {
                        list.setHasFixedSize(true);
                        list.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    }

                    adapter_ = new ListAppAdapter_dialog(view.getContext(),applications);
                    list.setAdapter(adapter_);
                    alert.setView(view1);
                    alertDialog=alert.create();
                    alertDialog.show();
                }
            }
        });

        mPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        empty = (ImageView) findViewById(R.id.empty);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent intent = new Intent(MainActivity.this,about.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean hasAccessGranted() {
        ContentResolver contentResolver = this.getContentResolver();
        String enabledNotificationListeners = Settings.Secure.getString(contentResolver, Utils.SETTING_NOTIFICATION_LISTENER);
        String packageName = this.getPackageName();

        // check to see if the enabledNotificationListeners String contains our package name
        return !(enabledNotificationListeners == null || !enabledNotificationListeners.contains(packageName));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!hasAccessGranted()) {
            mPreferences.edit().remove(Utils.PREF_ENABLED).apply();
            Snackbar.make(empty, R.string.snackbar_not_allowed_title, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.snackbar_not_allowed_action, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Utils.ACTION_NOTIFICATION_LISTENER_SETTINGS));
                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.cardview_light_background))
                    .show();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (TextUtils.isEmpty(s)) {
            adapter_.setApps(applications);

        } else {
            String query = s.toLowerCase();
            ArrayList<ApplicationInfo> filtered = new ArrayList<>();
            for (ApplicationInfo app : applications) {
                String label = app.loadLabel(manager).toString().toLowerCase();
                if (label.contains(query)) {
                    filtered.add(app);
                }
            }
            adapter_.setApps(filtered);
        }
        return true;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            fragment = null;
            switch (position){
                case  0:
                    fragment = new Frag1_profile();
                    break;
                case 1:
                    fragment = new Frag2_app();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Profiles";
                case 1:
                    return "Apps";
            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Do you really want to exit ? ")
                .setIcon(R.drawable.cancel)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
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
