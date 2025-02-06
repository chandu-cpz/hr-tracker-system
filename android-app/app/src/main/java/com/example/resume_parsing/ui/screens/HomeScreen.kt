
package com.example.resume_parsing.ui.screens
//
//import android.widget.Toast
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import coil.compose.AsyncImage
//import com.example.resume_parsing.App.Companion.context
//import com.example.resume_parsing.network.Job
//import com.example.resume_parsing.network.RetrofitClient
//import com.example.resume_parsing.utils.PreferencesHelper
//import com.example.resume_parsing.viewmodel.JobViewModel
//import kotlinx.coroutines.launch
//import okhttp3.ResponseBody
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//@Composable
//fun HomeScreen(navController: NavHostController) {
//    val drawerState = rememberDrawerState(DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//    val jobViewModel: JobViewModel = viewModel()
//    val jobs by jobViewModel.jobs
//    var selectedJob by remember { mutableStateOf<Job?>(null) }
//
//    ModalNavigationDrawer(
//        drawerState = drawerState,
//        drawerContent = { DrawerContent(navController) },
//        content = {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.Black)
//            ) {
//                IconButton(
//                    onClick = {
//                        scope.launch {
//                            if (drawerState.isClosed) drawerState.open() else drawerState.close()
//                        }
//                    },
//                    modifier = Modifier
//                        .align(Alignment.TopStart)
//                        .padding(top = 80.dp)
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Menu,
//                        contentDescription = "Open Drawer",
//                        tint = Color.Cyan
//                    )
//                }
//
//                Column(
//                    modifier = Modifier.fillMaxSize(),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(
//                        text = "Job Offers",
//                        fontSize = 24.sp,
//                        color = Color.White,
//                        modifier = Modifier.padding(top = 80.dp)
//                    )
//
//                    LazyVerticalGrid(
//                        columns = GridCells.Fixed(2),
//                        contentPadding = PaddingValues(16.dp),
//                        modifier = Modifier.padding(top = 20.dp)
//                    ) {
//                        items(jobs) { job ->
//                            JobCard(job) { selectedJob = job }
//                        }
//                    }
//                }
//
//                selectedJob?.let { job ->
//                    JobDetailsDialog(job) { selectedJob = null }
//                }
//            }
//        }
//    )
//}
//
//@Composable
//fun DrawerContent(navController: NavHostController) {
//    Column(
//        modifier = Modifier
//            .fillMaxHeight()
//            .background(Color(0xFF2C3E50))
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Icon(
//            imageVector = Icons.Default.Person,
//            contentDescription = "Profile Picture",
//            modifier = Modifier.size(80.dp),
//            tint = Color.Cyan
//        )
//        Spacer(modifier = Modifier.height(10.dp))
//        Text(text = "User Name", color = Color.White, fontSize = 20.sp)
//        Button(
//            onClick = {
//                RetrofitClient.apiService.logout().enqueue(object : Callback<ResponseBody> {
//                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                        if (response.isSuccessful) {
//                            PreferencesHelper.clearToken(context)
//                            Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
//                            navController.navigate("login")
//                        } else {
//                            Toast.makeText(context, "Error logging out: ${response.message()}", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//
//                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                        Toast.makeText(context, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
//                    }
//                })
//            },
//            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
//            colors = ButtonDefaults.buttonColors(Color.Cyan)
//        ) {
//            Text("Log Out", color = Color.Black)
//        }
//    }
//}
//
//@Composable
//fun JobCard(job: Job, onClick: () -> Unit) {
//    Box(
//        modifier = Modifier
//            .padding(8.dp)
//            .fillMaxWidth()
//            .background(Color.White, shape = RoundedCornerShape(8.dp))
//            .clickable { onClick() },
//        contentAlignment = Alignment.Center
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            AsyncImage(
//                model = job.companyImage,
//                contentDescription = "Company Background",
//                modifier = Modifier.fillMaxSize()
//            )
//            Text(text = job.jobTitle, fontSize = 16.sp, color = Color.Black)
//            Text(text = job.companyName, fontSize = 14.sp, color = Color.Gray)
//        }
//    }
//}
//
//@Composable
//fun JobDetailsDialog(job: Job, onDismiss: () -> Unit) {
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        confirmButton = {
//            Button(onClick = onDismiss) {
//                Text("Close")
//            }
//        },
//        title = { Text(job.jobTitle) },
//        text = {
//            Column {
//                Text(text = "Company: ${job.companyName}")
//                Text(text = "Location: ${job.location}")
//                Text(text = "Salary: ₹${job.salary}")
//            }
//        }
//    )
//}
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.resume_parsing.App.Companion.context
import com.example.resume_parsing.network.Job
import com.example.resume_parsing.network.RetrofitClient
import com.example.resume_parsing.utils.PreferencesHelper
import com.example.resume_parsing.viewmodel.JobViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
@Composable
fun HomeScreen(navController: NavHostController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val jobViewModel: JobViewModel = viewModel()
    val jobs by jobViewModel.jobs
    var selectedJob by remember { mutableStateOf<Job?>(null) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerContent(navController) },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                // Drawer Icon
                IconButton(
                    onClick = {
                        scope.launch {
                            if (drawerState.isClosed) drawerState.open() else drawerState.close()
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 80.dp, start = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Open Drawer",
                        tint = Color.Cyan
                    )
                }

                // Title Text
                Text(
                    text = "Job Offers",
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 80.dp)
                )

                // Job Grid Layout
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.padding(top = 120.dp)
                ) {
                    items(jobs) { job ->
                        JobCard(job) { selectedJob = job }
                    }
                }

                // Job Details Popup
                selectedJob?.let { job ->
                    JobDetailsPopup(job) { selectedJob = null }
                }
            }
        }
    )
}

@Composable
fun DrawerContent(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color(0xFF2C3E50))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Profile Picture",
            modifier = Modifier.size(80.dp),
            tint = Color.Cyan
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "User Name", color = Color.White, fontSize = 20.sp)

        Button(
            onClick = {
                RetrofitClient.apiService.logout().enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            PreferencesHelper.clearToken(context)
                            Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
                            navController.navigate("login")
                        } else {
                            Toast.makeText(context, "Error logging out: ${response.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(context, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(Color.Cyan)
        ) {
            Text("Log Out", color = Color.Black)
        }
    }
}

@Composable
fun JobCard(job: Job, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Company Image (Logo)
            job.postedBy?.companyImage?.let { companyImage ->
                AsyncImage(
                    model = companyImage,
                    contentDescription = "Company Logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(bottom = 8.dp)
                )
            }

            // Job Title
            Text(text = job.jobTitle, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)

            // Company Name
            Text(text = job.companyName, fontSize = 14.sp, color = Color.Gray)

            // Job Role (Location)
            Text(text = "Location: ${job.location}", fontSize = 12.sp, color = Color.Gray)

            // Skills (display each skill in a box)
            Row(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                job.skills.take(3).forEach { skill ->
                    Text(
                        text = skill,
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .background(Color(0xFFE3F2FD), shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun JobDetailsPopup(job: Job, onClose: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxSize(0.9f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = job.jobTitle, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(text = "Company: ${job.companyName}", fontSize = 16.sp)
                Text(text = "Location: ${job.location}", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Skills Required:", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                job.skills.forEach { skill ->
                    Text(text = skill, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onClose) {
                    Text("Close")
                }
            }
        }
    }
}
