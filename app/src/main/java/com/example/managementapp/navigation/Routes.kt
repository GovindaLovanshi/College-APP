package com.example.managementapp.navigation

sealed class Routes(val route:String){

        object Home:Routes("Home")

       object Gallery:Routes("Gallery")

       object Faculty:Routes("Faculty")

        object Aboutus:Routes("Aboutus")

      object BottomNav:Routes("BottomNav")

    object AdminDashBoard :Routes("AdminDashBoard")

    object BannerManage:Routes("BannerMange")

    object CollegeInformationManage:Routes("CollegeInformationManage")

    object FacultyManage:Routes("FacultyManage")

    object GalleryManage:Routes("GalleryManage")

    object ManageNotice :Routes("ManageNotice")
    object FacultyDetails:Routes("faculty_deatils/{catName}")
}