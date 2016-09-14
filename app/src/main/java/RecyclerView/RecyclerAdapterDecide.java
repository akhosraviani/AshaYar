package RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import junit.framework.Assert;

import java.util.List;

import Model.Decide;
import example.f.y.ashayar.MainActivity;
import example.f.y.ashayar.R;
import example.f.y.ashayar.ShipMent;
import example.f.y.ashayar.ShowPhiwm;
import example.f.y.ashayar.ShowWifi;

public class RecyclerAdapterDecide extends RecyclerView.Adapter<RecyclerAdapterDecide.VH> {
    Context context;
    List<Decide> decideList;
LayoutInflater inflater;
    String username;
    String ip;
    public RecyclerAdapterDecide(Context context, List<Decide> decideList,String ip,String username) {
        this.context = context;
        this.decideList = decideList;
        inflater=LayoutInflater.from(context);
        this.username=username;
        this.ip=ip;
    }
    @Override
    public RecyclerAdapterDecide.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_row_decide,parent,false);
        VH vh=new VH(view);
        return vh;
    }

    @Override
    public int getItemCount() {
        return decideList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterDecide.VH holder, int position) {
        Decide decides=decideList.get(position);

        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"iranscanmobile.ttf");
        try{
            holder.decide.setTypeface(typeface);
            holder.decide.setText(decides.getName());
            Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(),decides.getImage());
            holder.image.setImageBitmap(largeIcon);
        }catch (Exception e){
            e.getMessage();
        }


    }

    class VH extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView decide;
        ImageView image;
        public VH(View itemView) {
            super(itemView);
            decide=(TextView)itemView.findViewById(R.id.decide);
            image=(ImageView)itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (decideList.get(getPosition()).getName()){
                case "انبارگردانی":
                     intent=new Intent(context,ShowPhiwm.class);
                    intent.putExtra("ip", ip);
                    intent.putExtra("Type","partCode");
                    context.startActivity(intent);
                    break;
                case "فروش":
                     intent=new Intent(context,ShipMent.class);
                    intent.putExtra("ip", ip);
                    context. startActivity(intent);
                    break;
                case "خروج از برنامه":
                     intent=new Intent(context.getApplicationContext(),MainActivity.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("EXIT", true);
                    context.startActivity(intent);
                    break;
                case"انبارگردانی سریالی":
                    intent=new Intent(context,ShowPhiwm.class);
                    intent.putExtra("ip", ip);
                    intent.putExtra("Type","Serial");
                    intent.putExtra("userName",username);
                    context.startActivity(intent);
            }
//            if (decideList.get(getPosition()).getName()=="انبارگردانی"){
//                Intent intent=new Intent(context,ShowPhiwm.class);
//                intent.putExtra("ip",ip);
//                context.startActivity(intent);
//            }
//            else {
//                if (decideList.get(getPosition()).getName()=="فروش"){
//                Intent intent=new Intent(context,ShipMent.class);
//                intent.putExtra("ip",ip);
//               context. startActivity(intent);}
//                else {
//                    Intent intent=new Intent(context.getApplicationContext(),MainActivity.class);
//                    intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.putExtra("EXIT",true);
//                    context.startActivity(intent);
//                }
//            }
        }
    }
}
