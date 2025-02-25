package com.example.resume_parsing.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.resume_parsing.network.ApiService
import com.example.resume_parsing.network.CloudinaryUploadResponse
import com.example.resume_parsing.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

object FileUploadUtil {
    suspend fun uploadFile(context: Context, fileUri: Uri, folder: String): CloudinaryUploadResponse? =
        withContext(Dispatchers.IO) {
            Log.d("FileUpload", "${fileUri.path} is being uploaded to $folder")

            val apiService = RetrofitClient.apiService
            val cloudName = "dmbqqvqcb" // Replace with your Cloudinary cloud name
            val apiKey = "253373599139292" // Your Cloudinary API key

            return@withContext try {
                // Step 1: Get signed upload preset from backend
                val uploadPresetResponse = apiService.getSignedUploadPreset(folder)
                val signedPreset = uploadPresetResponse.signedPreset
                val timestamp = uploadPresetResponse.timestamp

                Log.d("FileUpload", "Received signedPreset: $signedPreset, timestamp: $timestamp")

                // Step 2: Convert URI to File
                val file = FileUtils.uriToFile(fileUri, context) ?: return@withContext null
                Log.d("FileUpload", "Converted file: ${file.absolutePath}")

                // Step 3: Prepare file for upload
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull()) // Adjust media type if needed
                val multipartBody = MultipartBody.Part.createFormData("file", file.name, requestFile)

                // Step 4: Upload to Cloudinary (use `upload_preset` instead of `signature`)
                val uploadResponse = apiService.uploadFile(
                    cloudName = cloudName,
                    file = multipartBody,
                    folder = folder,
                    timestamp = timestamp,
                    apiKey = apiKey,
                    uploadPreset = signedPreset  // Use `upload_preset` instead of `signature`
                )

                Log.d("FileUpload", "Upload successful: $uploadResponse")
                uploadResponse
            } catch (e: IOException) {
                Log.e("FileUpload", "Upload failed: ${e.message}", e)
                null
            }
        }
}

object FileUtils {  //Create new Object FileUtils , to make it simple

    fun uriToFile(uri: Uri, context: Context): File? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                ?: return null  // Handle null input stream

            val fileName = getFileName(uri, context) ?: "temp_file"  // Get a suitable file name

            val file = File(context.cacheDir, fileName)  // Use the app's cache directory

            inputStream.use { input ->
                FileOutputStream(file).use { output ->
                    val buffer = ByteArray(4 * 1024) // 4KB buffer
                    var bytesRead: Int
                    if (input != null) {
                        while (input.read(buffer).also { bytesRead = it } != -1) {
                            output.write(buffer, 0, bytesRead)
                        }
                    }
                    output.flush()
                }
            }
            file  // Return the temporary file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getFileName(uri: Uri, context: Context): String? {
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val displayNameIndex = it.getColumnIndexOrThrow(android.provider.OpenableColumns.DISPLAY_NAME)
                if (displayNameIndex >= 0) {
                    return it.getString(displayNameIndex)
                }
            }
        }
        return null
    }
}