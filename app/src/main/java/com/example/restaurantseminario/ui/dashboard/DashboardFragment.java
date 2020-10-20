package com.example.restaurantseminario.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.restaurantseminario.menuAdapter.DashBoardAdapter;
import com.example.restaurantseminario.menuAdapter.StructureDashboard;
import com.example.restaurantseminario.utils.DataUser;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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
        switch(DataUser.role){
            case "client":{
                siEsUser();
                break;
            }
            case "owner": {
                siEsOwner();
                break;
            }
        }
    }


    private void siEsUser() {
        ListView list  = this.getActivity().findViewById(R.id.hello);
        ArrayList<StructureDashboard> datos = new ArrayList<>();

        StructureDashboard item = new StructureDashboard();

            item.setName("hellooooooooo");

            datos.add(item);

        DashBoardAdapter adapter = new DashBoardAdapter(datos,this.getContext());
        list.setAdapter(adapter);
    }

    private void siEsOwner() {
    }


}