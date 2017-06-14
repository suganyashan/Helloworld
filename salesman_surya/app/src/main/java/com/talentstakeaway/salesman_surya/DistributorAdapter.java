package com.talentstakeaway.salesman_surya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.data;
import static android.R.attr.id;
import static com.talentstakeaway.salesman_surya.R.id.distributor_search;
import static com.talentstakeaway.salesman_surya.R.id.salesman_search;

/**
 * Created by EMDnew35 on 12-Jun-17.
 */

public class DistributorAdapter extends BaseAdapter implements Filterable {

    private ArrayList<Report> mOriginalValues; // Original Values
    private ArrayList<Report> mDisplayedValues;
    LayoutInflater inflater;


    public DistributorAdapter(Context context, ArrayList<Report> mProductArrayList){
        this.mOriginalValues = mProductArrayList;
        this.mDisplayedValues = mProductArrayList;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mDisplayedValues.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;


        if (convertView == null) {

            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_layout, null);
            holder.date = (TextView) convertView.findViewById(R.id.Date);
            holder.distributor = (TextView) convertView.findViewById(R.id.distributer);
            holder.salesman = (TextView) convertView.findViewById(R.id.salesman);
            holder.task_location = (TextView) convertView.findViewById(R.id.Task_Location);
            holder.start_time = (TextView) convertView.findViewById(R.id.start_time);
            holder.end_time = (TextView) convertView.findViewById(R.id.end_time);
            holder.task_description = (TextView) convertView.findViewById(R.id.task_description);
            holder.status = (TextView) convertView.findViewById(R.id.status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.date.setText(mDisplayedValues.get(position).getDate());
        holder.distributor.setText(mDisplayedValues.get(position).getDistributor());
        holder.salesman.setText(mDisplayedValues.get(position).getSalesman());
        holder.task_location.setText(mDisplayedValues.get(position).getTask_location());
        holder.start_time.setText(mDisplayedValues.get(position).getStart_time());
        holder.end_time.setText(mDisplayedValues.get(position).getEnd_time());
        holder.task_description.setText(mDisplayedValues.get(position).getTask_description());
        holder.status.setText(mDisplayedValues.get(position).getStatus());


        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                mDisplayedValues = (ArrayList<Report>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();// Holds the results of a filtering operation in values
                ArrayList<Report> FilteredArrList = new ArrayList<Report>();
                    if (mOriginalValues == null) {
                        mOriginalValues = new ArrayList<Report>(mDisplayedValues); // saves the original data in mOriginalValues
                    }

                    /********
                     *
                     *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                     *  else does the Filtering and returns FilteredArrList(Filtered)
                     *
                     ********/
                    if (constraint == null || constraint.length() == 0) {

                        // set the Original result to return
                        results.count = mOriginalValues.size();
                        results.values = mOriginalValues;
                    } else {
                        constraint = constraint.toString().toLowerCase();
                        for (int i = 0; i < mOriginalValues.size(); i++) {
                            String distributor = mOriginalValues.get(i).getDistributor();
                            if ((distributor.toLowerCase().startsWith(constraint.toString()))) {
                               FilteredArrList.add(new Report(mOriginalValues.get(i).getSl(), mOriginalValues.get(i).getDate(), mOriginalValues.get(i).getDistributor(), mOriginalValues.get(i).getSalesman(), mOriginalValues.get(i).getTask_location(), mOriginalValues.get(i).getStart_time(), mOriginalValues.get(i).getEnd_time(), mOriginalValues.get(i).getTask_description(), mOriginalValues.get(i).getStatus()));
                            }
                        }
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }

                return results;
            }
        };
        return filter;
    }
    private class ViewHolder{
        TextView date;
        TextView distributor;
        TextView salesman;
        TextView task_location;
        TextView start_time;
        TextView end_time;
        TextView task_description;
        TextView status;
    }
}


