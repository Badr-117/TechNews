package com.example.gamingnews;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NewsAdapter extends ArrayAdapter<News> {

    private static final String LOG_TAG = NewsAdapter.class.getSimpleName();

    public NewsAdapter(Activity context, ArrayList<News> news){

        super(context, 0, news);
    }


    public View getView(int position,  View convertView, ViewGroup parent) {
        View gridItemView = convertView;
        if (gridItemView == null){
            gridItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        News currentNews = getItem(position);

        ImageView image = (ImageView) gridItemView.findViewById(R.id.imageV);
        if(currentNews != null){
            Picasso.with(getContext()).load(currentNews.getImageUrl()).into(image);
        }

        TextView title = (TextView) gridItemView.findViewById(R.id.title_tv);
        title.setText(currentNews.getTitle());

        TextView date = (TextView) gridItemView.findViewById(R.id.date_tv);
        date.setText(dateFormat(currentNews.getDate()));

        TextView source = (TextView) gridItemView.findViewById(R.id.source_tv);
        source.setText(currentNews.getSource());

        return gridItemView;

    }

    public String dateFormat(String input){
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.CANADA);
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.CANADA);
        String formattedDate = "";
        try{
            Date date = inputFormat.parse(input);
            formattedDate = outputFormat.format(date);
        }catch (ParseException e){
            e.printStackTrace();

        }

        return formattedDate;

    }


}
