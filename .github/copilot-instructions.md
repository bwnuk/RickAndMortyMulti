# Copilot Instructions for Rick and Morty CMP Project

## Project Overview

This is a Compose Multiplatform (CMP) project targeting Android and iOS.
We fetch data from Rick and Morty API: https://rickandmortyapi.com/api

## Application Description

This is a character browser application with the following features:

Main Features:
- Character list with infinite scroll pagination
- Character detail screen with photo and full information
- Favorite characters functionality with local storage
- Search characters by name

Navigation Structure:
- Bottom Navigation with 3 tabs
- Home tab: Character list with pagination on scroll
- Favorites tab: Locally saved favorite characters
- Search tab: Search view to find characters by name

User Flows:
- User opens app and sees character list on Home tab
- User scrolls down to load more characters (pagination)
- User taps on character card to see detail screen
- User can add/remove character from favorites
- User switches to Favorites tab to see saved characters
- User switches to Search tab to find specific character

## Technology Stack

- Compose Multiplatform (latest stable)
- Ktor for networking
- Koin for dependency injection
- kotlinx.serialization for JSON parsing
- Coil 3 for image loading
- Navigation Compose (multiplatform from JetBrains)
- Lifecycle ViewModel (multiplatform from JetBrains)
- Room or SQLDelight for local database (favorites)

## Architecture

We follow Clean Architecture with MVVM pattern.

### Layer Structure

```
composeApp/
└── src/
    ├── commonMain/kotlin/
    │   └── org/example/rickandmorty/
    │       ├── data/
    │       │   ├── remote/
    │       │   │   ├── api/
    │       │   │   │   └── RickAndMortyApi.kt
    │       │   │   └── dto/
    │       │   │       ├── CharacterDto.kt
    │       │   │       ├── LocationDto.kt
    │       │   │       └── ApiResponseDto.kt
    │       │   ├── local/
    │       │   │   ├── database/
    │       │   │   │   └── AppDatabase.kt
    │       │   │   ├── dao/
    │       │   │   │   └── FavoriteCharacterDao.kt
    │       │   │   └── entity/
    │       │   │       └── FavoriteCharacterEntity.kt
    │       │   ├── repository/
    │       │   │   ├── CharacterRepositoryImpl.kt
    │       │   │   └── FavoriteRepositoryImpl.kt
    │       │   └── mapper/
    │       │       └── CharacterMapper.kt
    │       ├── domain/
    │       │   ├── model/
    │       │   │   ├── Character.kt
    │       │   │   └── Location.kt
    │       │   ├── repository/
    │       │   │   ├── CharacterRepository.kt
    │       │   │   └── FavoriteRepository.kt
    │       │   └── usecase/
    │       │       ├── GetCharactersUseCase.kt
    │       │       ├── GetCharacterByIdUseCase.kt
    │       │       ├── SearchCharactersUseCase.kt
    │       │       ├── GetFavoritesUseCase.kt
    │       │       ├── AddToFavoritesUseCase.kt
    │       │       ├── RemoveFromFavoritesUseCase.kt
    │       │       └── IsFavoriteUseCase.kt
    │       ├── presentation/
    │       │   ├── screens/
    │       │   │   ├── home/
    │       │   │   │   ├── HomeScreen.kt
    │       │   │   │   ├── HomeViewModel.kt
    │       │   │   │   └── HomeUiState.kt
    │       │   │   ├── favorites/
    │       │   │   │   ├── FavoritesScreen.kt
    │       │   │   │   ├── FavoritesViewModel.kt
    │       │   │   │   └── FavoritesUiState.kt
    │       │   │   ├── search/
    │       │   │   │   ├── SearchScreen.kt
    │       │   │   │   ├── SearchViewModel.kt
    │       │   │   │   └── SearchUiState.kt
    │       │   │   └── detail/
    │       │   │       ├── CharacterDetailScreen.kt
    │       │   │       ├── CharacterDetailViewModel.kt
    │       │   │       └── CharacterDetailUiState.kt
    │       │   ├── components/
    │       │   │   ├── CharacterCard.kt
    │       │   │   ├── FavoriteButton.kt
    │       │   │   ├── LoadingIndicator.kt
    │       │   │   ├── ErrorView.kt
    │       │   │   ├── SearchBar.kt
    │       │   │   └── BottomNavigationBar.kt
    │       │   ├── navigation/
    │       │   │   ├── NavGraph.kt
    │       │   │   ├── Screen.kt
    │       │   │   └── BottomNavItem.kt
    │       │   └── theme/
    │       │       ├── Theme.kt
    │       │       ├── Color.kt
    │       │       └── Type.kt
    │       ├── di/
    │       │   ├── NetworkModule.kt
    │       │   ├── DatabaseModule.kt
    │       │   ├── RepositoryModule.kt
    │       │   ├── UseCaseModule.kt
    │       │   ├── ViewModelModule.kt
    │       │   └── AppModule.kt
    │       └── App.kt
    ├── androidMain/kotlin/
    │   └── org/example/rickandmorty/
    │       ├── MainActivity.kt
    │       └── MainApplication.kt
    └── iosMain/kotlin/
        └── org/example/rickandmorty/
            └── MainViewController.kt
```

### Layer Responsibilities

Data Layer:
- Contains DTOs that match API JSON structure
- Contains repository implementations
- Contains mappers to convert DTOs to domain models
- Contains local database (Room/SQLDelight) for favorites
- Handles all network operations with Ktor

Domain Layer:
- Contains pure Kotlin domain models
- Contains repository interfaces
- Contains use cases with single responsibility
- No dependencies on data or presentation layers

Presentation Layer:
- Contains ViewModels that hold UI state
- Contains Compose screens and components
- Contains navigation logic
- Contains bottom navigation setup
- Depends on domain layer only

## Coding Conventions

### General Kotlin Rules

Always use:
- data class for DTOs and domain models
- sealed interface for UI states and events
- object for singletons like API endpoints
- internal visibility unless public is required
- explicit return types for public functions

Never use:
- var unless absolutely necessary
- !! operator, use safe calls instead
- lateinit for nullable types
- Any platform-specific code in commonMain

### Naming Conventions

DTOs:

```kotlin
@Serializable
data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String
)
```

Domain Models:

```kotlin
data class Character(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val imageUrl: String,
    val isFavorite: Boolean = false
)
```

Repository Interface:

```kotlin
interface CharacterRepository {
    suspend fun getCharacters(page: Int): Result<List<Character>>
    suspend fun getCharacterById(id: Int): Result<Character>
    suspend fun searchCharacters(query: String, page: Int): Result<List<Character>>
}
```

Repository Implementation:

```kotlin
internal class CharacterRepositoryImpl(
    private val api: RickAndMortyApi,
    private val mapper: CharacterMapper
) : CharacterRepository {
    override suspend fun getCharacters(page: Int): Result<List<Character>> {
        // implementation
    }
}
```

Use Cases:

```kotlin
class GetCharactersUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(page: Int): Result<List<Character>> {
        return repository.getCharacters(page)
    }
}
```

Favorite Use Cases:

```kotlin
class AddToFavoritesUseCase(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(character: Character) {
        repository.addToFavorites(character)
    }
}

class RemoveFromFavoritesUseCase(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(characterId: Int) {
        repository.removeFromFavorites(characterId)
    }
}

class IsFavoriteUseCase(
    private val repository: FavoriteRepository
) {
    operator fun invoke(characterId: Int): Flow<Boolean> {
        return repository.isFavorite(characterId)
    }
}
```

### UI State Pattern

Always use sealed interface for UI states:

```kotlin
sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(
        val characters: List<Character>,
        val isLoadingMore: Boolean = false,
        val canLoadMore: Boolean = true
    ) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

sealed interface FavoritesUiState {
    data object Loading : FavoritesUiState
    data class Success(val favorites: List<Character>) : FavoritesUiState
    data object Empty : FavoritesUiState
}

sealed interface SearchUiState {
    data object Idle : SearchUiState
    data object Loading : SearchUiState
    data class Success(val results: List<Character>) : SearchUiState
    data object Empty : SearchUiState
    data class Error(val message: String) : SearchUiState
}
```

### ViewModel Pattern

```kotlin
class HomeViewModel(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var currentPage = 1

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            getCharactersUseCase(currentPage)
                .onSuccess { characters ->
                    _uiState.value = HomeUiState.Success(characters = characters)
                }
                .onFailure { error ->
                    _uiState.value = HomeUiState.Error(
                        message = error.message ?: "Unknown error occurred"
                    )
                }
        }
    }

    fun loadMore() {
        val currentState = _uiState.value
        if (currentState is HomeUiState.Success && !currentState.isLoadingMore && currentState.canLoadMore) {
            viewModelScope.launch {
                _uiState.value = currentState.copy(isLoadingMore = true)
                currentPage++
                getCharactersUseCase(currentPage)
                    .onSuccess { newCharacters ->
                        _uiState.value = currentState.copy(
                            characters = currentState.characters + newCharacters,
                            isLoadingMore = false,
                            canLoadMore = newCharacters.isNotEmpty()
                        )
                    }
                    .onFailure {
                        currentPage--
                        _uiState.value = currentState.copy(isLoadingMore = false)
                    }
            }
        }
    }

    fun retry() {
        loadCharacters()
    }
}
```

### Compose Screen Pattern - State Hoisting

CRITICAL: Screens must be completely stateless. They receive state and callbacks as parameters. ViewModel is connected at the navigation layer, NOT inside the screen.

Stateless Screen:

```kotlin
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onCharacterClick: (Int) -> Unit,
    onFavoriteClick: (Character) -> Unit,
    onLoadMore: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is HomeUiState.Loading -> LoadingIndicator(modifier = modifier)
        is HomeUiState.Success -> CharacterList(
            characters = uiState.characters,
            isLoadingMore = uiState.isLoadingMore,
            onCharacterClick = onCharacterClick,
            onFavoriteClick = onFavoriteClick,
            onLoadMore = onLoadMore,
            modifier = modifier
        )
        is HomeUiState.Error -> ErrorView(
            message = uiState.message,
            onRetry = onRetry,
            modifier = modifier
        )
    }
}
```

ViewModel Connection in Navigation (the correct place):

```kotlin
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            val viewModel: HomeViewModel = koinViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            HomeScreen(
                uiState = uiState,
                onCharacterClick = { characterId ->
                    navController.navigate(Screen.CharacterDetail.createRoute(characterId))
                },
                onFavoriteClick = viewModel::toggleFavorite,
                onLoadMore = viewModel::loadMore,
                onRetry = viewModel::retry
            )
        }

        composable(Screen.Favorites.route) {
            val viewModel: FavoritesViewModel = koinViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            FavoritesScreen(
                uiState = uiState,
                onCharacterClick = { characterId ->
                    navController.navigate(Screen.CharacterDetail.createRoute(characterId))
                },
                onRemoveFavorite = viewModel::removeFavorite
            )
        }

        composable(Screen.Search.route) {
            val viewModel: SearchViewModel = koinViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

            SearchScreen(
                uiState = uiState,
                searchQuery = searchQuery,
                onSearchQueryChange = viewModel::onSearchQueryChange,
                onCharacterClick = { characterId ->
                    navController.navigate(Screen.CharacterDetail.createRoute(characterId))
                }
            )
        }

        composable(
            route = Screen.CharacterDetail.route,
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId") ?: return@composable
            val viewModel: CharacterDetailViewModel = koinViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(characterId) {
                viewModel.loadCharacter(characterId)
            }

            CharacterDetailScreen(
                uiState = uiState,
                onBackClick = { navController.popBackStack() },
                onFavoriteClick = viewModel::toggleFavorite,
                onRetry = { viewModel.loadCharacter(characterId) }
            )
        }
    }
}
```

Why this pattern is correct:
- Screens are completely stateless and reusable
- Easy to create previews with fake data
- Easy to unit test without mocking ViewModels
- State hoisting is properly applied
- ViewModel is connected at the "edge" (navigation layer)

### Screen Preview Pattern

Because screens are stateless, previews are easy to create:

```kotlin
@Preview
@Composable
private fun HomeScreenLoadingPreview() {
    AppTheme {
        HomeScreen(
            uiState = HomeUiState.Loading,
            onCharacterClick = {},
            onFavoriteClick = {},
            onLoadMore = {},
            onRetry = {}
        )
    }
}

@Preview
@Composable
private fun HomeScreenSuccessPreview() {
    AppTheme {
        HomeScreen(
            uiState = HomeUiState.Success(
                characters = listOf(
                    Character(
                        id = 1,
                        name = "Rick Sanchez",
                        status = CharacterStatus.ALIVE,
                        species = "Human",
                        imageUrl = "",
                        isFavorite = false
                    ),
                    Character(
                        id = 2,
                        name = "Morty Smith",
                        status = CharacterStatus.ALIVE,
                        species = "Human",
                        imageUrl = "",
                        isFavorite = true
                    )
                ),
                isLoadingMore = false,
                canLoadMore = true
            ),
            onCharacterClick = {},
            onFavoriteClick = {},
            onLoadMore = {},
            onRetry = {}
        )
    }
}

@Preview
@Composable
private fun HomeScreenErrorPreview() {
    AppTheme {
        HomeScreen(
            uiState = HomeUiState.Error(message = "Failed to load characters"),
            onCharacterClick = {},
            onFavoriteClick = {},
            onLoadMore = {},
            onRetry = {}
        )
    }
}
```

### Pagination with LazyColumn

```kotlin
@Composable
fun CharacterList(
    characters: List<Character>,
    isLoadingMore: Boolean,
    onCharacterClick: (Int) -> Unit,
    onFavoriteClick: (Character) -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex != null && lastVisibleIndex >= characters.size - 3) {
                    onLoadMore()
                }
            }
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = characters,
            key = { it.id }
        ) { character ->
            CharacterCard(
                character = character,
                onClick = { onCharacterClick(character.id) },
                onFavoriteClick = { onFavoriteClick(character) }
            )
        }

        if (isLoadingMore) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
```

### Bottom Navigation Pattern

```kotlin
sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : BottomNavItem(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )
    data object Favorites : BottomNavItem(
        route = "favorites",
        title = "Favorites",
        icon = Icons.Default.Favorite
    )
    data object Search : BottomNavItem(
        route = "search",
        title = "Search",
        icon = Icons.Default.Search
    )
}

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Favorites,
        BottomNavItem.Search
    )

    NavigationBar(modifier = modifier) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = { onNavigate(item.route) }
            )
        }
    }
}
```

### Main App Composable

```kotlin
@Composable
fun App() {
    AppTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val bottomNavRoutes = listOf(
            Screen.Home.route,
            Screen.Favorites.route,
            Screen.Search.route
        )
        val shouldShowBottomBar = currentRoute in bottomNavRoutes

        Scaffold(
            bottomBar = {
                if (shouldShowBottomBar) {
                    BottomNavigationBar(
                        currentRoute = currentRoute,
                        onNavigate = { route ->
                            navController.navigate(route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        ) { paddingValues ->
            NavGraph(
                navController = navController,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}
```

### Favorite Button Component

```kotlin
@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onToggle,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
            tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onSurface
        )
    }
}
```

### Composable Component Pattern

```kotlin
@Composable
fun CharacterCard(
    character: Character,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = character.imageUrl,
                contentDescription = character.name,
                modifier = Modifier.size(64.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = character.status.displayName,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            FavoriteButton(
                isFavorite = character.isFavorite,
                onToggle = onFavoriteClick
            )
        }
    }
}
```

### Koin Dependency Injection

Network Module:

```kotlin
val networkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
            install(Logging) {
                level = LogLevel.BODY
            }
        }
    }

    single { RickAndMortyApi(get()) }
}
```

Database Module:

```kotlin
val databaseModule = module {
    single { provideDatabase() }
    single { get<AppDatabase>().favoriteCharacterDao() }
}
```

Repository Module:

```kotlin
val repositoryModule = module {
    single { CharacterMapper() }
    single<CharacterRepository> { CharacterRepositoryImpl(get(), get()) }
    single<FavoriteRepository> { FavoriteRepositoryImpl(get(), get()) }
}
```

UseCase Module:

```kotlin
val useCaseModule = module {
    factory { GetCharactersUseCase(get()) }
    factory { GetCharacterByIdUseCase(get()) }
    factory { SearchCharactersUseCase(get()) }
    factory { GetFavoritesUseCase(get()) }
    factory { AddToFavoritesUseCase(get()) }
    factory { RemoveFromFavoritesUseCase(get()) }
    factory { IsFavoriteUseCase(get()) }
}
```

ViewModel Module:

```kotlin
val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::CharacterDetailViewModel)
}
```

App Module:

```kotlin
val appModule = listOf(
    networkModule,
    databaseModule,
    repositoryModule,
    useCaseModule,
    viewModelModule
)
```

### Ktor API Service

```kotlin
class RickAndMortyApi(private val client: HttpClient) {

    private companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api"
    }

    suspend fun getCharacters(page: Int): ApiResponseDto<CharacterDto> {
        return client.get("$BASE_URL/character") {
            parameter("page", page)
        }.body()
    }

    suspend fun getCharacterById(id: Int): CharacterDto {
        return client.get("$BASE_URL/character/$id").body()
    }

    suspend fun searchCharacters(name: String, page: Int): ApiResponseDto<CharacterDto> {
        return client.get("$BASE_URL/character") {
            parameter("name", name)
            parameter("page", page)
        }.body()
    }
}
```

### Navigation Routes

```kotlin
sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Favorites : Screen("favorites")
    data object Search : Screen("search")
    data object CharacterDetail : Screen("character_detail/{characterId}") {
        fun createRoute(characterId: Int) = "character_detail/$characterId"
    }
}
```

### Error Handling

Always use Result type for repository operations:

```kotlin
override suspend fun getCharacters(page: Int): Result<List<Character>> {
    return try {
        val response = api.getCharacters(page)
        val characters = response.results.map { mapper.toDomain(it) }
        Result.success(characters)
    } catch (e: Exception) {
        Result.failure(e)
    }
}
```

In ViewModel:

```kotlin
getCharactersUseCase(page)
    .onSuccess { characters ->
        // handle success
    }
    .onFailure { error ->
        // handle error
    }
```

## API Reference

Base URL: https://rickandmortyapi.com/api

Endpoints:
- GET /character - List all characters (paginated)
- GET /character/{id} - Get single character
- GET /character/?name={name} - Filter by name
- GET /character/?status={status} - Filter by status (alive, dead, unknown)
- GET /location - List all locations
- GET /episode - List all episodes

Response structure for lists:

```json
{
  "info": {
    "count": 826,
    "pages": 42,
    "next": "https://rickandmortyapi.com/api/character?page=2",
    "prev": null
  },
  "results": [
    {
      "id": 1,
      "name": "Rick Sanchez",
      "status": "Alive",
      "species": "Human",
      "type": "",
      "gender": "Male",
      "origin": {
        "name": "Earth (C-137)",
        "url": "https://rickandmortyapi.com/api/location/1"
      },
      "location": {
        "name": "Citadel of Ricks",
        "url": "https://rickandmortyapi.com/api/location/3"
      },
      "image": "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
      "episode": [
        "https://rickandmortyapi.com/api/episode/1",
        "https://rickandmortyapi.com/api/episode/2"
      ],
      "url": "https://rickandmortyapi.com/api/character/1",
      "created": "2017-11-04T18:48:46.250Z"
    }
  ]
}
```

## Common Mistakes to Avoid

1. Do not use Android Context in commonMain
2. Do not use Retrofit, use Ktor instead
3. Do not use Hilt, use Koin instead
4. Do not use Gson or Moshi, use kotlinx.serialization
5. Do not put business logic in ViewModels, use UseCases
6. Do not pass ViewModel to Compose screens - pass state and callbacks instead
7. Do not forget to handle loading and error states
8. Do not hardcode strings in Compose, use resources
9. Do not forget to implement pagination trigger before list ends
10. Do not forget to update favorite status when returning from detail screen
11. Do not create stateful screens - always use state hoisting
12. Do not connect ViewModel inside screen composables - connect at navigation layer

## File Documentation

Every public class and function should have KDoc documentation explaining its purpose. Write brief but meaningful descriptions. Do not add obvious comments.