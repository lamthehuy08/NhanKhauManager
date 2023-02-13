package Controller.KhoanThu;

import Infomation.KhoanThu;
import com.example.nhankhaumanager.DBUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LichSuCapNhatKhoanThu {
    @FXML private TextArea LichSu;
    @FXML private Label Ldot;
    private String MAHO;
    private String LichSuDaCo = "";
    private String dot;
    public LichSuCapNhatKhoanThu(){}
    public LichSuCapNhatKhoanThu(String MAHO,String dot){
        this.MAHO = MAHO;
        this.dot = dot;
    }

    protected void init(String MAHO,String dot){
        this.MAHO = MAHO;
        this.dot = dot;
        Ldot.setText("Mã hộ :" + this.MAHO + "   Đợt: " + this.dot);
        try {
            Connection conn = DBUtil.getConnection();
            String sql = "select LICHSU from KHOANTHUDOT"+ this.dot+ " where MAHO = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,MAHO);
            ResultSet rs = pst.executeQuery();
            rs.next();
            if(rs.getString("LICHSU") != null){
                LichSuDaCo = rs.getString("LICHSU");
                LichSu.setText(LichSuDaCo);
            }
            else LichSu.setText("\n\n\n\n\n\n\t\t\t\t\t\t\tChưa có lịch sử cập nhật!");

            DBUtil.Close(conn);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private String SaveHistoryCapNhatKhoanThu(float nopthem, float tblsNopThem, float thieunhiNopThem, float nguoingheoNopThem, float lulutNopThem){
        if(nopthem > 0 || tblsNopThem > 0 || thieunhiNopThem > 0 || nguoingheoNopThem > 0 || lulutNopThem > 0){
            String s = "\n- Ngày cập nhật: ";
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            s += dtf.format(LocalDateTime.now());
            if(nopthem != 0)s = s + "\n\t+ Khoản bắt buộc: +" + nopthem;
            if(tblsNopThem != 0)s = s + "\n\t+ Thương binh liệt sỹ 27/07: +" + tblsNopThem;
            if(thieunhiNopThem != 0)s = s + "\n\t+ Tết thiếu nhi: +" + thieunhiNopThem;
            if(nguoingheoNopThem != 0)s = s + "\n\t+ Ủng hộ người nghèo: +" + nguoingheoNopThem;
            if(lulutNopThem != 0)s = s + "\n\t+ Ủng hộ đồng bào lũ lụt: +" + lulutNopThem;
            return s;
        }
        return "";
    }
    protected void CapNhatLichSu(float nopthem, float tblsNopThem, float thieunhiNopThem, float nguoingheoNopThem, float lulutNopThem){

        LichSuDaCo += SaveHistoryCapNhatKhoanThu(nopthem,tblsNopThem,thieunhiNopThem,nguoingheoNopThem,lulutNopThem);
        try {
            Connection conn = DBUtil.getConnection();

            String sql0 = "select LICHSU from KHOANTHUDOT" + dot +" where MAHO = ?;";
            System.out.println(dot);
            PreparedStatement pst0 = conn.prepareStatement(sql0);
            pst0.setString(1,MAHO);
            ResultSet rs = pst0.executeQuery();
            rs.next();
            if(rs.getString("LICHSU") != null){
                LichSuDaCo += rs.getString("LICHSU");
            }

            String sql = "update KHOANTHUDOT" + dot+" set LICHSU = ? where MAHO = ?;";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(2,MAHO);
            pst.setString(1,LichSuDaCo);
            pst.executeUpdate();

            DBUtil.Close(conn);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
