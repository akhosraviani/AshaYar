package RecyclerView;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import example.f.y.ashayar.R;

/**
 * Created by jasmine on 9/14/2016.
 */
public class SerialCodeRecycler extends RecyclerView.Adapter<SerialCodeRecycler.VH> {
    List<String> partCode;
LayoutInflater inflater;
    Context context;
    public SerialCodeRecycler(List<String> partCode,Context context) {
        inflater=LayoutInflater.from(context);
        this.context=context;
        this.partCode = partCode;
    }

    @Override
    public SerialCodeRecycler.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.show_recycle_serial_code,parent,false);
        VH vh=new VH(view);
        return vh;
    }

    @Override
    public int getItemCount() {
        return partCode.size();
    }

    @Override
    public void onBindViewHolder(SerialCodeRecycler.VH holder, int position) {
        Typeface typeface=Typeface.createFromAsset(context.getAssets(), "iranscanmobile.ttf");
        holder.Message.setText(partCode.get(position));
        holder.Message.setTypeface(typeface);
    }

    public class VH extends RecyclerView.ViewHolder{
TextView Message;
        public VH(View itemView) {
            super(itemView);
            Message=(TextView)itemView.findViewById(R.id.serialcode);
        }
    }
}
