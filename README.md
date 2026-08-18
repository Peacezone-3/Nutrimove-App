# NutriMove App

NutriMove é uma aplicação Android desenvolvida em Kotlin com Jetpack Compose, criada com o objetivo de apoiar utilizadores na gestão de hábitos relacionados com nutrição e treino físico.

O projeto combina funcionalidades de acompanhamento alimentar, planos de treino e uma experiência inicial de onboarding, permitindo ao utilizador personalizar a aplicação de acordo com o seu perfil e objetivos.

## Objetivo do projeto

Este projeto foi desenvolvido como aplicação prática de desenvolvimento mobile, com foco em:

- criação de interfaces modernas com Jetpack Compose;
- navegação entre ecrãs;
- gestão de preferências do utilizador;
- organização modular do código;
- integração com APIs externas;
- aplicação de conceitos relacionados com saúde, nutrição e atividade física.

## Funcionalidades principais

- Onboarding inicial do utilizador
- Gestão de preferências com DataStore
- Secção de nutrição
- Criação e visualização de planos alimentares
- Detalhes de refeições e planos nutricionais
- Secção de treinos
- Visualização de planos de treino por dia
- Detalhes de exercícios
- Navegação entre ecrãs com Navigation Compose
- Integração com API externa através de Retrofit
- Estrutura preparada para integração com serviços de IA

## Tecnologias utilizadas

- Kotlin
- Android Studio
- Jetpack Compose
- Material 3
- Navigation Compose
- Android DataStore
- Retrofit
- Gson Converter
- Lottie Compose
- Gradle

## Estrutura do projeto

```text
Nutrimove-App/
├── app/
│   └── src/
│       └── main/
│           └── java/
│               └── com/
│                   └── nutrimove/
│                       ├── ai/
│                       │   ├── OpenAIService.kt
│                       │   └── RetrofitClient.kt
│                       ├── data/
│                       │   └── UserPreferences.kt
│                       ├── ui/
│                       │   ├── components/
│                       │   ├── navigation/
│                       │   ├── screens/
│                       │   │   ├── nutrition/
│                       │   │   ├── onboarding/
│                       │   │   ├── profile/
│                       │   │   └── treinos/
│                       │   └── theme/
│                       ├── MainActivity.kt
│                       └── SplashActivity.kt
├── build.gradle
├── settings.gradle
└── README.md
```

# Principais áreas da aplicação
## Onboarding
A aplicação inclui um fluxo inicial de onboarding, utilizado para recolher ou configurar informações relevantes do utilizador antes de entrar na aplicação principal.

O estado do onboarding é guardado através de DataStore, permitindo que a aplicação saiba se o utilizador já completou esta etapa.

## Nutrição
A secção de nutrição permite consultar e gerir conteúdos relacionados com planos alimentares. Inclui ecrãs para listagem, detalhe e adição de planos de refeição.

## Treinos
A secção de treinos permite visualizar planos de treino, consultar exercícios e organizar informação por dias ou categorias.

## Integração com API
O projeto inclui uma camada preparada para comunicação com serviços externos através de Retrofit, incluindo ficheiros dedicados à integração com API/IA.

# Como executar o projeto
## Pré-requisitos
Antes de executar o projeto, é necessário ter instalado:

Android Studio
JDK 11 ou superior
Gradle
Emulador Android ou dispositivo físico

## Passos para correr a aplicação
Clonar o repositório:

git clone https://github.com/Peacezone-3/Nutrimove-App.git

Abrir o projeto no Android Studio.
Sincronizar o projeto com o Gradle.
Configurar a API key, caso seja necessário utilizar funcionalidades relacionadas com IA.
No ficheiro gradle.properties, adicionar:

openai.api.key=SUA_API_KEY_AQUI

Executar a aplicação num emulador ou dispositivo Android.

## Configuração da API Key
O projeto utiliza a propriedade openai.api.key para disponibilizar a chave através do BuildConfig.

Exemplo no gradle.properties:

openai.api.key=SUA_API_KEY_AQUI


# Aprendizagens desenvolvidas
Durante o desenvolvimento deste projeto foram aplicados e reforçados conhecimentos em:

desenvolvimento Android com Kotlin;
criação de UI declarativa com Jetpack Compose;
utilização de Material 3;
navegação entre ecrãs;
persistência local de preferências;
organização de código por camadas e funcionalidades;
consumo de APIs com Retrofit;
estruturação de uma aplicação mobile com foco na experiência do utilizador.

# Melhorias futuras
Algumas funcionalidades que poderão ser adicionadas futuramente:

autenticação de utilizador;
base de dados local com Room;
sincronização com backend;
histórico de treinos e refeições;
dashboards de progresso;
cálculo automático de calorias e macronutrientes;
recomendações personalizadas com IA;
testes unitários e instrumentados;
melhoria da arquitetura com MVVM mais estruturado;
publicação de versão APK/release.

# Autores
Desenvolvido por Alexandre Fonseca e Pedro Silva.

GitHub: Peacezone-3
