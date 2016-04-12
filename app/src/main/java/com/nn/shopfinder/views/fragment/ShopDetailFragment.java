package com.nn.shopfinder.views.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nn.shopfinder.R;
import com.nn.shopfinder.model.DataModel;
import com.nn.shopfinder.model.shop.GenericShop;

/**
 * Created by Nuno on 12/04/2016.
 */
public class ShopDetailFragment extends Fragment {

    private static final String TAG = "SHOPDETAIL_FRAGMENT";
    private View view;
    private Activity parentActivity;
    private GenericShop shop;


    public static ShopDetailFragment newInstance(String shopID){
        ShopDetailFragment frag = new ShopDetailFragment();
        Bundle b = new Bundle();
        b.putString("shopID",shopID);
        frag.setArguments(b);
        return frag;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view != null)
            view = inflater.inflate(R.layout.fragment_shopdetail,container);

        if(shop == null)
        shop = DataModel.getInstance().getShopByID(getArguments().getString("shopID"));


        return view;
    }

}
