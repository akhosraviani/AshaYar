package RecyclerView;

import android.content.Context;
import android.graphics.Typeface;
import android.net.wifi.ScanResult;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import example.f.y.ashayar.R;


/**
 * Created by jasmine on 6/21/2016.
 */
public class ShowWifiAdapter extends RecyclerView.Adapter<ShowWifiAdapter.VH> {
    Context context;
    List<ScanResult> result;
    LayoutInflater  inflater;
    String ip;

    public ShowWifiAdapter(Context context, List<ScanResult> result) {
        this.context = context;
        this.result = result;
        this.ip=ip;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public ShowWifiAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=inflater.inflate(R.layout.custom_row_show_wifi,parent,false);
            VH vh=new VH(view);
            return vh;


    }

    @Override
    public void onBindViewHolder(ShowWifiAdapter.VH holder, int position) {
      holder.wifi.setText(result.get(position).SSID);
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView wifi;
        public VH(View itemView) {
            super(itemView);
            wifi=(TextView)itemView.findViewById(R.id.wifi_name);
            Typeface typeface=Typeface.createFromAsset(context.getAssets(),"times.ttf");
                wifi.setTypeface(typeface);
        }
    }
}
