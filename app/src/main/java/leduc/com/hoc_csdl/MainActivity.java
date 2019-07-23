package leduc.com.hoc_csdl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText edtAdd;
    private Button btnThem;
    private Button btnHuy;

    private EditText edt_sua;
    private Button btnXacNhan;
    private Button btnCancel;

    public static Database database;
    private ListView lvCongViec;
    private List<CongViec> congViecList;
    private CongViecAdapter congViecAdapter;

    private Button btnThemDoVat;
    private ListView lvDoVat;
    private List<DoVat> listDoVat;
    private DoVatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_add_do_vat);

        btnThemDoVat    = (Button) findViewById(R.id.btnThemDoVat);
        lvDoVat         = (ListView) findViewById(R.id.lvDoVat);

        listDoVat = new ArrayList<DoVat>();
        adapter = new DoVatAdapter(this, R.layout.activity_hthi_dovat, listDoVat);
        lvDoVat.setAdapter(adapter);

        // tạo database
        database = new Database(this, "QuanLi.db", null, 1);
        database.queryData("CREATE TABLE IF NOT EXISTS doVat (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ten TEXT, " +
                "MoTa TEXT, " +
                "HinhAnh BLOB)");
        // lay du lieu ra
        Cursor cursor = database.getData("SELECT * FROM doVat");
        while (cursor.moveToNext()) {
            listDoVat.add(new DoVat(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getBlob(3)
            ));
        }


        btnThemDoVat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ThemDoVatActivity.class));
            }
        });




        /*init();
        // tạo database
        database = new Database(this, "Work.db", null, 1);
        congViecList = new ArrayList<CongViec>();
        congViecAdapter = new CongViecAdapter(this, R.layout.cong_viec, congViecList);

        // lấy dữ liệu đẩy la list
        lvCongViec.setAdapter(congViecAdapter);

        // tạo table congViec
        database.queryData("CREATE TABLE IF NOT EXISTS congViec (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenCV TEXT)");

        // insert dữ liệu
        //database.queryData("INSERT INTO congViec VALUES (null, 'Học Android')");

        getDataCongViec();*/
    }
    public void getDataCongViec() {
        // Lấy dữ liệu ra
        Cursor dataCongViec = database.getData("SELECT * FROM congViec");
        // xóa dữ liệu lúc đầu đi để cập nhật lại dữ liệu mới
        congViecList.clear();
        while (dataCongViec.moveToNext()) {
            String ten = dataCongViec.getString(1);
            int id = dataCongViec.getInt(0);
            congViecList.add(new CongViec(id, ten));
        }
        congViecAdapter.notifyDataSetChanged();
    }

    // thêm icon vào menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu_congviec, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // thêm dialog vào item menu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuAdd) {
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    // tạo hàm gọi dialog
    public void DialogThem() {
        final Dialog dialog = new Dialog(this);
        // tắt đi title
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        edtAdd          = (EditText) dialog.findViewById(R.id.edtAdd);
        btnThem         = (Button) dialog.findViewById(R.id.btnThem);
        btnHuy          = (Button) dialog.findViewById(R.id.btnHuy);
        // khi muốn thêm
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tencv = edtAdd.getText().toString();
                if(tencv.equals("")) {
                    showToast("Vui Lòng Nhập Công Việc");
                } else {
                    database.queryData("INSERT INTO congViec VALUES (null, '"+ tencv +"')");
                    showToast("Đã Thêm");
                    dialog.dismiss();
                    getDataCongViec();
                }
            }
        });

        // khi muốn hủy
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void DialogSua(String ten, final int id) {
        final Dialog dialog = new Dialog(this);
        // tắt đi title
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua);
        edt_sua            = (EditText) dialog.findViewById(R.id.edt_sua);
        btnXacNhan         = (Button) dialog.findViewById(R.id.btnXacNhan);
        btnCancel          = (Button) dialog.findViewById(R.id.btn_huy);

        edt_sua.setText(ten);
        // khi muốn thêm
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenMoi = edt_sua.getText().toString().trim();
                if(tenMoi.equals("")) {
                    showToast("Vui Lòng Nhập Công Việc");
                } else {
                    database.queryData("UPDATE congViec SET tenCV = '" + tenMoi + "' WHERE id = '" + id + "'");
                    showToast("Đã Cập Nhật");
                    dialog.dismiss();
                    getDataCongViec();
                }
            }
        });

        // khi muốn hủy
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void DialogXoa(String ten, final int id) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn Có Muốn Xóa " + ten + " Ko??");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.queryData("DELETE FROM congViec WHERE id = '" + id + "'");
                showToast("Đã Xóa Thành Công");
                getDataCongViec();
            }
        });

        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogXoa.show();

    }


    public void init() {
        lvCongViec      = (ListView) findViewById(R.id.lvCongViec);

    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
