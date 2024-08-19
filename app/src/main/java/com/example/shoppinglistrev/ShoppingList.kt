package com.example.shoppinglistrev

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingList() {
    val context = LocalContext.current
    var sItems by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var showDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            YourListText()

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Use weight to fill remaining space
            ) {
                items(sItems) {
                    item ->
                    if (item.isEditing)
                    {
                        ShoppingItemEditor(
                            item = item,
                            onEditComplete = {
                                editedName, editedQuantity ->
                                sItems = sItems.map { it.copy(isEditing = false) }
                                val editedItem = sItems.find { it.id == item.id }
                                editedItem?.let {
                                    it.name = editedName
                                    it.quantity = editedQuantity
                                }
                            })
                    }else {
                        ShoppingListItem(
                            item = item,
                            onEditClick = {
                                sItems = sItems.map { it.copy(isEditing = it.id == item.id) } },
                            onDeleteClick = {
                                sItems = sItems - item
                            })
                    }
                }
            }
        }

        Button(
            onClick = {
                Toast.makeText(context, "Add an Item", Toast.LENGTH_SHORT).show()
                showDialog = true
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
    }
    if (showDialog){
        itemQuantity = "1"
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    Button(
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFF008080),
                            contentColor = Color.White),
                        onClick = {
                        if (itemName.isNotBlank()){
                            val newItem = ShoppingItem(
                                id = sItems.size + 1,
                                name = itemName,
                                quantity = itemQuantity.toIntOrNull() ?: 1
                            )
                            sItems = sItems + newItem
                            showDialog = false
                            itemName = ""
                        }
                    }) {
                        Text(text = "Add")
                    }
                    Button(
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color(0xFF008080),
                            contentColor = Color.White),
                        onClick = { showDialog = false}) {
                        Text(text = "Cancel")
                    }
                }
            },
            title = { Text(text = "Add Shopping Item")},
            text = {
                Column {
                    OutlinedTextField(
                        value = itemName,
                        onValueChange = {itemName = it},
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                        )

                    OutlinedTextField(
                        value = itemQuantity,
                        onValueChange = {itemQuantity = it},
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }
        )
    }
}

@Composable
fun YourListText() {
    val text = "Your List"
    val outlineText = buildAnnotatedString {
        text.forEach { char ->
            withStyle(
                style = SpanStyle(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(1f, 1f),
                        blurRadius = 0f
                    )
                )
            ) {
                append(char)
            }
        }
    }

    Text(
        text = outlineText,
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            color = Color(0xFF008080)
        ),
        modifier = Modifier.padding(16.dp)
    )
}