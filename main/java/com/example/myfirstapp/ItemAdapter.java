package com.example.myfirstapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends ArrayAdapter<Checks> {


    LayoutInflater mInflater;
    String[] items;
    String[] bushido;
    String[] description;
    Integer scr;

    private List<Checks> itemList = new ArrayList<Checks>();



    public ItemAdapter(Context c,List<Checks> rList, String[] i , String [] p , String[] d, Integer sc, TextView scc) {
        super(c, android.R.layout.simple_list_item_1);
        items = i;
        bushido = p;
        description = d;
        scr = sc;
        this.itemList = rList;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void updateItems(List<Checks> rList, String[] i ,String [] p ,String[] d) {
            items = i;
            bushido = p;
            description = d;
            this.itemList = rList;

            notifyDataSetChanged();

    }

    @Override
    public void add(Checks object) {
        itemList.add(object);
        super.add(object);
    }

    public ArrayList<Checks> getList() {
        return new ArrayList<Checks>();
    }

    private class ViewHolder {
        TextView txtItemName;
    }

    public int getCountItem() {
        return this.itemList.size();
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Checks getItem(int i) {
        return this.itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.my_listview_detail, null); }

        convertView.setBackgroundColor(Color.CYAN);

        TextView nameTextView = convertView.findViewById(R.id.NameTextView);
        TextView bushidoTextView = convertView.findViewById(R.id.bushidoTextView);
        //TextView descriptionTextView = convertView.findViewById(R.id.descriptionTextView);
        holder.txtItemName = convertView.findViewById(R.id.finalScoreView);
        convertView.setTag(holder);

        String name = items[i];
        //String desc = description[i];
        String bush = bushido[i];

        holder = (ViewHolder) convertView.getTag();

        Checks item = getItem(i);

        holder.txtItemName.setText(item.getItemName());


        // TextView finalscorre = convertView.findViewById(R.id.finalScoreView);
        // finalscorre.setText(String.valueOf(scr));

        nameTextView.setText(name);
        //descriptionTextView.setText(desc);
        bushidoTextView.setText(bush);

        return convertView;
    }

}

