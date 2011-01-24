(ns maru.common.game.group.core
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.game.color.core :as color])
  (:require [maru.common.utility.core :as utility]))

(defn craft [color stones liberties]
  {:color color :stones (set stones) :liberties (set liberties)})

(defn ally? [board neighbor color] (= color (board/color board neighbor)))

(defn unique-ally? [board neighbor color allies]
  (and (ally? board neighbor color) (not (.contains allies neighbor))))

(defn find-liberty-helper [board point size]
  (let [neighbors (board/find-neighbors point size)]
    (filter #(= color/open (board/color board %)) neighbors)))

(defn find-liberty [board stones size]
  (loop [stones stones liberties ()]
    (let [stone (first stones)]
      (if (= 0 (count stones))
        (set liberties)
        (recur (rest stones)
          (clojure.set/union (find-liberty-helper board stone size) liberties))))))

(defn find-ally-recursive [board neighbor color allies size]
  (let [allies (cons neighbor allies)
        neighbors (board/find-neighbors neighbor size)]
    (set (reduce
      #(if (and (ally? board %2 color) (not (.contains %1 %2)))
           (find-ally-recursive board %2 color %1 size) %1)
        allies neighbors))))

(defn find-ally [board point color size]
  (let [ally (find-ally-recursive board point color () size)
        liberties (find-liberty board ally size)]
  (craft color ally liberties)))

(defn find-enemies [board point color size]
  (let [color (if (= color/white color) color/black color/white)
        neighbors (board/find-neighbors point size)]
    (set (reduce #(if (unique-ally? board %2 color %1)
      (merge %1 (find-ally board %2 color size)) %1) () neighbors))))

(defn find-captures [enemies]
  (reduce #(if (= (count (:liberties %2)) 0)(clojure.set/union %1 (:stones %2)) %1) () enemies))
