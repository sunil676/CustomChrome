package com.sunil.customchrome;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_chrome = (Button) findViewById(R.id.button_chrome);
        button_chrome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("http://sunil-android.blogspot.in/");
                openCustomChromeTab(uri);
            }
        });

    }

    public void  openCustomChromeTab(Uri uri){

        // create an intent builder
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();

        // Begin customizing
        // set toolbar colors
        intentBuilder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        // add menu items
        intentBuilder.addMenuItem(getString(R.string.title_menu_1),
                createPendingIntent(ChromeTabActionBroadcastReceiver.ACTION_MENU_ITEM_1));
        intentBuilder.addMenuItem(getString(R.string.title_menu_2),
                createPendingIntent(ChromeTabActionBroadcastReceiver.ACTION_MENU_ITEM_2));

        //Setting a custom back button
        intentBuilder.setCloseButtonIcon(BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_arrow_back));

        // set start and exit animations
        intentBuilder.setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left);
        intentBuilder.setExitAnimations(this, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);

        // build custom tabs intent
        CustomTabsIntent customTabsIntent = intentBuilder.build();

        // launch the url
        customTabsIntent.launchUrl(this, uri);
    }

    private PendingIntent createPendingIntent(int actionSource) {
        Intent actionIntent = new Intent(this, ChromeTabActionBroadcastReceiver.class);
        actionIntent.putExtra(ChromeTabActionBroadcastReceiver.KEY_ACTION_SOURCE, actionSource);
        return PendingIntent.getBroadcast(this, actionSource, actionIntent, 0);
    }
}
