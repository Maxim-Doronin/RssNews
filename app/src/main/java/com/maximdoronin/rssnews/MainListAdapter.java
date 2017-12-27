package com.maximdoronin.rssnews;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by maximdoronin on 12/26/17.
 */

public class MainListAdapter extends BaseAdapter {
    private List<RssItem> mRssItems;
    private LayoutInflater mInflater;
    private Context mContext;

    public MainListAdapter(Context context, List<RssItem> items) {
        this.mRssItems = items;
        this.mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mRssItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mRssItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = mInflater.inflate(R.layout.item_layout, parent, false);
        }

        RssItem rssItem = mRssItems.get(position);
        ViewHolder holder = new ViewHolder(view);

        holder.titleTextView.setText(rssItem.getTitle());

        holder.descriptionTextView.setText(rssItem.getDescription());
        holder.descriptionTextView.setTextColor(
                ContextCompat.getColor(mContext, R.color.description_text));

        holder.dateTextView.setTextColor(ContextCompat.getColor(mContext, R.color.description_text));
        holder.dateTextView.setText(new SimpleDateFormat("HH:mm").format(rssItem.getPubDate()));

        holder.linkTextView.setTextColor(ContextCompat.getColor(mContext, R.color.description_text));
        String pattern = "cl4url=(\\S[^%]*)%";
        Pattern regexp = Pattern.compile(pattern);
        Matcher matcher = regexp.matcher(rssItem.getLink());
        if (matcher.find()) {
            holder.linkTextView.setText(matcher.group(1));
        } else {
            holder.linkTextView.setText(rssItem.getLink());
        }

        return view;
    }

    private class ViewHolder{

        TextView linkTextView;
        TextView titleTextView;
        TextView descriptionTextView;
        TextView dateTextView;

        ViewHolder(View item){
            linkTextView = (TextView) item.findViewById(R.id.linkTextView);
            titleTextView = (TextView) item.findViewById(R.id.titleTextView);
            descriptionTextView = (TextView) item.findViewById(R.id.descriptionTextView);
            dateTextView = (TextView) item.findViewById(R.id.dateTextView);
        }
    }
}
