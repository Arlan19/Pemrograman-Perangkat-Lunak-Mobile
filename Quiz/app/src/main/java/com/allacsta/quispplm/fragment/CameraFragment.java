package com.allacsta.quispplm.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.allacsta.quispplm.R;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class CameraFragment extends Fragment {

    private PreviewView previewView;
    private Camera camera;
    private ImageCapture imageCapture;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inisialisasi preview view
        previewView = view.findViewById(R.id.previewView);

        // Inisialisasi kamera
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                // Konfigurasi preview
                Preview preview = new Preview.Builder().build();
                Preview.SurfaceProvider surfaceProvider = previewView.getSurfaceProvider();

                // Konfigurasi image capture
                imageCapture = new ImageCapture.Builder().build();

                // Pilih kamera belakang (back camera)
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();

                // Buka kamera
                cameraProvider.unbindAll();
                camera = cameraProvider.bindToLifecycle(
                        getViewLifecycleOwner(),
                        cameraSelector,
                        preview,
                        imageCapture
                );
            } catch (ExecutionException | InterruptedException e) {
                // handle exception
            }
        }, ContextCompat.getMainExecutor(requireContext()));

        ImageButton captureButton = view.findViewById(R.id.captureButton);
        captureButton.setOnClickListener(v -> {
            // Ambil gambar
            ImageCapture imageCapture = this.imageCapture;
            if (imageCapture == null) return;

            // Membuat file untuk menyimpan gambar
            File photoFile = new File(requireActivity().getExternalMediaDirs()[0],
                    System.currentTimeMillis() + ".jpg");

            // Konfigurasi output image
            ImageCapture.OutputFileOptions outputOptions =
                    new ImageCapture.OutputFileOptions.Builder(photoFile).build();

            // Ambil gambar dengan output file yang telah dikonfigurasi
            imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(requireContext()),
                    new ImageCapture.OnImageSavedCallback() {
                        @Override
                        public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                            // Tampilkan pesan jika berhasil mengambil gambar
                            Toast.makeText(requireContext(), "Gambar berhasil disimpan di " + photoFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(@NonNull ImageCaptureException exception) {
                            // Tampilkan pesan jika terjadi kesalahan saat mengambil gambar
                            Toast.makeText(requireContext(), "Terjadi kesalahan saat mengambil gambar", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

    }


}