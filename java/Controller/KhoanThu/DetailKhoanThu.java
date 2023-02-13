package Controller.KhoanThu;

import Infomation.KhoanThu;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DetailKhoanThu {
    private KhoanThu kt;
    @FXML private Label MAHO;
    @FXML private Label TENCHUHO;
    @FXML private Label TONGCANNOP;
    @FXML private Label DANOP;
    @FXML private Label CONTHIEU;
    @FXML private Label TBLS;
    @FXML private Label THIEUNHI;
    @FXML private Label NGUOINGHEO;
    @FXML private Label LULUT;
    @FXML private Label TONGUNGHO;
    protected void init(KhoanThu kt){
        this.kt = kt;
        loadDetail();
    }
    private void loadDetail(){
        MAHO.setText("Mã hộ: " + kt.getMaHo());
        TENCHUHO.setText("Tên chủ hộ: " + kt.getTen());
        TONGCANNOP.setText(kt.getTong()+ " VNĐ");
        DANOP.setText(kt.getDaThu()+ " VNĐ");
        CONTHIEU.setText((kt.getTong() - kt.getDaThu())+ " VNĐ");
        TBLS.setText(kt.getTBLS()+ " VNĐ");
        THIEUNHI.setText(kt.getTHIEUNHI()+ " VNĐ");
        NGUOINGHEO.setText(kt.getNGUOINGHEO()+ " VNĐ");
        LULUT.setText(kt.getLULUT()+ " VNĐ");
        TONGUNGHO.setText((kt.getLULUT()+kt.getTBLS() + kt.getNGUOINGHEO()+kt.getTHIEUNHI())+ " VNĐ");
    }
}
