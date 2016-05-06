package br.com.mytasks.controller.activity.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.io.Serializable;
import java.util.List;

import br.com.mytasks.R;
import br.com.mytasks.entities.Event;
import br.com.mytasks.util.DateConversor;


public class AdapterEvent extends RecyclerView.Adapter<AdapterEvent.ViewHolder> implements Serializable {

    private List<Event> events;
    private Context context;


    public AdapterEvent(List<Event> events, Context context){
        this.events = events;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_event, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(events.get(position).getName());
        holder.initialDate.setText(DateConversor.dateAndHourToStringConversor(events.get(position).getInitialDate()));
        holder.finalDate.setText(DateConversor.dateAndHourToStringConversor(events.get(position).getFinalDate()));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void addEvent(int position, Event event) {
        events.add(position, event);
        notifyItemInserted(position);
    }

    public void removeEvent(Event event) {
        int position = events.indexOf(event);
        events.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView initialDate;
        TextView finalDate;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_event_name);
            finalDate = (TextView) itemView.findViewById(R.id.tv_final_date_event);
            initialDate = (TextView) itemView.findViewById(R.id.tv_date_initial_event);
        }
    }
}
