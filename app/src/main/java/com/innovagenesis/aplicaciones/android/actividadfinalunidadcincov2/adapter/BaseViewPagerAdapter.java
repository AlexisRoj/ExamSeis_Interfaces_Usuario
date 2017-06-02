package com.innovagenesis.aplicaciones.android.actividadfinalunidadcincov2.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.innovagenesis.aplicaciones.android.actividadfinalunidadcincov2.BaseFragment;

/**
 * Adaptador encargado de rellenar los tabs
 * con texto.
 * Created by Alexis on 22/11/2016.
 */

public class BaseViewPagerAdapter extends FragmentStatePagerAdapter {

    private String[] tabs;
    private int idArreglo;

    public BaseViewPagerAdapter(FragmentManager fm, String[] tabs, int idArreglo) {
        super(fm);
        this.tabs = tabs;
        this.idArreglo = idArreglo;
    }

    @Override
    public Fragment getItem(int position) {
        /** Se envia la posicion del ID, y el ID del arreglo en el que se encuentra*/
        return BaseFragment.getInstance(tabs[position],position,idArreglo);
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
