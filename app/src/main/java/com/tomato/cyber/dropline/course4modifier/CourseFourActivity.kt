package com.tomato.cyber.dropline.course4modifier

import android.graphics.RenderEffect
import android.graphics.Shader.TileMode
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.layout.OnGloballyPositionedModifier
import androidx.compose.ui.layout.OnPlacedModifier
import androidx.compose.ui.layout.OnRemeasuredModifier
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.modifier.ModifierLocal
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.tomato.cyber.R
import com.tomato.cyber.log
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import kotlin.math.min


class CourseFourActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Box(
                Modifier
                    .safeDrawingPadding()
                    .fillMaxSize()
            ) {


            }
        }
    }
}

@Composable
fun ModifierLocal(modifier: Modifier = Modifier) {

    val shearWidthKey = modifierLocalOf { "0" }
    Modifier
        //提供数据
        .modifierLocalProvider(shearWidthKey, { "100" })
        .modifierLocalConsumer {
            //读取数据
            shearWidthKey.current
        }

    Modifier
        .modifierLocalProvider(shearWidthKey, { "yuqiaodan" })
        .layout { measurable, constraints ->
            val placeable = measurable.measure(constraints)

            //怎么把这个witchString局部变量共享到下边的layout去？
            val widthString = placeable.width.toString()

            layout(placeable.width, placeable.height) {
                placeable.placeRelative(0, 0)
            }
        }
        .modifierLocalConsumer {
            shearWidthKey.current
        }
        .layout { measurable, constraints ->

            val placeable = measurable.measure(constraints)
            layout(placeable.width, placeable.height) {
                placeable.placeRelative(0, 0)
            }
        }

}

@Composable
fun PositionedModifierTest(modifier: Modifier = Modifier) {

    Box(
        Modifier
            .size(100.dp)
            .onGloballyPositioned { layoutCoordinates ->
                layoutCoordinates.localToWindow(Offset.Zero)
                layoutCoordinates.localToRoot(Offset.Zero)

                layoutCoordinates.positionInParent()
                layoutCoordinates.positionInWindow()
                layoutCoordinates.positionInRoot()

                //在父布局中的位置 和 宽高 以RECT的方式返回
                layoutCoordinates.boundsInParent()
            })

    Box(Modifier.onPlaced { layoutCoordinates ->
        layoutCoordinates.localToWindow(Offset.Zero)
        layoutCoordinates.localToRoot(Offset.Zero)

        layoutCoordinates.positionInParent()
        layoutCoordinates.positionInWindow()
        layoutCoordinates.positionInRoot()

        //在父布局中的位置 和 宽高 以RECT的方式返回
        layoutCoordinates.boundsInParent()
    })
}

@Composable
fun SemanticsModifierTest(modifier: Modifier = Modifier) {
    Column {
        Text(text = "床前明月光")
        Box(
            Modifier
                .size(100.dp)
                .background(Color.Red)
                //可以清除并重新设置意义
                .clearAndSetSemantics { }
                //使用参数mergeDescendants可以合并子组件的semantics 不再被外部组件合并
                .semantics(mergeDescendants = true) {
                    //使用contentDescription可以设置组件的意义
                    contentDescription = "红色方块"

                }) {
            Text(text = "床前明月光")
        }
        //按钮和Text自动合并 contentDescription
        Button(onClick = {}) {
            Text(text = "床前明月光")
        }
    }
}


@Composable
fun ParentDataModifierTest(modifier: Modifier = Modifier) {
    Row {
        //内部能调用
        CustomLayout2 {
            Box(
                Modifier
                    .size(40.dp)
                    .background(Color.Green)
                    .weightData(1f)
            )
        }

    }
}


//使用CustomLayout2Scope修饰content作用域
@Composable
fun CustomLayout2(
    modifier: Modifier = Modifier,
    content: @Composable @UiComposable CustomLayout2Scope.() -> Unit
) {
    Layout(content = { CustomLayout2Scope.content() }, modifier) { measurables, constraints ->
        measurables.forEach {
            //此处就能获取到通过weightData设置的weightData
            val data = it.parentData as? Float

        }
        layout(100, 100) {

        }
    }
}

//使用LayoutScopeMarker注解可以设置weightData使用范围
@LayoutScopeMarker
//@Immutable//此注解 减少从组过程中不必要的重组 一般使用在接口上 object不需/要
object CustomLayout2Scope {
    fun Modifier.weightData(weight: Float) = then(object : ParentDataModifier {
        override fun Density.modifyParentData(parentData: Any?): Any? {
            //parentData 是连续调用的右边的参数
            return weight
        }
    })
}


/**
 * 高斯模糊测试 - 列表
 * */
@Preview
@Composable
fun FrostedGlassEffectExample1() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val hazeState = rememberHazeState()

        //背景层：将被模糊的内容
        Image(
            painter = painterResource(id = R.mipmap.bg_blur_test),
            contentDescription = "背景图",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                //设置模糊来源（背景）
                .hazeSource(hazeState)
        )

        LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(30) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Color.Transparent
                        )
                        //设置模糊目标（前景）
                        .hazeEffect(state = hazeState, style = HazeStyle.Unspecified),
                ) {
                    // 内容层：负责显示清晰的文字，它没有 blur 修饰符
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "高斯模糊",
                        color = Color.White
                    )
                }
            }
        }

    }
}

/**
 * 高斯模糊测试 - 单组件
 * */
@Preview
@Composable
fun FrostedGlassEffectExample() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val hazeState = rememberHazeState()

        //背景层：将被模糊的内容
        Image(
            painter = painterResource(id = R.mipmap.bg_blur_test),
            contentDescription = "背景图",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                //设置模糊来源（背景）
                .hazeSource(hazeState)
        )
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(300.dp, 200.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    Color.Black.copy(alpha = 0.4f)
                )
                //设置模糊目标（前景）
                .hazeEffect(state = hazeState, style = HazeStyle.Unspecified),
        ) {
            // 内容层：负责显示清晰的文字，它没有 blur 修饰符
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "高斯模糊背景弹窗",
                color = Color.White
            )
        }
    }
}


@Composable
fun PointerInputModifierTest() {


    Box( //可实现：普通点击、长按点击、双击
        Modifier
            .pointerInput(Unit) {
                awaitEachGesture {
                    val down = awaitFirstDown()
                }
                forEachGesture {
                    awaitPointerEventScope {
                        val down = awaitFirstDown()
                    }
                }

                detectTapGestures(
                    //普通点击
                    onTap = {
                        log("点击")
                    },
                    //长按点击
                    onLongPress = {
                        log("长按")
                    },
                    //双击
                    onDoubleTap = {
                        log("双击")
                    },
                    //触摸（按下）
                    onPress = {
                        log("触摸（按下）")
                    }
                )
            }
            .size(100.dp)
            .background(Color.Red))


    /*  Box( //可实现：普通点击、长按点击、双击
          Modifier
              .combinedClickable(
                  //普通点击
                  onClick = {
                      log("点击")
                  },
                  //长按点击
                  onLongClick = {
                      log("长按")
                  },
                  //双击
                  onDoubleClick = {
                      log("双击")
                  }
              )
              .size(100.dp)
              .background(Color.Red))*/


}

@Composable
fun DrawModifierTest() {
    Text(
        "yuqiaodan", Modifier
            .background(Color.Green)
            .drawWithContent {
                //右边位置绘制一个圆
                drawCircle(
                    Color.Red,
                    radius = 5.dp.toPx(),
                    center = Offset(size.width, size.height / 2)
                )
                //绘制原有的绘制内容 也就是drawWithContent右边的绘制内容 和 组件原本的内容
                drawContent()
                //如果不调用 drawContent()，那么组件的绘制内容就不会被绘制 无内容
                //drawContent()
                //中间位置绘制一个圆
                drawCircle(Color.Red, radius = 5.dp.toPx())
            }
            //drawWithContent右边的绘制内容会受到drawContent影响
            .padding(10.dp)
            .background(Color.Yellow))
}


@Composable
fun LayoutModifierTest() {
    Box(Modifier.background(Color.Yellow)) {
        Text("yuqiaodan", Modifier.layout { measurable, constraints ->
            //constraints是父布局对改组件的限制，可以通过constraints设置一个padding
            //比如这里想添加一个2dp的padding,那么就需要将constraints.maxWidth和constraints.maxHeight减去2创建一个newConstraints
            /* val padding = 2.dp.roundToPx()
             val newConstraints = constraints.copy(
                 maxWidth = constraints.maxWidth - padding,
                 maxHeight = constraints.maxWidth - padding
             )*/

            //测量:使用measurable.measure测量自身的尺寸
            val placeable = measurable.measure(constraints)
            //修改1:比如 希望修改组件尺寸 取宽高的最小值设置为正方形
            val size = min(placeable.width, placeable.height)
            //布局:Modifier.layout要求返回一个MeasureResult 通过layout()方法返回
            //布局:layout()方法返回需要传入测量结果进行保存，并且在placementBlock中确认组件的摆放位置
            return@layout layout(size, size, placementBlock = {
                //需要在placementBlock中通过placeable.placeRelative确认组件的摆放位置
                //修改2:比如 希望修改组件摆放位置偏移 x轴向右偏移10px y轴向下偏移5dp
                placeable.placeRelative(10, 5.dp.roundToPx())
            })
        })
    }
}

@Composable
fun LayoutModifierTest2() {
    Modifier.padding(5.dp)
    Box(Modifier.background(Color.Yellow)) {
        Text("yuqiaodan", Modifier.layout { measurable, constraints ->
            //constraints是父布局对改组件的限制,其内部包含了对该组件的宽高上下限限制，可以通过constraints设置一个padding
            //修改3：比如这里想添加一个5dp的padding,那么就需要将constraints.maxWidth和constraints.maxHeight减去2创建一个newConstraints
            val paddingPx = 5.dp.roundToPx()
            val newConstraints = constraints.copy(
                maxWidth = constraints.maxWidth - paddingPx * 2,
                maxHeight = constraints.maxWidth - paddingPx * 2
            )
            //测量:使用measurable.measure测量自身的尺寸
            val placeable = measurable.measure(newConstraints)
            //布局:Modifier.layout要求返回一个MeasureResult 通过layout()方法返回
            //布局:layout()方法返回需要传入测量结果进行保存，并且在placementBlock中确认组件的摆放位置
            return@layout layout(
                placeable.width + paddingPx * 2,
                placeable.height + paddingPx * 2,
                placementBlock = {
                    //需要在placementBlock中通过placeable.placeRelative确认组件的摆放位置
                    placeable.placeRelative(paddingPx, paddingPx)
                })
        })
    }
}
