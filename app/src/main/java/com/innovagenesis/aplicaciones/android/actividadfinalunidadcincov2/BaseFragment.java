package com.innovagenesis.aplicaciones.android.actividadfinalunidadcincov2;


import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovagenesis.aplicaciones.android.actividadfinalunidadcincov2.dialogo.DialogMenssaje;


/**
 * Fragment que desplega el contenido de los tabs
 */
public class BaseFragment extends Fragment {

    private TypedArray imgTabs;

    private String name;
    private static final String ARG_NAME = "name";

    private int position;
    private static final String ARG_DRAWABLE = "position";

    private int idArreglo;
    private static final String ARG_ARREGLO = "idArreglo";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    public static Fragment getInstance(String name, int position, int idArreglo) {
        BaseFragment fragment = new BaseFragment();

        /** Parametros que trae el fragment*/
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putInt(ARG_DRAWABLE, position);
        args.putInt(ARG_ARREGLO, idArreglo);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            name = args.getString(ARG_NAME);
            position = args.getInt(ARG_DRAWABLE);
            idArreglo = args.getInt(ARG_ARREGLO);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        switch (idArreglo) {

            /** Seleciona la lista a desplegar de iconos e imagenes*/
            case 1:
                imgTabs = getContext().getResources().obtainTypedArray(R.array.facebookImg);
                break;
            case 2:
                imgTabs = getContext().getResources().obtainTypedArray(R.array.twiterImg);
                break;
            case 3:
                imgTabs = getContext().getResources().obtainTypedArray(R.array.plusImg);
                break;
            case 4:
                imgTabs = getContext().getResources().obtainTypedArray(R.array.twiterImg);
                break;
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.imgArray);

        /** Cambia los colores de los items de las pestañas*/
        @SuppressLint("Recycle")
        TypedArray arrayColorToolbar = getResources().obtainTypedArray(R.array.colorToolbar);
        int color = arrayColorToolbar.getResourceId(idArreglo, 0);
        int cambiarColor = ContextCompat.getColor(getActivity().getBaseContext(), color);

        imageView.setImageTintList(ColorStateList.valueOf(cambiarColor));
        imageView.setImageResource(imgTabs.getResourceId(position,0));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /** Crea el mensaje a desplegar + la confirmacion**/
                DialogMenssaje dialogMenssaje = new DialogMenssaje();
                dialogMenssaje.newShareDialog(getActivity()).show();
            }
        });

        ((TextView) view.findViewById(R.id.text_name)).setText(this.name);
    }
}
