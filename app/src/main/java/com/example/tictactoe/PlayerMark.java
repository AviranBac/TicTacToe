package com.example.tictactoe;

public enum PlayerMark {
    NONE(null, null, null),
    X(R.drawable.x, R.drawable.xplay, R.drawable.xwin),
    O(R.drawable.o, R.drawable.oplay, R.drawable.owin);

    final Integer markDrawable;
    final Integer playStatusDrawable;
    final Integer winStatusDrawable;

    PlayerMark(Integer markDrawable, Integer playStatusDrawable, Integer winStatusDrawable) {
        this.markDrawable = markDrawable;
        this.playStatusDrawable = playStatusDrawable;
        this.winStatusDrawable = winStatusDrawable;
    }

    public Integer getMarkDrawable() {
        return this.markDrawable;
    }

    public Integer getPlayStatusDrawable() {
        return this.playStatusDrawable;
    }

    public Integer getWinStatusDrawable() {
        return this.winStatusDrawable;
    }
}
