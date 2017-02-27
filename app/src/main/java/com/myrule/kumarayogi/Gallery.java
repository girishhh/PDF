package com.myrule.kumarayogi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.myrule.kumarayogi.pdf.R;

/**
 * Created by girish on 25/2/17.
 */
public class Gallery extends AppCompatActivity {

    public Integer[] mThumbIds = {R.drawable.gallery01,R.drawable.gallery02,R.drawable.gallery03,R.drawable.gallery04,R.drawable.gallery05,
            R.drawable.gallery06,R.drawable.gallery07,R.drawable.gallery08,R.drawable.gallery09,R.drawable.gallery11,
            R.drawable.gallery12,R.drawable.gallery13,R.drawable.gallery14,R.drawable.gallery15,R.drawable.gallery16,R.drawable.gallery17,
            R.drawable.gallery18};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id)
            {
                Intent intent = new Intent(getApplicationContext(),SingleImage.class);
                intent.putExtra("image_resource_id",mThumbIds[position]);
                startActivity(intent);
            }
        });


    }
    public class ImageAdapter extends BaseAdapter {


        private Context mContext;
        public int getCount() {
            return mThumbIds.length;
        }
        public Object getItem(int position) {
            return mThumbIds[position];
        }
        public long getItemId(int position) {
            return 0;
        }
        public ImageAdapter(Context c) {
            mContext = c;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null){
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(320,320));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            }
            else{
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

    }



}
