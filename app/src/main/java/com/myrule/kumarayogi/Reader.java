package com.myrule.kumarayogi;


import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;


import com.myrule.kumarayogi.pdf.R;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

public class Reader extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        PDFView pdfView= (PDFView)findViewById(R.id.pdfView);

        pdfView.fromAsset("test.pdf")
                .defaultPage(0)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableAnnotationRendering(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }
}
