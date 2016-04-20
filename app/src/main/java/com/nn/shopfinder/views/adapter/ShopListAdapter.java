package com.nn.shopfinder.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nn.shopfinder.R;
import com.nn.shopfinder.model.shop.GenericShop;

import java.util.List;

/**
 * Created by Nuno on 14/04/2016.
 */
public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ViewHolder> {

    private List<? extends GenericShop> shops;

    public ShopListAdapter(List<? extends GenericShop> shops) {
        this.shops = shops;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        View v = inf.inflate(R.layout.shoplist_item, parent, false);

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

        holder.brandIcon.setImageResource(shop.getIconResourceId());

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
}
