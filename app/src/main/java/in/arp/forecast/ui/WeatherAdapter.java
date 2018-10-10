package in.arp.forecast.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arpaul.utilitieslib.CalendarUtils;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.arp.forecast.R;
import in.arp.forecast.model.WeatherDetail;
import in.arp.forecast.model.WeatherList;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private final LayoutInflater mInflater;
    private WeatherList list; // Cached copy of words
    private final String FROM_DATE_TIME = "yyyy-MM-dd HH:mm:ss";;
    private final String TO_DATE_TIME = "dd MMM, yyyy hh:mm:ss aa";
    private final int KELVIN_VAL = 273;
    private final String DEG_C = " \u2103";
    public DecimalFormat df2Digit;

    public WeatherAdapter(Context context, WeatherList list) {
        mInflater = LayoutInflater.from(context);
        this.list = list;
        df2Digit = new DecimalFormat("0.00");
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
            holder.tvDate.setText("Date: - "+ CalendarUtils.getDateinPattern(current.getDt_txt(), FROM_DATE_TIME, TO_DATE_TIME));
            holder.tvTemp.setText(df2Digit.format((current.getMain().getTemp() - KELVIN_VAL)) + DEG_C);
            holder.tvMin.setText("Min: - "+df2Digit.format((current.getMain().getTemp_min() - KELVIN_VAL)) + DEG_C);
            holder.tvMax.setText("Max: - "+df2Digit.format((current.getMain().getTemp_max() - KELVIN_VAL)) + DEG_C);
            holder.tvHumidity.setText(current.getMain().getHumidity() + "");

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
