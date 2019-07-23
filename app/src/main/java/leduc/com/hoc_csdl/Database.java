package leduc.com.hoc_csdl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class Database extends SQLiteOpenHelper {

    // khởi tạo constructor
    // context: dùng ở class nào thì gọi
    // name   :    tên database
    // factory: con trỏ
    // version: từ 1 trở lên
    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // truy vấn ko trả kết quả
    // CREATE, ISNERT, UPDATATE, DELETE
    public void queryData(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }


    // truy vấn có trả kết quả SELECT
    public Cursor getData(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    public void InsertDoVat(String ten, String mota, byte[] hinh) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "INSERT INTO doVat VALUES (null, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        // phân tích dữ liệu xong thì xóa đi
        statement.clearBindings();

        statement.bindString(1, ten);
        statement.bindString(2, mota);
        statement.bindBlob(3, hinh);

        // insert vào csdl
        statement.executeInsert();
    }

    // ko dùng đến
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
