package com.bongoye.faceRecognition;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.CascadeClassifier;
  
public class faceRecognizer {
    public static String pathToProject = System.getProperty("user.dir");
    public faceRecognizer(String fileToRead,String haarcascade)
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        CascadeClassifier faceDetector = new CascadeClassifier();
        faceDetector.load(pathToProject + "/haarcascades/" + haarcascade);
  
        Mat image = Imgcodecs.imread(fileToRead);
  
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image,faceDetections);
  
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(
                image, new Point(rect.x, rect.y),
                new Point(rect.x + rect.width,
                          rect.y + rect.height),
                new Scalar(0, 255, 0));
        }

        Imgcodecs.imwrite("output.jpg", image);
    }
}