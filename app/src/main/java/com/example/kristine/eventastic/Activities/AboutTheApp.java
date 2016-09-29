package com.example.kristine.eventastic.Activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.example.kristine.eventastic.Adapter.ViewPagerAdapter;
import com.example.kristine.eventastic.Fragments.FirstFragment;
import com.example.kristine.eventastic.Fragments.FourthFragment;
import com.example.kristine.eventastic.Fragments.SecondFragment;
import com.example.kristine.eventastic.Fragments.ThirdFragment;
import com.example.kristine.eventastic.R;

public class AboutTheApp extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    // this activity shows information about this app and its usage
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setupWindow();
        setupToolBar();
        setupBackArrow();
    }


    // setup toolbar with 4 slidingTabs showing short information/explanation texts
    private void setupToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout =(TabLayout)findViewById(R.id.tablayout);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.red));

        // instances of ViewPager and PagerAdapter
        // ViewPager handles animation and allows swiping horizontally to access previous and next steps
        // The pager adapter provides the pages to the view pager widget
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        // adding each fragment to the pager
        viewPagerAdapter.addFragments(new FirstFragment(), getResources().getString(R.string.about_this_app_what_tab));
        viewPagerAdapter.addFragments(new SecondFragment(), getResources().getString(R.string.about_this_app_how_tab));
        viewPagerAdapter.addFragments(new ThirdFragment(), getResources().getString(R.string.about_this_app_help_tab));
        viewPagerAdapter.addFragments(new FourthFragment(), getResources().getString(R.string.about_this_app_contact_tab));

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    //Choosing this icon the user gets back to the MainActivity
    private void setupBackArrow() {
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }


    private void setupWindow() {
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        }
    }
}
