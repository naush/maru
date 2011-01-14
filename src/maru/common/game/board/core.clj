(ns maru.common.game.board.core
  (:refer-clojure :exclude [empty])
  (:require [maru.common.game.state.core :as state])
  (:require [maru.common.utility.core :as utility]))

(def open 0)

(def black 1)

(def white 2)

(def empty (vec (repeat (* state/size state/size) open)))

(defn to-point [x y] (+ x (* y state/size)))

(defn to-x [point] (rem point state/size))

(defn to-y [point] (int (/ point state/size)))

(defn set-stone [board point color] (assoc board point color))

(defn set-black [board point] (set-stone board point black))

(defn set-white [board point] (set-stone board point white))

(defn remove-stone [board point] (assoc board point open))

(defn set-stones [board stones] (reduce #(set-stone %1 (:point %2) (:color %2)) board stones))

(defn remove-stones [board stones]
  (if (empty? stones) board
    (reduce #(remove-stone %1 %2) board stones)))

(defn color [board point] (nth board point))

(defn open? [board x y] (= open (nth board (to-point x y))))

(defn out-of-bound [x y]
  (let [size (dec state/size)]
    (or (< x 0) (> x size) (< y 0) (> y size))))

(defn find-neighbors [point]
  (let [x (to-x point)
        y (int (/ point state/size))
        north [x (dec y)]
        east [(inc x) y]
        south [x (inc y)]
        west [(dec x) y]]
    (set (map #(+ (first %1) (* (second %1) state/size))
      (filter #(not (out-of-bound (first %1) (second %1)))
        (list north east south west))))))
