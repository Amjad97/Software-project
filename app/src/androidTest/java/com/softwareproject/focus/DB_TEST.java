package com.softwareproject.focus;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.softwareproject.focus.Database.Database;
import com.softwareproject.focus.Models.app;
import com.softwareproject.focus.Models.profile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DB_TEST {

    Database db;

    @Before
    public void useAppContext() {
        // Context of the app under test.
        Context context = InstrumentationRegistry.getTargetContext();
        this.db = new Database(context);
    }

    @Test
    public void insert_profile_test(){
        db.Insert_profile("social media","Active","Wed", "Fri","No");
        profile profile = db.getAllProfile().get(0);

        assertEquals("social media",profile.getName());
        assertEquals("Active",profile.getStatus());
        assertEquals("Wed",profile.getDays());
        assertEquals("Fri",profile.getTimes());
        assertEquals("No",profile.getRepeat());
    }

    @Test
    public void update_profile_test(){
        db.Insert_profile("social media","Active","Wed", "Fri","No");
        db.update_profile(1,"sleep", "sat","mon","daily");
        profile pro = db.getAllProfile().get(0);
        assertEquals(1,pro.getId());
        assertEquals("sleep",pro.getName());
        assertEquals("sat",pro.getDays());
        assertEquals("mon",pro.getTimes());
        assertEquals("daily",pro.getRepeat());
    }
    @Test
    public void update_status_test(){
        db.Insert_profile("do not disturb","Deactivate","Wed", "Fri","No");
        db.update_status("do not disturb" , "active");
        profile pro = db.getAllProfile().get(0);
        assertEquals("do not disturb",pro.getName());
        assertEquals("active",pro.getStatus());
    }

    @Test
    public void update_app_status_test(){
        db.Insert_app("do not disturb","Deactivate",0);
        db.update_app_status("do not disturb" , "active",0);
        app pro = db.get_app().get(0);
        assertEquals("do not disturb",pro.getName());
        assertEquals("active",pro.getaSwitch());
    }

    @Test
    public void insert_app_test(){
        db.Insert_app("Facebook","Active",1);
        app app = db.getAllApp().get(0);

        assertEquals("Facebook",app.getName());
        assertEquals("Active",app.getaSwitch());
        assertEquals(1,app.getProfile_id());
    }

    @Test
    public void del_profile_test(){
        db.Insert_profile("do not disturb","Deactivate","Wed", "Fri","No");
        db.del_profile(1);

        assertEquals(false ,db.getAllProfile().contains(new profile(1,"do not disturb","Deactivate","Wed", "Fri","No")));
    }

    @Test
    public void del_app_test(){
        db.Insert_app("Facebook","Active",1);
        db.del_app("Facebook",1);

        assertEquals(false ,db.getAllApp().contains(new app("Facebook","Active", 1)));
    }

    @After
    public void clear_test(){
        db.clear_db();
    }

}