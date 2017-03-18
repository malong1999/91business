package com.yhjiankang.business.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.internal.MDAdapter;
import com.yhjiankang.business.R;


public class MaterialSimpleListAdapter extends ArrayAdapter<MaterialSimpleListItem> implements MDAdapter {

    private MaterialDialog dialog;

    public MaterialSimpleListAdapter(Context context) {
        super(context, R.layout.md_simplelist_item, android.R.id.title);
    }

    @Override
    public void setDialog(MaterialDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int index, View convertView, ViewGroup parent) {
        final View view = super.getView(index, convertView, parent);
        if (dialog != null) {
            final MaterialSimpleListItem item = getItem(index);
            TextView tv = (TextView) view.findViewById(android.R.id.title);
            tv.setTextColor(dialog.getBuilder().getItemColor());
            tv.setText(item.getContent());
            dialog.setTypeface(tv, dialog.getBuilder().getRegularFont());
        }
        return view;
    }

}