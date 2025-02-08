package com.example.resume_parsing.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.resume_parsing.network.UserResponse

@OptIn(ExperimentalLayoutApi::class)
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun EditProfileDialog(
    user: UserResponse?,
    onDismiss: () -> Unit,
    onSave: (UserResponse) -> Unit
) {
    val skillsList = listOf("HTML", "CSS", "JavaScript", "Java", "Python", "C++", "C#", "PHP", "Ruby", "Swift", "TypeScript",
        "Node.js", "React", "Angular", "Vue.js", "Express.js", "Django", "Flask", "Spring Framework", "Bootstrap",
        "Sass", "Less", "jQuery", "RESTful API", "GraphQL", "Git", "GitHub", "GitLab", "Bitbucket", "JIRA",
        "Agile Development", "Scrum", "Kanban", "Continuous Integration", "Continuous Deployment", "DevOps",
        "Docker", "Kubernetes", "Jenkins", "Ansible", "Chef", "Puppet", "Amazon Web Services (AWS)", "Microsoft Azure",
        "Google Cloud Platform (GCP)", "Firebase", "SQL", "MySQL", "PostgreSQL", "MongoDB", "Redis", "NoSQL",
        "Firebase Firestore", "Firebase Realtime Database", "Elasticsearch", "Blockchain", "Smart Contracts",
        "Solidity", "Machine Learning", "Deep Learning", "Natural Language Processing (NLP)", "Computer Vision",
        "TensorFlow", "PyTorch", "Scikit-learn", "Data Science", "Big Data", "Hadoop", "Spark", "Apache Kafka",
        "RESTful API Design", "Microservices Architecture", "WebSocket", "OAuth", "JWT (JSON Web Tokens)", "OAuth 2.0",
        "JSON", "XML", "Responsive Web Design", "Cross-browser Compatibility", "UI/UX Design", "Adobe Creative Suite",
        "Figma", "Sketch", "Zeplin", "Microsoft Office", "Project Management", "Leadership", "Communication",
        "Problem Solving", "Critical Thinking", "Agile Methodologies", "Team Collaboration", "Version Control",
        "Unit Testing", "Integration Testing", "Automation Testing", "Containerization", "Infrastructure as Code (IaC)",
        "Security Best Practices")

    var fullName by remember { mutableStateOf(user?.fullName ?: "") }
    var address by remember { mutableStateOf(user?.address ?: "") }
    var email by remember { mutableStateOf(user?.email ?: "") }
    var education by remember { mutableStateOf(user?.education ?: "") }
    var gender by remember { mutableStateOf(user?.gender ?: "Male") }
    var experience by remember { mutableStateOf(user?.experience ?: "0") }
    var phoneNumber by remember { mutableStateOf(user?.phoneNumber ?: "") }

    var selectedSkills by remember { mutableStateOf(mutableListOf<String>()) }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var showSkillDropdown by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f) // Popup takes up 90% of the screen height
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color.Gray),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Upload Image")
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(value = fullName, onValueChange = { fullName = it }, label = { Text("Full Name") })
                    OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Address") })
                    OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
                    OutlinedTextField(value = education, onValueChange = { education = it }, label = { Text("Education") })

                    // Gender Selection
                    Text("Gender:")
                    Column {
                        listOf("Male", "Female", "Other").forEach {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(selected = gender == it, onClick = { gender = it })
                                Text(it)
                            }
                        }
                    }

                    // Experience Stepper
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Experience:")
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = { if (experience.toInt() > 0) experience = (experience.toInt() - 1).toString() }) {
                            Icon(Icons.Default.Remove, contentDescription = "Decrease")
                        }
                        Text(experience, fontSize = 18.sp)
                        IconButton(onClick = { experience = (experience.toInt() + 1).toString() }) {
                            Icon(Icons.Default.Add, contentDescription = "Increase")
                        }
                    }

                    OutlinedTextField(value = phoneNumber, onValueChange = { phoneNumber = it }, label = { Text("Phone Number") })

                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Skills Selection with Searchable Dropdown
                item {
                    Box {
                        OutlinedTextField(
                            value = searchQuery.text,
                            onValueChange = { searchQuery = TextFieldValue(it) },
                            label = { Text("Search Skills") },
                            trailingIcon = {
                                IconButton(onClick = { showSkillDropdown = !showSkillDropdown }) {
                                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )

                        DropdownMenu(expanded = showSkillDropdown, onDismissRequest = { showSkillDropdown = false }) {
                            skillsList.filter { it.contains(searchQuery.text, ignoreCase = true) }
                                .forEach { skill ->
                                    DropdownMenuItem(
                                        text = { Text(skill) },
                                        onClick = {
                                            if (!selectedSkills.contains(skill)) {
                                                selectedSkills = selectedSkills.toMutableList().apply { add(skill) }
                                            }
                                            showSkillDropdown = false
                                        }
                                    )
                                }
                        }
                    }
                }

                // Display Selected Skills as Chips
                item {
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalArrangement = Arrangement.Top,
                        maxItemsInEachRow = 3
                    ) {
                        selectedSkills.forEach { skill ->
                            FilterChip(
                                selected = true,
                                onClick = { selectedSkills = selectedSkills.toMutableList().apply { remove(skill) } },
                                label = { Text(skill) },
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Save Button
                item {
                    Button(
                        onClick = {
//                            onSave(
//                                UserResponse(
//                                    fullName = fullName,
//                                    email = email,
//                                    address = address,
//                                    education = education,
//                                    gender = gender,
//                                    experience = experience,
//                                    phoneNumber = phoneNumber,
//                                    skills = selectedSkills
//                                )
//                            )
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFFFF7043)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Save Profile", color = Color.White)
                    }
                }
            }
        }
    }
}