package com.example.demoFirebase.config;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.InputStream;

@Configuration
public class FirebaseConfig{

    @Bean
    public DatabaseReference firebaseDatabase(){
        DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
        return firebase;
    }

    @Value("${rs.pscode.firebase.databse.url}")
    private String databaseUrl;

    @Value("${rs.pscode.firebase.config.path}")
    private String configPath;

@PostConstruct
    public void init(){

    //download Json and create service account

    InputStream inputStream = FirebaseConfig.class.getClassLoader().getResourceAsStream(configPath);

    FirebaseOptions options =  new FirebaseOptions.Builder().setServiceAccount(inputStream)
            .setDatabaseUrl(databaseUrl).build();

    FirebaseApp.initializeApp(options);
}






}