package com.jli.gardener.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by john on 5/21/16.
 */
public class HubCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public View mRootView;
    private final IClickListener mClickListener;

    public HubCardViewHolder(View v, IClickListener mClickListener) {
        super(v);
        mRootView = v;
        this.mClickListener = mClickListener;
        mRootView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mClickListener.onClick(this, getAdapterPosition());
    }

    public interface IClickListener {
        void onClick(HubCardViewHolder viewHolder, int position);
    }
}
