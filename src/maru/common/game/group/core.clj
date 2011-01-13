(ns maru.common.game.group.core
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.game.rule.core :as rule])
  (:require [maru.common.utility.core :as utility]))

(defn craft [color stones]
  (hash-map :color color :stones (set stones)))

(defn ally? [board neighbor color] (= color (board/color board neighbor)))

(defn unique-ally? [board neighbor color allies] (and (ally? board neighbor color) (utility/!contains allies neighbor)))

(defn find-ally-recursive [board neighbor color allies]
  (let [allies (cons neighbor allies)
        neighbors (board/find-neighbors neighbor)]
    (set (reduce
      #(if (and (ally? board %2 color) (utility/!contains %1 %2)) (find-ally-recursive board %2 color %1) %1)
        allies neighbors))))

(defn find-ally [board pos color]
  (let [ally (find-ally-recursive board pos color ())]
  (craft color ally)))

(defn find-enemy [board pos color]
  (let [color (if (= board/white color) board/black board/white)
        neighbors (board/find-neighbors pos)]
    (set (reduce #(if (unique-ally? board %2 color %1) (merge %1 (find-ally board %2 color)) %1) () neighbors))))

(defn find-liberty-helper [board pos]
  (let [neighbors (board/find-neighbors pos)]
    (filter #(= board/gray (board/color board %)) neighbors)))

(defn find-liberty [board stones]
  (loop [stones stones liberties ()]
    (let [stone (first stones)]
      (if (= 0 (count stones))
        (set liberties)
        (recur (rest stones) (clojure.set/union (find-liberty-helper board stone) liberties))))))
