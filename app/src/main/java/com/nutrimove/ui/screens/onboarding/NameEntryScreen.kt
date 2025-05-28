package com.nutrimove.ui.screens.onboarding



import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier

import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp

import androidx.navigation.NavController

import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.*

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp

import com.nutrimove.R







@Composable

fun NameEntryScreen(

    navController: NavController,

    onNameEntered: (String) -> Unit

) {

    var name by remember { mutableStateOf("") }



    Surface(

        modifier = Modifier.fillMaxSize(),

        color = MaterialTheme.colorScheme.background

    ) {

        Column(

            modifier = Modifier

                .fillMaxSize()

                .padding(24.dp),

            verticalArrangement = Arrangement.Center,

            horizontalAlignment = Alignment.CenterHorizontally

        ) {

// LOGO

            Image(

                painter = painterResource(id = R.drawable.teste1), // substitui com o teu nome correto

                contentDescription = "Logo NutriMove",

                modifier = Modifier

                    .height(400.dp) // ajusta o tamanho conforme necessário

                    .padding(bottom = 75.dp)

            )



            Text(

                text = "Olá, qual é o seu nome?",

                fontSize = 28.sp,

                fontWeight = FontWeight.Bold,

                color = MaterialTheme.colorScheme.primary,

                modifier = Modifier.padding(bottom = 24.dp)

            )



            OutlinedTextField(

                value = name,

                onValueChange = { name = it },

                label = { Text("Nome") },

                singleLine = true,

                shape = RoundedCornerShape(12.dp),

                modifier = Modifier

                    .fillMaxWidth()

                    .padding(bottom = 24.dp)

            )



            Button(

                onClick = {

                    if (name.isNotBlank()) {

                        onNameEntered(name)

                    }

                },

                enabled = name.isNotBlank(),

                modifier = Modifier

                    .fillMaxWidth()

            ) {

                Text("Continuar")

            }

        }

    }

}