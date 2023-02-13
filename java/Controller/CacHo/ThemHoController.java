package Controller.CacHo;

import Controller.KhoanThu.MainKhoanThu;
import Controller.NhanKhau.ThemNhanKhauController;
import Infomation.CacHo;
import com.example.nhankhaumanager.DBUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ThemHoController {
    private ObservableList<CacHo> list;
    @FXML private TextField maho;
    @FXML private TextField tenChuHo;
    @FXML private TextField soNha;
    @FXML private TextField Duong;
    @FXML private TextField Phuong;
    @FXML private TextField Quan;
    @FXML private Label notify;

    public void setInit(ObservableList<CacHo> list){
        this.list = list;
    }
    private boolean Error_Blank(){
        return maho.getText().isEmpty()
                || tenChuHo.getText().isEmpty()
                || soNha.getText().isEmpty()
                || Duong.getText().isEmpty()
                || Phuong.getText().isEmpty()
                || Quan.getText().isEmpty();
    }
    private boolean Error_Duplicate(){
        try {
            Connection conn = DBUtil.getConnection();

            String sql = "SELECT MAHO FROM CACHO;";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                if(maho.getText().equals(rs.getString("MAHO"))){
                    DBUtil.Close(conn);
                    return true;
                }
            }
            DBUtil.Close(conn);
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private boolean Error_SoNha(){
        String SN = soNha.getText();
        for(int i = 0;i<SN.length();i++){
            char id = SN.charAt(i);
            if(id > '9' || id < '0')return true;
        }
        return false;
    }

    public void edit(ActionEvent e){

        if(Error_Duplicate()){
            notify.setTextFill(Color.RED);
            notify.setText("This 'MAHO' have already existed in the table");
            return;
        }

        if(Error_SoNha()){
            notify.setTextFill(Color.RED);
            notify.setText("The field 'SONHA' must be a type of integer!");
            return;
        }

        if(Error_Blank()){
            notify.setTextFill(Color.RED);
            notify.setText("You must fill all the blank!!");
        }
        else{
            notify.setTextFill(Color.GREEN);
            notify.setText("Add successfully!");
            list.add(new CacHo(maho.getText(),tenChuHo.getText(),Integer.parseInt(soNha.getText()),Duong.getText(),Phuong.getText(),Quan.getText()));
            try {
                Connection conn = DBUtil.getConnection();

                String sql = "INSERT INTO CACHO " +
                        "VALUES(?,?,?,?,?,?,?);";

                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1,maho.getText());
                pst.setString(2,tenChuHo.getText());
                pst.setString(3,soNha.getText());
                pst.setString(4,Duong.getText());
                pst.setString(5,Phuong.getText());
                pst.setString(6,Quan.getText());
                pst.setString(7,null);
                pst.executeUpdate();
                DBUtil.Close(conn);

                ThemNhanKhauController.ThemNhanKhauAfterThemHo(maho.getText(),tenChuHo.getText());
                MainKhoanThu.ThemKhoanThuAfterThemHo(maho.getText());

            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
