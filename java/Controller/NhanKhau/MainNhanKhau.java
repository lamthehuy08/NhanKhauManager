package Controller.NhanKhau;

import Controller.KhoanThu.MainKhoanThu;
import Infomation.CacHo;
import Infomation.NhanKhau;
import com.example.nhankhaumanager.DBUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainNhanKhau {
     private CacHo ho;
     private TableView<NhanKhau>table;
     private Label QuanHeChuHo;
     private Label ten;
     private Label bidanh;
     private Label ngaysinh;
     private Label noisinh;
     private Label quequan;
     private Label dantoc;
     private Label ngheNghiep;
     private Label noilamviec;
     private Label soCMND;
     private Label ngaycap;
     private Label noicap;
     private Label ngaydangkythuongtru;
     private Label thuongtrubefore;
     private Label noichuyenden;
     private Label ngaychuyendi;
     private Label ghichu;
     private ObservableList<NhanKhau> list;
    public void init( Label QHCH,Label ten, Label bidanh, Label ngaysinh, Label noisinh, Label quequan, Label dantoc, Label ngheNghiep, Label noilamviec, Label soCMND, Label ngaycap, Label noicap, Label ngaydangkythuongtru, Label thuongtrubefore, Label noichuyenden, Label ngaychuyendi, Label ghichu) {
        this.ten = ten;
        this.QuanHeChuHo = QHCH;
        this.bidanh = bidanh;
        this.ngaysinh = ngaysinh;
        this.noisinh = noisinh;
        this.quequan = quequan;
        this.dantoc = dantoc;
        this.ngheNghiep = ngheNghiep;
        this.noilamviec = noilamviec;
        this.soCMND = soCMND;
        this.ngaycap = ngaycap;
        this.noicap = noicap;
        this.ngaydangkythuongtru = ngaydangkythuongtru;
        this.thuongtrubefore = thuongtrubefore;
        this.noichuyenden = noichuyenden;
        this.ngaychuyendi = ngaychuyendi;
        this.ghichu = ghichu;
    }

    public void init(CacHo ho, TableView<NhanKhau>tb,ObservableList<NhanKhau> list,Label lbMaho, Label lbTenChuHo, Label lbSoNhanKhau){
        this.table = tb;
        this.ho = ho;
        this.list = list;
        xemThongtinNhanKhau(true);
        lbSoNhanKhau.setText(String.valueOf(loadTableNhanKhau()));
        lbMaho.setText(ho.getMaHo());
        lbTenChuHo.setText(ho.getTen());
    }
    private int loadTableNhanKhau(){
        try {
            Connection conn = DBUtil.getConnection();

            int countSoNhanKhau = 0;
            String sql1 = "SELECT n.MAHO, TEN FROM NHANKHAU n,CACHO c WHERE n.MAHO = c.MAHO AND c.MAHO = ?;";

            PreparedStatement pst1 = conn.prepareStatement(sql1);
            pst1.setString(1,ho.getMaHo());
            ResultSet rs1 = pst1.executeQuery();

            while(rs1.next()){
                list.add(new NhanKhau(rs1.getString("MAHO"),rs1.getString("TEN")));
                countSoNhanKhau++;
            }
            DBUtil.Close(conn);
            return countSoNhanKhau;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void xemThongtinNhanKhau(boolean isChuHo){
        NhanKhau nk = table.getSelectionModel().getSelectedItem();
            try {
                Connection c = DBUtil.getConnection();

                String sql = "SELECT * FROM NHANKHAU WHERE NHANKHAU.MAHO = ? AND NHANKHAU.TEN = ?;";

                PreparedStatement pst = c.prepareStatement(sql);
                if(isChuHo){
                    pst.setString(1,ho.getMaHo());
                    pst.setString(2,ho.getTen());
                }
                else if(nk!=null){
                    pst.setString(1,nk.getMaHo());
                    pst.setString(2,nk.getTen());
                }
                else return;

                ResultSet rs = pst.executeQuery();

                while(rs.next()){
                    String bd = rs.getString("BIDANH");
                    String nghenghiep = rs.getString("NGHENGHIEP");
                    String noilamV = rs.getString("NOILAMVIEC");
                    String soCCCD = rs.getString("SOCMND");
                    String ngayCap = rs.getString("NGAYCAP");
                    String Noicap = rs.getString("NOICAP");
                    String NDKTT = rs.getString("NGAYDANGKYTHUONGTRU");
                    String noiChuyenden = rs.getString("NOICHUYENDEN");
                    String thuongtrubf = rs.getString("THUONGTRUBEFORE");
                    String ngayChuyendi = rs.getString("NGAYCHUYENDI");

                    QuanHeChuHo.setText(rs.getString("QUANHECHUHO"));
                    ten.setText(rs.getString("TEN"));
                    ngaysinh.setText(rs.getString("NGAYSINH"));
                    noisinh.setText(rs.getString("NOISINH"));
                    quequan.setText(rs.getString("QUEQUAN"));
                    dantoc.setText(rs.getString("DANTOC"));
                    ghichu.setText((rs.getString("GHICHU")));


                    if( bd != null)bidanh.setText(bd);
                    else bidanh.setText("");
                    if( nghenghiep!= null)ngheNghiep.setText(nghenghiep);
                    else ngheNghiep.setText("");
                    if(noilamV != null) noilamviec.setText(noilamV);
                    else noilamviec.setText("");
                    if(soCCCD != null) soCMND.setText(soCCCD);
                    else soCMND.setText("");
                    if( ngayCap != null) ngaycap.setText(ngayCap);
                    else ngaycap.setText("");
                    if( Noicap != null) noicap.setText(Noicap);
                    else noicap.setText("");
                    if( NDKTT != null) ngaydangkythuongtru.setText(NDKTT);
                    else ngaydangkythuongtru.setText("");
                    if( noiChuyenden != null) noichuyenden.setText(noiChuyenden);
                    else noichuyenden.setText("");
                    if( thuongtrubf != null) thuongtrubefore.setText(thuongtrubf);
                    else thuongtrubefore.setText("");
                    if( ngayChuyendi != null) ngaychuyendi.setText(ngayChuyendi);
                    else ngaychuyendi.setText("");
                }
                DBUtil.Close(c);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
    public void addNhanKhau() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ThemNhanKhau.fxml"));
        Parent root = loader.load();
        ThemNhanKhauController them = loader.getController();
        them.init(list,ho.getMaHo());
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Thêm Nhân Khẩu");
        stage.show();
    }
    public void XoaNhanKhau() throws ClassNotFoundException, SQLException {
        NhanKhau nk = table.getSelectionModel().getSelectedItem();
        if(nk != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure to remove this people?");
            alert.setTitle("WARNING!");
            alert.setHeaderText("you're about to remove!");
            if(alert.showAndWait().get() == ButtonType.OK){

                Connection con = DBUtil.getConnection();
                String sql = "DELETE FROM NHANKHAU WHERE TEN = ?;";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1,nk.getTen());
                pst.executeUpdate();
                list.remove(nk);
                // update to KHOAN THU
                MainKhoanThu.UpdateKhoanThuAfterUpdateNhanKhau(nk.getMaHo(),list.size());

                DBUtil.Close(con);
            }
        }
    }
    public void editNhanKhau() throws IOException {
        NhanKhau nk = table.getSelectionModel().getSelectedItem();
        int index = table.getSelectionModel().getSelectedIndex();

        if(nk != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditNhanKhau.fxml"));
            Parent root = loader.load();
            EditNhanKhauController edit = loader.getController();
            edit.init(list,index);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
    public void LichSuThayDoi() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LichsuThaydoiNhanKhau.fxml"));
        Parent root = loader.load();
        LichSuChinhSuaNhanKhau history = loader.getController();
        history.init(ho.getMaHo());
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    public static void XoaNhanKhauAfterXoaHo(String MH){
        try {
            Connection con = DBUtil.getConnection();

            String sql = "DELETE FROM NHANKHAU WHERE MAHO = ?;";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, MH);
            pst.executeUpdate();

            DBUtil.Close(con);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
