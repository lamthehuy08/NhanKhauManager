package Controller.NhanKhau;

import Controller.KhoanThu.MainKhoanThu;
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
import java.sql.SQLException;
import java.time.LocalDate;

public class ThemNhanKhauController {
    @FXML private Label notify;
    private ObservableList<NhanKhau> list;
    private String MH;
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


    public void init(ObservableList<NhanKhau>list,String MH){
        this.list = list;
        this.MH = MH;
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
        String s7 = gioitinh.getText();

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
        else if(s7.isEmpty()){
            setTextNotify("You must fill the blank 'gioi tinh'",Color.RED);
            return true;
        }
        return false;
    }

    public void Them(ActionEvent e) throws ClassNotFoundException, SQLException {
        if(Error()){
            return;
        }
        else{

            Connection con = DBUtil.getConnection();

            String sql = "INSERT INTO NHANKHAU VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

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
            pst.setObject(10,ngaycap.getValue());
            pst.setString(11,noicap.getText());
            pst.setObject(12,ngaydangkythuongtru.getValue());
            pst.setString(13,thuongtrubefore.getText());
            pst.setString(14,noichuyenden.getText());
            pst.setObject(15,ngaychuyendi.getValue());
            pst.setString(16,MH);
            pst.setString(17,bidanh.getText());
            pst.setString(18,QuanHeChuHo.getText());
            pst.setString(19,ghichu.getText());

            int ss = pst.executeUpdate();
            if(ss > 0){
                setTextNotify("add successfully!",Color.GREEN);
                list.add(new NhanKhau(MH,ten.getText()));

                // update to TONG of KHOANTHU due to increase people in a HO.
                String sql1 = "UPDATE KHOANTHUDOT1 SET TONG = ? WHERE MAHO = ?;";
                pst = con.prepareStatement(sql1);
                pst.setFloat(1,list.size() * 6000*12);
                pst.setString(2,MH);
                pst.executeUpdate();

                String sql2 = "UPDATE KHOANTHUDOT2 SET TONG = ? WHERE MAHO = ?;";
                pst = con.prepareStatement(sql1);
                pst.setFloat(1,list.size() * 6000*12);
                pst.setString(2,MH);
                pst.executeUpdate();
            }
            else{
                setTextNotify("failed!!",Color.RED);
            }

            DBUtil.Close(con);
        }
    }
    public static void ThemNhanKhauAfterThemHo(String MH,String ten){
        Connection con = null;
        try {
            con = DBUtil.getConnection();
            String sql = "INSERT INTO NHANKHAU VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1,ten);
            pst.setString(2,"");
            pst.setString(3,"");
            pst.setString(4,"");
            pst.setString(5,"");
            pst.setString(6,"");
            pst.setString(7,null);
            pst.setString(8,null);
            pst.setString(9,null);
            pst.setObject(10,null);
            pst.setString(11,null);
            pst.setObject(12,null);
            pst.setString(13,null);
            pst.setString(14,null);
            pst.setObject(15,null);
            pst.setString(16,MH);
            pst.setString(17,null);
            pst.setString(18,"Chủ hộ");
            pst.setString(19,null);
            pst.executeUpdate();

            DBUtil.Close(con);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
