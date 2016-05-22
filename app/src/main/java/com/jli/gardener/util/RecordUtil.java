package com.jli.gardener.util;

import com.jli.gardener.model.Record;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by john on 5/21/16.
 */
public class RecordUtil {

    public static List<Record> getRecordsBetweenDates(List<Record> records, Date start, Date end) {
        List<Record> result = new ArrayList<>();
        for(Record record : records) {
            Date recordDate = record.getTimestampDate();
            if(recordDate.after(start) && recordDate.before(end)) {
                result.add(record);
            }
        }
        return result;
    }

}
