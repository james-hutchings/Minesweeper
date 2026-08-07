package mineSweeper;

public enum Difficulty {
    EASY("Easy", 9, 9, 0.12),
    MEDIUM("Medium", 16, 16, 0.15),
    HARD("Hard", 24, 24, 0.17);

    private final String label;
    private final int rows;
    private final int cols;
    private final double bombPercent;

    Difficulty(String label, int rows, int cols, double bombPercent) {
        this.label = label;
        this.rows = rows;
        this.cols = cols;
        this.bombPercent = bombPercent;
    }

    public String getLabel() {
        return label;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public double getBombPercent() {
        return bombPercent;
    }

    @Override
    public String toString() {
        return label;
    }
}
