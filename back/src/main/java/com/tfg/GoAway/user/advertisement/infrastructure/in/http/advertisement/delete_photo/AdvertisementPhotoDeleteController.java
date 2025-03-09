package com.tfg.GoAway.user.advertisement.infrastructure.in.http.advertisement.delete_photo;

import com.tfg.GoAway.user.advertisement.application.advertisement.delete_photo.AdvertisementPhotoDelete;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/advertisements/{advertisementId}/photos")
public class AdvertisementPhotoDeleteController {

    private final AdvertisementPhotoDelete advertisementPhotoDelete;

    @DeleteMapping("/{photoId}")
    public ResponseEntity<String> deletePhoto(
            @PathVariable String advertisementId,
            @PathVariable String photoId) {

                
        try {
            advertisementPhotoDelete.deletePhoto(advertisementId, photoId);
            return ResponseEntity.ok("Foto eliminada correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(403).body(e.getMessage()); // 403: Forbidden
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error inesperado al eliminar la foto.");
        }
    }
}