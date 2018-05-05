package com.dcf82.fs.sample.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcf82.fs.sample.R;
import com.dcf82.fs.sample.activities.VenueItemDetailsActivity;
import com.dcf82.fs.sample.fourSquareBeans.FourSquareResponse;
import com.dcf82.fs.sample.fourSquareBeans.VenueItem;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.dcf82.fs.sample.map.MyInfoWindowAdapter;

public class FourSquareMap extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener {

    // Debug variable
    private static final String LOG = "FourSquareMap";

    // Current instance of Map
    private GoogleMap mMap;

    // This is te current location drawn over the map
    private LatLng mCurrentPosition;

    // Window Adapter
    private MyInfoWindowAdapter mMyAdapter;

    // Places
    private FourSquareResponse mData;

    // Single Place
    private VenueItem mSingleData;

    // Google Maps API variables
    protected GoogleApiClient mGoogleApi;

    public FourSquareMap() {}

    public static FourSquareMap newInstance(FourSquareResponse fourSquareResponse, VenueItem singleData) {
        FourSquareMap fragment = new FourSquareMap();
        Bundle args = new Bundle();
        args.putSerializable("data", fourSquareResponse);
        args.putSerializable("item", singleData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mData = (FourSquareResponse)getArguments().getSerializable("data");
            mSingleData = (VenueItem)getArguments().getSerializable("item");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mapview_fragment, container, false);

        // Getting the map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add listeners
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMarkerClickListener(this);

        // Add the Window Adapter
        mMyAdapter = new MyInfoWindowAdapter(getActivity(), null);
        mMap.setInfoWindowAdapter(mMyAdapter);

        // Build Google API
        mGoogleApi = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .build();

        // Connect to Google Places
        mGoogleApi.connect();

        // Set my current location if this is available
        mCurrentPosition = buildLatLon(
                Double.parseDouble(getContext().getString(R.string.latitude)),
                Double.parseDouble(getContext().getString(R.string.longitude)));

        // Draw the selected position on the map
        setCurrentLocation();

        // Add the other markers
        loadMarkers();

        // Add Item
        loadItem();
    }

    private LatLng buildLatLon(double latitude, double longitude) {
        return new LatLng(latitude, longitude);
    }

    protected void setCurrentLocation() {

        // Nothing to do
        if (mCurrentPosition == null) return;

        CameraPosition cameraPosition = new CameraPosition.Builder()
                /* Sets the location */
                .target(mCurrentPosition)
                /* Sets the zoom */
                .zoom(14)
                /* Sets the orientation of the camera to east */
                .bearing(90)
                /* Sets the tilt of the camera to 30 degrees */
                .tilt(30)
                /* Creates a CameraPosition from the builder */
                .build();

        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        mMap.addMarker(new MarkerOptions().position(mCurrentPosition).title("user")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_directions_walk_black_36dp)));
    }

    private void loadMarkers() {
        if (mData == null) return;
        for (VenueItem item : mData.getResponse().getVenues()) {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(item.getLocation().getLatitude(), item.getLocation()
                            .getLongitude()))
                    .title(item.getId()));
        }
    }

    private void loadItem() {
        if (mSingleData == null) return;
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(mSingleData.getLocation().getLatitude(), mSingleData
                        .getLocation().getLongitude()))
                .title(mSingleData.getId()));
    }

    private VenueItem getItem(String id) {

        if (id.equals("user") || mData == null) return null;

        for (VenueItem item : mData.getResponse().getVenues()) {
            if (item.getId().equals(id)) {
                return item;
            }
        }

        return null;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        // Close the Marker
        marker.hideInfoWindow();

        VenueItem item = getItem(marker.getTitle());

        if (item == null) return;

        // Open Details screen
        Intent activity = new Intent(getActivity(), VenueItemDetailsActivity.class);
        activity.putExtra("item", item);
        startActivity(activity);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        VenueItem item = getItem(marker.getTitle());

        if (item == null) return false;

        // Open the Marker
        mMyAdapter.setVenueData(item);

        marker.showInfoWindow();

        return false;
    }
}
