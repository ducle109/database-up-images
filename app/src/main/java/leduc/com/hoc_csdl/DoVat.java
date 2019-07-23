package leduc.com.hoc_csdl;

public class DoVat {
    private int id;
    private String ten;
    private String mota;
    private byte[] hinh;

    public DoVat(int id, String ten, String mota, byte[] hinh) {
        this.id = id;
        this.ten = ten;
        this.mota = mota;
        this.hinh = hinh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }
}
