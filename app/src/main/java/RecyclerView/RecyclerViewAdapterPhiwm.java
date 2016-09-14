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

import Model.InformationPhiwm;
import example.f.y.ashayar.R;
import example.f.y.ashayar.SerialCode;
import example.f.y.ashayar.ShowListInventory;


/**
 * Created by jasmine on 5/7/2016.
 */
public class RecyclerViewAdapterPhiwm extends RecyclerView.Adapter<RecyclerViewAdapterPhiwm.VH> {
    Context context;
    List<InformationPhiwm> phIwms;
    LayoutInflater inflater;
    String ip,phiwm,type,username;

    public RecyclerViewAdapterPhiwm(Context context, List<InformationPhiwm> phIwms, String ip,String type,String username) {
        this.context = context;
        this.phIwms = phIwms;
        inflater= LayoutInflater.from(context);
        this.ip = ip;
        this.username=username;
        this.type=type;
    }

    @Override
    public RecyclerViewAdapterPhiwm.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_show_phiwm,parent,false);
        VH vh=new VH(view);
        return vh;
    }

    @Override
    public int getItemCount() {
        return phIwms.size();
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterPhiwm.VH holder, int position) {
        InformationPhiwm phiwmModel=phIwms.get(position);
holder.phiwm.setText(phiwmModel.getCode());
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"iranscanmobile.ttf");
holder.phiwm.setTypeface(typeface);
        holder.InventoryCode.setTypeface(typeface);
        holder.title.setTypeface(typeface);
        holder.InventoryCode.setText(phiwmModel.getInventoryTitle());
        holder.title.setText(phiwmModel.getTitle());
    }

    class VH extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView phiwm;
        TextView InventoryCode;
        public VH(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title);
            phiwm=(TextView)itemView.findViewById(R.id.Code);
            InventoryCode=(TextView)itemView.findViewById(R.id.Inventory_code);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent;
            InformationPhiwm phiwm= phIwms.get(getPosition());
            if (type.equals("partCode")){

            final InformationPhiwm finalPhiwm=phiwm;
                   intent  = new Intent(context, ShowListInventory.class);
                    intent.putExtra("phiwm", finalPhiwm.getCode());
            intent.putExtra("Title",finalPhiwm.getTitle());
                    intent.putExtra("InventoryCode", finalPhiwm.getInventoryCode());
                    intent.putExtra("ip", ip);
                    context.startActivity(intent);}
            else {
 intent=new Intent(context, SerialCode.class);
                intent.putExtra("partcode",phiwm.getCode());
                intent.putExtra("ip",ip);
                intent.putExtra("phiwm",phiwm.getCode());
                intent.putExtra("userName",username);
                context.startActivity(intent);
            }

//
//LayoutInflater inflater=LayoutInflater.from(context);
//            View view=inflater.inflate(R.layout.dialog_design, null, false);
//            Button edit=(Button)view.findViewById(R.id.edit);
//            Button New=(Button)view.findViewById(R.id.New);
//            alert.setView(view);
//            alert.show();
//
//            edit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                }
//            });
//            New.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });

        }
    }
}
