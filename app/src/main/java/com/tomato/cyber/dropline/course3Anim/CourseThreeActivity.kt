package com.tomato.cyber.dropline.course3Anim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

class CourseThreeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Box(
                Modifier
                    .fillMaxSize()
                    .safeDrawingPadding()
            ) {
                AnimatedContentTest()
            }
        }
    }
}

@Preview
@Composable
fun AnimTestScreen() {
    var big by remember { mutableStateOf(false) }
    //创建和更新Transition updateTransition 内部调用了remember 不需要再调用
    val bigTransition = updateTransition(big, label = "big")
    bigTransition.AnimatedVisibility(visible = { it }) {


    }

    val size by bigTransition.animateDp(
        transitionSpec = {
            /* if (!initialState && targetState) {
                 spring()
             } else {
                 tween()
             }*/
            when {
                false isTransitioningTo true -> {
                    spring()
                }

                true isTransitioningTo false -> {
                    tween()
                }

                else -> {
                    tween()
                }
            }
        },
        label = "size"
    ) { state ->
        (if (state) {
            96.dp
        } else {
            48.dp
        })
    }
    //Transition可以同时设置多个属性的状态动画 比如 这里同时设置大小和圆角
    val corner by bigTransition.animateDp(label = "corner") { state -> if (state) 0.dp else 16.dp }
    Box(
        Modifier
            .size(size)
            .clip(RoundedCornerShape(corner))
            .background(Color.Green)
            .clickable {
                big = !big
            })
}

//原函数使用animateDpAsState实现
@Preview
@Composable
fun AnimTestScreen1() {
    var big by remember { mutableStateOf(false) }
    val size by animateDpAsState(
        if (big) {
            96.dp
        } else {
            48.dp
        }, label = "size"
    )
    val corner by animateDpAsState(
        if (big) {
            16.dp
        } else {
            0.dp
        }, label = "corner"
    )
    Box(
        Modifier
            .clip(RoundedCornerShape(corner))
            .size(size)
            .background(Color.Green)
            .clickable {
                big = !big
            })
}


@Composable
fun AnimateDecayTest() {
    //使用BoxWithConstraints可以为其内部提供边界限制
    BoxWithConstraints {
        val animX = remember { Animatable(0.dp, Dp.VectorConverter) }
        val animY = remember { Animatable(0.dp, Dp.VectorConverter) }
        val decay = remember { exponentialDecay<Dp>() }
        LaunchedEffect(Unit) {
            delay(1000L)
            animX.animateDecay(4000.dp, decay)
        }
        LaunchedEffect(Unit) {
            delay(1000L)
            animY.animateDecay(3000.dp, decay)
        }
        //通过updateBounds限制动画的边界 使方块移动到右边后停止
        animY.updateBounds(upperBound = maxHeight - 100.dp)
        //动态计算X轴偏移 移除animX边界
        val offsetX = remember(animX.value) {
            var useValue = animX.value
            val boundsEnd = (maxWidth - 100.dp)
            //处理多次反弹 计算逻辑
            while (useValue >= (boundsEnd * 2)) {
                useValue -= (boundsEnd * 2)
            }
            //小于边界 使用当前值 大于边界boundsEnd*2 使用boundsEnd*2-useValue
            if (useValue < boundsEnd) {
                useValue
            } else {
                boundsEnd * 2 - useValue
            }
        }

        Box(
            Modifier
                .offset {
                    IntOffset(offsetX.roundToPx(), animY.value.roundToPx())
                }
                .size(100.dp)
                .background(Color.Green)
        )
    }
}

@Composable
fun AnimateDecayTest1() {
    BoxWithConstraints {
        // 创建二维Animatable，初始值为(0.dp, 0.dp)
        val anim = remember {
            Animatable(
                initialValue = Offset(0.dp.value, 0.dp.value),
                typeConverter = Offset.VectorConverter
            )
        }
        // 创建衰减动画规格
        val decay = exponentialDecay<Offset>()
        // 在1秒后同时启动X和Y方向的衰减动画
        LaunchedEffect(Unit) {
            delay(1000L)
            // 启动二维衰减动画
            anim.animateDecay(
                initialVelocity = Offset(3000.dp.value, 3000.dp.value), animationSpec = decay
            )
        }
        // 设置边界限制
        val maxX = (maxWidth - 100.dp).value
        val maxY = (maxHeight - 100.dp).value
        anim.updateBounds(upperBound = Offset(maxX, maxY))
        Box(
            Modifier
                // 使用Offset中的x和y值
                .padding(
                    start = anim.value.x.dp, top = anim.value.y.dp
                )
                .size(100.dp)
                .background(Color.Green)
        )
    }
}


@Composable
fun AnimVisibilityTest() {
    Column {
        var isShow by remember { mutableStateOf(false) }
        AnimatedVisibility(
            isShow,
            enter = fadeIn(initialAlpha = 0.3f) + fadeIn(initialAlpha = 0.5f),
            exit = scaleOut()
        ) {
            Spacer(
                Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .size(100.dp)
                    .background(Color.Green)
            )
            Spacer(
                Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .size(50.dp)
                    .background(Color.Red)
            )
        }
        Button(onClick = {
            isShow = !isShow
        }) {
            Text("切换")
        }
    }
}


@Composable
fun AnimatedContentTest() {
    Column {
        var type by remember { mutableStateOf(0) }
        AnimatedContent(
            type,
            transitionSpec = {
                initialState
                targetState
                when {
                    0 isTransitioningTo 1 -> {
                        //直接使用构造函数创建ContentTransform
                        ContentTransform(fadeIn(), fadeOut())
                    }

                    1 isTransitioningTo 2 -> {
                        //也可以使用togetherWith创建ContentTransform
                        (fadeIn() togetherWith fadeOut())
                    }

                    2 isTransitioningTo 0 -> {
                        //设置目标状态0的内容被起始状态3的内容覆盖
                        /*ContentTransform(
                            fadeIn(tween(3000)),
                            fadeOut(tween(3000, 3000)),
                            targetContentZIndex = -1f, SizeTransform(false)
                        )*/
                        //以上写法和以下写法完全一致
                        (fadeIn(tween(3000)) togetherWith
                                fadeOut(tween(3000, 3000)))
                            .apply {
                                targetContentZIndex = -1f
                            }.using(SizeTransform(false))
                    }

                    else -> {
                        ContentTransform(fadeIn(), fadeOut())
                    }
                }
            }
        ) {
            when (it) {
                0 -> {
                    Spacer(
                        Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .size(50.dp)
                            .background(Color.Green)
                    )
                }

                1 -> {
                    Spacer(
                        Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .size(100.dp)
                            .background(Color.Red)
                    )
                }

                2 -> {
                    Spacer(
                        Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .size(160.dp)
                            .background(Color.Blue)
                    )
                }
            }
        }
        Button(onClick = {
            type = (type + 1) % 3
        }) {
            Text("切换")
        }
    }
}