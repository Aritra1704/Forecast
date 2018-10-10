package in.arp.forecast.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arpaul.utilitieslib.NetworkUtility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.arp.forecast.R;
import in.arp.forecast.model.WeatherDetail;
import in.arp.forecast.model.WeatherList;
import in.arp.forecast.viewmodel.SharedVM;
import in.arp.forecast.webservice.ApiImpl;

public class WeatherActivity extends AppCompatActivity implements ApiImpl.ApiResp {

    @BindView(R.id.tvLocation)
    protected TextView tvLocation;
    @BindView(R.id.rvWeather)
    protected RecyclerView rvWeather;
    @BindView(R.id.fabRefresh)
    protected FloatingActionButton fabRefresh;
    @BindView(R.id.fabAdd)
    protected FloatingActionButton fabAdd;
    @BindView(R.id.progressbar)
    protected ProgressBar progressbar;

    private SharedVM model;
    private String selLocation;
    private WeatherAdapter adapter;
    private FragmentManager fm;
    public static final int GET_WEATHER = 001;

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
                if(TextUtils.isEmpty(selLocation))
                    Snackbar.make(view, "Select location first", Snackbar.LENGTH_LONG).show();
                else if(!NetworkUtility.isConnectionAvailable(WeatherActivity.this))
                    Snackbar.make(view, "Internet connection not available.", Snackbar.LENGTH_LONG).show();
                else {
                    progressbar.setVisibility(View.VISIBLE);
                    ApiImpl.getInst(GET_WEATHER).getfiveDayData(selLocation,"json", WeatherActivity.this);
                }
            }
        });

        adapter = new WeatherAdapter(this, new WeatherList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvWeather.setLayoutManager(linearLayoutManager);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        rvWeather.addItemDecoration(mDividerItemDecoration);
        rvWeather.setAdapter(adapter);
    }

    @Override
    public void onResponse(Object response, Object error, int apiId) {
        switch (apiId) {
            case GET_WEATHER :
                progressbar.setVisibility(View.GONE);
                if(error == null) {
                    WeatherList list =  new WeatherList();
                    list.setList((ArrayList<WeatherDetail>) response);;

                    if(list != null)
                        adapter.refresh(list);
                } else {
                    String err = (String) error;
                    Toast.makeText(this, err, Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                return;
        }
    }
}
