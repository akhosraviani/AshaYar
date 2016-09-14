
package RecyclerView;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import Model.InformationRow;
import example.f.y.ashayar.R;


/**
 * Created by jasmine on 4/26/2016.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.VH> {
    List<InformationRow> informationRows;
    LayoutInflater inflater;
    Context context;
    String ip;
    String phiWm;
    String InventoryCode;
    public interface OnItemClickListener {
        void onItemClick(InformationRow item, int position);
    }
    private final OnItemClickListener listener;


    public RecycleViewAdapter(Context context,List<InformationRow> informationRows,OnItemClickListener listener){
        this.context=context;
        this.informationRows=informationRows;
        this.phiWm=phiWm;
        this.ip=ip;
        inflater=LayoutInflater.from(context);
        this.InventoryCode=InventoryCode;
        this.listener = listener;
    }
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_row_list_inventory,parent,false);
        VH vh=new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

        holder.bind(informationRows.get(position), listener);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return informationRows.size();
    }

    public class VH extends RecyclerView.ViewHolder

    {
        TextView sequence,Qty,PartCode,PartTitle,UOM;
        public VH(View itemView) {
            super(itemView);
            sequence=(TextView)itemView.findViewById(R.id.sequence);
            Qty=(TextView)itemView.findViewById(R.id.Qty);
            PartCode=(TextView)itemView.findViewById(R.id.partCode);
            PartTitle=(TextView)itemView.findViewById(R.id.partTitle);
            UOM=(TextView)itemView.findViewById(R.id.UOM);
            itemView.setClickable(true);
//            itemView.setOnClickListener(this);
        }
        public void bind(final InformationRow item, final OnItemClickListener listener) {
            sequence.setText(item.getSequence());
            PartTitle.setText(item.getPartTitle());
            PartCode.setText(item.getPartCode());
            Typeface typeface=Typeface.createFromAsset(context.getAssets(),"iranscanmobile.ttf");
            sequence.setTypeface(typeface);
            PartTitle.setTypeface(typeface);
            PartCode.setTypeface(typeface);
            try {
                if (item.getQty().equals("null")){
                    Qty.setText("");
                    UOM.setText("");
                }

                else {
                    Qty.setText(item.getQty());
                    UOM.setText(item.getUOMTitle());
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int n=getPosition();
                    listener.onItemClick(item,n);
                }
            });
        }

}}
