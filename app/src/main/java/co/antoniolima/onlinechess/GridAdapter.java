package co.antoniolima.onlinechess;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import static co.antoniolima.onlinechess.Constants.BLACK_B;
import static co.antoniolima.onlinechess.Constants.BLACK_G;
import static co.antoniolima.onlinechess.Constants.BLACK_R;
import static co.antoniolima.onlinechess.Constants.BOARD_WIDTH;
import static co.antoniolima.onlinechess.Constants.WHITE_B;
import static co.antoniolima.onlinechess.Constants.WHITE_G;
import static co.antoniolima.onlinechess.Constants.WHITE_R;

public class GridAdapter extends BaseAdapter{

    Context context;
    private final int [] images;
    int x, y;

    public GridAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    //return cell
    @Override
    public Object getItem(int i) { return images[i]; }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ImageView imageView = new ImageView(context);
        imageView.setImageResource(images[i]);
        imageView.setAdjustViewBounds(true);

//      y = ind/cols
//      x = ind%cols

        y = i/BOARD_WIDTH;
        x = i%BOARD_WIDTH;

        if (y%2 == 0) {
            if (x % 2 != 0) {
                imageView.setBackgroundColor(Color.rgb(BLACK_R,BLACK_G,BLACK_B));
            } else{
                imageView.setBackgroundColor(Color.rgb(WHITE_R,WHITE_G,WHITE_B));
            }
        }else{
            if (x % 2 == 0) {
                imageView.setBackgroundColor(Color.rgb(BLACK_R,BLACK_G,BLACK_B));
            }
            else{
                imageView.setBackgroundColor(Color.rgb(WHITE_R,WHITE_G,WHITE_B));
            }
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                y = i/BOARD_WIDTH;
                x = i%BOARD_WIDTH;

                CharSequence text = "Pos: " + x + "," + y;
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        return imageView;
    }
}