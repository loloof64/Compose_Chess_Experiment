/*
    Compose chess experiment
    Copyright (C) 2022  Laurent Bernabe

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

 */
package com.loloof64.compose_chess_experiment.ui.components.chess_board

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.loloof64.compose_chess_experiment.R

const val emptyCell = ' '

@Composable
fun ChessBoard(
    position: String = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
    reversed: Boolean = false,
) {
    val positionParts = position.split(' ')
    val lineParts = positionParts[0].split('/')
    val isWhiteTurn = positionParts[1] == "w"
    val bgColor = Color(0xFF9999FF)
    BoxWithConstraints {
        val heightBasedAspectRatio = maxHeight > maxWidth
        val minAvailableSide = if (maxWidth < maxHeight) maxWidth else maxHeight
        val cellSize = minAvailableSide * 0.11f

        Column(
            modifier = Modifier
                .aspectRatio(1f, heightBasedAspectRatio)
                .background(bgColor)
        ) {
            ChessBoardHorizontalLabels(cellSize = cellSize, whiteTurn = null, reversed = reversed)
            (0..7).forEach {
                val row = if (reversed) it + 1 else 8 - it
                val rowLabel = "${Char('0'.code + row)}"
                val firstIsWhite = it % 2 == 0
                val piecesValues = lineParts[if (reversed) 7-it else it]
                ChessBoardCellsLine(
                    cellSize = cellSize, firstCellWhite = firstIsWhite,
                    rowLabel = rowLabel, piecesValues = piecesValues,
                    reversed = reversed
                )
            }
            ChessBoardHorizontalLabels(cellSize = cellSize, whiteTurn = isWhiteTurn, reversed = reversed)
        }
    }
}

@Composable
private fun ChessBoardCellsLine(
    modifier: Modifier = Modifier,
    cellSize: Dp,
    firstCellWhite: Boolean,
    rowLabel: String,
    piecesValues: String,
    reversed: Boolean,
) {
    val pieces = piecesValues.flatMap { value ->
        if (value.isDigit()) {
            List(value.digitToInt()) { emptyCell }
        } else {
            listOf(value)
        }
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ChessBoardVerticalLabel(text = rowLabel, cellSize = cellSize)
        (0..7).forEach {
            ChessBoardCell(
                isWhite = if ((it % 2) == 0) firstCellWhite else !firstCellWhite,
                size = cellSize,
                pieceValue = pieces[if (reversed) 7 - it else it]
            )
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
    Column(
        modifier = modifier
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
    whiteTurn: Boolean?,
    reversed: Boolean
) {
    val fontSize = with(LocalDensity.current) {
        (cellSize * 0.3f).toSp()
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(cellSize / 2)
    ) {
        Row(
            modifier = Modifier
                .width(cellSize / 2)
                .height(cellSize / 2)
        ) {
            Text(
                text = "",
                fontWeight = FontWeight.Bold,
                color = Color.Transparent,
                fontSize = fontSize
            )
        }
        (0..7).forEach {
            val col = if (reversed) 7-it else it
            val colLabel = "${Char('A'.code + col)}"
            Row(
                modifier = Modifier
                    .width(cellSize)
                    .height(cellSize / 2),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
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
            Row(
                modifier = Modifier
                    .width(cellSize / 2)
                    .height(cellSize / 2)
            ) {
                Text(
                    text = "",
                    fontWeight = FontWeight.Bold,
                    color = Color.Transparent,
                    fontSize = fontSize
                )
            }
        } else {
            val color = if (whiteTurn) Color.White else Color.Black
            Column(
                modifier = Modifier
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
    size: Dp,
    pieceValue: Char,
) {
    val bgColor = if (isWhite) Color(0xFFFFDEAD) else Color(0xFFCD853F)
    Surface(modifier = modifier.size(size)) {
        Column(modifier = Modifier.background(bgColor)) {
            if (pieceValue != emptyCell) {
                Image(
                    imageVector = ImageVector.vectorResource(id = getVectorForPiece(pieceValue)),
                    contentDescription = stringResource(id = R.string.chess_piece)
                )
            }
        }
    }
}

fun getVectorForPiece(pieceValue: Char): Int {
    return when (pieceValue) {
        'P' -> R.drawable.pl
        'N' -> R.drawable.nl
        'B' -> R.drawable.bl
        'R' -> R.drawable.rl
        'Q' -> R.drawable.ql
        'K' -> R.drawable.kl

        'p' -> R.drawable.pd
        'n' -> R.drawable.nd
        'b' -> R.drawable.bd
        'r' -> R.drawable.rd
        'q' -> R.drawable.qd
        'k' -> R.drawable.kd
        else -> throw IllegalArgumentException("Not recognized piece $pieceValue")
    }
}

@Preview(device = Devices.DEFAULT)
@Preview(device = Devices.TABLET)
@Composable
fun ChessBoardPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            /* TODO */
        }) {
            Text(text = "New game", fontSize = 25.sp)
        }
        ChessBoard(
            position = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
        )
    }
}