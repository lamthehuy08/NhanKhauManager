package Controller;

import Infomation.NhanKhau;
import com.example.nhankhaumanager.DBUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EditNhanKhauController {

    @FXML
    private Label notify;
    private ObservableList<NhanKhau> list;
    private int index;
    @FXML private TextField QuanHeChuHo;
    @FXML private TextField ten;
    @FXML private TextField bidanh;
    @FXML private DatePicker ngaysinh;
    @FXML private TextField noisinh;
    @FXML private TextField quequan;
    @FXML private TextField dantoc;
    @FXML private TextField ngheNghiep;
    @FXML private TextField noilamviec;
    @FXML private TextField soCMND;
    @FXML private DatePicker ngaycap;
    @FXML private TextField noicap;
    @FXML private DatePicker ngaydangkythuongtru;
    @FXML private TextField thuongtrubefore;
    @FXML private TextField noichuyenden;
    @FXML private DatePicker ngaychuyendi;
    @FXML private TextField ghichu;


    public void init(ObservableList<NhanKhau>list,int i )  {
        
        this.list = list;
        this.index = i;
        loadText();
    }

    private void loadText() {
        try {
            Connection con = DBUtil.getConnection();
            String sql = "SELECT * FROM NHANKHAU WHERE MAHO = ? AND TEN = ?;";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1,list.get(index).getMaHo());
            pst.setString(2,list.get(index).getTen());

            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                ten.setText(rs.getString("TEN"));
                ngaysinh.setValue(LocalDate.parse(rs.getString("NGAYSINH")));
                noisinh.setText(rs.getString("NOISINH"));
                quequan.setText(rs.getString("QUEQUAN"));
                dantoc.setText(rs.getString("DANTOC"));
                ngheNghiep.setText(rs.getString("NGHENGHIEP"));
                noilamviec.setText(rs.getString("NOILAMVIEC"));
                soCMND.setText(rs.getString("SOCMND"));
                if(rs.getString("NGAYCAP") !=null){
                    ngaycap.setValue(LocalDate.parse(rs.getString("NGAYCAP")));
                }
                noicap.setText(rs.getString("NOICAP"));
                if(rs.getString("NGAYDANGKYTHUONGTRU") != null){
                    ngaydangkythuongtru.setValue(LocalDate.parse(rs.getString("NGAYDANGKYTHUONGTRU")));
                }
                thuongtrubefore.setText(rs.getString("THUONGTRUBEFORE"));
                noichuyenden.setText(rs.getString("NOICHUYENDEN"));
                if(rs.getString("NGAYCHUYENDI") != null){
                    ngaychuyendi.setValue(LocalDate.parse(rs.getString("NGAYCHUYENDI")));
                }
                bidanh.setText(rs.getString("BIDANH"));
                QuanHeChuHo.setText(rs.getString("QUANHECHUHO"));
                ghichu.setText(rs.getString("GHICHU"));
            }
            DBUtil.Close(con);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void edit(ActionEvent e){
        if(Error()){
            return;
        }
        else{
            try {
                Connection con = DBUtil.getConnection();

                String sql = "UPDATE NHANKHAU" +
                        " SET TEN = ?,NGAYSINH = ?,NOISINH = ?,QUEQUAN = ?,DANTOC = ?,NGHENGHIEP = ?,NOiLAMVIEC = ?" +
                        ", SOCMND = ?, NGAYCAP = ?,NOICAP = ?,NGAYDANGKYTHUONGTRU = ?,THUONGTRUBEFORE = ?" +
                        ", NOICHUYENDEN = ?, NGAYCHUYENDI = ?,BIDANH = ?, QUANHECHUHO = ?, GHICHU = ?" +
                        " WHERE MAHO = ? AND TEN = ?;";

                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1,ten.getText());
                pst.setString(2,ngaysinh.getValue().toString());
                pst.setString(3,noisinh.getText());
                pst.setString(4,quequan.getText());
                pst.setString(5,dantoc.getText());
                pst.setString(6,ngheNghiep.getText());
                pst.setString(7,noilamviec.getText());
                pst.setString(8,soCMND.getText());
                if(ngaycap.getValue() != null) pst.setString(9,ngaycap.getValue().toString());
                else pst.setString(9,null);
                pst.setString(10,noicap.getText());
                if(ngaydangkythuongtru.getValue() != null)pst.setString(11,ngaydangkythuongtru.getValue().toString());
                else pst.setString(11,null);
                pst.setString(12,thuongtrubefore.getText());
                pst.setString(13,noichuyenden.getText());
                if(ngaychuyendi.getValue() != null) pst.setString(14,ngaychuyendi.getValue().toString());
                else pst.setString(14,null);
                pst.setString(15,bidanh.getText());
                pst.setString(16,QuanHeChuHo.getText());
                pst.setString(17,ghichu.getText());
                pst.setString(18,list.get(index).getMaHo());
                pst.setString(19,list.get(index).getTen());

                int ss = pst.executeUpdate();
                if(ss > 0){
                    setTextNotify("update successfully!", Color.GREEN);
                }
                else{
                    setTextNotify("failed!!",Color.RED);
                }

                DBUtil.Close(con);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private void setTextNotify(String s,Color color){
        notify.setTextFill(color);
        notify.setText(s);
    }
    private boolean Error(){

        String s1 = QuanHeChuHo.getText();
        String s2 = ten.getText();
        LocalDate s3 = ngaysinh.getValue();
        String s4 = noisinh.getText();
        String s5 = quequan.getText();
        String s6 = dantoc.getText();

        if(s1.isEmpty()){
            setTextNotify("You must fill the blank 'Quan he chu ho'",Color.RED);
            return true;
        }
        else if(s2.isEmpty()){
            setTextNotify("You must fill the blank 'ten'",Color.RED);
            return true;
        }
        else if(s3 == null){
            setTextNotify("You must fill the blank 'ngay sinh'",Color.RED);
            return true;
        }
        else if(s4.isEmpty()){
            setTextNotify("You must fill the blank 'noi sinh'" ,Color.RED);
            return true;
        }
        else if(s5.isEmpty()){
            setTextNotify("You must fill the blank 'que quan'",Color.RED);
            return true;
        }
        else if(s6.isEmpty()){
            setTextNotify("You must fill the blank 'dan toc'",Color.RED);
            return true;
        }
        return false;
    }
}
