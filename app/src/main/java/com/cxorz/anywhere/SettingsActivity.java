package com.cxorz.anywhere;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* To enable full screen for the welcome page, the status bar is set to transparent,
         * but this causes the status bar to be blank on other pages.
         * Design approach:
         * 1. All Activities except WelcomeActivity inherit from BaseActivity
         * 2. WelcomeActivity is handled separately, other Activities manually fill StatusBar
         * */
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary, this.getTheme()));

        setContentView(R.layout.activity_settings);
        
        if (savedInstanceState == null) {
            getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.settings, new FragmentSettings())
            .commit();
        }

        /* Get the default top title bar (called ActionBar in Android) */
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