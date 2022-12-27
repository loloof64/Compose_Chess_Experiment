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
package com.loloof64.compose_chess_experiment.ui.screens.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.loloof64.compose_chess_experiment.R
import com.loloof64.compose_chess_experiment.ui.components.chess_board.ChessBoard
import com.loloof64.compose_chess_experiment.utils.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    viewModel: ChessGameViewModel = viewModel()
) {
    var reversed by rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.game_page)) },
                actions = {
                    IconButton(onClick = { reversed = !reversed }) {
                        Icon(
                            painter = painterResource(id = R.drawable.swap_vert),
                            contentDescription = stringResource(id = R.string.toggle_board_side),
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                viewModel.newGame("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2")
            }) {
                Text(text = "New game")
            }
            when (val state = viewModel.uiState.collectAsState().value) {
                is Response.Success -> {
                    ChessBoard(
                        position = state.data.currentPosition,
                        reversed = reversed
                    )
                }
                else -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(text = stringResource(id = R.string.chess_position_error))
                    }
                }
            }
        }
    }
}