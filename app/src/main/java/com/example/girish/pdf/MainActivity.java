package com.example.girish.pdf;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import uk.co.senab.photoview.PhotoViewAttacher;


public class MainActivity extends AppCompatActivity {
    int count = 0;
    int total_pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        readpdf_by_download();

        read_pdf_from_assets(count);

        Button left_button = (Button)findViewById(R.id.left_button);
        Button right_button = (Button)findViewById(R.id.right_button);
        total_pages = get_total_pages();

        left_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(count==0){
                    Toast.makeText(getApplicationContext(),"No more pages",Toast.LENGTH_SHORT).show();
                }else{
                    read_pdf_from_assets(--count);
                }
            }
        });

        right_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(count==(total_pages-1)){
                    Toast.makeText(getApplicationContext(),"No more pages",Toast.LENGTH_SHORT).show();
                }else{
                    read_pdf_from_assets(++count);
                }
            }
        });

    }

    public int get_total_pages(){
        int page_count=0;
        AssetManager assetManager = getAssets();
        AssetFileDescriptor assetFileDescriptor = null;
        try {
            assetFileDescriptor = assetManager.openFd("test.pdf");
            ParcelFileDescriptor fileDescriptor_test = assetFileDescriptor.getParcelFileDescriptor();
            PdfRenderer pdfRenderer = new PdfRenderer(fileDescriptor_test);
            page_count = pdfRenderer.getPageCount();
            pdfRenderer.close();
            assetFileDescriptor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return page_count;
    }

    public void read_pdf_from_assets(int index){

        AssetManager assetManager = getAssets();
        try {

            AssetFileDescriptor assetFileDescriptor = assetManager.openFd("test.pdf");
            ParcelFileDescriptor fileDescriptor_test = assetFileDescriptor.getParcelFileDescriptor();


            PdfRenderer pdfRenderer = new PdfRenderer(fileDescriptor_test);

            PdfRenderer.Page rendererPage = pdfRenderer.openPage(index);

            int rendererPageWidth = 2048;
            int rendererPageHeight = 2048;

            Bitmap bitmap = Bitmap.createBitmap(
                    rendererPageWidth,
                    rendererPageHeight,
                    Bitmap.Config.ARGB_8888);

            rendererPage.render(bitmap, null, null,
                    PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

            ImageView pdfView = (ImageView)findViewById(R.id.pdfview);

            PhotoViewAttacher pAttacher;
            pAttacher = new PhotoViewAttacher(pdfView);
            pAttacher.update();


            pdfView.setImageBitmap(bitmap);
            rendererPage.close();

            pdfRenderer.close();
            assetFileDescriptor.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void readpdf_by_download(){
        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);

            URL url = new URL("http://cir.dcs.uni-pannon.hu/cikkek/conceptual_design.pdf");
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.connect();

            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File (sdCard.getAbsolutePath() + "/test1/test2");
            dir.mkdirs();

            File file = new File(dir, "filename.pdf");
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            InputStream inputStream = urlConnection.getInputStream();

            int totalSize = urlConnection.getContentLength();

            byte[] buffer = new byte[1024*1024];
            int bufferLength = 0;
            while((bufferLength = inputStream.read(buffer))>0 ){
                fileOutputStream.write(buffer, 0, bufferLength);
            }
            fileOutputStream.close();


            File sd_file = new File(dir, "filename.pdf");

            ParcelFileDescriptor fd = ParcelFileDescriptor.open(sd_file,ParcelFileDescriptor.MODE_READ_ONLY);

            PdfRenderer renderer = new PdfRenderer(fd);

            PdfRenderer.Page page_property = renderer.openPage(0);
            int rendererPageWidth = page_property.getWidth();
            int rendererPageHeight = page_property.getHeight();

            final int pageCount = renderer.getPageCount();

            Bitmap bitmap = Bitmap.createBitmap(
                    rendererPageWidth,
                    rendererPageHeight,
                    Bitmap.Config.ARGB_8888);

            page_property.render(bitmap, null, null,
                    PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

            ImageView pdfView = (ImageView)findViewById(R.id.pdfview);

            pdfView.setImageBitmap(bitmap);
            page_property.close();

            fd.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
