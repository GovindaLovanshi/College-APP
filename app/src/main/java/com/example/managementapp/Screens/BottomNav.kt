//package com.example.managementapp.Screens
//
//
//
//import android.widget.Toast
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Build
//import androidx.compose.material.icons.filled.Call
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.rounded.Menu
//import androidx.compose.material3.BottomAppBar
//import androidx.compose.material3.Divider
//import androidx.compose.material3.DrawerValue
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.ModalDrawerSheet
//import androidx.compose.material3.ModalNavigationDrawer
//import androidx.compose.material3.NavigationBarItem
//import androidx.compose.material3.NavigationDrawerItem
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.rememberDrawerState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import androidx.navigation.NavGraph.Companion.findStartDestination
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import com.example.managementapp.R
//import com.example.managementapp.model.BottomNavItem
//import com.example.managementapp.model.DataItem
//import com.example.managementapp.navigation.Routes
//import kotlinx.coroutines.launch
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun BottomNav(navController: NavController){
//
//
//    val navController1 = rememberNavController()
//    val scope = rememberCoroutineScope()
//    val drawleState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    val context = LocalContext.current
//
//    val SlectedItemIndex  by rememberSaveable{
//        mutableStateOf(0)
//    }
//
//    val list = listOf(
//        DataItem(
//            "WebSite",
//            Icons.Default.Add
//        ),
//        DataItem(
//            "Notes",
//            Icons.Default.Build,
//        ),
//        DataItem(
//            "Notes",
//            Icons.Default.Home
//        ),
//        DataItem(
//            "contact us",
//            Icons.Default.Call
//        )
//    )
//
//    ModalNavigationDrawer(drawerState = drawleState,
//        drawerContent = {
//            ModalDrawerSheet {
//            Image(painter = painterResource(id = R.drawable), contentDescription = null, modifier = Modifier.height(220.dp),
//                contentScale = ContentScale.Crop)
//
//                Divider()
//                Text(text = "")
//
//                list.forEachIndexed(index, items ->
//                 NavigationDrawerItem(label = { Text(text = items.title)}, selected =index == selectedItemIndex , onClick = {
//                     Toast.makeText(context,items.title,Toast.LENGTH_SHORT).show()
//
//                     scope.launch{
//                         drawleState.close()
//                     }
//                 },
//                     icon = {
//                         Icon(painter = painterResource(id = items.icon), contentDescription =null,
//                             modifier = Modifier.size(24.dp))
//                     }
//                     ))
//        }},
//        content = {
//            Scaffold (
//                topBar ={
//                    TopAppBar(title = { Text(text = "Apna college")},
//                        navigationIcon = {
//                            IconButton(onClick = {scope.launch { drawleState.open() }}) {
//                                Icon(imageVector = Icons.Rounded.Menu, contentDescription = null)
//
//                            }
//                        })
//                },
//
//                bottomBar = {
//                    MyBottomNav(navController = navController)
//                }
//            ){ padding ->
//
//                NavHost(navController = navController1, startDestination =Routes.Home.route,
//                    modifier = Modifier.padding(padding)){
//                    composable(route = Routes.Home.route){
//                        Home(navController = navController)
//                    }
//
//                    composable(route = Routes.Gallery.route){
//                        Gallery(navController = navController)
//                    }
//
//                    composable(route = Routes.Faculty.route){
//                        Faculty(navController = navController)
//                    }
//
//                    composable(route = Routes.Aboutus.route){
//                        Aboutus(navController = navController)
//                    }
//                }
//
//            }
//        })
//
//
//}
//@Composable
//fun MyBottomNav(navController: NavController){
//
//    val backStackEntery = navController.currentBackStackEntryAsState()
//
//    val list = listOf(
//        BottomNavItem(
//            "Home",
//            Routes.Home.route,
//            R.drawable.
//        ),
//        BottomNavItem(
//            "Gallery",
//            Routes.Gallery.route,
//            R.drawable.
//        ),
//        BottomNavItem(
//            "Faculty",
//            Routes.Faculty.route,
//            R.drawable
//        ),
//        BottomNavItem(
//            "About us",
//            Routes.Aboutus.route,
//            R.drawable
//        )
//
//    )
//
//    BottomAppBar {
//        list.forEach {
//            val currentRoute = it.route
//
//                val otherRoute =
//                    try{backStackEntery.value!!.destination.route
//            }catch (e:Exception){
//                Routes.Home.route
//            }
//
//            val  selected = currentRoute == otherRoute
//
//            NavigationBarItem(selected = selected, onClick = { navController.navigate(it.route){
//                popUpTo(navController.graph.findStartDestination().id){
//                    saveState = true
//                }
//                launchSingleTop =true
//            } }, icon = {
//                Icon(painter = painterResource(id = it.icon), contentDescription =it.title, modifier = Modifier.size(24.dp) )
//            })
//        }
//    }
//
//}