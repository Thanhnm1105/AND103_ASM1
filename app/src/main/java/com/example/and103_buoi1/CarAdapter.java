package com.example.and103_buoi1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CarAdapter extends BaseAdapter {

    private List<CarModel> carModelList;
    private Context context;
    private LayoutInflater inflater;

    public CarAdapter(Context context, List<CarModel> carModelList) {
        this.context = context;
        this.carModelList = carModelList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return carModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return carModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_car, parent, false);
            holder = new ViewHolder();

            holder.tvID = convertView.findViewById(R.id.tvId);
            holder.imganh = convertView.findViewById(R.id.imganh);
            holder.tvName = convertView.findViewById(R.id.tvName);
            holder.tvNamSX = convertView.findViewById(R.id.tvNamSX);
            holder.tvHang = convertView.findViewById(R.id.tvHang);
            holder.tvGia = convertView.findViewById(R.id.tvGia);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CarModel car = carModelList.get(position);

        holder.tvID.setText(car.get_id());
        holder.tvName.setText(car.getTen());
        holder.tvNamSX.setText(String.valueOf(car.getNamSX()));
        holder.tvHang.setText(car.getHang());
        holder.tvGia.setText(String.format("%.2f", car.getGia()));

        // Kiểm tra nếu có URL ảnh và tải ảnh vào ImageView bằng Picasso
        if (car.getAnh() != null && !car.getAnh().isEmpty()) {
            Picasso.get().load(car.getAnh()).into(holder.imganh);
        } else {
            holder.imganh.setImageResource(R.drawable.ic_launcher_foreground); // Hình ảnh mặc định nếu không có URL
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView tvID, tvName, tvNamSX, tvHang, tvGia;
        ImageView imganh;
    }
}
