package com.techpulse.features.learningplan.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.techpulse.features.learningplan.domain.model.LearningItem
import com.techpulse.features.learningplan.presentation.components.ProgressCard
import com.techpulse.features.learningplan.presentation.components.StreakBadge

@Composable
fun LearningPlanScreen(viewModel: LearningPlanViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadPlan() }

    when (val state = uiState) {
        is LearningPlanUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is LearningPlanUiState.Error -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(state.message, style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = { viewModel.loadPlan() }) { Text("Retry") }
                }
            }
        }
        is LearningPlanUiState.Success -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        "Learning Plan",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StreakBadge(streakWeeks = state.plan.streakWeeks, modifier = Modifier.weight(1f))
                        ProgressCard(
                            completed = state.plan.completedCount,
                            total = state.plan.totalCount,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                item { Spacer(Modifier.height(8.dp)) }
                items(state.plan.items, key = { it.id }) { item ->
                    LearningItemCard(
                        item = item,
                        onToggle = { viewModel.toggleItemCompletion(item.id, !item.isCompleted) }
                    )
                }
            }
        }
    }
}

@Composable
private fun LearningItemCard(item: LearningItem, onToggle: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = item.isCompleted, onCheckedChange = { onToggle() })
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    item.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    item.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AssistChip(
                        onClick = {},
                        label = { Text(item.type.name.lowercase().replaceFirstChar { it.uppercase() }) }
                    )
                    AssistChip(
                        onClick = {},
                        label = { Text("${item.estimatedMinutes} min") }
                    )
                }
            }
        }
    }
}
