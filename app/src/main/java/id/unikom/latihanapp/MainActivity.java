package id.unikom.latihanapp;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import id.unikom.latihanapp.adapter.ContactAdapter;
import id.unikom.latihanapp.db.AppDatabase;
import id.unikom.latihanapp.model.Contact;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    RecyclerView list;
    ContactAdapter contactAdapter;
    //range is from 0 to 65535.
    private static final int RC_LOCATION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        FormActivity.class);
                startActivity(intent);
            }
        });

        list = findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        list.setHasFixedSize(true);

        // QUERY KE DATABASE
        List<Contact> lapors = AppDatabase
                .getInstance(this).contactDao().getAll();
        // Array lapor mapping ke row_layout
        contactAdapter = new ContactAdapter(this,lapors);
        list.setAdapter(contactAdapter);
        requestPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_LOCATION)
    private void requestPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "Location Permission",
                    RC_LOCATION, perms);
        }
    }
}