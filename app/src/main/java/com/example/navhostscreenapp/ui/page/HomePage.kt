package com.example.navhostscreenapp.ui.page

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.IconButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.navhostscreenapp.ui.items.DynamicsIcon
import com.example.navhostscreenapp.ui.items.ItemInfo
import com.example.navhostscreenapp.ui.theme.NavhostScreenAppTheme

/**
 * 关于嵌套导航
 * https://developer.android.google.cn/guide/topics/large-screens/navigation-for-responsive-uis#nested_navigation_host
 */
@Composable
fun Home(
    navController: NavController,
    viewModel: HomePageViewModel = viewModel(factory = ViewModelProvider.Factory)
) {
    var text by rememberSaveable {
        mutableStateOf("")
    }
    val list = viewModel.questionDao.selectAll(text).collectAsState(initial = arrayListOf())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(1.dp)
            .background(Color.LightGray)
    ) {
        TopSearchBar(modifier = Modifier.fillMaxWidth(), onChange = {
            text = it
            Log.d("TopSearchBar", "Home: " + it)
        })
        DynamicsIcon()
        TestReplay()
        LazyColumn(

        ) {
            //我们多传入一个参数key，即使用QuestionItem中的id，必须要清楚的是，这个key必须是独一无二的，因为当存在两个
            // 相同的key时，列表将无法确定item的唯一性。
            //
            //这样的好处就是，列表可以清楚感知每一个item的唯一性，当数据源只发生了项的位置的变化，或者部分项被新增或者移除了，
            // 列表只需要处理那些发生过变化的项对应的可组合项即可，不需要重组整个列表。这样列表的性能提高了一个数量级。
            items(items = list.value, key = { it.id }) {
                QuestionItem(item = it.id, navController)
            }
        }
    }
}

@Composable
fun TestReplay() {
    Log.i("TestReplay","1")
    var status by remember {
        mutableStateOf(false)
    }
    Button(onClick = { status=!status }.also {
        Log.i("TestReplay","2")
    }) {
        TestTextDemo(text = status.toString()).also {
            Log.i("TestReplay","3")
        }
    }
}

@Composable
fun TestTextDemo(text:String) {
    Log.i("TestReplay","4")
    Text(text = text).also {
        Log.i("TestReplay","5")
    }
}


@Composable
fun QuestionItem(item: Int, navController: NavController) {
    Row(
        modifier = Modifier
            .padding(3.dp)
            .border(width = 1.dp, color = Color.Blue, shape = ShapeDefaults.Small)
            .shadow(1.dp, spotColor = Color.Black)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(1.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 2.dp), text = "$item"
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 2.dp), text = "more..........."
            )
        }
        IconButton(onClick = { navController.navigate("info") }) {
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "open the item")
        }
    }
}

/**
 * 基于官方文档 总结出ViewModel在Compose中的方式：
 *
 * 1.ViewModel仅用于最顶层的屏幕级可组合项，即离Activity或者Fragment的setContent{}方法最近的那个可组合项。
 *
 * 2.遵循单一数据源规范，ViewModel将状态传递给子可组合项，子可组合项将事件向上传递给顶层的可组合项，
 * 不能将ViewModel直接传递给子可组合项。
 *
 * 注：很久以前官方文档还会提到ViewModel可能会导致子可组合项的内存泄漏，因为ViewModel的生命周期会比子可组合项更长，
 * 一些lambda或者匿名方法会导致可组合项被ViewModel持有导致内存泄漏。
 */
@Composable
fun TopSearchBar(modifier: Modifier = Modifier, onChange: (String) -> Unit) {
    var searchText by remember {
        mutableStateOf("")
    }
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(modifier = Modifier.weight(1f), value = searchText, onValueChange = {
            searchText = it
        }, singleLine = true, leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "search")
        }, trailingIcon = {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "search",
                modifier = Modifier.clickable {
                    searchText = "".also {
                        onChange.invoke(it)
                    }
                })
        }
        )
        TextButton(onClick = { onChange(searchText) }) {
            Text(text = "search", color = Color.Blue)
        }
    }

}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NavhostScreenAppTheme {
        TopSearchBar(onChange = {
            it
        })
    }
}

