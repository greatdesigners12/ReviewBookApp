package com.example.reviewbookapp.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun Logo(fontSize : TextUnit) {
    Text(text = "Review Book", color = Color(0xFFf216b7), fontSize = fontSize)
}

@Composable
fun boldText(text : String,size : Int){
    Text(text, fontSize = size.sp, fontWeight = FontWeight.Bold, overflow = TextOverflow.Ellipsis)
}