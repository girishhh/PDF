package com.myrule.kumarayogi;


import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.myrule.kumarayogi.pdf.R;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class Reader extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        final String asset_file_name = getIntent().getStringExtra("file_name");

        PDFView pdfView= (PDFView)findViewById(R.id.pdfView);

        pdfView.fromAsset(asset_file_name)
                .defaultPage(0)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableAnnotationRendering(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();

        Button dow_btn = (Button)findViewById(R.id.download_button);
        dow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isExternalStorageWritable()) {
                    InputStream in = null;
                    try {
                        long free_space = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getFreeSpace() / 1048576;
                        in = getAssets().open(asset_file_name);
                        float file_size = in.available() / (float) 1048576;
                        in.close();

                        if (free_space > file_size) {
                            String file_size_str = new DecimalFormat("##.##").format(file_size);

                            AlertDialog.Builder alert_dialog = new AlertDialog.Builder(Reader.this);
                            alert_dialog.setTitle("Download Message");
                            alert_dialog.setMessage("Are you sure to download..\n" + file_size_str + " MB " + "space will be used after this download");
                            alert_dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    download_file(asset_file_name);

                                }
                            });
                            alert_dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alert_msg = alert_dialog.create();
                            alert_msg.show();

                        } else {
                            Toast.makeText(getApplicationContext(), "There is no enough space on your device", Toast.LENGTH_LONG).show();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else{
                    Toast.makeText(getApplicationContext(),"Sorry Dont have permission to write to external storage",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public void download_file(String file_name){
        DownloadTask dow_task = new DownloadTask(Reader.this);
        dow_task.execute(file_name);
    }
}
