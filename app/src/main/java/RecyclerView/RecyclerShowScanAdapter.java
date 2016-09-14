package RecyclerView;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import Model.InfoBarcode;
import example.f.y.ashayar.R;

/**
 * Created by jasmine on 6/29/2016.
 */
public class RecyclerShowScanAdapter extends RecyclerView.Adapter<RecyclerShowScanAdapter.VH> {
    Context context;
    List<InfoBarcode> scanShow;
    LayoutInflater inflater;
    public interface OnItemClickListener {
        void onItemClick(InfoBarcode item, int position);
    }
    private final OnItemClickListener listener;
    public RecyclerShowScanAdapter(Context context, List<InfoBarcode> scanShow,OnItemClickListener listener1) {
        this.context = context;
        this.scanShow = scanShow;
        this.listener = listener1;
        inflater=LayoutInflater.from(context);
    }
public void swap(List<InfoBarcode> Scan){
    InfoBarcode infoBarcode;
    for (int i=0;i<Scan.size();i++){
       infoBarcode=Scan.get(i);
        for (int j=0;j<scanShow.size();j++){
            InfoBarcode infoBarcode1=scanShow.get(j);
            if (infoBarcode.getBarcode().equals(infoBarcode1.getBarcode())){
                scanShow.get(j).setMessage(Scan.get(i).getMessage());
            }

        }
    }
//    scanShow.clear();
//    scanShow.addAll(Scan);
    notifyDataSetChanged();
}
    public void remove(int position){
        notifyItemRemoved(position);

    }
    @Override
    public RecyclerShowScanAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_row_scan_sale,parent,false);
        VH vh=new VH(view);
        return vh;

    }

    @Override
    public int getItemCount() {
        return scanShow.size();
    }

    @Override
    public void onBindViewHolder(RecyclerShowScanAdapter.VH holder, int position) {
InfoBarcode scan=scanShow.get(position);
        Typeface typeface=Typeface.createFromAsset(context.getAssets(), "iranscanmobile.ttf");

        try{
        if (scan.getMessage()==null||scan.getMessage().equals("")){
            holder.Message.setVisibility(View.GONE);
        }else {
            holder.Message.setVisibility(View.VISIBLE);
            holder.Message.setText(scan.getMessage());
        }}catch (Exception e){
            e.getMessage();
        }

        holder.scan.setText(scan.getBarcode());
        holder.scan.setTypeface(typeface);
        holder.Message.setTypeface(typeface);
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView scan,Message;
        public VH(View itemView) {
            super(itemView);
            scan=(TextView)itemView.findViewById(R.id.scan_show);
            Message=(TextView)itemView.findViewById(R.id.Message);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int n=getPosition();
                    listener.onItemClick(scanShow.get(getPosition()),n);
                    return false;
                }
            });
        }
    }


}
