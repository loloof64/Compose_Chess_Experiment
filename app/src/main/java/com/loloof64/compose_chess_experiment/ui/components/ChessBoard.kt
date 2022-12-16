package com.loloof64.compose_chess_experiment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp

@Composable
fun ChessBoard(
    modifier: Modifier = Modifier,
) {
    val bgColor = Color(0xFF9999FF)
    BoxWithConstraints(modifier = modifier
        .background(bgColor)
        .aspectRatio(1f)) {
        val minAvailableSide = if (maxWidth < maxHeight) maxWidth else maxHeight
        val cellSize = minAvailableSide * 0.11f

        Column {
            ChessBoardHorizontalLabels(cellSize = cellSize, whiteTurn = null)
            (0..7).forEach {
                val row = 8-it
                val rowLabel = "${Char('0'.code + row)}"
                val firstIsWhite = it % 2 == 0
                ChessBoardCellsLine(cellSize = cellSize, firstCellWhite = firstIsWhite, rowLabel = rowLabel)
            }
            ChessBoardHorizontalLabels(cellSize = cellSize, whiteTurn = null)
        }
    }
}

@Composable
private fun ChessBoardCellsLine(
    modifier: Modifier = Modifier,
    cellSize: Dp,
    firstCellWhite: Boolean,
    rowLabel: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ChessBoardVerticalLabel(text = rowLabel, cellSize = cellSize)
        (0..7).forEach {
            ChessBoardCell(isWhite = if ((it % 2) == 0) firstCellWhite else !firstCellWhite, size = cellSize)
        }
        ChessBoardVerticalLabel(text = rowLabel, cellSize = cellSize)
    }
}

@Composable
private fun ChessBoardVerticalLabel(
    modifier: Modifier = Modifier,
    text: String,
    cellSize: Dp,
) {
    val fontSize = with(LocalDensity.current) {
        (cellSize * 0.3f).toSp()
    }
    Column(modifier = modifier
        .width(cellSize / 2)
        .height(cellSize / 2)
        .background(Color.Transparent),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
    ) {
        Text(text, fontWeight = FontWeight.Bold, color = Color.Yellow, fontSize = fontSize)
    }
}

@Composable
private fun ChessBoardHorizontalLabels(
    modifier: Modifier = Modifier,
    cellSize: Dp,
    whiteTurn: Boolean?
) {
    val fontSize = with(LocalDensity.current) {
        (cellSize * 0.3f).toSp()
    }
    Row(modifier = modifier
        .fillMaxWidth()
        .height(cellSize / 2)
    ) {
        Row(modifier = Modifier
            .width(cellSize / 2)
            .height(cellSize / 2)) {
            Text(
                text = "",
                fontWeight = FontWeight.Bold,
                color = Color.Transparent,
                fontSize = fontSize
            )
        }
        (0..7).forEach {
            val col = it
            val colLabel = "${Char('A'.code + col)}"
            Row(modifier = Modifier
                .width(cellSize)
                .height(cellSize / 2),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = colLabel,
                    fontWeight = FontWeight.Bold,
                    color = Color.Yellow,
                    fontSize = fontSize,
                    textAlign = TextAlign.Center,
                )
            }
        }
        if (whiteTurn == null) {
            Row(modifier = Modifier
                .width(cellSize / 2)
                .height(cellSize / 2)) {
                Text(
                    text = "",
                    fontWeight = FontWeight.Bold,
                    color = Color.Transparent,
                    fontSize = fontSize
                )
            }
        }
        else {
            val color = if (whiteTurn) Color.White else Color.Black
            Column(modifier = Modifier
                .width(cellSize / 2)
                .height(cellSize / 2)
                .clip(CircleShape)
                .background(color)
            ) {

            }
        }
    }
}

@Composable
private fun ChessBoardCell(
    modifier: Modifier = Modifier,
    isWhite: Boolean,
    size: Dp
    ) {
    val bgColor = if (isWhite) Color(0xFFFFDEAD) else Color(0xFFCD853F)
    Surface(modifier = modifier.size(size)) {
        Column(modifier = Modifier.background(bgColor)) {
            
        }
    }
}

@Preview
@Composable
fun ChessBoardPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        ChessBoard(modifier = Modifier.fillMaxSize())
    }
}