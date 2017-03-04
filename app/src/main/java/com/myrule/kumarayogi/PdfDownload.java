package com.myrule.kumarayogi;

import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class PdfDownload extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_download);
        try {
            AssetManager assets = getAssets();
            final String files[] = assets.list("download");
            ArrayAdapter adapter = new ArrayAdapter(this,R.layout.download_list,files);
            ListView listView = (ListView)findViewById(R.id.pdf_files);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    if(isExternalStorageWritable()) {
                        InputStream in = null;
                        try {
                            long free_space = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getFreeSpace() / 1048576;
                            in = getAssets().open("download/" + files[position].toString());
                            float file_size = in.available() / (float) 1048576;

                            if (free_space > file_size) {
                                String file_size_str = new DecimalFormat("##.##").format(file_size);

                                AlertDialog.Builder alert_dialog = new AlertDialog.Builder(PdfDownload.this);
                                alert_dialog.setTitle("Download Message");
                                alert_dialog.setMessage("Are you sure to download..\n" + file_size_str + " MB " + "space will be used after this download");
                                final InputStream finalIn = in;
                                alert_dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        download_file(finalIn, position, files);
                                        dialog.cancel();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void download_file(InputStream in,int position,String files[]){
        String ext_file_path=null;
        OutputStream out = null;
        ext_file_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File ext_file = new File(ext_file_path,files[position].toString());
        try {
            out=new FileOutputStream(ext_file);
            copyfile(in,out);
            in.close();
            in = null;
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),"Downloaded Successfully",Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),"Go to "+ext_file_path+" to open file",Toast.LENGTH_LONG).show();
    }

    public void copyfile(InputStream in, OutputStream out){
        byte[] buffer = new byte[1024];
        int read;
        try {
            while((read = in.read(buffer)) != -1){
                Toast.makeText(getApplicationContext(),String.valueOf(read),Toast.LENGTH_SHORT);
                out.write(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
