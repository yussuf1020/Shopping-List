package com.example.shoppinglistrev

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingList() {
    val context = LocalContext.current
    var sItems by remember { mutableStateOf(listOf<ShoppingItem>()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Button(
            onClick = {
                Toast.makeText(context, "Add an Item", Toast.LENGTH_SHORT).show()

            },
            modifier = Modifier
                .size(90.dp)
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFF008080))
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp), // Adjust padding to avoid overlap with the button
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Use weight to fill remaining space
                    .padding(16.dp)
            ) {
                items(sItems) {

                }
            }
        }
    }
}

