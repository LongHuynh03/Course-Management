package com.example.asm.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asm.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {

    private  static String address;

    public static MapsFragment newInstance(String dc){
        MapsFragment mapsFragment = new MapsFragment();
        address = dc;
        return mapsFragment;
    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            try {
                if (address.equals("HN")){
                    LatLng sydney = new LatLng(21.038135725372666, 105.7467766382311);
                    googleMap.addMarker(new MarkerOptions().position(sydney).title("Cơ sở Hà Nội"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
                }
                if (address.equals("DN")){
                    LatLng sydney = new LatLng(16.07572267958301, 108.16995972570946);
                    googleMap.addMarker(new MarkerOptions().position(sydney).title("Cơ sở Đà Nẵng"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
                }
                if (address.equals("TN")){
                    LatLng sydney = new LatLng(12.709885622922195, 108.07508722567428);
                    googleMap.addMarker(new MarkerOptions().position(sydney).title("Cơ sở Tây Nguyên"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
                }
                if (address.equals("HCM")){
                    LatLng sydney = new LatLng(10.852932873703297, 106.62954306322096);
                    googleMap.addMarker(new MarkerOptions().position(sydney).title("Cơ sở Hồ Chí Minh"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
                }
                if (address.equals("CT")){
                    LatLng sydney = new LatLng(10.02699975268968, 105.75716539681797);
                    googleMap.addMarker(new MarkerOptions().position(sydney).title("Cơ sở Cần Thơ"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
                }
            }catch (Exception e){

            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}