# 🏢 Site Aidat Takip Sistemi (Apartment Dues Tracking System)

![Java](https://img.shields.io/badge/Language-Java-orange)
![IDE](https://img.shields.io/badge/IDE-IntelliJ_IDEA-blue)
![License](https://img.shields.io/badge/License-MIT-green)

**BPR 151 - Programcılığa Giriş** dersi final projesi kapsamında geliştirilmiş, apartman ve site yöneticileri için basit, hızlı ve verimli bir masaüstü aidat takip yazılımıdır.

## 📖 Proje Hakkında
Küçük ve orta ölçekli apartman yönetimlerinde aidat takibi genellikle defterler veya karmaşık Excel dosyaları ile yapılmaktadır. Bu proje, veri kaybını önlemek ve hesaplamaları otomatize etmek amacıyla geliştirilmiştir. Yöneticiler bu arayüz sayesinde daire sakinlerini kaydedebilir, ödemeleri takip edebilir ve kasadaki toplam durumu anlık olarak görebilirler.

## ✨ Özellikler

* **📝 Kolay Veri Girişi:** Daire No, Ad Soyad, Dönem ve Tutar bilgileriyle hızlı kayıt.
* **🔍 Canlı Arama (Live Search):** Yüzlerce kayıt arasından isim veya daire numarasına göre anlık filtreleme.
* **💾 Veri Kalıcılığı:** "Dosyaya Kaydet" özelliği ile verileri `.txt` formatında dışarı aktarma ve yedekleme.
* **🗑️ Güvenli Silme:** Yanlışlıkla veri silinmesini önlemek için onay (Confirmation) mekanizması.
* **💰 Otomatik Hesaplama:** Eklenen veya silinen kayıtlara göre Kasa Toplamını anlık güncelleme.
* **🎨 Modern Arayüz:** Java Swing `Nimbus` teması ile kullanıcı dostu görünüm.

## 🛠️ Kullanılan Teknolojiler

* **Dil:** Java (JDK 21)
* **Arayüz (GUI):** Java Swing (javax.swing)
* **IDE:** IntelliJ IDEA
* **Dosya İşlemleri:** Java I/O (BufferedWriter)


## 🚀 Kurulum ve Çalıştırma

Projeyi yerel bilgisayarınızda çalıştırmak için şu adımları izleyin:

1.  Bu repoyu klonlayın:
    ```bash
    git clone [https://github.com/KULLANICIADINIZ/site-aidat-takip.git](https://github.com/KULLANICIADINIZ/site-aidat-takip.git)
    ```
2.  Projeyi **IntelliJ IDEA** ile açın.
3.  `AidatTakipSistemi.java` dosyasına sağ tıklayın ve **Run** seçeneğini seçin.
