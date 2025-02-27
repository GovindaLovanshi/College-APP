package com.example.managementapp.admin.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.managementapp.R
import com.example.managementapp.itemview.NoticeItemView
import com.example.managementapp.viewModel.CollegeInfoViewModel
import com.example.managementapp.viewModel.NoticeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollegeInformationManage(navController: NavController) {

    val context = LocalContext.current

    val noticeModel: CollegeInfoViewModel = viewModel()

    val isUploaded by noticeModel._isPost.observeAsState(false)
//    val isDeleteted by noticeModel._isDeleted.observeAsState(false)
    val CollegeInfo by noticeModel.collegeInfo.observeAsState(null)


    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }


    var name by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    var address by remember {
        mutableStateOf("")
    }


    var link by remember {
        mutableStateOf("")
    }
    noticeModel.getCollegeInfo()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            imageUri = it
        }



//    LaunchedEffect(isDeleteted) {
//        if (isDeleteted) {
//            Toast.makeText(context, "Notice is Deleted", Toast.LENGTH_SHORT).show()
//        }
//    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Notice Manage", color = Color.Black)
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


            ElevatedCard(modifier = Modifier.padding(8.dp)) {
                OutlinedTextField(value = name, onValueChange = {
                    name = it
                }, placeholder = { Text(text = "College Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )

                OutlinedTextField(value = description, onValueChange = {
                    description = it
                }, placeholder = { Text(text = "Notice Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )

                OutlinedTextField(value = link, onValueChange = {
                    link = it
                }, placeholder = { Text(text = "Notice Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
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

                Row(modifier = Modifier) {
                    Button(
                        onClick = {

                            if (imageUri == null) {
                                Toast.makeText(
                                    context,
                                    "Please Provide Image",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (address == "" || link == "" || name == "" || description == "") {
                                Toast.makeText(
                                    context,
                                    "Please Provide All ",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                noticeModel.saveCollegeInfo(
                                    imageUri!!,
                                    name,
                                    address,
                                    address,
                                    link,
                                )
                            }
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(4.dp)
                    ) {
                        Text(text = "Update College")
                    }


                }
            }

        }


    }

}
