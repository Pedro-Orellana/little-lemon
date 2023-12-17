package com.pedroapps.littlelemon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.pedroapps.littlelemon.ui.theme.karlaFontFamily


@Composable
fun Home(navController: NavHostController) {
    val context = LocalContext.current
    val database = MenuDatabase.getDatabase(context = context)
    val menuItems = database.menuDao().getAllMenuItems().observeAsState()

    var searchPhrase by remember {
        mutableStateOf("")
    }

    var filteredItems by remember {
        mutableStateOf(listOf<MenuItemRoom>())
    }

    val itemsToDisplay = if (searchPhrase.isEmpty()) {
        filteredItems
    } else {
        menuItems.value?.filter { it.title.contains(searchPhrase, ignoreCase = true) }
    }



    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HomeHeader {
            navController.navigate("Profile")
        }

        HeroSection(searchPhrase = searchPhrase, setSearchPhrase = { searchPhrase = it })
        MenuBreakdown(allItems = menuItems.value ?: listOf(),
            setItemsToDisplay = { filteredItems = it })
        MenuItems(items = itemsToDisplay)
    }
}


@Composable
fun MenuItems(items: List<MenuItemRoom>?) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (items != null) {
            for (item in items) {
                MenuItem(item)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(item: MenuItemRoom) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .size(width = 0.dp, height = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Divider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 24.dp, start = 24.dp)
                .size(width = 0.dp, height = 90.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 12.dp, bottom = 12.dp)
                    .size(width = 260.dp, 0.dp)

            ) {
                Text(
                    text = item.title,
                    fontFamily = karlaFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Text(
                    text = item.description,
                    fontFamily = karlaFontFamily,
                    fontWeight = FontWeight.Light,
                    lineHeight = 12.sp,
                    maxLines = 2,
                    fontSize = 12.sp
                )
                Text(
                    text = "$${item.price}",
                    fontFamily = karlaFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            }
            GlideImage(
                //This when statement is because the images for Lemon Dessert and Grilled fish from the server are down,
                // so I had to replace them so it still displays an image
                model = (when (item.title) {
                    "Lemon Desert" -> R.drawable.lemon_dessert
                    "Grilled Fish" -> R.drawable.grilled_fish
                    else -> item.image
                }),
                contentDescription = "food image",
                modifier = Modifier.size(width = 100.dp, height = 60.dp),
                contentScale = ContentScale.FillBounds
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val items = listOf(
        MenuItemRoom(
            1,
            "title1",
            "This is a very long description, just so I can see how the text wraps around in the next line",
            "10",
            "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJQAAACUCAMAAABC4vDmAAAAYFBMVEWkxjn////+/vy71HahxC3d6b/r8tmcwRqoyUTi7Magwyfb6Lry9+iewyOjxTXO357S4qe40m/l7s6zz2Lu9N+YvwDE2oz4+/LX5bGuzFaryk3B14O10Gi91nzJ3ZWxzlzGl4Q4AAAE8ElEQVR4nO2ca5eiMAyGASOVSoOUuyjz///looJctxe07Jw9fc98mQHpYwlpQpNxXD0dCVaFzgeKCslRcxBH83w3AbjonH8BSHTH0IbigKdw+NXzCl5GdZz8VNVPEtdRyQvPG46HDiI3DuXWwPJ+RF7WFSGEAeBTAKz9tapL3nPXDGrtIfShwsyB55BBXTmMorMQUuZUdfA8mTokFF/vK1Buw+Be+DkjsAL0BgPCcr84AGv0R9gAFZ7wFLeTIRMyiJ2JARqE8hpAkCI91Z7YePIrfg7l3+STNBK7+eahatVZGmZL+/HThDoC1UN6iIKmS9eC8vI1DyAXUj3L0oEKb9uYHlQ3nYdQA4o7G25dL+poLDbqUPykaeFTwUmdShnqyDbeul4aEYwqFP+U6eHhVedKEYrjx0wtlapdqUGlorVXgwrT70EVydjGIcsyogSJpD118tEfpVBaBcq7j1c7Grdfl18VHkW4tjFVGo8dCb2reFEVqHLMBLfnZdOTdK7w9FyKvduYn5XfgSomfhw6Y42knpRGrzP5GAqpwg2UQ3mHyUWrbr04ymeq80thNflSB/kNlEMFE+vBU6oLNbvTEHwO5c1MujMKr5ZaOtSvOSmnQSFcpVMlhTqT2ffHp/leVJ6+Z9bqz/0uOX8K5V3ntwmdOihjFQ+PEJ+D2llcQDpVMqhyOSNICVFNHMhaHgYytyCB8u4fxSt/QZV5UAmUr5W5qIpJEhwJVGMGSpI1S6AWVvoVoWxU4dGUyEfYIiIOYcRQlw9SBZGo+L2bGOpg4Nl7CA7boYofIybVGpU42BNC+fKgaSPUSegUhFCBIZNqjUoYKgihzka81ENMuCgLoSJzUNFmqNzc7RP6dBGUkdX4JfGaLIS6ZcSQsngrlMuPxiRM4GdQG17lfkOzYUdQYRnleXPRf5n7ofxLk+dROXrV94YqcgcotD9O/F7CPYPqx0jjfty8mEMF0OfBCFn5+ohnaj0eUlKvzPogHuk7I+yg+CS+7xJu70DRkGgHNUn+EYMxFJ/lHFm5z0yV2eSvCP4Iav5iBzHcAyqcZ49wHaB4tvhQtAdUtLh+xt9Qy8ExKcxDFckiXHuFpA+otQDz8cbANJS/zJ5fIekDKl28L2jzDW4eii9zJbymHdRa1EsC81DBCtQzTrZQFspCWSgLZaEslIWyUBbKQlkoC2WhLJSFslAWykJZKAtloSyUhfqPodY2jLI9NowW24yjDaOVrTXcZWttWVY7bK25y5oW+FebkPf3zqj/e7Zr/WFju5rVJCEWe0AV8/tHq2Fj202nLU3IngUC5ksAgmmLEHQV+F2xhO+MxkfaFcKbr+Aox9vI4HR1Nn1ZSer00AhZV0ixA5QbDGUlrO9UGApwvEsFjFFGr+9CmD2g3CK/toMyBtXlXZQzLlXiZdOUwVDWuwtUe5OCx7h8rVRpRTtBLWWhLNSuUL+x+PR31g4fzUEJO22FUNMmuC/q3ZC3AcpQ24y0cUYM5X2j03cpRHE5sKSthpvo5pH2I8u61o7s624BmKwXUtp0mCb0q1hAE2mHtLxntDgmGWGdVvkoG2m1AwH6o4QkZ3l7rVJzdBhcopfuKyYGTTRSs8KN9+7ghRffao4eaa2ndtpZXKxMFZr8Hxyue16ZBzpxhOEKFEhbVy2UhbJQFspCWSgLZaEslIWyUL8Z6g9Ta29lluVxhQAAAABJRU5ErkJggg==",
            "starters"
        ), MenuItemRoom(
            2,
            "title2",
            "description2",
            "10",
            "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJQAAACUCAMAAABC4vDmAAAAYFBMVEWkxjn////+/vy71HahxC3d6b/r8tmcwRqoyUTi7Magwyfb6Lry9+iewyOjxTXO357S4qe40m/l7s6zz2Lu9N+YvwDE2oz4+/LX5bGuzFaryk3B14O10Gi91nzJ3ZWxzlzGl4Q4AAAE8ElEQVR4nO2ca5eiMAyGASOVSoOUuyjz///looJctxe07Jw9fc98mQHpYwlpQpNxXD0dCVaFzgeKCslRcxBH83w3AbjonH8BSHTH0IbigKdw+NXzCl5GdZz8VNVPEtdRyQvPG46HDiI3DuXWwPJ+RF7WFSGEAeBTAKz9tapL3nPXDGrtIfShwsyB55BBXTmMorMQUuZUdfA8mTokFF/vK1Buw+Be+DkjsAL0BgPCcr84AGv0R9gAFZ7wFLeTIRMyiJ2JARqE8hpAkCI91Z7YePIrfg7l3+STNBK7+eahatVZGmZL+/HThDoC1UN6iIKmS9eC8vI1DyAXUj3L0oEKb9uYHlQ3nYdQA4o7G25dL+poLDbqUPykaeFTwUmdShnqyDbeul4aEYwqFP+U6eHhVedKEYrjx0wtlapdqUGlorVXgwrT70EVydjGIcsyogSJpD118tEfpVBaBcq7j1c7Grdfl18VHkW4tjFVGo8dCb2reFEVqHLMBLfnZdOTdK7w9FyKvduYn5XfgSomfhw6Y42knpRGrzP5GAqpwg2UQ3mHyUWrbr04ymeq80thNflSB/kNlEMFE+vBU6oLNbvTEHwO5c1MujMKr5ZaOtSvOSmnQSFcpVMlhTqT2ffHp/leVJ6+Z9bqz/0uOX8K5V3ntwmdOihjFQ+PEJ+D2llcQDpVMqhyOSNICVFNHMhaHgYytyCB8u4fxSt/QZV5UAmUr5W5qIpJEhwJVGMGSpI1S6AWVvoVoWxU4dGUyEfYIiIOYcRQlw9SBZGo+L2bGOpg4Nl7CA7boYofIybVGpU42BNC+fKgaSPUSegUhFCBIZNqjUoYKgihzka81ENMuCgLoSJzUNFmqNzc7RP6dBGUkdX4JfGaLIS6ZcSQsngrlMuPxiRM4GdQG17lfkOzYUdQYRnleXPRf5n7ofxLk+dROXrV94YqcgcotD9O/F7CPYPqx0jjfty8mEMF0OfBCFn5+ohnaj0eUlKvzPogHuk7I+yg+CS+7xJu70DRkGgHNUn+EYMxFJ/lHFm5z0yV2eSvCP4Iav5iBzHcAyqcZ49wHaB4tvhQtAdUtLh+xt9Qy8ExKcxDFckiXHuFpA+otQDz8cbANJS/zJ5fIekDKl28L2jzDW4eii9zJbymHdRa1EsC81DBCtQzTrZQFspCWSgLZaEslIWyUBbKQlkoC2WhLJSFslAWykJZKAtloSyUhfqPodY2jLI9NowW24yjDaOVrTXcZWttWVY7bK25y5oW+FebkPf3zqj/e7Zr/WFju5rVJCEWe0AV8/tHq2Fj202nLU3IngUC5ksAgmmLEHQV+F2xhO+MxkfaFcKbr+Aox9vI4HR1Nn1ZSer00AhZV0ixA5QbDGUlrO9UGApwvEsFjFFGr+9CmD2g3CK/toMyBtXlXZQzLlXiZdOUwVDWuwtUe5OCx7h8rVRpRTtBLWWhLNSuUL+x+PR31g4fzUEJO22FUNMmuC/q3ZC3AcpQ24y0cUYM5X2j03cpRHE5sKSthpvo5pH2I8u61o7s624BmKwXUtp0mCb0q1hAE2mHtLxntDgmGWGdVvkoG2m1AwH6o4QkZ3l7rVJzdBhcopfuKyYGTTRSs8KN9+7ghRffao4eaa2ndtpZXKxMFZr8Hxyue16ZBzpxhOEKFEhbVy2UhbJQFspCWSgLZaEslIWyUL8Z6g9Ta29lluVxhQAAAABJRU5ErkJggg==",
            "starters"
        ), MenuItemRoom(
            3,
            "title3",
            "description3",
            "10",
            "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJQAAACUCAMAAABC4vDmAAAAYFBMVEWkxjn////+/vy71HahxC3d6b/r8tmcwRqoyUTi7Magwyfb6Lry9+iewyOjxTXO357S4qe40m/l7s6zz2Lu9N+YvwDE2oz4+/LX5bGuzFaryk3B14O10Gi91nzJ3ZWxzlzGl4Q4AAAE8ElEQVR4nO2ca5eiMAyGASOVSoOUuyjz///looJctxe07Jw9fc98mQHpYwlpQpNxXD0dCVaFzgeKCslRcxBH83w3AbjonH8BSHTH0IbigKdw+NXzCl5GdZz8VNVPEtdRyQvPG46HDiI3DuXWwPJ+RF7WFSGEAeBTAKz9tapL3nPXDGrtIfShwsyB55BBXTmMorMQUuZUdfA8mTokFF/vK1Buw+Be+DkjsAL0BgPCcr84AGv0R9gAFZ7wFLeTIRMyiJ2JARqE8hpAkCI91Z7YePIrfg7l3+STNBK7+eahatVZGmZL+/HThDoC1UN6iIKmS9eC8vI1DyAXUj3L0oEKb9uYHlQ3nYdQA4o7G25dL+poLDbqUPykaeFTwUmdShnqyDbeul4aEYwqFP+U6eHhVedKEYrjx0wtlapdqUGlorVXgwrT70EVydjGIcsyogSJpD118tEfpVBaBcq7j1c7Grdfl18VHkW4tjFVGo8dCb2reFEVqHLMBLfnZdOTdK7w9FyKvduYn5XfgSomfhw6Y42knpRGrzP5GAqpwg2UQ3mHyUWrbr04ymeq80thNflSB/kNlEMFE+vBU6oLNbvTEHwO5c1MujMKr5ZaOtSvOSmnQSFcpVMlhTqT2ffHp/leVJ6+Z9bqz/0uOX8K5V3ntwmdOihjFQ+PEJ+D2llcQDpVMqhyOSNICVFNHMhaHgYytyCB8u4fxSt/QZV5UAmUr5W5qIpJEhwJVGMGSpI1S6AWVvoVoWxU4dGUyEfYIiIOYcRQlw9SBZGo+L2bGOpg4Nl7CA7boYofIybVGpU42BNC+fKgaSPUSegUhFCBIZNqjUoYKgihzka81ENMuCgLoSJzUNFmqNzc7RP6dBGUkdX4JfGaLIS6ZcSQsngrlMuPxiRM4GdQG17lfkOzYUdQYRnleXPRf5n7ofxLk+dROXrV94YqcgcotD9O/F7CPYPqx0jjfty8mEMF0OfBCFn5+ohnaj0eUlKvzPogHuk7I+yg+CS+7xJu70DRkGgHNUn+EYMxFJ/lHFm5z0yV2eSvCP4Iav5iBzHcAyqcZ49wHaB4tvhQtAdUtLh+xt9Qy8ExKcxDFckiXHuFpA+otQDz8cbANJS/zJ5fIekDKl28L2jzDW4eii9zJbymHdRa1EsC81DBCtQzTrZQFspCWSgLZaEslIWyUBbKQlkoC2WhLJSFslAWykJZKAtloSyUhfqPodY2jLI9NowW24yjDaOVrTXcZWttWVY7bK25y5oW+FebkPf3zqj/e7Zr/WFju5rVJCEWe0AV8/tHq2Fj202nLU3IngUC5ksAgmmLEHQV+F2xhO+MxkfaFcKbr+Aox9vI4HR1Nn1ZSer00AhZV0ixA5QbDGUlrO9UGApwvEsFjFFGr+9CmD2g3CK/toMyBtXlXZQzLlXiZdOUwVDWuwtUe5OCx7h8rVRpRTtBLWWhLNSuUL+x+PR31g4fzUEJO22FUNMmuC/q3ZC3AcpQ24y0cUYM5X2j03cpRHE5sKSthpvo5pH2I8u61o7s624BmKwXUtp0mCb0q1hAE2mHtLxntDgmGWGdVvkoG2m1AwH6o4QkZ3l7rVJzdBhcopfuKyYGTTRSs8KN9+7ghRffao4eaa2ndtpZXKxMFZr8Hxyue16ZBzpxhOEKFEhbVy2UhbJQFspCWSgLZaEslIWyUL8Z6g9Ta29lluVxhQAAAABJRU5ErkJggg==",
            "starters"
        ), MenuItemRoom(
            4,
            "title4",
            "description4",
            "10",
            "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJQAAACUCAMAAABC4vDmAAAAYFBMVEWkxjn////+/vy71HahxC3d6b/r8tmcwRqoyUTi7Magwyfb6Lry9+iewyOjxTXO357S4qe40m/l7s6zz2Lu9N+YvwDE2oz4+/LX5bGuzFaryk3B14O10Gi91nzJ3ZWxzlzGl4Q4AAAE8ElEQVR4nO2ca5eiMAyGASOVSoOUuyjz///looJctxe07Jw9fc98mQHpYwlpQpNxXD0dCVaFzgeKCslRcxBH83w3AbjonH8BSHTH0IbigKdw+NXzCl5GdZz8VNVPEtdRyQvPG46HDiI3DuXWwPJ+RF7WFSGEAeBTAKz9tapL3nPXDGrtIfShwsyB55BBXTmMorMQUuZUdfA8mTokFF/vK1Buw+Be+DkjsAL0BgPCcr84AGv0R9gAFZ7wFLeTIRMyiJ2JARqE8hpAkCI91Z7YePIrfg7l3+STNBK7+eahatVZGmZL+/HThDoC1UN6iIKmS9eC8vI1DyAXUj3L0oEKb9uYHlQ3nYdQA4o7G25dL+poLDbqUPykaeFTwUmdShnqyDbeul4aEYwqFP+U6eHhVedKEYrjx0wtlapdqUGlorVXgwrT70EVydjGIcsyogSJpD118tEfpVBaBcq7j1c7Grdfl18VHkW4tjFVGo8dCb2reFEVqHLMBLfnZdOTdK7w9FyKvduYn5XfgSomfhw6Y42knpRGrzP5GAqpwg2UQ3mHyUWrbr04ymeq80thNflSB/kNlEMFE+vBU6oLNbvTEHwO5c1MujMKr5ZaOtSvOSmnQSFcpVMlhTqT2ffHp/leVJ6+Z9bqz/0uOX8K5V3ntwmdOihjFQ+PEJ+D2llcQDpVMqhyOSNICVFNHMhaHgYytyCB8u4fxSt/QZV5UAmUr5W5qIpJEhwJVGMGSpI1S6AWVvoVoWxU4dGUyEfYIiIOYcRQlw9SBZGo+L2bGOpg4Nl7CA7boYofIybVGpU42BNC+fKgaSPUSegUhFCBIZNqjUoYKgihzka81ENMuCgLoSJzUNFmqNzc7RP6dBGUkdX4JfGaLIS6ZcSQsngrlMuPxiRM4GdQG17lfkOzYUdQYRnleXPRf5n7ofxLk+dROXrV94YqcgcotD9O/F7CPYPqx0jjfty8mEMF0OfBCFn5+ohnaj0eUlKvzPogHuk7I+yg+CS+7xJu70DRkGgHNUn+EYMxFJ/lHFm5z0yV2eSvCP4Iav5iBzHcAyqcZ49wHaB4tvhQtAdUtLh+xt9Qy8ExKcxDFckiXHuFpA+otQDz8cbANJS/zJ5fIekDKl28L2jzDW4eii9zJbymHdRa1EsC81DBCtQzTrZQFspCWSgLZaEslIWyUBbKQlkoC2WhLJSFslAWykJZKAtloSyUhfqPodY2jLI9NowW24yjDaOVrTXcZWttWVY7bK25y5oW+FebkPf3zqj/e7Zr/WFju5rVJCEWe0AV8/tHq2Fj202nLU3IngUC5ksAgmmLEHQV+F2xhO+MxkfaFcKbr+Aox9vI4HR1Nn1ZSer00AhZV0ixA5QbDGUlrO9UGApwvEsFjFFGr+9CmD2g3CK/toMyBtXlXZQzLlXiZdOUwVDWuwtUe5OCx7h8rVRpRTtBLWWhLNSuUL+x+PR31g4fzUEJO22FUNMmuC/q3ZC3AcpQ24y0cUYM5X2j03cpRHE5sKSthpvo5pH2I8u61o7s624BmKwXUtp0mCb0q1hAE2mHtLxntDgmGWGdVvkoG2m1AwH6o4QkZ3l7rVJzdBhcopfuKyYGTTRSs8KN9+7ghRffao4eaa2ndtpZXKxMFZr8Hxyue16ZBzpxhOEKFEhbVy2UhbJQFspCWSgLZaEslIWyUL8Z6g9Ta29lluVxhQAAAABJRU5ErkJggg==",
            "starters"
        ),

        MenuItemRoom(
            5,
            "title5",
            "description5",
            "10",
            "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJQAAACUCAMAAABC4vDmAAAAYFBMVEWkxjn////+/vy71HahxC3d6b/r8tmcwRqoyUTi7Magwyfb6Lry9+iewyOjxTXO357S4qe40m/l7s6zz2Lu9N+YvwDE2oz4+/LX5bGuzFaryk3B14O10Gi91nzJ3ZWxzlzGl4Q4AAAE8ElEQVR4nO2ca5eiMAyGASOVSoOUuyjz///looJctxe07Jw9fc98mQHpYwlpQpNxXD0dCVaFzgeKCslRcxBH83w3AbjonH8BSHTH0IbigKdw+NXzCl5GdZz8VNVPEtdRyQvPG46HDiI3DuXWwPJ+RF7WFSGEAeBTAKz9tapL3nPXDGrtIfShwsyB55BBXTmMorMQUuZUdfA8mTokFF/vK1Buw+Be+DkjsAL0BgPCcr84AGv0R9gAFZ7wFLeTIRMyiJ2JARqE8hpAkCI91Z7YePIrfg7l3+STNBK7+eahatVZGmZL+/HThDoC1UN6iIKmS9eC8vI1DyAXUj3L0oEKb9uYHlQ3nYdQA4o7G25dL+poLDbqUPykaeFTwUmdShnqyDbeul4aEYwqFP+U6eHhVedKEYrjx0wtlapdqUGlorVXgwrT70EVydjGIcsyogSJpD118tEfpVBaBcq7j1c7Grdfl18VHkW4tjFVGo8dCb2reFEVqHLMBLfnZdOTdK7w9FyKvduYn5XfgSomfhw6Y42knpRGrzP5GAqpwg2UQ3mHyUWrbr04ymeq80thNflSB/kNlEMFE+vBU6oLNbvTEHwO5c1MujMKr5ZaOtSvOSmnQSFcpVMlhTqT2ffHp/leVJ6+Z9bqz/0uOX8K5V3ntwmdOihjFQ+PEJ+D2llcQDpVMqhyOSNICVFNHMhaHgYytyCB8u4fxSt/QZV5UAmUr5W5qIpJEhwJVGMGSpI1S6AWVvoVoWxU4dGUyEfYIiIOYcRQlw9SBZGo+L2bGOpg4Nl7CA7boYofIybVGpU42BNC+fKgaSPUSegUhFCBIZNqjUoYKgihzka81ENMuCgLoSJzUNFmqNzc7RP6dBGUkdX4JfGaLIS6ZcSQsngrlMuPxiRM4GdQG17lfkOzYUdQYRnleXPRf5n7ofxLk+dROXrV94YqcgcotD9O/F7CPYPqx0jjfty8mEMF0OfBCFn5+ohnaj0eUlKvzPogHuk7I+yg+CS+7xJu70DRkGgHNUn+EYMxFJ/lHFm5z0yV2eSvCP4Iav5iBzHcAyqcZ49wHaB4tvhQtAdUtLh+xt9Qy8ExKcxDFckiXHuFpA+otQDz8cbANJS/zJ5fIekDKl28L2jzDW4eii9zJbymHdRa1EsC81DBCtQzTrZQFspCWSgLZaEslIWyUBbKQlkoC2WhLJSFslAWykJZKAtloSyUhfqPodY2jLI9NowW24yjDaOVrTXcZWttWVY7bK25y5oW+FebkPf3zqj/e7Zr/WFju5rVJCEWe0AV8/tHq2Fj202nLU3IngUC5ksAgmmLEHQV+F2xhO+MxkfaFcKbr+Aox9vI4HR1Nn1ZSer00AhZV0ixA5QbDGUlrO9UGApwvEsFjFFGr+9CmD2g3CK/toMyBtXlXZQzLlXiZdOUwVDWuwtUe5OCx7h8rVRpRTtBLWWhLNSuUL+x+PR31g4fzUEJO22FUNMmuC/q3ZC3AcpQ24y0cUYM5X2j03cpRHE5sKSthpvo5pH2I8u61o7s624BmKwXUtp0mCb0q1hAE2mHtLxntDgmGWGdVvkoG2m1AwH6o4QkZ3l7rVJzdBhcopfuKyYGTTRSs8KN9+7ghRffao4eaa2ndtpZXKxMFZr8Hxyue16ZBzpxhOEKFEhbVy2UhbJQFspCWSgLZaEslIWyUL8Z6g9Ta29lluVxhQAAAABJRU5ErkJggg==",
            "starters"
        )
    )

    MenuItems(items = items)
}