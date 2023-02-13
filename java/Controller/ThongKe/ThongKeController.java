package Controller.ThongKe;

import Infomation.NhanKhau;
import com.example.nhankhaumanager.DBUtil;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ResourceBundle;

public class ThongKeController{

    private ChoiceBox<String> TvTtChoiceBox;
    private ChoiceBox<String> ageChoiceBox;
    private ChoiceBox<String> sexChoiceBox;
    private ObservableList<NhanKhau>list;
    private int countSQL;
    private Label counter;

    public void init(ObservableList<NhanKhau> list, ChoiceBox<String> TvTt,ChoiceBox<String> age,ChoiceBox<String> sex,Label l){
        this.list = list;
        this.TvTtChoiceBox = TvTt;
        this.ageChoiceBox = age;
        this.sexChoiceBox = sex;
        this.counter = l;
    }

    private int LocAge(String year_born, String age_){

        String age[] = age_.equals("Tất cả") ? null : age_.split("-");

        int yb = Integer.parseInt(year_born);
        int now = Integer.parseInt(String.valueOf(Year.now()));
        int tuoi = now - yb;

        if(age == null)return tuoi;

        int f = Integer.parseInt(age[0]);
        int s = Integer.parseInt(age[1]);

        if(tuoi >= f && tuoi <= s )return tuoi;

        return -1;
    }

    private String SQL_Statement(String sex ,String tvtt ){

        countSQL = 0;

        String sql = "SELECT MAHO, TEN, GIOITINH, YEAR(NGAYSINH) year_born, NGAYDANGKYTHUONGTRU ndk FROM NHANKHAU";

        if(!sex.equals("Tất cả")){
            sql +=  " where GIOITINH = ?";
            countSQL = 1;
            if(tvtt.equals("Yes")){
                sql += " and NGAYDANGKYTHUONGTRU IS NOT NULL;";
            }
            else if(tvtt.equals("No")){
                sql += " and NGAYDANGKYTHUONGTRU IS NULL;";
            }
        }
        else{
            if(tvtt.equals("Yes")){
                sql += " where NGAYDANGKYTHUONGTRU IS NOT NULL;";
            }
            else if(tvtt.equals("No")){
                sql += " where NGAYDANGKYTHUONGTRU IS NULL;";
            }
        }

        return sql;
    }

    public void Loc() {

            String tvtt = TvTtChoiceBox.getValue();
            String age_ = ageChoiceBox.getValue();
            String sex = sexChoiceBox.getValue();

            if(tvtt != null && age_ != null && sex != null){
                try {
                    Connection conn = DBUtil.getConnection();

                    String sql = SQL_Statement(sex,tvtt);

                    PreparedStatement pst = conn.prepareStatement(sql);
                    if(countSQL == 1) pst.setString(1,sex);

                    ResultSet rs = pst.executeQuery();

                    while(rs.next()) {

                        int tuoi = LocAge(rs.getString("year_born"), age_);
                        boolean isMove = (rs.getString("ndk") != null);

                        NhanKhau nk = new NhanKhau(rs.getString("MAHO"),
                                rs.getString("TEN"),
                                rs.getString("GIOITINH"),tuoi,isMove);

                        if(tuoi != -1){
                            if(tvtt.equals("Tất cả")){
                                list.add(nk);
                            }
                            else if(tvtt.equals("Yes") && isMove){
                                list.add(nk);
                            }
                            else if(tvtt.equals("No") && !isMove){
                                list.add(nk);
                            }
                        }
                    }

                    counter.setText("Count = " + list.size());

                    DBUtil.Close(conn);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

    }
}
