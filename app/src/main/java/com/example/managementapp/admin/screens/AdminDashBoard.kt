package com.example.managementapp.admin.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.managementapp.model.DashBoardItems
import com.example.managementapp.navigation.Routes
import com.example.managementapp.ui.theme.TITLE_SIZE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashBoard(navController: NavController) {

    val listAdmin = listOf(
        DashBoardItems("Banner Manage", Routes.BannerManage.route),
        DashBoardItems("Manage Notice",Routes.ManageNotice.route),
        DashBoardItems("Gallery Mange", Routes.GalleryManage.route),
        DashBoardItems("College Information Manage", Routes.CollegeInformationManage.route),
        DashBoardItems("Faculty Manage", Routes.FacultyManage.route),
    )

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Admin DashBoard")
            })
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(items = listAdmin, itemContent = {


                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        navController.navigate(it.route)

                    }) {
                    Text(
                        text = it.title,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = TITLE_SIZE
                    )
                }
            })
        }

    }
}

    @Preview(showBackground = true)
    @Composable
    fun AdminDashBoardPreview() {
        AdminDashBoard(navController = rememberNavController())

    }
