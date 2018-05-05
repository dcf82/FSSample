package com.dcf82.fs.sample.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.dcf82.fs.sample.R;
import com.dcf82.fs.sample.fourSquareBeans.VenueItem;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private VenueItem mVenue;
    private final View mWindow;

    public MyInfoWindowAdapter(Context context, VenueItem venue) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mVenue = venue;
        mWindow = inflater.inflate(R.layout.place_over_map, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return buildView(marker);
    }

    private View buildView(Marker marker) {
        this.updateData();
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    private void updateData() {

        if (mVenue == null) return;

        TextView tv = mWindow.findViewById(R.id.name);
        tv.setText(mVenue.getName());
    }

    public void setVenueData(VenueItem venue) {
        this.mVenue = venue;
        updateData();
    }

}
