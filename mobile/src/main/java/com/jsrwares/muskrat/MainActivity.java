package com.jsrwares.muskrat;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.jsrwares.muskrat.media.controllers.Controller;
import com.jsrwares.muskrat.tabs.SlidingTabLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPager mViewPager;
    SlidingTabLayout mSlidingTabLayout;
    SlidingTabFragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new SlidingTabsPagerAdapter(getSupportFragmentManager()));
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
        mSlidingTabLayout.setOnPageChangeListener(new Controller.OnPageChangeListenerImpl());

//        ContactDao dao = new ContactDao(this);
//        Contact joe = new Contact(null, "Joe", "Rogers", "jsrogers1@gmail.com", "678-592-9139");
//        Contact sue = new Contact(null, "Sue", "Sanders", "sue123@msn.com", "555-555-5555");
//        Contact robbin = new Contact(null, "Robbin", "Lowe", "robbin@abc.com", "123-456-7890");
//
//        dao.insert(joe);
//        dao.insert(sue);
//        dao.insert(robbin);
//
//        int count = dao.getCount();
//        Toast.makeText(this, "Count: " + count, Toast.LENGTH_LONG).show();
    }

    class SlidingTabsPagerAdapter extends FragmentPagerAdapter {

        String[] tabLabels;

        public SlidingTabsPagerAdapter(FragmentManager fm) {
            super(fm);
            tabLabels = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
//            SlidingTabFragment fragment = SlidingTabFragment.getInstance(position);
//            return fragment;
            mCurrentFragment = SlidingTabFragment.getInstance(position);
            return mCurrentFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabLabels[position];
        }

        @Override
        public int getCount() {
            return tabLabels.length;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mCurrentFragment.onKeyDown(keyCode, event))
            return true;
        else
            return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
