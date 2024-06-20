package com.example.managementapp.itemview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import com.example.managementapp.R
import com.example.managementapp.model.GalleryModel

@Composable
fun GalleryItemView(
    galleryModel: GalleryModel,
    delete: (GalleryModel) -> Unit,
    deleteImage: (image: String, cat: String) -> Unit
) {

    OutlinedCard(
        modifier = Modifier.padding(4.dp).fillMaxWidth()
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (category, deleteButton) = createRefs()

            Text(
                text = galleryModel.category,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    .constrainAs(category) {
                        start.linkTo(parent.start)
                        end.linkTo(deleteButton.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
            Card(
                modifier = Modifier
                    .constrainAs(deleteButton) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                    .padding(4.dp)
                    .clickable {
                        delete(galleryModel)
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        LazyRow() {
            items(galleryModel.images ?: emptyList()) { imageUrl ->
                ImageItemView(
                    imageUrl = imageUrl,
                    cat = galleryModel.category!!,
                    delete = { imageUrl, cat ->
                        deleteImage(imageUrl, cat)
                    }
                )
            }
        }
    }
}

