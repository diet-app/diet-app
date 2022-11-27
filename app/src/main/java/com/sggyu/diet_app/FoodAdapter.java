package com.sggyu.diet_app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> implements Filterable
{
    private List<Food> foodList;
    private Activity context;
    private FoodDB database;
    private List<Food> filteredFoodList;

    @Override
    public Filter getFilter() {
        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String str = charSequence.toString();
                if(str.isEmpty()){
                    filteredFoodList = foodList;
                }
                else{
                    List<Food> filteringList = new ArrayList<>();
                    for (Food item : foodList){
                        if(item.name.contains(str))
                            filteringList.add(item);
                    }
                    filteredFoodList = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredFoodList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                filteredFoodList.clear();
//                filteredFoodList.addAll((List)filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    public interface ItemClickListener{
        void onItemClicked(View view, int position);
    }
//    private AdapterView.OnItemClickListener itemClickListener;
//    public void setOnItemClickListener (AdapterView.OnItemClickListener listener){
//        itemClickListener = listener;
//    }
    public FoodAdapter(Activity context, List<Food> dataList)
    {
        this.context = context;
        this.foodList = dataList;
        this.filteredFoodList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodAdapter.ViewHolder holder, int position)
    {
        final Food data = filteredFoodList.get(position);
        database = FoodDB.getInstance(context);
        holder.textView.setText(data.name);
        holder.kcal.setText(data.kcal+" kcal");
        holder.foodView.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   Context context = v.getContext();
                                                   Intent intent = new Intent(v.getContext(), EnterDiet2.class);
                                                   v.getContext().startActivity(intent);
                                               }
                                           });
//        holder.btEdit.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Food mainData = foodList.get(holder.getAdapterPosition());
//
//                final int sID = mainData.fid;
//                String sText = mainData.name;
//
//                final Dialog dialog = new Dialog(context);
//                dialog.setContentView(R.layout.dialog_update);
//
//                int width = WindowManager.LayoutParams.MATCH_PARENT;
//                int height = WindowManager.LayoutParams.WRAP_CONTENT;
//
//                dialog.getWindow().setLayout(width, height);
//
//                dialog.show();
//
//                final EditText editText = dialog.findViewById(R.id.dialog_edit_text);
//                Button bt_update = dialog.findViewById(R.id.bt_update);
//
//                editText.setText(sText);
//
//                bt_update.setOnClickListener(new View.OnClickListener()
//                {
//                    @Override
//                    public void onClick(View v)
//                    {
//                        dialog.dismiss();
//                        String uText = editText.getText().toString().trim();
//
//                        database.foodDao().update(sID, uText);
//
//                        foodList.clear();
//                        foodList.addAll(database.foodDao().getAll());
//                        notifyDataSetChanged();
//                    }
//                });
//            }
//        });
//
//        /* 삭제 클릭 */
//        holder.btDelete.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Food mainData = foodList.get(holder.getAdapterPosition());
//
//                database.foodDao().delete(mainData);
//
//                int position = holder.getAdapterPosition();
//                foodList.remove(position);
//                notifyItemRemoved(position);
//                notifyItemRangeChanged(position, foodList.size());
//            }
//        });
    }

    @Override
    public int getItemCount()
    {
        return filteredFoodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView textView,kcal;
        ImageView btEdit, btDelete;
        LinearLayout foodView;
        ItemClickListener itemClickListener;
        public ViewHolder(@NonNull View view)
        {
            super(view);
            textView = view.findViewById(R.id.text_view);
            kcal = view.findViewById(R.id.textView2);
            foodView = view.findViewById(R.id.foodView);
//            btEdit = view.findViewById(R.id.bt_edit);
//            btDelete = view.findViewById(R.id.bt_delete);
        }
        @Override
        public void onClick(View v){
            this.itemClickListener.onItemClicked(v, getLayoutPosition());
        }
    }
}