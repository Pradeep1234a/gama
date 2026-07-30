package com.colorversekids.studio.app.feature.parent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.colorversekids.studio.app.core.designsystem.ElectricCoral
import com.colorversekids.studio.app.core.designsystem.SunshineGold
import com.colorversekids.studio.app.core.designsystem.components.KidsCard
import com.colorversekids.studio.app.core.designsystem.components.KidsPrimaryButton
import com.colorversekids.studio.app.core.designsystem.components.KidsTopBar

@Composable
fun ParentDashboardScreen(
    viewModel: ParentViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    if (!uiState.isGateUnlocked) {
        ParentalGateDialog(
            question = uiState.gateQuestion,
            onVerify = { input -> viewModel.verifyGateAnswer(input) },
            onCancel = onBackClick
        )
    } else {
        Scaffold(
            topBar = {
                KidsTopBar(
                    title = "Parent Portal 🛡️",
                    onBackClick = onBackClick
                )
            },
            modifier = modifier
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Educational Usage Insights Card
                KidsCard(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Educational Insights 📊",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            InsightItem(value = "${uiState.totalTimeSpentMinutes}m", label = "Play Time Today")
                            InsightItem(value = "${uiState.completedDrawingsCount}", label = "Drawings Completed")
                            InsightItem(value = "Space", label = "Top Category")
                        }
                    }
                }

                // Screen Time Controls Card
                KidsCard(
                    containerColor = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Daily Screen Time Limit ⏳",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            listOf(15, 30, 45, 60).forEach { mins ->
                                val selected = uiState.settings.screenTimeLimitMinutes == mins
                                FilterChip(
                                    selected = selected,
                                    onClick = { viewModel.updateScreenTime(mins) },
                                    label = { Text("${mins}m") }
                                )
                            }
                        }
                    }
                }

                // Sound & Audio Controls
                KidsCard(
                    containerColor = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Audio & Guidance Controls 🔊",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Sound Effects (SFX)", style = MaterialTheme.typography.bodyLarge)
                            Switch(
                                checked = uiState.settings.isSoundEffectsEnabled,
                                onCheckedChange = { viewModel.toggleSound(it) }
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Voice Guidance Prompts", style = MaterialTheme.typography.bodyLarge)
                            Switch(
                                checked = uiState.settings.isVoiceGuidanceEnabled,
                                onCheckedChange = { viewModel.toggleVoice(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InsightItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
            color = ElectricCoral
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ParentalGateDialog(
    question: String,
    onVerify: (String) -> Boolean,
    onCancel: () -> Unit
) {
    var inputAnswer by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onCancel) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Text(text = "🔒", fontSize = 48.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Grown-Ups Only!",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Please solve this math equation to proceed:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = question,
                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Black),
                    color = SunshineGold
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = inputAnswer,
                    onValueChange = {
                        inputAnswer = it
                        showError = false
                    },
                    label = { Text("Enter Answer") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = showError,
                    modifier = Modifier.fillMaxWidth()
                )

                if (showError) {
                    Text(
                        text = "Incorrect answer. Try again!",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = onCancel,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }

                    KidsPrimaryButton(
                        onClick = {
                            val success = onVerify(inputAnswer)
                            if (!success) showError = true
                        },
                        containerColor = ElectricCoral,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Unlock", color = Color.White)
                    }
                }
            }
        }
    }
}
