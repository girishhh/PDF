package com.myrule.kumarayogi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.myrule.kumarayogi.pdf.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.logging.Logger;

public class PdfDownload extends AppCompatActivity {
    InputStream in_stream;
    OutputStream out_stream;

    public PdfDownload(){};
    PdfDownload(InputStream in_stream,OutputStream out_stream){
        this.in_stream = in_stream;
        this.out_stream = out_stream;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_download);
            AssetManager assets = getAssets();
            final String files[] = {"file1.pdf","file2.pdf"};


            ArrayAdapter adapter = new ArrayAdapter(this,R.layout.download_list,files);
            ListView listView = (ListView)findViewById(R.id.pdf_files);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    Intent intent=new Intent(getApplicationContext(),Reader.class);
                    intent.putExtra("file_name",files[position]);
                    startActivity(intent);
                }
            });
    }

    public void download_file(String file_name){
        DownloadTask dow_task = new DownloadTask(PdfDownload.this);
        dow_task.execute(file_name);
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}
