package com.example.jenna.whodat;//package aws.example.rekognition.image;
import android.content.Intent;
import android.util.Log;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.BoundingBox;
import com.amazonaws.services.rekognition.model.Celebrity;
import com.amazonaws.services.rekognition.model.RecognizeCelebritiesRequest;
import com.amazonaws.services.rekognition.model.RecognizeCelebritiesResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import com.amazonaws.util.IOUtils;
import java.util.List;


public class RecognizeCelebrities {
    List<Celebrity> celebs;

    public  void findCeleb(ByteBuffer pic) {
        ByteBuffer photo = pic;
        AWSCredentials credentials = new BasicAWSCredentials("Access Key","Secret Access Key");
        AmazonRekognition rekognitionClient = new AmazonRekognitionClient(credentials);

       /* ByteBuffer imageBytes=null;
        try (InputStream inputStream = new FileInputStream(new File(photo))) {
            imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        }
        catch(Exception e)
        {
            Log.i("failure", "Failed to load file " + photo);
            System.exit(1);
        }

*/
        RecognizeCelebritiesRequest request = new RecognizeCelebritiesRequest()
                .withImage(new Image()
                        .withBytes(photo));

       /* RecognizeCelebritiesRequest request = new RecognizeCelebritiesRequest()
                .withImage(photo);*/
        Log.i("looking", "Looking for celebrities in image \n");

        RecognizeCelebritiesResult result=rekognitionClient.recognizeCelebrities(request);

        //Display recognized celebrity information
        celebs=result.getCelebrityFaces();
        Log.i("celebs", celebs.size() + " celebrity(s) were recognized.\n");
        for (Celebrity celebrity: celebs) {
            Log.i("recognized", "Celebrity recognized: " + celebrity.getName());
            Log.i("id", "Celebrity ID: " + celebrity.getId());
            BoundingBox boundingBox=celebrity.getFace().getBoundingBox();
            Log.i("position", "position: " +
                    boundingBox.getLeft().toString() + " " +
                    boundingBox.getTop().toString());
            Log.i("further info", "Further information (if available):");
            for (String url: celebrity.getUrls()){
                Log.i("url", url);
            }
            //System.out.println();
        }
        Log.i("unrecognized", result.getUnrecognizedFaces().size() + " face(s) were unrecognized.");
    }

    public List<Celebrity> getCelebs() {
        return celebs;
    }
}
