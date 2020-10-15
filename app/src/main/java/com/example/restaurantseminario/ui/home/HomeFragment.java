package com.example.restaurantseminario.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.restaurantseminario.R;
import com.example.restaurantseminario.adapters.HomeAdapter;
import com.example.restaurantseminario.adapters.StructureDataHome;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        ListView list  = this.getActivity().findViewById(R.id.restaurante_list);
        ArrayList<StructureDataHome>datos = new ArrayList<>();
        for (int i=0; i<100; i++){
            StructureDataHome item = new StructureDataHome();
            item.setNombre("nombre"+i);
            datos.add(item);
        }
        HomeAdapter adapter = new HomeAdapter(datos,this.getContext());
        //ArrayAdapter<String> adapter = new ArrayAdapter(this.getContext(),android.R.layout.simple_list_item_1,datos);
        list.setAdapter(adapter);
    }
}