package com.techpulse.features.settings.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    var darkMode by remember { mutableStateOf(false) }
    var deliveryDay by remember { mutableStateOf("Sunday") }
    var contentDensity by remember { mutableStateOf("Standard") }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item {
            Text("Settings", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(16.dp))
        }
        item { SectionHeader("Appearance") }
        item {
            SettingsToggleItem("Dark Mode", "Override system theme", darkMode) { darkMode = it }
        }
        item { SectionHeader("Content") }
        item {
            SettingsItem("Delivery Day", deliveryDay) {}
        }
        item {
            SettingsItem("Content Density", contentDensity) {}
        }
        item { SectionHeader("Account") }
        item { SettingsItem("Export Data", "Download your data") {} }
        item { SettingsItem("Privacy Policy", "") {} }
        item { SettingsItem("Delete Account", "Permanently delete all data") {} }
        item { SectionHeader("About") }
        item { SettingsItem("Version", "1.0.0") {} }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        title,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
private fun SettingsItem(title: String, subtitle: String, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = if (subtitle.isNotEmpty()) {{ Text(subtitle) }} else null,
        modifier = Modifier.clickable(onClick = onClick)
    )
}

@Composable
private fun SettingsToggleItem(title: String, subtitle: String, checked: Boolean, onToggle: (Boolean) -> Unit) {
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = { Text(subtitle) },
        trailingContent = { Switch(checked = checked, onCheckedChange = onToggle) }
    )
}
