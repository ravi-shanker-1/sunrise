package com.techpulse.features.onboarding.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techpulse.features.onboarding.domain.model.InterestLevel
import com.techpulse.features.onboarding.domain.model.TechCategory
import com.techpulse.features.onboarding.domain.model.Technology

private const val MIN_SELECTIONS = 3

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun TechStackSelectorScreen(
    viewModel: OnboardingViewModel,
    onContinue: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val selectionCount = state.selectedTechs.size
    val canContinue = selectionCount >= MIN_SELECTIONS

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Choose Your Tech Stack",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "$selectionCount selected (min $MIN_SELECTIONS)",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (canContinue) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            Button(
                onClick = onContinue,
                enabled = canContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Text(
                    text = if (canContinue) "Continue" else "Select at least $MIN_SELECTIONS technologies",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val categories = state.availableTechnologies.entries.toList()

            items(categories) { (category, technologies) ->
                CategorySection(
                    category = category,
                    technologies = technologies,
                    selectedTechs = state.selectedTechs,
                    onToggleTechnology = viewModel::toggleTechnology,
                    onSetInterestLevel = viewModel::setInterestLevel
                )
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CategorySection(
    category: TechCategory,
    technologies: List<Technology>,
    selectedTechs: Set<com.techpulse.features.onboarding.domain.model.TechSelection>,
    onToggleTechnology: (String) -> Unit,
    onSetInterestLevel: (String, InterestLevel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(
                text = category.icon,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = category.displayName,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            technologies.forEach { tech ->
                val selection = selectedTechs.find { it.technologyId == tech.id }
                val isSelected = selection != null

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FilterChip(
                        selected = isSelected,
                        onClick = { onToggleTechnology(tech.id) },
                        label = {
                            Text(
                                text = tech.name,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        leadingIcon = if (isSelected) {
                            {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            }
                        } else {
                            null
                        }
                    )

                    if (isSelected) {
                        InterestLevelSelector(
                            currentLevel = selection?.interestLevel ?: InterestLevel.INTERESTED,
                            onLevelSelected = { level ->
                                onSetInterestLevel(tech.id, level)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InterestLevelSelector(
    currentLevel: InterestLevel,
    onLevelSelected: (InterestLevel) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(top = 4.dp)
    ) {
        InterestLevel.entries.forEach { level ->
            val isActive = level == currentLevel
            OutlinedButton(
                onClick = { onLevelSelected(level) },
                modifier = Modifier.height(28.dp),
                contentPadding = ButtonDefaults.TextButtonContentPadding,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (isActive) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
            ) {
                Text(
                    text = level.displayName,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isActive) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
        }
    }
}
