package com.lee.easytodo.view.calendarview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangjianfei
 * @com www.weicheche.cn
 * @package com.lee.androidxgroup.view.original_java
 * @create 2019-08-19.
 * @function
 */
abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter {

    @SuppressWarnings("all")
    LayoutInflater mInflater;
    private List<T> mItems;
    private BaseRecyclerAdapter.OnItemClickListener onItemClickListener;
    private BaseRecyclerAdapter.OnClickListener onClickListener;
    Context mContext;

    BaseRecyclerAdapter(Context context) {
        mContext = context;
        this.mItems = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
        onClickListener = new BaseRecyclerAdapter.OnClickListener() {
            @Override
            public void onClick(int position, long itemId) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(position, itemId);
            }
        };

    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final RecyclerView.ViewHolder holder = onCreateDefaultViewHolder(parent, viewType);
        if (holder != null) {
            holder.itemView.setTag(holder);
            holder.itemView.setOnClickListener(onClickListener);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        onBindViewHolder(holder, mItems.get(position), position);
    }

    abstract RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type);

    abstract void onBindViewHolder(RecyclerView.ViewHolder holder, T item, int position);

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    void setOnItemClickListener(BaseRecyclerAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @SuppressWarnings("unused")
    void addAll(List<T> items) {
        if (items != null && items.size() > 0) {
            mItems.addAll(items);
            notifyItemRangeInserted(mItems.size(), items.size());
        }
    }

    final void addItem(T item) {
        if (item != null) {
            this.mItems.add(item);
            notifyItemChanged(mItems.size());
        }
    }

    @SuppressWarnings("unused")
    final List<T> getItems() {
        return mItems;
    }


    final T getItem(int position) {
        if (position < 0 || position >= mItems.size())
            return null;
        return mItems.get(position);
    }

    static abstract class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            onClick(holder.getAdapterPosition(), holder.getItemId());
        }

        public abstract void onClick(int position, long itemId);
    }


    interface OnItemClickListener {
        void onItemClick(int position, long itemId);
    }
}
