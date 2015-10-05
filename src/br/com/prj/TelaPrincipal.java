/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prj;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 *
 * @author Rodrigo
 */
public class TelaPrincipal extends javax.swing.JFrame {

    // posição do label dentro do jPanel
    int boundX = 12;
    int boundY = 22;

    // aumenta o espaço do rosto encontrado do retangulo
    private final static Integer PAD_LATERAL = 3, PAD_SUPERIOR = 8;
    private final static String URL_LIB_FACE = "C:\\opencv\\sources\\data\\lbpcascades\\lbpcascade_frontalface.xml";
    File selectedFile = null;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public TelaPrincipal() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCarregar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnProcurar = new javax.swing.JButton();
        totalRostos = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setLocationByPlatform(true);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        btnCarregar.setText("Carregar Imagem");
        btnCarregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarregarActionPerformed(evt);
            }
        });

        btnProcurar.setText("Procurar Rostos");
        btnProcurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcurarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Rostos Encontrados"));
        jPanel1.setAutoscrolls(true);
        jPanel1.setPreferredSize(new java.awt.Dimension(440, 560));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 436, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 537, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCarregar)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnProcurar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(totalRostos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCarregar)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(115, 115, 115)
                                .addComponent(btnProcurar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addComponent(totalRostos))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCarregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarregarActionPerformed

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + File.separator + "Pictures"));
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            jLabel1.setIcon(resizeImage(selectedFile.getAbsolutePath(), jLabel1.getWidth(), jLabel1.getHeight()));
        }

    }//GEN-LAST:event_btnCarregarActionPerformed

    private void btnProcurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcurarActionPerformed

        // remove todos os labels do componente
        jPanel1.removeAll();

        // reposicionao o primeiro label
        boundX = 12;
        boundY = 22;

        CascadeClassifier faceDetector
                = new CascadeClassifier(URL_LIB_FACE);

        Mat imagem = Imgcodecs.imread(selectedFile.getAbsolutePath());
        // imagem com retangulo dos rostos encontrados
        Mat imagemRect = Imgcodecs.imread(selectedFile.getAbsolutePath());
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(imagem, faceDetections);

        // tentar verificar se o rect encontrado possui olhos
        Rect[] faceEncontrada = new Rect[faceDetections.toArray().length];
        int i = 0;
        for (Rect rect : faceDetections.toArray()) {

            faceEncontrada[i] = new Rect(
                    new Point(rect.x - PAD_LATERAL + 5, rect.y - PAD_SUPERIOR + 5),
                    new Point(rect.x + rect.width + PAD_LATERAL, (rect.y + rect.height + PAD_SUPERIOR) - 5)
            );

            adicionarLabel(toBufferedImage(new Mat(imagem, faceEncontrada[i])));

            Imgproc.rectangle(imagemRect,
                    new Point(rect.x - PAD_LATERAL, rect.y - PAD_SUPERIOR),
                    new Point(rect.x + rect.width + PAD_LATERAL, (rect.y + rect.height + PAD_SUPERIOR) - 5),
                    new Scalar(0, 255, 0));

            i++;
        }

        if (faceDetections.toArray().length == 0) {
            totalRostos.setText("Não foi possível identificar nenhum rosto na imagem selecionada.");
        } else {
            totalRostos.setText("Identificamos " + faceDetections.toArray().length + " rosto(s) na imagem carregada.");
        }

        Imgcodecs.imwrite("img/imagem_processada.jpg", imagemRect);
        jLabel1.setIcon(resizeImage("img/imagem_processada.jpg", jLabel1.getWidth(), jLabel1.getHeight()));


    }//GEN-LAST:event_btnProcurarActionPerformed

    private ImageIcon resizeImage(String link, int w, int h) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(link));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image dimg = img.getScaledInstance(w, h,
                Image.SCALE_SMOOTH);
        return new ImageIcon(dimg);
    }

    public void adicionarLabel(final Image img) {

        JLabel lbImagem = new JLabel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, getWidth() - 5, getHeight() - 5, null);
            }
        };

        // Posiciona a imagem
        lbImagem.setBounds(boundX, boundY, 70, 70);
        lbImagem.setVisible(rootPaneCheckingEnabled);

        // Prepara a posição x do próximo label
        boundX += 70;

        // Verifica se estourou o limite
        if (boundX >= jPanel1.getWidth() - 70) {
            // Desce uma fila
            boundY += 70;
            boundX = 10;
        }

        jPanel1.add(lbImagem);
        jPanel1.repaint();

    }

    /**
     * Converte a imagem Mat em Image sem a necessidade de criar um arquivo
     * fisico
     *
     * @param m
     * @return
     */
    public Image toBufferedImage(Mat m) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (m.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels() * m.cols() * m.rows();
        byte[] b = new byte[bufferSize];
        m.get(0, 0, b);
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;

    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCarregar;
    private javax.swing.JButton btnProcurar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel totalRostos;
    // End of variables declaration//GEN-END:variables
}
