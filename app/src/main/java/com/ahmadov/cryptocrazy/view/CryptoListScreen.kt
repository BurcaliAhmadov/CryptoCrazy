package com.ahmadov.cryptocrazy.view
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahmadov.cryptocrazy.ViewModel.CryptoListViewModel
import com.ahmadov.cryptocrazy.model.CryptoListItem

@Composable
fun CryptoListScreen(
    navController: NavController,
    viewModel: CryptoListViewModel= hiltViewModel()
){
    Surface(
        color =MaterialTheme.colors.secondary,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Text(text = "Crypto Crazy",
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                textAlign= TextAlign.Center, fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color=MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.height(10.dp))

            //Search
            SearchBar(modifier= Modifier
                .fillMaxWidth()
                .padding(16.dp),hint = "Search..."){
                viewModel.searchCryptoList(it)
            }
            Spacer(modifier = Modifier.height(10.dp))
            //List
            CryptoList(navController = navController)

        }
    }
}
@Composable
fun SearchBar(
    modifier: Modifier,
    hint:String = "",
   onSearch:(String)->Unit ={}
){
    var text by remember{
        mutableStateOf("") }

    var isHintDisplayed by remember {
        mutableStateOf(hint!="")
    }
    Box(modifier = modifier){
        BasicTextField(value = text, onValueChange = {
            text=it
            onSearch(it)
        }, maxLines = 1,
            singleLine = true,
            textStyle =TextStyle(color = Color.Black),
            modifier= Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(
                    Color.White,
                    CircleShape
                )
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = it.isFocused != true && text.isEmpty()
                }

        )
        if(isHintDisplayed){
            Text(text=hint,
                color = Color.LightGray,
                modifier=Modifier.padding(horizontal = 20.dp,vertical=12.dp)

            )
        }
    }

}

@Composable
fun CryptoList(navController: NavController,viewModel: CryptoListViewModel= hiltViewModel()
){
    val cryptoList by remember {viewModel.cryptoList}
    val errorMessage by remember {viewModel.errorMessage}
    val isLoading by remember {viewModel.isLoading}

    CryptoListView(navController = navController, cryptos = cryptoList)
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        if(isLoading){
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        if(errorMessage.isNotEmpty()){
            //retryView
            RetryView(error = errorMessage) {
                viewModel.loadCryptos()

            }
        }
    }


}

@Composable
fun CryptoListView(navController: NavController,cryptos:List<CryptoListItem>) {
    LazyColumn(contentPadding = PaddingValues(5.dp)) {
        items(cryptos){crypto->
            CryptoRow(navController = navController, crypto = crypto)
        }
    }
}


@Composable
fun CryptoRow(navController: NavController,crypto:CryptoListItem) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colors.secondary)
        .clickable {
            navController.navigate(
                "crypto_detail_screen/${crypto.currency}/${crypto.price}"
            )


        }
    ){
        Text(text=crypto.currency,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(2.dp),
            fontWeight = FontWeight.Bold,
            color=MaterialTheme.colors.primary

        )
        Text(
            text = crypto.price,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(2.dp),
            color = MaterialTheme.colors.primaryVariant

        )
    }

}
@Composable
fun RetryView(
    error:String,
    onRetry:() -> Unit
){
    Column() {
        Text(error, color = Color.Red, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            onRetry
        }, modifier = Modifier.align(Alignment.CenterHorizontally)){
            Text(text = "Retry")
        }


        
    }

}