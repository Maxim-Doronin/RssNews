package com.maximdoronin.rssnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by maximdoronin on 12/26/17.
 */

public class SubjectListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    public SubjectListAdapter(Context context) {
        this.mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return NewsSubject.values().length;

    }

    @Override
    public Object getItem(int position) {
        return NewsSubject.values()[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.subject_layout, parent, false);
        }

        NewsSubject subject = NewsSubject.values()[position];
        SubjectListAdapter.ViewHolder holder = new SubjectListAdapter.ViewHolder(view);

        holder.subjectTextView.setText(subject.toString());

        return view;
    }

    private class ViewHolder {

        TextView subjectTextView;

        ViewHolder(View item) {
            subjectTextView = (TextView) item.findViewById(R.id.subjectTextView);
        }
    }
}
