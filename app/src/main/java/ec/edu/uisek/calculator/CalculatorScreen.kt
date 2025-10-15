package ec.edu.uisek.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.uisek.calculator.ui.theme.Purple40
import ec.edu.uisek.calculator.ui.theme.Red
import ec.edu.uisek.calculator.ui.theme.UiSekBlue

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel= viewModel()
) {
    val state=viewModel.state
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = state.display,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            fontSize = 56.sp,
            textAlign = TextAlign.End,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )

        // Aquí colocaremos la cuadrícula de botones
        CalculatorGrid (onEvent=viewModel::onEvent)
    }
}

@Composable
fun CalculatorGrid(onEvent: (CalculatorEvent)-> Unit ){
    val buttons = listOf(
        "7", "8", "9", "÷",
        "4", "5", "6", "x",
        "1", "2", "3", "−",
        "0", ".", "=", "+"
    )
    LazyVerticalGrid(columns = GridCells.Fixed(4)) {
        items(buttons.size) { index ->
            val label = buttons[index]
            CalculatorButton(label = label) {
                when (label) {
                    in "0".."9" -> onEvent(CalculatorEvent.Number(label))
                    "." -> onEvent(CalculatorEvent.Decimal)
                    "=" -> onEvent(CalculatorEvent.Calculate)
                    else -> onEvent(CalculatorEvent.Operator(label))
                }
            }
        }

        item(span = { GridItemSpan(2) }) { CalculatorButton(label = "AC") { onEvent(CalculatorEvent.AllClear) } }
        item {}
        item { CalculatorButton(label = "C") { onEvent(CalculatorEvent.Clear) } }
    }


}


@Composable
fun CalculatorButton(label: String, onClick: () -> Unit) {
    Box (
        modifier = Modifier
            .aspectRatio(ratio = if(label=="AC")2f else 1f)
            .fillMaxSize()
            .clip(CircleShape)
            .background(color = when (label){
                in listOf("÷", "x", "−", "+", "=", ".")->Purple40
                in listOf("AC","C")-> Red
                else -> UiSekBlue
            }
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ){
        Text(
            text = label,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorScreen()
}