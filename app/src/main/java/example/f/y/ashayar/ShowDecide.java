package example.f.y.ashayar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import RecyclerView.RecyclerAdapterDecide;
import java.util.List;

import Model.Decide;
import Model.fillfDecide;
import imageView.BlurBuilder;
import imageView.ImageViewCircle;

public class ShowDecide extends AppCompatActivity {
    ImageView person;
    TextView Name;
    ImageViewCircle imageView_round;
    String ip,userName;
    Bitmap bitmapMaster;
    Toolbar toolbar;
  //  ImageViewCircle exist;
    List<Decide> decideList;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_decide);
        toolbar=(Toolbar)findViewById(R.id.lable_entry_data);
        setSupportActionBar(toolbar);
        // Get the ActionBar here to configure the way it behaves.
        final ActionBar ab = getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home scan
        ab.setDisplayShowHomeEnabled(true); // show or hide the default home scan
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setDisplayShowCustomEnabled(true);
        initiation();
        SetData();
       Listener();
    }
    public void SetData(){
        try {
            Intent intent=getIntent();
            ip=intent.getStringExtra("ip");
            userName=intent.getStringExtra("userName");
            Name.setText(intent.getStringExtra("name"));
            bitmapMaster=String2Bitmap(intent.getStringExtra("image"));
            person.setImageBitmap(bitmapMaster);
            BlurBuilder blurBuilder=new BlurBuilder();
            if (bitmapMaster==null){
              //  person.setImageBitmap(Drawble2bitmap(R.drawable.images));
                imageView_round.setImageBitmap(Drawble2bitmap(R.drawable.untitled));
            }
            else{
          //      person.getBackground().setAlpha(100);
            Bitmap blur=blurBuilder.blur(ShowDecide.this,bitmapMaster);
            person.setImageBitmap(blur);
        //    loadGrayBitmap(bitmapMaster);
            imageView_round.setImageBitmap(bitmapMaster);}
        }catch (Exception e){
            e.getMessage();
        }
        setRecyclerView();
    }
    public Bitmap Drawble2bitmap(int resource){
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),resource);
        return bitmap;
    }
    public Drawable Bitmap2Drawble(Bitmap bitmap){
        Drawable drawable=new BitmapDrawable(getResources(),bitmap);
        return drawable;
    }
    public Bitmap String2Bitmap(String encodedString){
        try {
            byte[] encodeByte= Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch (Exception e){
            e.getMessage();
            return null;
        }
    }
    public void initiation(){
        imageView_round=(ImageViewCircle)findViewById(R.id.imageView_round);
        Name=(TextView)findViewById(R.id.name);
        person=(ImageView)findViewById(R.id.blur);
        //exist=(ImageViewCircle)findViewById(R.id.exit);
        fillfDecide decide=new fillfDecide();
        decideList=decide.decideList();
        recyclerView=(RecyclerView)findViewById(R.id.decide);

    }
    public void setRecyclerView(){
        RecyclerAdapterDecide adapter=new RecyclerAdapterDecide(this,decideList,ip,userName);
        LinearLayoutManager ll=new LinearLayoutManager(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.scrollToPosition(0);
        recyclerView.setLayoutManager(ll);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void Listener(){
//        exist.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
//                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.putExtra("EXIT",true);
//                startActivity(intent);
//
//            }
//        });
    }
    public Bitmap CropImage(Bitmap originalBitmap){
        Paint paint = new Paint();
        Bitmap croppedBmp = Bitmap.createBitmap(originalBitmap, 0, 0,
                originalBitmap.getWidth() / 2, originalBitmap.getHeight());
        Canvas canvas = new Canvas(croppedBmp);
        int h = originalBitmap.getHeight();
        canvas.drawBitmap(originalBitmap, 10, 10, paint);
        canvas.drawBitmap(croppedBmp, 10, 10 + h + 10, paint);
return croppedBmp;


//
//        Bitmap bmOverlay = Bitmap.createBitmap(originalBitmap.getWidth(),
//                originalBitmap.getHeight(),
//                Bitmap.Config.ARGB_8888);
//
//        Paint p = new Paint();
//        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//        Canvas canvas = new Canvas(bmOverlay);
//        canvas.drawBitmap(originalBitmap, 0, 0, null);
//        canvas.drawRect(0, 0, 20, 20, p);
//
//        Point a = new Point(0, 20);
//        Point b = new Point(20, 20);
//        Point c = new Point(0, 40);
//
//        Path path = new Path();
//        path.setFillType(Path.FillType.EVEN_ODD);
//        path.lineTo(b.x, b.y);
//        path.lineTo(c.x, c.y);
//        path.lineTo(a.x, a.y);
//        path.close();
//
//        canvas.drawPath(path, p);
//
//        a = new Point(0, 40);
//        b = new Point(0, 60);
//        c = new Point(20, 60);
//
//        path = new Path();
//        path.setFillType(Path.FillType.EVEN_ODD);
//        path.lineTo(b.x, b.y);
//        path.lineTo(c.x, c.y);
//        path.lineTo(a.x, a.y);
//        path.close();
//
//        canvas.drawPath(path, p);
//
//        canvas.drawRect(0, 60, 20, originalBitmap.getHeight(), p);
//
//        return bmOverlay;
    }
    //ایچاد کلاس برای بلورکردن تصویر
//    private void loadGrayBitmap(Bitmap src) {
//        if (src != null) {
//
////عرض و ارتفاع تصویر
//            int w = src.getWidth();
//            int h = src.getHeight();
////برابری عرض و ارتفاع با مقدار بلور تصویر
//            RectF rectF = new RectF(w/2, h/2, w*3/4, h*3/4);
////شدت بلور تصویر
//            float blurRadius =20000.9f;
//
////ساخت بیت مپ کانوس
//            Bitmap bitmapResult = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//            Canvas canvasResult = new Canvas(bitmapResult);
//
////ایجاد یک paint
//            Paint blurPaintOuter = new Paint();
////معرفی رنگ برای paint
//            blurPaintOuter.setColor(0xFFffffff);
//
////اضافه کردن فیلتر خارجی
//            blurPaintOuter.setMaskFilter(new
//                    BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.INNER));
//            canvasResult.drawBitmap(bitmapMaster, 0, 0, blurPaintOuter);
//
////رنگ داخلی فیلتر
//            Paint blurPaintInner = new Paint();
//            blurPaintInner.setColor(0xFFffffff);
//            blurPaintInner.setMaskFilter(new
//                    BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.OUTER));
//            canvasResult.drawRect(rectF, blurPaintInner);
//
//            person.setImageBitmap(bitmapResult);
//        }
//    }
}
