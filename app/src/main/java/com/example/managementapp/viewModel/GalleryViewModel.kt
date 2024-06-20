package com.example.managementapp.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.managementapp.model.GalleryModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class GalleryViewModel : ViewModel() {

    private val galleryRef = FirebaseFirestore.getInstance().collection("gallery")
    private val storageRef = FirebaseStorage.getInstance().reference

    val _isPost = MutableLiveData<Boolean>()
    val isPost: LiveData<Boolean> = _isPost

    val _isDeleted = MutableLiveData<Boolean>() 
    val isDeleted: LiveData<Boolean> = _isDeleted

    private val _galleryList = MutableLiveData<List<GalleryModel>>()
    val galleryList: LiveData<List<GalleryModel>> = _galleryList

    fun saveGalleryImage(uri: Uri, category: String, isCategory: Boolean) {
        _isPost.postValue(false)
        val randomId = UUID.randomUUID().toString()
        val imageRef = storageRef.child("gallery/$randomId.jpg")

        imageRef.putFile(uri).addOnSuccessListener { taskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                if (isCategory) {
                    uploadCategoryImage(downloadUri.toString(), category)
                } else {
                    updateImage(downloadUri.toString(), category)
                }
            }
        }.addOnFailureListener { e ->
            _isPost.postValue(false)
            // Handle errors
        }
    }

    private fun uploadCategoryImage(imageUrl: String, category: String) {
        val map = hashMapOf(
            "category" to category,
            "images" to listOf(imageUrl)
        )
        galleryRef.document(category).set(map)
            .addOnSuccessListener {
                _isPost.postValue(true)
            }.addOnFailureListener {
                _isPost.postValue(false)
                // Handle errors
            }
    }

    private fun updateImage(imageUrl: String, category: String) {
        galleryRef.document(category).update("images", FieldValue.arrayUnion(imageUrl))
            .addOnSuccessListener {
                _isPost.postValue(true)
            }.addOnFailureListener {
                _isPost.postValue(false)
                // Handle errors
            }
    }

    fun getGallery(categoryName: String) {
        galleryRef.get().addOnSuccessListener { querySnapshot ->
            val list = mutableListOf<GalleryModel>()
            for (doc in querySnapshot) {
                val galleryModel = doc.toObject(GalleryModel::class.java)
                list.add(galleryModel)
            }
            _galleryList.postValue(list)
        }.addOnFailureListener { e ->
            // Handle errors
        }
    }

    fun deleteGallery(galleryModel: GalleryModel) {
        galleryModel.images?.forEach { imageUrl ->
            FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl).delete()
        }
        galleryRef.document(galleryModel.category).delete().addOnSuccessListener {
            _isDeleted.postValue(true)
        }.addOnFailureListener {
            _isDeleted.postValue(false)
            // Handle errors
        }
    }

    fun deleteImage(category: String, image: String) {
        galleryRef.document(category).update("images", FieldValue.arrayRemove(image))
            .addOnSuccessListener {
                FirebaseStorage.getInstance().getReferenceFromUrl(image).delete()
                _isDeleted.postValue(true)
            }.addOnFailureListener {
                _isDeleted.postValue(false)
                // Handle errors
            }
    }
}
