package com.example.sqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ComputerSQLite computerSQLite;
    ListView list;
    ArrayList<Computer> arrayList;
    ComputerAdapter adapter;
    Button buttnThem;
    EditText edtTen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //anh xa listview
        list = (ListView)findViewById(R.id.list_maytinh);
        edtTen = (EditText) findViewById(R.id.editTen);
        buttnThem = (Button) findViewById(R.id.btnThem);

        buttnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mayTinh = edtTen.getText().toString().trim();
                if(TextUtils.isEmpty(mayTinh)){
                    Toast.makeText(MainActivity.this, "Nhập tên máy cần thêm !",Toast.LENGTH_SHORT).show();
                    return;
                }
                computerSQLite.QueryData("INSERT INTO ComputerTable VALUES (null, '"+ mayTinh +"')");
                //Chay du lieu
                action();
            }
        });

        arrayList = new ArrayList<>();
        adapter = new ComputerAdapter(this, R.layout.item_maytinh, arrayList);
        list.setAdapter(adapter);

        //Tao database:
        computerSQLite = new ComputerSQLite(this, "computer.sqlite",null,1);

        //tao bang
        computerSQLite.QueryData("CREATE TABLE IF NOT EXISTS ComputerTable(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenComputer VARCHAR(200))");

        //Them du lieu(Chỉ chạy 1 lần -> tránh lặp lại dữ liệu)
//        computerSQLite.QueryData("INSERT INTO ComputerTable VALUES (null, 'Máy tính 1')");
//        computerSQLite.QueryData("INSERT INTO ComputerTable VALUES (null, 'Máy tính 2')");
//        computerSQLite.QueryData("INSERT INTO ComputerTable VALUES (null, 'Máy tính 3')");
        //Hien thi
        action();
    }

    public void DiaglogDelete(String ten,final int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có đồng ý xóa -- "+ten+" -- không ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                computerSQLite.QueryData("DELETE FROM ComputerTable WHERE Id= '"+id+"'");
                //Chay Data
                action();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
    //Sự kiện update
    public  void DiaglogUpdate(String tenmaytinh, final int id ){
        Dialog diaglog = new Dialog(this);
        diaglog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        diaglog.setContentView(R.layout.item_sua);
        EditText edtSua = (EditText)diaglog.findViewById(R.id.editSua);
        Button btnSua = (Button)diaglog.findViewById(R.id.btnUpdate);
        Button btnHuy = (Button)diaglog.findViewById(R.id.btnCancel);

        edtSua.setText(tenmaytinh);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diaglog.dismiss();
            }
        });
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenUpdate = edtSua.getText().toString().trim();
                if (TextUtils.isEmpty(tenUpdate)){
                    Toast.makeText(MainActivity.this,"Chưa nhập !", Toast.LENGTH_SHORT).show();
                    diaglog.dismiss();
                    return;
                }

                computerSQLite.QueryData("UPDATE ComputerTable SET TenComputer = '"+tenUpdate+"' WHERE Id ='"+ id +"'");
                diaglog.dismiss();
                //action Getdata
                action();

            }
        });

        diaglog.show();
    }
    private void action(){
        Cursor dataComputer = computerSQLite.GetData("SELECT * FROM ComputerTable");
                arrayList.clear();
                while (dataComputer.moveToNext()){
            String ten = dataComputer.getString(1);
            //Hien thi thong bao
            //Toast.makeText(this,ten,Toast.LENGTH_SHORT).show();
            //Hien thi list
            int id = dataComputer.getInt(0);
            arrayList.add(new Computer(id, ten));
        }
                adapter.notifyDataSetChanged();
    }
}