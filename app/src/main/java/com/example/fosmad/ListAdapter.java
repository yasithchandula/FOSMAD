package com.example.fosmad;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter {

    private final Activity mContext;
    List<Member> memberList;
  //member check list
    public ListAdapter(Activity mContext, List<Member>memberList) {
        super(mContext, R.layout.list_item, memberList);
        this.mContext = mContext;
        this.memberList = memberList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.list_item,null,true);
//list details
//        TextView tName = listItemView.findViewById(R.id.tname);
//        TextView tAddress = listItemView.findViewById(R.id.taddress);
//        TextView tConNo = listItemView.findViewById(R.id.tConNo);
//
//        Member member= memberList.get(position);
//
//        tName.setText(member.getName());
//        tAddress.setText(member.getAddress());
//        tConNo.setText(member.getConNo());
        return listItemView;
    }
}



