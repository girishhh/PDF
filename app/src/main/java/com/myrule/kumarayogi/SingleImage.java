package com.myrule.kumarayogi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.myrule.kumarayogi.pdf.R;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by girish on 25/2/17.
 */
public class SingleImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleimage);
        int res_id = getIntent().getIntExtra("image_resource_id",-9999);
        ImageView img_view = (ImageView)findViewById(R.id.single_image);
        img_view.setImageResource(res_id);

        PhotoViewAttacher pAttacher;
        pAttacher = new PhotoViewAttacher(img_view);
        pAttacher.update();
    }
}