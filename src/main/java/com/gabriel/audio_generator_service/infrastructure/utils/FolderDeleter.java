package com.gabriel.audio_generator_service.infrastructure.utils;

import java.io.File;

public class FolderDeleter {

    /**
     * Deletes the specified folder and all its contents.
     *
     * @param folder The folder to be deleted.
     */
    public static void deleteFolder(File folder) {
        if (!folder.exists()) {
            System.out.println("The folder does not exist: " + folder.getAbsolutePath());
            return;
        }

        // If it's a directory, delete all contents recursively
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) { // Check if directory is not empty
                for (File file : files) {
                    // Recursively delete files and subdirectories
                    deleteFolder(file);
                }
            }
        }

        // Delete the folder/file itself
        boolean deleted = folder.delete();
        if (deleted) {
            System.out.println("Deleted: " + folder.getAbsolutePath());
        } else {
            System.out.println("Failed to delete: " + folder.getAbsolutePath());
        }
    }

}

