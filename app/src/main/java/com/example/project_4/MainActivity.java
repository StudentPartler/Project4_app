package com.example.project_4;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int PERMISSION_REQUEST_CODE_LOCATION = 2;
    private static final int PERMISSION_REQUEST_CODE_AUDIO = 3;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private boolean isRecording = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request location and recording permissions
        requestPermissions();


        // Set up SOS button click listener
        Button sosButton = findViewById(R.id.sosButton);
        sosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle SOS button click
                sendSOS();
            }
        });

        // Set up location button click listener
        Button locationButton = findViewById(R.id.locationButton);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle location button click
                shareLocation();
            }
        });

        // Set up record button click listener
        Button recordButton = findViewById(R.id.recordButton);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle record button click
                toggleRecording();
            }
        });
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            int audioPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);

            // Check if the location permission is not granted
            if (locationPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_REQUEST_CODE_LOCATION);
            }

            // Check if the audio recording permission is not granted
            if (audioPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        PERMISSION_REQUEST_CODE_AUDIO);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE_LOCATION) {
            // Handle the result of the location permission request
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permission granted
                Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // Location permission denied
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_AUDIO) {
            // Handle the result of the audio permission request
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Audio permission granted
                Toast.makeText(this, "Audio permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // Audio permission denied
                Toast.makeText(this, "Audio permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void toggleRecording() {
        if (isRecording) {
            stopRecording();
        } else {
            startRecording();
        }
    }
    private void sendSOS() {
        // Implement SOS functionality, e.g., send distress signal to Raspberry Pi
        Toast.makeText(this, "SOS activated", Toast.LENGTH_SHORT).show();
    }

    private void shareLocation() {
        // Implement location sharing functionality
        Toast.makeText(this, "Sharing location", Toast.LENGTH_SHORT).show();

        // Get the current location
        Location currentLocation = getCurrentLocation();

        if (currentLocation != null) {
            // Create a Google Maps URL with the latitude and longitude
            String mapUrl = "http://maps.google.com/maps?q=" +
                    currentLocation.getLatitude() + "," + currentLocation.getLongitude();

            // Create an intent to send the location via SMS
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
            sendIntent.setData(Uri.parse("smsto:"));  // Set the data for the intent to specify it's an SMS
            sendIntent.putExtra("sms_body", "Check out my current location: " + mapUrl); // Add the SMS body, which includes the current location URL

            // Start the SMS intent
            startActivity(sendIntent);
        } else {
            Toast.makeText(this, "Unable to retrieve current location", Toast.LENGTH_SHORT).show();
        }


    }

    // Example method to get the current location (you should implement this according to your requirements)
    private Location getCurrentLocation() {
        // Use Android's location services to obtain the current location
        // This might involve using LocationManager or FusedLocationProviderClient
        // Here, we are using a dummy location for demonstration purposes
        Location location = new Location("dummy_provider");
        location.setLatitude(37.7749);  // Replace with the actual latitude
        location.setLongitude(-122.4194);  // Replace with the actual longitude
        return location;
    }



    private void startRecording() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            // Start audio recording
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4); // Change the output format
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC); // Change the audio encoder

            // Specify the output file path for audio using getExternalFilesDir()
            String audioFilePath = getExternalFilesDir(Environment.DIRECTORY_MUSIC) + "/audioRecording.aac";
            mediaRecorder.setOutputFile(audioFilePath);

            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
                isRecording = true;
                Toast.makeText(this, "Audio Recording started", Toast.LENGTH_SHORT).show();

                // Log the absolute path to the console
                String absolutePath = new File(audioFilePath).getAbsolutePath();
                Log.d("Recording", "Audio file path: " + absolutePath);

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Recording", "Error starting audio recording: " + e.getMessage());
                Toast.makeText(this, "Error starting audio recording: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void stopRecording() {
        if (isRecording) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
            Toast.makeText(this, "Recording stopped", Toast.LENGTH_SHORT).show();
        }
    }
}
