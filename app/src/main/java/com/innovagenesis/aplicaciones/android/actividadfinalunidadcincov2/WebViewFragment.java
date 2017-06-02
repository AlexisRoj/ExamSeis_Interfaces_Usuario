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
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment extends Fragment {

    private int id;

    public WebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view, container, false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        /** Indica cual es la selecion realizada*/
        MainActivity activity = (MainActivity) getActivity();
        id = activity.getIdSubmenu();

        String [] url = activity.getResources().getStringArray(R.array.uriMenu);

        /** Crea el webView*/
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        WebView webView = (WebView) view.findViewById(R.id.webview);
        final TextView textView = (TextView)view.findViewById(R.id.texttmp);


        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        activity.setTitle(url[id]);
        webView.loadUrl(url[id]);

        /** Carga el progressBar*/
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress){
                progressBar.setProgress(0);
                progressBar.setVisibility(View.VISIBLE);

                progressBar.incrementProgressBy(progress);

                if (progress == 100){
                    progressBar.setVisibility(View.GONE);
                    textView.setVisibility(View.INVISIBLE);
                }
            }
        });

        /*** Agrega la imagen y el texto de configuracion*/
        @SuppressLint("Recycle")
        TypedArray arrayColorToolbar = getResources().obtainTypedArray(R.array.colorToolbar);

        int color = arrayColorToolbar.getResourceId(id, 0);
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
            String[] subTitle = getResources().getStringArray(R.array.nombreMenu);
            MainActivity activity = (MainActivity) getActivity();

            activity.updateView(title,subTitle[id]);
            activity.navigationView.setCheckedItem(R.id.nav_home);
        }
    }

}
