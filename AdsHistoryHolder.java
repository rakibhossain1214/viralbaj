package com.example.viralbaj;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdsHistoryHolder extends RecyclerView.ViewHolder{
    public CircleImageView imageViewCategory;
    public Button btnLocationChart, btnGenderChart, btnAgeChart, btnList;
    public TextView earningType;
    public CardView adCard;
    public TextView adDate;

    public AdsHistoryHolder(@NonNull View itemView) {
        super(itemView);

        imageViewCategory = (CircleImageView) itemView.findViewById(R.id.imageview_category);
        btnLocationChart = (Button) itemView.findViewById(R.id.btn_locationchart);
        btnGenderChart = (Button) itemView.findViewById(R.id.btn_genderchart);
        btnAgeChart = (Button) itemView.findViewById(R.id.btn_agechart);
        btnList = (Button) itemView.findViewById(R.id.btn_workerlist);
        earningType = (TextView) itemView.findViewById(R.id.earning_type);
        adCard = (CardView) itemView.findViewById(R.id.adcard);
        adDate = (TextView) itemView.findViewById(R.id.ad_date);
    }

}
