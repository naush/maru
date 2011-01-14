(ns maru.common.game.group.core
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.utility.core :as utility]))

(defn craft [color stones liberties]
  (hash-map :color color :stones (set stones) :liberties (set liberties)))

(defn ally? [board neighbor color] (= color (board/color board neighbor)))

(defn unique-ally? [board neighbor color allies] (and (ally? board neighbor color) (utility/!contains allies neighbor)))

(defn find-liberty-helper [board point]
  (let [neighbors (board/find-neighbors point)]
    (filter #(= board/open (board/color board %)) neighbors)))

(defn find-liberty [board stones]
  (loop [stones stones liberties ()]
    (let [stone (first stones)]
      (if (= 0 (count stones))
        (set liberties)
        (recur (rest stones) (clojure.set/union (find-liberty-helper board stone) liberties))))))

(defn find-ally-recursive [board neighbor color allies]
  (let [allies (cons neighbor allies)
        neighbors (board/find-neighbors neighbor)]
    (set (reduce
      #(if (and (ally? board %2 color) (utility/!contains %1 %2)) (find-ally-recursive board %2 color %1) %1)
        allies neighbors))))

(defn find-ally [board point color]
  (let [ally (find-ally-recursive board point color ())
        liberties (find-liberty board ally)]
  (craft color ally liberties)))

(defn find-enemies [board point color]
  (let [color (if (= board/white color) board/black board/white)
        neighbors (board/find-neighbors point)]
    (set (reduce #(if (unique-ally? board %2 color %1) (merge %1 (find-ally board %2 color)) %1) () neighbors))))

(defn find-captures [enemies]
  (reduce #(if (= (count (:liberties %2)) 0)(clojure.set/union %1 (:stones %2)) %1) () enemies))
