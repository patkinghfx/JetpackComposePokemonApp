package com.example.pokemonapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
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

    @Composable
    fun HomePage() {

        var text by remember { mutableStateOf("") }
        var flag by remember { mutableStateOf(false) }

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
            Row {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Find Pokemon") }
                )
                Button(
                    onClick = {
                        flag = true
                        mainViewModel.getPokemon(text)
                    },
                    modifier = Modifier
                        .padding(5.dp),

                    ) {Text("Go")

                }
            }
            if(flag){
                DisplayPokemon()
            }
        }
    }

    @Composable
    fun DisplayPokemon() {
        val pokemon by mainViewModel.pokemonStateFlow.collectAsState()

        Column {
            val imageUrl = pokemon?.sprite.toString()
            AsyncImage(
                model = imageUrl,
                contentDescription = "Searched Pokemon",
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = pokemon?.name.toString(),
                fontSize = 30.sp
            )
        }
    }
}
