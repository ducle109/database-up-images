package leduc.com.hoc_csdl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class CongViecAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<CongViec> congViecList;

    public CongViecAdapter(MainActivity context, int layout, List<CongViec> congViecList) {
        this.context = context;
        this.layout = layout;
        this.congViecList = congViecList;
    }


    @Override
    public int getCount() {
        // trả về tất cả list công việc
        return congViecList.size();
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

        // nếu mà chưa tồn tại thì mới tạo
        if(view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtName      = (TextView) view.findViewById(R.id.txtName);
            holder.img_delete   = (ImageView) view.findViewById(R.id.img_delete);
            holder.img_check    = (ImageView) view.findViewById(R.id.img_check);

            // truyền những view đã tạo sang holder
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // lấy từ list ra
        final CongViec congViec = congViecList.get(position);

        // lấy ra text trong công việc
        holder.txtName.setText(congViec.getTen());

        holder.img_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogSua(congViec.getTen(), congViec.getId());
            }
        });

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogXoa(congViec.getTen(), congViec.getId());
            }
        });


        return view;
    }

    private class ViewHolder {
        private TextView    txtName;
        private ImageView   img_delete;
        private ImageView   img_check;
    }
}
