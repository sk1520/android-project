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

public class MapBridgesAndTunnelsFragment extends Fragment { // TODO - edit last piece of bottom textview (not set here) from XML

    private SubsamplingScaleImageView bridgesAndTunnelsPhotoView;
    private TextView downloadBridgeTunnelTextView;

    public MapBridgesAndTunnelsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        View returnView = inflater.inflate(R.layout.fragment_map_bridges_and_tunnels_layout, container, false);

        bridgesAndTunnelsPhotoView = (SubsamplingScaleImageView) returnView.findViewById(R.id.bridgesAndTunnelsPhotoView);
        downloadBridgeTunnelTextView = (TextView) returnView.findViewById(R.id.downloadBridgeTunnelTextView);

        setMapData();

        return returnView;
    }

    private void setMapData() {
        bridgesAndTunnelsPhotoView.setImage(ImageSource.asset("bridges_tunnels_map.jpg"));
        downloadBridgeTunnelTextView.setText(getResources().getText(R.string.map_bridges_and_tunnels_download));
        downloadBridgeTunnelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MapUrl = "http://web.mta.info/bandt/html/btmap.html";
                Intent mapPdf = new Intent(Intent.ACTION_VIEW);
                mapPdf.setData(Uri.parse(MapUrl));
                startActivity(mapPdf);
            }
        });
    }

}
