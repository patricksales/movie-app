# MovieApp - Aplicativo de Filmes

Aplicativo Android para consulta de filmes populares, utilizando a API do TheMovieDB (TMDB). O projeto demonstra boas práticas de desenvolvimento Android com arquitetura limpa, segurança e testes.

## Funcionalidades

### Autenticação e Segurança
- Login com usuário e senha (mockado: `admin` / `1234`)
- Autenticação biométrica (Fingerprint/FaceID) para acessos subsequentes
- Armazenamento seguro de credenciais com **EncryptedSharedPreferences** e **Android Keystore**

### Home - Filmes Populares
- Listagem em grid/lista dos filmes populares do TMDB
- Paginação com rolagem infinita (Infinite Scroll)
- Barra de pesquisa com debounce para filtrar filmes por título
- Tratamento visual de estados: Loading, Erro e Lista Vazia

### Detalhes do Filme
- Poster em tamanho grande, título, nota (rating) e sinopse
- Gêneros exibidos por extenso (mapeamento de IDs via endpoint `/genre/movie/list`)
- Botão para favoritar/desfavoritar
- Botão para compartilhar o link do filme

### Favoritos (Offline First)
- Persistência local com Room Database
- Estado de favorito sincronizado entre todas as telas
- Aba dedicada para filmes favoritados
- Funciona offline

## Arquitetura

O projeto segue os princípios de **Clean Architecture** com **MVVM**:

```

com/movieapp/
├── core/
│   ├── data/local/       (MovieDatabase, DAO, Entity)
│   ├── data/remote/      (TmdbApi, DTOs)
│   ├── di/               (AppModules - network, database, security)
│   ├── navigation/       (AppNavigation, Screen)
│   ├── security/         (BiometricHelper, SecurePreferences)
│   └── ui/               (components + theme)
├── features/
│   ├── auth/
│   │   ├── data/repository/
│   │   ├── di/AuthModule.kt
│   │   ├── domain/repository/
│   │   └── presentation/ (ui + viewmodel)
│   ├── home/
│   │   ├── data/repository/
│   │   ├── di/HomeModule.kt
│   │   ├── domain/ (model, data/repository, usecase)
│   │   └── presentation/ (ui + viewmodel)
│   ├── moviedetail/
│   │   ├── di/MovieDetailModule.kt
│   │   ├── domain/ (model, usecase)
│   │   └── presentation/ (ui + viewmodel)
│   └── favorites/
│       ├── di/FavoritesModule.kt
│       ├── domain/usecase/
│       └── presentation/ (ui + viewmodel)

```

### Decisões de Arquitetura

| Decisão | Justificativa |
|---------|--------------|
| **MVVM com StateFlow** | Reatividade nativa do Kotlin, lifecycle-aware |
| **Clean Architecture** | Separação clara de responsabilidades, testabilidade |
| **Koin** | Injeção de dependência leve e idiomática em Kotlin (DSL), fácil de configurar e integrar com Compose |
| **Retrofit** | Cliente HTTP robusto e amplamente utilizado |
| **Room** | ORM oficial do Android, suporte a Flow para reatividade |
| **Jetpack Compose** | UI declarativa moderna, menos boilerplate |
| **Coil** | Carregamento de imagens otimizado para Compose |
| **EncryptedSharedPreferences** | Armazenamento seguro de dados sensíveis |
| **AndroidX Biometric** | API padronizada para autenticação biométrica |

## Configuração

### Pré-requisitos
- Android Studio Hedgehog (2023.1.1) ou superior
- JDK 17
- Android SDK 34

### Configurar a API Key do TMDB

1. Crie uma conta em [https://www.themoviedb.org/](https://www.themoviedb.org/)
2. Acesse [https://www.themoviedb.org/settings/api](https://www.themoviedb.org/settings/api) e obtenha sua API Key (v3 auth)
3. Adicione no arquivo `gradle.properties` (local, não commitado):

```properties
TMDB_API_KEY=sua_api_key_aqui
```

### Configurar Firebase e Google Services

1. Crie um projeto no [Firebase Console](https://console.firebase.google.com/)
2. Registre seu aplicativo Android no console
3. Baixe o arquivo de configuração `google-services.json`
4. **Coloque o arquivo na pasta do módulo app**:

## Monitoramento e Performance

O aplicativo inclui instrumentação para monitoramento de performance via Firebase Performance e captura de crashes via Firebase Crashlytics. Esta funcionalidade ajuda a medir tempos de carregamento (traces customizados), latência de requests e a correlacionar problemas com crashes.

Arquivo principal
- Implementação atual: `app/src/main/java/com/movieapp/features/monitoring/PerformenceMonitoring.kt`

O que ele faz
- Cria traces customizados para medir duração de operações (ex.: `load_movies`).
- Permite criar traces pontuais para requisições de rede (`traceNetworkRequest(name)`).
- O Crashlytics já está habilitado em `MainActivity` (coleta automática de crashes e registro de userId).

Exemplos de uso (invocar nos ViewModels ou repositórios)

- Medir um bloco síncrono/suspenso (trace de carregamento de filmes):
```kotlin
PerformanceMonitoring.traceMovieLoading {
    // chamada suspensa que carrega filmes (ex.: repository.fetchPopularMovies())
}
```

### Build e Execução

```bash
# Clone o repositório
git clone <repo-url>
cd <repo-name>

# Build do projeto
./gradlew assembleDebug

# Executar testes unitários
./gradlew testDebugUnitTest
```

## Testar o Fluxo de Biometria

1. **Emulador**: Acesse Settings > Security > Fingerprint e configure uma digital
2. **Primeiro acesso**: Faça login com `admin` / `1234`
3. **Diálogo**: Após login bem-sucedido, o app perguntará se deseja ativar biometria
4. **Segundo acesso**: Ao reabrir o app, a biometria será solicitada automaticamente
5. **Fallback**: Caso a biometria falhe, o botão "Usar senha" permite login manual

## Stack Técnica

| Categoria | Tecnologia |
|-----------|-----------|
| Linguagem | Kotlin |
| UI | Jetpack Compose |
| Arquitetura | MVVM + Clean Architecture |
| DI | Koin |
| Networking | Retrofit + OkHttp |
| Banco de Dados | Room |
| Imagens | Coil |
| Paginação | Infinite Scroll manual |
| Segurança | AndroidX Biometric + EncryptedSharedPreferences |
| Asincronismo | Coroutines + Flow |
| Testes | JUnit 4 + MockK + Turbine |
| Navegação | Navigation Compose |

## Testes

O projeto inclui testes unitários abrangentes para:

- **ViewModels**: `LoginViewModel`, `HomeViewModel`, `MovieDetailViewModel`, `FavoritesViewModel`
- **Repositories**: `MovieRepositoryImpl`, `AuthRepositoryImpl`

```bash
# Executar todos os testes
./gradlew testDebugUnitTest

# Executar teste específico
./gradlew testDebugUnitTest --tests "com.movieapp.ui.home.HomeViewModelTest"
```

### Cobertura dos Testes

- Login com credenciais corretas/incorretas
- Ativação e uso de biometria
- Carregamento de filmes populares e tratamento de erros
- Paginação e busca com debounce
- Mapeamento de gêneros (IDs para nomes)
- Sincronização de estado de favoritos
- Persistência offline de favoritos
- Toggle de favorito (inserção/remoção)
- Cache de gêneros

## API Endpoints Utilizados

| Endpoint | Descrição |
|----------|-----------|
| `GET /movie/popular` | Filmes populares (paginado) |
| `GET /search/movie` | Busca de filmes por título |
| `GET /genre/movie/list` | Lista de gêneros |
| `GET /movie/{movie_id}` | Detalhes de um filme |

## Uso de IA

Este projeto utilizou IA para:
- Criação dos testes unitários
- Documentação do README
