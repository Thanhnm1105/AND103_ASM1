
package com.example.and103_buoi1;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {

    ListView lvMain;
    List<CarModel> listCarModel;

    CarAdapter carAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lvMain = findViewById(R.id.listviewMain);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<List<CarModel>> call = apiService.getCars();

        call.enqueue(new Callback<List<CarModel>>() {
            @Override
            public void onResponse(Call<List<CarModel>> call, Response<List<CarModel>> response) {
                if (response.isSuccessful()) {
                    listCarModel = response.body();

                    carAdapter = new CarAdapter(getApplicationContext(), listCarModel);

                    lvMain.setAdapter(carAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<CarModel>> call, Throwable t) {
                Log.e("Main", t.getMessage());
            }
        });
        lvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                CarModel xeCanXoa = listCarModel.get(position);

                // Tạo Dialog xác nhận trước khi xóa
                new AlertDialog.Builder(Home.this)
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc chắn muốn xóa xe này không?")
                        .setPositiveButton("Xóa", (dialog, which) -> {
                            // Gọi API xóa xe
                            Call<Void> callXoaXe = apiService.xoaXe(xeCanXoa.get_id());

                            callXoaXe.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        listCarModel.remove(position);
                                        carAdapter.notifyDataSetChanged();
                                        Toast.makeText(Home.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Log.e("Main", t.getMessage());
                                }
                            });
                        })
                        .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss()) // Đóng Dialog khi hủy
                        .show();

                return true; // Trả về true để xử lý sự kiện long click
            }
        });



        findViewById(R.id.btn_add).setOnClickListener(v -> {
            CarModel xe = new CarModel("Xe 1411", 2023, "Toyota", 1200,"");

            Call<List<CarModel>> callAddXe = apiService.addCar(xe);

            callAddXe.enqueue(new Callback<List<CarModel>>() {
                @Override
                public void onResponse(Call<List<CarModel>> call, Response<List<CarModel>> response) {
                    if (response.isSuccessful()) {

                        listCarModel.clear();

                        listCarModel.addAll(response.body());

                        carAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<CarModel>> call, Throwable t) {
                    Log.e("Main", t.getMessage());
                }
            });
        });
        // Gọi API để cập nhật xe
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CarModel xeToEdit = listCarModel.get(position);

                // Tạo View cho Dialog sửa thông tin xe
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_car, null);
                EditText edtName = dialogView.findViewById(R.id.edtName);
                EditText edtNamSX = dialogView.findViewById(R.id.edtNamSX);
                EditText edtHang = dialogView.findViewById(R.id.edtHang);
                EditText edtGia = dialogView.findViewById(R.id.edtGia);

                // Hiển thị dữ liệu hiện tại của xe trong các EditText
                edtName.setText(xeToEdit.getTen());
                edtNamSX.setText(String.valueOf(xeToEdit.getNamSX()));
                edtHang.setText(xeToEdit.getHang());
                edtGia.setText(String.valueOf(xeToEdit.getGia()));

                new AlertDialog.Builder(Home.this)
                        .setTitle("Sửa thông tin xe")
                        .setView(dialogView)
                        .setPositiveButton("Lưu", (dialog, which) -> {
                            // Lấy dữ liệu từ EditText
                            String newName = edtName.getText().toString();
                            int newNamSX = Integer.parseInt(edtNamSX.getText().toString());
                            String newHang = edtHang.getText().toString();
                            double newGia = Double.parseDouble(edtGia.getText().toString());


                            // Tạo đối tượng CarModel với dữ liệu đã thay đổi
                            CarModel updatedCar = new CarModel(xeToEdit.get_id(), newName, newNamSX, newHang, newGia, xeToEdit.getAnh());


                            // Gọi API cập nhật xe
                            Call<List<CarModel>> callUpdateCar = apiService.updateCar(xeToEdit.get_id(), updatedCar);
                            callUpdateCar.enqueue(new Callback<List<CarModel>>() {
                                @Override
                                public void onResponse(Call<List<CarModel>> call, Response<List<CarModel>> response) {
                                    if (response.isSuccessful()) {
                                        // Cập nhật danh sách xe trong adapter
                                        listCarModel.clear();
                                        listCarModel.addAll(response.body());
                                        carAdapter.notifyDataSetChanged();
                                        Toast.makeText(Home.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Home.this, "Cập nhật thất bại: " + response.message(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<CarModel>> call, Throwable t) {
                                    Toast.makeText(Home.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        })
                        .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });




    }
}