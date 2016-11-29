package com.dgu.table.univ.univtable;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import crawl.AttendInfo;
import crawl.ClassInfo;

public class AttendListAdapter extends RecyclerView.Adapter<AttendListAdapter.ViewHolder> {

    public static final int HEADER = 3, DEFAULT = 0;
    public Context mContext = null;
    public List<AttendInfo> mListData = new ArrayList<>();
    public int item_layout;

    public AttendListAdapter(Context mContext, int item_layout) {
        super();
        this.mContext = mContext;
        this.item_layout = item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_attend, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        AttendInfo mData = mListData.get(position);
        holder._subject.setText(mData.subject);
        holder._date.setText(mData.rawDate);
        holder.cardview.setOnClickListener(new CardView.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final AssignInfo mData = staticInfo.mAdapter.mListData.get(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView _subject, _date;
        public CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            _date = (TextView)itemView.findViewById(R.id.date);
            _subject = (TextView)itemView.findViewById(R.id.subject);
            cardview = (CardView)itemView.findViewById(R.id.cardview);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(AttendInfo addInfo){
        mListData.add(addInfo);
    }
    public void dataChange(){
        this.notifyDataSetChanged();
    }

}