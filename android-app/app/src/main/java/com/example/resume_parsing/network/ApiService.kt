package com.example.resume_parsing.network

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

data class UserSignUpRequest(
    val fullName: String,
    val email: String,
    val password: String,
    val gender: String = "male",   // Default value if not passed
    val role: String = "User",     // Default value if not passed
    val company: String? = null,
    val companyImage: String? = null
)
data class Job(
    val _id: String,
    val jobTitle: String,
    val jobDescription: String,
    val companyName: String,
    val responsibilities: String,
    val qualifications: String,
    val location: String,
    val jobtype: String,
    val noOfPosts: Int,
    val salary: Int,
    val isOpen: Boolean,
    val skills: List<String>?,
    val experience: String?,
    val postedBy: PostedBy?,
    val appliedBy: List<String>?,
    val createdAt: String?,
    val updatedAt: String?
)

data class PostedBy(
    val companyImage: String?,
    val _id: String
)

data class UpdateUserRequest(
    val fullName: String,
    val gender: String,
    val skills: List<String>?,
    val phoneNumber: String,
    val address: String,
    val education: String,
    val experience: String,
    val profileImage: String? = null,  //Can be null
    val email: String,
    val resume: String? = null,
    val user: UserInfo
)
data class UserInfo(val _id: String)
data class LoginRequest(val email: String, val password: String)

data class UserResponse(
    val _id: String,
    val fullName: String,
    val role: String,
    val gender: String,
    val email: String,
    val skills: List<String>?,
    val company: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val address: String?,
    val education: String?,
    val experience: String?,
    val phoneNumber: String?,
    val profileImage: String?,
    val resume: String?,
    val error: String?
)
data class SignedUploadResponse(
    val signedPreset: String,
    val timestamp: Long
)
data class CloudinaryUploadResponse(
    val public_id: String,
    val version: Int,
    val signature: String,
    val width: Int,
    val height: Int,
    val format: String,
    val resource_type: String,
    val created_at: String,
    val tags: List<String>,
    val bytes: Int,
    val type: String,
    val etag: String,
    val placeholder: Boolean,
    val url: String,
    val secure_url: String,
    val original_filename: String,
    val original_extension: String
)
data class PostJobRequest(
    val jobTitle: String,
    val jobDescription: String,
    val companyName: String,
    val responsibilities: String,
    val qualifications: String,
    val location: String,
    val salary: Int,
    val skills: List<String>?,
    val isOpen: Boolean? = true,
    val jobType: String,
    val noOfPosts: Int
)

data class PostJobResponse(
    val _id: String,
    val jobTitle: String,
    val jobDescription: String,
    val companyName: String,
    val responsibilities: String,
    val qualifications: String,
    val location: String,
    val salary: Int,
    val skills: List<String>?,
    val isOpen: Boolean,
    val postedBy: String,
    val jobType:String,
    val noOfPosts:Int,
    val createdAt: String,
    val updatedAt: String
)
data class JobResponse(
    val jobs: List<Job>,
    val totalPages: Int
)
data class Application(
    val _id: String,
    val resume: String,
    val jobId: String,
    val accepted: String,
    val appliedBy: UserResponse,
    val postedBy: String,
    val createdAt: String,
    val updatedAt: String
)
data class ApplicationResponse(
    val _id: String,
    val resume: String,
    val jobId: Job,
    val accepted: String,
    val appliedBy: UserResponse,
    val postedBy: String,
    val createdAt: String,
    val updatedAt: String
)
data class ApplicationCountResponse(
    val applications: Int,
    val employees: Int,
    val rejected: Int,
    val male: Int,
    val female: Int,
    val others: Int,
    val accepted:Int,
    val pending: Int,
)

data class JobTitleStat(
    val _id:String,
    val count:Int
)
data class GetApplicantsResponse(
    val accepted: String,
    val appliedBy: UserResponse,
    val createdAt: String,
    val jobId: String,
    val postedBy: String,
    val resume: String,
    val updatedAt: String,
    val __v: Int,
    val _id: String
)
data class GetApplicationDetail(val applicationId: String)
data class AcceptRejectRequest(val applicationId:String)
data class jobcountresponse(val openJobsCount:Int)
interface ApiService {
    @POST("/api/signup")  // Replace with your actual API endpoint
    suspend fun signUp(@Body userSignUpRequest: UserSignUpRequest): Response<Unit>
    @POST("/api/login")  // Replace with your server's login route
    fun loginUser(@Body loginRequest: LoginRequest): Call<UserResponse>
    @GET("api/login/")
    fun logout(): Call<ResponseBody>
    @GET("api/login/auto")
    suspend fun AutoLogin(): Response<UserResponse> // The token will automatically be sent in cookies
    @GET("api/jobs/")  // Replace with your actual endpoint
    suspend fun getAllJobs(
        @Query("page") page: Int = 1,
        @Query("jobTitle") jobTitle: String? = null,
        @Query("location") location: String? = null,
        @Query("experience") experience: String? = null,
        @Query("jobType") jobType: String? = null,
        @Query("minSalary") minSalary: String? = null,
        @Query("maxSalary") maxSalary: String? = null,
        @Query("sort") sort: String? = null,
        @Query("sortOrder") sortOrder: String? = null
    ): JobResponse
    @GET("/api/signed-upload/{folder}")  // Adjust endpoint if needed
    suspend fun getSignedUploadPreset(@retrofit2.http.Path("folder") folder: String): SignedUploadResponse
    @Multipart
    @POST("https://api.cloudinary.com/v1_1/{cloudName}/auto/upload")
    suspend fun uploadFile(
        @Path("cloudName") cloudName: String,
        @Part file: MultipartBody.Part,
        @Part("folder") folder: String,
        @Part("timestamp") timestamp: Long,
        @Part("api_key") apiKey: String,
        @Part("upload_preset") uploadPreset: String  // ✅ Use `upload_preset` instead of `signature`
    ): CloudinaryUploadResponse
    @PATCH("/api/updateUser") // Endpoint to update user data
    suspend fun updateUser(@Body updateUserRequest: UpdateUserRequest): Response<UserResponse>
    @POST("/api/jobs")  // Replace with your actual API endpoint
    suspend fun postJob(@Body postJobRequest: PostJobRequest): Response<PostJobResponse>
    @GET("/api/application/count")
    suspend fun getApplicationCount(@Query("user._id") userId:String ,@Query("user.role") userRole:String ): Response<ApplicationCountResponse>
    @GET("/api/jobs/")
    suspend fun getJobsPostedByHR(
        @Query("postedBy") postedBy:String
    ): JobResponse

    @GET("/api/application/applicants/")
    suspend fun getApplicants(
        @Query("postedBy") postedBy: String
    ):List<GetApplicantsResponse>

    @POST("/api/application/accept")
    suspend fun acceptApplication(@Body applicationId:AcceptRejectRequest): Response<ApplicationResponse>
    @POST("/api/application/reject")
    suspend fun rejectApplication(@Body applicationId:AcceptRejectRequest): Response<ApplicationResponse>
    @POST("api/application/details")
    suspend fun getSingleApplicationDetails(@Body applicationId: GetApplicationDetail):UserResponse
    @GET("/api/jobs/open")
    suspend fun JobOpenCounter():Response<jobcountresponse>

}