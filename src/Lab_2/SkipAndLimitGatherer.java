/*
 * Copyright (c) 2014, NTUU KPI, Computer systems department and/or its affiliates. All rights reserved.
 * NTUU KPI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 */
package Lab_2;


import java.util.function.Predicate;
import java.util.stream.Gatherer;


/**
 *  Пропускає перші N елементів, що задовольняють умову.
 *  Після пропуску передає вниз по стріму рівно M елементів.
 */
public class SkipAndLimitGatherer {
	/**
     * @param skipCondition умова, за якою визначається, що елемент підлягає пропуску
     * @param skipQuota     кількість елементів, які потрібно пропустити
     * @param limitQuota    кількість елементів, які потрібно передати downstream
     * @param <T>           тип елементів стріму
     * @return сконфігурований Gatherer
     */
	public static <T> Gatherer<T, ?, T> create(Predicate<T> skipCondition, long skipQuota, long limitQuota) {
		// Створення початкового стану
		return Gatherer.ofSequential(() -> new State<>(skipCondition, skipQuota, limitQuota),
				// Інтегратор: обробка кожного елемента стріму
				(state, element, downstream) -> {
					//Якщо ще потрібно пропускати й умова виконана — пропускаємо елемент
					if (state.shouldSkip(element)) {
						return true;
					}
					//Якщо елемент можна передавати вниз
					if (state.canEmit()) {
						state.incrementEmitted();
						downstream.push(element);
						// Якщо досягли ліміту — завершуємо стрім
						if (!state.canEmit()) {
							return false;
						}
						return true;
					}

					return false;
				});
	}
	/**
     * Внутрішній стан Gatherer. Зберігає:
     *   - skipQuota: скільки елементів ще потрібно пропустити,
     *   - limitQuota: скільки потрібно передати вниз,
     *   - emittedCount: скільки вже передано,
     *   - skipCondition: умову пропуску.
     */
	static class State<T> {
		private final Predicate<T> skipCondition;
		private long skipQuota;
		private final long limitQuota;
		private long emittedCount;
		  /**
         * Ініціалізація стану.
         */
		State(Predicate<T> skipCondition, long skipQuota, long limitQuota) {
			this.skipCondition = skipCondition;
			this.skipQuota = skipQuota;
			this.limitQuota = limitQuota;
			this.emittedCount = 0;
		}
		/**
         * Перевіряє, чи потрібно пропустити елемент.
         */
		boolean shouldSkip(T element) {
			if (skipQuota > 0 && skipCondition.test(element)) {
				skipQuota--;
				return true;
			}
			return false;
		}
		 /**
         * Чи ще можна передавати елементи вниз.
         */
		boolean canEmit() {
			return emittedCount < limitQuota;
		}
		   /**
         * Збільшує лічильник переданих елементів.
         */
		void incrementEmitted() {
			emittedCount++;
		}
	}
}