package Infomation;

public class KhoanThu extends Info{

    private double DaThu;
    private double ConThieu;
    private double Tong;
    private double UngHo;
    private double TBLS;

    public double getTBLS() {
        return TBLS;
    }

    public void setTBLS(double TBLS) {
        this.TBLS = TBLS;
    }

    public double getTHIEUNHI() {
        return THIEUNHI;
    }

    public void setTHIEUNHI(double THIEUNHI) {
        this.THIEUNHI = THIEUNHI;
    }

    public double getNGUOINGHEO() {
        return NGUOINGHEO;
    }

    public void setNGUOINGHEO(double NGUOINGHEO) {
        this.NGUOINGHEO = NGUOINGHEO;
    }

    public double getLULUT() {
        return LULUT;
    }

    public void setLULUT(double LULUT) {
        this.LULUT = LULUT;
    }

    private double THIEUNHI;
    private double NGUOINGHEO;
    private double LULUT;


    public double getTong() {
        return Tong;
    }

    public void setTong(double tong) {
        Tong = tong;
    }

    public double getUngHo() {
        return UngHo;
    }

    public void setUngHo(double ungHo) {
        UngHo = ungHo;
    }

    public double getDaThu() {
        return DaThu;
    }
    public void setDaThu(double DaThu) {
        this.DaThu = DaThu;
    }
    public double getConThieu() {
        return ConThieu;
    }
    public void setConThieu(double conPhaiDong) {
        ConThieu = conPhaiDong;
    }
    public KhoanThu(String maHo, String ten, double tong,double soTienDaThu, double conPhaiDong,
                    double TBLS,double THIEUNHI,double NGUOINGHEO,double LULUT,double ungho) {
        this.setMaHo(maHo);
        this.setTen(ten);
        Tong = tong;
        DaThu = soTienDaThu;
        ConThieu = conPhaiDong;
        UngHo = ungho;
        this.TBLS= TBLS;
        this.NGUOINGHEO = NGUOINGHEO;
        this.LULUT = LULUT;
        this.THIEUNHI = THIEUNHI;
    }
}
