
package dynamitechetan.popularmovies2.API;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import dynamitechetan.popularmovies2.R;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mImageUrls;


    public ImageAdapter(Context context) {
        this(context, new ArrayList<String>());
    }

    public ImageAdapter(Context context, ArrayList<String> imageUrls) {
        mContext = context;
        mImageUrls = imageUrls;
    }


    public void clear() {
        mImageUrls.clear();
    }


    public void add(String imageUrl) {
        mImageUrls.add(imageUrl);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mImageUrls.size();
    }

    @Override
    public String getItem(int position) {
        return mImageUrls.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = (ImageView) convertView;
        if (imageView == null) {
            imageView = new ImageView(mContext);
            imageView.setAdjustViewBounds(true);
        }

        String url = getItem(position);

        Picasso.with(mContext)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder_error)
                .into(imageView);

        return imageView;
    }
}