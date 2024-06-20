package com.example.managementapp.model

import androidx.compose.ui.graphics.vector.ImageVector

data class DataItem(val title:String, val icon: ImageVector)

data class BottomNavItem(val title:String,val icon:Int,val route:String)