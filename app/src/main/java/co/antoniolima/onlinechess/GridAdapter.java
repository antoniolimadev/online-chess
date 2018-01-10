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

import static co.antoniolima.onlinechess.Constants.BLACK;
import static co.antoniolima.onlinechess.Constants.BOARD_WIDTH;
import static co.antoniolima.onlinechess.Constants.BLACK_B;
import static co.antoniolima.onlinechess.Constants.BLACK_G;
import static co.antoniolima.onlinechess.Constants.BLACK_R;
import static co.antoniolima.onlinechess.Constants.DRAWABLE_SELECTED_RED;
import static co.antoniolima.onlinechess.Constants.WHITE_B;
import static co.antoniolima.onlinechess.Constants.WHITE_G;
import static co.antoniolima.onlinechess.Constants.WHITE_R;

public class GridAdapter extends BaseAdapter {

    Context context;
    GameController gameController;
    int x, y;

    public GridAdapter(Context context, GameController gameController) {
        this.gameController = gameController;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.gameController.getImages().length;
    }

    //return cell
    @Override
    public Object getItem(int i) { return this.gameController.getImage(i); }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ImageView imageView = new ImageView(context);

        if(!this.gameController.getHighlightPosition(i)) {
            imageView.setImageResource(this.gameController.getImage(i));
        } else {
            Resources r = context.getResources();
            Drawable[] layers = new Drawable[2];
            layers[0] = r.getDrawable(this.gameController.getImage(i)); // imagem que lá está
            layers[1] = r.getDrawable(DRAWABLE_SELECTED_RED);               // borda amarela
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            imageView.setImageDrawable(layerDrawable);
        }
        if (gameController.getMyColor() == BLACK){
            imageView.setRotation(180);
        }
        if (gameController.isThisPositionTaken(i)){
            if (gameController.isLocalMultiplayerGame() && gameController.getPieceByPosition(i).getColor() == BLACK){
                imageView.setRotation(180);
            }
        }
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
                // ignora clicks quando é a vez do bot jogar
                if (!gameController.getCurrentPlayer().isBot()){
                    gameController.selectPosition(i);
                    notifyDataSetChanged();
                }
            }
        });
        return imageView;
    }
}