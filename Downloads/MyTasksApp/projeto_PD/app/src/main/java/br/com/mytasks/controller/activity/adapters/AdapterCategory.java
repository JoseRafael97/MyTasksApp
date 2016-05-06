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

import java.util.List;

import br.com.mytasks.R;
import br.com.mytasks.entities.Category;


public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.View_Holder> {

    private List<Category> list;
    private Context context;

    public AdapterCategory(List<Category> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_category, parent, false);
        return new View_Holder(v);


    }

    @Override
    public void onBindViewHolder(View_Holder holder, int position) {
        holder.title.setText(list.get(position).getName());
        int color = Color.parseColor(list.get(position).getColor());
        GradientDrawable bgShape = (GradientDrawable) holder.view.getBackground();
        bgShape.setColor(color);
        int fontColor;
        if(list.get(position).selected) {
             fontColor = context.getResources().getColor(R.color.white);
            holder.title.setTextColor(fontColor);
            holder.cardView.setBackgroundResource(R.color.colorPrimary);
        }else{
            fontColor = context.getResources().getColor(R.color.colorPrimary);
            holder.title.setTextColor(fontColor);
            holder.cardView.setBackgroundResource(R.color.white);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void addCategory(int position, Category category) {
        list.add(position, category);
        notifyItemInserted(position);
    }

    public Category findId(long id){
        for (Category category : list){
            if (category.getId() == id){
                return category;
            }
        }
        return null;
    }

    public void categoryUpdate(Category category){
        int position = list.indexOf(category);
        list.get(position).setName(category.getName());
        list.get(position).setColor(category.getColor());
        notifyItemChanged(position);
    }

    public void removeCategory(Category category) {
        int position = list.indexOf(category);
        list.remove(position);
        notifyItemRemoved(position);
    }

    public class View_Holder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView view;
        CardView cardView;


        public View_Holder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            view = (ImageView) itemView.findViewById(R.id.circle_shape);
        }
    }

}
