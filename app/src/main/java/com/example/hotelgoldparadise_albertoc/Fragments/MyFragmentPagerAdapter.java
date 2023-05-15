package com.example.hotelgoldparadise_albertoc.Fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    public MyFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new InsertNewClient();
        }else if(position ==1){
            return new ModifyClient();
        }else{
            return new DeleteClient();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0: return "INSERTAR";
            case 1: return  "MODIFICAR";
            default: return "ELIMINAR";
        }
    }
}
