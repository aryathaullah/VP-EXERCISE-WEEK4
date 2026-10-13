
package com.example.vp_exercise_week4

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val darkBg = Color(0xFF0C0D14)
val cardBg = Color(0xFF171927)
val boxBg = Color(0xFF202235)
val yellow = Color(0xFFFFB900)
val gray = Color(0xFF9297AC)
val hpColor = Color(0xFFFF315E)
val mpColor = Color(0xFF169FFF)
val green = Color(0xFF26D68A)

data class Hero(
    val id: Int,
    val name: String,
    val heroClass: String,
    val rarity: String,
    val level: Int,
    val hp: Int,
    val maxHp: Int,
    val mp: Int,
    val maxMp: Int,
    val cp: Int,
    val atk: Int,
    val def: Int,
    val crit: Int,
    val spd: Int,
    val skill: String,
    val description: String,
    val image: Int
)

val heroes = listOf(
    Hero(1, "Alistair", "Paladin", "MYTHIC", 50,
        1650, 1800, 650, 800, 5600, 380, 540, 22, 105,
        "Wrath of the Heavens",
        "Calls down a celestial hammer, stunning all enemies for 3 seconds and taunting survivors.",
        R.drawable.paladin),

    Hero(2, "Theresa", "Cleric", "MYTHIC", 49,
        1100, 1200, 2100, 2400, 5480, 220, 410, 15, 120,
        "Divine Blessing",
        "Restores HP to all allies and increases their defense.",
        R.drawable.cleric),

    Hero(3, "Vespera", "Rogue", "MYTHIC", 48,
        890, 950, 410, 600, 5350, 520, 220, 35, 160,
        "Shadow Strike",
        "Deals critical damage to an enemy using a powerful shadow attack.",
        R.drawable.rogue),

    Hero(4, "Elaria", "Archmage", "MYTHIC", 50,
        450, 600, 1950, 2200, 5120, 580, 180, 30, 115,
        "Arcane Storm",
        "Summons a magical storm that damages all enemies.",
        R.drawable.archmage),

    Hero(5, "Kael", "Berserker", "LEGENDARY", 47,
        1400, 1600, 300, 500, 4980, 610, 260, 28, 130,
        "Berserker Rage",
        "Increases attack power and deals massive damage to enemies.",
        R.drawable.berserker)
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal2(){

    var search by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    var expandedId by remember { mutableStateOf<Int?>(null) }
    var sortPower by remember { mutableStateOf(true) }
    var showDeploy by remember { mutableStateOf(false) }

    val squad = remember { mutableStateListOf(3, 4, 5) }
    val hpValues = remember { mutableStateMapOf<Int, Int>() }
    val mpValues = remember { mutableStateMapOf<Int, Int>() }

    var message by remember { mutableStateOf("") }

    val filters = listOf("All", "Berserker", "Archmage", "Paladin", "Cleric", "Rogue")

    val filteredHeroes = heroes.filter {
        (selectedFilter == "All" || it.heroClass == selectedFilter) &&
                (it.name.contains(search, true) || it.heroClass.contains(search, true) ||
                        it.rarity.contains(search, true))
    }.let {
        if(sortPower) it.sortedByDescending { hero -> hero.cp }
        else it.sortedBy { hero -> hero.name }
    }

    val totalCp = heroes.filter { squad.contains(it.id) }.sumOf { it.cp }

    Column(modifier = Modifier.fillMaxSize().background(darkBg)){

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 20.dp)
        ){

            item {

                Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 28.dp)){

                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically){

                        Column{
                            Text("Party Roster", color = Color.White, fontSize = 27.sp, fontWeight = FontWeight.Bold)
                            Text("Guild Vanguard · 14 Heroes Available", color = gray, fontSize = 11.sp)
                        }

                        Text("★ Expedition", color = yellow, fontSize = 10.sp,
                            modifier = Modifier.border(1.dp, yellow, RoundedCornerShape(20.dp))
                                .background(Color(0xFF352A0B), RoundedCornerShape(20.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp))
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Column(modifier = Modifier.fillMaxWidth()
                        .border(1.dp, Color(0xFF675029), RoundedCornerShape(17.dp))
                        .background(Color(0xFF201D32), RoundedCornerShape(17.dp))
                        .padding(12.dp)){

                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween){

                            Text("●  EXPEDITION SQUAD (${squad.size}/4)", color = gray,
                                fontSize = 11.sp, fontWeight = FontWeight.Bold)

                            Text("⚔ $totalCp CP", color = yellow, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(verticalAlignment = Alignment.CenterVertically){

                            for(id in squad){
                                val hero = heroes.first { it.id == id }

                                Box(modifier = Modifier.padding(end = 6.dp).size(43.dp)
                                    .border(2.dp, yellow, RoundedCornerShape(9.dp))
                                    .clip(RoundedCornerShape(9.dp))
                                    .background(Color.White)
                                    .clickable { expandedId = id },
                                    contentAlignment = Alignment.Center){

                                    Image(painter = painterResource(hero.image),
                                        contentDescription = hero.name,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Fit)
                                }
                            }

                            if(squad.size < 4){
                                Box(modifier = Modifier.size(43.dp)
                                    .border(1.dp, Color(0xFF36384E), RoundedCornerShape(9.dp))
                                    .clickable { message = "Choose a hero from the roster" },
                                    contentAlignment = Alignment.Center){

                                    Text("+", color = gray, fontSize = 20.sp)
                                }
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Button(onClick = {
                                showDeploy = !showDeploy
                            }, shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = yellow),
                                contentPadding = PaddingValues(horizontal = 12.dp),
                                modifier = Modifier.height(40.dp)){

                                Text("Deploy ⚡", color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    if(showDeploy){
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Squad deployed! Total Power: $totalCp CP",
                            color = green, fontSize = 12.sp)
                    }

                    if(message.isNotEmpty()){
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(message, color = yellow, fontSize = 11.sp)
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(modifier = Modifier.fillMaxWidth().height(44.dp)
                        .border(1.dp, Color(0xFF33364D), RoundedCornerShape(12.dp))
                        .background(cardBg, RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically){

                        Text("⌕", color = gray, fontSize = 24.sp)

                        Spacer(modifier = Modifier.width(8.dp))

                        Box(modifier = Modifier.weight(1f)){
                            if(search.isEmpty()){
                                Text("Search by hero name, class, or rarity...",
                                    fontSize = 11.sp, color = Color(0xFF6C728B),
                                    maxLines = 1)
                            }

                            BasicTextField(
                                value = search,
                                onValueChange = { search = it },
                                singleLine = true,
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontSize = 12.sp, color = Color.White),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            item {

                Row(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, bottom = 18.dp),
                    verticalAlignment = Alignment.CenterVertically){

                    LazyRow(modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(7.dp)){

                        items(filters){ filter ->

                            val active = selectedFilter == filter
                            val count = if(filter == "All") 14
                            else when(filter){
                                "Berserker" -> 2
                                "Archmage" -> 3
                                "Paladin" -> 3
                                "Cleric" -> 3
                                else -> 3
                            }

                            Box(modifier = Modifier
                                .border(1.dp, if(active) yellow else Color(0xFF33364D),
                                    RoundedCornerShape(20.dp))
                                .background(if(active) Color(0xFF3B2B09) else cardBg,
                                    RoundedCornerShape(20.dp))
                                .clickable {
                                    selectedFilter = filter
                                }
                                .padding(horizontal = 12.dp, vertical = 6.dp)){

                                Text("$filter ($count)", fontSize = 11.sp,
                                    color = if(active) yellow else gray)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(7.dp))

                    Text(if(sortPower) "Sort: Power" else "Sort: Name",
                        fontSize = 10.sp, color = yellow,
                        modifier = Modifier.padding(end = 12.dp)
                            .background(boxBg, RoundedCornerShape(8.dp))
                            .clickable { sortPower = !sortPower }
                            .padding(8.dp))
                }
            }

            items(filteredHeroes){ hero ->

                HeroCard(
                    hero = hero,
                    expanded = expandedId == hero.id,
                    inSquad = squad.contains(hero.id),
                    hp = hpValues[hero.id] ?: hero.hp,
                    mp = mpValues[hero.id] ?: hero.mp,
                    onExpand = {
                        expandedId = if(expandedId == hero.id) null else hero.id
                    },
                    onHeal = {
                        val current = hpValues[hero.id] ?: hero.hp
                        hpValues[hero.id] = (current + 250).coerceAtMost(hero.maxHp)
                    },
                    onSkill = {
                        val current = mpValues[hero.id] ?: hero.mp
                        if(current >= 100){
                            mpValues[hero.id] = current - 100
                            message = "${hero.name} used ${hero.skill}!"
                        }else{
                            message = "Not enough MP!"
                        }
                    },
                    onSquad = {
                        if(squad.contains(hero.id)){
                            squad.remove(hero.id)
                            message = "${hero.name} removed from squad"
                        }else if(squad.size < 4){
                            squad.add(hero.id)
                            message = "${hero.name} added to squad"
                        }else{
                            message = "Squad is full! Maximum 4 heroes"
                        }
                    }
                )
            }

            if(filteredHeroes.isEmpty()){
                item{
                    Box(modifier = Modifier.fillMaxWidth().padding(30.dp),
                        contentAlignment = Alignment.Center){
                        Text("No heroes found", color = gray)
                    }
                }
            }
        }
    }
}

@Composable
fun HeroCard(
    hero: Hero,
    expanded: Boolean,
    inSquad: Boolean,
    hp: Int,
    mp: Int,
    onExpand: () -> Unit,
    onHeal: () -> Unit,
    onSkill: () -> Unit,
    onSquad: () -> Unit
){

    val heroColor = when(hero.heroClass){
        "Paladin" -> yellow
        "Cleric" -> green
        "Rogue" -> Color(0xFFD335FC)
        "Archmage" -> Color.Cyan
        else -> hpColor
    }

    Card(
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
            .border(1.dp, if(inSquad) yellow else Color(0xFF34374C),
                RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg)
    ){

        Column(modifier = Modifier.fillMaxWidth().padding(12.dp)){

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){

                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)){

                    Text(hero.rarity, color = hpColor, fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.border(1.dp, hpColor, RoundedCornerShape(3.dp))
                            .background(Color(0xFF371525)).padding(horizontal = 5.dp, vertical = 2.dp))

                    Text(hero.heroClass, fontSize = 10.sp, fontWeight = FontWeight.Bold,
                        color = heroColor,
                        modifier = Modifier.background(heroColor.copy(alpha = 0.14f),
                            RoundedCornerShape(3.dp)).padding(horizontal = 5.dp, vertical = 2.dp))
                }

                Row(verticalAlignment = Alignment.CenterVertically){

                    Text("⚔ ${hero.cp} CP", color = yellow, fontSize = 11.sp,
                        fontWeight = FontWeight.Bold)

                    if(inSquad){
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("IN SQUAD", fontSize = 8.sp, color = yellow,
                            modifier = Modifier.border(1.dp, yellow, RoundedCornerShape(4.dp))
                                .padding(3.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(modifier = Modifier.fillMaxWidth().clickable { onExpand() },
                verticalAlignment = Alignment.CenterVertically){

                Box(modifier = Modifier.size(68.dp)
                    .border(2.dp, heroColor, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)){

                    Image(painter = painterResource(hero.image),
                        contentDescription = hero.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit)

                    Text("Lvl ${hero.level}", fontSize = 9.sp, color = Color.White,
                        modifier = Modifier.align(Alignment.BottomEnd)
                            .background(Color(0xFF252638), RoundedCornerShape(topStart = 5.dp))
                            .padding(3.dp))
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)){

                    Text(hero.name, color = Color.White, fontSize = 17.sp,
                        fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(8.dp))

                    StatBar("HP", hp, hero.maxHp, hpColor)

                    Spacer(modifier = Modifier.height(5.dp))

                    StatBar("MP", mp, hero.maxMp, mpColor)
                }

                Text(if(expanded) "⌃" else "⌄", color = gray, fontSize = 23.sp,
                    modifier = Modifier.padding(start = 6.dp))
            }

            if(expanded){

                Spacer(modifier = Modifier.height(14.dp))

                HorizontalDivider(color = Color(0xFF303349))

                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)){

                    StatBox("ATK", hero.atk.toString(), hpColor, Modifier.weight(1f))
                    StatBox("DEF", hero.def.toString(), green, Modifier.weight(1f))
                    StatBox("CRIT", "${hero.crit}%", yellow, Modifier.weight(1f))
                    StatBox("SPD", hero.spd.toString(), mpColor, Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(modifier = Modifier.fillMaxWidth()
                    .border(1.dp, Color(0xFF303349), RoundedCornerShape(10.dp))
                    .background(boxBg, RoundedCornerShape(10.dp))
                    .padding(10.dp)){

                    Text("★  ${hero.skill}", color = yellow,
                        fontSize = 11.sp, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(hero.description, color = gray, fontSize = 10.sp,
                        lineHeight = 15.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)){

                    Box(modifier = Modifier.weight(1f).height(38.dp)
                        .border(1.dp, green, RoundedCornerShape(9.dp))
                        .background(Color(0xFF193229), RoundedCornerShape(9.dp))
                        .clickable { onHeal() },
                        contentAlignment = Alignment.Center){

                        Text("🧪 Heal (+250 HP)", color = green,
                            fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    Box(modifier = Modifier.weight(1f).height(38.dp)
                        .border(1.dp, yellow, RoundedCornerShape(9.dp))
                        .background(Color(0xFF3D3015), RoundedCornerShape(9.dp))
                        .clickable { onSquad() },
                        contentAlignment = Alignment.Center){

                        Text(if(inSquad) "✕ Remove" else "⚔ Add to Squad",
                            color = yellow, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(modifier = Modifier.fillMaxWidth().height(34.dp)
                    .background(boxBg, RoundedCornerShape(8.dp))
                    .clickable { onSkill() },
                    contentAlignment = Alignment.Center){

                    Text("Use Skill (-100 MP)", color = Color(0xFFB8BDE3), fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
fun StatBar(
    label: String,
    value: Int,
    maxValue: Int,
    color: Color
){

    Row(verticalAlignment = Alignment.CenterVertically){

        Text(label, color = gray, fontSize = 10.sp,
            fontWeight = FontWeight.Bold, modifier = Modifier.width(28.dp))

        Box(modifier = Modifier.weight(1f).height(7.dp)
            .background(Color(0xFF323446), RoundedCornerShape(10.dp))){

            Box(modifier = Modifier.fillMaxWidth(
                (value.toFloat() / maxValue.toFloat()).coerceIn(0f, 1f))
                .fillMaxHeight()
                .background(color, RoundedCornerShape(10.dp)))
        }

        Spacer(modifier = Modifier.width(7.dp))

        Text("$value / $maxValue", color = gray, fontSize = 9.sp,
            modifier = Modifier.width(75.dp))
    }
}

@Composable
fun StatBox(
    label: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
){

    Column(modifier = modifier.background(boxBg, RoundedCornerShape(8.dp))
        .padding(vertical = 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally){

        Text(label, fontSize = 9.sp, color = color, fontWeight = FontWeight.Bold)

        Text(value, fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
    }
}
