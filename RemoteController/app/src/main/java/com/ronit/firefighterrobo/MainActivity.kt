package com.ronit.firefighterrobo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.database.*
import com.ronit.firefighterrobo.ui.theme.FireFighterRoboTheme
import kotlin.properties.Delegates

class MainActivity : ComponentActivity() {

    private lateinit var database: DatabaseReference
    private var up by Delegates.notNull<Int>()
    private var left by Delegates.notNull<Int>()
    private var right by Delegates.notNull<Int>()
    private var down by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        val interactionSource= MutableInteractionSource()
        val interactionSource1= MutableInteractionSource()
        val interactionSource2= MutableInteractionSource()
        val interactionSource3= MutableInteractionSource()
        super.onCreate(savedInstanceState)

        setContent {

            up=  if (interactionSource.collectIsPressedAsState().value) 1 else 0
            left=  if (interactionSource1.collectIsPressedAsState().value) 1 else 0
            right=   if (interactionSource2.collectIsPressedAsState().value) 1 else 0
            down=  if (interactionSource3.collectIsPressedAsState().value) 1 else 0


            database= FirebaseDatabase.getInstance().getReference("buttons")
            val postListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    database.child("up").setValue(up)
                    database.child("left").setValue(left)
                    database.child("right").setValue(right)
                    database.child("down").setValue(down)

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("error","error")
                }
            }

            database.addValueEventListener(postListener)

            FireFighterRoboTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 50.dp), horizontalAlignment =Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround) {
                        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.Bottom) {
                            LargeFloatingActionButton(onClick ={} , interactionSource = interactionSource) {
                                Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "Reverse", modifier = Modifier.size(100.dp))

                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .weight(0.6f)
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),horizontalArrangement = Arrangement.SpaceBetween) {

                            LargeFloatingActionButton(onClick ={} , interactionSource = interactionSource1) {
                                Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Reverse", modifier = Modifier.size(100.dp))

                            }

                            LargeFloatingActionButton(onClick ={} , interactionSource = interactionSource2) {
                                Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "Reverse", modifier = Modifier.size(100.dp))

                            }
                        }

                        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.Top){
                        LargeFloatingActionButton(onClick ={} , interactionSource = interactionSource3) {
                            Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "Reverse", modifier = Modifier.size(100.dp))
                        }
                        }
                    }
                }
            }
        }
    }
}

