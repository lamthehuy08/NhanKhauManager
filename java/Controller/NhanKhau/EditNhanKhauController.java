package Controller.NhanKhau;

import Infomation.NhanKhau;
import com.example.nhankhaumanager.DBUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

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
    @FXML private TextField gioitinh;
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
                gioitinh.setText(rs.getString("GIOITINH"));
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
                        " SET TEN = ?,NGAYSINH = ?,NOISINH = ?,QUEQUAN = ?,DANTOC = ?,GIOITINH = ?,NGHENGHIEP = ?,NOiLAMVIEC = ?" +
                        ", SOCMND = ?, NGAYCAP = ?,NOICAP = ?,NGAYDANGKYTHUONGTRU = ?,THUONGTRUBEFORE = ?" +
                        ", NOICHUYENDEN = ?, NGAYCHUYENDI = ?,BIDANH = ?, QUANHECHUHO = ?, GHICHU = ?" +
                        " WHERE MAHO = ? AND TEN = ?;";

                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1,ten.getText());
                pst.setString(2,ngaysinh.getValue().toString());
                pst.setString(3,noisinh.getText());
                pst.setString(4,quequan.getText());
                pst.setString(5,gioitinh.getText());
                pst.setString(6,dantoc.getText());
                pst.setString(7,ngheNghiep.getText());
                pst.setString(8,noilamviec.getText());
                pst.setString(9,soCMND.getText());
                if(ngaycap.getValue() != null) pst.setString(10,ngaycap.getValue().toString());
                else pst.setString(10,null);
                pst.setString(11,noicap.getText());
                if(ngaydangkythuongtru.getValue() != null)pst.setString(12,ngaydangkythuongtru.getValue().toString());
                else pst.setString(12,null);
                pst.setString(13,thuongtrubefore.getText());
                pst.setString(14,noichuyenden.getText());
                if(ngaychuyendi.getValue() != null) pst.setString(15,ngaychuyendi.getValue().toString());
                else pst.setString(15,null);
                pst.setString(16,bidanh.getText());
                pst.setString(17,QuanHeChuHo.getText());
                pst.setString(18,ghichu.getText());
                pst.setString(19,list.get(index).getMaHo());
                pst.setString(20,list.get(index).getTen());

                int ss = pst.executeUpdate();
                if(ss > 0){
                    setTextNotify("update successfully!", Color.GREEN);
                    DBUtil.Close(con);
                    refresh();
                    return;
                }
                else{
                    setTextNotify("failed!!",Color.RED);
                }
                sql = "UPDATE CACHO SET TENCHUHO = ? WHERE MAHO = ?;";
                pst = con.prepareStatement(sql);
                pst.setString(1,ten.getText());
                pst.setString(2,list.get(index).getMaHo());
                pst.executeUpdate();

                DBUtil.Close(con);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    private void refresh(){
        try {
            Connection con =  DBUtil.getConnection();
            String sql1 = "SELECT n.MAHO, TEN FROM NHANKHAU n,CACHO c WHERE n.MAHO = c.MAHO AND c.MAHO = ?;";

            PreparedStatement pst1 = con.prepareStatement(sql1);
            pst1.setString(1,list.get(index).getMaHo());
            ResultSet rs1 = pst1.executeQuery();

            list.clear();
            while(rs1.next()) {
                list.add(new NhanKhau(rs1.getString("MAHO"), rs1.getString("TEN")));
            }
            DBUtil.Close(con);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
