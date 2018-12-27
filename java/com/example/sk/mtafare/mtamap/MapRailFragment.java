package com.example.sk.mtafare.mtamap;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.sk.mtafare.R;

public class MapRailFragment extends Fragment {

    private SubsamplingScaleImageView railLongIslandPhotoView;
    private SubsamplingScaleImageView railMetroNorthPhotoView;
    private TextView downloadLIRRMapTextView;
    private TextView downloadMetroNorthMapTextView;

    public MapRailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        View returnView = inflater.inflate(R.layout.fragment_map_rail_layout, container, false);

        railLongIslandPhotoView = (SubsamplingScaleImageView) returnView.findViewById(R.id.railLongIslandPhotoView);
        downloadLIRRMapTextView = (TextView) returnView.findViewById(R.id.downloadLIRRMapTextView);

        railMetroNorthPhotoView = (SubsamplingScaleImageView) returnView.findViewById(R.id.railMetroNorthPhotoView);
        downloadMetroNorthMapTextView = (TextView) returnView.findViewById(R.id.downloadMetroNorthMapTextView);

        setMapData();

        return returnView;
    }

    private void setMapData() {
        railLongIslandPhotoView.setImage(ImageSource.asset("rail_map_long_island.jpg"));
        downloadLIRRMapTextView.setText(getResources().getText(R.string.map_rail_LIRR_download));
        downloadLIRRMapTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MapUrl = "http://web.mta.info/lirr/Timetable/LIRRweb-Feb17.pdf";
                Intent mapPdf = new Intent(Intent.ACTION_VIEW);
                mapPdf.setData(Uri.parse(MapUrl));
                startActivity(mapPdf);
            }
        });

        railMetroNorthPhotoView.setImage(ImageSource.asset("rail_map_metro_north.jpg"));
        downloadMetroNorthMapTextView.setText(getResources().getText(R.string.map_rail_Metro_North_download));
        downloadMetroNorthMapTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MapUrl = "http://web.mta.info/mnr/html/mnrmap.htm";
                Intent mapPdf = new Intent(Intent.ACTION_VIEW);
                mapPdf.setData(Uri.parse(MapUrl));
                startActivity(mapPdf);
            }
        });
    }

}
