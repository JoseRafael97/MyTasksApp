package br.com.mytasks.controller.activity.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.mytasks.R;
import br.com.mytasks.entities.Activity;
import br.com.mytasks.util.StatusActivityUtil;


public class AdapterActivityHome extends RecyclerView.Adapter<AdapterActivityHome.ViewHolder> implements Serializable{

    private List<Activity> list;
    private Context context;

    public AdapterActivityHome(List<Activity> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_home_activity, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StatusActivityUtil statusActivityUtil = new StatusActivityUtil();

        holder.name.setText(list.get(position).getName().toUpperCase());
        holder.nameCategory.setText(list.get(position).getCategory().getName());

        int color = Color.parseColor(list.get(position).getCategory().getColor());
        holder.cardView.setBackgroundColor(color);
    }


    @Override
    public int getItemCount() {
        if(list == null){
            list = new ArrayList<>();
        }
        return list.size();
    }

    public void addActivity(int position, Activity activity) {
        list.add(position, activity);
        notifyItemInserted(position);
    }

    public void removeActivity(Activity activity) {
        int position = list.indexOf(activity);
        list.remove(position);
        notifyItemRemoved(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;
        TextView nameCategory;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_activity_home);
            name = (TextView) itemView.findViewById(R.id.tv_activity_home);
            nameCategory = (TextView) itemView.findViewById(R.id.tv_category_home);
            cardView = (CardView) itemView.findViewById(R.id.card_view_activity_home);
        }
    }
}
