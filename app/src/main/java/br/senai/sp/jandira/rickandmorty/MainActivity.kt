package br.senai.sp.jandira.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.senai.sp.jandira.rickandmorty.model.CharacterList
import br.senai.sp.jandira.rickandmorty.model.Info
import br.senai.sp.jandira.rickandmorty.service.RetrofitFactory
import br.senai.sp.jandira.rickandmorty.ui.theme.RickAndMortyTheme
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickAndMortyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {

    var results by remember {
        mutableStateOf(listOf<br.senai.sp.jandira.rickandmorty.model.Character>())
    }

    var info by remember {
        mutableStateOf(Info)
    }

    Column() {
        Button(onClick = {
            //Chamada para a API
            val call = RetrofitFactory().getCharacterService().getCharacters()

            call.enqueue(object : Callback<CharacterList> {
                override fun onResponse(
                    call: Call<CharacterList>,
                    response: Response<CharacterList>
                ) {
                    results = response.body()!!.results
                    info = response.body()!!.info
                }

                override fun onFailure(call: Call<CharacterList>, t: Throwable) {

                }

            })

        }) {
            Text(text = "List All Characters")

        }
        LazyColumn() {
            items(results) {
                Column() {
                    Text(text = it.name)
                    Text(text = it.species)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    RickAndMortyTheme {
        Greeting("Android")
    }
}