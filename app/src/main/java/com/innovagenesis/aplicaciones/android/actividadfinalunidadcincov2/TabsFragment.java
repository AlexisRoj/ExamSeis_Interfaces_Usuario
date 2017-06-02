package com.innovagenesis.aplicaciones.android.actividadfinalunidadcincov2;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innovagenesis.aplicaciones.android.actividadfinalunidadcincov2.adapter.BaseViewPagerAdapter;

/**
 * Administra los tabs
 * Created by Alexis on 22/11/2016.
 */

public class TabsFragment extends Fragment {

    private String[] tabs;
    private TypedArray imgTabs;
    int cambiarColor;
    int id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.app_bar_with_tabs, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity activity = (MainActivity) getActivity();
        id = activity.getPositionArray();

        switch (id) {

            /** Seleciona la lista a desplegar de iconos e imagenes*/
            case 1:
                tabs = getContext().getResources().getStringArray(R.array.facebookTabs);
                imgTabs = getContext().getResources().obtainTypedArray(R.array.facebookImg);
                break;
            case 2:
                tabs = getContext().getResources().getStringArray(R.array.instagramTabs);
                imgTabs = getContext().getResources().obtainTypedArray(R.array.twiterImg);
                break;
            case 3:
                tabs = getContext().getResources().getStringArray(R.array.plusTabs);
                imgTabs = getContext().getResources().obtainTypedArray(R.array.plusImg);
                break;
            case 4:
                tabs = getContext().getResources().getStringArray(R.array.twiterTabs);
                imgTabs = getContext().getResources().obtainTypedArray(R.array.twiterImg);
                break;
        }

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        /** Adjunta el adapter*/
        viewPager.setAdapter(new BookViewPagerAdapter(getActivity().getSupportFragmentManager()));


        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE); // para cuando es mas de 3 tabs
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        /**
         *
         * Administra los colores del drawer, toolbar y tabs
         *
         * */

        @SuppressLint("Recycle")
        TypedArray arrayColorToolbar = getResources().obtainTypedArray(R.array.colorToolbar);

        int color = arrayColorToolbar.getResourceId(id, 0);
        cambiarColor = ContextCompat.getColor(activity.getBaseContext(), color);

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);


        if (Build.VERSION.SDK_INT >= 21) {
            /** Cambia colores apartir de la version 21*/
            toolbar.setBackgroundColor(cambiarColor);
            tabLayout.setBackgroundColor(cambiarColor);
            activity.getWindow().setStatusBarColor(cambiarColor);
        }

        /** Incerta el icono en el tab */
        for (int i = 0; i < imgTabs.length(); i++) {

            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null){
                tab.setIcon(imgTabs.getResourceId(i,0));
                //tab.setText(null); //elimina el texto de los tabs
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String title = getString(R.string.app_name);
        String subTitle = null;
        int check = 0;
        /** Selector de titulos de toolbar*/

        MainActivity activity = (MainActivity) getActivity();

        switch (id){
            case 1:
                subTitle = getString(R.string.facebook);
                check = R.id.nav_facebook;
                break;
            case 2:
                subTitle = getString(R.string.instragram);
                check = R.id.nav_instagram;
                break;
            case 3:
                subTitle = getString(R.string.google_plus);
                check = R.id.nav_plus;
                break;
            case 4:
                subTitle = getString(R.string.twitter);
                check = R.id.nav_tweeter;
                break;
        }

        /** Actualiza el titulo y marca el item selecionado del drawer*/
        activity.updateView(title, subTitle);
        //activity.navigationView.setBackgroundColor(cambiarColor); //cambia fondo
        activity.navigationView.setCheckedItem(check);
    }

    private class BookViewPagerAdapter extends BaseViewPagerAdapter {

        private BookViewPagerAdapter(FragmentManager manager) {
            super(manager, tabs,id);
        }


    }
}




