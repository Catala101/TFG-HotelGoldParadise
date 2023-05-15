package com.example.hotelgoldparadise_albertoc;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.hotelgoldparadise_albertoc.Fragments.MyFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;


public class ClientGestion extends AppCompatActivity {
    ViewPager my_view_pager;
    TabLayout my_tab_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_gestion);
        my_view_pager = findViewById(R.id.my_view_pager);

        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        my_view_pager.setAdapter(myFragmentPagerAdapter);

        my_tab_layout = findViewById(R.id.my_tab_layout);
        my_tab_layout.setupWithViewPager(my_view_pager);



    }

}
