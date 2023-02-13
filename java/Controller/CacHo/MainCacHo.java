package Controller.CacHo;

import Controller.KhoanThu.MainKhoanThu;
import Controller.NhanKhau.MainNhanKhau;
import Infomation.CacHo;
import Infomation.Info;
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

public class MainCacHo {
    private ObservableList<CacHo> list;
    private TableView<CacHo> table;
    public MainCacHo(ObservableList<CacHo> l){
        this.list = l ;
        loadCacHo();
    }
    public void init(TableView<CacHo> t){
        this.table = t;
    }
    public void addCacHo() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ThemHoScene.fxml"));
        Parent root = loader.load();
        ThemHoController them = loader.getController();
        them.setInit(list);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Thêm hộ khẩu");
        stage.show();
    }
    public void editCacHo() throws IOException {
        CacHo ho = this.table.getSelectionModel().getSelectedItem();
        int i = this.table.getSelectionModel().getSelectedIndex();

        if(ho != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditCacHo.fxml"));
            Parent root = loader.load();
            EditCacHo_controller edit = loader.getController();
            edit.getTextField(ho,list,i);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Edit hộ khẩu");
            stage.show();
        }
    }
    public void TruyNhapHo(MainNhanKhau mainNhanKhau,TableView<NhanKhau>tb,ObservableList<NhanKhau>list, Label lbMaho, Label lbTenChuHo, Label lbSoNhanKhau){
        CacHo ho = table.getSelectionModel().getSelectedItem();
        if(ho != null){
            list.clear();
            mainNhanKhau.init(ho,tb,list,lbMaho,lbTenChuHo,lbSoNhanKhau);
        }
    }
    public void XoaHo(){
        Info ho = table.getSelectionModel().getSelectedItem();
        if(ho != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("WARNING!");
            alert.setHeaderText("Are you sure for removing?");
            alert.setContentText("You're about to remove!");
            if (alert.showAndWait().get() == ButtonType.OK) {
                try {
                    Connection c = DBUtil.getConnection();

                    // delete from TABLE NHANKHAU in DB
                    MainNhanKhau.XoaNhanKhauAfterXoaHo(ho.getMaHo());
                    MainKhoanThu.XoaKhoanThuAfterXoaHo(ho.getMaHo());

                    String sql = "DELETE FROM CACHO WHERE MAHO = ?;";
                    PreparedStatement pst = c.prepareStatement(sql);
                    pst.setString(1, ho.getMaHo());
                    pst.executeUpdate();
                    DBUtil.Close(c);

                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                list.remove(ho);
            }
        }
    }
    private void loadCacHo(){
        try {
            Connection conn = DBUtil.getConnection();

            String sqlCacHo = "SELECT * FROM CACHO" ;

            PreparedStatement pst = conn.prepareStatement(sqlCacHo);
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                list.add(new CacHo(rs.getString("MAHO"),
                        rs.getString("TENCHUHO"),
                        rs.getInt("SONHA"),
                        rs.getString("DUONG"),
                        rs.getString("PHUONG"),
                        rs.getString("QUAN")));
            }
            DBUtil.Close(conn);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
