
package mvc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class MhsController {
    MhsModel mhsmodel;
    MhsView mhsview;
    MhsDAO mhsdao;
    static String nim;
    
    public MhsController(MhsModel mhsmodel, MhsView mhsview, MhsDAO mhsdao)
    {
        this.mhsmodel = mhsmodel;
        this.mhsview = mhsview;
        this.mhsdao = mhsdao;
        
        if (mhsdao.getJmldata() !=0)
        {
            String dataMahasiswa[][]= mhsdao.readMahasiswa();
            mhsview.tabel.setModel((new JTable(dataMahasiswa, mhsview.namaKolom)).getModel());
        
        } 
        else
        {
            JOptionPane.showMessageDialog(null, "data tidak ada");
        }
        
        mhsview.simpan.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String nim = mhsview.getNim();
                String nama = mhsview.getNama();
                String alamat = mhsview.getAlamat();
                
                if(nim.isEmpty() || nama.isEmpty() || alamat.isEmpty())
                {
                    JOptionPane.showMessageDialog(null, "Harap isi semua field");
                }
                else
                {
                    mhsmodel.setMhsModel(nim, nama, alamat);
                    mhsdao.Insert(mhsmodel);
                    String dataMahasiswa[][] = mhsdao.readMahasiswa();
                    mhsview.tabel.setModel((new JTable(dataMahasiswa, mhsview.namaKolom)).getModel());
                }
            }
        });
        
        mhsview.tabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) 
            {
                tabelMouseClicked(e);
                mhsview.delete.setEnabled(true);
                
                mhsview.delete.addActionListener((ActionEvent x)->
                {
                    mhsmodel.setNim(nim);
                    mhsdao.Delete(mhsmodel);
                    String dataMahasiswa[][] = mhsdao.readMahasiswa();
                    mhsview.tabel.setModel(new JTable(dataMahasiswa, mhsview.namaKolom).getModel());
                });
            }

            private void tabelMouseClicked(MouseEvent e) {
                int baris = mhsview.tabel.rowAtPoint(e.getPoint());
                nim = mhsview.tabel.getValueAt(baris,0).toString();
            }       
        });
        
    }
}
