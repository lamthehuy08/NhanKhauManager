package Controller.KhoanThu;

import Infomation.KhoanThu;
import com.example.nhankhaumanager.DBUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditKhoanThu {
    private ObservableList<KhoanThu> list;
    private KhoanThu kt;
    private String dot;
    @FXML private AnchorPane scenePane;
    @FXML private Label MAHO;
    @FXML private Label TENCHUHO;
    @FXML private Label NGAYUPDATE;
    @FXML private Label DANOP;
    @FXML private Label CONTHIEU;
    @FXML private Label notify;
    @FXML private TextField NOPTHEM;
    @FXML private TextField TBLS;
    @FXML private TextField THIEUNHI;
    @FXML private TextField NGUOINGHEO;
    @FXML private TextField LULUT;
    private float UNGHODaNop;
    private float TBLSDaNop;
    private float THIEUNHIDaNop;
    private float NGUOINGHEODaNop;
    private float LULUTDaNop;
    private float nopthem;
    private float tblsNopThem;
    private float thieunhiNopThem;
    private float nguoingheoNopThem;
    private float lulutNopThem;
    public void init(ObservableList<KhoanThu>l,KhoanThu kt,String dot ){
        this.list = l;
        this.kt = kt;
        this.dot = dot;
        initialize();
    }
    public void CapNhat(){
        if(!Error()){
            try {
                Connection conn = DBUtil.getConnection();

                float TongNop = Float.parseFloat(DANOP.getText()) + nopthem;

                String sql = "UPDATE KHOANTHUDOT" + dot +" SET DATHU = ?, TBLS = ?,THIEUNHI = ?," +
                        " NGUOINGHEO = ?,LULUT = ?,UNGHO = ? WHERE MAHO = ?;";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setFloat(1,TongNop);
                pst.setFloat(2,TBLSDaNop + tblsNopThem);
                pst.setFloat(3,THIEUNHIDaNop + thieunhiNopThem);
                pst.setFloat(4,NGUOINGHEODaNop + nguoingheoNopThem);
                pst.setFloat(5,LULUTDaNop + lulutNopThem);
                pst.setString(7,kt.getMaHo());
                pst.setFloat(6,UNGHODaNop + tblsNopThem+thieunhiNopThem+nguoingheoNopThem+lulutNopThem);
                int check = pst.executeUpdate();
                if(check > 0){
                    DANOP.setText(String.valueOf(TongNop));
                    CONTHIEU.setText(String.valueOf(Float.parseFloat(CONTHIEU.getText()) - nopthem));
                    new LichSuCapNhatKhoanThu(kt.getMaHo(),dot).CapNhatLichSu(nopthem,tblsNopThem,thieunhiNopThem,nguoingheoNopThem,lulutNopThem);
                }
                DBUtil.Close(conn);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public void Huy(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("WARNING!");
        alert.setContentText("You are about to cancer");
        alert.setHeaderText("Are you sure to cancer?");
        if(alert.showAndWait().get() == ButtonType.OK){
            Stage stage = (Stage) scenePane.getScene().getWindow();
            stage.close();
        }
    }
    public void NopTat(ActionEvent e){
        NOPTHEM.setText(CONTHIEU.getText());
    }
    private boolean CheckIsNumb(String s){
        for(int i = 0;i<s.length();i++){
            if(s.charAt(i) == '.')continue;
            if(s.charAt(i) < '0' || s.charAt(i) > '9'){
                return false;
            }
        }
        return true;
    }
    private boolean Error(){

        if(NOPTHEM.getText().isEmpty()){
            notify.setTextFill(Color.RED);
            notify.setText("You must fill the field 'số tiền nộp thêm'");
            return true;
        }
        else{
            if(!CheckIsNumb(NOPTHEM.getText())){
                notify.setTextFill(Color.RED);
                notify.setText("'số tiền nộp thêm' must be a number");
                return true;
            }
            else{
                nopthem = Float.parseFloat(NOPTHEM.getText());
                if(nopthem > kt.getConThieu()){
                    notify.setTextFill(Color.RED);
                    notify.setText("'số tiền nộp thêm' must be smaller than or equal to 'còn thiếu'");
                    return true;
                }
                else{
                    notify.setTextFill(Color.GREEN);
                    notify.setText("Update successfully!");
                }
            }
        }
        if(!TBLS.getText().isEmpty()){
            if(!CheckIsNumb(TBLS.getText())){
                notify.setTextFill(Color.RED);
                notify.setText("'Ủng hộ ngày thương binh-liệt sỹ 27/07:' must be a number");
                return true;
            }
            else tblsNopThem = Float.parseFloat(TBLS.getText());
        }else tblsNopThem = 0;

        if(!THIEUNHI.getText().isEmpty()){
            if(!CheckIsNumb(THIEUNHI.getText())){
                notify.setTextFill(Color.RED);
                notify.setText("'Ủng hộ ngày tết thiếu nhi::' must be a number");
                return true;
            }else thieunhiNopThem = Float.parseFloat(THIEUNHI.getText());
        }else thieunhiNopThem = 0;

        if(!NGUOINGHEO.getText().isEmpty()){
            if(!CheckIsNumb(NGUOINGHEO.getText())){
                notify.setTextFill(Color.RED);
                notify.setText("'Ủng hộ vì người nghèo:' must be a number");
                return true;
            }
            else nguoingheoNopThem = Float.parseFloat(NGUOINGHEO.getText());
        }else nguoingheoNopThem = 0;

        if(!LULUT.getText().isEmpty()){
            if(!CheckIsNumb(LULUT.getText())){
                notify.setTextFill(Color.RED);
                notify.setText("'Ủng hộ đồng bào lũ lụt' must be a number");
                return true;
            }
            else lulutNopThem = Float.parseFloat(LULUT.getText());
        }else lulutNopThem = 0;
        return false;
    }
    private void initialize() {
        try {
            Connection conn = DBUtil.getConnection();

            String sql = "SELECT * FROM CACHO, KHOANTHUDOT" + dot+
                    " WHERE CACHO.MAHO = KHOANTHUDOT"+dot+".MAHO AND CACHO.MAHO = ?;";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,kt.getMaHo());
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                MAHO.setText("Mã hộ: " + rs.getString("MAHO"));
                TENCHUHO.setText("Tên chủ hộ: " + rs.getString("TENCHUHO"));
                DANOP.setText(rs.getFloat("DATHU") + "");
                CONTHIEU.setText((rs.getFloat("TONG")- rs.getFloat("DATHU")) + "");
                THIEUNHIDaNop = Float.parseFloat(rs.getString("THIEUNHI"));
                TBLSDaNop = Float.parseFloat(rs.getString("TBLS"));
                NGUOINGHEODaNop = Float.parseFloat(rs.getString("NGUOINGHEO"));
                LULUTDaNop = Float.parseFloat(rs.getString("LULUT"));
                UNGHODaNop= Float.parseFloat(rs.getString("UNGHO"));
            }

            NGAYUPDATE.setText("Ngày cập nhật: "+DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now()));
            DBUtil.Close(conn);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
