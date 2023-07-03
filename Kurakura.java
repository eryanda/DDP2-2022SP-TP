import java.awt.Dimension;
import java.awt.Graphics2D; 
import java.awt.geom.AffineTransform; 
import java.awt.image.BufferedImage;


/**
 * 
 * Kurakura.java
 * 
 * Class {@code Kurakura} mendefinisikan object kura-kura.
 * Object kura-kura digerakkan oleh beberapa method antara lain:
 * maju, mundur dan rotasi. Object kura-kura digambarkan oleh method
 * {@code draw} yang akan dipanggil oleh object yang memiliki 
 * object kura-kura ini untuk digambarkan pada object tersebut.
 * Object kura-kura ini direpresentasikan oleh image kura-kura. 
 * Informasi mengenai nama file image terletak pada 
 * text file dengan nama {@code kurakuraku.properties}.
 * File ber extensi {@code .properties} dapat diakses langsung 
 * melalui library java tertentu.
 * <br>
 * <br>
 * Tugas anda pada file ini:<br>
 * - Lengkapi javadoc comment pada tiap deklarasi method. <br>
 * - Lengkapi method "mundur" yang belum ada implementasinya.<br> 
 * - Lengkapi dengan method lain yang dibutuhkan.<br>
 * 
 * @author Ade Azurat for DPBO 2008 @ Fasilkom UI
 * @author Ade Azurat for DDP2 2023 @ Fasilkom UI
 *
 */

public class Kurakura {
    
   private int x = 200, y = 100;            // default awal posisi kura-kura
   private int width = 400, height = 300;   // default ukuran layar/canvas
   private double arah = 0;                 // arah 0 -> sumbu x
   private boolean jejak = true;            // status membuat jejak atau tidak
   
   private String imageName = "turtle.gif"; // default file name object kurakura
   private java.awt.Image  img;             // object image kura-kura. 
   private BufferedImage imgJejak;          // object image untuk jejak kura-kura
   private AffineTransform matRotasi;       // mendefinisikan matriks rotasi
   private AffineTransform matTrans;        // mendefinisikan matriks translasi      
   private AffineTransform matGabung;       // mendefinisikan matriks gabungan 
   
    /** Creates a new instance of Kurakura */
    public Kurakura() {
      
      java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("Kurakuraku");       
      imageName = bundle.getString("image"); 
        
      img = java.awt.Toolkit.getDefaultToolkit().getImage(imageName);
      
      matRotasi = new AffineTransform();
      matGabung = new AffineTransform();
      matTrans = new AffineTransform();
      reset();       
    }

    /** Membuat object kurakura dengan ukuran lebar tinggi tertentu */
    public Kurakura(int w, int h){
        this();
        setSize(w,h);
        reset();
    }
    
    public void setSize(int w, int h){        
        width = w;
        height = h;        
        imgJejak = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
    }
    
    /** Mengatur posisi kura-kura ke posisi awal */
    public void reset(){        
        x = width/2-15;
        y = height/2-20;   
        matTrans.setToTranslation(x, y);
    }
    
    public void rotasi(double d){        
        arah += Math.toRadians(d);
        matRotasi.setToRotation(arah,img.getWidth(null)/2,img.getHeight(null)/2); // rotasi dihitung dari pusat image.              
    }
    
    public void maju(double jarak){
        double dx,dy;
        dx = jarak * Math.cos(arah);
        dy = jarak * Math.sin(arah); 
        
        if (jejak){
            Graphics2D g = imgJejak.createGraphics();
            g.draw(new java.awt.geom.Line2D.Double(x,y,x+dx,y+dy));
        }
        
        x += dx;
        y += dy;
        matTrans.setToTranslation(x,y);
    }
    
    public void mundur(double jarak){
        double dx,dy;
        dx = -jarak * Math.cos(arah);
        dy = -jarak * Math.sin(arah); 
        
        if (jejak){
            Graphics2D g = imgJejak.createGraphics();
            g.draw(new java.awt.geom.Line2D.Double(x,y,x+dx,y+dy));
        }
        
        x += dx;
        y += dy;
        matTrans.setToTranslation(x,y);
    }
    
    /** 
     * Mengatur apakah gerakan kurakura akan memberikan 
     * jejak dilayar atau tidak bila bernilai {@code True}
     * maka akan terlihat jejak garis.
     * 
     * @param b sebuah boolean yang menentukan 
     *          akan memberikan jejak atau tidak  
     * 
     */
    public void setJejak(boolean v){
        jejak = v;
    }
    
    /** 
     * Menggambarkan kura-kura 
     * 
     * @param g object {@code Graphics2D} secara internal menggunakan library
     *          akan menggambarkan kurakura.
     */
    public void draw(Graphics2D g)
    {      
      matGabung.setToIdentity();     
      matGabung.concatenate(matTrans);            
      matGabung.concatenate(matRotasi);      
      if (imgJejak!=null)
          g.drawImage(imgJejak,img.getWidth(null)/2,img.getHeight(null)/2,null);
      if (img!=null)
          g.drawImage(img, matGabung, null);
    }
    
    /** 
     * Accessor method: 
     * Mengambil informasi posisi kurakura dilayar
     * 
     * @return informasi posisi kurakura saat ini dalam object {@code Dimension}
     *  
     */
    public Dimension getPosition(){
        return new Dimension(x,y);
    }

    /** 
     * Mutator method: 
     * men-set posisi kurakura 
     * 
     * @param informasi posisi kurakura dalam object {@code Dimension}
     *  
     */
    public void setPosition(Dimension pos){
        x = (int) pos.getWidth();
        y = (int) pos.getHeight();
        matTrans.setToTranslation(x, y);
    }
    
    public double getArah(){
        return arah;
    }
    
    public void setArah(double a){
        arah =a;
        matRotasi.setToRotation(arah,img.getWidth(null)/2,img.getHeight(null)/2); // rotasi dihitung dari pusat image.              
    }
}
