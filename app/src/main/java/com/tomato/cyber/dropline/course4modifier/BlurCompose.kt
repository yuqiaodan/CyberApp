package com.tomato.cyber.dropline.course4modifier

/**
 * Created by Jordan on 2025/6/20.
 * Description：
 */


import android.os.Build
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.tomato.cyber.R
import dev.chrisbanes.haze.hazeSource

// Assuming you have a Compose theme setup
// For example:
// class MainActivity : ComponentActivity() {
//     override fun onCreate(savedInstanceState: Bundle?) {
//         super.onCreate(savedInstanceState)
//         setContent {
//             MyApplicationTheme {
//                 Surface(
//                     modifier = Modifier.fillMaxSize(),
//                     color = MaterialTheme.colorScheme.background
//                 ) {
//                     DynamicBlurExample()
//                 }
//             }
//         }
//     }
// }


@Composable
fun DynamicBlurExample() {
    // State to control the blur radius
    var blurEnabled by remember { mutableStateOf(false) }

    // Animate the blur radius
    val blurRadius by animateFloatAsState(
        targetValue = if (blurEnabled) 20f else 0f, // 20f for blurred, 0f for no blur
        animationSpec = tween(durationMillis = 500), label = "blurAnimation"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Background content (e.g., an image)

        //背景层：将被模糊的内容
        Image(
            painter = painterResource(id = R.mipmap.bg_blur_test),
            contentDescription = "背景图",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )

        // The Box with dynamic blur
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f) // Take 80% of width
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp)) // Clip to rounded corners
                .background(Color.Black.copy(alpha = 0.3f)) // Semi-transparent background
                .then(
                    // Apply blur only on Android S (API 31) and above
                    // For older versions, this will just be a regular Box
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        Modifier.graphicsLayer {
                            renderEffect = androidx.compose.ui.graphics.BlurEffect(
                                radiusX = blurRadius,
                                radiusY = blurRadius,
                                edgeTreatment = androidx.compose.ui.graphics.TileMode.Decal
                            )
                        }
                    } else {
                        Modifier // No blur for older Android versions
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "This is a blurred Box!",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Button to toggle blur
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ) {
            Button(onClick = { blurEnabled = !blurEnabled }) {
                Text(if (blurEnabled) "Disable Blur" else "Enable Blur")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Note: Blur effect requires Android 12 (API 31) or higher.",
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}



@Composable
fun RealGlassMorphismPopupExample() {
    var showPopup by remember { mutableStateOf(false) }

    val blurRadius by animateFloatAsState(
        targetValue = if (showPopup) 20f else 0f, // Blur when popup is shown
        animationSpec = tween(durationMillis = 500), label = "popupBlurAnimation"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // --- 1. Background Image (remains UNCHANGED and CLEAR) ---
        Image(
            painter = painterResource(id = R.mipmap.bg_blur_test),
            contentDescription = "背景图",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )


        // --- 2. The Real "Glass Morphism" Popup Box (BACKGROUND BLURRED, CONTENT CLEAR) ---
        if (showPopup) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f) // Take 80% of width
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp)) // Clip to rounded corners for the "glass" shape
                    .background(Color.White.copy(alpha = 0.3f)) // Semi-transparent white for the frosted glass effect
                    .then(
                        // This is where the magic happens for Android 12+
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            Modifier.graphicsLayer {
                                renderEffect = androidx.compose.ui.graphics.BlurEffect(
                                    radiusX = blurRadius,
                                    radiusY = blurRadius,
                                    edgeTreatment = androidx.compose.ui.graphics.TileMode.Decal // Important for clean edges
                                )
                            }
                        } else {
                            // Fallback for older versions: just a semi-transparent box, no blur
                            Modifier
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                // --- Content inside the popup (remains clear) ---
                Text(
                    text = "我是清晰的弹窗内容！",
                    color = Color.Black,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        // Button to toggle the popup
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ) {
            Button(onClick = { showPopup = !showPopup }) {
                Text(if (showPopup) "关闭弹窗" else "显示弹窗")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "注意: 真正的毛玻璃效果需要 Android 12 (API 31) 或更高版本。",
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


@Composable
fun RealGlassMorphismDialogExample() {
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // --- 1. Background Image (始终清晰) ---
        Image(
            painter = painterResource(id = R.mipmap.bg_blur_test),
            contentDescription = "背景图",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )

        // Button to toggle the dialog
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ) {
            Button(onClick = { showDialog = !showDialog }) {
                Text(if (showDialog) "关闭毛玻璃弹窗" else "显示毛玻璃弹窗")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "注意: 真正的毛玻璃背景模糊效果需要 Android 12 (API 31) 或更高版本。",
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }

    // --- 2. 真正的毛玻璃弹窗 ---
    if (showDialog) {
        // 使用 Dialog，它会创建一个新的窗口
        Dialog(
            onDismissRequest = { showDialog = false },
            properties = DialogProperties(
                usePlatformDefaultWidth = false // 允许我们自定义宽度
            )
        ) {
            // 获取弹窗的底层 Window
            val dialogWindow = (LocalView.current.parent as? DialogWindowProvider)?.window

            // 动画控制模糊半径
            val targetBlurRadius = if (showDialog) 50 else 0 // 模糊半径可以更大
            val animatedBlurRadius by animateFloatAsState(
                targetValue = targetBlurRadius.toFloat(),
                animationSpec = tween(durationMillis = 500), label = "dialogBlurAnimation"
            )

            // 设置 Window 背景模糊效果
            DisposableEffect(dialogWindow, animatedBlurRadius) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    dialogWindow?.let { window ->
                        // 确保窗口背景是透明的，这样模糊效果才能透过
                        window.setBackgroundDrawable(null)
                        // 设置背景模糊半径
                        window.setBackgroundBlurRadius(animatedBlurRadius.toInt())
                    }
                }
                onDispose {
                    // 弹窗关闭时，或者模糊效果移除时，重置背景模糊
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        dialogWindow?.setBackgroundBlurRadius(0)
                    }
                }
            }

            // --- 弹窗内容 (完全清晰) ---
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f) // 弹窗的宽度
                    .height(200.dp) // 弹窗的高度
                    .clip(RoundedCornerShape(16.dp)) // 弹窗圆角
                    .background(Color.Transparent), // 弹窗自身背景必须是透明的
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "我是清晰的弹窗内容！",
                    color = Color.White, // 在模糊背景上，白色文字更醒目
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}


@Composable
fun SimulatedGlassMorphismEffect() {
    // State to control whether the blur "overlay" is enabled
    var showBlurredOverlay by remember { mutableStateOf(false) }

    // Animate the blur radius for the overlay
    val blurRadius by animateFloatAsState(
        targetValue = if (showBlurredOverlay) 20f else 0f, // 20f for blurred, 0f for no blur
        animationSpec = tween(durationMillis = 500), label = "blurOverlayAnimation"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // --- 1. Background Image (始终清晰，未被修改) ---
        Image(
            painter = painterResource(id = R.mipmap.bg_blur_test),
            contentDescription = "背景图",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )


        // --- 2. 模糊覆盖层 (The "毛玻璃"效果的来源) ---
        // 这个 Box 覆盖在背景图片上，并应用模糊效果。
        // 它本身就是“模糊的背景”，你的弹窗内容会显示在它上面。
        if (showBlurredOverlay) { // 只有在需要模糊效果时才显示此覆盖层
            Box(
                modifier = Modifier
                    .fillMaxSize() // 填充整个屏幕
                    .then(
                        // 仅在 Android 12 (API 31) 及以上版本应用模糊
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            Modifier.graphicsLayer {
                                renderEffect = androidx.compose.ui.graphics.BlurEffect(
                                    radiusX = blurRadius,
                                    radiusY = blurRadius,
                                    // Decal 确保模糊不会扩散到 Composable 的边界之外
                                    edgeTreatment = androidx.compose.ui.graphics.TileMode.Decal
                                )
                            }
                        } else {
                            Modifier // 低版本不应用模糊
                        }
                    )
                    .background(Color.Black.copy(alpha = 0.4f)) // 添加半透明的深色层，增强毛玻璃质感
            )
        }

        // --- 3. 弹窗内容 (保持清晰) ---
        // 这个 Box 位于模糊覆盖层之上，其内容和自身背景保持清晰。
        if (showBlurredOverlay) { // 只有在模糊覆盖层显示时才显示弹窗内容
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f) // 弹窗宽度
                    .height(200.dp) // 弹窗高度
                    .clip(RoundedCornerShape(16.dp)) // 弹窗圆角
                    .background(Color.White.copy(alpha = 0.9f)), // 半透明白色背景，确保内容可读
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "我是清晰的弹窗内容！",
                    color = Color.Black,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        // 切换模糊效果的按钮
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ) {
            Button(onClick = { showBlurredOverlay = !showBlurredOverlay }) {
                Text(if (showBlurredOverlay) "关闭毛玻璃效果" else "开启毛玻璃效果")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "注意: 这种毛玻璃模拟效果需要 Android 12 (API 31) 或更高版本。",
                color = Color.White.copy(alpha = 0.7f),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


@Composable
fun SimpleBlurTest() {
    var isBlurred by remember { mutableStateOf(false) }

    val blurRadius by animateFloatAsState(
        targetValue = if (isBlurred) 20f else 0f,
        animationSpec = tween(durationMillis = 500), label = "simpleBlurAnimation"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // 这是最基础的模糊测试：模糊这个Box本身及其内容
        Box(
            modifier = Modifier
                .fillMaxSize(0.8f) // 占据大部分屏幕
                .background(Color.Blue.copy(alpha = 0.5f)) // 半透明背景
                .then(
                    // 仅在 Android 12+ 应用模糊
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        Modifier.graphicsLayer {
                            renderEffect = androidx.compose.ui.graphics.BlurEffect(
                                radiusX = blurRadius,
                                radiusY = blurRadius,
                                edgeTreatment = androidx.compose.ui.graphics.TileMode.Decal
                            )
                        }
                    } else {
                        Modifier // 低版本不应用模糊
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "这个文本和背景应该被模糊！",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(16.dp)
            )
        }

        Button(
            onClick = { isBlurred = !isBlurred },
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 32.dp)
        ) {
            Text(if (isBlurred) "取消模糊" else "应用模糊")
        }
    }
}
