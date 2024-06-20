package com.example.managementapp.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.managementapp.model.CollegeInfoModel
import com.example.managementapp.utils.ConstantItem.COLLEGEINFO
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID

class CollegeInfoViewModel : ViewModel() {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()

    private val collegeInfoRef = firestore.collection(COLLEGEINFO)
    private val storageRef: StorageReference = storage.reference

    val _isPost = MutableLiveData<Boolean>()
    val isPost: LiveData<Boolean> = _isPost

    private val _collegeInfo = MutableLiveData<CollegeInfoModel?>()
    val collegeInfo: MutableLiveData<CollegeInfoModel?> = _collegeInfo

    fun saveCollegeInfo(
        uri: Uri,
        name: String,
        address: String,
        description: String,
        websiteLink: String
    ) {
        _isPost.postValue(false)
        val randomId = UUID.randomUUID().toString()
        val imageRef = storageRef.child("$COLLEGEINFO/${randomId}.jpg")
        val uploadTask = imageRef.putFile(uri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { imageUrl ->
                uploadCollegeInfo(imageUrl.toString(), name, address, description, websiteLink)
            }
        }
    }

    private fun uploadCollegeInfo(
        imageUrl: String,
        name: String,
        address: String,
        description: String,
        websiteLink: String
    ) {
        val map = hashMapOf(
            "imageUrl" to imageUrl,
            "websiteLink" to websiteLink,
            "name" to name,
            "description" to description,
            "address" to address
        )
        collegeInfoRef.document("CollegeInfo").set(map)
            .addOnSuccessListener {
                _isPost.postValue(true)
            }.addOnFailureListener {
                _isPost.postValue(false)
            }
    }

    fun getCollegeInfo() {
        collegeInfoRef.document("CollegeInfo").get().addOnSuccessListener { document ->
            val collegeInfo = document.toObject(CollegeInfoModel::class.java)
            if (collegeInfo != null) {
                _collegeInfo.postValue(collegeInfo)
            }
        }.addOnFailureListener { exception ->
            // Handle failure
        }
    }
}
