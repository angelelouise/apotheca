package com.ufrn.angele.apotheca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ufrn.angele.apotheca.R;
import com.ufrn.angele.apotheca.dominio.Discente;
import com.ufrn.angele.apotheca.dominio.Turma;
import com.ufrn.angele.apotheca.dominio.Usuario;
import com.ufrn.angele.apotheca.fragment.HomeFragment;
import com.ufrn.angele.apotheca.fragment.NotificacoesFragment;
import com.ufrn.angele.apotheca.fragment.PerfilFragment;
import com.ufrn.angele.apotheca.fragment.PostsFragment;
import com.ufrn.angele.apotheca.fragment.TurmasFragment;
import com.ufrn.angele.apotheca.outros.CircleTransform;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static class ViewHolder{
        private NavigationView navigationView;
        private DrawerLayout drawer;
        private View navHeader;
        private ImageView imgNavHeaderBg, imgProfile;
        private TextView txtName, txtRank;
        private Toolbar toolbar;
        private FloatingActionButton fab;
    }
    private ViewHolder mViewHolder = new ViewHolder();
    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "https://api.androidhive.info/images/nav-menu-header-bg.jpg";
    private static String urlProfileImg = "";
    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_PERFIL = "perfil";
    private static final String TAG_TURMAS = "turmas";
    private static final String TAG_POSTS = "posts";
    private static final String TAG_NOTIFICACAO = "notificacoes";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    private Usuario usuario = new Usuario();
    private ArrayList<Discente> discentes = new ArrayList<>();
    private ArrayList<Turma> turmas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        usuario = (Usuario) intent.getSerializableExtra("usuario");
        discentes = (ArrayList<Discente>) intent.getSerializableExtra("discentes");
        turmas = (ArrayList<Turma>) intent.getSerializableExtra("turmas");

        mViewHolder.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mViewHolder.toolbar);
        mHandler = new Handler();

        mViewHolder.drawer = findViewById(R.id.drawer_layout);
        mViewHolder.navigationView = findViewById(R.id.nav_view);
        mViewHolder.fab = findViewById(R.id.fab);

        // Navigation view header
        mViewHolder.navHeader = mViewHolder.navigationView.getHeaderView(0);
        mViewHolder.txtName = mViewHolder.navHeader.findViewById(R.id.name);
        mViewHolder.txtRank = mViewHolder.navHeader.findViewById(R.id.rank);
        mViewHolder.imgNavHeaderBg = mViewHolder.navHeader.findViewById(R.id.img_header_bg);
        mViewHolder.imgProfile = mViewHolder.navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        mViewHolder.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }


    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {
        // trocar pela consulta do nome
        mViewHolder.txtName.setText(usuario.getNome());
        //trocar pela consulta do rank
        mViewHolder.txtRank.setText("Universitário Sofrido");
        urlProfileImg = usuario.getUrl_foto();
        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mViewHolder.imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mViewHolder.imgProfile);

        // showing dot next to notifications label
        mViewHolder.navigationView.getMenu().getItem(0).setActionView(R.layout.menu_dot);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            mViewHolder.drawer.closeDrawers();

            // show or hide the fab button
            toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        toggleFab();

        //Closing drawer on item click
        mViewHolder.drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                return new HomeFragment();
            case 1:
                Log.d("discentesMain",discentes.toString());
                PerfilFragment perfilFragment =new PerfilFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("usuario", usuario);
                bundle.putSerializable("discente", discentes);


                perfilFragment.setArguments(bundle);
                return perfilFragment;
            case 2:
                return new TurmasFragment();
            case 3:
                return new PostsFragment();
            case 4:
                return  new NotificacoesFragment();

            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        mViewHolder.navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        mViewHolder.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_perfil:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PERFIL;
                        break;
                    case R.id.nav_turmas:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_TURMAS;
                        break;
                    case R.id.nav_posts:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_POSTS;
                        break;
                    case R.id.nav_notificacoes:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_NOTIFICACAO;
                        break;
                    case R.id.nav_sobre:
                        // launch new intent instead of loading fragment
                        //startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        mViewHolder.drawer.closeDrawers();
                        return true;
                    case R.id.nav_sair:
                        // launch new intent instead of loading fragment
                        //startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                        mViewHolder.drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,  mViewHolder.drawer,  mViewHolder.toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        mViewHolder.drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if ( mViewHolder.drawer.isDrawerOpen(GravityCompat.START)) {
            mViewHolder.drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//
//        // show menu only when home fragment is selected
//        if (navItemIndex == 0) {
//            getMenuInflater().inflate(R.menu.main, menu);
//        }
//
//        // when fragment is notifications, load the menu created for notifications
//        if (navItemIndex == 3) {
//            getMenuInflater().inflate(R.menu.notifications, menu);
//        }
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_logout) {
//            Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
//            return true;
//        }
//
//        // user is in notifications fragment
//        // and selected 'Mark all as Read'
//        if (id == R.id.action_mark_all_read) {
//            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
//        }
//
//        // user is in notifications fragment
//        // and selected 'Clear All'
//        if (id == R.id.action_clear_notifications) {
//            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    // show or hide the fab
    private void toggleFab() {
        if (navItemIndex == 0)
            mViewHolder.fab.show();
        else
            mViewHolder.fab.hide();
    }
}
