(ns maru.common.pattern.core
  (:require [maru.common.game.board.core :as board])
  (:require [maru.common.sgf.core :as sgf])
  (:use [maru.common.utility.core :only [letter-to-digit]]))

(defn swap [point size]
  (let [x (board/to-x point size)
        y (board/to-y point size)]
    (board/to-point y x size)))

(defn flip-x [point size]
  (let [x (board/to-x point size)
        y (board/to-y point size)]
    (board/to-point (- (dec size) x) y size)))

(defn flip-y [point size]
  (let [x (board/to-x point size)
        y (board/to-y point size)]
    (board/to-point x (- (dec size) y) size)))

(defn flip [point size]
  (let [x (board/to-x point size)
        y (board/to-y point size)]
    (board/to-point (- (dec size) x)
                    (- (dec size) y)
                    size)))

(defn sgf-move-to-point [move size]
  (let [x (letter-to-digit (first move))
        y (letter-to-digit (second move))]
    (board/to-point x y size)))

(defn sgf-move-to-stone [move size]
  (let [color (first (first move))
        move (second (first move))]
    (sgf-move-to-point move size)))

(defn from-sgf [node size]
  (let [moves (sgf/dump-moves node)]
    (map #(sgf-move-to-stone % size) moves)))

(defn next-move [pattern log]
  (cond
    (and (empty? log) (empty? pattern)) -1
    (empty? log) (first pattern)
    (empty? pattern) -1
    (= (first pattern) (first log)) (recur (rest pattern) (rest log))
    :else (recur pattern (rest log))))

(defn next-moves [patterns log]
  (map #(next-move % log) patterns))
