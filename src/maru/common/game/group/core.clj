(ns maru.common.game.group.core
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.game.rule.core :as rule]))

(defn craft [color stones]
  (hash-map :color color :stones (set stones)))

(defn ally? [board neighbor color neighbors]
  (not (or (board/out-of-bound neighbor)
           (not (= color (board/color board neighbor)))
           (.contains neighbors neighbor))))

(defn find-neighboring-ally [board neighbor color allies]
  (let [allies (cons neighbor allies)
        north (- neighbor board/size)
        east (inc neighbor)
        south (+ neighbor board/size)
        west (dec neighbor)]
    (set (reduce
      #(if (ally? board %2 color %1) (find-neighboring-ally board %2 color %1) %1)
        allies (list north east south west)))))

(defn find-ally [board pos color]
  (hash-map :color color :stones (find-neighboring-ally board pos color ())))

(defn find-enemy [board pos color]
  (let [color (if (= board/white color) board/black board/white)
        north (- pos board/size)
        east (inc pos)
        south (+ pos board/size)
        west (dec pos)]
    (set (reduce #(if (ally? board %2 color %1) (merge %1 (find-ally board %2 color)) %1) () (list north east south west)))))
