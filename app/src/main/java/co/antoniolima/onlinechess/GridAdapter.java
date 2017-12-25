package co.antoniolima.onlinechess;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import static co.antoniolima.onlinechess.Constants.BOARD_WIDTH;
import static co.antoniolima.onlinechess.Constants.BLACK_B;
import static co.antoniolima.onlinechess.Constants.BLACK_G;
import static co.antoniolima.onlinechess.Constants.BLACK_R;
import static co.antoniolima.onlinechess.Constants.WHITE_B;
import static co.antoniolima.onlinechess.Constants.WHITE_G;
import static co.antoniolima.onlinechess.Constants.WHITE_R;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_SELECTED;

public class GridAdapter extends BaseAdapter {

    Context context;
    GameData gameData;
    private final int [] images;
    int x, y;

    public GridAdapter(Context context, GameData gameData, int[] images) {
        this.gameData = gameData;
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

                boolean positionTaken = gameData.isThisPositionTaken(i);

                if(positionTaken) {
                    Piece selectedPiece = gameData.getPieceByPosition(i);
                    Resources r = context.getResources();
                    Drawable[] layers = new Drawable[2];
                    layers[0] = r.getDrawable(selectedPiece.getCurrentImageId());   // imagem que lá está
                    layers[1] = r.getDrawable(DRAWABLE_SELECTED);                   // borda amarela
                    LayerDrawable layerDrawable = new LayerDrawable(layers);
                    imageView.setImageDrawable(layerDrawable);
                    gameData.selectPiece(selectedPiece);
                }
            }
        });
        return imageView;
    }
}