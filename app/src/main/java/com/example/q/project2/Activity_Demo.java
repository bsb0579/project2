package com.example.q.project2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import java.util.ArrayList;

public class Activity_Demo extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__demo);

        ArrayList<String> permissions = new ArrayList<>();
        permissions.add(checkPermission(Manifest.permission.READ_CONTACTS));
        permissions.add(checkPermission(Manifest.permission.WRITE_CONTACTS));
        permissions.add(checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE));
        permissions.add(checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE));
        permissions.add(checkPermission(Manifest.permission.INTERNET));
        while(permissions.remove(null));
        if(!permissions.isEmpty())
            getPermission(permissions.toArray(new String[permissions.size()]), permissions.size());



        if(permissions.isEmpty()) {


            //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            //setSupportActionBar(toolbar);
            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

            //added to inset ICONS
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_phone);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_picture);
            tabLayout.getTabAt(2).setIcon(R.drawable.ic_game);

            mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setOnTabSelectedListener(
//                new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
//                    @Override
//                    public void onTabSelected(TabLayout.Tab tab) {
//                        super.onTabSelected(tab);
//                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_phone_select);
//                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_picture_select);
//                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_game_select);
//                    }
//                    @Override
//                    public void onTabUnselected(TabLayout.Tab tab) {
//                        super.onTabUnselected(tab);
//                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_phone);
//                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_picture);
//                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_game);
//                    }
//                    @Override
//                    public void onTabReselected(TabLayout.Tab tab) {
//                        super.onTabReselected(tab);
//                    }
//                }
//        );
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        }



        else
            recreate();
    }

    public String checkPermission(String request){
        if(ContextCompat.checkSelfPermission(this,request) != PackageManager.PERMISSION_GRANTED){
            return request;
        }
        else
            return null;
    }
    public void getPermission(String[] permissions,int request_code){
        ActivityCompat.requestPermissions(this, permissions, request_code);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if(grantResults.length > 0) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "Permission Denied. Cannot Launch app.", Toast.LENGTH_SHORT).show();
                    onDestroy();
                }
            }
        }
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    Contacts tab1 = new Contacts();
                    return tab1;
                case 1:
                    Galleryserver tab2 = new Galleryserver();
                    return tab2;
                case 2:
                    Anonymous tab3 = new Anonymous();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position){
            switch (position){
                case 0:
                    return  "CONTACTS";
                case 1:
                    return  "GALLERYSERVER";
                case 2:
                    return "ANONYMOUS";

            }
            return null;
        }
    }
}