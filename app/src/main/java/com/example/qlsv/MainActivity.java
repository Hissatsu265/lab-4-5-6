package com.example.qlsv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<SinhVien> arrayList;
    RecyclerView recyclerView;
    Database database;
    AdapterSinhVien adapterSinhVien;
    Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        btn2=findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);
            }
        });
    }

    public void initView() {
        recyclerView = findViewById(R.id.rv_list_sv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        arrayList = new ArrayList<>();
        adapterSinhVien = new AdapterSinhVien(this, arrayList);
        recyclerView.setAdapter(adapterSinhVien);

        // Initialize and/or open the database
        database = new Database(this, "dsSV.sqlite", null, 1);

        // Create table if not exists
        database.QueryData("CREATE TABLE IF NOT EXISTS DS_SinhVien(Id INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR(200), DTB VARCHAR(200))");

        // Insert sample data into DS_SinhVien table
//        database.QueryData("INSERT INTO DS_SinhVien VALUES(null,'Lionel Messi','8.5')");
//        database.QueryData("INSERT INTO DS_SinhVien VALUES(null,'Cristiano Ronaldo','9.5')");

        // Fetch and display data
        getTask();
    }

    void getTask() {
        arrayList.clear();
        Cursor data = database.GetData("SELECT * FROM DS_SinhVien");
        while (data.moveToNext()) {
            arrayList.add(new SinhVien(data.getInt(0), data.getString(1), data.getString(2)));
        }
        adapterSinhVien.notifyDataSetChanged(); // Notify adapter once after data retrieval
    }

    void DeleteTask(int id)
    {
        database.QueryData("DELETE FROM DS_SinhVien WHERE ID ='"+id +"' ");
    }
    public static boolean kiemTraTen(String ten) {
        // Kiểm tra xem chuỗi không rỗng
        if (ten == null || ten.isEmpty()) {
            return false;
        }

        // Kiểm tra xem chuỗi chỉ chứa các ký tự chữ cái và khoảng trắng
        for (char kyTu : ten.toCharArray()) {
            if (!Character.isLetter(kyTu) && kyTu != ' ') {
                return false;
            }
        }

        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {



        openDialogAddSV();

        return super.onOptionsItemSelected(item);

    }

    private void openDialogAddSV() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_sv);
        Button btnAdd = (Button)dialog.findViewById(R.id.btnAdd);
        Button btnCancle = (Button)dialog.findViewById(R.id.btnCancle);
        EditText et_Ten = (EditText)dialog.findViewById(R.id.et_add_Ten);
        EditText et_Dtb = (EditText)dialog.findViewById(R.id.et_add_Dtb);


        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kiemTraTen(et_Ten.getText().toString()) ) {
                    database.QueryData("INSERT INTO DS_SinhVien (Name, DTB) VALUES ('"+ et_Ten.getText().toString() + "', '"+ et_Dtb.getText().toString()+"')");
                    getTask();
                    dialog.dismiss();
                }
                else
                    Toast.makeText(MainActivity.this, "Invalid name", Toast.LENGTH_SHORT).show();

            }
        });

        dialog.show();
    }
    public void openDialogEditSV(int id,String Ten, String dtb) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_sv);
        Button btnAdd = (Button)dialog.findViewById(R.id.btnAdd);
        Button btnCancle = (Button)dialog.findViewById(R.id.btnCancle);
        EditText et_Ten = (EditText)dialog.findViewById(R.id.et_add_Ten);
        EditText et_Dtb = (EditText)dialog.findViewById(R.id.et_add_Dtb);
        et_Dtb.setText(dtb);
        et_Ten.setText(Ten);


        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kiemTraTen(et_Ten.getText().toString()) ) {

                    database.QueryData("UPDATE DS_SinhVien SET Name = '" + et_Ten.getText().toString() + "', DTB = '" + et_Dtb.getText().toString() + "' WHERE Id = " + id);
                    getTask();
                    dialog.dismiss();
                }
                else
                    Toast.makeText(MainActivity.this, "Invalid name", Toast.LENGTH_SHORT).show();

            }
        });

        dialog.show();
    }


}
