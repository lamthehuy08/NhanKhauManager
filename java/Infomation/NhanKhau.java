package Infomation;

public class NhanKhau extends Info{
    private String CMND;
    private String ngaySinh;
    private String queQuan;
    private String DanToc;
    private String NgheNghiep;
    private String NoiLamViec;
    private String SoCMND;
    private String NgayCap;
    private String NoiCap;
    private String NgayDangKyThuongTru;
    private String ThuongTruBefore;
    private String NgayChuyenDi;
    private String NoiChuyenDen;
    public NhanKhau(String maho, String name){
        this.setMaHo(maho);
        this.setTen(name);
    }

    public NhanKhau(String maho,String name, String gioitinh,int tuoi,boolean tvtt){
        super.setMaHo(maho);
        super.setTen(name);
        super.setGioiTinh(gioitinh);
        super.setTuoi(tuoi);
        super.setTvtt(tvtt);
    }

    public String getCMND() {
        return CMND;
    }

    public void setCMND(String CMND) {
        this.CMND = CMND;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getQueQuan() {
        return queQuan;
    }

    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }

    public String getDanToc() {
        return DanToc;
    }

    public void setDanToc(String danToc) {
        DanToc = danToc;
    }

    public String getNgheNghiep() {
        return NgheNghiep;
    }

    public void setNgheNghiep(String ngheNghiep) {
        NgheNghiep = ngheNghiep;
    }

    public String getNoiLamViec() {
        return NoiLamViec;
    }

    public void setNoiLamViec(String noiLamViec) {
        NoiLamViec = noiLamViec;
    }

    public String getSoCMND() {
        return SoCMND;
    }

    public void setSoCMND(String soCMND) {
        SoCMND = soCMND;
    }

    public String getNgayCap() {
        return NgayCap;
    }

    public void setNgayCap(String ngayCap) {
        NgayCap = ngayCap;
    }

    public String getNoiCap() {
        return NoiCap;
    }

    public void setNoiCap(String noiCap) {
        NoiCap = noiCap;
    }

    public String getNgayDangKyThuongTru() {
        return NgayDangKyThuongTru;
    }

    public void setNgayDangKyThuongTru(String ngayDangKyThuongTru) {
        NgayDangKyThuongTru = ngayDangKyThuongTru;
    }

    public String getThuongTruBefore() {
        return ThuongTruBefore;
    }

    public void setThuongTruBefore(String thuongTruBefore) {
        ThuongTruBefore = thuongTruBefore;
    }

    public String getNgayChuyenDi() {
        return NgayChuyenDi;
    }

    public void setNgayChuyenDi(String ngayChuyenDi) {
        NgayChuyenDi = ngayChuyenDi;
    }

    public String getNoiChuyenDen() {
        return NoiChuyenDen;
    }

    public void setNoiChuyenDen(String noiChuyenDen) {
        NoiChuyenDen = noiChuyenDen;
    }


}
