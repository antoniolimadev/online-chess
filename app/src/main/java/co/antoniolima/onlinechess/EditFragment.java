package co.antoniolima.onlinechess;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class EditFragment extends Fragment {

    Button takePictureButton;
    Button saveDataButton;
    ImageView imageView;
    Uri file;
    EditText editName, editEmail;
    DatePicker editDate;
    String name, email, userDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_fragment, container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.edit);
        takePictureButton = (Button) view.findViewById(R.id.button_image);
        saveDataButton = (Button) view.findViewById(R.id.button_save);
        imageView = (ImageView) view.findViewById(R.id.foto_id);

        editName = (EditText) getView().findViewById(R.id.editName);
        editEmail = (EditText) getView().findViewById(R.id.editEmail);
        editDate = (DatePicker) getView().findViewById(R.id.datePicker);

        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeFotoFunc(view);
            }
        });

        saveDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataFunc(view);
            }
        });

    }

    public void saveDataFunc(View view){
        editName = (EditText) getView().findViewById(R.id.editName);
        name = editName.getText().toString();
        ((MainActivity)getActivity()).setUserName(name);
        editEmail = (EditText) getView().findViewById(R.id.editEmail);
        email = editEmail.getText().toString();
        ((MainActivity)getActivity()).setUserEmail(email);
        editDate = (DatePicker) getView().findViewById(R.id.datePicker);
        userDate = editDate.getDayOfMonth() + "/" + editDate.getMonth()
                + "/" + editDate.getYear();
        ((MainActivity)getActivity()).setUserDate(userDate);
        getFragmentManager().popBackStackImmediate();
    }

    public void takeFotoFunc(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        file = Uri.fromFile(photo);
        getActivity().startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = file;
                    getActivity().getContentResolver().notifyChange(selectedImage, null);
                    ContentResolver cr = getActivity().getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);

                        imageView.setImageBitmap(bitmap);
                        Toast.makeText(getActivity(), selectedImage.toString(),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }

}
