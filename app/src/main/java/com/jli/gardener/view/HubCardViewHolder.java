package com.jli.gardener.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by john on 5/21/16.
 */
// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public class HubCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    // each data item is just a string in this case
    public View mRootView;
    private final IClickListener clickListener;

    public HubCardViewHolder(View v, IClickListener clickListener) {
        super(v);
        mRootView = v;
        this.clickListener = clickListener;
        mRootView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        clickListener.onClick(this, getAdapterPosition());
    }

    public interface IClickListener {
        void onClick(HubCardViewHolder viewHolder, int position);
    }
}
