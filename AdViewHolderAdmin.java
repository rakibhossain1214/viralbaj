package com.example.viralbaj;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.vision.text.Line;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdViewHolderAdmin extends RecyclerView.ViewHolder {

    public TextView tvAdownerId;
    public TextView tvAdownerName;
    public TextView tvAdurl;
    public TextView tvBuyingType;
    public TextView tvAdCount;
    public TextView tvBuyingAmount;
    public TextView tvAccountType;
    public TextView tvTrxId;
    public TextView tvAccountNumber;
    public TextView tvAddate;
    public TextView adStatus;

    public Button btnAccept;
    public Button btnReject;

    public CardView adCard;

    public AdViewHolderAdmin(@NonNull View itemView) {
        super(itemView);

        tvAdownerId = (TextView) itemView.findViewById(R.id.adownerid);
        tvAdownerName = (TextView) itemView.findViewById(R.id.adownername);
        tvAdurl = (TextView) itemView.findViewById(R.id.adurl);
        tvBuyingType = (TextView) itemView.findViewById(R.id.buyingtype);
        tvAdCount = (TextView) itemView.findViewById(R.id.adcount);
        tvBuyingAmount = (TextView) itemView.findViewById(R.id.buyingamount);
        tvAccountType = (TextView) itemView.findViewById(R.id.buyingaccounttype);
        tvTrxId = (TextView) itemView.findViewById(R.id.buyingtrxid);
        tvAccountNumber = (TextView) itemView.findViewById(R.id.buyingaccountnumber);
        tvAddate = (TextView) itemView.findViewById(R.id.addate);
        adCard = (CardView) itemView.findViewById(R.id.adcard);
        adStatus = (TextView) itemView.findViewById(R.id.adstatus);

        btnAccept = (Button) itemView.findViewById(R.id.btn_accept);
        btnReject = (Button) itemView.findViewById(R.id.btn_reject);
    }
}
