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
                "Use um peso desafiador mantendo o controle da barra o tempo todo, evitando balanço.",
                "Baixe a barra de forma lenta e explosiva, sentindo o peito contrair no ponto inferior."
            )),
            WorkoutExercise("Supino inclinado c/ alteres", 3, 8, "Peitoral Alto", listOf(
                "Mantenha os peitorais ativos e não deixe os ombros elevarem.",
                "Controle a descida até sentir um alongamento confortável no músculo alvo."
            )),
            WorkoutExercise("Crucifixo", 3, 10, "Peitoral", listOf(
                "Abra os braços com leve flexão nos cotovelos, mantendo tensão no peito.",
                "Converja lentamente os pesos até contração máxima, sem pressa."
            )),
            WorkoutExercise("Remada curvada", 3, 6, "Costas [Densidade]", listOf(
                "Mantenha o tronco estável e puxe o peso em direção ao umbigo.",
                "Concentre-se em sentir as escápulas se aproximarem ao final do movimento."
            )),
            WorkoutExercise("Elevações no pull-up", 2, 6, "Costas [Dorsal]", listOf(
                "Inicie pendurado completamente e eleve o queixo acima da barra.",
                "Desça de modo controlado até o braço ficar quase estendido."
            )),
            WorkoutExercise("Bíceps com alteres alternado", 4, 12, "Bíceps", listOf(
                "Gire o pulso para supinação total no topo para máxima contração.",
                "Mantenha o cotovelo imóvel para forçar o bíceps a realizar todo o trabalho."
            )),
            WorkoutExercise("Extensão de tríceps com cabo", 4, 12, "Tríceps", listOf(
                "Posicione o cotovelo preso ao lado do corpo e estenda completamente.",
                "Faça uma pausa de 1 segundo na extensão total para ativação extra."
            ))
        )),
        WorkoutDay("Dia 2 | Inferiores", listOf(
            WorkoutExercise("Agachamento Smith", 3, 6, "Quadríceps", listOf(
                "Mantenha o tronco ereto e joelhos alinhados à ponta dos pés.",
                "Desça até os quadris ficarem abaixo da linha dos joelhos, se possível."
            )),
            WorkoutExercise("Prensa de pernas", 3, 6, "Quadríceps", listOf(
                "Coloque os pés na largura dos ombros e empurre com os calcanhares.",
                "Controle a fase excêntrica para proteger os joelhos."
            )),
            WorkoutExercise("Extensão de pernas", 4, 10, "Quadríceps", listOf(
                "Finalizar o movimento com contração isométrica de 1 segundo.",
                "Evite hiperextensão dos joelhos no topo."
            )),
            WorkoutExercise("Romanian Deadlift", 2, 6, "Posterior de Coxa", listOf(
                "Mantenha as costas retas e desça até sentir alongamento nos isquiotibiais.",
                "Suba empurrando o quadril para frente, contraindo glúteos no topo."
            ))
        ))
    )

    3 -> listOf(
        WorkoutDay("Dia 1 | Push", listOf(
            WorkoutExercise("Supino reto", 3, 6, "Peitoral", listOf(
                "Desça controlado e exploda o peso para cima, mantendo cotovelos ligeiramente abertos.",
                "Use toda a amplitude sem sacrificar postura."
            )),
            WorkoutExercise("Supino inclinado c/ alteres", 3, 6, "Peitoral Alto", listOf(
                "Ajuste o banco a 30° para foco no peitoral superior.",
                "Concentre-se na contração no ponto mais alto do movimento."
            )),
            WorkoutExercise("Crucifixo", 3, 6, "Peitoral", listOf(
                "Abra os braços em arco, mantendo leve flexão nos cotovelos.",
                "Traga os halteres até tocarem suavemente à frente do peito."
            )),
            WorkoutExercise("Prensa de ombros", 3, 6, "Ombros", listOf(
                "Empurre a barra acima da cabeça mantendo o core firme.",
                "Não deixe o pescoço tensionar—mantenha olhar para frente."
            )),
            WorkoutExercise("Elevações laterais", 3, 6, "Ombros", listOf(
                "Eleve os braços até a altura dos ombros com leve curva nos cotovelos.",
                "Controle a descida para manter tensão contínua."
            )),
            WorkoutExercise("Puxada frontal", 2, 10, "Ombros", listOf(
                "Use corda ou barra reta e mantenha os cotovelos próximos.",
                "Abra levemente o peito ao puxar para sentir deltóides frontais."
            )),
            WorkoutExercise("Extensão de tríceps c/ corda", 3, 10, "Tríceps", listOf(
                "Separe as pontas da corda no final para máxima ativação lateral.",
                "Mantenha os cotovelos imóveis ao lado do corpo."
            )),
            WorkoutExercise("Extensão de tríceps acima da cabeça", 3, 10, "Tríceps Longo", listOf(
                "Mantenha o tronco ereto e os cotovelos apontados para frente.",
                "Faça o movimento de forma lenta tanto na subida quanto na descida."
            ))
        )),
        WorkoutDay("Dia 2 | Pull", listOf(
            WorkoutExercise("Elevações", 3, 6, "Costas", listOf(
                "Puxe com as costas, não com os braços; concentre-se na escápula.",
                "Desça até braços quase estendidos para máxima amplitude."
            )),
            WorkoutExercise("Remada curvada c/ barra", 3, 6, "Costas", listOf(
                "Mantenha o tronco fixo e puxe a barra ao abdômen.",
                "Sinta a contração entre as escápulas no topo."
            )),
            WorkoutExercise("Pulldown na máquina", 3, 6, "Costas", listOf(
                "Use pegada larga para maior envolvimento do dorsal.",
                "Puxe até a base do peito, mantendo tronco estável."
            )),
            WorkoutExercise("Bíceps com alteres alternado", 4, 10, "Bíceps", listOf(
                "Estabilize o cotovelo e gire o pulso para máxima supinação.",
                "Faça a fase negativa de forma controlada."
            )),
            WorkoutExercise("Rosca direta", 4, 10, "Bíceps", listOf(
                "Mantenha ombros baixos e cotovelos presos ao tronco.",
                "Levante sem balançar o corpo."
            ))
        )),
        WorkoutDay("Dia 3 | Legs", listOf(
            WorkoutExercise("Agachamento", 3, 6, "Quadríceps", listOf(
                "Mantenha os calcanhares firmes e joelhos alinhados.",
                "Desça até formação de ângulo de 90° ou mais."
            )),
            WorkoutExercise("Stiff Leg Deadlift", 3, 6, "Posterior de Coxa", listOf(
                "Mantenha coluna neutra e leve o quadril para trás.",
                "Sinta o alongamento nos isquiotibiais antes de retornar."
            )),
            WorkoutExercise("Extensão de pernas", 4, 10, "Quadríceps", listOf(
                "Suba até contração máxima e prenda por 1–2s.",
                "Controle o retorno para proteger os joelhos."
            )),
            WorkoutExercise("Prensa de pernas", 2, 6, "Quadríceps", listOf(
                "Empurre com calcanhares evitando empenar os joelhos para dentro.",
                "Mantenha o tronco apoiado no encosto."
            ))
        ))
    )
    4 -> listOf(
        WorkoutDay("Dia 1 | Push", listOf(
            WorkoutExercise("Supino reto", 3, 6, "Peitoral", listOf(
                "Empurre explosivamente mantendo os ombros para baixo.",
                "Desça controlado até quase tocar o peito e suba sem acelerar."
            )),
            WorkoutExercise("Supino inclinado c/ alteres", 3, 8, "Peitoral Alto", listOf(
                "Ângulo de 30° para maximizar o foco no peitoral superior.",
                "Mantenha os halteres alinhados ao seu peito no topo."
            )),
            WorkoutExercise("Desenvolvimento militar", 3, 6, "Ombros", listOf(
                "Mantenha o core firme e os cotovelos ligeiramente à frente.",
                "Empurre até completa extensão sem hiperextender a lombar."
            )),
            WorkoutExercise("Elevações laterais", 3, 10, "Ombros Laterais", listOf(
                "Leve os halteres até a altura dos ombros com leve curva nos cotovelos.",
                "Controle a descida para manter tensão no deltóide."
            )),
            WorkoutExercise("Tríceps na polia alta", 3, 10, "Tríceps", listOf(
                "Mantenha os cotovelos imóveis ao lado do tronco.",
                "Estenda totalmente com uma leve pausa no final."
            ))
        )),
        WorkoutDay("Dia 2 | Pull", listOf(
            WorkoutExercise("Pull-up", 3, 6, "Costas", listOf(
                "Puxe o queixo acima da barra focando no latissimus.",
                "Desça controlado até braço quase estendido."
            )),
            WorkoutExercise("Remada unilateral c/ halter", 3, 8, "Costas", listOf(
                "Mantenha coluna neutra e puxe o peso até o quadril.",
                "Sinta a escápula retraindo no topo do movimento."
            )),
            WorkoutExercise("Pulldown na barra V", 3, 8, "Costas Inferiores", listOf(
                "Use pegada neutra e puxe até a base do peito.",
                "Evite balançar o tronco para não diminuir o foco muscular."
            )),
            WorkoutExercise("Rosca martelo", 3, 10, "Bíceps e Braço", listOf(
                "Mantenha punhos neutros para ativar braquiorradiais.",
                "Faça a fase negativa de forma lenta e controlada."
            ))
        )),
        WorkoutDay("Dia 3 | Legs", listOf(
            WorkoutExercise("Agachamento livre", 3, 6, "Quadríceps", listOf(
                "Mantenha os calcanhares firmes e joelhos alinhados aos pés.",
                "Desça até quadril abaixo dos joelhos e suba com explosão."
            )),
            WorkoutExercise("Leg press", 3, 8, "Quadríceps", listOf(
                "Posicione os pés na largura dos ombros e empurre pelos calcanhares.",
                "Controle o retorno sem trancar os joelhos."
            )),
            WorkoutExercise("Stiff leg deadlift", 3, 8, "Posterior de Coxa", listOf(
                "Mantenha a barra próxima aos pés para reduzir lombar.",
                "Desça até sentir bom alongamento nos isquiotibiais."
            )),
            WorkoutExercise("Panturrilha em pé", 4, 12, "Panturrilhas", listOf(
                "Faça amplitude total estendendo o tornozelo ao máximo.",
                "Execute repetições lentas para evitar impulso."
            ))
        )),
        WorkoutDay("Dia 4 | Full Body", listOf(
            WorkoutExercise("Levantamento terra", 3, 5, "Posterior e Core", listOf(
                "Mantenha costas retas e peso próximo ao corpo.",
                "Engaje glúteos e erga o quadril suavemente."
            )),
            WorkoutExercise("Barbell row", 3, 6, "Costas", listOf(
                "Use pegada pronada e puxe até o umbigo.",
                "Mantenha ombros retraídos o tempo todo."
            )),
            WorkoutExercise("Supino reto", 3, 6, "Peitoral", listOf(
                "Foque em empurrar a barra de maneira uniforme.",
                "Não deixe o torso “saltar” na subida."
            )),
            WorkoutExercise("Agachamento frontal", 3, 6, "Quadríceps", listOf(
                "Mantenha cotovelos altos para suportar a barra.",
                "Desça com tronco ereto para proteger a coluna."
            ))
        ))
    )

    5 -> listOf(
        WorkoutDay("Dia 1 | Push A", listOf(
            WorkoutExercise("Supino reto", 3, 6, "Peitoral", listOf(
                "Use pegada um pouco mais larga para foco no peito externo.",
                "Mantenha a escápula retraída durante toda a execução."
            )),
            WorkoutExercise("Desenvolvimento Arnold", 3, 8, "Ombros", listOf(
                "Gire os pulsos durante o movimento para máxima ativação.",
                "Controle cada fase para manter tensão."
            )),
            WorkoutExercise("Mergulho em paralelas", 3, 8, "Peitoral e Tríceps", listOf(
                "Incline o tronco ligeiramente para frente.",
                "Pernas juntas e movimento suave sem balanço."
            ))
        )),
        WorkoutDay("Dia 2 | Pull A", listOf(
            WorkoutExercise("Pull-up pronado", 3, 6, "Costas", listOf(
                "Estenda totalmente os braços na fase negativa.",
                "Evite usar impulso ao subir."
            )),
            WorkoutExercise("Remada curvada", 3, 6, "Costas", listOf(
                "Mantenha tronco fixo e puxe com força do cotovelo.",
                "Segure 1s na contração máxima."
            )),
            WorkoutExercise("Face pull", 3, 12, "Deltoides Posteriores", listOf(
                "Puxe a corda ao nível do rosto abrindo os cotovelos.",
                "Mantenha ombros baixos."
            ))
        )),
        WorkoutDay("Dia 3 | Legs", listOf(
            WorkoutExercise("Agachamento sumô", 3, 8, "Adutores", listOf(
                "Posição ampla com pés apontando para fora.",
                "Desça até sentir alongamento interno."
            )),
            WorkoutExercise("Avanço com halteres", 3, 10, "Quadríceps", listOf(
                "Dê passo largo e mantenha tronco ereto.",
                "Empurre o calcanhar da frente ao subir."
            )),
            WorkoutExercise("Flexão plantar sentado", 4, 12, "Panturrilhas", listOf(
                "Pressione até extensão total para ativar o gastrocnêmio.",
                "Mantenha ritmo controlado."
            ))
        )),
        WorkoutDay("Dia 4 | Push B", listOf(
            WorkoutExercise("Supino inclinado", 3, 6, "Peitoral Superior", listOf(
                "Use pegada média e desça até sentir alongamento leve.",
                "Suba explosivo sem tirar ombros do banco."
            )),
            WorkoutExercise("Elevação frontal", 3, 10, "Deltoides Anteriores", listOf(
                "Eleve até a altura dos olhos, mantendo braços retos.",
                "Não force os pulsos para cima."
            )),
            WorkoutExercise("Tríceps testa", 3, 10, "Tríceps", listOf(
                "Mantenha cotovelos fixos e abaixe a barra lentamente.",
                "Volte à posição inicial sem travar totalmente."
            ))
        )),
        WorkoutDay("Dia 5 | Pull B", listOf(
            WorkoutExercise("Remada na máquina", 3, 8, "Costas", listOf(
                "Encoste o peito no suporte e puxe até o umbigo.",
                "Concentre-se na contração das escápulas."
            )),
            WorkoutExercise("Rosca direta barra W", 3, 10, "Bíceps", listOf(
                "Pegada supinada e cotovelos imóveis.",
                "Faça pausa no topo para maior pico de contração."
            ))
        ))
    )

    6 -> listOf(
        WorkoutDay("Dia 1 | Peito", listOf(
            WorkoutExercise("Supino reto", 4, 6, "Peitoral", listOf(
                "Use pegada firme e desça controlado.",
                "Exploda a barra para cima sentindo o peito contrair."
            )),
            WorkoutExercise("Crucifixo inclinado", 3, 10, "Peitoral Superior", listOf(
                "Banco a 45° para foco no peitoral superior.",
                "Mantenha cotovelos levemente flexionados."
            )),
            WorkoutExercise("Mergulho", 3, 8, "Peitoral Inferior", listOf(
                "Incline o tronco para frente para maior ênfase no peito.",
                "Desça até formar um ângulo de 90° no cotovelo."
            ))
        )),
        WorkoutDay("Dia 2 | Costas", listOf(
            WorkoutExercise("Pull-up", 4, 6, "Costas", listOf(
                "Puxe o peito até a barra mantendo tronco firme.",
                "Desça lento e total na fase excêntrica."
            )),
            WorkoutExercise("Remada T-Bar", 3, 8, "Costas Médias", listOf(
                "Use pegada neutra e mantenha tronco estável.",
                "Puxe até sentir as escápulas se aproximarem."
            )),
            WorkoutExercise("Pullover c/ halter", 3, 10, "Costas e Peitoral", listOf(
                "Mantenha o peito apoiado no banco e braços retos.",
                "Sinta alongamento profundo nos dorsais."
            ))
        )),
        WorkoutDay("Dia 3 | Pernas A", listOf(
            WorkoutExercise("Agachamento", 3, 6, "Quadríceps", listOf(
                "Mantenha calcanhares firmes e olhar à frente.",
                "Desça até ângulo de 90° e suba explosivamente."
            )),
            WorkoutExercise("Stiff leg deadlift", 3, 8, "Posterior de Coxa", listOf(
                "Mantenha costas neutras e abaixe o peso controlado.",
                "Contraia glúteos no topo."
            )),
            WorkoutExercise("Extensão de pernas", 3, 12, "Quadríceps", listOf(
                "Suba até impedir a extensão total por 1s.",
                "Controle o retorno para proteger os joelhos."
            ))
        )),
        WorkoutDay("Dia 4 | Ombros", listOf(
            WorkoutExercise("Desenvolvimento militar sentado", 3, 6, "Ombros", listOf(
                "Empurre mantendo tronco apoiado no encosto.",
                "Não deixe o peito sobressair à frente."
            )),
            WorkoutExercise("Elevação lateral", 3, 10, "Deltoides", listOf(
                "Eleve até a altura dos ombros sem balançar o corpo.",
                "Desça controlado mantendo tensão."
            )),
            WorkoutExercise("Elevação frontal", 3, 10, "Deltoides Anteriores", listOf(
                "Use halter ou barra curta e levante no plano frontal.",
                "Mantenha core firme para evitar balanço."
            ))
        )),
        WorkoutDay("Dia 5 | Pernas B", listOf(
            WorkoutExercise("Leg press", 3, 6, "Quadríceps", listOf(
                "Pés na largura dos ombros e empurre pelos calcanhares.",
                "Controle a descida sem trancar joelhos."
            )),
            WorkoutExercise("Afundo búlgaro", 3, 8, "Quadríceps e Glúteos", listOf(
                "Mantenha tronco ereto e joelho da frente alinhado ao pé.",
                "Desça até 90° e suba sem impulsionar."
            )),
            WorkoutExercise("Mesa flexora", 3, 10, "Posterior de Coxa", listOf(
                "Faça contração total no topo e controle a fase negativa.",
                "Mantenha quadril firme no banco."
            ))
        )),
        WorkoutDay("Dia 6 | Braços", listOf(
            WorkoutExercise("Rosca direta", 3, 10, "Bíceps", listOf(
                "Mantenha punhos supinados e cotovelos imóveis.",
                "Controle a fase excêntrica para maximizar tensão."
            )),
            WorkoutExercise("Rosca martelo", 3, 10, "Bíceps e Antebraço", listOf(
                "Punhos neutros e cotovelos próximos ao corpo.",
                "Levante até a altura dos ombros e desça lento."
            )),
            WorkoutExercise("Tríceps testa", 3, 10, "Tríceps", listOf(
                "Mantenha cotovelos estáticos e barra próxima à testa.",
                "Estenda totalmente e segure 1s no topo."
            )),
            WorkoutExercise("Mergulho entre bancos", 3, 12, "Tríceps", listOf(
                "Mantenha pernas estendidas e desça até 90°.",
                "Empurre concentrando a força no tríceps."
            ))
        ))
    )

    else -> emptyList()
}

