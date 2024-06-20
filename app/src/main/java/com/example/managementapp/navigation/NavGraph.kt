package com.example.managementapp.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.managementapp.FacultyDeatail
import com.example.managementapp.Screens.Aboutus
import com.example.managementapp.Screens.BottomNav
import com.example.managementapp.Screens.Faculty
import com.example.managementapp.Screens.Gallery
import com.example.managementapp.Screens.Home
import com.example.managementapp.admin.screens.AdminDashBoard
import com.example.managementapp.admin.screens.BannerManage
import com.example.managementapp.admin.screens.CollegeInformationManage
import com.example.managementapp.admin.screens.GalleryManage
import com.example.managementapp.admin.screens.ManageNotice
import com.example.managementapp.utils.ConstantItem.isAdmin

@Composable
fun NavGraph(navController:NavHostController) {



    NavHost(
        navController = navController,
        startDestination = if(isAdmin)Routes.AdminDashBoard.route else Routes.BottomNav.route
    ){
        composable(Routes.BottomNav.route){
            BottomNav(navController)
        }
        composable(Routes.Aboutus.route){
            Aboutus(navController)
        }
        composable(Routes.Faculty.route){
            Faculty(navController)
        }
        
        composable(Routes.Gallery.route){
            Gallery(navController)
        }
        
        composable(Routes.Home.route){
            Home(navController )
        }

        composable(Routes.AdminDashBoard.route){
            AdminDashBoard(navController = navController)
        }
        
        composable(Routes.BannerManage.route){
            BannerManage(navController = navController)
        }
        
        composable(Routes.CollegeInformationManage.route){
            CollegeInformationManage(navController = navController)
        }
        
        composable(Routes.FacultyManage.route){
            Faculty(navController = navController)
        }
        
        composable(Routes.GalleryManage.route){
            GalleryManage(navController = navController)
            
        }
        
        composable(Routes.ManageNotice.route){
            ManageNotice(navController = navController)
            
        }
        
        composable(Routes.FacultyDetails.route){
            val data = it.arguments?.getString("catName")
            FacultyDeatail(navController = navController,data!!)
        }
    }
}