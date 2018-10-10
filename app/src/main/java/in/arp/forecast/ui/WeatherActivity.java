package in.arp.forecast.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.arp.forecast.R;
import in.arp.forecast.model.WeatherList;
import in.arp.forecast.viewmodel.SharedVM;

public class WeatherActivity extends AppCompatActivity {

    @BindView(R.id.tvLocation)
    private TextView tvLocation;
    @BindView(R.id.rvWeather)
    private RecyclerView rvWeather;
    @BindView(R.id.fabRefresh)
    private FloatingActionButton fabRefresh;
    @BindView(R.id.fabAdd)
    private FloatingActionButton fabAdd;

    private SharedVM model;
    private String selLocation;
    private WeatherAdapter adapter;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        fm = getSupportFragmentManager();
        bindControl();
    }

    void bindControl() {
        model = ViewModelProviders.of(this).get(SharedVM.class);
        model.getSelected().observe(this, item -> {
            selLocation = item;
            tvLocation.setText(selLocation);
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddLocFragment enterOTPDialog = new AddLocFragment().newInstance();
                enterOTPDialog.show(fm, "enter_loc");
            }
        });

        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        adapter = new WeatherAdapter(this, new WeatherList());
        rvWeather.setLayoutManager(new LinearLayoutManager(this));
        rvWeather.setAdapter(adapter);
    }
}
