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

public class MapSubwayFragment extends Fragment {

    private TextView downloadSubwayMapTextView;
    private TextView individualLineTextView;
    private SubsamplingScaleImageView subwayMapPhotoView;

    public MapSubwayFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        View returnView = inflater.inflate(R.layout.fragment_map_subway_layout, container, false);

        downloadSubwayMapTextView = (TextView) returnView.findViewById(R.id.downloadSubwayMapTextView);
        individualLineTextView = (TextView) returnView.findViewById(R.id.individualLineTextView);
        subwayMapPhotoView = (SubsamplingScaleImageView) returnView.findViewById(R.id.subwayMapPhotoView);

        setMapData();

        return returnView;
    }

    private void setMapData() {
        subwayMapPhotoView.setImage(ImageSource.asset("subway_map.jpg"));
        downloadSubwayMapTextView.setText(getResources().getText(R.string.map_subway_download));
        downloadSubwayMapTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MapUrl = "http://web.mta.info/nyct/maps/subway_map.pdf";
                Intent mapPdf = new Intent(Intent.ACTION_VIEW);
                mapPdf.setData(Uri.parse(MapUrl));
                startActivity(mapPdf);
            }
        });

        for (int i = 0; i < getResources().getStringArray(R.array.map_subway_stops).length; i++) {
            individualLineTextView.append(getResources().getTextArray(R.array.map_subway_stops)[i]);
            individualLineTextView.append("\n\n");
        }
    }

}
