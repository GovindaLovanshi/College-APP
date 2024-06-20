package com.example.managementapp.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.managementapp.model.NoticeModel
import com.example.managementapp.utils.ConstantItem.NOTICE
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class NoticeViewModel : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    private val noticeRef = firestore.collection(NOTICE)
    private val storageRef = storage.reference

    val _isPost = MutableLiveData<Boolean>()
    val isPost: LiveData<Boolean> = _isPost

    val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted: LiveData<Boolean> = _isDeleted

    private val _noticeList = MutableLiveData<List<NoticeModel>>()
    val noticeList: LiveData<List<NoticeModel>> = _noticeList

    fun saveNotice(uri: Uri, title: String, link: String) {
        _isPost.postValue(false)
        val randomId = UUID.randomUUID().toString()

        val imageRef = storageRef.child("$NOTICE/$randomId.jpg")

        val uploadTask = imageRef.putFile(uri)

        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { imageUrl ->
                uploadNotice(imageUrl.toString(), title, link, randomId)
            }
        }
    }

    private fun uploadNotice(imageUrl: String, title: String, link: String, docId: String) {
        val noticeData = NoticeModel(docId, imageUrl, title, link)
        noticeRef.document(docId).set(noticeData)
            .addOnSuccessListener {
                _isPost.postValue(true)
            }.addOnFailureListener {
                _isPost.postValue(false)
            }
    }

    fun getNotice() {
        noticeRef.get().addOnSuccessListener { snapshot ->
            val list = mutableListOf<NoticeModel>()
            for (doc in snapshot) {
                list.add(doc.toObject(NoticeModel::class.java))
            }
            _noticeList.postValue(list)
        }
    }

    fun deleteNotice(noticeModel: NoticeModel) {
        noticeRef.document(noticeModel.docId ?: "").delete()
            .addOnSuccessListener {
                storageRef.child(noticeModel.imageUrl ?: "").delete()
                _isDeleted.postValue(true)
            }.addOnFailureListener {
                _isDeleted.postValue(false)
            }
    }
}
