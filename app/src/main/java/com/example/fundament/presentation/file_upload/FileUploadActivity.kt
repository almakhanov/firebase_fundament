package com.example.fundament.presentation.file_upload

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.fundament.R
import com.example.fundament.base.Status
import com.example.fundament.extensions.alert
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_file_upload.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class FileUploadActivity : AppCompatActivity() {

    lateinit var viewModel: FileUploadViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_upload)
        viewModel = getViewModel()
        viewModel.statusMutableLiveData.observe(this, Observer {
            when(it){
                Status.SHOW_LOADING -> {
                    progress_bar.visibility = View.VISIBLE
                }
                Status.HIDE_LOADING -> {
                    progress_bar.visibility = View.GONE
                }
            }
        })

        viewModel.messageLiveData.observe(this, Observer {
            alert(message = it)
        })

        photoAccountBox.setOnClickListener {
            setAccountListener()
        }

        viewModel.downloadUriLiveData.observe(this, Observer {
            photoAccountImageView.setImageURI(it)
        })
    }

    private fun setAccountListener() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_DENIED
        ) {
            CropImage.activity().start(this)
        } else {
            requestCameraPermission()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            try {
                when (requestCode) {
                    CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                        val result = CropImage.getActivityResult(data)
                        viewModel.uploadFile(result.uri)
                    }
                    CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE -> {
                        val result = CropImage.getActivityResult(data)
                        Toast.makeText(this, "Ошибка ${result.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private fun requestCameraPermission() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) ||
            !ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) ||
            !ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)
        ) {
            ActivityCompat.requestPermissions(this, permissions, 120)
            return
        }
    }
}
