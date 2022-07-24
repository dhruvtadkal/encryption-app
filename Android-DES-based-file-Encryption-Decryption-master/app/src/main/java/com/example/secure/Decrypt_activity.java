package com.example.secure;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Key;

public class Decrypt_activity extends Activity {
    ImageButton addFileBtn;
    Button decButton;
    TextView pathTextView;
    Intent addFileIntent;
    String fileName;
    Uri fileUri;
    SharedPreferences sharedPreferences;
    private static final String Shared_pref_name = "secureDesApp";
    private static final String Key_key = "secureDesKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);
        sharedPreferences = getSharedPreferences(Shared_pref_name, MODE_PRIVATE);
        String password = sharedPreferences.getString(Key_key, null);
        this.addFileBtn = findViewById(R.id.addFileBtnDec);
        this.decButton = findViewById(R.id.decFileBtn);
        this.pathTextView = findViewById(R.id.pathTextViewDec);
        this.addFileBtn.setOnClickListener((View v) -> {
            Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "secure/encrypt");
            addFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
            addFileIntent.setDataAndType(uri, "*/*");
            startActivityForResult(addFileIntent, 10);
        });

        this.decButton.setOnClickListener((View v)->{
            try {
                Key_Generator kgn =new Key_Generator(password);
                Key key;
                key = kgn.generateKey();
                InputStream inputStream = v.getContext().getContentResolver().openInputStream(this.fileUri);
                byte[] data = new byte[inputStream.available()];
                inputStream.read(data);
                if(data==null){
                    Toast.makeText(v.getContext(), "Data not fetched", Toast.LENGTH_SHORT).show();
                }
                else{
                    byte[] decrypted = Decrypt.decode(key, data);
                    if(decrypted==null){
                        Toast.makeText(v.getContext(), "Empty data", Toast.LENGTH_SHORT).show();
                    }else{
                        File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                                + File.separator + "secure/decrypt");
                        if(!directory.exists()) directory.mkdirs();
                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                                + File.separator + "secure/decrypt", this.fileName);
                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                        bos.write(decrypted);
                        bos.flush();
                        bos.close();
                    }
                }
            }catch (Exception e){
                System.out.println(e.getLocalizedMessage());
                Toast.makeText(v.getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (resultCode == RESULT_OK) {
                this.fileUri = data.getData();
                Cursor cursor = getContentResolver().query(this.fileUri, null, null, null, null);
                int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                cursor.moveToFirst();
                this.fileName = cursor.getString(nameIndex);
                cursor.close();
                this.addFileBtn.setImageResource(R.drawable.ic_file);
                this.pathTextView.setText(this.fileName);
            }
        }
    }
}