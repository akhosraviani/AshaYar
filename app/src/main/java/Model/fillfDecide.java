package Model;

import java.util.ArrayList;
import java.util.List;

import example.f.y.ashayar.R;

/**
 * Created by jasmine on 7/17/2016.
 */
public class fillfDecide {
    public List<Decide> decideList(){
        List<Decide> decides=new ArrayList<>();
        decides.add(new Decide("انبارگردانی",R.drawable.barcode));
        decides.add(new Decide("انبارگردانی سریالی",R.drawable.barcode));
        decides.add(new Decide("فروش", R.drawable.basket));
        decides.add(new Decide("خروج از برنامه", R.drawable.exit));

        return decides;
    }
}
