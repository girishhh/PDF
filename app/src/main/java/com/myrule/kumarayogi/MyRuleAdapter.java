package com.myrule.kumarayogi;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myrule.kumarayogi.pdf.R;

/**
 * Created by girish on 24/2/17.
 */
public interface MyRuleAdapter {


    void onCreate(Bundle savedInstanceState);

    class CustomList extends ArrayAdapter<String> {
        private String[] names;
        private String[] desc;
        private Activity context;

        public CustomList(Activity context, String[] names, String[] desc) {
            super(context, R.layout.list_layout, names);
            this.context = context;
            this.names = names;
            this.desc = desc;
            //this.imageid = imageid;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.list_layout, null, true);
            TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
            TextView textViewDesc = (TextView) listViewItem.findViewById(R.id.textViewDesc);
            textViewName.setText(names[position]);
            textViewDesc.setText(desc[position]);
            return listViewItem;
        }

    }

    class CustomSlideAdapter extends PagerAdapter {


        Context context;
        int[] imageId = {R.drawable.image1,R.drawable.launcher,R.drawable.image3,R.drawable.image4,R.drawable.image5};

        public CustomSlideAdapter(Context context){
            this.context = context;

        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub

            LayoutInflater inflater = ((Activity)context).getLayoutInflater();

            View viewItem = inflater.inflate(R.layout.image_slide, container, false);
            ImageView imageView = (ImageView) viewItem.findViewById(R.id.imageView);
            imageView.setImageResource(imageId[position]);
            //TextView textView1 = (TextView) viewItem.findViewById(R.id.textView1);
            //textView1.setText("hi");
            ((ViewPager)container).addView(viewItem);

            return viewItem;
        }



        @Override
        public int getCount() {
            return imageId.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view==(View) object);
        }
        public void destroyItem(ViewGroup container, int position, Object object){
            container.removeView((LinearLayout)object);
        }
    }


}
