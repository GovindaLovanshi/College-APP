package com.example.managementapp.admin.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.managementapp.R
import com.example.managementapp.itemview.FacultyItemView
import com.example.managementapp.navigation.Routes
import com.example.managementapp.viewModel.FacultyViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacultyManage(navController: NavController) {


    val context = LocalContext.current

    val facultyModel: FacultyViewModel = viewModel()

    val isUploaded by facultyModel._isPost.observeAsState(false)
    val isDeleteted by facultyModel._isDeleted.observeAsState(false)
    val categoryList by facultyModel.categoryList.observeAsState(null)
    val option = arrayListOf<String>()


    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var Expended by remember {
        mutableStateOf(false)
    }

    var category by remember {
        mutableStateOf("")
    }

    var isFaculty by remember {
        mutableStateOf(false)
    }

    var isCategory by remember {
        mutableStateOf(false)
    }



    var name by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var isTeacher by remember {
        mutableStateOf(false)
    }

    var position by remember {
        mutableStateOf("")
    }
    facultyModel.getFaculty(category)

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            imageUri = it
        }




    LaunchedEffect(isUploaded) {
        if (isUploaded) {
            Toast.makeText(context, "Data  is Uploaded", Toast.LENGTH_SHORT).show()
            imageUri = null
            isFaculty = false
            isTeacher = false
            email = ""
            name = ""
            position = ""
            category = ""
        }

    }

    LaunchedEffect(isDeleteted) {
        if (isDeleteted) {
            Toast.makeText(context, "Data is Deleted", Toast.LENGTH_SHORT).show()
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Faculty Manage", color = Color.Black)
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Color.Green),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {

                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black
                        )

                    }
                },

                )


        },

        ) { padding ->


        Column(modifier = Modifier.padding(padding)) {

            if (isCategory) {
                ElevatedCard(modifier = Modifier.padding(8.dp)) {


                    OutlinedTextField(
                        value = category, onValueChange = {
                            category = it
                        },
                        placeholder = { Text(text = "Position") }, modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                    )

                    Row(modifier = Modifier.padding(8.dp)) {
                        Button(
                            onClick = {

                                if (category == null) {
                                    Toast.makeText(
                                        context,
                                        "Please Provide Category",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    facultyModel.uploadCategory(category)
                                }
                            }, modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(4.dp)
                        ) {
                            Text(text = "Add Category")
                        }

                        OutlinedButton(
                            onClick = {
                                imageUri = null
                                isCategory = false
                                isTeacher = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(4.dp)
                        ) {
                            Text(text = "Cancel")
                        }
                    }

                }
            }


            if (isTeacher) {


//                Row (modifier = Modifier.padding(8.dp)){
//                    Card(modifier = Modifier
//                        .weight(1f)
//                        .padding(4.dp)
//                        .clickable {
//                            isCategory = true
//                            isTeacher = false
//                        }) {
//                        Text(
//                            text = "Add catefory",
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 18.sp,
//                            modifier = Modifier
//                                .padding(8.dp)
//                                .fillMaxWidth(), textAlign = TextAlign.Center
//                        )
//
//                    }
//
//                    Card(modifier = Modifier
//                        .weight(1f)
//                        .padding(4.dp)
//                        .clickable {
//                            isCategory = false
//                            isTeacher = true
//                        }) {
//                        Text(
//                            text = "Add Techer",
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 18.sp,
//                            modifier = Modifier
//                                .padding(8.dp)
//                                .fillMaxWidth(),
//                            textAlign = TextAlign.Center
//                        )
//                    }
//                }
                ElevatedCard(modifier = Modifier.padding(8.dp)) {

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Spacer(modifier = Modifier.height(5.dp))
                        Image(
                            painter = if (imageUri == null) painterResource(id = R.drawable.placeholderimage) else rememberAsyncImagePainter(
                                model = imageUri
                            ),
                            contentDescription = "banner_image", modifier = Modifier
                                .height(120.dp)
                                .width(120.dp)
                                .clip(CircleShape)
                                .clickable {
                                    launcher.launch("image/*")
                                },
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(5.dp))
                        OutlinedTextField(value = name, onValueChange = {
                            name = it
                        }, placeholder = { Text(text = "Name ") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        )

                        OutlinedTextField(value = email, onValueChange = {
                            email = it
                        }, placeholder = { Text(text = "email ") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        )

                        OutlinedTextField(
                            value = position, onValueChange = {
                                position = it
                            }, placeholder = { Text(text = "Teacher Position") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        )

                        Box(modifier = Modifier.wrapContentSize(Alignment.TopStart))

                    }

                    OutlinedTextField(
                        value = category, onValueChange = {
                            category = it
                        },
                        readOnly = true, placeholder = { Text(text = "Select Your Department") },
                        label = { Text(text = "Department ") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),

                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = Expended)
                        }
                    )

                    DropdownMenu(expanded = Expended, onDismissRequest = { Expended = false }) {

                        if (categoryList != null && categoryList!!.isNotEmpty()) {
                            option.clear()
                            for (data in categoryList!!) {
                                option.add(data.toString())
                            }

                            option.forEach { selectoption ->
                                DropdownMenuItem(
                                    text = { Text(text = "Selection Optiopn") },
                                    onClick = {
                                        category = selectoption
                                        Expended = false
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }


                        }

                        Spacer(modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                Expended = !Expended
                            })

                    }
                    Row(modifier = Modifier) {
                        Button(
                            onClick = {

                                if (imageUri == null) {
                                    Toast.makeText(
                                        context,
                                        "Please Provide Image",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (name == "" || email == "" || position == "" || category == "") {
                                    Toast.makeText(
                                        context,
                                        "Please Provide all feild",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    facultyModel.saveFaculty(
                                        imageUri!!,
                                        name,
                                        email,
                                        position,
                                        category,
                                    )
                                }
                            }, modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(4.dp)
                        ) {
                            Text(text = "Add Teacher")
                        }

                        OutlinedButton(
                            onClick = {
                                imageUri = null
                                isCategory = false
                                isTeacher = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(4.dp)
                        ) {
                            Text(text = "Cancel")
                        }
                    }
                }
            }
        }

    }

    LazyColumn {
        items(categoryList ?: emptyList()) {
            FacultyItemView(it, delete = { docId ->
                facultyModel.deleteFaculty(docId,category,imageUri)
            }) { categoryName ->
                val routes = Routes.FacultyDetails.route.replace("{catName}", categoryName)

                navController.navigate(routes)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FacultyPreview() {

}

