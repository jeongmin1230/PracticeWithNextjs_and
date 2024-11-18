package com.jm.practicewithnextjs.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jm.practicewithnextjs.AppTopBar
import com.jm.practicewithnextjs.GeneralButton
import com.jm.practicewithnextjs.R
import com.jm.practicewithnextjs.User
import com.jm.practicewithnextjs.api.model.response.NoticeDetailResponse
import com.jm.practicewithnextjs.api.model.response.NoticeListResponse
import com.jm.practicewithnextjs.setTextFieldColor
import com.jm.practicewithnextjs.ui.theme.PracticeWithNextjsTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticeWithNextjsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier) {
    val mainViewModel = MainViewModel()
    val route = stringArrayResource(R.array.main_nav)
    val mainNavController = rememberNavController()
    val today = SimpleDateFormat(stringResource(R.string.date_format), Locale.getDefault()).format(Date())
    NavHost(navController = mainNavController, startDestination = route[0]) {
        composable(route[0]) {
            Main(mainViewModel.noticeList, mainNavController, mainViewModel.writingId) { mainViewModel.loadList() }
        }
        composable(route[1]) {
            Column(Modifier.fillMaxSize()) {
                AppTopBar(stringResource(R.string.write_announcement)) { mainNavController.popBackStack() }
                Spacer(Modifier.height(20.dp))
                Column(Modifier.padding(horizontal = 8.dp)) {
                    Detail(mainViewModel.loadContent) { mainViewModel.detail() }
                }
            }
        }
        composable(route[2]) {
            mainViewModel.init()
            Column(Modifier.fillMaxSize()) {
                AppTopBar(stringResource(R.string.write_announcement)) { mainNavController.popBackStack() }
                Spacer(Modifier.height(20.dp))
                Column(Modifier.padding(horizontal = 12.dp)) {
                    Writing(mainViewModel.writeTitle, mainViewModel.writeBody) { mainViewModel.write(today) { mainNavController.popBackStack() } }
                }
            }
        }
    }
}

@Composable
fun Main(noticeList: MutableState<List<NoticeListResponse.Data>>, navController: NavHostController, clickId: MutableState<String>, loadList: () -> Unit) {
    LaunchedEffect(true) { loadList() }
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .background(Color(0xFFBCD4C9))
            .fillMaxWidth()) {
            Text(
                text = stringResource(R.string.welcome).format(User.name),
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black, textAlign = TextAlign.Center),
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth()
            )

        }
        if(User.type == stringResource(R.string.manager)) {
            Spacer(Modifier.height(8.dp))
            Box(modifier = Modifier
                .padding(horizontal = 8.dp)
                .background(Color(0xFFBCD4C9))
                .fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.write_announcement),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = Color.Black),
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .fillMaxWidth()
                        .clickable { navController.navigate(context.resources.getStringArray(R.array.main_nav)[2]) }
                )
            }
            Spacer(Modifier.height(20.dp))
        }
        if(noticeList.value.isNotEmpty()) {
            noticeList.value.forEach {
                EachList(it.title, it.date, it.registrant, it.body, navController, stringArrayResource(R.array.main_nav)[1]) { clickId.value = it.id }
            }

        } else {
            Text(stringResource(R.string.empty_list))
        }
    }

}

@Composable
fun EachList(title: String, date: String, registrant: String, body: String, navController: NavHostController, route: String, click: () -> Unit) {
    Column(modifier = Modifier
        .clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {
            click()
            navController.navigate(route)
        }
        .padding(start = 4.dp, top = 4.dp, end = 4.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(Color.Black),
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Row {
            Text(
                text = date,
                style = MaterialTheme.typography.bodySmall.copy(Color.DarkGray)
            )
            Text(
                text = registrant,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.DarkGray, textAlign = TextAlign.End),
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun Detail(content: MutableState<List<NoticeDetailResponse>>, loadDetail: () -> Unit) {
    LaunchedEffect(true) { loadDetail() }
    Column {
        Column(Modifier.padding(horizontal = 12.dp)) {
            content.value.forEach {
                DetailLayout(
                    label = stringResource(R.string.title),
                    text = it.title,
                    modifier = Modifier
                )
                DetailLayout(
                    label = stringResource(R.string.content),
                    text = it.body,
                    modifier = Modifier.height(200.dp)
                )
                DetailLayout(
                    label = stringResource(R.string.date),
                    text = it.date,
                    modifier = Modifier
                )
                DetailLayout(
                    label = stringResource(R.string.registrant),
                    text = it.registrant,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun DetailLayout(label: String, text: String, modifier: Modifier) {
    Text(
        text = label,
        style = MaterialTheme.typography.titleSmall.copy(Color.Black),
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall.copy(Color.Black),
        modifier = modifier
            .background(Color(0xFFE2EDEB))
            .padding(all = 12.dp)
            .fillMaxWidth()
    )
    Spacer(Modifier.height(12.dp))
}

@Composable
fun Writing(title: MutableState<String>, content: MutableState<String>, write: () -> Unit) {
    Column {
        Text(
            text = stringResource(R.string.title),
            style = MaterialTheme.typography.titleSmall.copy(Color.Black),
            modifier = Modifier.padding(start = 12.dp)
        )
        TextField(
            value = title.value,
            onValueChange = { title.value = it},
            colors = setTextFieldColor(Color(0xFFE2EDEB)),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.content),
            style = MaterialTheme.typography.titleSmall.copy(Color.Black),
            modifier = Modifier.padding(start = 12.dp)
        )
        TextField(
            value = content.value,
            onValueChange = { content.value = it},
            colors = setTextFieldColor(Color(0xFFE2EDEB)),
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        )
        Spacer(Modifier.height(20.dp))
        GeneralButton(Modifier.background(Color(0xFFBCD4C9)), stringResource(R.string.write_announcement)) { write() }
    }
}