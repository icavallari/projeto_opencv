/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abc;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;

/**
 *
 * @author Rodrigo
 */
public class Main {

    private final static Integer ESP_LATERAL = 3, ESP_SUPERIOR = 8;
    private final static String URL_LIB_FACE = "C:\\opencv\\sources\\data\\lbpcascades\\lbpcascade_frontalface.xml";

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {

        CascadeClassifier faceDetector
                = new CascadeClassifier(URL_LIB_FACE);

        Mat imagem = Imgcodecs.imread("img/varias.jpg");
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(imagem, faceDetections);

        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(imagem,
                    new Point(rect.x - ESP_LATERAL, rect.y - ESP_SUPERIOR),
                    new Point(rect.x + rect.width + ESP_LATERAL, rect.y + rect.height + ESP_SUPERIOR),
                    new Scalar(0, 255, 0));
        }

        if (faceDetections.toArray().length == 0) {
            System.out.println("sem rosto");
        } else {
            System.out.println("Identificamos " + faceDetections.toArray().length + " rosto(s) na imagem carregada.");
        }

        Imgcodecs.imwrite("img/face.jpg", imagem);

    }
}
