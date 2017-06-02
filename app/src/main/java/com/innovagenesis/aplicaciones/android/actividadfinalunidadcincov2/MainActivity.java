package com.innovagenesis.aplicaciones.android.actividadfinalunidadcincov2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.innovagenesis.aplicaciones.android.actividadfinalunidadcincov2.dialogo.DialogMenssaje;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MenuItem.OnMenuItemClickListener {

    View hView;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FloatingActionButton fab;
    private int idSubmenu;

    public int getIdSubmenu() {
        return idSubmenu;
    }

    private int positionArray;

    public int getPositionArray() {
        return positionArray;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /** Se instancia Fragmento principal */
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new MainFragment())
                .commit();

        /** Se instancian el drawer y el navigation*/

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        /** Validacion de que se encuentra vacio*/

        if (navigationView != null)
            navigationView.setNavigationItemSelectedListener(this);

        /** Boton Flotante esta funcionando */

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = "arojash@innovagenesis.com";
                String subject = "Sugerencias";
                String body = "";

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, body);

                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
            }
        });
    }

    public void updateView(String title, String subTitle) {
        /** Actualiza el titulo y subtitulo del toolbar*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(title);
            toolbar.setSubtitle(subTitle);
        }
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(navigationView))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        /** Traen las etiquetas e imagenes del subMenu*/
        String[] etiquetaSubMenu = getResources().getStringArray(R.array.nombreMenu);
        @SuppressLint("Recycle")
        TypedArray imgSubMenu = getResources().obtainTypedArray(R.array.nombreImgMenu);
        String[] uriIntent = getResources().getStringArray(R.array.uriMenu);

        SubMenu versionWeb = menu.addSubMenu(R.string.version_web);


        for (int i = 0; i < etiquetaSubMenu.length - 1; i++) {
            /** Agrega submenu, se envia uri a traves de un arreglo
             * Se le suma uno a i para reutilizar arreglo de etiquetas*/

            new Intent(Intent.ACTION_VIEW, Uri.parse(uriIntent[i + 1]));

            versionWeb.add(0, i + 1, i + 1, etiquetaSubMenu[i + 1])
                    .setIcon(imgSubMenu.getResourceId(i + 1, 0))
                    .setOnMenuItemClickListener(this);
            //.setIntent(intent);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.compartir) {

            /** Crea el mensaje a desplegar + la confirmacion**/
            DialogMenssaje dialogMenssaje = new DialogMenssaje();
            dialogMenssaje.newShareDialog(this).show();

            return true;
        } else if (id == R.id.settings) {

            /** Instancia Configuracion*/
            Fragment fragment = new ConfiguracionFragment();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("ResourceType")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        Fragment fragment;

        switch (id) {
            /** Selector de fragmentos del Drawer*/
            case R.id.nav_home:
                positionArray = 0;
                break;
            case R.id.nav_facebook:
                positionArray = 1;
                break;
            case R.id.nav_instagram:
                positionArray = 2;
                break;
            case R.id.nav_plus:
                positionArray = 3;
                break;
            case R.id.nav_tweeter:
                positionArray = 4;
                break;
        }

        if (positionArray < 1)
            fragment = new MainFragment();
        else
            fragment = new TabsFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        /**Cambia el color del Drawer*/
        @SuppressLint("Recycle")
        TypedArray arrayColorToolbar = getResources().obtainTypedArray(R.array.colorToolbar);
        int color = arrayColorToolbar.getResourceId(positionArray, 0);
        int cambiarColor = ContextCompat.getColor(getBaseContext(), color);

        hView = navigationView.getHeaderView(0);
        LinearLayout head = (LinearLayout) hView.findViewById(R.id.header);
        head.setBackgroundColor(cambiarColor);

        /** Cambia la imagen del drawer*/
        @SuppressLint("Recycle")
        TypedArray imgDrawer = getResources().obtainTypedArray(R.array.nombreImgMenu2);
        ImageView imgSocial = (ImageView) findViewById(R.id.imgSocial);

        imgSocial.setImageResource(imgDrawer.getResourceId(positionArray, 0));

        /** Cambia color boton flotante*/
        fab.setBackgroundTintList(ColorStateList.valueOf(cambiarColor));

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        /**Instancia la vista de navegacion*/
        idSubmenu = item.getItemId();
        Fragment fragment = new WebViewFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,fragment)
                .commit();

        return true;
    }
}
