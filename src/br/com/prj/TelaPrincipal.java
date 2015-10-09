/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.prj;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
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

    Mat imagemDest = null;
    Mat imagemCarregada = null;
    Mat blurred = new Mat();

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
        imgCarregada = new javax.swing.JLabel();
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
                        .addComponent(imgCarregada, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                .addComponent(imgCarregada, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            imgCarregada.setIcon(resizeImage(selectedFile.getAbsolutePath(), imgCarregada.getWidth(), imgCarregada.getHeight()));
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

        imagemCarregada = Imgcodecs.imread(selectedFile.getAbsolutePath(), Imgcodecs.CV_LOAD_IMAGE_COLOR);
        // imagem com retangulo dos rostos encontrados
        imagemDest = new Mat(imagemCarregada.rows(), imagemCarregada.cols(), imagemCarregada.type());
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(imagemCarregada, faceDetections);

        // tentar verificar se o rect encontrado possui olhos
        Rect[] faceEncontrada = new Rect[faceDetections.toArray().length];
        int i = 0;
        for (Rect rect : faceDetections.toArray()) {

            faceEncontrada[i] = new Rect(
                    new Point(rect.x - PAD_LATERAL + 5, rect.y - PAD_SUPERIOR + 5),
                    new Point(rect.x + rect.width + PAD_LATERAL, (rect.y + rect.height + PAD_SUPERIOR) - 5)
            );

            adicionarLabel(convertMatToImage(new Mat(imagemCarregada, faceEncontrada[i])), faceEncontrada[i]);

//            ADICIONA RETANGULO DO ROSTO NA IMAGEM  
//            Imgproc.rectangle(imagemDest,
//                    new Point(rect.x - PAD_LATERAL, rect.y - PAD_SUPERIOR),
//                    new Point(rect.x + rect.width + PAD_LATERAL, (rect.y + rect.height + PAD_SUPERIOR) - 5),
//                    new Scalar(0, 255, 0));
            i++;
        }

        if (faceDetections.toArray().length == 0) {
            totalRostos.setText("Não foi possível identificar nenhum rosto na imagem selecionada.");
        } else {
            totalRostos.setText("Identificamos " + faceDetections.toArray().length + " rosto(s) na imagem carregada.");
        }

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

    public void adicionarLabel(final Image img, final Rect faceEncontrada) {

        JLabel lbImagem = new JLabel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, getWidth() - 5, getHeight() - 5, null);
            }
        };

        final Integer ultimoPixelColuna = faceEncontrada.width + faceEncontrada.x;
        final Integer ultimoPixelLinha = faceEncontrada.height + faceEncontrada.y;

        final Mat mat = convertImageToMat(img);
        Imgproc.medianBlur(mat, mat, 17);

        lbImagem.addMouseListener(new MouseAdapter() {

            private Boolean blur = false;

            @Override
            public void mouseClicked(MouseEvent e) {

                int i = -1, j = 0;
                for (int linha = faceEncontrada.y; linha <= ultimoPixelLinha; linha++) {
                    j = 0;
                    i++;
                    for (int coluna = faceEncontrada.x; coluna < ultimoPixelColuna; coluna++) {
                        double[] cor = mat.get(i, j++);
                        if (cor != null) {
                            imagemCarregada.put(linha, coluna, new double[]{cor[0], cor[1], cor[2]});
                        }
                    }
                }

                if (!blur) {
                    Imgcodecs.imwrite("img/imagem_processada.jpg", imagemCarregada);
                } else {
                    System.out.println("remover blur");
                }

                blur = !blur;
                imgCarregada.setIcon(resizeImage("img/imagem_processada.jpg", imgCarregada.getWidth(), imgCarregada.getHeight()));
            }

        });

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
     * Converte um Mat em Image
     *
     * @param m
     * @return new Image()
     */
    public Image convertMatToImage(Mat m) {
        MatOfByte bytemat = new MatOfByte();
        Imgcodecs.imencode(".jpg", m, bytemat);
        byte[] bytes = bytemat.toArray();
        InputStream in = new ByteArrayInputStream(bytes);
        try {
            return ImageIO.read(in);
        } catch (IOException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Convert uma Image em Mat
     *
     * @param img
     * @return new Mat()
     */
    public Mat convertImageToMat(Image img) {
        BufferedImage image = (BufferedImage) img;
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, data);
        return mat;
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
    private javax.swing.JLabel imgCarregada;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel totalRostos;
    // End of variables declaration//GEN-END:variables

}
