package com.myrule.kumarayogi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.myrule.kumarayogi.pdf.R;

import java.util.Timer;
import java.util.TimerTask;


public class Home extends AppCompatActivity implements MyRuleAdapter {

    private ListView listView;
    private String names[] = {
            "Karanika Kumarayogi",
            "Gallery",
            "Quotes",
            "Download PDF",
    };

    private String desc[] = {
            "",
            "",
            "",
            "",
    };

    ViewPager viewPager;
    int currentPage=0;
    int NUM_PAGES=5;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        CustomList customList = new CustomList(this, names, desc);


        viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter adapter = new CustomSlideAdapter(Home.this);
        viewPager.setAdapter(adapter);


        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (currentPage == NUM_PAGES) {
                            currentPage = 0;
                        }
                        viewPager.setCurrentItem(currentPage++, true);
                    }
                });
            }
        }, 500, 3000);


        listView = (ListView) findViewById(R.id.listView);
        if (listView != null) {
            listView.setAdapter(customList);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                switch (i){
                    case 0: intent=new Intent(getApplicationContext(),Reader.class);
                        startActivity(intent);break;
                    case 1:
                        intent=new Intent(getApplicationContext(),Gallery.class);
                        startActivity(intent);break;
                    case 2:
                        Toast.makeText(getApplicationContext(),"Coming soon",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(),"Coming soon",Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(),"Coming soon",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}