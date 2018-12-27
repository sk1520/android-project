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

public class MapBusFragment extends Fragment {

    private SubsamplingScaleImageView busBronxMapPhotoView;
    private SubsamplingScaleImageView busBrooklynMapPhotoView;
    private SubsamplingScaleImageView busManhattanMapPhotoView;
    private SubsamplingScaleImageView busQueensMapPhotoView;
    private SubsamplingScaleImageView BusStatenIslandMapPhotoView;
    private TextView downloadBronxMapTextView;
    private TextView downloadBrooklynMapTextView;
    private TextView downloadManhattanMapTextView;
    private TextView downloadQueensMapTextView;
    private TextView downloadStatenIslandMapTextView;

    public MapBusFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        View returnView = inflater.inflate(R.layout.fragment_map_bus_layout, container, false);

        busBronxMapPhotoView = (SubsamplingScaleImageView) returnView.findViewById(R.id.busBronxMapPhotoView);
        downloadBronxMapTextView = (TextView) returnView.findViewById(R.id.downloadBronxMapTextView);

        busBrooklynMapPhotoView = (SubsamplingScaleImageView) returnView.findViewById(R.id.busBrooklynMapPhotoView);
        downloadBrooklynMapTextView = (TextView) returnView.findViewById(R.id.downloadBrooklynMapTextView);

        busManhattanMapPhotoView = (SubsamplingScaleImageView) returnView.findViewById(R.id.busManhattanMapPhotoView);
        downloadManhattanMapTextView = (TextView) returnView.findViewById(R.id.downloadManhattanMapTextView);

        busQueensMapPhotoView = (SubsamplingScaleImageView) returnView.findViewById(R.id.busQueensMapPhotoView);
        downloadQueensMapTextView = (TextView) returnView.findViewById(R.id.downloadQueensMapTextView);

        BusStatenIslandMapPhotoView = (SubsamplingScaleImageView) returnView.findViewById(R.id.BusStatenIslandMapPhotoView);
        downloadStatenIslandMapTextView = (TextView) returnView.findViewById(R.id.downloadStatenIslandMapTextView);

        setMapData();

        return returnView;
    }

    private void setMapData() {
        busBronxMapPhotoView.setImage(ImageSource.asset("bus_map_bronx.jpg"));
        downloadBronxMapTextView.setText(getResources().getText(R.string.map_bus_bronx_download));
        downloadBronxMapTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MapUrl = "http://web.mta.info/nyct/maps/busbx.pdf";
                Intent mapPdf = new Intent(Intent.ACTION_VIEW);
                mapPdf.setData(Uri.parse(MapUrl));
                startActivity(mapPdf);
            }
        });

        busBrooklynMapPhotoView.setImage(ImageSource.asset("bus_map_brooklyn.jpg"));
        downloadBrooklynMapTextView.setText(getResources().getText(R.string.map_bus_brooklyn_download));
        downloadBrooklynMapTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MapUrl = "http://web.mta.info/nyct/maps/busbkln.pdf";
                Intent mapPdf = new Intent(Intent.ACTION_VIEW);
                mapPdf.setData(Uri.parse(MapUrl));
                startActivity(mapPdf);
            }
        });

        busManhattanMapPhotoView.setImage(ImageSource.asset("bus_map_manhattan.jpg"));
        downloadManhattanMapTextView.setText(getResources().getText(R.string.map_bus_manhattan_download));
        downloadManhattanMapTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MapUrl = "http://web.mta.info/nyct/maps/manbus.pdf";
                Intent mapPdf = new Intent(Intent.ACTION_VIEW);
                mapPdf.setData(Uri.parse(MapUrl));
                startActivity(mapPdf);
            }
        });

        busQueensMapPhotoView.setImage(ImageSource.asset("bus_map_queens.jpg"));
        downloadQueensMapTextView.setText(getResources().getText(R.string.map_bus_queens_download));
        downloadQueensMapTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MapUrl = "http://web.mta.info/nyct/maps/busqns.pdf";
                Intent mapPdf = new Intent(Intent.ACTION_VIEW);
                mapPdf.setData(Uri.parse(MapUrl));
                startActivity(mapPdf);
            }
        });

        BusStatenIslandMapPhotoView.setImage(ImageSource.asset("bus_map_staten_island.jpg"));
        downloadStatenIslandMapTextView.setText(getResources().getText(R.string.map_bus_staten_island_download));
        downloadStatenIslandMapTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MapUrl = "http://web.mta.info/nyct/maps/bussi.pdf";
                Intent mapPdf = new Intent(Intent.ACTION_VIEW);
                mapPdf.setData(Uri.parse(MapUrl));
                startActivity(mapPdf);
            }
        });
    }

}
