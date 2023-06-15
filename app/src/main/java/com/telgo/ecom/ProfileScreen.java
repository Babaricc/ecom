package com.telgo.ecom;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.telgo.ecom.databinding.ActivityProfileScreenBinding;
import com.telgo.ecom.session.UserSession;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class ProfileScreen extends AppCompatActivity {

    Uri imageUri;

    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    CustomDialog customDialog;
    String mobileNumber = null;

    UserSession userSession;
    Geocoder geocoder;
    List<Address> addresses;
    Address currentAddress;
    User currentUser;
    LocationManager mLocationManager;
    LocationListener mLocationListener;

    Location location;
    int LOCATION_REFRESH_TIME = 15000; // 15 seconds to update
    int LOCATION_REFRESH_DISTANCE = 500; // 500 meters to updat

    ActivityProfileScreenBinding binding;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.userImage.setDrawingCacheEnabled(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");

        if (getIntent().getExtras() != null && getIntent().getExtras().getString("mobileNumber") != null) {
            mobileNumber = getIntent().getExtras().getString("mobileNumber");
        }

        if (mobileNumber != null && !mobileNumber.isEmpty())
            databaseReference = firebaseDatabase.getReference("users/" + mobileNumber);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        if (!CheckPermissions.askPermission(ProfileScreen.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            showMessage("ACCESS LOCATION PERMISSION");
            return;
        }

        if (!CheckPermissions.askPermission(ProfileScreen.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
            showMessage("ACCESS LOCATION PERMISSION");
            return;
        }

        initializeLocation();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    currentUser = snapshot.getValue(User.class);
                    Log.d("User!!", "onDataChange: " + currentUser);
                    Log.d("User!!", "getImageId: " + currentUser.getImageId());

                    binding.name.setText(currentUser.getName());
                    binding.email.setText(currentUser.getEmail());
                    binding.password.setText(currentUser.getPassword());
                    binding.address.setText(currentUser.getFullAddress());
                    binding.contact.setText(currentUser.getContact());
                    binding.userName.setText(currentUser.getUsername());
                    binding.address.setText(currentUser.getFullAddress());

                    Log.d("imageId:", "onDataChange: " + currentUser.getImageId());

                    StorageReference mImageRef = FirebaseStorage.getInstance().getReference("images/" + currentUser.getImageId());
                    final long ONE_MEGABYTE = 1024 * 1024;
                    mImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Picasso.get().load(uri).fit().centerCrop().into(binding.userImage);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.d("Failure ", "onFailure: " + exception.getMessage());
                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("User!! cancel", "onDataChange: ");
            }
        });


    }

    @SuppressLint("MissingPermission")
    private void initializeLocation() {
        if (mLocationListener == null) initializeLocationListner();
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        @SuppressLint("MissingPermission") Location location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        Log.d("location!!", "Latitude 2!!:" + latitude);
        Log.d("location!!", " Longitude 2:" + longitude);

        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, mLocationListener);

    }

    private void initializeLocationListner() {
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    currentAddress = addresses.get(0);
                    binding.address.setText(currentAddress.getAddressLine(0));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        };


    }

    public void showMessage(String permission) {
        Toast.makeText(this, "Please allow " + permission, Toast.LENGTH_SHORT).show();
    }


    public void browseImage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                binding.userImage.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(ProfileScreen.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(ProfileScreen.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }

    }


    public void saveProfile(View view) {
        if (mLocationManager == null) initializeLocation();
        databaseReference = firebaseDatabase.getReference("users");
        String imageId = UUID.randomUUID().toString();

        User user = new User();
        user.setName(binding.userName.getText().toString());
        user.setFullAddress(binding.address.getText().toString());
        user.setEmail(binding.email.getText().toString());
        user.setContact(binding.contact.getText().toString());
        user.setUsername(binding.userName.getText().toString());
        user.setPassword(binding.password.getText().toString());
        user.setFullAddress(currentAddress != null ? currentAddress.getAddressLine(0) : "");

        com.telgo.ecom.Address address = new com.telgo.ecom.Address();
        address.setCity(currentAddress.getLocality());
        address.setState(currentAddress.getAdminArea());
        address.setCountry(currentAddress.getCountryName());
        address.setKnownName(currentAddress.getFeatureName());

        user.setAddress(address);
        user.setImageId(imageId);

        Log.d("Save!!", "saveProfile: " + user);

        String userId = user.getContact();
        if (mobileNumber != null && !mobileNumber.isEmpty()) {
            userId = mobileNumber;
        }
        databaseReference.child(userId).setValue(user);

        userSession = new UserSession(this);

        customDialog = new CustomDialog(ProfileScreen.this, "Profile Saving", "Your profile data has been saved Successfully!", R.raw.success, false);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Saving...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        if (imageUri != null) {
            storageReference = storage.getReference("images/" + imageId);
            storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageSaved = true;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    CustomDialog errorDialog = new CustomDialog(ProfileScreen.this, "Profile Photo Saving Error ", "" + e.getMessage(), R.raw.failure, false);
                    errorDialog.show();
                    imageSaved = false;
                }
            });
        }

        progressDialog.dismiss();
        customDialog.show();

        customDialog.findViewById(R.id.dialog_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                userSession.setUserPreferences(user, imageUri);
                startActivity(new Intent(ProfileScreen.this, HomeScreen.class));
            }
        });
    }

    boolean imageSaved = false;

}