package com.cxorz.anywhere;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* To enable fullscreen welcome page, status bar was set transparent, but this causes blank status bar on other pages
         * Design as follows:
         * 1. All Activities except WelcomeActivity inherit BaseActivity
         * 2. WelcomeActivity handles separately, other Activities manually fill StatusBar
         * */
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary, this.getTheme()));

        setContentView(R.layout.activity_settings);
        
        if (savedInstanceState == null) {
            getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.settings, new FragmentSettings())
            .commit();
        }

        /* Get default top title bar (Android calls it ActionBar) */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish(); // back button
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}