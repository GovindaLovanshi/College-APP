package com.example.managementapp.admin.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.managementapp.itemview.BannerViewItem
import com.example.managementapp.viewModel.BannerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BannerManage(navController: NavController) {

    val context = LocalContext.current

    val bannerViewModel: BannerViewModel = viewModel()

    val isUploaded by bannerViewModel._isPost.observeAsState(false)
    val isDeleted by bannerViewModel._isDeleted.observeAsState(false)
    val bannerList by bannerViewModel.bannerList.observeAsState(null)
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    bannerViewModel.getBanner()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            imageUri = it
        }

    LaunchedEffect(isUploaded) {
        if (isUploaded) {
            Toast.makeText(context, "Image is Uploaded", Toast.LENGTH_SHORT).show()
            imageUri = null
        }
    }

    LaunchedEffect(isDeleted) {
        if (isDeleted) {
            Toast.makeText(context, "Image is Deleted", Toast.LENGTH_SHORT).show()
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Banner Manage", color = Color.Black)
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
        floatingActionButton = {
            FloatingActionButton(onClick = {
                launcher.launch("image/*")
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ) { padding ->


        Column(modifier = Modifier.padding(padding)) {


            if (imageUri != null) {
                ElevatedCard(modifier = Modifier.padding(8.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter(model = imageUri),
                        contentDescription = "banner_image", modifier = Modifier
                            .height(220.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )

                    Row(modifier = Modifier) {
                        Button(
                            onClick = {
                                bannerViewModel.saveImage(imageUri!!)
                            }, modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(4.dp)
                        ) {
                            Text(text = "Add Image")
                        }

                        OutlinedButton(
                            onClick = { imageUri = null },
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

            LazyColumn {
                items(bannerList ?: emptyList()) {
                    BannerViewItem(bannerModel = it, delete = { docId ->
                        bannerViewModel.deleteBanner(docId)
                    })
                }
            }
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun BannerManageView() {
    BannerManage(navController = rememberNavController())
}
