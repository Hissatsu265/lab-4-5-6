package com.example.qlsv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterSinhVien extends RecyclerView.Adapter<AdapterSinhVien.ViewHolder> {
    MainActivity context;
    ArrayList<SinhVien> sinhVienArrayList;


    public AdapterSinhVien(MainActivity context, ArrayList<SinhVien> sinhVienArrayList) {
        this.context = context;
        this.sinhVienArrayList = sinhVienArrayList;
    }

    @NonNull
    @Override
    public AdapterSinhVien.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from((parent.getContext()));
        View itemView = layoutInflater.inflate(R.layout.layout_sv,parent,false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSinhVien.ViewHolder holder, int position) {
        holder.tv_ten.setText(sinhVienArrayList.get(position).Ten);
        holder.tv_dtb.setText("Đtb: " +sinhVienArrayList.get(position).Dtb+"");
        holder.tv_mssv.setText("MSSV: " +sinhVienArrayList.get(position).MSSV+"");

    }

    @Override
    public int getItemCount() {
        return sinhVienArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_ten,tv_mssv,tv_dtb;
        Button btn_edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ten = (TextView)itemView.findViewById(R.id.tv_ten);
            tv_dtb = (TextView) itemView.findViewById((R.id.tv_dtb));
            tv_mssv = (TextView) itemView.findViewById(R.id.tv_mssv);
            btn_edit = (Button)itemView.findViewById(R.id.btn_Edit);

            tv_ten.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tv_dtb.getVisibility()== View.VISIBLE)
                    {
                        tv_dtb.setVisibility(View.GONE);
                        tv_mssv.setVisibility(View.GONE);
                    }
                    else
                    {
                        tv_dtb.setVisibility(View.VISIBLE);
                        tv_mssv.setVisibility(View.VISIBLE);
                    }
                }
            });

            tv_ten.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(context, "Đã xóa " + sinhVienArrayList.get(getAdapterPosition()).Ten, Toast.LENGTH_SHORT).show();
                    context.DeleteTask(sinhVienArrayList.get(getAdapterPosition()).MSSV);
                    context.getTask();
                    return false;
                }
            });

            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.openDialogEditSV(sinhVienArrayList.get(getAdapterPosition()).MSSV,sinhVienArrayList.get(getAdapterPosition()).Ten,sinhVienArrayList.get(getAdapterPosition()).Dtb);
                    context.getTask();

                }
            });


        }
    }


}
