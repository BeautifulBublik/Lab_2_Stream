/*
 * Copyright (c) 2014, NTUU KPI, Computer systems department and/or its affiliates. All rights reserved.
 * NTUU KPI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 */
package Lab_2;

/**
 * Клас для збирання статистики за параметром  атака істоти.
 */
class CreatureStats {
	/** Мінімальне значення (початково — найбільше можливе число). */
    private double min = Double.MAX_VALUE;

    /** Максимальне значення (початково — найменше можливе число). */
    private double max = -Double.MAX_VALUE;

    /** Сума всіх значень. */
    private double sum = 0;

    /** Сума квадратів значень — для обчислення дисперсії. */
    private double sumOfSquares = 0;

    /** Кількість доданих елементів. */
    private long count = 0;

    /**
     * Додає нове значення до статистики.
     *
     * @param attackValue значення, яке потрібно врахувати
     */
    public void add(double attackValue) {
        if (attackValue < min) min = attackValue;
        if (attackValue > max) max = attackValue;
        sum += attackValue;
        sumOfSquares += attackValue * attackValue;
        count++;
    }

    /** @return мінімальне значення або 0, якщо даних немає */
    public double getMin() {
        return count == 0 ? 0 : min;
    }

    /** @return максимальне значення або 0, якщо даних немає */
    public double getMax() {
        return count == 0 ? 0 : max;
    }

    /** @return середнє значення */
    public double getAverage() {
        return count == 0 ? 0 : sum / count;
    }

    /**
     * Обчислює стандартне відхилення.
     *
     * Формула дисперсії:
     * Стандартне відхилення
     *
     * @return стандартне відхилення
     */
    public double getStandardDeviation() {
        if (count <= 1) return 0.0;

        double variance = (sumOfSquares - (sum * sum) / count) / count;

        return Math.sqrt(Math.max(0, variance));
    }

    /**
     * Комбінує поточну статистику зі статистикою іншого об’єкта.
     * Використовується у Collector під час паралельної обробки.
     *
     * @param other інший контейнер статистики
     * @return цей же контейнер після об’єднання
     */
    public CreatureStats combine(CreatureStats other) {
        this.min = Math.min(this.min, other.min);
        this.max = Math.max(this.max, other.max);
        this.sum += other.sum;
        this.sumOfSquares += other.sumOfSquares;
        this.count += other.count;
        return this;
    }

    /**
     * Повертає зручний для читання результат статистики.
     */
    @Override
    public String toString() {
        return String.format(
            "Статистика (N=%d):\n Min: %.2f\n Max: %.2f\n Avg: %.2f\n StdDev: %.4f",
            count, getMin(), getMax(), getAverage(), getStandardDeviation()
        );
    }
}