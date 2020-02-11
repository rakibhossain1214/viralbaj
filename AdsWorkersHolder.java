package com.example.viralbaj;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdsWorkersHolder  extends RecyclerView.ViewHolder {
    public CircleImageView profileImage;
    public TextView userName, userGender, userLocation, userAge;
    public CardView userCard;

    public AdsWorkersHolder(@NonNull View itemView) {
        super(itemView);

        userName = (TextView) itemView.findViewById(R.id.username);
        userGender = (TextView) itemView.findViewById(R.id.usergender);
        userLocation = (TextView) itemView.findViewById(R.id.userlocation);
        userAge = (TextView) itemView.findViewById(R.id.userage);
        userCard = (CardView) itemView.findViewById(R.id.user_card);

        profileImage = (CircleImageView) itemView.findViewById(R.id.profile_image);
    }
}
