package Controller.KhoanThu;

import Infomation.KhoanThu;
import com.example.nhankhaumanager.DBUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainKhoanThu {
    private Label tong;
    private Label dathu;
    private String dot;
    private static int SoDot = 2;
    private ObservableList<KhoanThu> list;
    private TableView<KhoanThu> table;



    public void init(TableView<KhoanThu> tb,ObservableList<KhoanThu>l, Label t, Label d){
        this.table = tb;
        this.list = l;
        this.tong = t;
        this.dathu = d;
    }
    public void refresh(){
        list.clear();
        loadThongTin();
    }
    protected void loadThongTin(){
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();

            String sql = "SELECT * FROM CACHO, KHOANTHUDOT"+ dot +
                    " WHERE CACHO.MAHO = KHOANTHUDOT"+dot+".MAHO;";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            float counterTien = 0;
            int counterHo = 0;

            while(rs.next()){
                float tong = rs.getFloat("TONG");
                float dathu = rs.getFloat("DATHU");
                float ungho = rs.getFloat("UNGHO");
                float remain = tong - dathu;
                counterTien = counterTien + dathu + ungho;
                if(remain == 0)counterHo++;

                list.add(new KhoanThu(rs.getString("MAHO"),
                        rs.getString("TENCHUHO"),
                        tong,
                        dathu,
                        remain,
                        rs.getFloat("TBLS"),
                        rs.getFloat("THIEUNHI"),
                        rs.getFloat("NGUOINGHEO"),
                        rs.getFloat("LULUT"),
                        rs.getFloat("UNGHO")));
            }

            tong.setText("Tổng tiền đã thu của đợt: " + counterTien + " VNĐ");
            dathu.setText("Số hộ đã nộp đủ: " + counterHo);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DBUtil.Close(conn);
    }
    public void xem(String dot){
        list.clear();
        this.dot = dot.substring(4);
        loadThongTin();
    }
    public void CapNhatKhoanThu(){ // show scene to update;
        KhoanThu kt = table.getSelectionModel().getSelectedItem();

        if(kt!=null){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditKhoanThu.fxml"));
                Parent root = loader.load();
                EditKhoanThu editKhoanThu = loader.getController();
                editKhoanThu.init(list,kt,dot);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                stage.setOnCloseRequest(event ->{
                    event.consume();
                    editKhoanThu.Huy();
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void ChiTietKhoanThu(){
        KhoanThu kt = table.getSelectionModel().getSelectedItem();

        if(kt != null){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailKhoanThu.fxml"));
                Parent root = loader.load();
                DetailKhoanThu detailKhoanThu = loader.getController();
                detailKhoanThu.init(kt);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Chi tiết khoản thu");
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
    public void LichSuKhoanThu(){
        KhoanThu kt = table.getSelectionModel().getSelectedItem();

        if(kt != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LichSuCapNhatKhoanThu.fxml"));
            try {
                Parent root = loader.load();
                LichSuCapNhatKhoanThu lichSuCapNhatKhoanThu = loader.getController();
                lichSuCapNhatKhoanThu.init(kt.getMaHo(),dot);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void ThemKhoanThuAfterThemHo(String MH){
        try {
            Connection con = DBUtil.getConnection();

            for(int i = 1; i<=SoDot;i++){
                String sql = "INSERT INTO KHOANTHUDOT" + i +  " VALUES(?,?,?,?,?,?,?,?,?);";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1,MH);
                pst.setFloat(2,6000*12);
                pst.setFloat(3,0);
                pst.setFloat(4,0);
                pst.setFloat(5,0);
                pst.setFloat(6,0);
                pst.setFloat(7,0);
                pst.setFloat(8,0);
                pst.setString(9,null);
                pst.executeUpdate();
            }

            DBUtil.Close(con);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void UpdateKhoanThuAfterUpdateNhanKhau(String MH, int newSoLuong){
        try {
            Connection c = DBUtil.getConnection();
            String sql = "UPDATE FROM KHOANTHU SET TONG = ? WHERE MAHO = ?;";
            PreparedStatement pst = c.prepareStatement(sql);
            pst = c.prepareStatement(sql);
            pst.setFloat(1,newSoLuong * 6000*12);
            pst.setString(2,MH);
            pst.executeUpdate();
            DBUtil.Close(c);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void XoaKhoanThuAfterXoaHo(String MH){
        try {
            Connection c = DBUtil.getConnection();
            for(int i = 1;i<=SoDot;i++){
                String sql = "DELETE FROM KHOANTHUDOT" + i + " WHERE MAHO = ?;";
                PreparedStatement pst = c.prepareStatement(sql);
                pst.setString(1,MH);
                pst.executeUpdate();
            }
            DBUtil.Close(c);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
