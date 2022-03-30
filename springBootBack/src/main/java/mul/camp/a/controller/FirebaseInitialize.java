package mul.camp.a.controller;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
 
@Service 
public class FirebaseInitialize {
	
//	@Value("${app.firebase-configuration-file}")
//	private String fcp;
	
	@PostConstruct //has to be Run during the Start of
	
	public void initialize() {
		System.out.println("initialize=======================");
		try {
			FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");
			System.out.println("ddddddddddddddd");
			FirebaseOptions options = new FirebaseOptions.Builder() 
					.setCredentials(GoogleCredentials.fromStream(serviceAccount)) 
					.setDatabaseUrl("https://hospital-9c77a.appspot.com") .build();
			System.out.println("wwwwwwwwwwwwwwww");
			FirebaseApp.initializeApp(options);
		} catch (Exception e){
			e.printStackTrace();
			
		}
		
//		try {
//			System.out.println("dddddddddddddddddddddd");
//            FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(
//                GoogleCredentials.fromStream(
//                    new ClassPathResource(fcp).getInputStream())).build();
//            System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrr");
//            if (FirebaseApp.getApps().isEmpty()) {
//                FirebaseApp.initializeApp(options);
//                System.out.println("wwwwwwwwwwwwwwww");
//            }
//        } catch (IOException e) {
//        }
	}
}
