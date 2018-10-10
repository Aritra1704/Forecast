package in.arp.forecast.ui;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.arp.forecast.R;
import in.arp.forecast.model.WeatherDetail;
import in.arp.forecast.model.WeatherList;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private final LayoutInflater mInflater;
    private WeatherList list; // Cached copy of words

    public WeatherAdapter(Context context, WeatherList list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    public void refresh(WeatherList list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.weather_item, parent, false);
        return new WeatherViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        if (list != null) {
            WeatherDetail current = list.getList().get(position);
            holder.tvDate.setText("Comment: - "+current.getDt_txt() + "");
            holder.tvTemp.setText("Lat: - "+current.getMain().getTemp() + "");
            holder.tvMin.setText("Lng: - "+current.getMain().getTemp_min() + "");
            holder.tvMax.setText("Acc: - "+current.getMain().getTemp_max() + "");
            holder.tvHumidity.setText("Alt: - "+current.getMain().getHumidity() + "");

        } else {
        }
    }

    @Override
    public int getItemCount() {
        if (list != null && list.getList() != null)
            return list.getList().size();
        else return 0;
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvTemp)
        TextView tvTemp;
        @BindView(R.id.tvMin)
        TextView tvMin;
        @BindView(R.id.tvMax)
        TextView tvMax;
        @BindView(R.id.tvHumidity)
        TextView tvHumidity;

        private WeatherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
