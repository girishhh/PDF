package com.myrule.kumarayogi;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by girish on 12/3/17.
 */
public class DownloadTask extends AsyncTask<String, Void, String> {
    Context context;
    ProgressDialog progress;
    String ext_file_path=null;

    DownloadTask(Context context_from_pdf){
        context = context_from_pdf;
    }

    @Override
    protected void onPreExecute(){
        progress = null;
        progress = new ProgressDialog(context);
        progress.setMessage("Downloading File :) ");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(false);
        progress.setProgress(0);
        progress.show();
    }

    public void copyfile(InputStream in,OutputStream out){
        int input_stream_size = 0;
        try {
            input_stream_size = in.available()/1024;
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] buffer = new byte[1024];
        int read;
        int counter=0;
        float progress_value;
        try {
            while((read = in.read(buffer)) != -1){
                out.write(buffer, 0, read);
                counter = counter +1 ;
                progress_value = counter/(float)input_stream_size;
                progress.setProgress(Math.round(progress_value*100));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(String status){
        progress.setMessage("File download complete");
        progress.dismiss();
        Toast.makeText(context,"Downloaded Successfully",Toast.LENGTH_LONG).show();
        Toast.makeText(context,"Go to "+ext_file_path+" to open file",Toast.LENGTH_LONG).show();
    }

    protected String doInBackground(String... params) {
        OutputStream out = null;
        InputStream in =null;
        ext_file_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        String file_name=null;
        if(params[0].contains("download/")){
            file_name = params[0];
            file_name = file_name.replace("download/","");
        }
        else{
            file_name = params[0];
        }
        File ext_file = new File(ext_file_path,file_name);

        try {
            in = context.getAssets().open(params[0].toString());
            out=new FileOutputStream(ext_file);
            copyfile(in,out);
            in.close();
            in = null;
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "1";
    }
}
