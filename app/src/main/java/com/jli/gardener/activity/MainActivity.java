package com.jli.gardener.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jli.gardener.R;
import com.jli.gardener.RecordManager;
import com.jli.gardener.model.ModuleProvider;
import com.jli.gardener.model.PlantModule;
import com.jli.gardener.model.Report;
import com.jli.gardener.view.HubAdapter;
import com.jli.gardener.view.HubCardViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public class MainActivity extends AppCompatActivity implements Observer {

    RecyclerView mHubRecyclerView;
    HubAdapter mHubAdapter;

    List<PlantModule> mMockPlantHubs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindAndInitUi();
        initializeData();
    }

    void bindAndInitUi() {
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Show stubbed plants so we dont have an empty screen
        mMockPlantHubs = new ArrayList<>();
        PlantModule mockPlantHub = new PlantModule("Backyard Garden");
        mMockPlantHubs.add(mockPlantHub);

        PlantModule mockPlantHubTwo = new PlantModule("Side Garden");
        mMockPlantHubs.add(mockPlantHubTwo);

        mHubRecyclerView = (RecyclerView) findViewById(R.id.hub_recycler_view);
        mHubRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mHubAdapter = new HubAdapter(mMockPlantHubs, new ClickListenerImpl());
        mHubRecyclerView.setAdapter(mHubAdapter);
    }

    void initializeData() {
        ModuleProvider.ReportHandler reportHandler = new ModuleProvider.ReportHandler() {
            @Override
            public void onResult(boolean isSuccessful, Report result) {
                RecordManager.getInstance().add(result);
            }
        };
        ModuleProvider provider = new ModuleProvider(this);
        provider.getLatestReport(reportHandler);
        RecordManager.getInstance().addObserver(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RecordManager.getInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object data) {
        List<PlantModule> modules = RecordManager.getInstance().getPlantModules();
        mHubAdapter.setData(modules);
    }

    class ClickListenerImpl implements HubCardViewHolder.IClickListener {

        @Override
        public void onClick(HubCardViewHolder viewHolder, int position) {
            Intent intent = new Intent(MainActivity.this, ModuleDetailActivity.class);

            PlantModule module = mHubAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putParcelable("module", module);
            intent.putExtras(bundle);

            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
