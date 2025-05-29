// app/src/main/java/com/nutrimove/ui/screens/profile/ProfileMainScreen.kt
package com.nutrimove.ui.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nutrimove.data.UserKeys
import com.nutrimove.data.UserPreferences
import com.nutrimove.ui.theme.Dimens

@Composable
fun ProfileMainScreen(navController: NavController) {
    val context     = LocalContext.current
    val prefs       = remember { UserPreferences(context) }
    val userMap by prefs.userFlow.collectAsState(initial = emptyMap())
    val name        = (userMap[UserKeys.NAME] as? String).orEmpty()

    Box(modifier = Modifier.fillMaxSize()) {
        // Greeting at top
        Text(
            text      = "Olá, $name!",
            style     = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier  = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.spacingXl),
            textAlign = TextAlign.Center
        )

        // Scrollable menu cards
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.spacingMd)
                .padding(top = 120.dp, bottom = 80.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimens.spacingMd)
        ) {
            ProfileCard(
                icon    = Icons.Filled.Person,
                title   = "Dados Pessoais",
                onClick = { navController.navigate("profile_personal") }
            )
            ProfileCard(
                icon    = Icons.Filled.CalendarToday,
                title   = "Dias de Treino",
                onClick = { navController.navigate("profile_training_days") }
            )
            ProfileCard(
                icon    = Icons.Filled.TrendingUp,
                title   = "Progresso",
                onClick = { navController.navigate("profile_progression") }
            )
            ProfileCard(
                icon    = Icons.Filled.BarChart,
                title   = "Estatísticas",
                onClick = { navController.navigate("profile_statistics") }
            )
            ProfileCard(
                icon    = Icons.Filled.Work,
                title   = "Trabalhe Conosco",
                onClick = { navController.navigate("profile_trabalhe_conosco") }
            )
            ProfileCard(
                icon    = Icons.Filled.Lightbulb,
                title   = "Dicas",
                onClick = { navController.navigate("profile_tips") }
            )
        }
    }
}

@Composable
private fun ProfileCard(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier  = Modifier
            .fillMaxWidth(0.8f)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape     = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier             = Modifier
                .fillMaxWidth()
                .padding(Dimens.spacingMd),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector        = icon,
                    contentDescription = null,
                    modifier           = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(Dimens.spacingSm))
                Text(title, style = MaterialTheme.typography.bodyLarge)
            }
            Icon(Icons.Filled.ArrowForward, contentDescription = null)
        }
    }
}
