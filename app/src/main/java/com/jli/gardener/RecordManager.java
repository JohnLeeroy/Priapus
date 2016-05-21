package com.jli.gardener;

import com.jli.gardener.model.PlantModule;
import com.jli.gardener.model.Record;
import com.jli.gardener.model.Report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * Created by john on 5/21/16.
 */
public class RecordManager extends Observable {
    Map<String, PlantModule> mModules;

    private static RecordManager sInstance;

    public static RecordManager getInstance() {
        if(sInstance == null) {
            sInstance = new RecordManager();
        }
        return sInstance;
    }

    private RecordManager() {
        mModules = new HashMap<>();
    }

    public void add(Report report) {
        if(report == null) {
            return;
        }
        List<Record> records = report.getRecords();
        for(Record record : records) {
            PlantModule module = mModules.get(record.getId());
            if(module == null) {
                module = new PlantModule(record.getId());
            }
            List<Record> recordList = module.getRecords();
            if(recordList == null) {
                recordList = new ArrayList<>();
            }
            recordList.add(record);
            Collections.sort(recordList);
            module.setRecords(recordList);
            mModules.put(record.getId(), module);
        }
        setChanged();
        notifyObservers();
    }

    public PlantModule getPlantModule(String id) {
        return mModules.get(id);
    }

    public List<PlantModule> getPlantModules() {
        return new ArrayList<PlantModule>(mModules.values());
    }
}
