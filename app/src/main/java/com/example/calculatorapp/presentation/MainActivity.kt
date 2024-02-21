package com.example.calculatorapp.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.calculatorapp.domain.model.CurrenciesInfo
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: MainViewModel
    private var toCurrencyIndex: Int=0
    private var fromCurrencyIndex: Int=0
    private var result: Double=0.00
    private var data:List<CurrenciesInfo>?=null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java];
        mainViewModel.onEvent(HomeEvent.Refresh)
        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                ScreenUi()

            }
        }

    }

    @OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
    @Composable
    fun ScreenUi() {
        var text by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth(100.0f)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Please type your amount", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = text, style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(16.dp))
            if (mainViewModel.state.isLoading) Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
            else {
               data= mainViewModel.state.currencyList
                Column(Modifier.fillMaxWidth()) {
                    Spinner(
                        list = data!!.map { it.title!! },
                        preselected = data!!.first().title!!,
                        title = "From",
                        onSelectionChanged = {selected->
                            fromCurrencyIndex=selected
                        })
                    Spinner(
                        list = mainViewModel.state.currencyList.map { it.title!! },
                        preselected = mainViewModel.state.currencyList.first().title!!,
                        title = "To",
                        onSelectionChanged = {selected->
                            toCurrencyIndex=selected

                        })

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    ) {
                        Button( onClick = { text=""}){
                            Text(
                                text = "Clear",
                                style = TextStyle(fontSize = 15.sp)
                            )
                        }
                        Button( onClick = {
                            if (text.isNullOrEmpty()){
                                Toast.makeText(applicationContext,"Please type amount",Toast.LENGTH_SHORT).show()
                                return@Button
                            }
                            result=(text.toDouble()*(data!![toCurrencyIndex].rate !! / data!![fromCurrencyIndex].rate!!))
                            text=""
                            text=result.toString()
                        }) {
                            Text(
                                text = "Convert",
                                style = TextStyle(fontSize = 15.sp)
                            )
                        }
                    }

                    val numbers = (0..9).toList()
                    LazyVerticalGrid(
                        contentPadding = PaddingValues(8.dp),
                        modifier = Modifier.padding(8.dp),
                        cells = GridCells.Fixed(3)
                    ) {
                        items(numbers.size) { item ->
                            GridCell(item, onItemClick = { clickedItem ->
                                // Handle item click here
                                // You can do something with the clicked item
                                // For example, navigate to a detail screen
                                // or perform some action
                                text += clickedItem.toString()

                            })
                        }
                    }


                }
            }
        }
    }

    @Composable
    fun GridCell(item: Int,onItemClick: (Int) -> Unit) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(80.dp) // Adjust the size of the cell as needed
                .clip(MaterialTheme.shapes.medium)
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))

                .clickable {
                    onItemClick(item)
                }
        ) {
            // Content of the cell
            Text(
                text = "${item.toString()}",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp),
                color = Color.Black,
            )
        }

    }


    @Preview(showSystemUi = true)
    @Composable
    fun PreviewScreenUi() {
        Surface {
            ScreenUi()
        }
    }

    @Composable
    fun Spinner(
        list: List<String>,
        preselected: String,
        title: String,
        onSelectionChanged: (selection: Int) -> Unit
    ) {

        var selected by remember { mutableStateOf(preselected) }
        var expanded by remember { mutableStateOf(false) } // initial value

        Box {
            Column {
                OutlinedTextField(
                    value = (selected),
                    onValueChange = { },
                    label = { Text(text = title) },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = { Icon(Icons.Outlined.ArrowDropDown, null) },
                    readOnly = true
                )
                DropdownMenu(
                    modifier = Modifier.fillMaxWidth(),
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    list.forEachIndexed { index,entry ->
                        DropdownMenuItem(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onSelectionChanged(index)
                                selected = entry
                                expanded = false
                            },
                            content = {
                                Text(
                                    text = (entry),
                                )
                            }
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Transparent)
                    .padding(10.dp)
                    .clickable(
                        onClick = { expanded = !expanded }
                    )
            )
        }
    }


}