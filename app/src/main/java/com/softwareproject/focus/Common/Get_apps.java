package com.softwareproject.focus.Common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Get_apps {

    Context context;

    public Get_apps(Context context) {
        this.context = context;
    }

    public List<ApplicationInfo> get_apps (){
        List<ApplicationInfo> appsList = new ArrayList<>();
        final PackageManager manager = context.getPackageManager();
        List<ApplicationInfo> applications = manager.getInstalledApplications(0);

        for (ApplicationInfo app : applications) {
            if(manager.getLaunchIntentForPackage(app.packageName) != null) {
                // apps with launcher intent
                if((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 1) {
                    // updated system apps

                } else if ((app.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                    // system apps

                } else {
                    // user installed apps

                }
                appsList.add(app);
            }

        }

        Collections.sort(appsList, new Comparator<ApplicationInfo>() {
            @Override
            public int compare(ApplicationInfo app1, ApplicationInfo app2) {
                String label1 = app1.loadLabel(manager).toString();
                String label2 = app2.loadLabel(manager).toString();

                return label1.compareToIgnoreCase(label2);
            }
        });

        return appsList;
    }
}
