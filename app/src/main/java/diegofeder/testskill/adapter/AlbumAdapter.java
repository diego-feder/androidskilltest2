package diegofeder.testskill.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import diegofeder.testskill.R;
import diegofeder.testskill.domain.Album;

public class AlbumAdapter extends BaseAdapter {

    private final List<Album> albumList;
    private final Activity activity;

    public AlbumAdapter(List<Album> albumList, Activity activity) {
        this.albumList = albumList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return albumList.size();
    }

    @Override
    public Album getItem(int position) {
        return albumList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolderItem mViewHolderItem;

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.item_list_album, parent, false);

            mViewHolderItem = new ViewHolderItem();
            mViewHolderItem.ivThumbnail = convertView.findViewById(R.id.image_view_thumbnail);
            mViewHolderItem.tvTitle = convertView.findViewById(R.id.text_view_title);
            convertView.setTag(mViewHolderItem);
        } else {
            mViewHolderItem = (ViewHolderItem) convertView.getTag();
        }
        Picasso.with(activity).load(albumList.get(position).getThumbnailUrl()).into(mViewHolderItem.ivThumbnail);
        mViewHolderItem.tvTitle.setText(albumList.get(position).getTitle());
        return convertView;

    }

    private static class ViewHolderItem {
        private ImageView ivThumbnail;
        private TextView tvTitle;
    }
}
