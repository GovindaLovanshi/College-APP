package com.example.managementapp.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.managementapp.model.FacultyModel
import com.example.managementapp.utils.ConstantItem.FACULTY
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class FacultyViewModel : ViewModel() {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()

    private val facultyRef = firestore.collection(FACULTY)
    private val storageRef = storage.reference

    val _isPost = MutableLiveData<Boolean>()
    val isPost: LiveData<Boolean> = _isPost

    val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted: LiveData<Boolean> = _isDeleted

    private val _facultyList = MutableLiveData<List<FacultyModel>>()
    val facultyList: LiveData<List<FacultyModel>> = _facultyList

    private val _categoryList = MutableLiveData<List<String>>()
    val categoryList: LiveData<List<String>> = _categoryList

    fun saveFaculty(uri: Uri, name: String, email: String, position: String, catName: String) {
        _isPost.postValue(false)
        val randomId = UUID.randomUUID().toString()
        val imageRef = storageRef.child("$FACULTY/$randomId.jpg")

        val uploadTask = imageRef.putFile(uri)

        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { imageUrl ->
                uploadFaculty(imageUrl.toString(), randomId, name, email, position, catName)
            }
        }
    }

    private fun uploadFaculty(
        imageUrl: String,
        docId: String,
        email: String,
        name: String,
        position: String,
        catName: String
    ) {
        val facultyData = FacultyModel(docId, imageUrl, name, email, position, catName)
        facultyRef.document(catName).collection("teacher").document(docId).set(facultyData)
            .addOnSuccessListener {
                _isPost.postValue(true)
            }.addOnFailureListener {
                _isPost.postValue(false)
            }
    }

    fun uploadCategory(category: String) {
        val categoryData = mapOf("category" to category)
        facultyRef.document(category).set(categoryData)
            .addOnSuccessListener {
                _isPost.postValue(true)
            }.addOnFailureListener {
                _isPost.postValue(false)
            }
    }

    fun getFaculty(categoryName: String) {
        facultyRef.document(categoryName).collection("teacher").get().addOnSuccessListener { snapshot ->
            val list = mutableListOf<FacultyModel>()
            for (doc in snapshot) {
                list.add(doc.toObject(FacultyModel::class.java))
            }
            _facultyList.postValue(list)
        }
    }

    fun getCategory(categoryName: String) {
        facultyRef.document(categoryName).collection("teacher").get().addOnSuccessListener { snapshot ->
            val list = snapshot.documents.mapNotNull { doc -> doc.getString("category") }
            _categoryList.postValue(list)
        }
    }

    fun deleteFaculty(docId: String, catName: String, imageUrl: Uri?) {
        facultyRef.document(catName).collection("teacher").document(docId).delete()
            .addOnSuccessListener {
                storageRef.child(imageUrl.toString()).delete()
                _isDeleted.postValue(true)
            }.addOnFailureListener {
                _isDeleted.postValue(false)
            }
    }

    fun deleteCategory(category: String) {
        facultyRef.document(category).delete()
            .addOnSuccessListener {
                _isDeleted.postValue(true)
            }.addOnFailureListener {
                _isDeleted.postValue(false)
            }
    }
}
