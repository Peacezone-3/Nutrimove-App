// app/src/main/java/com/nutrimove/ui/screens/profile/TrabalheConoscoScreen.kt
package com.nutrimove.ui.screens.profile

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nutrimove.ui.theme.Dimens

@Composable
fun TrabalheConoscoScreen(navController: NavController) {
    val context = LocalContext.current

    var section by remember { mutableStateOf<ContactSection?>(null) }

    // PT form state
    var ptName       by remember { mutableStateOf("") }
    var ptLicensed   by remember { mutableStateOf("") }
    var ptExperience by remember { mutableStateOf("") }
    var ptLocation   by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.spacingMd)
    ) {
        // Header
        Text(
            "Trabalhe Conosco",
            style     = MaterialTheme.typography.headlineSmall,
            modifier  = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(Dimens.spacingMd))

        if (section == null) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                // Top: PT
                Column(
                    modifier           = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Button(
                        onClick  = { section = ContactSection.PT },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("És um PT?")
                    }
                    Text(
                        text      = "Entra em contacto conosco para potencialmente entrar na nossa lista de PTs profissionais e receberes clientes na tua zona, simplesmente preenche o formulário acima!",
                        style     = MaterialTheme.typography.bodyMedium,
                        modifier  = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimens.spacingSm),
                        textAlign = TextAlign.Start
                    )
                }

                // Middle: Cliente
                Column(
                    modifier           = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Button(
                        onClick  = { section = ContactSection.CLIENT },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("És um cliente?")
                    }
                    Text(
                        text      = "Se não estiver satisfeito com os recursos gratuitos que oferecemos, tens a opção de ser acompanhado diretamente por um dos nossos profissionais na tua área. Contacta-nos!",
                        style     = MaterialTheme.typography.bodyMedium,
                        modifier  = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimens.spacingSm),
                        textAlign = TextAlign.Start
                    )
                }
            }
        }

        // PT Section
        if (section == ContactSection.PT) {
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.spacingMd)) {
                Text(
                    "Se és Personal Trainer, envia-nos um e-mail para entrar na nossa lista de espera. " +
                            "Quando tivermos clientes disponíveis na tua área, entraremos em contacto.",
                    style = MaterialTheme.typography.bodyMedium
                )
                OutlinedTextField(ptName,   onValueChange = { ptName = it },   label = { Text("Nome completo") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(ptLicensed, onValueChange = { ptLicensed = it }, label = { Text("Licenciado? (Sim/Não)") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(ptExperience, onValueChange = { ptExperience = it },
                    label = { Text("Anos de experiência") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(ptLocation, onValueChange = { ptLocation = it }, label = { Text("Localização") }, modifier = Modifier.fillMaxWidth())

                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:contact@nutrimove.com")
                            putExtra(Intent.EXTRA_SUBJECT, "Candidatura Personal Trainer")
                            putExtra(Intent.EXTRA_TEXT,
                                """
                                Nome: $ptName
                                Licenciado: $ptLicensed
                                Experiência: $ptExperience anos
                                Localização: $ptLocation
                                """.trimIndent()
                            )
                        }
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled  = ptName.isNotBlank() && ptLicensed.isNotBlank()
                ) {
                    Text("Enviar E-mail")
                }

                TextButton(
                    onClick = { section = null },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Voltar")
                }
            }
        }

        // Cliente Section
        if (section == ContactSection.CLIENT) {
            Column(verticalArrangement = Arrangement.spacedBy(Dimens.spacingMd)) {
                Text(
                    "Se não estás satisfeito com o que oferecemos gratuitamente no app, " +
                            "podes contactar um dos nossos Personal Trainers diretamente.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:contact@nutrimove.com")
                            putExtra(Intent.EXTRA_SUBJECT, "Pedido de Contacto - Cliente")
                        }
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Contactar-nos")
                }

                TextButton(
                    onClick = { section = null },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Voltar")
                }
            }
        }

        Spacer(Modifier.height(Dimens.spacingMd))
        Text(
            "Sair",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.popBackStack() }
                .padding(vertical = Dimens.spacingSm),
            textAlign = TextAlign.Center
        )
    }
}

enum class ContactSection { PT, CLIENT }
