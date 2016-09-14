package RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import Model.ShipmentAuthorize;
import example.f.y.ashayar.ListScan;
import example.f.y.ashayar.R;


/**
 * Created by jasmine on 6/29/2016.
 */
public class RecyclerAdapterAuthorize extends RecyclerView.Adapter<RecyclerAdapterAuthorize.VH> {
    List<ShipmentAuthorize> authorizes;
    Context context;
    LayoutInflater inflater;
    String Shipment;
String ip;
    public RecyclerAdapterAuthorize(List<ShipmentAuthorize> authorizes, Context context,String ip,String shipment) {
        this.authorizes = authorizes;
        this.context = context;
        this.Shipment=shipment;
        this.ip=ip;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerAdapterAuthorize.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_row_shipment_authorize,parent,false);
        VH vh=new VH(view);
        return vh;
    }
    @Override
    public int getItemCount() {
        return authorizes.size();
    }
    @Override
    public void onBindViewHolder(RecyclerAdapterAuthorize.VH holder, int position) {
       ShipmentAuthorize shipmentAuthorize=authorizes.get(position);
        holder.productTitle.setText(shipmentAuthorize.getProductCode());
        holder.productCode.setText(shipmentAuthorize.getProductTitle());
        holder.code.setText(shipmentAuthorize.getCode());

    }
    public class VH extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView code,productCode,productTitle;
        public VH(View itemView) {
            super(itemView);
            code=(TextView)itemView.findViewById(R.id.code);
            productCode=(TextView)itemView.findViewById(R.id.productCode);
            productTitle=(TextView)itemView.findViewById(R.id.productTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context, ListScan.class);
            intent.putExtra("shipment",Shipment);
            intent.putExtra("ip",ip);
            intent.putExtra("authorize",authorizes.get(getPosition()).getCode());
            context.startActivity(intent);
        }
    }
}
