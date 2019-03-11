package com.quirktastic.onboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.quirktastic.R;
import com.quirktastic.onboard.model.events.EventListItem;

import java.util.ArrayList;

public class EventListSearchAdapter extends ArrayAdapter<EventListItem> {

    ArrayList<EventListItem> customers, tempEventListItem, suggestions;

    public EventListSearchAdapter(Context context, ArrayList<EventListItem> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.customers = objects;
        this.tempEventListItem = new ArrayList<EventListItem>(objects);
        this.suggestions = new ArrayList<EventListItem>(objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EventListItem eventListItem = getItem(position);
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
            EventListItem eventListItem = (EventListItem) resultValue;
            return eventListItem.getTitle();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (EventListItem eventListItem : tempEventListItem) {
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
            ArrayList<EventListItem> c = (ArrayList<EventListItem>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (EventListItem cust : c) {
                    add(cust);
                    notifyDataSetChanged();
                }
            }
        }
    };


}
