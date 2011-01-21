(ns maru.common.pattern.core
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.sgf.core :as sgf])
  (:use [maru.common.utility.core :only [letter-to-digit]]))

(defn sgf-move-to-point [move]
  (let [x (letter-to-digit (first move))
        y (letter-to-digit (second move))]
    (board/to-point x y)))

(defn sgf-move-to-stone [move]
  (let [color (first (first move))
        move (second (first move))]
    (sgf-move-to-point move)))

(defn from-sgf [node]
  (let [moves (sgf/dump-moves node)]
    (map #(sgf-move-to-stone %) moves)))

(defn next-move [pattern log]
  (cond
    (and (empty? log) (empty? pattern)) -1
    (empty? log) (first pattern)
    (empty? pattern) -1
    (= (first pattern) (first log)) (recur (rest pattern) (rest log))
    :else (recur pattern (rest log))))
