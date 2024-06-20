package com.example.managementapp.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.managementapp.model.BannerModel
import com.example.managementapp.utils.ConstantItem.BANNER
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.util.UUID

class BannerViewModel : ViewModel() {

    private val bannerRef = Firebase.firestore.collection(BANNER)
    private val storageRef = Firebase.storage.reference

    val _isPost = MutableLiveData<Boolean>()
    val isPost: LiveData<Boolean> = _isPost

    val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted: LiveData<Boolean> = _isDeleted

    private val _bannerList = MutableLiveData<List<BannerModel>>()
    val bannerList: LiveData<List<BannerModel>> = _bannerList

    fun saveImage(imageUri: Uri) {
        _isPost.postValue(false)
        val randomId = UUID.randomUUID().toString()

        val imageRef = storageRef.child("$BANNER/$randomId.jpg")

        val uploadTask = imageRef.putFile(imageUri)

        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { imageUrl ->
                uploadImage(imageUrl.toString(), randomId)
            }
        }
    }

    private fun uploadImage(imageUrl: String, docId: String) {
        val map = mutableMapOf<String, String>().apply {
            this["url"] = imageUrl
            this["docId"] = docId
        }

        bannerRef.document(docId).set(map)
            .addOnSuccessListener {
                _isPost.postValue(true)
            }.addOnFailureListener {
                _isPost.postValue(false)
            }
    }

    fun getBanner() {
        bannerRef.get().addOnSuccessListener { snapshot ->
            val list = mutableListOf<BannerModel>()
            for (doc in snapshot) {
                list.add(doc.toObject(BannerModel::class.java))
            }
            _bannerList.postValue(list)
        }
    }

    fun deleteBanner(bannerModel: BannerModel) {
        bannerModel.docId?.let { docId ->
            bannerModel.url?.let { url ->
                bannerRef.document(docId).delete().addOnSuccessListener {
                    Firebase.storage.getReferenceFromUrl(url).delete()
                    _isDeleted.postValue(true)
                }.addOnFailureListener {
                    _isDeleted.postValue(false)
                }
            }
        }
    }
}
