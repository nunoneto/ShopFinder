package com.nn.shopfinder.views.adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nn.shopfinder.R;
import com.nn.shopfinder.model.shop.GenericShop;

import java.util.List;

/**
 * Created by Nuno on 14/04/2016.
 */
public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder> {

    private List<? extends GenericShop> shops;
    private Fragment parent;
    private Context context;
    private OnItemClickedListener listener;

    public ShopListAdapter(List<? extends GenericShop> shops, Fragment frag, OnItemClickedListener listener) {
        this.shops = shops;
        this.parent = frag;
        this.context = frag.getActivity().getApplicationContext();
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        View v = inf.inflate(R.layout.shoplist_item, parent, false);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(v);
            }
        });


        return new ViewHolder(
                v,
                (TextView)v.findViewById(R.id.shopName),
                (TextView)v.findViewById(R.id.shopLocation),
                (ImageView)v.findViewById(R.id.shopIcon)
        );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        GenericShop shop = shops.get(position);

        holder.nameTxt.setText(shop.getName());
        holder.localTxt.setText(shop.getAddress());

        //TODO add image cache and circular mask
        Glide.with(parent).load(shop.getIconResourceId()).transform(new CircleTransform(context)).into(holder.brandIcon);

    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nameTxt, localTxt;
        public ImageView brandIcon;

        public ViewHolder(View itemView, TextView nameTxt, TextView localTxt, ImageView brandIcon) {
            super(itemView);
            this.nameTxt = nameTxt;
            this.localTxt = localTxt;
            this.brandIcon = brandIcon;
        }
    }

    public interface OnItemClickedListener{

        void onItemClicked(View v);

    }
}
