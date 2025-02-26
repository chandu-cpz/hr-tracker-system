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
            val cloudName = "dmbqqvqcb" // Your Cloudinary cloud name
            val apiKey = "253373599139292" // Your Cloudinary API key

            return@withContext try {
                // Step 1: Get signed upload preset from backend
                val uploadPresetResponse = apiService.getSignedUploadPreset(folder)
                val signature = uploadPresetResponse.signedPreset
                val timestamp = uploadPresetResponse.timestamp

                Log.d("FileUpload", "Received signature: $signature, timestamp: $timestamp")
                
                // Step 2: Convert URI to File
                val file = FileUtils.uriToFile(fileUri, context) ?: return@withContext null
                Log.d("FileUpload", "Converted file: ${file.absolutePath}, name: ${file.name}")

                // Step 3: Prepare file for upload - IMPORTANT: paramName must be "file"
                val requestFile = file.asRequestBody("*/*".toMediaTypeOrNull()) // Accept any file type
                val multipartBody = MultipartBody.Part.createFormData("file", file.name, requestFile)

                // Create request body for each parameter to ensure proper formatting
                val apiKeyPart = MultipartBody.Part.createFormData("api_key", apiKey)
                val signaturePart = MultipartBody.Part.createFormData("signature", signature)
                val timestampPart = MultipartBody.Part.createFormData("timestamp", timestamp.toString())
                val folderPart = MultipartBody.Part.createFormData("folder", folder)

                Log.d("FileUpload", "Uploading with reformatted parameters")

                // Step 4: Upload to Cloudinary with required parameters as MultipartBody.Part
                val uploadResponse = apiService.uploadFileWithParts(
                    cloudName = cloudName,
                    file = multipartBody,
                    signature = signaturePart,
                    timestamp = timestampPart, 
                    apiKey = apiKeyPart,
                    folder = folderPart
                )

                Log.d("FileUpload", "Upload successful - Secure URL: ${uploadResponse.secure_url}")
                uploadResponse
            } catch (e: Exception) {
                Log.e("FileUpload", "Upload failed: ${e.message}", e)
                Log.e("FileUpload", "Stack trace:", e)
                null
            }
        }
}

object FileUtils {  // File utility methods
    fun uriToFile(uri: Uri, context: Context): File? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                ?: return null

            val fileName = getFileName(uri, context) ?: "temp_file"
            val file = File(context.cacheDir, fileName)

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
            file
        } catch (e: Exception) {
            Log.e("FileUtils", "Error converting URI to file: ${e.message}", e)
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