package com.nn.shopfinder.views.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nn.shopfinder.R;
import com.nn.shopfinder.model.DataModel;
import com.nn.shopfinder.model.shop.GenericShop;
import com.nn.shopfinder.views.adapter.ShopListAdapter;

import java.util.List;

/**
 * Created by NB20301 on 14/04/2016.
 */
public class ShopListingFragment extends Fragment {

    private final static String TAG = "SHOPLISTING_FRAGMENT";

    private View view;
    private RecyclerView recyclerView;
    private ShopListAdapter shopListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_shoplist, container);

        List<? extends GenericShop> shops = DataModel.getInstance().getAllShops();

        recyclerView = (RecyclerView) view.findViewById(R.id.shopList);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        shopListAdapter = new ShopListAdapter(shops);
        recyclerView.setAdapter(shopListAdapter);

        return view;
    }

}
