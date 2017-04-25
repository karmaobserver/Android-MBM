package com.mbm.ftn.mbm.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mbm.ftn.mbm.R;
import com.mbm.ftn.mbm.models.Number;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Makso on 4/25/2017.
 */

public class NumberAdapter extends BaseAdapter{

    private List<Number> numbers = new ArrayList<>();

    private final LayoutInflater layoutInflater;

    private final Context context;

    public NumberAdapter(Context context, List<Number> numbers) {
        this.numbers = numbers;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return numbers.size();
    }

    @Override
    public Object getItem(int position) {
        return numbers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            // create a new view holder since we don't have a view to reuse
            viewHolder = new ViewHolder();

            // inflate view item
            convertView = layoutInflater.inflate(R.layout.view_item_number, parent, false);

            // store the views in a view holder for later reuse
            viewHolder.text = (TextView) convertView.findViewById(R.id.text);
            viewHolder.textNumber = (TextView) convertView.findViewById(R.id.textNumber);

            // save view holder for later reuse
            convertView.setTag(viewHolder);
        } else {
            // we already have a view holder
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // get item
        final Number number = (Number) getItem(position);

        // populate view item with task's data
        viewHolder.text.setText(number.getText());
        viewHolder.textNumber.setText(number.getNumber());

        /*viewHolder.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Number currentNumber = numbers.get(position);

                // save the current state to task, needed because of cell reuse
                numbers.set(position, currentNumber);

            }
        });*/

        return convertView;
    }

    private static class ViewHolder {

        private TextView text;

        private TextView textNumber;
    }

    public List<Number> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Number> numbers) {
        this.numbers = numbers;
    }
}

