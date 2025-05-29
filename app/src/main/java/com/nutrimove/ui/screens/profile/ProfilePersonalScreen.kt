// app/src/main/java/com/nutrimove/ui/screens/profile/ProfilePersonalScreen.kt
package com.nutrimove.ui.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nutrimove.data.UserKeys
import com.nutrimove.data.UserPreferences
import com.nutrimove.ui.theme.Dimens

@Composable
fun ProfilePersonalScreen(navController: NavController) {
    val context = LocalContext.current
    val prefs   = remember { UserPreferences(context) }
    val userMap by prefs.userFlow.collectAsState(initial = emptyMap())

    val fields = listOf(
        "Nome"       to (userMap[UserKeys.NAME]     as? String ?: ""),
        "Idade"      to ((userMap[UserKeys.AGE]     as? Int)?.toString() ?: ""),
        "Altura"     to ((userMap[UserKeys.HEIGHT] as? Int)?.toString() ?: "") + " cm",
        "Peso"       to ((userMap[UserKeys.WEIGHT] as? Int)?.toString() ?: "") + " kg",
        "Atividade"  to (userMap[UserKeys.ACTIVITY] as? String ?: ""),
        "Objetivo"   to (userMap[UserKeys.GOAL]     as? String ?: "")
    )

    Column(
        modifier           = Modifier
            .fillMaxSize()
            .padding(Dimens.spacingMd),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // ========== Data list ==========
        Column {
            Text(
                text      = "Dados pessoais",
                style     = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.Center,
                modifier  = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimens.spacingMd)
            )

            fields.forEach { (label, value) ->
                Row(
                    modifier            = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimens.spacingXs),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Text(
                        label,
                        style      = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        value,
                        style      = MaterialTheme.typography.bodyMedium
                    )
                }
                Divider()
            }
        }

        // ========== Bottom actions ==========
        Column {
            Button(
                onClick  = { navController.navigate("profile_personal_edit") },
                modifier = Modifier.fillMaxWidth(),
                shape    = MaterialTheme.shapes.medium
            ) {
                Text("Change")
            }
            Spacer(Modifier.height(Dimens.spacingSm))
            Text(
                text      = "Sair",
                style     = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary),
                modifier  = Modifier
                    .fillMaxWidth()
                    .clickable { navController.popBackStack() }
                    .padding(vertical = Dimens.spacingSm),
                textAlign = TextAlign.Center
            )
        }
    }
}
