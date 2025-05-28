// app/src/main/java/com/nutrimove/ui/screens/TrainingModels.kt

package com.nutrimove.ui.screens.treinos

data class WorkoutExercise(
    val name: String,
    val sets: Int,
    val reps: Int,
    val targetMuscle: String,
    val tips: List<String>
)

data class WorkoutDay(
    val title: String,
    val exercises: List<WorkoutExercise>
)

fun generateSplit(days: Int): List<WorkoutDay> = when (days) {
    2 -> listOf(
        WorkoutDay("Dia 1 | Superiores", listOf(
            WorkoutExercise("Supino reto c/ barra", 3, 8, "Peitoral", listOf(
                "Mantenha cotovelos alinhados",
                "Contraia bem o peitoral"
            )),
            WorkoutExercise("Supino inclinado c/ alteres", 3, 8, "Peitoral Alto", listOf(
                "Mantenha cotovelos alinhados",
                "Tocar com alteres no peito"
            )),
            WorkoutExercise("Crucifixo", 3, 10, "Peitoral", listOf(
                "Mantenha cotovelos estáveis",
                "Contraia até bater uma na outra"
            )),
            WorkoutExercise("Remada curvada", 3, 6, "Costas [Densidade]", listOf(
                "Costas retas",
                "Puxar barra para zona do abdomen"
            )),
            WorkoutExercise("Elevações", 2, 5-10, "Costas [Dorsal]", listOf(
                "Estique os braços em baixo e suba até o queixo passar a barra"
            )),
            WorkoutExercise("Bicep com alteres alternado", 4, 8-12, "Biceps", listOf(
                "Começe numa posição supinada"
            )),
            WorkoutExercise("Extensão de Tricep com cabos", 4, 8-12, "Triceps", listOf(
                "Contraia bem o tricep na posição inferior"
            ))

        )),
        WorkoutDay("Dia 2 | Inferiores", listOf(
            WorkoutExercise("Agachamento Smith", 3, 6-8, "Quadríceps", listOf(
                "Tronco reto, evite flexão lombar",
                "Descer até o seu corpo fazer ~90º"
            )),
            WorkoutExercise("Prensa de pernas", 3, 6-8, "Quadríceps", listOf(
                "Pés alinhados",
                "Movimento controlado"
            )),
            WorkoutExercise("Extensão de Pernas", 4, 8-12, "Quadríceps", listOf(
                "Subir até ao máximo para contrair bem o quadricep"
            )),
            WorkoutExercise("Romanian Deadlift", 2, 6-8, "Posterior de coxa", listOf(
                "Tronco reto, evite flexão lombar"
            ))
        ))
    )

    3 -> listOf(
        WorkoutDay("Dia 1 | Push", listOf(
            WorkoutExercise("Supino reto", 3, 6-8, "Peitoral", listOf("…")),
            WorkoutExercise("Supino inclinado com alteres", 3, 6-8, "Peitoral", listOf("…")),
            WorkoutExercise("Crucifixo", 3, 6-8, "Peitoral", listOf("…")),
            WorkoutExercise("Prensa de ombros", 3, 6-8, "Ombros", listOf("…")),
            WorkoutExercise("Elevações Laterais", 3, 6-8, "Ombros", listOf("…")),
            WorkoutExercise("Puxada frontal", 2, 10-15, "Ombros", listOf("…")),
            WorkoutExercise("Extensão de tricep com cordas", 3, 6-10, "Tricep", listOf("…")),
            WorkoutExercise("Extensão de tricep com V-BAR acima da cabeça", 3, 6-10, "Ombros", listOf("…"))
        )),
        WorkoutDay("Dia 2 | Pull", listOf(
            WorkoutExercise("Elevações", 3, 6-10, "Costas", listOf("…")),
            WorkoutExercise("Remada Curvada com Barra", 3, 6-10, "Costas", listOf("…")),
            WorkoutExercise("Máquina de Pulldown", 3, 6-8, "Costas", listOf("…")),
            WorkoutExercise("Bicep com alteres alternado", 4, 10-15, "Bicep", listOf("…")),
            WorkoutExercise("Rosca bíceps", 4, 10-15, "Bíceps", listOf("…"))
        )),
        WorkoutDay("Dia 3 | Legs", listOf(
            WorkoutExercise("Agachamento", 3, 6, "Quadríceps", listOf("…")),
            WorkoutExercise("Stiff Leg Deadlifts", 3, 6, "Posterior de Coxa", listOf("…")),
            WorkoutExercise("Extensão de Pernas", 4, 6-10, "Quadríceps", listOf("…")),
            WorkoutExercise("Prensa de pernas", 2, 4-8, "Quadríceps", listOf("…"))
        ))
    )

    4 -> listOf(
        WorkoutDay("Dia 1 | Push", listOf(
            WorkoutExercise("Supino reto", 3, 6-8, "Peitoral", listOf("…")),
            WorkoutExercise("Supino inclinado com alteres", 3, 6-8, "Peitoral", listOf("…")),
            WorkoutExercise("Crucifixo", 3, 6-8, "Peitoral", listOf("…")),
            WorkoutExercise("Prensa de ombros", 3, 6-8, "Ombros", listOf("…")),
            WorkoutExercise("Elevações Laterais", 3, 6-8, "Ombros", listOf("…")),
            WorkoutExercise("Puxada frontal", 2, 10-15, "Ombros", listOf("…")),
            WorkoutExercise("Extensão de tricep com cordas", 3, 6-10, "Tricep", listOf("…")),
            WorkoutExercise("Extensão de tricep com V-BAR acima da cabeça", 3, 6-10, "Ombros", listOf("…"))
        )),
        WorkoutDay("Dia 2 | Pull", listOf(
            WorkoutExercise("Elevações", 3, 6-10, "Costas", listOf("…")),
            WorkoutExercise("Remada Curvada com Barra", 3, 6-10, "Costas", listOf("…")),
            WorkoutExercise("Máquina de Pulldown", 3, 6-8, "Costas", listOf("…")),
            WorkoutExercise("Bicep com alteres alternado", 4, 10-15, "Bicep", listOf("…")),
            WorkoutExercise("Rosca bíceps", 4, 10-15, "Bíceps", listOf("…"))
        )),
        WorkoutDay("Dia 3 | Legs", listOf(
            WorkoutExercise("Agachamento", 3, 6, "Quadríceps", listOf("…")),
            WorkoutExercise("Stiff Leg Deadlifts", 3, 6, "Posterior de Coxa", listOf("…")),
            WorkoutExercise("Extensão de Pernas", 4, 6-10, "Quadríceps", listOf("…")),
            WorkoutExercise("Prensa de pernas", 2, 4-8, "Quadríceps", listOf("…")),
        )),
        WorkoutDay("Dia 4 | Push/Pull", listOf(
            WorkoutExercise("Supino reto", 3, 6-8, "Peitoral", listOf("…")),
            WorkoutExercise("Supino inclinado com alteres", 3, 6-8, "Peitoral", listOf("…")),
            WorkoutExercise("Prensa de ombros", 3, 6-8, "Ombros", listOf("…")),
            WorkoutExercise("Elevações Laterais", 3, 6-8, "Ombros", listOf("…")),
            WorkoutExercise("Extensão de tricep com cordas", 3, 6-10, "Tricep", listOf("…")),
            WorkoutExercise("Remada Curvada com Barra", 3, 6-10, "Costas", listOf("…")),
            WorkoutExercise("Máquina de Pulldown", 3, 6-8, "Costas", listOf("…")),
            WorkoutExercise("Bicep com alteres alternado", 4, 10-15, "Bicep", listOf("…"))
        ))
    )

    5 -> listOf(
        WorkoutDay("Dia 1 | Push", listOf(
            WorkoutExercise("Supino reto", 3, 6-8, "Peitoral", listOf("…")),
            WorkoutExercise("Supino inclinado com alteres", 3, 6-8, "Peitoral", listOf("…")),
            WorkoutExercise("Crucifixo", 3, 6-8, "Peitoral", listOf("…")),
            WorkoutExercise("Prensa de ombros", 3, 6-8, "Ombros", listOf("…")),
            WorkoutExercise("Elevações Laterais", 3, 6-8, "Ombros", listOf("…")),
            WorkoutExercise("Puxada frontal", 2, 10-15, "Ombros", listOf("…")),
            WorkoutExercise("Extensão de tricep com cordas", 3, 6-10, "Tricep", listOf("…")),
            WorkoutExercise("Extensão de tricep com V-BAR acima da cabeça", 3, 6-10, "Ombros", listOf("…"))
        )),
        WorkoutDay("Dia 2 | Pull", listOf(
            WorkoutExercise("Elevações", 3, 6-10, "Costas", listOf("…")),
            WorkoutExercise("Remada Curvada com Barra", 3, 6-10, "Costas", listOf("…")),
            WorkoutExercise("Máquina de Pulldown", 3, 6-8, "Costas", listOf("…")),
            WorkoutExercise("Bicep com alteres alternado", 4, 10-15, "Bicep", listOf("…")),
            WorkoutExercise("Rosca bíceps", 4, 10-15, "Bíceps", listOf("…"))
        )),
        WorkoutDay("Dia 3 | Legs", listOf(
            WorkoutExercise("Agachamento", 3, 6, "Quadríceps", listOf("…")),
            WorkoutExercise("Stiff Leg Deadlifts", 3, 6, "Posterior de Coxa", listOf("…")),
            WorkoutExercise("Extensão de Pernas", 4, 6-10, "Quadríceps", listOf("…")),
            WorkoutExercise("Prensa de pernas", 2, 4-8, "Quadríceps", listOf("…")),
        )),
        WorkoutDay("Dia 4 | Push", listOf(
            WorkoutExercise("Supino reto", 3, 6-8, "Peitoral", listOf("…")),
            WorkoutExercise("Supino inclinado com alteres", 3, 6-8, "Peitoral", listOf("…")),
            WorkoutExercise("Crucifixo", 3, 6-8, "Peitoral", listOf("…")),
            WorkoutExercise("Prensa de ombros", 3, 6-8, "Ombros", listOf("…")),
            WorkoutExercise("Elevações Laterais", 3, 6-8, "Ombros", listOf("…")),
            WorkoutExercise("Puxada frontal", 2, 10-15, "Ombros", listOf("…")),
            WorkoutExercise("Extensão de tricep com cordas", 3, 6-10, "Tricep", listOf("…")),
            WorkoutExercise("Extensão de tricep com V-BAR acima da cabeça", 3, 6-10, "Ombros", listOf("…"))
        )),
        WorkoutDay("Dia 5 | Pull", listOf(
            WorkoutExercise("Elevações", 3, 6-10, "Costas", listOf("…")),
            WorkoutExercise("Remada Curvada com Barra", 3, 6-10, "Costas", listOf("…")),
            WorkoutExercise("Máquina de Pulldown", 3, 6-8, "Costas", listOf("…")),
            WorkoutExercise("Bicep com alteres alternado", 4, 10-15, "Bicep", listOf("…")),
            WorkoutExercise("Rosca bíceps", 4, 10-15, "Bíceps", listOf("…"))
        ))
    )

    6 -> listOf(
        WorkoutDay("Dia 1 | Peito", listOf(
            WorkoutExercise("Supino reto", 4, 6-8, "Peitoral", listOf("…")),
            WorkoutExercise("Supino inclinado com alteres", 4, 6-8, "Peitoral", listOf("…")),
            WorkoutExercise("Dips", 3, 6-10, "Peitoral", listOf("…")),
            WorkoutExercise("Crucifixo", 3, 6-10, "Peitoral", listOf("…")),
        )),
        WorkoutDay("Dia 2 | Costas", listOf(
            WorkoutExercise("Elevações", 3, 6-10, "Costas", listOf("…")),
            WorkoutExercise("Remada Curvada com Barra", 3, 6-10, "Costas", listOf("…")),
            WorkoutExercise("Máquina de Pulldown", 4, 6-10, "Costas", listOf("…")),
        )),
        WorkoutDay("Dia 3 | Pernas A", listOf(
            WorkoutExercise("Agachamento", 3, 8, "Quadríceps", listOf("…")),
            WorkoutExercise("Prensa de Perna", 3, 6-10, "Quadríceps", listOf("…")),
            WorkoutExercise("Extensora de Pernas", 4, 6-10, "Quadríceps", listOf("…"))

        )),
        WorkoutDay("Dia 4 | Ombros", listOf(
            WorkoutExercise("Prensa de ombros", 3, 6-8, "Ombros", listOf("…")),
            WorkoutExercise("Desenvolvimento Militar", 3, 6, "Ombros", listOf("…")),
            WorkoutExercise("Elevações Laterais", 3, 6-8, "Ombros", listOf("…")),
            WorkoutExercise("Puxada frontal", 2, 10-15, "Ombros", listOf("…"))
        )),
        WorkoutDay("Dia 5 | Pernas B", listOf(
            WorkoutExercise("Stiff Leg Deadlift", 3, 6, "Posterior", listOf("…")),
            WorkoutExercise("Hamstring Contractions", 3, 10-12, "Quadríceps", listOf("…")),
            WorkoutExercise("Romanian Deadlift c/ Alteres", 3, 10-12, "Quadríceps", listOf("…"))
        )),
        WorkoutDay("Dia 6 | Braços", listOf(
            WorkoutExercise("Bicep com alteres alternado", 3, 10-15, "Bicep", listOf("…")),
            WorkoutExercise("Bicep com alteres sentado", 3, 8-10, "Bicep", listOf("…")),
            WorkoutExercise("Rosca bíceps", 4, 10-15, "Bíceps", listOf("…")),
            WorkoutExercise("Extensão de tricep com cordas", 3, 6-10, "Tricep", listOf("…")),
            WorkoutExercise("Extensão de tricep com V-BAR acima da cabeça", 3, 6-10, "Ombros", listOf("…"))
        ))
    )

    else -> emptyList()
}
