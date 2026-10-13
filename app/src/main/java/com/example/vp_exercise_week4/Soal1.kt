
package com.example.vp_exercise_week4

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
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

val orange = Color(0xFFFF6547)
val bgColor = Color(0xFFF7F5F2)
val darkColor = Color(0xFF172536)
val grayColor = Color(0xFF8493A5)

data class Car(
    val id: Int,
    val name: String,
    val year: Int,
    val mileage: String,
    val transmission: String,
    val fuel: String,
    val price: String,
    val location: String,
    val description: String,
    val image: Int
)

val carList = listOf(
    Car(1, "Tiggo 9 CSH", 2024, "5,000 km", "Auto", "PHEV",
        "Rp 450.000.000", "Surabaya",
        "Premium SUV with hybrid technology and modern features.",
        R.drawable.tiggo),

    Car(2, "Honda CRV", 2013, "15,200 km", "Auto", "Petrol",
        "Rp 215.000.000", "Surabaya",
        "Comfortable family SUV with spacious interior.",
        R.drawable.crv),

    Car(3, "Toyota Yaris Cross", 2024, "30,500 km", "Auto", "Hybrid",
        "Rp 350.000.000", "Surabaya",
        "Modern compact SUV with efficient hybrid engine.",
        R.drawable.yariscross),

    Car(4, "Daihatsu Xenia", 2022, "28,000 km", "Manual", "Petrol",
        "Rp 185.000.000", "Surabaya",
        "Practical family car with comfortable seating.",
        R.drawable.xenia),

    Car(5, "Toyota Yaris", 2021, "52,400 km", "Auto", "Petrol",
        "Rp 245.000.000", "Semarang",
        "The Toyota Yaris G AT is a sporty compact hatchback that punches above its weight class. Equipped with a 1.5L dual VVT-i engine, 7 airbags, pre-collision system, and a floating head unit, it offers premium features at a very accessible price point.",
        R.drawable.yaris),

    Car(6, "Daihatsu Ayla", 2022, "20,000 km", "Auto", "Petrol",
        "Rp 125.000.000", "Surabaya",
        "Compact city car suitable for daily transportation.",
        R.drawable.ayla),

    Car(7, "Honda HRV", 2023, "12,000 km", "Auto", "Petrol",
        "Rp 310.000.000", "Surabaya",
        "Modern SUV with comfortable interior and stylish design.",
        R.drawable.hrv),

    Car(8, "Jaecoo J5", 2025, "3,000 km", "Auto", "Electric",
        "Rp 350.000.000", "Jakarta",
        "Modern electric SUV with premium design.",
        R.drawable.j5),

    Car(9, "MG4 EV", 2024, "8,000 km", "Auto", "Electric",
        "Rp 320.000.000", "Jakarta",
        "Electric hatchback with modern technology.",
        R.drawable.mg4),

    Car(10, "Toyota Kijang Innova Reborn", 2021, "45,000 km", "Manual", "Diesel",
        "Rp 280.000.000", "Surabaya",
        "Spacious family MPV with reliable diesel engine.",
        R.drawable.reborn)

)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Soal1(){

    var selectedCar by remember { mutableStateOf<Car?>(null) }
    val favorites = remember { mutableStateListOf(1, 3, 5) }

    if(selectedCar == null){
        CarHome(favorites = favorites, onCarClick = { selectedCar = it })
    }else{
        CarDetail(car = selectedCar!!, favorites = favorites, onBack = { selectedCar = null })
    }
}

@Composable
fun CarHome(
    favorites: SnapshotStateList<Int>,
    onCarClick: (Car) -> Unit
){

    Column(modifier = Modifier.fillMaxSize().background(bgColor)){

        Column(modifier = Modifier.fillMaxWidth().padding(start = 18.dp, end = 18.dp, top = 20.dp, bottom = 16.dp)){

            Box(modifier = Modifier.width(28.dp).height(3.dp).background(orange, RoundedCornerShape(5.dp)))

            Spacer(modifier = Modifier.height(10.dp))

            Text("On Sale", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = darkColor)

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){

                Text("Find your perfect ride", fontSize = 12.sp, color = grayColor)

                Text("♥ ${favorites.size} saved", fontSize = 11.sp, color = orange,
                    modifier = Modifier.background(Color(0xFFFFEDE8), RoundedCornerShape(20.dp)).border(1.dp, Color(0xFFFFC8BB), RoundedCornerShape(20.dp)).padding(horizontal = 12.dp, vertical = 7.dp))
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){

            items(carList){ car ->

                CarItem(
                    car = car,
                    isFavorite = favorites.contains(car.id),
                    onFavoriteClick = {
                        if(favorites.contains(car.id)) favorites.remove(car.id)
                        else favorites.add(car.id)
                    },
                    onClick = { onCarClick(car) }
                )
            }
        }
    }
}

@Composable
fun CarItem(
    car: Car,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit
){

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ){

        Row(modifier = Modifier.fillMaxWidth().padding(10.dp), verticalAlignment = Alignment.CenterVertically){

            Box(modifier = Modifier.size(100.dp).clip(RoundedCornerShape(12.dp))){

                Image(painter = painterResource(car.image), contentDescription = car.name,
                    modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)

                Text(car.fuel.uppercase(), fontSize = 9.sp, color = orange,
                    modifier = Modifier.align(Alignment.BottomStart).padding(4.dp).background(Color.White, RoundedCornerShape(5.dp)).padding(5.dp))
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)){

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){

                    Text(car.name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = darkColor,
                        maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f))

                    Text(if(isFavorite) "♥" else "♡", fontSize = 25.sp,
                        color = if(isFavorite) orange else grayColor,
                        modifier = Modifier.clickable { onFavoriteClick() }.padding(start = 6.dp))
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(car.transmission, fontSize = 10.sp, color = grayColor,
                    modifier = Modifier.background(bgColor, RoundedCornerShape(5.dp)).padding(6.dp))

                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){

                    Text("● ${car.year}", fontSize = 11.sp, color = darkColor)
                    Text(car.mileage, fontSize = 10.sp, color = grayColor)
                }
            }
        }
    }
}

@Composable
fun CarDetail(
    car: Car,
    favorites: SnapshotStateList<Int>,
    onBack: () -> Unit
){

    val isFavorite = favorites.contains(car.id)

    Column(modifier = Modifier.fillMaxSize().background(bgColor)){

        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())){

            Box(modifier = Modifier.fillMaxWidth().height(250.dp)){

                Image(painter = painterResource(car.image), contentDescription = car.name,
                    contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())

                Box(modifier = Modifier.padding(start = 14.dp, top = 14.dp).size(38.dp)
                    .background(Color.White, CircleShape).clickable { onBack() },
                    contentAlignment = Alignment.Center){

                    Text("←", fontSize = 24.sp, color = darkColor)
                }
            }

            Box(modifier = Modifier.fillMaxWidth()){

                Column(modifier = Modifier.fillMaxWidth().offset(y = (-18).dp)
                    .clip(RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp))
                    .background(bgColor).padding(start = 16.dp, end = 16.dp, top = 18.dp)){

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){

                        Text(car.name, fontSize = 23.sp, fontWeight = FontWeight.Bold, color = darkColor,
                            modifier = Modifier.weight(1f))

                        Text(if(isFavorite) "♥" else "♡", fontSize = 28.sp,
                            color = if(isFavorite) orange else grayColor,
                            modifier = Modifier.clickable {
                                if(isFavorite) favorites.remove(car.id)
                                else favorites.add(car.id)
                            })
                    }

                    Spacer(modifier = Modifier.height(3.dp))

                    Row(verticalAlignment = Alignment.CenterVertically){
                        Text("●", fontSize = 11.sp, color = orange)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(car.location, fontSize = 11.sp, color = grayColor)
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Box(modifier = Modifier.border(1.dp, Color(0xFFFFC8BB), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)){

                        Text(car.price, fontSize = 14.sp, color = orange, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text("SPECIFICATIONS", fontSize = 10.sp, color = Color(0xFFA9B5C1),
                        fontWeight = FontWeight.Bold, letterSpacing = 1.sp)

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)){

                        SpecificationBox("YEAR", car.year.toString(), Modifier.weight(1f))
                        SpecificationBox("MILEAGE", car.mileage, Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)){

                        SpecificationBox("TRANSMISSION", car.transmission, Modifier.weight(1f))
                        SpecificationBox("FUEL TYPE", car.fuel, Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text("ABOUT THIS CAR", fontSize = 10.sp, color = Color(0xFFA9B5C1),
                        fontWeight = FontWeight.Bold, letterSpacing = 1.sp)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(car.description, fontSize = 12.sp, color = grayColor, lineHeight = 19.sp)

                    Spacer(modifier = Modifier.height(4.dp))
                }

                Text(car.fuel.uppercase(), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = orange,
                    modifier = Modifier.offset(y = (-40).dp).padding(start = 22.dp)
                        .background(Color.White, RoundedCornerShape(topStart = 7.dp, topEnd = 7.dp))
                        .padding(horizontal = 9.dp, vertical = 7.dp))
            }
        }

        Row(modifier = Modifier.fillMaxWidth().background(bgColor)
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)){

            Box(modifier = Modifier.size(48.dp).border(1.dp, orange, RoundedCornerShape(10.dp))
                .clickable {
                    if(isFavorite) favorites.remove(car.id)
                    else favorites.add(car.id)
                }, contentAlignment = Alignment.Center){

                Text(if(isFavorite) "♥" else "♡", fontSize = 25.sp, color = orange)
            }

            Button(onClick = {}, modifier = Modifier.weight(1f).height(48.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = orange)){

                Text("Contact Seller", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
fun SpecificationBox(
    title: String,
    value: String,
    modifier: Modifier = Modifier
){

    Column(modifier = modifier.height(66.dp)
        .border(1.dp, Color(0xFFE8E8E8), RoundedCornerShape(10.dp))
        .background(Color.White, RoundedCornerShape(10.dp)).padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween){

        Text(title, fontSize = 9.sp, color = Color(0xFFA9B5C1), letterSpacing = 0.5.sp)

        Text(value, fontSize = 13.sp, color = darkColor, fontWeight = FontWeight.Bold)
    }
}
