//package imageView;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.PorterDuff;
//import android.graphics.PorterDuffXfermode;
//import android.graphics.Rect;
//import android.graphics.RectF;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.util.AttributeSet;
//import android.widget.ImageView;
//
//import example.f.y.ashayar.R;
//
///**
// * Created by jasmine on 7/14/2016.
// */
//public class SquareImageView extends ImageView {
//
//    public SquareImageView(Context context) {
//        super(context);
//    }
//
//    public SquareImageView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//    }
//
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        Paint paint = new Paint();
//
//        canvas.drawColor(Color.YELLOW);
//
//
//        // you need to insert a image flower_blue into res/drawable folder
//        paint.setFilterBitmap(true);
//        Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),
//                R.drawable.flower_blue);
//
//        Bitmap croppedBmp = Bitmap.createBitmap(bitmapOrg, 0, 0,
//                bitmapOrg.getWidth() / 2, bitmapOrg.getHeight());
//        int h = bitmapOrg.getHeight();
//        canvas.drawBitmap(bitmapOrg, 10, 10, paint);
//        canvas.drawBitmap(croppedBmp, 10, 10 + h + 10, paint);
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); // Snap to width
//    }
//}
//
