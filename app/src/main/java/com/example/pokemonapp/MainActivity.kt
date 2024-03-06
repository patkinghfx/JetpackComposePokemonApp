package com.example.pokemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pokemonapp.models.PokemonType
import com.example.pokemonapp.ui.theme.PokemonAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = MainViewModel()
        setContent {
            PokemonAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomePage()
                }
            }
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun HomePage() {
        val pokemon by mainViewModel.pokemonStateFlow.collectAsState()

        var text by remember { mutableStateOf("") }
        var flag by remember { mutableStateOf(false) }
        val keyboardController = LocalSoftwareKeyboardController.current

        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.pokemon_logo),
                contentDescription = "Pokemon Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(40.dp)
            )
            Row (verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Find Pokemon") },
                    modifier = Modifier.padding(25.dp)
                )
                Button(
                    onClick = {
                        flag = true
                        mainViewModel.getPokemon(text)
                        keyboardController?.hide()
                    },
                    modifier = Modifier
                        .padding(5.dp),

                    ) {Text("Go")

                }
            }
            if(flag){
                LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(5.dp))
                 {
                    item{
                        // val imageUrl = pokemon?.sprite?.pokeSprite
                        AsyncImage(
                            model = pokemon?.sprites?.pokeSprite,
                            contentDescription = "Searched Pokemon",
                            modifier = Modifier.size(220.dp)
                        )}
                    item{
                        Text(
                            text = pokemon?.name.toString(),
                            fontSize = 30.sp
                        )}
                     item{
                         Text(
                             text = "Weight: ${pokemon?.weight.toString()}",
                             fontSize = 15.sp
                         )}
                     item{
                         Text(
                             text = "Height: ${pokemon?.height.toString()}",
                             fontSize = 15.sp
                         )}
                     item{
                         Text(
                             text = "Types: ${pokemon?.types?.joinToString(separator = " - ") { it.type.name }}",
                             fontSize = 15.sp
                         )}
                }
            }
        }
    }
}
