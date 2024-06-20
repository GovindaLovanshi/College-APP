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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.managementapp.R
import com.example.managementapp.itemview.FacultyItemView
import com.example.managementapp.itemview.GalleryItemView
import com.example.managementapp.navigation.Routes
import com.example.managementapp.viewModel.FacultyViewModel
import com.example.managementapp.viewModel.GalleryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryManage(navController: NavController) {


    val context = LocalContext.current

    val galleryModel: GalleryViewModel = viewModel()

    val isUploaded by galleryModel._isPost.observeAsState(false)
    val isDeleteted by galleryModel._isDeleted.observeAsState(false)
    val gallertList by galleryModel.galleryList.observeAsState(null)
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


    var isImage by remember {
        mutableStateOf(false)
    }

    var position by remember {
        mutableStateOf("")
    }
    galleryModel.getGallery(category)

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            imageUri = it
        }




    LaunchedEffect(isUploaded) {
        if (isUploaded) {
            Toast.makeText(context, "Data  is Uploaded", Toast.LENGTH_SHORT).show()
            imageUri = null
            isFaculty = false
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

                    Image(
                        painter = if (imageUri == null) painterResource(id = R.drawable.placeholderimage) else rememberAsyncImagePainter(
                            model = imageUri
                        ),
                        contentDescription = "banner_image", modifier = Modifier
                            .height(220.dp)
                            .fillMaxWidth()
                            .clickable {
                                launcher.launch("image/*")
                            },
                        contentScale = ContentScale.Crop
                    )

                    Row(modifier = Modifier.padding(8.dp)) {
                        Button(
                            onClick = {

                                if (category == null && imageUri == null) {
                                    Toast.makeText(
                                        context,
                                        "Please Provide Ca;; ",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    galleryModel.saveGalleryImage(imageUri!!,category,true)
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
                                isImage = false
                                isCategory = false

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


            if (isImage) {

//
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


                    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)){
                        OutlinedTextField(
                            value = category, onValueChange = {
                                category = it
                            },
                            readOnly = true, placeholder = { Text(text = "Select Your Department") },
                            label = { Text(text = "Gallery ") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),

                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = Expended)
                            }
                        )
                    }
                }

                    Spacer(modifier = Modifier.height(5.dp))
                    Image(
                        painter = if (imageUri == null) painterResource(id = R.drawable.placeholderimage) else rememberAsyncImagePainter(
                            model = imageUri
                        ),
                        contentDescription = "banner_image", modifier = Modifier
                            .height(220.dp)
                            .fillMaxWidth()
                            .clickable {
                                launcher.launch("image/*")
                            },
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(5.dp))





                }



                DropdownMenu(expanded = Expended, onDismissRequest = { Expended = false }) {

                    if (gallertList != null && gallertList!!.isNotEmpty()) {
                        option.clear()
                        for (data in gallertList!!) {
                            option.add(data.category!!)
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
                            } else if (category == "") {
                                Toast.makeText(
                                    context,
                                    "Please Provide all category",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                galleryModel.saveGalleryImage(
                                    imageUri!!,
                                    category,
                                    true
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

    LazyColumn {
        items(gallertList ?: emptyList()) {
            GalleryItemView(it, delete = { docId ->
                galleryModel.deleteGallery(docId)
            }, deleteImage = {cat,imageUrl->
                galleryModel.deleteImage(cat,imageUrl)
            })
        }
    }

}

