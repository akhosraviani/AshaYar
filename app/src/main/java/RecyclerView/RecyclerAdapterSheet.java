package RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import Model.ShipMentModel;
import example.f.y.ashayar.Information;
import example.f.y.ashayar.R;


/**
 * Created by jasmine on 6/21/2016.
 */
public class RecyclerAdapterSheet extends RecyclerView.Adapter<RecyclerAdapterSheet.VH> {
    Context context;
    List<ShipMentModel> shipMentModels;
    LayoutInflater  inflater;
    String ip;

    public RecyclerAdapterSheet(Context context, List<ShipMentModel> shipMentModels,String ip) {
        this.context = context;
        this.shipMentModels = shipMentModels;
        this.ip=ip;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerAdapterSheet.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            View view=inflater.inflate(R.layout.custom_row_shipment,parent,false);
            VH vh=new VH(view);
            return vh;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerAdapterSheet.VH holder, int position) {
        ShipMentModel mentModel=shipMentModels.get(position);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(), "iranscanmobile.ttf");
        holder.code.setTypeface(typeface);
        holder.title.setTypeface(typeface);
        holder.code.setText(mentModel.getCode());
        holder.title.setText(mentModel.getTitle());
    }

    @Override
    public int getItemCount() {
        return shipMentModels.size();
    }

    public class VH extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title,code;
        public VH(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title);
            code=(TextView)itemView.findViewById(R.id.code);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context, Information.class);
            intent.putExtra("code",shipMentModels.get(getPosition()).getCode());
            intent.putExtra("ip",ip);
            context.startActivity(intent);
        }
    }
}
