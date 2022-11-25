package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    private final Map<Integer, List<Integer>> drawableToPossibleWinningScenarios = new HashMap<Integer, List<Integer>>() {{
        put(R.drawable.mark1, Arrays.asList(0, 4, 8));
        put(R.drawable.mark2, Arrays.asList(2, 4, 6));
        put(R.drawable.mark3, Arrays.asList(0, 3, 6));
        put(R.drawable.mark4, Arrays.asList(1, 4, 7));
        put(R.drawable.mark5, Arrays.asList(2, 5, 8));
        put(R.drawable.mark6, Arrays.asList(0, 1, 2));
        put(R.drawable.mark7, Arrays.asList(3, 4, 5));
        put(R.drawable.mark8, Arrays.asList(6, 7, 8));
    }};
    private List<ImageView> cellViews;
    private ImageView winMarkView;
    private Button resetBtn;
    private boolean isActive;
    private List<PlayerMark> gameBoard;
    private int numberOfTurns;
    private PlayerMark currentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.cellViews = Stream.of(R.id.cell_btn_0, R.id.cell_btn_1, R.id.cell_btn_2, R.id.cell_btn_3, R.id.cell_btn_4, R.id.cell_btn_5, R.id.cell_btn_6, R.id.cell_btn_7, R.id.cell_btn_8)
                .map(id -> (ImageView) findViewById(id))
                .collect(Collectors.toList());
        this.winMarkView = findViewById(R.id.win_mark);
        this.resetBtn = findViewById(R.id.reset_btn);

        this.initializeOnClickListeners();
        this.initializeGame();
    }

    private void initializeOnClickListeners() {
        this.cellViews.forEach(cellBtn -> cellBtn.setOnClickListener(this::onCellClick));
        this.resetBtn.setOnClickListener(this::onResetClick);
    }

    private void onResetClick(View view) {
        this.resetBtn.setVisibility(View.INVISIBLE);
        this.winMarkView.setImageResource(R.drawable.empty);
        this.cellViews.forEach(cellBtn -> cellBtn.setImageResource(R.drawable.empty));

        this.initializeGame();
    }

    private void initializeGame() {
        this.isActive = true;
        this.gameBoard = new ArrayList<>(Collections.nCopies(9, PlayerMark.NONE));
        this.numberOfTurns = 0;
        this.currentPlayer = PlayerMark.NONE;
        this.setNextPlayer();
    }

    private void setNextPlayer() {
        this.currentPlayer = this.currentPlayer == PlayerMark.X ? PlayerMark.O : PlayerMark.X;
        this.setStatusImage(this.currentPlayer.getPlayStatusDrawable());
    }

    private void onCellClick(View view) {
        if (this.isActive) {
            ImageView currentCellView = (ImageView) view;
            int cellIndex = Integer.parseInt(currentCellView.getTag().toString());

            if (this.gameBoard.get(cellIndex) == PlayerMark.NONE) {
                this.markCell(cellIndex, currentCellView);

                Optional<Integer> optionalWinDrawable = this.calculateOptionalWinDrawable();

                if (optionalWinDrawable.isPresent()) {
                    this.endGame(this.currentPlayer.getWinStatusDrawable());
                    this.winMarkView.setImageResource(optionalWinDrawable.get());
                } else if (this.numberOfTurns == this.gameBoard.size()) {
                    this.endGame(R.drawable.nowin);
                } else {
                    this.setNextPlayer();
                }
            }
        }
    }

    private void endGame(int statusImage) {
        this.isActive = false;
        this.setStatusImage(statusImage);
        this.resetBtn.setVisibility(View.VISIBLE);
    }

    private void setStatusImage(int imageResource) {
        ((ImageView) findViewById(R.id.status_btn)).setImageResource(imageResource);
    }

    private void markCell(int cellIndex, ImageView currentCellView) {
        this.gameBoard.set(cellIndex, this.currentPlayer);
        currentCellView.setImageResource(this.currentPlayer.getMarkDrawable());
        this.numberOfTurns++;
    }

    private Optional<Integer> calculateOptionalWinDrawable() {
        return this.drawableToPossibleWinningScenarios.entrySet().stream()
                .filter(entry -> this.doesPossibleScenarioWin(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    private boolean doesPossibleScenarioWin(List<Integer> scenario) {
        return this.gameBoard.get(scenario.get(0)) == this.currentPlayer &&
                this.gameBoard.get(scenario.get(1)) == this.currentPlayer &&
                this.gameBoard.get(scenario.get(2)) == this.currentPlayer;
    }
}