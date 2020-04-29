
package mvc;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;


public class MhsDAO {
    private int jmlData;
    private Connection koneksi;
    private Statement statement;
    
    public MhsDAO()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String  url = "jdbc:mysql://localhost/mahasiswa";
            koneksi = (Connection) DriverManager.getConnection(url, "root", "");
            statement = (Statement) koneksi.createStatement();
            JOptionPane.showMessageDialog(null, "Koneksi berhasil");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Class not found : " + ex);
        }  catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "SQL Exception : " + ex);
        } 
   
    }
    
    public void Insert(MhsModel Model)
    {
        try
        {
            String query ="INSERT INTO data_mhs VALUES ('"+Model.getNama()+"','"+ Model.getNim()+"','" + Model.getAlamat()+"')";
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null,"Data berhasil disimpan");
        }   catch(Exception sql){
            JOptionPane.showMessageDialog(null, sql.getMessage());
        }           
    }
    
    public void Update(MhsModel Model)
    {
        try
        {
            String query ="UPDATE data_mhs SET nim='" + Model.getNim()+
                    "',"+ "nama='" + Model.getNama()+ "', alamat='" + Model.getAlamat()+
                    "' WHERE nim = '"+ Model.getNim()+ "'";
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null,"Data berhasil di update");
        }   catch(Exception sql){
            JOptionPane.showMessageDialog(null, sql.getMessage());
        }           
    }
    
    public void Delete(MhsModel Model)
    {
        try
        {
            String query ="DELETE FROM data_mhs WHERE nim= '"+ Model.getNim()+ "'";
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null,"Data berhasil di hapus");
        }   catch(Exception sql){
            JOptionPane.showMessageDialog(null, sql.getMessage());
        }           
    }
    
    public String[][] readMahasiswa()
    {
        try{
            int jmlData = 0;
            String data[][]=new String[getJmldata()][3];
            String query = "Select * from `data_mhs`";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                data[jmlData][0] = resultSet.getString("nim");
                data[jmlData][1] = resultSet.getString("nama");
                data[jmlData][2] = resultSet.getString("alamat");
                jmlData++;
            }
            return data;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return null;
        }
    }
    
    public int getJmldata()
    {
        int jmlData = 0;
        try{
            String query = "SELECT * from `data_mhs`";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                jmlData++;
            }
            return jmlData;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return 0;
        }
    }
   

    
}
