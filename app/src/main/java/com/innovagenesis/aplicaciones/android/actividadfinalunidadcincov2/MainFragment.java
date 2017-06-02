package com.innovagenesis.aplicaciones.android.actividadfinalunidadcincov2;


import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Administra el home
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.app_bar_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*** Agrega la imagen y el texto de configuracion*/
        ((ImageView)view.findViewById(R.id.image_storage)).setImageResource(R.drawable.home);
        ((TextView)view.findViewById(R.id.textContent)).setText(R.string.home);

        @SuppressLint("Recycle")
        TypedArray arrayColorToolbar = getResources().obtainTypedArray(R.array.colorToolbar);

        int color = arrayColorToolbar.getResourceId(0, 0);
        int cambiarColor = ContextCompat.getColor(getActivity().getBaseContext(), color);

        /** Cambia colores del toolbar*/
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);

        if (Build.VERSION.SDK_INT >= 21) {
            /** Cambia colores apartir de la version 21*/
            toolbar.setBackgroundColor(cambiarColor);
            getActivity().getWindow().setStatusBarColor(cambiarColor);
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() instanceof MainActivity){

            /** Cambia el titulo y el subtitulo del Toolbar*/
            String title = getString(R.string.app_name);
            String subTitle = getString(R.string.home);

            MainActivity activity = (MainActivity) getActivity();

            activity.updateView(title,subTitle);
            activity.navigationView.setCheckedItem(R.id.nav_home);
        }
    }
}
