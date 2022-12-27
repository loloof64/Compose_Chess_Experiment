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

import androidx.lifecycle.ViewModel
import com.loloof64.compose_chess_experiment.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.wolfraam.chessgame.ChessGame
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

enum class GameError {
    InvalidFen,
}

data class ChessGameUiState(
    val currentPosition: String,
    private val playedPositions: Map<String, Int>,
) {
    companion object {
        const val emptyPosition = "8/8/8/8/8/8/8/8 w - - 0 1"
        const val defaultPosition = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
    }
    private var board: ChessGame

    init {
        try {
            board = ChessGame(currentPosition)
        }
        catch (ex: IllegalArgumentException) {
            board = ChessGame(emptyPosition)
            throw ex
        }
    }
}


@HiltViewModel
class ChessGameViewModel @Inject constructor() : ViewModel() {
    private val _uiState: MutableStateFlow<Response<ChessGameUiState, GameError>> = MutableStateFlow(
        Response.Success(ChessGameUiState(
            currentPosition = ChessGameUiState.emptyPosition,
            playedPositions = mapOf(),
        ))
    )
    val uiState: StateFlow<Response<ChessGameUiState, GameError>> = _uiState

    fun newGame(startPosition: String = ChessGameUiState.defaultPosition) {
        _uiState.update { _ ->
            try {
                val newState = ChessGameUiState(
                    currentPosition = startPosition,
                    playedPositions = mapOf(startPosition to 1)
                )
                return@update Response.Success(newState)
            }
            catch (ex: IllegalArgumentException) {
                return@update Response.Failure(GameError.InvalidFen)
            }
        }
    }
}