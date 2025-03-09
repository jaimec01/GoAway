package com.tfg.GoAway.user.advertisement.infrastructure.out.db.sql_server;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AdvertisementPhotoStorageService {

    // 📁 Ruta FIJA en el sistema de archivos (NO en src/main/resources/)
    private static final String BASE_DIR = "/app/images";
    
    public List<String> storePhotos(List<MultipartFile> photos, String userEmail, String adId) {
        if (photos == null || photos.isEmpty()) {
            System.out.println("⚠ No hay fotos para guardar.");
            return Collections.emptyList();
        }

        try {
            // **1️⃣ Crear carpeta fuera del classpath para evitar Tomcat**
            File baseDir = new File(BASE_DIR, userEmail + "/" + adId);
            if (!baseDir.exists() && baseDir.mkdirs()) {
                System.out.println("📁 Carpeta creada en: " + baseDir.getAbsolutePath());
            }

            List<String> resultPaths = new ArrayList<>();
            for (MultipartFile photo : photos) {
                String fileName = photo.getOriginalFilename();
                if (fileName == null || fileName.trim().isEmpty()) {
                    fileName = System.currentTimeMillis() + ".jpg";
                }

                File imageFile = new File(baseDir, fileName);

                try {
                    photo.transferTo(imageFile);
                    System.out.println("✅ Imagen guardada en: " + imageFile.getAbsolutePath());
                } catch (IOException e) {
                    System.err.println("❌ ERROR al guardar imagen en " + imageFile.getAbsolutePath() + ": " + e.getMessage());
                }

                String relativePath = userEmail + "/" + adId + "/" + fileName;
                resultPaths.add(relativePath);
            }

            return resultPaths;

        } catch (Exception e) {
            throw new RuntimeException("❌ ERROR GENERAL: " + e.getMessage(), e);
        }
    }
}
