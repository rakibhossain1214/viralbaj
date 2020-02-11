package com.example.viralbaj;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ServiceProgressHolder extends RecyclerView.ViewHolder {
    public CircleImageView imageViewCategory;
    public ProgressBar progressBar;
    public TextView earningType;
    public CardView adCard;
    public TextView textViewProgress;

    public ServiceProgressHolder(@NonNull View itemView) {
        super(itemView);

        progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        imageViewCategory = (CircleImageView) itemView.findViewById(R.id.imageview_category);
        earningType = (TextView) itemView.findViewById(R.id.earning_type);
        adCard = (CardView) itemView.findViewById(R.id.adcard);
        textViewProgress = (TextView) itemView.findViewById(R.id.textview_progress);

    }
}
