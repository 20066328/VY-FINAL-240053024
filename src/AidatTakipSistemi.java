import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class AidatTakipSistemi extends JFrame {

    // Arayüz bileşenleri
    private JTextField txtDaireNo, txtAdSoyad, txtTutar, txtArama;
    private JComboBox<String> cmbAy;
    private JLabel lblToplamTutar;
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter; // Arama/Filtreleme için gerekli

    // Toplam parayı tutacak değişken
    private double toplamKasa = 0.0;

    public AidatTakipSistemi() {
        // --- GÖRÜNÜM AYARI (NIMBUS TEMA) ---
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {}

        // Pencere Ayarları
        setTitle("Site Aidat Yönetim Sistemi");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- 1. ÜST PANEL (Veri Giriş) ---
        JPanel panelGiris = new JPanel(new GridLayout(6, 2, 8, 8));
        panelGiris.setBorder(BorderFactory.createTitledBorder("Yeni Tahsilat Girişi"));

        panelGiris.add(new JLabel("Daire No:"));
        txtDaireNo = new JTextField();
        panelGiris.add(txtDaireNo);

        panelGiris.add(new JLabel("Ad Soyad:"));
        txtAdSoyad = new JTextField();
        panelGiris.add(txtAdSoyad);

        panelGiris.add(new JLabel("Dönem (Ay):"));
        String[] aylar = {"Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran",
                "Temmuz", "Ağustos", "Eylül", "Ekim", "Kasım", "Aralık"};
        cmbAy = new JComboBox<>(aylar);
        panelGiris.add(cmbAy);

        panelGiris.add(new JLabel("Tutar (TL):"));
        txtTutar = new JTextField();
        panelGiris.add(txtTutar);

        panelGiris.add(new JLabel("")); // Boşluk

        JButton btnEkle = new JButton("Listeye Ekle");
        btnEkle.setBackground(new Color(46, 204, 113)); // Yeşil
        btnEkle.setForeground(Color.WHITE);
        panelGiris.add(btnEkle);

        add(panelGiris, BorderLayout.NORTH);

        // --- 2. ORTA PANEL (Arama ve Tablo) ---
        JPanel panelOrta = new JPanel(new BorderLayout(5, 5));
        panelOrta.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Arama Çubuğu Kısmı
        JPanel panelArama = new JPanel(new BorderLayout());
        panelArama.add(new JLabel("Kayıt Ara (Daire, İsim vb.): "), BorderLayout.WEST);
        txtArama = new JTextField();
        panelArama.add(txtArama, BorderLayout.CENTER);
        panelOrta.add(panelArama, BorderLayout.NORTH);

        // Tablo Kısmı
        String[] kolonlar = {"Daire", "Ad Soyad", "Dönem", "Tutar"};
        model = new DefaultTableModel(kolonlar, 0);
        table = new JTable(model);
        table.setRowHeight(25);

        // Sıralayıcıyı (Sorter) tabloya bağla
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        panelOrta.add(scrollPane, BorderLayout.CENTER);

        add(panelOrta, BorderLayout.CENTER);

        // --- 3. ALT PANEL (İşlemler ve Toplam) ---
        JPanel panelAlt = new JPanel(new BorderLayout());
        panelAlt.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelButonlar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnSil = new JButton("Seçili Satırı Sil");
        btnSil.setBackground(new Color(231, 76, 60)); // Kırmızı
        btnSil.setForeground(Color.WHITE);

        JButton btnKaydet = new JButton("Dosyaya Kaydet");
        btnKaydet.setBackground(new Color(52, 152, 219)); // Mavi
        btnKaydet.setForeground(Color.WHITE);

        panelButonlar.add(btnSil);
        panelButonlar.add(btnKaydet);

        lblToplamTutar = new JLabel("KASA TOPLAMI: 0.0 TL");
        lblToplamTutar.setFont(new Font("Arial", Font.BOLD, 18));
        lblToplamTutar.setForeground(new Color(44, 62, 80));

        panelAlt.add(panelButonlar, BorderLayout.WEST);
        panelAlt.add(lblToplamTutar, BorderLayout.EAST);

        add(panelAlt, BorderLayout.SOUTH);

        // --- AKSİYONLAR ---

        // Ekleme
        btnEkle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                kayitEkle();
            }
        });

        // Silme
        btnSil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                kayitSil();
            }
        });

        // Kaydetme
        btnKaydet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dosyayaKaydet();
            }
        });

        // CANLI ARAMA AKSİYONU (Her tuşa basıldığında çalışır)
        txtArama.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = txtArama.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null); // Arama yoksa filtreyi kaldır
                } else {
                    // Büyük/küçük harf duyarsız arama yap (?i)
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });
    }

    private void kayitEkle() {
        String daire = txtDaireNo.getText();
        String adSoyad = txtAdSoyad.getText();
        String ay = (String) cmbAy.getSelectedItem();
        String tutarText = txtTutar.getText();

        if (daire.isEmpty() || adSoyad.isEmpty() || tutarText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurunuz!");
            return;
        }

        try {
            double tutar = Double.parseDouble(tutarText);
            model.addRow(new Object[]{daire, adSoyad, ay, tutar});
            hesaplaGuncelle();
            temizle();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Tutar alanına sayı giriniz!");
        }
    }

    private void kayitSil() {
        int seciliSatir = table.getSelectedRow();
        if (seciliSatir != -1) {
            // Görünürdeki satırın modeldeki gerçek indeksini bul (Filtreleme varken önemlidir)
            int modelIndex = table.convertRowIndexToModel(seciliSatir);

            if (JOptionPane.showConfirmDialog(this, "Silmek istediğinize emin misiniz?", "Onay", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                model.removeRow(modelIndex);
                hesaplaGuncelle();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen silinecek satırı seçiniz.");
        }
    }

    private void dosyayaKaydet() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("aidat_listesi.txt"))) {
            writer.write("DAIRE | AD SOYAD | DONEM | TUTAR\n");
            writer.write("--------------------------------\n");
            for (int i = 0; i < model.getRowCount(); i++) {
                writer.write(model.getValueAt(i, 0) + " - " +
                        model.getValueAt(i, 1) + " - " +
                        model.getValueAt(i, 2) + " - " +
                        model.getValueAt(i, 3) + " TL\n");
            }
            writer.write("--------------------------------\n");
            writer.write("TOPLAM KASA: " + toplamKasa + " TL");
            JOptionPane.showMessageDialog(this, "Başarıyla kaydedildi!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
        }
    }

    private void hesaplaGuncelle() {
        double toplam = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            toplam += Double.parseDouble(model.getValueAt(i, 3).toString());
        }
        toplamKasa = toplam;
        lblToplamTutar.setText("KASA TOPLAMI: " + toplamKasa + " TL");
    }

    private void temizle() {
        txtDaireNo.setText("");
        txtAdSoyad.setText("");
        txtTutar.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AidatTakipSistemi().setVisible(true));
    }
}