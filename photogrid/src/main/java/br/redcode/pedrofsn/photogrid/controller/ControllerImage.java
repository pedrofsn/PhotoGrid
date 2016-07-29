package br.redcode.pedrofsn.photogrid.controller;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.redcode.pedrofsn.photogrid.App;
import br.redcode.pedrofsn.photogrid.domain.CallbackItemChanged;
import br.redcode.pedrofsn.photogrid.utils.Constantes;
import br.redcode.pedrofsn.photogrid.utils.PhotoGrid;
import br.redcode.pedrofsn.photogrid.utils.Utils;

/**
 * Created by User on 29/07/2016.
 */
public class ControllerImage {

    private final PhotoGrid photoGrid;
    public int tempPosition = Constantes.VALOR_INVALIDO;
    private String mTakePicturePath;
    private CallbackItemChanged callbackItemChanged;

    public ControllerImage(PhotoGrid photoGrid, CallbackItemChanged callbackItemChanged) {
        this.callbackItemChanged = callbackItemChanged;
        this.photoGrid = photoGrid;
    }

    private static File getNewImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    public Intent getTakePictureIntent() {
        mTakePicturePath = null;
        File pictureFile = null;

        try {
            pictureFile = getNewImageFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (pictureFile == null)
            return null;

        mTakePicturePath = pictureFile.getAbsolutePath();

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(pictureFile));
        return takePictureIntent;
    }

    public Intent getAttachPictureIntent() {
        Intent attachPictureIntent = new Intent(Intent.ACTION_PICK);
        attachPictureIntent.setType("image/*");
        return attachPictureIntent;
    }

    public void handleTakePictureResult() {
        addThumbnail(mTakePicturePath);
        addNewImageToGallery(mTakePicturePath);
    }

    public void handleAttachPictureResult(Intent data) {
        Uri uri = data.getData();
        addThumbnail(new File(getRealPathFromURI(uri)));
    }

    private void addNewImageToGallery(String filePath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(filePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        App.getContext().sendBroadcast(mediaScanIntent);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result = null;
        Cursor cursor = App.getContext().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public void addThumbnail(Object obj) {

        if (!Utils.isNullOrEmpty(obj) && Constantes.VALOR_INVALIDO != tempPosition) {
            if (tempPosition <= photoGrid.getData().size() - 1) {
                photoGrid.getData().get(tempPosition).setPath(obj);
                callbackItemChanged.notifyItemChanged(tempPosition);
                tempPosition = Constantes.VALOR_INVALIDO;
            }
        }
    }

    public void setTempPosition(int tempPosition) {
        this.tempPosition = tempPosition;
    }
}
