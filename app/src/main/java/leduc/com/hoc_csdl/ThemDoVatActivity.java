package leduc.com.hoc_csdl;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ThemDoVatActivity extends AppCompatActivity {
    private Button btnThemSp, btnHuySp;
    private EditText edt_name_sp, edt_mieuta_sp;
    private ImageView no_img;
    private ImageButton imgBtnCamera, imgBtnFile;

    final int REQUEST_CODE_CAMERA = 123;
    final int REQUEST_CODE_FOLDER = 456;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_do_vat);
        init();

        btnThemSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // chuyển data imageView -> byte[]
                BitmapDrawable bitmapDrawable = (BitmapDrawable) no_img.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                byte[] hinhAnh = byteArray.toByteArray();

                MainActivity.database.InsertDoVat(
                        edt_name_sp.getText().toString(),
                        edt_mieuta_sp.getText().toString(),
                        hinhAnh
                );
                showToast("Đã Thêm");
                startActivity(new Intent(ThemDoVatActivity.this, MainActivity.class));
            }
        });

        // dùng để kết nối với máy ảnh của đt
        imgBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // mở camera chụp ảnh
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });

        // dùng để kết nối với thư mục của đt
        imgBtnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        });
    }

    // dùng để kết nối với máy ảnh của đt
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            // gắn hình vào no img
            no_img.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            assert uri != null;
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                no_img.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    public void init() {
        btnThemSp       = (Button) findViewById(R.id.btn_them_sp);
        btnHuySp        = (Button) findViewById(R.id.btn_huy_sp);
        edt_name_sp     = (EditText) findViewById(R.id.edt_name_sp);
        edt_mieuta_sp   = (EditText) findViewById(R.id.edt_mieuta_sp);
        no_img          = (ImageView) findViewById(R.id.no_img);
        imgBtnCamera     = (ImageButton) findViewById(R.id.img_btn_camera);
        imgBtnFile      = (ImageButton) findViewById(R.id.img_btn_file);
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
