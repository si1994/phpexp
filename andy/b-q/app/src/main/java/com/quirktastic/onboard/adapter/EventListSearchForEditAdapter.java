package com.quirktastic.onboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.quirktastic.R;
import com.quirktastic.dashboard.profile.model.profiledetails.EventItem;

import java.util.ArrayList;

public class EventListSearchForEditAdapter extends ArrayAdapter<EventItem> {

    ArrayList<EventItem> customers, tempEventItem, suggestions;

    public EventListSearchForEditAdapter(Context context, ArrayList<EventItem> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.customers = objects;
        this.tempEventItem = new ArrayList<EventItem>(objects);
        this.suggestions = new ArrayList<EventItem>(objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EventItem eventListItem = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_event_search, parent, false);
        }
        TextView tvEventItem = (TextView) convertView.findViewById(R.id.tvEventItem);
        if (tvEventItem != null && eventListItem.getTitle() != null) {
            tvEventItem.setText(eventListItem.getTitle());
        }

        return convertView;
    }


    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            EventItem eventListItem = (EventItem) resultValue;
            return eventListItem.getTitle();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (EventItem eventListItem : tempEventItem) {
                    if (eventListItem.getTitle().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(eventListItem);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<EventItem> c = (ArrayList<EventItem>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (EventItem cust : c) {
                    add(cust);
                    notifyDataSetChanged();
                }
            }
        }
    };


}
