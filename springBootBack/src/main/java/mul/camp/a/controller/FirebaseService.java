package mul.camp.a.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.StorageClient;

@Service
public class FirebaseService {

	  @Value("${app.firebase-bucket}") private static String firebaseBucket;
	  public static String uploadFiles(MultipartFile file, String nameFile) throws IOException, FirebaseAuthException { 
		  System.out.println("firebaseService==========");
		  Bucket bucket = StorageClient.getInstance().bucket(); 
		  System.out.println("1111111111111111111111");
		  InputStream content = new ByteArrayInputStream(file.getBytes()); 
		  System.out.println("222222222222222222222222");
		  Blob blob = bucket.create(nameFile.toString(), content, file.getContentType()); 
		  System.out.println("333333333333333333333333");
		  return blob.getMediaLink(); 
		}
}
