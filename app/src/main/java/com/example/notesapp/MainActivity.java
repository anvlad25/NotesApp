package com.example.notesapp;

import android.content.res.Configuration;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    protected static boolean isLandscape;
    protected static ArrayList<Notes> notesArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        notesArrayList = initArrayNotes();

        initFrame();
        initToolbar();
        initDrawer(initToolbar());
    }

    private ArrayList<Notes> initArrayNotes() {
        ArrayList<Notes> arrayList = new ArrayList<>();
        arrayList.add(new Notes("Заметка 1", "Важный текст из заметки 1", new Date().getTime()));
        arrayList.add(new Notes("Заметка 2", "Очень важный текст из заметки 2", new Date().getTime()));

        return arrayList;
    }

    private void initFrame() {
        Bundle bundle = new Bundle();
        NotesFragment fragment = new NotesFragment();
        bundle.putParcelableArrayList(Notes.NOTE_KEY, notesArrayList);
        fragment.setArguments(bundle);

        if (!isLandscape) {
            includeFragment(fragment, R.id.frame_container);
        } else {
            includeFragment(fragment, R.id.frame_container);
            includeFragment(new NotesDescriptionFragment(), R.id.frame_container_land);
        }
    }

    private void includeFragment(Fragment fragment, int frameId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(frameId, fragment);

        fragmentTransaction.commit();
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, R.string.menu_settings, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return true;
    }

    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.action_settings) {
                Toast.makeText(MainActivity.this, R.string.menu_info, Toast.LENGTH_SHORT).show();
            }
            return false;
        });
    }
}