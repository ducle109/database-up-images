package leduc.com.hoc_csdl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DoVatAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<DoVat> listDoVat;

    public DoVatAdapter(Context context, int layout, List<DoVat> listDoVat) {
        this.context = context;
        this.layout = layout;
        this.listDoVat = listDoVat;
    }

    @Override
    public int getCount() {
        return listDoVat.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            holder.imgListView = (ImageView) view.findViewById(R.id.imgListView);
            holder.txtTenDoVat = (TextView) view.findViewById(R.id.txt_ten_dv);
            holder.txtMieuTa   = (TextView) view.findViewById(R.id.txt_mieu_ta);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        DoVat doVat = listDoVat.get(position);
        // // chuyển byte[] -> bitmap
        byte[] hinhanh = doVat.getHinh();
        // 1: hình ảnh, 2: offset = 0 mặc định để decode hết, 3: chiều dài
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0, hinhanh.length);
        holder.txtTenDoVat.setText(doVat.getTen());
        holder.txtMieuTa.setText(doVat.getMota());
        holder.imgListView.setImageBitmap(bitmap);

        return view;
    }

    public class ViewHolder {
        ImageView imgListView;
        TextView txtTenDoVat;
        TextView txtMieuTa;
    }
}
