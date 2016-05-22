package com.jli.gardener.activity;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.db.chart.model.Bar;
import com.db.chart.model.BarSet;
import com.db.chart.model.LineSet;
import com.db.chart.view.ChartView;
import com.db.chart.view.LineChartView;
import com.db.chart.view.XController;
import com.db.chart.view.YController;
import com.jli.gardener.R;
import com.jli.gardener.model.PlantModule;
import com.jli.gardener.model.Record;
import com.jli.gardener.util.RecordUtil;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ModuleDetailActivity extends AppCompatActivity {
    public static final float COLOR_MAX = 65535f;

    PlantModule mModule;
    Record mLastRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        mModule = bundle.getParcelable("module");
        mLastRecord = mModule.getRecords().get(0);

        bindUi();
    }

    void bindUi() {
        setContentView(R.layout.activity_module_detail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView moduleTitle = (TextView) findViewById(R.id.hub_name);
        moduleTitle.setText(mModule.getName());

        initLightingCard();
        initAmbientLightingCard();
        initHumidityCard();
        initTemperatureCard();
    }

    void initLightingCard() {
        ChartView mChart = (ChartView) findViewById(R.id.lighting_chart);
        BarSet barSet = new BarSet();
        barSet.setColor(Color.parseColor("#fc2a53"));

        float redVal = (mLastRecord.getDirectionalLightRed() / COLOR_MAX) * 256;
        float greenVal = (mLastRecord.getDirectionalLightGreen() / COLOR_MAX) * 256;
        float blueVal = (mLastRecord.getDirectionalLightBlue() / COLOR_MAX) * 256;
        Bar red = new Bar("Red", redVal);
        red.setColor(Color.parseColor("#F44336"));

        Bar green = new Bar("Green", greenVal);
        green.setColor(Color.parseColor("#4CAF50"));

        Bar blue = new Bar("Blue", blueVal);
        blue.setColor(Color.parseColor("#2196F3"));

        barSet.addBar(blue);
        barSet.addBar(green);
        barSet.addBar(red);
        mChart.addData(barSet);
        mChart.setStep(256);
        mChart.setXAxis(true)
                .setYAxis(true)
                .setXLabels(XController.LabelPosition.OUTSIDE)
                .setYLabels(YController.LabelPosition.OUTSIDE)
                .setLabelsColor(Color.parseColor("#86705c"))
                .setAxisColor(Color.parseColor("#86705c"));
        mChart.show();

        int color = Color.rgb((int) redVal, (int) greenVal, (int) blueVal);
        ImageView colorDrawable = (ImageView) findViewById(R.id.lighting_color);
        colorDrawable.setColorFilter(color);
    }

    void initAmbientLightingCard() {
        LineChartView lineChartView = (LineChartView) findViewById(R.id.ambient_chart);
        LineSet dataset = new LineSet();

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        calendar.add(Calendar.HOUR_OF_DAY, -24);
        Date yesterday = calendar.getTime();

        List<Record> records = RecordUtil.getRecordsBetweenDates(mModule.getRecords(), yesterday, now);
        Collections.sort(records);

        for(int i = 0; i < records.size(); i++) {
            Record record = records.get(i);
            float ambientVal = (record.getAmbientLight()/COLOR_MAX) * 100;
            if(i == 0) {
                dataset.addPoint("Yesterday", ambientVal);
            } else if(i == (records.size() -1)) {
                dataset.addPoint("Now", ambientVal);
            } else {
                dataset.addPoint("", ambientVal);
            }
        }

        lineChartView.setStep(10);
        dataset.setColor(Color.parseColor("#b3b5bb"))
                .setFill(Color.parseColor("#FFC107"))
                .setDotsColor(Color.parseColor("#FF6F00")).setSmooth(true);
        lineChartView.addData(dataset);
        lineChartView.show();
    }

    void initHumidityCard() {
        ChartView humidityChart = (ChartView) findViewById(R.id.humidity_chart);
        BarSet barSet = new BarSet();
        barSet.setColor(Color.parseColor("#fc2a53"));

        float regionHumidity = (mLastRecord.getHumidity() / 1024f) * 100;
        float soilHumidity = (mLastRecord.getSoil() / 1024f) * 100;

        Bar region = new Bar("Weather", regionHumidity);
        region.setColor(Color.parseColor("#4CAF50"));

        Bar soil = new Bar("Soil", soilHumidity);
        soil.setColor(Color.parseColor("#2196F3"));

        barSet.addBar(soil);
        barSet.addBar(region);
        humidityChart.addData(barSet);
        humidityChart.setStep(100);
        humidityChart.setXAxis(true)
                .setYAxis(true)
                .setXLabels(XController.LabelPosition.OUTSIDE)
                .setYLabels(YController.LabelPosition.OUTSIDE)
                .setLabelsColor(Color.parseColor("#86705c"))
                .setAxisColor(Color.parseColor("#86705c"));
        humidityChart.show();

        LineChartView lineChartView = (LineChartView) findViewById(R.id.humidity_line_chart);
        LineSet weatherDataset = new LineSet();
        LineSet soilDataset = new LineSet();

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        calendar.add(Calendar.HOUR_OF_DAY, -24);
        Date yesterday = calendar.getTime();

        List<Record> records = RecordUtil.getRecordsBetweenDates(mModule.getRecords(), yesterday, now);
        Collections.sort(records);

        for(int i = 0; i < records.size(); i++) {
            Record record = records.get(i);
            float weatherHumidityVal = (record.getHumidity()/1024f) * 100;
            float soilHumidityVal = (record.getSoil()/1024f) * 100;

            if(i == 0) {
                weatherDataset.addPoint("Yesterday", weatherHumidityVal);
            } else if(i == (records.size() -1)) {
                weatherDataset.addPoint("Now", weatherHumidityVal);

            } else {
                weatherDataset.addPoint("", weatherHumidityVal);
            }
            soilDataset.addPoint("", soilHumidityVal);
        }

        lineChartView.setStep(10);
        weatherDataset.setColor(Color.parseColor("#b3b5bb"))
                .setDotsColor(Color.parseColor("#4CAF50"))
                .setSmooth(true);

        soilDataset.setColor(Color.parseColor("#b3b5bb"))
                .setDotsColor(Color.parseColor("#2196F3"))
                .setSmooth(true);

        lineChartView.addData(weatherDataset);
        lineChartView.addData(soilDataset);
        lineChartView.show();
    }

    void initTemperatureCard() {
        TextView temperatureLabel = (TextView) findViewById(R.id.temperature_label);
        String label = String.format("%.1fF / %.1fC", mLastRecord.getTemperaturFahrenheit(), mLastRecord.getTemperaturC());
        temperatureLabel.setText(label);

        LineChartView lineChartView = (LineChartView) findViewById(R.id.temp_line_chart);
        LineSet dataset = new LineSet();

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        calendar.add(Calendar.HOUR_OF_DAY, -24);
        Date yesterday = calendar.getTime();

        List<Record> records = RecordUtil.getRecordsBetweenDates(mModule.getRecords(), yesterday, now);
        Collections.sort(records);

        for(int i = 0; i < records.size(); i++) {
            Record record = records.get(i);
            if(i == 0) {
                dataset.addPoint("Yesterday", record.getTemperaturFahrenheit());
            } else if(i == (records.size() -1)) {
                dataset.addPoint("Now", record.getTemperaturFahrenheit());
            } else {
                dataset.addPoint("", record.getTemperaturFahrenheit());
            }
        }

        lineChartView.setStep(10);
        dataset.setColor(Color.parseColor("#b3b5bb"))
                .setFill(Color.parseColor("#FFC107"))
                .setDotsColor(Color.parseColor("#FF6F00")).setSmooth(true);
        lineChartView.addData(dataset);
        lineChartView.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
