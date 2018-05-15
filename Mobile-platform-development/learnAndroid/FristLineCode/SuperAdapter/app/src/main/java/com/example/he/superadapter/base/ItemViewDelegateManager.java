package com.example.he.superadapter.base;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.example.he.superadapter.ViewHolder;

/**
 * 一个adapter中可能有多个不同type的item,item的管理器
 * Created by he on 2017/5/9.
 */

public class ItemViewDelegateManager<T> {
    private SparseArray<ItemViewDelegate<T>> delegateMap = new SparseArray<>();

    /**
     * 获取adapter中添加的不同type的item数量
     *
     * @return
     */
    public int getDifferentDelegatesCount() {
        return delegateMap.size();
    }

    public ItemViewDelegateManager<T> addDelegate(@NonNull ItemViewDelegate<T> delegate) {
        if (delegate == null)
            throw new NullPointerException("delegate不能为null");

        int viewType = delegateMap.size();
        delegateMap.put(viewType, delegate);
        viewType++;
        return this;
    }

    public ItemViewDelegateManager<T> addDelegate(int viewType, @NonNull ItemViewDelegate<T> delegate) {
        if (delegate == null) {
            throw new NullPointerException("delegate不能为null");
        }
        if (delegateMap.get(viewType) != null) {
            throw new IllegalArgumentException("不能重复添加delegate");
        }
        delegateMap.put(viewType, delegate);
        return this;
    }

    public ItemViewDelegateManager<T> removeDelegate(@NonNull ItemViewDelegate<T> delegate) {
        if (delegate == null) {
            throw new NullPointerException("delegate不能为null");
        }
        int index = delegateMap.indexOfValue(delegate);
        if (index >= 0)
            delegateMap.removeAt(index);
        return this;
    }

    public ItemViewDelegateManager<T> removeDelegate(int itemType) {
        int index = delegateMap.indexOfKey(itemType);
        if (index >= 0) {
            delegateMap.removeAt(index);
        }
        return this;
    }

    public int getItemViewType(T item, int position) {
        int delegatesCount = delegateMap.size();
        for (int i = 0; i < delegatesCount; i++) {
            ItemViewDelegate<T> delegate = delegateMap.valueAt(i);
            if (delegate.isForViewType(item, position)) {
                return delegateMap.keyAt(i);
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }


    public void convert(ViewHolder holder, T item, int position) {
        int delegatesCount = delegateMap.size();
        for (int i = 0; i < delegatesCount; i++) {
            ItemViewDelegate<T> delegate = delegateMap.valueAt(i);

            if (delegate.isForViewType(item, position)) {
                delegate.convert(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegateManager added that matches position=" + position + " in data source");
    }

    public int getItemViewLayoutId(@NonNull int viewType) {
        return delegateMap.get(viewType).getItemViewLayoutId();
    }

    public int getItemViewType(@NonNull ItemViewDelegate itemViewDelegate) {
        return delegateMap.indexOfValue(itemViewDelegate);
    }

    public ItemViewDelegate getItemViewDelegate(T item, int position) {
        int delegatesCount = delegateMap.size();
        for (int i = delegatesCount - 1; i >= 0; i--) {
            ItemViewDelegate<T> delegate = delegateMap.valueAt(i);
            if (delegate.isForViewType(item, position)) {
                return delegate;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }

    public int getItemViewLayoutId(T item, int position) {
        return getItemViewDelegate(item, position).getItemViewLayoutId();
    }

}
