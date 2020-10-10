package com.example.toaccountornot.ui.account;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toaccountornot.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> mFruitList;
    private MyViewClickListener myViewClickListener;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View fruitView;
        ImageView fruitImage;
        TextView fruitName;

        public  ViewHolder(View view) {
            super(view);
            fruitView = view;
            fruitImage = view.findViewById(R.id.category_image);
            fruitName = view.findViewById(R.id.category_name);
        }
    }

    public CategoryAdapter(List<Category> categoryList) {
        mFruitList = categoryList;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category, parent, false);
        final ViewHolder  holder = new ViewHolder(view);
        holder.fruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myViewClickListener != null) {
                    int position = holder.getAdapterPosition();
                    Category category = mFruitList.get(position);
                    myViewClickListener.callKeyboard(category.getName());
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category fruit = mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        holder.fruitImage.setImageResource(fruit.getImageId());
    }


    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
    //回调监听点击，调用软键盘
    public void setMyViewClickListener(MyViewClickListener myViewClickListener) {
        this.myViewClickListener = myViewClickListener;
    }
    public interface MyViewClickListener{
        void callKeyboard(String fisrtCategory);
    }
}
