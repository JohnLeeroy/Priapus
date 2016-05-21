package com.jli.gardener.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jli.gardener.R;
import com.jli.gardener.model.PlantModule;
import com.jli.gardener.model.Record;

import java.util.List;

/**
 * Created by john on 5/21/16.
 */
public class HubAdapter extends RecyclerView.Adapter<HubCardViewHolder> {
    private List<PlantModule> mDataset;

    private HubCardViewHolder.IClickListener clickListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public HubAdapter(List<PlantModule> myDataset, HubCardViewHolder.IClickListener clickListener) {
        mDataset = myDataset;
        this.clickListener = clickListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HubCardViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hub_card, parent, false);
        HubCardViewHolder vh = new HubCardViewHolder(view, clickListener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(HubCardViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset.get(position));
        PlantModule hub = mDataset.get(position);
        TextView name = (TextView)holder.mRootView.findViewById(R.id.hub_name);
        ImageView image = (ImageView)holder.mRootView.findViewById(R.id.hub_image);
        name.setText(hub.getName());

        List<Record> records = hub.getRecords();
        if(records == null) {
            return;
        }

        Record latestRecord = hub.getRecords().get(0);
        if(latestRecord != null) {
            TextView temperature = (TextView) holder.mRootView.findViewById(R.id.temp_fahrenheit);
            temperature.setText(String.format("%.1f", latestRecord.getTemperaturFahrenheit()) + "F");

            TextView humidity  = (TextView) holder.mRootView.findViewById(R.id.humidity);
            humidity.setText(String.format("%.1f", latestRecord.getHumidity()) + "%");

            TextView intensity = (TextView) holder.mRootView.findViewById(R.id.light_intensity);
            float lightIntensity = (latestRecord.getAmbientLight()/65535f) * 100;
            intensity.setText(String.format("%.1f", lightIntensity) + "%");
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setData(List<PlantModule> modules) {
        mDataset = modules;
        notifyDataSetChanged();
    }

    public List<PlantModule> getData() {
        return mDataset;
    }
}
