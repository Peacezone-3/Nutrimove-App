package com.nutrimove.ui.screens.profile



import androidx.compose.foundation.Image

import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.runtime.collectAsState

import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp

import androidx.compose.ui.graphics.ColorFilter

import androidx.navigation.NavController

import com.nutrimove.R

import com.nutrimove.data.UserKeys

import com.nutrimove.data.UserPreferences



@Composable

fun ProfileScreen(navController: NavController) {

    val context = LocalContext.current

    val prefs = remember { UserPreferences(context) }



    val userMap by prefs.userFlow.collectAsState(initial = emptyMap())

    val userName = (userMap[UserKeys.NAME] as? String).orEmpty()

    val days by prefs.trainingDaysFlow.collectAsState(initial = 3)



    Column(

        modifier = Modifier

            .fillMaxSize()

            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Spacer(modifier = Modifier.height(16.dp))



        Text(

            text = "Olá, $userName!",

            style = MaterialTheme.typography.headlineSmall

        )



        Spacer(modifier = Modifier.height(24.dp))



        ProfileMenuButton("Dados pessoais", R.drawable.ic_avatar) {

            navController.navigate("profile_personal")

        }

        Spacer(modifier = Modifier.height(12.dp))



        ProfileMenuButton("A sua progressão", R.drawable.ic_goal) {

            navController.navigate("profile_progress")

        }

        Spacer(modifier = Modifier.height(12.dp))



        ProfileMenuButton("Estatísticas", R.drawable.ic_stats) {

            navController.navigate("profile_statistics")

        }

        Spacer(modifier = Modifier.height(12.dp))



        ProfileMenuButton("Dicas", R.drawable.ic_dicas) {

            navController.navigate("profile_tips")

        }



        Spacer(modifier = Modifier.weight(1f))



        Text(

            text = "Alterar dias de treino semanais ($days)",

            style = MaterialTheme.typography.bodyMedium.copy(

                color = MaterialTheme.colorScheme.primary

            ),

            modifier = Modifier.clickable {

                navController.navigate("training_days")

            }

        )



        Spacer(modifier = Modifier.height(16.dp))

    }

}



@Composable

private fun ProfileMenuButton(text: String, iconResId: Int, onClick: () -> Unit) {

    OutlinedButton(

        onClick = onClick,

        modifier = Modifier

            .fillMaxWidth()

            .height(56.dp),

        shape = RoundedCornerShape(12.dp)

    ) {

        Row(

            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement = Arrangement.Start,

            modifier = Modifier.fillMaxWidth()

        ) {

            Image(

                painter = painterResource(id = iconResId),

                contentDescription = null,

                modifier = Modifier

                    .size(24.dp)

                    .padding(end = 8.dp),

                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)

            )

            Text(text, style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp))

        }

    }

}