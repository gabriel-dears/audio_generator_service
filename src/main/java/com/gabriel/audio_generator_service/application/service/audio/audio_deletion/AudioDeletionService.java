package com.gabriel.audio_generator_service.application.service.audio.audio_deletion;

import com.gabriel.audio_generator_service.application.service.url.url_generator.UrlGenerator;
import com.gabriel.audio_generator_service.infrastructure.utils.FolderDeleter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

@Service
public class AudioDeletionService {

    private static final Logger LOGGER = Logger.getLogger(AudioDeletionService.class.getName());

    private final UrlGenerator urlGenerator;

    // A thread-safe list to store paths of directories to be deleted
    private final List<File> foldersToDelete = new CopyOnWriteArrayList<>();

    public AudioDeletionService(UrlGenerator urlGenerator) {
        this.urlGenerator = urlGenerator;
    }

    // Add the directory path to the list for future deletion
    public void addAudioToDelete(String videoId) {
        File folderToDelete = urlGenerator.getClipsUrlBasedOnVideoIdAsFile(videoId);
        foldersToDelete.add(folderToDelete);
        LOGGER.info("Added for deletion: " + folderToDelete);
    }

    // Scheduled task to delete all directories once a day
    @Scheduled(cron = "0 50 22 * * ?", zone = "America/Sao_Paulo") // Runs at midnight every day
    public void deleteDirectoriesOnceADay() {
        handleFoldersDeletion();
        foldersToDelete.clear();
    }

    private void handleFoldersDeletion() {
        boolean foldersSuccessfullyDeleted = isFoldersSuccessfullyDeleted();
        handleLogsAfterFoldersDeletion(foldersSuccessfullyDeleted);
    }

    private void handleLogsAfterFoldersDeletion(boolean foldersSuccessfullyDeleted) {
        if (foldersSuccessfullyDeleted) {
            LOGGER.info("Folders deleted: " + foldersToDelete);
        } else {
            LOGGER.severe("Error while deleting folders: " + foldersToDelete);
        }
    }

    private boolean isFoldersSuccessfullyDeleted() {
        boolean foldersSuccessfullyDeleted = true;
        try {
            foldersToDelete.forEach(FolderDeleter::deleteFolder);
        } catch (Exception e) {
            foldersSuccessfullyDeleted = false;
            LOGGER.severe("Error while deleting folders: " + foldersToDelete);
        }
        return foldersSuccessfullyDeleted;
    }

}
