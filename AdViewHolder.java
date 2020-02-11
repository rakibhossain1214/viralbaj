package com.example.viralbaj;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdViewHolder extends RecyclerView.ViewHolder{
    public CircleImageView imageViewCategory;
    public TextView earningAmount;
    public TextView totalCount;
    public TextView countLeft;
//    public Button btnEarn;
    public TextView earningType;
    public CardView adCard;

    public AdViewHolder(@NonNull View itemView) {
        super(itemView);
        imageViewCategory = (CircleImageView) itemView.findViewById(R.id.imageview_category);
        earningAmount = (TextView) itemView.findViewById(R.id.earning_amount);
        totalCount = (TextView) itemView.findViewById(R.id.total_count);
        countLeft = (TextView) itemView.findViewById(R.id.count_left);
//        btnEarn = (Button) itemView.findViewById(R.id.btn_earn);
        earningType = (TextView) itemView.findViewById(R.id.earning_type);
        adCard = (CardView) itemView.findViewById(R.id.adcard);

    }
}
