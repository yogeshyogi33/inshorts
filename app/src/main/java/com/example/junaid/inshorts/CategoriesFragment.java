package com.example.junaid.inshorts;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Josh on 25-09-2016.
 */

public class CategoriesFragment extends Fragment {

    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragement_categories,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView listView = (ListView) v.findViewById(R.id.categories_listview);
       String[] list = {"Business","Politics","Entertainment","Science"};

        ListAdapter itemsAdapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(itemsAdapter);
    }
}
