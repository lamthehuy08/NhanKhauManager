package Controller;

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
    public void Them(ActionEvent e) throws ClassNotFoundException, SQLException {
        if(Error()){
            return;
        }
        else{

            Connection con = DBUtil.getConnection();

            String sql = "INSERT INTO NHANKHAU VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1,ten.getText());
            pst.setString(2,ngaysinh.getValue().toString());
            pst.setString(3,noisinh.getText());
            pst.setString(4,quequan.getText());
            pst.setString(5,dantoc.getText());
            pst.setString(6,ngheNghiep.getText());
            pst.setString(7,noilamviec.getText());
            pst.setString(8,soCMND.getText());
            pst.setString(9,ngaycap.getValue().toString());
            pst.setString(10,noicap.getText());
            pst.setString(11,ngaydangkythuongtru.getValue().toString());
            pst.setString(12,thuongtrubefore.getText());
            pst.setString(13,noichuyenden.getText());
            pst.setString(14,ngaychuyendi.getValue().toString());
            pst.setString(15,MH);
            pst.setString(16,bidanh.getText());
            pst.setString(17,QuanHeChuHo.getText());
            pst.setString(18,ghichu.getText());

            int ss = pst.executeUpdate();
            if(ss > 0){
                setTextNotify("add successfully!",Color.GREEN);
                list.add(new NhanKhau(MH,ten.getText()));
            }
            else{
                setTextNotify("failed!!",Color.RED);
            }

            DBUtil.Close(con);
        }
    }
}
