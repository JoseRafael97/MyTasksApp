package br.com.mytasks.controller.activity.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import br.com.mytasks.R;
import br.com.mytasks.entities.Activity;
import br.com.mytasks.exception.ActivityManagerException;
import br.com.mytasks.util.DateConversor;
import br.com.mytasks.util.StatusActivityUtil;


public class AdapterActivity extends RecyclerView.Adapter<AdapterActivity.ViewHolder> implements Serializable{

    private List<Activity> list;
    private Context context;

    public AdapterActivity(List<Activity> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_activity, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        StatusActivityUtil statusActivityUtil = new StatusActivityUtil();

        try {
            if(statusActivityUtil.getAtivitiesStatus(list.get(position)) == 1){
                holder.imageView.setImageResource(R.drawable.ic_circle_done);
            } else if(statusActivityUtil.getAtivitiesStatus(list.get(position)) == -1){
                holder.imageView.setImageResource(R.drawable.ic_circle_fail);
            }else{
                holder.imageView.setImageResource(R.drawable.ic_circle_current);
            }
        } catch (ActivityManagerException e) {
            e.printStackTrace();
        }

        holder.name.setText(list.get(position).getName().toUpperCase());
        holder.nameCategory.setText(list.get(position).getCategory().getName());
        holder.deadLine.setText(DateConversor.dateToStringConversor(list.get(position).getDeadLine()));
        holder.plannedHours.setText(DateConversor.hourStringConversor(list.get(position).getPlannedHours()));

        int color = Color.parseColor(list.get(position).getCategory().getColor());
        GradientDrawable bgShape = (GradientDrawable) holder.imageViewCircle.getBackground();
        bgShape.setColor(color);

        int fontColor;
        if(list.get(position).selected) {
            fontColor = context.getResources().getColor(R.color.white);
            holder.name.setTextColor(fontColor);
            holder.deadLine.setTextColor(fontColor);
            holder.nameCategory.setTextColor(fontColor);
            holder.plannedHours.setTextColor(fontColor);
            holder.lbHourPlaned.setTextColor(fontColor);
            holder.lbDeadLine.setTextColor(fontColor);
            holder.cardView.setBackgroundResource(R.color.colorPrimary);
        }else{
            fontColor = context.getResources().getColor(R.color.colorPrimary);
            holder.name.setTextColor(fontColor);
            holder.deadLine.setTextColor(fontColor);
            holder.nameCategory.setTextColor(fontColor);
            holder.plannedHours.setTextColor(fontColor);
            holder.lbHourPlaned.setTextColor(fontColor);
            holder.lbDeadLine.setTextColor(fontColor);
            holder.cardView.setBackgroundResource(R.color.white);
        }

    }

    @Override
    public int getItemCount() {
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
        TextView deadLine;
        TextView plannedHours;
        CardView cardView;
        TextView lbHourPlaned;
        TextView lbDeadLine;
        TextView repetitions;
        ImageView imageViewCircle;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_activity_home);
            name = (TextView) itemView.findViewById(R.id.tv_activity_home);
            nameCategory = (TextView) itemView.findViewById(R.id.tv_category_home);
            deadLine = (TextView) itemView.findViewById(R.id.tv_deadline_home_detail);
            plannedHours = (TextView) itemView.findViewById(R.id.tv_planned_hours_home);
            cardView = (CardView) itemView.findViewById(R.id.card_view_activity);
            lbHourPlaned =(TextView) itemView.findViewById(R.id.tv_label_planned_hours_home);
            lbDeadLine = (TextView) itemView.findViewById(R.id.tv_label_deadline_home);
            imageViewCircle = (ImageView) itemView.findViewById(R.id.color_shape);
        }
    }
}
