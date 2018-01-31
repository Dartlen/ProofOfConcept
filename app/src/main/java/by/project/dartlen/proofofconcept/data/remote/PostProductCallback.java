package by.project.dartlen.proofofconcept.data.remote;

import com.google.firebase.storage.UploadTask;

public interface PostProductCallback {
    void onSuccess(UploadTask.TaskSnapshot taskSnapshot);
    void onFailure(Exception exception);
    void onProgress(UploadTask.TaskSnapshot taskSnapshot);
    void onPaused(UploadTask.TaskSnapshot taskSnapshot);
}
